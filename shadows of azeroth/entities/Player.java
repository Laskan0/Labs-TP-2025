package entities;

import maps.Map;
import maps.Cell;

public class Player {




    public enum MapType {
        OGRE_LANDS,
        RUINS,
        FROZEN_MAP,
        FIGHT_MAP
    }

    private int x;
    private int y;
    private MapType currentMap = MapType.OGRE_LANDS;
    private boolean hasArtifact = false;
    private int health = 100;
    private int dmg = 50;
    private int healingPotion = 3;
    private int boostDmgPotion = 2;
    private int movesLeft = 3;
    private int coins = 0;
    private String username = ""; // Имя игрока
    private int score = 0; // Общий счёт
    public void setCoins(int i) {
        this.coins = i;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setPosition(int x, int y) { this.x = x; this.y = y; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public int getHealth() { return health; }
    public int getDmg() { return dmg; }
    public int getHealingPotion() { return healingPotion; }
    public int getBoostDmgPotion() { return boostDmgPotion; }
    public boolean isHasArtifact() { return hasArtifact; }
    public void setHasArtifact(boolean hasArtifact) { this.hasArtifact = hasArtifact; }
    public int getCoins() { return coins; }
    public void addCoins(int amount) { this.coins += amount; }


    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    // Метод для перемещения игрока
// В Player.java
    public boolean playerMove(int dx, int dy, Map map) {
        int newX = x + dx;
        int newY = y + dy;

        int maxX = map.getCurrentMapMaxX(currentMap);
        int maxY = map.getCurrentMapMaxY(currentMap);

        if (newX >= 0 && newX < maxX && newY >= 0 && newY < maxY) {
            Cell[][] currentGrid = map.getCurrentMapGrid(currentMap);
            String cellType = currentGrid[newX][newY].getCelltype();

            if (!cellType.equals("\uD83C\uDFD5\uFE0F")) { // Проверка на палатку
                x = newX;
                y = newY;
                return true;
            }
        }
        return false;
    }

    // Отображение состояния игрока
    public void player_condition() {
        System.out.println("ТЕКУЩАЯ СВАГА: " + health);
        System.out.println("ТЕКУЩИЙ УРОН: " + dmg);
        System.out.println("Зельки здоровья: " + healingPotion);
        System.out.println("Зельки атаки: " + boostDmgPotion);
        System.out.println("Монеты: " + coins);
        System.out.println("Артефакт: " + (hasArtifact ? "есть" : "нет"));
    }

    // Использование хода
    public boolean useMove() {
        if (movesLeft > 0) {
            movesLeft--;
            return true;
        }
        return false;
    }

    // Добавить монеты


    // Получить счёт
    public int getScore() {
        return calculateScore();
    }

    // Рассчёт счёта
    public int calculateScore() {
        return coins + (hasArtifact ? 100 : 0); // Пример подсчёта
    }

    // Установить счёт
    public void setScore(int score) {
        this.score = score;
    }

    // Получить имя


    // Установить имя


    // Геттеры и сеттеры


    public MapType getCurrentMapType() {
        return currentMap;
    }

    public void setCurrentMapType(MapType type) {
        this.currentMap = type;
    }





    public void setHealth(int health) {
        this.health = health;
    }



    public void setDmg(int dmg) {
        this.dmg = dmg;
    }



    public void setHealingPotion(int healingPotion) {
        this.healingPotion = healingPotion;
    }



    public void setBoostDmgPotion(int boostDmgPotion) {
        this.boostDmgPotion = boostDmgPotion;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public void resetMoves() {
        this.movesLeft = 3;
    }


}