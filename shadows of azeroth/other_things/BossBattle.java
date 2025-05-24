package other_things;

import entities.Player;
import java.util.Scanner;

public class BossBattle {
    private int bossHealth = 300; // Здоровье босса
    private boolean isBattleActive = true; // Флаг активности боя
    private boolean hasUsedStun = false; // Был ли использован стан
    private boolean hasUsedExhaustion = false; // Было ли применено истощение
    private boolean isExhausted = false; // Активно ли истощение
    private int exhaustionTurns = 0; // Количество ходов истощения
    private Scanner scanner;

    public BossBattle() {
        this.scanner = new Scanner(System.in);
    }

    public void applyTo(BossBattle boss) {
        // Восстанови состояние босса
    }

    public void startBattle(Player player) {
        System.out.println("Лич: 'Твои усилия бесполезны...'");
        while (isBattleActive) {
            if (isExhausted) applyExhaustion(); // Применяем эффект истощения

            displayMenu();
            String choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "1": playerAttack(player); break;
                case "2": playerDefend(); break;
                case "3": useItem(player); break;
                default:
                    System.out.println("Лич: 'Ты колеблешься? Это твоя ошибка.'");
            }

            if (bossHealth <= 0) {
                System.out.println("Лич повержен!");
                isBattleActive = false;
                player.setHasArtifact(true); // Игрок получает артефакт
                return;
            }

            if (player.getHealth() <= 0) {
                System.out.println("Ты пал в бою...");
                isBattleActive = false;
                return;
            }

            bossTurn(player); // Ход босса
        }
    }

    private void playerAttack(Player player) {
        int damage = (int) (player.getDmg() * (Math.random() * 1.5 + 0.5));
        if (isExhausted) damage /= 2; // Снижение урона при истощении
        bossHealth -= damage;
        if(player.isHasArtifact() == false){
            damage = 0;
        }
        System.out.println("Нанесено " + damage + " урона. У Лича осталось " + bossHealth + " HP.");
    }

    private void playerDefend() {
        System.out.println("Ты готовишься к защите...");
    }

    private void useItem(Player player) {
        System.out.println("Зелья лечения: " + player.getHealingPotion() +
                "\nЗелья усиления: " + player.getBoostDmgPotion());
        System.out.println("Выберите предмет (1 - лечение, 2 - усиление):");
        String choice = scanner.nextLine();
        if (choice.equals("1") && player.getHealingPotion() > 0) {
            player.setHealth(player.getHealth() + 35);
            player.setHealingPotion(player.getHealingPotion() - 1);
            System.out.println("Ты восстановил здоровье (+35 HP). Осталось зелий: " + player.getHealingPotion());
        } else if (choice.equals("2") && player.getBoostDmgPotion() > 0) {
            player.setDmg(player.getDmg() + 20);
            player.setBoostDmgPotion(player.getBoostDmgPotion() - 1);
            System.out.println("Ты усилил свои атаки (+20 DMG). Осталось зелий: " + player.getBoostDmgPotion());
        } else {
            System.out.println("У тебя нет таких предметов.");
        }
    }

    private void bossTurn(Player player) {
        if (!hasUsedStun && Math.random() < 0.33) {
            useStun(player);
        } else if (!hasUsedExhaustion && Math.random() < 0.33) {
            useExhaustion(player);
        } else {
            regularAttack(player);
        }
    }

    private void regularAttack(Player player) {
        int damage = (int) (Math.random() * 20 + 15);
        System.out.println("Лич атакует! Уклоняйся (лево/право):");
        String direction = scanner.nextLine().toLowerCase();
        String attackDirection = Math.random() > 0.5 ? "лево" : "право";

        if (direction.equals(attackDirection)) {
            System.out.println("Уклонение успешно!");
        } else {
            player.setHealth(player.getHealth() - damage);
            System.out.println("Получено " + damage + " урона. Осталось HP: " + player.getHealth());
        }
    }

    private void useStun(Player player) {
        System.out.println("Лич использует СТАН! Ты пропускаешь ход.");
        hasUsedStun = true;
        player.setHealth(player.getHealth() - 15);
        System.out.println("Получено 15 урона. Осталось HP: " + player.getHealth());
    }

    private void useExhaustion(Player player) {
        System.out.println("Лич накладывает ИСТОЩЕНИЕ! Твой урон снижен на 2 хода.");
        hasUsedExhaustion = true;
        isExhausted = true;
        exhaustionTurns = 2;
    }

    private void applyExhaustion() {
        if (exhaustionTurns > 0) {
            exhaustionTurns--;
            System.out.println("Эффект истощения: осталось " + exhaustionTurns + " ходов.");
        } else {
            isExhausted = false;
            System.out.println("Эффект истощения исчез.");
        }
    }

    private void displayMenu() {
        System.out.println("=== Действия ===");
        System.out.println("1. Атака\n2. Защита\n3. Использовать предмет");
    }

    public int isWonLich() {
        if (!isBattleActive && bossHealth <= 0) return 0; // Победа
        if (!isBattleActive && bossHealth > 0) return 1; // Поражение
        return 2; // Бой продолжается
    }


    // Геттеры для состояния босса
    public boolean isStunUsed() {
        return hasUsedStun;
    }

    public boolean isExhaustionUsed() {
        return hasUsedExhaustion;
    }

    public int getBossHealth() {
        return bossHealth;
    }

    public void setBossHealth(int bossHealth) {
        this.bossHealth = bossHealth;
    }

    public void setStunUsed(boolean used) {
        this.hasUsedStun = used;
    }

    public void setExhaustionUsed(boolean used) {
        this.hasUsedExhaustion = used;
    }

    public void setBattleActive(boolean active){
        this.isBattleActive = active;
    }
}
