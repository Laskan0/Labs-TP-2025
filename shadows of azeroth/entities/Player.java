package entities;

import maps.Map;
import maps.Cell;

public class Player {
    public enum MapType {
        OGRE_LANDS,
        RUINS,
        FROZEN_MAP,
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

    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    // Метод для перемещения игрока
    public boolean playerMove(int dx, int dy, Map map) {
        int newX = x + dx;
        int newY = y + dy;

        int maxX = map.getCurrentMapMaxX(currentMap);
        int maxY = map.getCurrentMapMaxY(currentMap);

        if (newX >= 0 && newX < maxX && newY >= 0 && newY < maxY) {
            Cell[][] currentGrid = map.getCurrentMapGrid(currentMap); // Получаем текущую карту
            String cellType = currentGrid[newX][newY].getCelltype();

            if (cellType.equals("\uD83C\uDFD5\uFE0F")) { // Палатка
                System.out.println("Нельзя пройти через палатку!");
                return false;
            }

            if (cellType.equals("\uD83D\uDD25")) { // Огонь
                health -= 10;
                System.out.println("Уебок ты сгорел в адском пламени!");
                System.out.println("Текущая свага: " + health);
            }




            x = newX;
            y = newY;
            return true;
        }
        return false; // Если вышли за границы карты
    }

    // Метод для отображения состояния игрока
    public void player_condition() {
        System.out.println("ТЕКУЩАЯ СВАГА: " + health);
        System.out.println("ТЕКУЩИЙ УРОН: " + dmg);
        System.out.println("Зельки сваги: " + healingPotion);
        System.out.println("Зельки Хайпа: " + boostDmgPotion);
    }

    public boolean useMove() {
        if(movesLeft > 0) {
            movesLeft--;
            return true;

        }
        return false;
    }




    // Геттеры и сеттеры
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public MapType getCurrentMapType() {
        return currentMap;
    }

    public void setCurrentMapType(MapType type) {
        this.currentMap = type;
    }

    public boolean isHasArtifact() {
        return hasArtifact;
    }

    public void setHasArtifact(boolean hasArtifact) {
        this.hasArtifact = hasArtifact;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public int getHealingPotion() {
        return healingPotion;
    }

    public void setHealingPotion(int healingPotion) {
        this.healingPotion = healingPotion;
    }

    public int getBoostDmgPotion() {
        return boostDmgPotion;
    }

    public void setBoostDmgPotion(int boostDmgPotion) {
        this.boostDmgPotion = boostDmgPotion;
    }

    public int getMovesLeft() {
        return movesLeft;
    }


    public void resetMoves() {
        this.movesLeft =3;
    }


}