import maps.Map;
import entities.Player;
import other_things.Diologies;
import java.util.Scanner;
import other_things.Quests;
import other_things.BossBattle;
public class Main {
    public static void main(String[] args) {
        Map map = new Map();
        Player player = new Player(0, 0);
        map.setPlayer(player);// Начальная позиция на карте огров
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
                case "w", "s", "a", "d": // Движение
                    int dx = input.equals("w") ? -1 : input.equals("s") ? 1 : 0;
                    int dy = input.equals("a") ? -1 : input.equals("d") ? 1 : 0;

                        // На других картах нет ограничения ходов
                    if (player.playerMove(dx, dy, map)) {
                        map.displayCurrentMap(player);
                        checkEvents(player, dialogs, map, scanner, quests, bossBattle);
                    }
                    else {
                        System.out.println("Движение невозможно!");
                        map.displayCurrentMap(player);
                    }
                    break;

                case "p": // Просмотр состояния
                    player.player_condition();
                    System.out.println("ВЫПИТЬ ЗЕЛЬЕ? 1 - здоровья; 2-атаки");
                    String potions = scanner.nextLine().toLowerCase();
                    if (potions.equals("1")) {
                        player.setHealth(player.getHealth() + 50);
                        player.setHealingPotion(player.getHealingPotion() - 1);
                        map.displayCurrentMap(player);

                    }

                    else if (potions.equals("2")) {
                    player.setDmg(player.getDmg() + 25);
                    player.setBoostDmgPotion(player.getBoostDmgPotion() - 1);
                    map.displayCurrentMap(player);

                    }
                    else {
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

    private static void checkEvents(Player player, Diologies dialogs, Map map, Scanner scanner, Quests quests, BossBattle battle) {
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
                    handleFirstQuest(quests, map, scanner, player);
                }
                break;
            case FROZEN_MAP:
                if (player.getX() == 4 && player.getY() == 4) { // Босс
                    battle.startBattle(player);
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

    private static void handleFirstQuest(Quests quests, Map map, Scanner scanner, Player player) {
        System.out.println("Начать квест? (1 - да, 2 - нет)");
        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            quests.startFirstQuest(player);
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