package maps;

import entities.Player;
import entities.Enemy;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BattleMap {
    private final Map map;
    private final Player player;
    private final List<Enemy> enemies;
    private final int size;
    private final int[][] enemyPos;
    private final String enemySymbol;
    private final Random rnd = new Random();

    public BattleMap(Map map, Player player, List<Enemy> enemies, String enemySymbol) {
        this.map         = map;
        this.player      = player;
        this.enemies     = enemies;
        this.enemySymbol = enemySymbol;
        this.size        = map.getCurrentMapMaxX(Player.MapType.FIGHT_MAP);

        // Задаём стартовые позиции врагов вдоль верхней границы
        enemyPos = new int[enemies.size()][2];
        for (int i = 0; i < enemies.size(); i++) {
            enemyPos[i][0] = 0;
            enemyPos[i][1] = i;
        }

        // Переключаем карту и центрируем игрока
        player.setCurrentMapType(Player.MapType.FIGHT_MAP);
        int mid = size / 2;
        player.setPosition(mid, mid);
    }

    public void runBattle() {
        Scanner sc = new Scanner(System.in);
        String pSym = "\uD83E\uDDB9\uD83C\uDFFB\u200D♂\uFE0F";

        mainLoop: while (true) {
            // 1) Проверка победы
            boolean anyAlive = enemies.stream().anyMatch(e -> e.getHealth() > 0);
            if (!anyAlive) {
                System.out.println("Все враги повержены — победа!");
                break;
            }

            // 2) Отрисовка поля
            map.resetFightMap();
            for (int i = 0; i < enemies.size(); i++) {
                Enemy e = enemies.get(i);
                if (e.getHealth() > 0) {
                    map.setFightCell(enemyPos[i][0], enemyPos[i][1], enemySymbol);
                }
            }
            map.setFightCell(player.getX(), player.getY(), pSym);
            map.displayCurrentMap(player);
            player.player_condition();

            // 3) Ход героя
            System.out.println("Ход героя: w/a/s/d — шаг; 1 — атак. в одном направлении; 2 — атак. по области; 3 — выпить зелье; q — сбежать");
            String cmd = sc.nextLine().trim();

            if ("q".equalsIgnoreCase(cmd)) {
                System.out.println("Вы сбежали!");
                break;
            }
            // Шаг
            else if ("w".equals(cmd) || "a".equals(cmd) || "s".equals(cmd) || "d".equals(cmd)) {
                int dx = cmd.equals("w") ? -1 : cmd.equals("s") ? 1 : 0;
                int dy = cmd.equals("a") ? -1 : cmd.equals("d") ? 1 : 0;
                if (!player.playerMove(dx, dy, map)) {
                    System.out.println("Туда пройти нельзя.");
                }
            }
            // Направленная атака
            else if ("1".equals(cmd)) {
                System.out.print("Выберите направление (w/a/s/d): ");
                String dir = sc.nextLine().trim();
                int adx = dir.equals("w") ? -1 : dir.equals("s") ? 1 : 0;
                int ady = dir.equals("a") ? -1 : dir.equals("d") ? 1 : 0;
                int tx = player.getX() + adx, ty = player.getY() + ady;
                for (int i = 0; i < enemies.size(); i++) {
                    Enemy e = enemies.get(i);
                    if (e.getHealth() > 0 &&
                            enemyPos[i][0] == tx &&
                            enemyPos[i][1] == ty) {
                        e.takeDamage(player.getDmg());
                        System.out.println("Направленная атака поразила "
                                + e.getName() + ", осталось HP: " + e.getHealth());
                    }
                }
            }
            // Атака по области
            else if ("2".equals(cmd)) {
                int[][] offs = {
                        {-1,-1},{-1,0},{-1,1},
                        { 0,-1},        { 0,1},
                        {+1,-1},{+1,0},{+1,1}
                };
                for (int[] off : offs) {
                    int tx = player.getX() + off[0], ty = player.getY() + off[1];
                    for (int i = 0; i < enemies.size(); i++) {
                        Enemy e = enemies.get(i);
                        if (e.getHealth() > 0 &&
                                enemyPos[i][0] == tx &&
                                enemyPos[i][1] == ty) {
                            e.takeDamage(player.getDmg());
                            System.out.println("Областная атака поразила " + e.getName());
                        }
                    }
                }
            }
            // Выпить зелье
            else if ("3".equals(cmd)) {
                System.out.println("1 — зелье здоровья; 2 — зелье урона");
                String potion = sc.nextLine().trim();
                if ("1".equals(potion) && player.getHealingPotion() > 0) {
                    player.setHealth(player.getHealth() + 50);
                    player.setHealingPotion(player.getHealingPotion() - 1);
                    System.out.println("Вы выпили зелье здоровья. HP = " + player.getHealth());
                }
                else if ("2".equals(potion) && player.getBoostDmgPotion() > 0) {
                    player.setDmg(player.getDmg() + 25);
                    player.setBoostDmgPotion(player.getBoostDmgPotion() - 1);
                    System.out.println("Вы выпили зелье урона. DMG = " + player.getDmg());
                }
                else {
                    System.out.println("У вас нет таких зелий или неверный выбор.");
                }
            }
            else {
                System.out.println("Неверная команда.");
            }

            // 4) Ход врагов
            for (int i = 0; i < enemies.size(); i++) {
                Enemy e = enemies.get(i);
                if (e.getHealth() <= 0) continue;
                int d = rnd.nextInt(4);
                int dx = (d == 0 ? -1 : d == 1 ? 1 : 0);
                int dy = (d == 2 ? -1 : d == 3 ? 1 : 0);
                int nx = enemyPos[i][0] + dx;
                int ny = enemyPos[i][1] + dy;
                if (nx >= 0 && nx < size && ny >= 0 && ny < size
                        && !(nx == player.getX() && ny == player.getY())
                        && isCellFree(nx, ny, i)) {
                    enemyPos[i][0] = nx;
                    enemyPos[i][1] = ny;
                }
            }

            // 5) Автоатака врагов по области
            int damageTaken = 0;
            int[][] neighborOffsets = {
                    {-1,-1},{-1,0},{-1,1},
                    { 0,-1},       { 0,1},
                    { 1,-1},{ 1,0},{ 1,1}
            };
            for (int i = 0; i < enemies.size(); i++) {
                Enemy e = enemies.get(i);
                if (e.getHealth() <= 0) continue;
                for (int[] off : neighborOffsets) {
                    int tx = player.getX() + off[0];
                    int ty = player.getY() + off[1];
                    if (enemyPos[i][0] == tx && enemyPos[i][1] == ty) {
                        damageTaken += 15;
                        break;
                    }
                }
            }
            if (damageTaken > 0) {
                player.setHealth(player.getHealth() - damageTaken);
                System.out.println("Враги ранят вас! Урон: " + damageTaken
                        + ", ваше HP: " + player.getHealth());
                if (player.getHealth() <= 0) {
                    System.out.println("Ты пал в бою... Орда скорбит.");
                    System.exit(0);
                }
            }
        }
    }

    private boolean isCellFree(int x, int y, int skipIndex) {
        for (int j = 0; j < enemies.size(); j++) {
            if (j == skipIndex) continue;
            if (enemies.get(j).getHealth() > 0
                    && enemyPos[j][0] == x
                    && enemyPos[j][1] == y) {
                return false;
            }
        }
        return true;
    }
}
