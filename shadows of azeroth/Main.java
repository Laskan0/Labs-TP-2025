import buildings.Alchemist;
import buildings.WarriorTower;
import maps.Map;
import entities.Player;
import other_things.Diologies;
import java.util.Scanner;
import other_things.Quests;
import other_things.BossBattle;
import java.util.List;
import entities.Enemy;
import maps.BattleMap;
import save.SaveManager;
import maps.Cell;
import time.GameTime;
import buildings.Totem;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        System.out.print("Введите ваше имя: ");
        String username = inputScanner.nextLine();

        Player player = new Player(0, 2);
        player.setUsername(username);
        GameTime gameTime = new GameTime();
        Diologies dialogs = new Diologies();
        Quests quests = new Quests(inputScanner);
        BossBattle bossBattle = new BossBattle();
        Totem totem = new Totem(gameTime);
        Alchemist alchemist = new Alchemist(gameTime);
        WarriorTower tower = new WarriorTower(gameTime);
        boolean gameOver = false;



        // Показ рекордов при запуске
        SaveManager.showTopRecords();

        // Выбор типа карты
        System.out.println("\nВыберите тип карты:");
        System.out.println("1 - Создать новую карту (редактор)");
        System.out.println("2 - Загрузить стандартную карту");
        System.out.println("3 - Загрузить сохранение");
        System.out.print("Ваш выбор: ");
        String mapChoice = inputScanner.nextLine().trim();

        Map map = null;

        if ("1".equals(mapChoice)) {
            map = new Map(); // Новая карта для редактора
            runMapEditor(map, player);
        } else if ("2".equals(mapChoice)) {
            map = new Map(); // Стандартная карта
            System.out.println("Загружена стандартная карта.");
        } else if ("3".equals(mapChoice)) {
            System.out.println("Выберите слот сохранения (1, 2, 3):");
            String slot = inputScanner.nextLine().trim();
            map = new Map();
            if (!SaveManager.loadGame(player, map, dialogs, quests, bossBattle, slot)) {
                System.out.println("Сохранение отсутствует. Начинаем новую игру...");
                map = new Map(); // Стандартная карта
            }
        } else {
            System.out.println("Неверный выбор. Завершение работы.");

            return;
        }


        Scanner gameScanner = new Scanner(System.in);

        // Установка игрока на карту
        map.setPlayer(player);

        // Основной игровой цикл
        dialogs.displayProlog();
        map.displayCurrentMap(player);
        // запуск игрового времени
        gameTime.start();

        while (!gameOver) {
            System.out.println("\nУправляй: w/a/s/d (ход), 1 (атака), 2 (сплеш), p (состояние), save (сохранить), load (загрузить), records (рекорды)");
            String input = gameScanner.nextLine().toLowerCase();

            switch (input) {
                case "save":
                    System.out.println("Выберите слот (1, 2, 3):");
                    String slot = gameScanner.nextLine().trim();
                    SaveManager.saveGame(player, map, dialogs, quests, bossBattle, slot);
                    break;

                case "load":
                    System.out.println("Выберите слот (1, 2, 3):");
                    slot = gameScanner.nextLine().trim();
                    if (SaveManager.loadGame(player, map, dialogs, quests, bossBattle, slot)) {
                        map.displayCurrentMap(player);
                    }
                    break;

                case "records":
                    SaveManager.showTopRecords();
                    break;

                // В Main.java
                case "w", "a", "s", "d":
                    int dx = input.equals("w") ? -1 : input.equals("s") ? 1 : 0;
                    int dy = input.equals("a") ? -1 : input.equals("d") ? 1 : 0;
                    if (player.playerMove(dx, dy, map)) {
                        map.displayCurrentMap(player); // Добавлено: перерисовка карты
                        checkEvents(player, dialogs, map, gameScanner, quests, bossBattle,totem,alchemist,tower);
                    } else {
                        System.out.println("Движение невозможно!");
                        map.displayCurrentMap(player); // Добавлено: перерисовка карты
                    }
                    break;

                case "p":
                    player.player_condition();
                    System.out.println("ВЫПИТЬ ЗЕЛЬЕ? 1 - здоровья; 2-атаки");
                    String potions = gameScanner.nextLine().toLowerCase();
                    if (potions.equals("1")) {
                        player.setHealth(player.getHealth() + 50);
                        player.setHealingPotion(player.getHealingPotion() - 1);
                        map.displayCurrentMap(player);
                    } else if (potions.equals("2")) {
                        player.setDmg(player.getDmg() + 25);
                        player.setBoostDmgPotion(player.getBoostDmgPotion() - 1);
                        map.displayCurrentMap(player);
                    } else {
                        map.displayCurrentMap(player);
                    }
                    break;

                default:
                    System.out.println("Неверная команда!");
            }

            // Автосохранение после ключевых событий
            if (bossBattle.isWonLich() == 0) {
                int reward = player.getCoins() + 100; // Пример подсчёта
                SaveManager.updateRecords(player);
                System.out.println("Лич повержен! Ты спас Орду!");
                gameOver = true;
            } else if (bossBattle.isWonLich() == 1 || player.getHealth() <= 0) {
                System.out.println("Ты пал в бою... Орда скорбит.");
                gameOver = true;
            }
        }

        gameScanner.close();
    }

    // Редактор карты
    private static void runMapEditor(Map map, Player player) {
        Scanner editorScanner = new Scanner(System.in);
        boolean editing = true;

        System.out.println("Добро пожаловать в редактор карты OGRE_LANDS!");
        System.out.println("Установите элементы на карту, вводя координаты X и Y.");
        int size = map.getCurrentMapMaxX(Player.MapType.OGRE_LANDS);

        Cell[][] editorMap = new Cell[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                editorMap[x][y] = new Cell("\uD83C\uDF32"); // Лес по умолчанию
            }
        }

        String[] elements = {
                "\uD83E\uDDCC", // Тралл
                "🛒",            // Магазин
                "⚒\uFE0F",     // Кузнец
                "\uD83D\uDC38", // Лягушка
                "\uD83D\uDD25", // Костёр
                "\uD83C\uDFD5\uFE0F", // Палатка
                "\uD83D\uDFE8",  // Дорога
                "\uD80C\uDE78",
                "\uD83C\uDF0C"
        };

        String[] elementNames = {
                "Тралл",
                "Магазин",
                "Кузнец",
                "Лягушка",
                "Костёр",
                "Палатка",
                "Дорога",
                "Переход в замороженную карту",
                "Переход в карту руин"
        };

        for (int i = 0; i < elements.length; i++) {
            boolean placed = false;
            while (!placed) {
                drawEditorMap(editorMap);
                System.out.println("Установите " + elementNames[i]);
                System.out.print("Введите координату X: ");
                int x = -1;
                try {
                    x = Integer.parseInt(editorScanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат. Попробуйте снова.");
                    continue;
                }

                System.out.print("Введите координату Y: ");
                int y = -1;
                try {
                    y = Integer.parseInt(editorScanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат. Попробуйте снова.");
                    continue;
                }

                if (x >= 0 && x < size && y >= 0 && y < size) {
                    if (!editorMap[x][y].getCelltype().equals("\uD83C\uDF32")) {
                        System.out.println("Эта ячейка уже занята. Выберите другие координаты.");
                        continue;
                    }
                    editorMap[x][y].setCelltype(elements[i]);
                    System.out.println(elementNames[i] + " установлен на (" + x + ", " + y + ").");
                    placed = true;
                } else {
                    System.out.println("Координаты вне диапазона. Повторите ввод.");
                }
            }
        }

        // Сохраняем отредактированную карту в основной объект Map
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                map.getCurrentMapGrid(Player.MapType.OGRE_LANDS)[x][y].setCelltype(editorMap[x][y].getCelltype());
            }
        }

        System.out.println("Карта успешно создана!");
        player.setCurrentMapType(Player.MapType.OGRE_LANDS);
        player.setPosition(0, 0);
        map.displayCurrentMap(player);
    }

    // Метод отрисовки редактора
    private static void drawEditorMap(Cell[][] map) {
        int size = map.length;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                System.out.print(" " + map[x][y].getCelltype() + " ");
            }
            System.out.println();
        }
    }

    // Проверка событий
    private static void checkEvents(Player player, Diologies dialogs, Map map, Scanner scanner, Quests quests, BossBattle battle, Totem totem, Alchemist alchemist, WarriorTower tower) {
        Cell[][] currentGrid = map.getCurrentMapGrid(player.getCurrentMapType());
        int x = player.getX();
        int y = player.getY();

        if (x < 0 || y < 0 || x >= currentGrid.length || y >= currentGrid[0].length) return;

        String cellType = currentGrid[x][y].getCelltype();

        switch (player.getCurrentMapType()) {
            case OGRE_LANDS:
                if (cellType.equals("\uD83E\uDDCC")) { // Тралл
                    handleThrallDialog(dialogs, map, scanner, player);
                } else if (cellType.equals("⚒\uFE0F")) { // Кузнец
                    handleSmithDialog(dialogs, map, scanner, player);
                } else if (cellType.equals("🛒")) { // Магазин
                    handleShop(map, player, scanner);
                } else if(cellType.equals("\uD83D\uDDFF")) {
                    totem.interact(player, map);
                } else if(cellType.equals("\uD83E\uDDEA")) {
                    alchemist.interact(player,map);
                }
                 else if(cellType.equals("\uD83C\uDFEF")) {
                tower.interact(player,map);
                }




        break;

            case RUINS:
                if (cellType.equals("\uD83D\uDD2E")) { // Загадка
                    System.out.println("Начать квест? (1 - да, 2 - нет)");
                    String choice = scanner.nextLine();
                    if ("1".equals(choice)) {
                        boolean completed = quests.startFirstQuest(player, map);
                        if (!completed) {
                            System.out.println("Духи напали! Переходим в бой...");
                            List<Enemy> spirits = List.of(
                                    new Enemy("Дух 1", 30, 5),
                                    new Enemy("Дух 2", 30, 5),
                                    new Enemy("Дух 3", 30, 5)
                            );
                            new BattleMap(map, player, spirits, "👻").runBattle();
                            int reward = 20;
                            player.addCoins(reward);
                            System.out.println("Вы победили духов и получили " + reward + " монет! Всего монет: " + player.getCoins());
                            player.setCurrentMapType(Player.MapType.OGRE_LANDS);
                            map.displayCurrentMap(player);
                        } else {
                            int reward = 50;
                            player.addCoins(reward);
                            System.out.println("Вы успешно решили все загадки и получили " + reward + " монет! Всего монет: " + player.getCoins());
                        }
                    }
                }
                break;

            case FROZEN_MAP:
                if (cellType.equals("\uD83E\uDDB9")) { // Босс
                    battle.startBattle(player);
                    map.displayCurrentMap(player);
                } else if (cellType.equals("\uD83D\uDC3E")) { // Приспешник
                    System.out.println("Приспешники Лича преграждают путь! Битва начинается...");
                    List<Enemy> minions = List.of(
                            new Enemy("Приспешник 1", 40, 10),
                            new Enemy("Приспешник 2", 40, 10),
                            new Enemy("Приспешник 3", 40, 10)
                    );
                    new BattleMap(map, player, minions, "\uD83D\uDC80").runBattle();
                    int reward = 30;
                    player.addCoins(reward);
                    System.out.println("Вы победили приспешников и получили " + reward + " монет! Всего монет: " + player.getCoins());
                    player.setCurrentMapType(Player.MapType.FROZEN_MAP);
                    map.displayCurrentMap(player);
                }
                break;


        }

        handleMapTransition(scanner, map, player);
    }


    // Диалог с траллом
    private static void handleThrallDialog(Diologies dialogs, Map map, Scanner scanner, Player player) {
        boolean dialogActive = true;
        while (dialogActive) {
            String currentDialog = dialogs.getThrallDialog();
            System.out.println(currentDialog);
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("e")) {
                dialogActive = false;
                map.displayCurrentMap(player);
                if (dialogs.canProgressThrallDialog()) {
                    dialogs.moveToNextThrallStage();
                }
                if (dialogs.getThrallStage() == 0)
                {
                    dialogs.setThrallStage(1);
                }
            } else {
                System.out.println("Орда недовольна твоей грамотностью...");
            }
        }
    }

    // Диалог с кузнецом
    private static void handleSmithDialog(Diologies dialogs, Map map, Scanner scanner, Player player) {
        boolean dialogActive = true;
        while (dialogActive) {
            String currentDialog = dialogs.getSmithDialog(player);
            System.out.println(currentDialog);
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("e")) {
                dialogActive = false;
                map.displayCurrentMap(player);
                if (dialogs.canProgressSmithStory(player)) {
                    dialogs.moveToNextSmithStage(player);
                }
                if (dialogs.getSmithStage() == 0)
                {
                    dialogs.setSmithStage(1);
                }
            } else {
                System.out.println("Кузнец хмурится: Повтори...");
            }
        }
    }

    // Магазин
    private static void handleShop(Map map, Player player, Scanner scanner) {
        boolean inShop = true;
        while (inShop) {

            System.out.println("Торговец зельями предлагает:");
            System.out.println("1) Зелье здоровья (+50 HP) — 25 монет");
            System.out.println("2) Зелье урона (+25 DMG) — 50 монет");
            System.out.println("0) Уйти");
            System.out.print("Ваш выбор: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    if (player.getCoins() >= 25) {
                        int before = player.getCoins();
                        player.addCoins(-25);
                        player.setHealth(player.getHealth() + 50);
                        System.out.println("Вы купили зелье здоровья! Монет осталось: " + player.getCoins());

                        // Логируем покупку
                        SaveManager.logPurchase(player.getUsername(), "здоровье", "успешно", before, player.getCoins());
                    } else {
                        System.out.println("Недостаточно монет!");

                        // Логируем попытку
                        SaveManager.logPurchase(player.getUsername(), "здоровье", "недостаточно монет", player.getCoins(), player.getCoins());
                    }
                    map.displayCurrentMap(player);
                    break;

                case "2":
                    if (player.getCoins() >= 50) {
                        int before = player.getCoins();
                        player.addCoins(-50);
                        player.setDmg(player.getDmg() + 25);
                        System.out.println("Вы купили зелье урона! Монет осталось: " + player.getCoins());

                        // Логируем покупку
                        SaveManager.logPurchase(player.getUsername(), "хайп", "успешно", before, player.getCoins());
                    } else {
                        System.out.println("Недостаточно монет!");

                        // Логируем попытку
                        SaveManager.logPurchase(player.getUsername(), "хайп", "недостаточно монет", player.getCoins(), player.getCoins());
                    }
                    map.displayCurrentMap(player);
                    break;

                case "0":
                    inShop = false;
                    map.displayCurrentMap(player);
                    break;

                default:
                    System.out.println("Неверный выбор!");
                    SaveManager.logPurchase(player.getUsername(), "неизвестно", "неверный выбор", player.getCoins(), player.getCoins());
            }
        }
    }

    // Переходы между картами
    private static void handleMapTransition(Scanner scanner, Map map, Player player) {
        Cell[][] currentCell = map.getCurrentMapGrid(player.getCurrentMapType());
        String cellType = currentCell[player.getX()][player.getY()].getCelltype();

        switch (player.getCurrentMapType()) {
            case OGRE_LANDS:
                if (cellType.equals("\uD80C\uDE78")) {
                    transitionToMap(scanner, map, player, Player.MapType.RUINS, 0, 0);
                } else if (cellType.equals("\uD83C\uDF0C")) {
                    transitionToMap(scanner, map, player, Player.MapType.FROZEN_MAP, 0, 0);
                }
                break;

            case RUINS:
                if (player.getX() == 4 && player.getY() == 4) {
                    transitionToMap(scanner, map, player, Player.MapType.OGRE_LANDS, 2, 7);
                }
                break;

            case FROZEN_MAP:
                if (player.getX() == 0 && player.getY() == 5) {
                    transitionToMap(scanner, map, player, Player.MapType.OGRE_LANDS, 0, 5);
                }
                break;
        }
    }

    // Метод перехода на другую карту
    private static void transitionToMap(Scanner scanner, Map map, Player player, Player.MapType targetMap, int startX, int startY) {
        System.out.println("Перейти на карту " + targetMap.name() + "? (1 - да, 2 - нет)");
        String choice = scanner.nextLine();
        if ("1".equals(choice)) {
            player.setCurrentMapType(targetMap);
            player.setPosition(startX, startY);
            map.displayCurrentMap(player);
        }
    }
}