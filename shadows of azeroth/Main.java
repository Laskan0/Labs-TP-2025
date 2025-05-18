import maps.Map;
import entities.Player;
import other_things.Diologies;
import java.util.Scanner;
import other_things.Quests;
import other_things.BossBattle;
import java.util.List;
import entities.Enemy;
import maps.BattleMap;
import maps.Cell;

public class Main {

    public static void main(String[] args) {
        Scanner choiceScanner = new Scanner(System.in);
        System.out.println("Выберите тип карты:");
        System.out.println("1 - Создать новую карту ");
        System.out.println("2 - Загрузить стандартную карту");
        System.out.print("Ваш выбор: ");

        String choice = choiceScanner.nextLine().trim();


        Map map = null;
        Player player = null;

        if ("1".equals(choice)) {
            map = new Map();
            player = new Player(0, 0);
            map.setPlayer(player);
            runMapEditor(map, player);
        } else if ("2".equals(choice)) {
            map = new Map();
            player = new Player(0, 0);
            map.setPlayer(player);
            System.out.println("Загружена стандартная карта.");
        } else {
            System.out.println("Неверный выбор. Завершение работы.");
            System.exit(0);
        }

        Diologies dialogs = new Diologies();
        Scanner scanner = new Scanner(System.in);
        Quests quests = new Quests(scanner);
        BossBattle bossBattle = new BossBattle();
        boolean gameOver = false;

        dialogs.displayProlog();
        map.displayCurrentMap(player);

        while (!gameOver) {
            System.out.println("Управляй: w/a/s/d (ход), 1 (атака мечом), 2 (сплеш), p (состояние)");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "w", "s", "a", "d":
                    int dx = input.equals("w") ? -1 : input.equals("s") ? 1 : 0;
                    int dy = input.equals("a") ? -1 : input.equals("d") ? 1 : 0;
                    if (player.playerMove(dx, dy, map)) {
                        map.displayCurrentMap(player);
                        checkEvents(player, dialogs, map, scanner, quests, bossBattle);
                    } else {
                        System.out.println("Движение невозможно!");
                        map.displayCurrentMap(player);
                    }
                    break;

                case "p":
                    player.player_condition();
                    System.out.println("ВЫПИТЬ ЗЕЛЬЕ? 1 - здоровья; 2-атаки");
                    String potions = scanner.nextLine().toLowerCase();
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

            // Проверка завершения игры
            if (bossBattle.isWonLich() == 0) {
                System.out.println("Лич повержен! Ты спас Орду!");
                gameOver = true;
            } else if (bossBattle.isWonLich() == 1 || player.getHealth() <= 0) {
                System.out.println("Ты пал в бою... Орда скорбит.");
                gameOver = true;
            }
        }

        scanner.close();
    }

