package other_things;

import entities.Player;

import java.util.Scanner;

public class Quests {
    private boolean readyToFight = false;

    private final String firstQuestRiddle = "«Без плоти, но повторяю шаг,\n" +
            "Без голоса, но вторю смех.\n" +
            "В замках пустых я живу всегда,\n" +
            "Кто я? Тень? Нет — тень от света зла.";
    private String firstQuestAnswer = "эхо";

    private String secondQuestRiddle = "Оно может повторить любое движение, но никогда не придумает ничего нового.";
    private String secondQuestAnswer = "зеркало";

    private String thirdQuestRiddle = "Какая посуда оставит голодным,\n" +
            "Ведь вовсе она для еды не пригодна";
    private String thirdQuestAnswer = "пустая";

    private String introduction = "Перед вами древняя гробница Титанов, пробитая в ледяной скале. Её руны, мерцающие синим огнем, словно глаза мертвецов, хранят три испытания духа. Лишь разгадав их, вы сможете прикоснуться к артефакту...";

    private boolean hasArtifact = false;

    private int questState = 0;

    private final Scanner scanner;

    public Quests(Scanner scanner) {
        this.scanner = scanner;
    }

    public void startFirstQuest(Player player) {
        System.out.println(introduction);

        while (questState < 3) {
            switch (questState) {
                case 0:
                    if (!solveRiddle(firstQuestRiddle, firstQuestAnswer, player)) {
                        return; // Выходим из метода, если игрок дал неправильный ответ
                    }
                    questState++;
                    break;
                case 1:
                    if (!solveRiddle(secondQuestRiddle, secondQuestAnswer, player)) {
                        return; // Выходим из метода, если игрок дал неправильный ответ
                    }
                    questState++;
                    break;
                case 2:
                    if (!solveRiddle(thirdQuestRiddle, thirdQuestAnswer, player)) {
                        return; // Выходим из метода, если игрок дал неправильный ответ
                    }
                    questState++;
                    break;
            }
        }

        if (questState == 3) {
            System.out.println("Поздравляю! Вы разгадали все загадки и получили артефакт!");
            player.setHasArtifact(true);
        }
    }

    private boolean solveRiddle(String riddle, String correctAnswer, Player player) {
        while (true) {
            System.out.println(riddle);
            System.out.print("Ваш ответ: ");
            String answer = scanner.nextLine().trim().toLowerCase();

            if (answer.equals(correctAnswer)) {
                System.out.println("Духи предков одобряют твой выбор. Твоя честь укреплена, как сталь Дуротара");
                return true; // Правильный ответ, продолжаем квест
            } else {
                System.out.println("Твои намерения скрыты тенью. Попробуй снова, если желаешь заслужить доверие предков");

            }
        }
    }



}