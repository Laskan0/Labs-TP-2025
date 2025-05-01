package entities;

public class Priest extends Enemy {
    private int healAmount; // Количество здоровья, которое жрец восстанавливает

    public Priest(String name, int health, int damage, int healAmount) {
        super(name, health, damage);
        this.healAmount = healAmount;
    }

    // Метод лечения
    public void heal() {
        setHealth(getHealth() + healAmount);
    }

    @Override
    public void attack(Player player) {
        super.attack(player);
        // Жрец лечится после атаки (если жив)
        if (getHealth() > 0) {
            heal();
        }
    }
}