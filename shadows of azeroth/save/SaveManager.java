package save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entities.Player;
import maps.Map;
import other_things.Diologies;
import other_things.Quests;
import other_things.BossBattle;
import maps.Cell;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SaveManager {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // 1) Сохранить игру
    public static void saveGame(Player player, Map map, Diologies dialogs, Quests quests, BossBattle boss, String slot) {
        if (player.getUsername() == null || player.getUsername().isEmpty()) {
            System.out.println("Введите имя игрока перед сохранением.");
            return;
        }

        // Создаем папку saves, если её нет
        Path savesDir = Paths.get("saves");
        try {
            Files.createDirectories(savesDir);
        } catch (IOException e) {
            System.out.println("Ошибка создания папки 'saves': " + e.getMessage());
            return;
        }

        GameSaveData data = new GameSaveData(player, map, dialogs, quests, boss);
        String filename = "saves/" + player.getUsername() + "_slot" + slot + ".json";

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(gson.toJson(data));
            System.out.println("Игра сохранена в " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка сохранения: " + e.getMessage());
        }
    }

    // 2) Загрузить игру
    public static boolean loadGame(Player player, Map map, Diologies dialogs, Quests quests, BossBattle boss, String slot) {
        if (player.getUsername() == null || player.getUsername().isEmpty()) {
            System.out.println("Введите имя игрока для загрузки сохранения.");
            return false;
        }

        String filename = "saves/" + player.getUsername() + "_slot" + slot + ".json";
        try {
            String json = new String(Files.readAllBytes(Paths.get(filename)));
            GameSaveData data = gson.fromJson(json, GameSaveData.class);

            data.player.applyTo(player);
            data.map.applyTo(map);
            data.dialogs.applyTo(dialogs);
            data.quests.applyTo(quests);
            data.boss.applyTo(boss);

            System.out.println("Игра загружена из " + filename);
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка загрузки: сохранение отсутствует или повреждено");
            return false;
        }
    }

    // 3) Автосохранение после ключевых событий
    public static void autoSaveGame(Player player, Map map, Diologies dialogs, Quests quests, BossBattle boss) {
        if (player.getUsername() == null || player.getUsername().isEmpty()) return;

        GameSaveData data = new GameSaveData(player, map, dialogs, quests, boss);
        String filename = "saves/" + player.getUsername() + "_autosave.json";

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(gson.toJson(data));
            System.out.println("Автосохранение выполнено: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка автосохранения.");
        }
    }

    // 4) Просмотр рекордов
    public static void showTopRecords() {
        File file = new File("records.json");
        if (!file.exists()) {
            System.out.println("Нет сохранённых рекордов.");
            return;
        }

        try {
            String json = new String(Files.readAllBytes(file.toPath()));
            List<Record> records = gson.fromJson(json, new TypeToken<List<Record>>(){}.getType());
            if (records == null) records = new ArrayList<>();

            // Сортируем по убыванию
            records.sort((a, b) -> Integer.compare(b.score, a.score));

            System.out.println("\nТОП-5 игроков:");
            for (int i = 0; i < Math.min(5, records.size()); i++) {
                System.out.printf("%d. %s (%s) — %d очков%n", i + 1, records.get(i).username, records.get(i).mapType, records.get(i).score);
            }
        } catch (Exception e) {
            System.out.println("Файл рекордов повреждён или пуст.");
        }
    }

    // 5) Обновление рекордов
    public static void updateRecords(Player player) {
        File file = new File("records.json");
        List<Record> records = new ArrayList<>();

        try {
            if (file.exists()) {
                String json = new String(Files.readAllBytes(file.toPath()));
                records = gson.fromJson(json, new TypeToken<List<Record>>(){}.getType());
            }
        } catch (Exception ignored) {}

        // Добавляем новый рекорд
        Record newRecord = new Record();
        newRecord.username = player.getUsername();
        newRecord.score = player.getScore(); // Подсчёт очков
        newRecord.mapType = player.getCurrentMapType().name();
        newRecord.date = new Date().toString();
        records.add(newRecord);

        // Сортируем и оставляем топ-10
        records.sort((a, b) -> Integer.compare(b.score, a.score));
        if (records.size() > 10) records = records.subList(0, 10);

        // Сохраняем обратно в файл
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(gson.toJson(records));
        } catch (IOException e) {
            System.out.println("Ошибка обновления рекордов.");
        }
    }

    // DTO: Player
    public static class PlayerData {
        public int x;
        public int y;
        public String currentMap;
        public int health;
        public int dmg;
        public int healingPotion;
        public int boostDmgPotion;
        public int coins;
        public boolean hasArtifact;
        public String username;
        public int score;

        public PlayerData(Player player) {
            this.x = player.getX();
            this.y = player.getY();
            this.currentMap = player.getCurrentMapType().name();
            this.health = player.getHealth();
            this.dmg = player.getDmg();
            this.healingPotion = player.getHealingPotion();
            this.boostDmgPotion = player.getBoostDmgPotion();
            this.coins = player.getCoins();
            this.hasArtifact = player.isHasArtifact();
            this.username = player.getUsername();
            this.score = player.getScore();
        }

        public void applyTo(Player player) {
            player.setPosition(x, y);
            player.setCurrentMapType(Player.MapType.valueOf(currentMap));
            player.setHealth(health);
            player.setDmg(dmg);
            player.setHealingPotion(healingPotion);
            player.setBoostDmgPotion(boostDmgPotion);
            player.addCoins(coins - player.getCoins());
            player.setHasArtifact(hasArtifact);
            player.setUsername(username);
            player.setScore(score);
        }
    }

    // DTO: Map
    public static class MapData {
        public List<List<String>> ogreMap = new ArrayList<>();
        public List<List<String>> ruinMap = new ArrayList<>();
        public List<List<String>> frozenMap = new ArrayList<>();
        public List<List<String>> fightMap = new ArrayList<>();

        public MapData(Map map) {
            ogreMap = convertMap(map, Player.MapType.OGRE_LANDS);
            ruinMap = convertMap(map, Player.MapType.RUINS);
            frozenMap = convertMap(map, Player.MapType.FROZEN_MAP);
            fightMap = convertMap(map, Player.MapType.FIGHT_MAP);
        }

        public void applyTo(Map map) {
            map.restoreMapFromList(Player.MapType.OGRE_LANDS, ogreMap);
            map.restoreMapFromList(Player.MapType.RUINS, ruinMap);
            map.restoreMapFromList(Player.MapType.FROZEN_MAP, frozenMap);
            map.restoreMapFromList(Player.MapType.FIGHT_MAP, fightMap);
        }

        private List<List<String>> convertMap(Map map, Player.MapType type) {
            List<List<String>> grid = new ArrayList<>();
            Cell[][] cells = map.getCurrentMapGrid(type);
            for (Cell[] row : cells) {
                List<String> line = new ArrayList<>();
                for (Cell cell : row) {
                    line.add(cell.getCelltype());
                }
                grid.add(line);
            }
            return grid;
        }
    }

    // DTO: Диалоги
    public static class DialogsData {
        public int thrallStage;
        public int smithStage;

        public DialogsData(Diologies dialogs) {
            this.thrallStage = dialogs.getThrallStage();
            this.smithStage = dialogs.getSmithStage();
        }

        public void applyTo(Diologies dialogs) {
            dialogs.setThrallStage(thrallStage);
            dialogs.setSmithStage(smithStage);
        }
    }

    // DTO: Квесты
    public static class QuestsData {
        public int questState;

        public QuestsData(Quests quests) {
            this.questState = quests.getQuestState();
        }

        public void applyTo(Quests quests) {
            quests.setQuestState(questState);
        }
    }

    // DTO: Босс
    public static class BossData {
        public int bossHealth;
        public boolean hasUsedStun;
        public boolean hasUsedExhaustion;

        public BossData(BossBattle boss) {
            this.bossHealth = boss.isWonLich() == 0 ? 0 : boss.isWonLich() == 1 ? 150 : 300;
            this.hasUsedStun = boss.isStunUsed();
            this.hasUsedExhaustion = boss.isExhaustionUsed();
        }

        public void applyTo(BossBattle boss) {
            boss.setBossHealth(bossHealth);
            boss.setStunUsed(hasUsedStun);
            boss.setExhaustionUsed(hasUsedExhaustion);
        }
    }

    // Объединяющий класс для сохранения
    public static class GameSaveData {
        public PlayerData player;
        public MapData map;
        public DialogsData dialogs;
        public QuestsData quests;
        public BossData boss;

        public GameSaveData(Player player, Map map, Diologies dialogs, Quests quests, BossBattle boss) {
            this.player = new PlayerData(player);
            this.map = new MapData(map);
            this.dialogs = new DialogsData(dialogs);
            this.quests = new QuestsData(quests);
            this.boss = new BossData(boss);
        }
    }

    // Класс рекордов
    public static class Record {
        public String username;
        public int score;
        public String mapType;
        public String date;

        public Record() {
            this.date = new Date().toString();
        }
    }
}