package entities;


public class Enemy {
    private String name;
    private int health;
    private int damage;

    public Enemy(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(health, 0); // Здоровье не может быть меньше 0
    }

    public int getDamage() {
        return damage;
    }

    // Метод атаки
    public void attack(Player player) {
        player.setHealth(player.getHealth() - damage);
    }

    // Метод получения урона
    public void takeDamage(int damage) {
        health -= damage;
    }
}