    private static void runMapEditor(Map map, Player player) {
        Scanner scanner = new Scanner(System.in);
        int size = map.getCurrentMapMaxY(Player.MapType.OGRE_LANDS);
        Cell[][] editorMap = new Cell[size][size];

        // Инициализируем карту лесом
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                editorMap[x][y] = new Cell("\uD83C\uDF32"); // Лес
            }
        }

        System.out.println("Почувствуй себя богом, властным над ландшавтным дизайном!!!!");
        System.out.println("Поставь всех приколов на карту. Смотри, чтобы они не коллизились, иначе другие боги тебя покарают");
        System.out.println("Карта размером " + size + "x" + size + ".");

        // Список элементов для установки
        String[] elements = {
                "\uD83E\uDDCC", // Тралл
                "⚒\uFE0F",     // Кузнец
                "🛒",            // Магазин
                "\uD80C\uDE78", // Переход в RUINS
                "\uD83C\uDF0C", // Переход в FROZEN_MAP
                "\uD83D\uDD25", // Костер
                "\uD83C\uDFD5\uFE0F", // Палатка
                "\uD83D\uDC38"   // Лягушка
        };

        String[] elementNames = {
                "Тралл",
                "Кузнец",
                "Магазин",
                "Переход в Руины",
                "Переход в Ледяную зону",
                "Костер",
                "Палатка",
                "Лягушка"
        };

        // Установка элементов по очереди
        for (int i = 0; i < elements.length; i++) {
            boolean placed = false;

            while (!placed) {
                System.out.println("\nТекущая карта:");
                drawEditorMap(editorMap);

                System.out.println("Установите " + elementNames[i]);
                System.out.print("Введите координату X: ");
                int x = -1;
                try {
                    x = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат. Попробуйте снова.");
                    continue;
                }

                System.out.print("Введите координату Y: ");
                int y = -1;
                try {
                    y = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат. Попробуйте снова.");
                    continue;
                }

                if (x >= 0 && x < size && y >= 0 && y < size) {
                    // Проверка: занята ли ячейка
                    if (!editorMap[x][y].getCelltype().equals("\uD83C\uDF32")) { // Лес
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

        // Что понасоздавали теперь ставим в дефолтный map
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                map.getCurrentMapGrid(Player.MapType.OGRE_LANDS)[x][y].setCelltype(editorMap[x][y].getCelltype());
            }
        }

        System.out.println("\nКарта успешно создана!");
        player.setCurrentMapType(Player.MapType.OGRE_LANDS);
        player.setPosition(0, 0); // Начальная позиция
        map.displayCurrentMap(player);
    }

    private static void drawEditorMap(Cell[][] map) {
        int size = map.length;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                System.out.print(" " + map[x][y].getCelltype() + " ");
            }
            System.out.println();
        }
    }

    private static void checkEvents(Player player, Diologies dialogs, Map map, Scanner scanner, Quests quests, BossBattle battle) {
        Cell[][] currentGrid = map.getCurrentMapGrid(player.getCurrentMapType());
        int x = player.getX();
        int y = player.getY();

        // Защита от выхода за границы
        if (x < 0 || y < 0 || x >= currentGrid.length || y >= currentGrid[0].length) {
            return;
        }

        String cellType = currentGrid[x][y].getCelltype();

        switch (player.getCurrentMapType()) {
            case OGRE_LANDS:
                if (cellType.equals("\uD83E\uDDCC")) { // Тралл 🧙‍♂️
                    handleThrallDialog(dialogs, map, scanner, player);
                } else if (cellType.equals("⚒\uFE0F")) { // Кузнец ⚒️
                    handleSmithDialog(dialogs, map, scanner, player);
                } else if (cellType.equals("🛒")) { // Магазин 🛒
                    handleShop(map, player, scanner);
                }
                break;

            case RUINS:
                if (cellType.equals("\uD83D\uDD2E")) { // Загадка 💡
                    handleFirstQuest(quests, map, scanner, player);
                }
                break;

            case FROZEN_MAP:
                if (cellType.equals("\uD83E\uDDB9")) { // Босс 🧟‍♂️
                    battle.startBattle(player);
                    map.displayCurrentMap(player);
                } else if (cellType.equals("\uD83D\uDC3E")) { // Приспешники 👹
                    System.out.println("Приспешники Лича преграждают путь! Битва начинается...");
                    List<Enemy> minions = List.of(
                            new Enemy("Приспешник 1", 40, 10),
                            new Enemy("Приспешник 2", 40, 10),
                            new Enemy("Приспешник 3", 40, 10)
                    );
                    new BattleMap(map, player, minions, "\uD83D\uDC80").runBattle();
                    int rewardMinions = 30;
                    player.addCoins(rewardMinions);
                    System.out.println("Вы победили приспешников и получили " + rewardMinions + " монет! Всего монет: " + player.getCoins());
                    player.setCurrentMapType(Player.MapType.FROZEN_MAP);
                    player.setPosition(4,4);
                    map.displayCurrentMap(player);
                }
                break;
        }

        handleMapTransition(scanner, map, player);
    }

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
                if (dialogs.getThrallStage() == 0) {
                    dialogs.setThrallStage(1);
                }
            } else {
                System.out.println("Орда недовольна твоей грамотностью...");
            }
        }
    }

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
                if (dialogs.getSmithStage() == 0) {
                    dialogs.setSmithStage(1);
                }
            } else {
                System.out.println("Кузнец хмурится: Повтори...");
            }
        }
    }

    private static void handleFirstQuest(Quests quests, Map map, Scanner scanner, Player player) {
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
                int rewardSpirits = 20;
                player.addCoins(rewardSpirits);
                System.out.println("Вы победили духов и получили " + rewardSpirits + " монет! Всего монет: " + player.getCoins());
                player.setCurrentMapType(Player.MapType.OGRE_LANDS);
                map.setPlayer(player);
                map.displayCurrentMap(player);
            } else {
                int rewardRiddles = 50;
                player.addCoins(rewardRiddles);
                System.out.println("Вы успешно решили все загадки и получили " + rewardRiddles + " монет! Всего монет: " + player.getCoins());
            }
        }
    }

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
                if (cellType.equals("\uD80C\uDE78")) {
                    transitionToMap(scanner, map, player, Player.MapType.OGRE_LANDS, 2, 7);
                }
                break;

            case FROZEN_MAP:
                if (cellType.equals("\uD83C\uDF0C")) {
                    transitionToMap(scanner, map, player, Player.MapType.OGRE_LANDS, 0, 5);
                }
                break;
        }
    }

    private static void handleShop(Map map, Player player, Scanner scanner) {
        boolean inShop = true;
        while (inShop) {
            map.displayCurrentMap(player);
            System.out.println("Торговец зельями предлагает:");
            System.out.println("1) Зелье сваги (+50 HP) — 25 монет");
            System.out.println("2) Зелье хайпа (+25 урона) — 50 монет");
            System.out.println("0) Уйти");
            System.out.print("Ваш выбор: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    if (player.getCoins() >= 25) {
                        player.addCoins(-25);
                        player.setHealth(player.getHealth() + 50);
                        System.out.println("Вы купили зелье сваги! Монет осталось: " + player.getCoins());
                    } else {
                        System.out.println("Недостаточно монет!");
                    }
                    break;

                case "2":
                    if (player.getCoins() >= 50) {
                        player.addCoins(-50);
                        player.setDmg(player.getDmg() + 25);
                        System.out.println("Вы купили зелье хайпа! Монет осталось: " + player.getCoins());
                    } else {
                        System.out.println("Недостаточно монет!");
                    }
                    break;

                case "0":
                    inShop = false;
                    map.displayCurrentMap(player);
                    break;

                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }

    private static void transitionToMap(Scanner scanner, Map map, Player player, Player.MapType targetMap, int startX, int startY) {
        System.out.println("Перейти? (1 - да, 2 - нет)");
        String choice = scanner.nextLine();
        if ("1".equals(choice)) {
            player.setCurrentMapType(targetMap);
            player.setPosition(startX, startY);
            map.displayCurrentMap(player);
        }
    }
}