package entities;

import maps.Cell;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Spirit {
    private int x;
    private int y;
    private int health = 50;
    private final int damage = 15;
    private static final Random random = new Random();
    private boolean isAggressive = true;

    public Spirit(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    /**
     * Умное перемещение духа с учетом препятствий
     */
    public void moveTowardsPlayer(int playerX, int playerY, Cell[][] grid) {
        if (!isAggressive) return;

        List<int[]> possibleMoves = getValidMoves(grid);
        if (possibleMoves.isEmpty()) return;

        // Выбираем оптимальное направление 80% времени
        if (random.nextDouble() < 0.8) {
            moveOptimalDirection(playerX, playerY, possibleMoves);
        } else {
            moveRandomDirection(possibleMoves);
        }
    }

    private List<int[]> getValidMoves(Cell[][] grid) {
        List<int[]> moves = new ArrayList<>();

        // Проверяем все 4 направления
        if (x > 0 && grid[x-1][y].isEmpty()) moves.add(new int[]{-1, 0}); // Влево
        if (x < grid.length-1 && grid[x+1][y].isEmpty()) moves.add(new int[]{1, 0}); // Вправо
        if (y > 0 && grid[x][y-1].isEmpty()) moves.add(new int[]{0, -1}); // Вверх
        if (y < grid[0].length-1 && grid[x][y+1].isEmpty()) moves.add(new int[]{0, 1}); // Вниз

        return moves;
    }

    private void moveOptimalDirection(int playerX, int playerY, List<int[]> possibleMoves) {
        int[] bestMove = null;
        int minDistance = Integer.MAX_VALUE;

        for (int[] move : possibleMoves) {
            int newX = x + move[0];
            int newY = y + move[1];
            int distance = calculateDistance(newX, newY, playerX, playerY);

            if (distance < minDistance) {
                minDistance = distance;
                bestMove = move;
            }
        }

        if (bestMove != null) {
            x += bestMove[0];
            y += bestMove[1];
        }
    }

    private void moveRandomDirection(List<int[]> possibleMoves) {
        int[] randomMove = possibleMoves.get(random.nextInt(possibleMoves.size()));
        x += randomMove[0];
        y += randomMove[1];
    }

    private int calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2); // Манхэттенское расстояние
    }

    /**
     * Проверка возможности атаковать игрока
     */
    public boolean canAttackPlayer(int playerX, int playerY) {
        int dx = Math.abs(x - playerX);
        int dy = Math.abs(y - playerY);
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }

    /**
     * Получение урона
     */
    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            System.out.println("Дух повержен!");
        }
    }

    // Геттеры и сеттеры
    public int getX() { return x; }
    public int getY() { return y; }
    public int getHealth() { return health; }
    public int getDamage() { return damage; }
    public boolean isAlive() { return health > 0; }

    public boolean isAtPosition(int targetX, int targetY) {
        return x == targetX && y == targetY;
    }

    @Override
    public String toString() {
        return String.format("Spirit[%d,%d] HP: %d", x, y, health);
    }
}