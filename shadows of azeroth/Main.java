import entities.Spirit;
import maps.Map;
import entities.Player;
import other_things.Diologies;
import java.util.Scanner;
import other_things.Quests;
import other_things.BossBattle;
import other_things.SpiritFight;
import entities.Spirit;

public class Main {
    public static void main(String[] args) {
        Map map = new Map();
        Player player = new Player(0, 0);
        map.setPlayer(player);// Начальная позиция на карте огров
        Diologies dialogs = new Diologies();
        Scanner scanner = new Scanner(System.in);
        Quests quests = new Quests(scanner);
        BossBattle bossBattle = new BossBattle();
        SpiritFight fight = new SpiritFight(map, player);
        boolean gameOver = false;

        dialogs.displayProlog();
        map.displayCurrentMap(player);

        while (!gameOver) {
            System.out.println("Управляй: w/a/s/d (ход), 1 (атака мечом), 2 (сплеш), p (состояние)");
            String input = scanner.nextLine().toLowerCase();


            if (player.getCurrentMapType() == Player.MapType.FIGHT_MAP) {
                // На боевой карте есть ограничение ходов
                if (player.getMovesLeft() <= 0) {
                    System.out.println("Сейчас ход призраков!");
                    fight.handleEnemyTurn();
                    player.resetMoves(); // Сброс ходов после хода призраков
                    continue;
                }
            }


            switch (input) {
                case "w", "s", "a", "d": // Движение
                    int dx = input.equals("w") ? -1 : input.equals("s") ? 1 : 0;
                    int dy = input.equals("a") ? -1 : input.equals("d") ? 1 : 0;

                    if (player.getCurrentMapType() == Player.MapType.FIGHT_MAP) {
                        // На боевой карте используем лимит ходов
                        if (player.useMove()) { // Используем ход
                            if (player.playerMove(dx, dy, map)) {
                                map.displayCurrentMap(player);
                                checkEvents(player, dialogs, map, scanner, quests, bossBattle, fight);
                            } else {
                                System.out.println("Движение невозможно!");
                                map.displayCurrentMap(player);
                            }
                        } else {
                            System.out.println("У вас нет ходов для движения!");
                        }
                    } else {
                        // На других картах нет ограничения ходов
                        if (player.playerMove(dx, dy, map)) {
                            map.displayCurrentMap(player);
                            checkEvents(player, dialogs, map, scanner, quests, bossBattle, fight);
                        } else {
                            System.out.println("Движение невозможно!");
                            map.displayCurrentMap(player);
                        }
                    }
                    break;

                case "1": // Атака мечом
                    if (player.getCurrentMapType() == Player.MapType.FIGHT_MAP) {
                        if (player.useMove()) { // Используем ход
                            System.out.println("Выберите направление (w/a/s/d):");
                            String direction = scanner.nextLine().toLowerCase();
                            int attackDx = direction.equals("w") ? -1 : direction.equals("s") ? 1 : 0;
                            int attackDy = direction.equals("a") ? -1 : direction.equals("d") ? 1 : 0;
                            player.swordAttack(attackDx, attackDy, map,fight);
                        } else {
                            System.out.println("У вас нет ходов для атаки!");
                        }
                    } else {
                        System.out.println("Атаковать можно только на боевой карте!");
                    }
                    break;

                case "2": // Сплеш-атака
                    if (player.getCurrentMapType() == Player.MapType.FIGHT_MAP) {
                        if (player.useMove()) { // Используем ход
                            player.splashAttack(map,fight);
                        } else {
                            System.out.println("У вас нет ходов для сплеш-атаки!");
                        }
                    } else {
                        System.out.println("Сплеш-атака доступна только на боевой карте!");
                    }
                    break;

                case "p": // Просмотр состояния
                    player.player_condition();
                    System.out.println("ВЫПИТЬ ЗЕЛЬЕ? 1 - здоровья; 2-атаки");
                    String potions = scanner.nextLine().toLowerCase();
                    if (potions.equals("1")) {
                        if (player.useMove()) { // Используем ход
                            player.setHealth(player.getHealth() + 50);
                            player.setHealingPotion(player.getHealingPotion() - 1);
                            map.displayCurrentMap(player);
                        } else {
                            System.out.println("У вас нет ходов для использования зелий!");
                        }
                    } else if (potions.equals("2")) {
                        if (player.useMove()) { // Используем ход
                            player.setDmg(player.getDmg() + 25);
                            player.setBoostDmgPotion(player.getBoostDmgPotion() - 1);
                            map.displayCurrentMap(player);
                        } else {
                            System.out.println("У вас нет ходов для использования зелий!");
                        }
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

            // Обработка хода противников на боевой карте
            if (player.getCurrentMapType() == Player.MapType.FIGHT_MAP) {
                fight.handleEnemyTurn();

            }
        }
        scanner.close();
    }

    private static void checkEvents(Player player, Diologies dialogs, Map map, Scanner scanner, Quests quests, BossBattle battle, SpiritFight fight) {
        switch (player.getCurrentMapType()) {
            case OGRE_LANDS:
                if (player.getX() == 3 && player.getY() == 2) { // Тралл
                    handleThrallDialog(dialogs, map, scanner, player);
                } else if (player.getX() == 1 && player.getY() == 4) { // Кузнец
                    handleSmithDialog(dialogs, map, scanner, player);
                }
                break;
            case RUINS:
                if (player.getX() == 2 && player.getY() == 2) { // Гробница
                    handleFirstQuest(quests, map, scanner, player, fight);
                }
                break;
            case FROZEN_MAP:
                if (player.getX() == 4 && player.getY() == 4) { // Босс
                    battle.startBattle(player);
                    map.displayCurrentMap(player);
                }
                break;
            case FIGHT_MAP:
                // Логика боя уже обрабатывается в основном цикле
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

                if(dialogs.getThrallStage() == 0) {
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

    private static void handleFirstQuest(Quests quests, Map map, Scanner scanner, Player player, SpiritFight fight) {
        System.out.println("Начать квест? (1 - да, 2 - нет)");
        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            quests.startFirstQuest(player, fight);

            // Если игрок попал на боевую карту, запускаем бой
            if (player.getCurrentMapType() == Player.MapType.FIGHT_MAP) {
                fight.startSpiritFight(player.getCurrentMapType(), player.getX(), player.getY());
            }
        }
    }

    private static void handleMapTransition(Scanner scanner, Map map, Player player) {
        switch (player.getCurrentMapType()) {
            case OGRE_LANDS:
                if (player.getX() == 2 && player.getY() == 7) { // Переход в руины
                    transitionToMap(scanner, map, player, Player.MapType.RUINS, 0, 0);
                } else if (player.getX() == 0 && player.getY() == 5) { // Переход в ледяные пустоши
                    transitionToMap(scanner, map, player, Player.MapType.FROZEN_MAP, 0, 0);
                }
                break;
            case RUINS:
                if (player.getX() == 4 && player.getY() == 4) { // Возврат в долину огров
                    transitionToMap(scanner, map, player, Player.MapType.OGRE_LANDS, 2, 7);
                }
                break;
            case FROZEN_MAP:
                if (player.getX() == 7 && player.getY() == 7) { // Возврат в долину огров
                    transitionToMap(scanner, map, player, Player.MapType.OGRE_LANDS, 0, 5);
                }
                break;
            case FIGHT_MAP:
                // Нет переходов с боевой карты
                break;
        }
    }

    private static void transitionToMap(Scanner scanner, Map map, Player player, Player.MapType targetMap, int startX, int startY) {
        System.out.println("Перейти? (1 - да, 2 - нет)");
        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            player.setCurrentMapType(targetMap);
            player.setPosition(startX, startY);
            map.displayCurrentMap(player);
        }
    }
}