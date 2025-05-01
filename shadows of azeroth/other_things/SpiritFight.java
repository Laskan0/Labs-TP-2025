package other_things;

import entities.Player;
import entities.Spirit;
import maps.Map;
import java.util.Arrays;

public class SpiritFight {
    private final Player player;
    private final Map map;
    private Spirit[] spirits;
    private boolean battleActive;
    private int turnCounter;

    public SpiritFight(Map map, Player player) {
        this.map = map;
        this.player = player;
        this.spirits = new Spirit[2];
        this.battleActive = false;
    }

    /**
     * Начинает бой с духами
     */
    public void startSpiritFight(Player.MapType previousMap, int startX, int startY) {
        this.battleActive = true;
        this.turnCounter = 0;

        // Инициализация духов с проверкой границ карты
        int spirit1X = Math.max(0, Math.min(startX + 1, map.getCurrentMapMaxX(Player.MapType.FIGHT_MAP) - 1));
        int spirit2X = Math.max(0, Math.min(startX - 1, map.getCurrentMapMaxX(Player.MapType.FIGHT_MAP) - 1));

        spirits[0] = new Spirit(spirit1X, startY);
        spirits[1] = new Spirit(spirit2X, startY);

        player.setCurrentMapType(Player.MapType.FIGHT_MAP);
        player.resetMoves();

        System.out.println("Бой с духами начался! Духи появились на позициях: " +
                Arrays.toString(spirits));

        updateSpiritsOnMap();
        map.displayCurrentMap(player);
    }

    /**
     * Обрабатывает ход духов
     */
    public void handleEnemyTurn() {
        if (!battleActive) return;

        turnCounter++;
        System.out.println("\n=== Ход духов (" + turnCounter + ") ===");

        // Движение и атака духов
        for (int i = 0; i < spirits.length; i++) {
            if (spirits[i] != null && spirits[i].isAlive()) {
                Spirit spirit = spirits[i];

                // Интеллектуальное перемещение
                spirit.moveTowardsPlayer(
                        player.getX(),
                        player.getY(),
                        map.getCurrentMapGrid(Player.MapType.FIGHT_MAP)
                );

                // Проверка атаки
                if (spirit.canAttackPlayer(player.getX(), player.getY())) {
                    int damage = spirit.getDamage();
                    player.setHealth(player.getHealth() - damage);
                    System.out.printf("Дух [%d,%d] атаковал! -%d HP (Осталось: %d)%n",
                            spirit.getX(), spirit.getY(), damage, player.getHealth());
                }
            }
        }

        updateSpiritsOnMap();
        checkBattleStatus();
        map.displayCurrentMap(player);
    }

    /**
     * Проверяет попадание по духам
     */
    public void checkSpiritHit() {
        if (!battleActive) return;

        boolean spiritsRemaining = false;

        for (int i = 0; i < spirits.length; i++) {
            if (spirits[i] != null && spirits[i].isAlive()) {
                spiritsRemaining = true;
                break;
            }
        }

        if (!spiritsRemaining) {
            endBattle(true);
        }
    }

    /**
     * Обновляет позиции духов на карте
     */
    private void updateSpiritsOnMap() {
        // Очистка предыдущих позиций
        Cell[][] grid = map.getCurrentMapGrid(Player.MapType.FIGHT_MAP);
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                if (cell.getCelltype().equals("S")) {
                    cell.setCelltype("-");
                }
            }
        }

        // Установка новых позиций
        for (Spirit spirit : spirits) {
            if (spirit != null && spirit.isAlive()) {
                int x = spirit.getX();
                int y = spirit.getY();
                if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
                    grid[x][y].setCelltype("S");
                }
            }
        }
    }

    /**
     * Проверяет статус боя
     */
    private void checkBattleStatus() {
        // Проверка здоровья игрока
        if (player.getHealth() <= 0) {
            endBattle(false);
            return;
        }

        // Проверка оставшихся духов
        boolean allDead = true;
        for (Spirit spirit : spirits) {
            if (spirit != null && spirit.isAlive()) {
                allDead = false;
                break;
            }
        }

        if (allDead) {
            endBattle(true);
        }
    }

    /**
     * Завершает бой
     */
    private void endBattle(boolean playerWon) {
        this.battleActive = false;

        if (playerWon) {
            System.out.println("\nПОБЕДА! Все духи уничтожены.");
            player.setHasArtifact(true);
        } else {
            System.out.println("\nПОРАЖЕНИЕ! Вы пали в бою...");
        }

        // Возврат на основную карту
        player.setCurrentMapType(Player.MapType.OGRE_LANDS);
        player.resetMoves();
        map.displayCurrentMap(player);
    }

    /**
     * Проверяет активен ли бой
     */
    public boolean isBattleActive() {
        return battleActive;
    }

    /**
     * Получает текущих духов
     */
    public Spirit[] getSpirits() {
        return spirits;
    }
}