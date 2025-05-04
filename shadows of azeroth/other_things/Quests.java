package other_things;

import entities.Player;
import maps.Map;
import java.util.Scanner;

public class Quests {
    private final String firstQuestRiddle = "«Без плоти, но повторяю шаг,\n" +
            "Без голоса, но вторю смех.\n" +
            "В замках пустых я живу всегда,\n" +
            "Кто я? Тень? Нет — тень от света зла.";
    private final String firstQuestAnswer = "эхо";

    private final String secondQuestRiddle = "Оно может повторить любое движение, но никогда не придумает ничего нового.";
    private final String secondQuestAnswer = "зеркало";

    private final String thirdQuestRiddle = "Какая посуда оставит голодным,\n" +
            "Ведь вовсе она для еды не пригодна";
    private final String thirdQuestAnswer = "пустая";

    private final String introduction = "Перед вами древняя гробница Титанов, пробитая в ледяной скале. Её руны, мерцающие синим огнем, словно глаза мертвецов, хранят три испытания духа. Лишь разгадав их, вы сможете прикоснуться к артефакту...";

    private int questState = 0;
    private final Scanner scanner;

    public Quests(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean startFirstQuest(Player player, Map map) {
        System.out.println(introduction);

        while (questState < 3) {
            switch (questState) {
                case 0:
                    if (!solveRiddle(firstQuestRiddle, firstQuestAnswer)) {
                        return false;
                    }
                    questState++;
                    break;
                case 1:
                    if (!solveRiddle(secondQuestRiddle, secondQuestAnswer)) {
                        return false;
                    }
                    questState++;
                    break;
                case 2:
                    if (!solveRiddle(thirdQuestRiddle, thirdQuestAnswer)) {
                        return false;
                    }
                    questState++;
                    break;
            }
        }

        System.out.println("Поздравляю! Вы разгадали все загадки и получили артефакт!");
        player.setHasArtifact(true);
        return true;
    }

    private boolean solveRiddle(String riddle, String correctAnswer) {
        int attempts = 3;

        while (attempts > 0) {
            System.out.println(riddle);
            System.out.print("Ваш ответ: ");
            String answer = scanner.nextLine().trim().toLowerCase();

            if (answer.equals(correctAnswer)) {
                System.out.println("Духи предков одобряют твой выбор. Твоя честь укреплена, как сталь Дуротара.");
                return true;
            } else {
                attempts--;
                if (attempts > 0) {
                    System.out.println("Неверно. Осталось попыток: " + attempts);
                } else {
                    System.out.println("Ты исчерпал все попытки. Духи гневаются — готовься к бою!");
                    return false;
                }
            }
        }
        return false;
    }
}
