package save;
import com.google.gson.Gson;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import entities.*;
import other_things.*;
import maps.*;


public class SaveManager {
    private static final Gson gson = new Gson();

    // Сохранение игры
    public static void saveGame(Player player, Map map, Diologies dialogs, Quests quests, BossBattle boss) {
        GameSaveData data = new GameSaveData(player, map, dialogs, quests, boss);
        try (FileWriter writer = new FileWriter("savegame.json")) {
            writer.write(gson.toJson(data));
            System.out.println("Игра сохранена!");
        } catch (IOException e) {
            System.out.println("Ошибка сохранения.");
        }
    }

    // Загрузка игры
    public static boolean loadGame(Player player, Map map, Diologies dialogs, Quests quests, BossBattle boss) {
        try {
            String json = new String(Files.readAllBytes(Paths.get("savegame.json")));
            GameSaveData data = gson.fromJson(json, GameSaveData.class);

            data.player.applyTo(player);
            data.map.applyTo(map);
            data.dialogs.applyTo(dialogs);
            data.quests.applyTo(quests);
            data.boss.applyTo(boss);

            System.out.println("Игра загружена!");
            return true;
        } catch (Exception e) {
            System.out.println("Сохранение отсутствует или повреждено.");
            return false;
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
        }

        public void applyTo(Player player) {
            player.setPosition(x, y);
            player.setCurrentMapType(Player.MapType.valueOf(currentMap));
            player.setHealth(health);
            player.setDmg(dmg);
            player.setHealingPotion(healingPotion);
            player.setBoostDmgPotion(boostDmgPotion);
            player.addCoins(0); // обновление монет
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
            setMap(map, ogreMap, Player.MapType.OGRE_LANDS);
            setMap(map, ruinMap, Player.MapType.RUINS);
            setMap(map, frozenMap, Player.MapType.FROZEN_MAP);
            setMap(map, fightMap, Player.MapType.FIGHT_MAP);
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

        private void setMap(Map map, List<List<String>> data, Player.MapType type) {
            Cell[][] target = map.getCurrentMapGrid(type);
            for (int x = 0; x < data.size(); x++) {
                List<String> row = data.get(x);
                for (int y = 0; y < row.size(); y++) {
                    target[x][y].setCelltype(row.get(y));
                }
            }
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
        public boolean riddleCompleted;

        public QuestsData(Quests quests) {
            this.riddleCompleted = true; // или логика проверки
        }

        public void applyTo(Quests quests) {
            // Здесь можно восстановить прогресс квестов
        }
    }

    // DTO: Босс
    public static class BossData {
        public int bossHealth;
        public boolean hasUsedStun;
        public boolean hasUsedExhaustion;

        public BossData(BossBattle boss) {
            this.bossHealth = boss.isWonLich() == 0 ? 0 : boss.isWonLich() == 1 ? 150 : 300;
            this.hasUsedStun = false;
            this.hasUsedExhaustion = false;
        }

        public void applyTo(BossBattle boss) {
            // Здесь можно восстановить состояние босса
        }
    }

    // Объединяющий класс
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
}