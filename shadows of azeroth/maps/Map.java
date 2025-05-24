package maps;

import entities.Player;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private final Cell[][] ogreMap;
    private final Cell[][] clearOgreMap;
    private final Cell[][] ruinMap;
    private final Cell[][] clearRuinMap;
    private final Cell[][] frozenMap;
    private final Cell[][] clearFrozenMap;
    private final Cell[][] fightMap;
    private final Cell[][] clearFightMap;

    private final int OGRE_MAP_SIZE = 8;
    private final int RUINS_MAP_SIZE = 5;
    private final int FROZEN_MAP_SIZE = 8;
    private final int FIGHT_MAP_SIZE = 5;

    private Player player;

    public Map() {
        // Инициализация всех карт
        ogreMap = new Cell[OGRE_MAP_SIZE][OGRE_MAP_SIZE];
        clearOgreMap = new Cell[OGRE_MAP_SIZE][OGRE_MAP_SIZE];
        ruinMap = new Cell[RUINS_MAP_SIZE][RUINS_MAP_SIZE];
        clearRuinMap = new Cell[RUINS_MAP_SIZE][RUINS_MAP_SIZE];
        frozenMap = new Cell[FROZEN_MAP_SIZE][FROZEN_MAP_SIZE];
        clearFrozenMap = new Cell[FROZEN_MAP_SIZE][FROZEN_MAP_SIZE];
        fightMap = new Cell[FIGHT_MAP_SIZE][FIGHT_MAP_SIZE];
        clearFightMap = new Cell[FIGHT_MAP_SIZE][FIGHT_MAP_SIZE];

        createMaps();
    }

    // Создание всех карт
    private void createMaps() {
        createOgreMap();
        createRuinMap();
        createFrozenMap();
        createFightMap();
    }

    // Создание OGRE_MAP
    private void createOgreMap() {
        for (int x = 0; x < OGRE_MAP_SIZE; x++) {
            for (int y = 0; y < OGRE_MAP_SIZE; y++) {
                ogreMap[x][y] = new Cell("-");
                clearOgreMap[x][y] = new Cell("-");
            }
        }

        // Лес по краям
        for (int y = 0; y < OGRE_MAP_SIZE; y++) {
            ogreMap[0][y].setCelltype("\uD83C\uDF32");
            ogreMap[1][y].setCelltype("\uD83C\uDF32");
            ogreMap[6][y].setCelltype("\uD83C\uDF32");
            ogreMap[7][y].setCelltype("\uD83C\uDF32");
            clearOgreMap[0][y].setCelltype("\uD83C\uDF32");
            clearOgreMap[1][y].setCelltype("\uD83C\uDF32");
            clearOgreMap[6][y].setCelltype("\uD83C\uDF32");
            clearOgreMap[7][y].setCelltype("\uD83C\uDF32");
        }

        for (int x = 0; x < OGRE_MAP_SIZE; x++) {
            ogreMap[x][0].setCelltype("\uD83C\uDF32");
            ogreMap[x][7].setCelltype("\uD83C\uDF32");
            clearOgreMap[x][0].setCelltype("\uD83C\uDF32");
            clearOgreMap[x][7].setCelltype("\uD83C\uDF32");
        }

        // Палатки
        ogreMap[2][1].setCelltype("\uD83C\uDFD5\uFE0F");
        ogreMap[2][3].setCelltype("\uD83C\uDFD5\uFE0F");
        ogreMap[5][1].setCelltype("\uD83C\uDFD5\uFE0F");
        ogreMap[3][6].setCelltype("\uD83C\uDFD5\uFE0F");
        ogreMap[4][5].setCelltype("\uD83C\uDFD5\uFE0F");
        ogreMap[5][3].setCelltype("\uD83C\uDFD5\uFE0F");
        ogreMap[5][4].setCelltype("\uD83C\uDFD5\uFE0F");

        // Тралл
        ogreMap[3][2].setCelltype("\uD83E\uDDCC");
        clearOgreMap[3][2].setCelltype("\uD83E\uDDCC");

        // Лягушки
        ogreMap[4][1].setCelltype("\uD83D\uDC38");
        ogreMap[5][2].setCelltype("\uD83D\uDC38");
        ogreMap[5][6].setCelltype("\uD83D\uDC38");
        ogreMap[4][6].setCelltype("\uD83D\uDC38");
        ogreMap[3][5].setCelltype("\uD83D\uDC38");
        ogreMap[3][1].setCelltype("\uD83D\uDC38");
        ogreMap[2][2].setCelltype("\uD83D\uDC38");

        // Дороги
        ogreMap[2][4].setCelltype("\uD83D\uDFE8");
        ogreMap[2][5].setCelltype("\uD83D\uDFE8");
        ogreMap[2][6].setCelltype("\uD83D\uDFE8");
        ogreMap[3][4].setCelltype("\uD83D\uDFE8");
        ogreMap[4][4].setCelltype("\uD83D\uDFE8");
        ogreMap[4][2].setCelltype("\uD83D\uDFE8");
        ogreMap[4][3].setCelltype("\uD83D\uDFE8");
        ogreMap[1][5].setCelltype("\uD83D\uDFE8");

        // Переходы
        ogreMap[2][7].setCelltype("\uD80C\uDE78");
        ogreMap[0][5].setCelltype("\uD83C\uDF0C");

        // Магазин и кузнец
        ogreMap[6][3].setCelltype("🛒");
        ogreMap[1][4].setCelltype("⚒\uFE0F");

        // Копируем данные в clearOgreMap
        for (int x = 0; x < OGRE_MAP_SIZE; x++) {
            for (int y = 0; y < OGRE_MAP_SIZE; y++) {
                clearOgreMap[x][y].setCelltype(ogreMap[x][y].getCelltype());
            }
        }
    }

    // Создание RUINS_MAP
    private void createRuinMap() {
        for (int x = 0; x < RUINS_MAP_SIZE; x++) {
            for (int y = 0; y < RUINS_MAP_SIZE; y++) {
                ruinMap[x][y] = new Cell("-");
                clearRuinMap[x][y] = new Cell("-");
            }
        }

        // Пример заполнения руин
        ruinMap[0][0].setCelltype("\uD83D\uDC80");
        ruinMap[0][1].setCelltype("\u26B0");
        ruinMap[0][2].setCelltype("\uD83C\uDF32");
        ruinMap[0][3].setCelltype("\u26B0");
        ruinMap[0][4].setCelltype("\uD83C\uDF32");

        ruinMap[1][0].setCelltype("\u26B0");
        ruinMap[1][1].setCelltype("\uD83D\uDD2E"); // Загадка
        ruinMap[1][2].setCelltype("\uD83D\uDC80");
        ruinMap[1][3].setCelltype("\uD83C\uDF32");
        ruinMap[1][4].setCelltype("\u26B0");

        ruinMap[2][0].setCelltype("\uD83C\uDF32");
        ruinMap[2][1].setCelltype("\uD83D\uDC80");
        ruinMap[2][2].setCelltype("\uD83D\uDD4C");
        ruinMap[2][3].setCelltype("\uD83D\uDC80");
        ruinMap[2][4].setCelltype("\uD83C\uDF32");

        ruinMap[3][0].setCelltype("\uD83C\uDF32");
        ruinMap[3][1].setCelltype("\u26B0");
        ruinMap[3][2].setCelltype("\uD83D\uDD2E");
        ruinMap[3][3].setCelltype("\u26B0");
        ruinMap[3][4].setCelltype("\uD83D\uDC80");

        ruinMap[4][0].setCelltype("\uD83D\uDC80");
        ruinMap[4][1].setCelltype("\uD83C\uDF32");
        ruinMap[4][2].setCelltype("\u26B0");
        ruinMap[4][3].setCelltype("\uD83D\uDC80");
        ruinMap[4][4].setCelltype("\uD80C\uDE78");

        // Копируем данные в clearRuinMap
        for (int x = 0; x < RUINS_MAP_SIZE; x++) {
            for (int y = 0; y < RUINS_MAP_SIZE; y++) {
                clearRuinMap[x][y].setCelltype(ruinMap[x][y].getCelltype());
            }
        }
    }

    // Создание FROZEN_MAP
    private void createFrozenMap() {
        for (int x = 0; x < FROZEN_MAP_SIZE; x++) {
            for (int y = 0; y < FROZEN_MAP_SIZE; y++) {
                frozenMap[x][y] = new Cell("-");
                clearFrozenMap[x][y] = new Cell("-");
            }
        }

        // Лед по краям
        for (int x = 0; x < FROZEN_MAP_SIZE; x++) {
            for (int y = 0; y < FROZEN_MAP_SIZE; y++) {
                if (x == 0 || x == FROZEN_MAP_SIZE - 1 || y == 0 || y == FROZEN_MAP_SIZE - 1) {
                    frozenMap[x][y].setCelltype("\u2744");
                    clearFrozenMap[x][y].setCelltype("\u2744");
                } else {
                    frozenMap[x][y].setCelltype("\uD83C\uDF32");
                    clearFrozenMap[x][y].setCelltype("\uD83C\uDF32");
                }
            }
        }

        // Босс
        frozenMap[4][4].setCelltype("\uD83E\uDDB9");
        clearFrozenMap[4][4].setCelltype("\uD83E\uDDB9");

        // Приспешники
        frozenMap[6][1].setCelltype("\uD83D\uDC3E");
        frozenMap[6][2].setCelltype("\uD83D\uDC3E");
        frozenMap[6][4].setCelltype("\uD83D\uDC3E");

        // Переходы
        frozenMap[7][7].setCelltype("\uD83C\uDF0C");

        // Другие объекты
        frozenMap[2][0].setCelltype("\uD83E\uDEA7");
        frozenMap[2][1].setCelltype("\uD83C\uDF32");
        frozenMap[2][2].setCelltype("\uD83D\uDC3E");
        frozenMap[2][3].setCelltype("\uD83C\uDFD4");
        frozenMap[2][4].setCelltype("\uD83E\uDEA7");
        frozenMap[2][5].setCelltype("\uD83D\uDD7B");
        frozenMap[2][6].setCelltype("\u2744");
        frozenMap[2][7].setCelltype("\uD83C\uDF32");

        frozenMap[3][0].setCelltype("\uD83C\uDF32");
        frozenMap[3][1].setCelltype("\uD83D\uDC3E");
        frozenMap[3][2].setCelltype("\uD83C\uDFD4");
        frozenMap[3][3].setCelltype("\uD83D\uDD2E");
        frozenMap[3][4].setCelltype("\uD83C\uDFD4");
        frozenMap[3][5].setCelltype("\uD83D\uDC3E");
        frozenMap[3][6].setCelltype("\uD83C\uDF32");
        frozenMap[3][7].setCelltype("\uD83D\uDC3E");

        frozenMap[4][0].setCelltype("\uD83C\uDFD4");
        frozenMap[4][1].setCelltype("\uD83E\uDEA7");
        frozenMap[4][2].setCelltype("\uD83D\uDC3E");
        frozenMap[4][3].setCelltype("\uD83C\uDFD4");
        frozenMap[4][5].setCelltype("\uD83D\uDC3E");
        frozenMap[4][6].setCelltype("\uD83C\uDFD4");
        frozenMap[4][7].setCelltype("\uD83E\uDEA7");

        frozenMap[5][0].setCelltype("\uD83D\uDC3E");
        frozenMap[5][1].setCelltype("\uD83C\uDF32");
        frozenMap[5][2].setCelltype("\uD83D\uDD7B");
        frozenMap[5][3].setCelltype("\uD83E\uDEA7");
        frozenMap[5][4].setCelltype("\uD83D\uDC3E");
        frozenMap[5][5].setCelltype("\uD83C\uDFD4");
        frozenMap[5][6].setCelltype("\uD83E\uDEA7");
        frozenMap[5][7].setCelltype("\uD83C\uDF32");

        frozenMap[6][0].setCelltype("\uD83C\uDFD4");
        frozenMap[6][1].setCelltype("\uD83E\uDEA7");
        frozenMap[6][2].setCelltype("\uD83D\uDC3E");
        frozenMap[6][3].setCelltype("\uD83C\uDF32");
        frozenMap[6][4].setCelltype("\uD83D\uDC3E");
        frozenMap[6][5].setCelltype("\uD83C\uDFD4");
        frozenMap[6][6].setCelltype("\uD83E\uDEA7");
        frozenMap[6][7].setCelltype("\uD83D\uDC3E");

        frozenMap[7][0].setCelltype("\u2744");
        frozenMap[7][1].setCelltype("\uD83C\uDFD4");
        frozenMap[7][2].setCelltype("\uD83D\uDC3E");
        frozenMap[7][3].setCelltype("\uD83C\uDF32");
        frozenMap[7][4].setCelltype("\uD83D\uDC3E");
        frozenMap[7][5].setCelltype("\uD83C\uDFD4");
        frozenMap[7][6].setCelltype("\uD83E\uDEA7");
        frozenMap[7][7].setCelltype("\uD83C\uDF0C");

        // Копируем данные в clearFrozenMap
        for (int x = 0; x < FROZEN_MAP_SIZE; x++) {
            for (int y = 0; y < FROZEN_MAP_SIZE; y++) {
                clearFrozenMap[x][y].setCelltype(frozenMap[x][y].getCelltype());
            }
        }
    }

    // Создание FIGHT_MAP
    private void createFightMap() {
        for (int x = 0; x < FIGHT_MAP_SIZE; x++) {
            for (int y = 0; y < FIGHT_MAP_SIZE; y++) {
                fightMap[x][y] = new Cell("-");
                clearFightMap[x][y] = new Cell("-");
            }
        }

        // Стандартная боевая карта
        for (int x = 0; x < FIGHT_MAP_SIZE; x++) {
            for (int y = 0; y < FIGHT_MAP_SIZE; y++) {
                if (x == 0 || y == 0 || x == FIGHT_MAP_SIZE - 1 || y == FIGHT_MAP_SIZE - 1) {
                    fightMap[x][y].setCelltype("\uD83D\uDD2E"); // Загадки по краям
                } else {
                    fightMap[x][y].setCelltype("-");
                }
                clearFightMap[x][y].setCelltype(fightMap[x][y].getCelltype());
            }
        }
    }

    // Отображение текущей карты
    // В Map.java
    public void displayCurrentMap(Player player) {
        Cell[][] currentMap = null;
        int size = 0;

        // Определяем текущую карту и её размеры
        switch (player.getCurrentMapType()) {
            case OGRE_LANDS:
                currentMap = ogreMap;
                size = OGRE_MAP_SIZE;
                break;
            case RUINS:
                currentMap = ruinMap;
                size = RUINS_MAP_SIZE;
                break;
            case FROZEN_MAP:
                currentMap = frozenMap;
                size = FROZEN_MAP_SIZE;
                break;
            case FIGHT_MAP:
                currentMap = fightMap;
                size = FIGHT_MAP_SIZE;
                break;
            default:
                return; // Если карта не определена — выходим
        }

        // Рисуем карту
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x == player.getX() && y == player.getY()) {
                    System.out.print(" 🧙‍♂️ ");
                } else {
                    System.out.print(" " + currentMap[x][y].getCelltype() + " ");
                }
            }
            System.out.println();
        }
    }
    // В Map.java
    public void updatePlayerPosition(Player player) {
        for (int x = 0; x < getCurrentMapMaxX(player.getCurrentMapType()); x++) {
            for (int y = 0; y < getCurrentMapMaxY(player.getCurrentMapType()); y++) {
                if (x == player.getX() && y == player.getY()) {
                    setFightCell(x, y, "\uD83E\uDDB9"); // Эмодзи героя
                }
            }
        }
    }

    private void drawMap(Cell[][] map, int size, int playerX, int playerY) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x == playerX && y == playerY) {
                    System.out.print(" 🧙‍♂️ ");
                } else {
                    System.out.print(" " + map[x][y].getCelltype() + " ");
                }
            }
            System.out.println();
        }
    }

    // Получение текущей карты
    public Cell[][] getCurrentMapGrid(Player.MapType mapType) {
        return switch (mapType) {
            case OGRE_LANDS -> ogreMap;
            case RUINS -> ruinMap;
            case FROZEN_MAP -> frozenMap;
            case FIGHT_MAP -> fightMap;
            default -> null;
        };
    }

    // Получение "чистой" версии карты
    private Cell[][] getClearMap(Player.MapType mapType) {
        return switch (mapType) {
            case OGRE_LANDS -> clearOgreMap;
            case RUINS -> clearRuinMap;
            case FROZEN_MAP -> clearFrozenMap;
            case FIGHT_MAP -> clearFightMap;
            default -> null;
        };
    }

    // Получение размера карты
    public int getCurrentMapMaxX(Player.MapType mapType) {
        return switch (mapType) {
            case OGRE_LANDS -> OGRE_MAP_SIZE;
            case RUINS -> RUINS_MAP_SIZE;
            case FROZEN_MAP -> FROZEN_MAP_SIZE;
            case FIGHT_MAP -> FIGHT_MAP_SIZE;
            default -> 0;
        };
    }
    // В классе Map
    public int getCurrentMapMaxY(Player.MapType mapType) {
        return getCurrentMapMaxX(mapType); // Карта квадратная
    }

    // Метод установки эмодзи врага на боевой карте
    public void setFightCell(int x, int y, String symbol) {
        if (x >= 0 && x < fightMap.length && y >= 0 && y < fightMap[0].length) {
            fightMap[x][y].setCelltype(symbol);
        }
    }

    // Восстановление боевой карты
    public void resetFightMap() {
        for (int x = 0; x < FIGHT_MAP_SIZE; x++) {
            for (int y = 0; y < FIGHT_MAP_SIZE; y++) {
                fightMap[x][y].setCelltype(clearFightMap[x][y].getCelltype());
            }
        }
    }

    // Методы для сохранений
    public List<List<String>> getMapAsList(Player.MapType mapType) {
        Cell[][] map = getCurrentMapGrid(mapType);
        List<List<String>> grid = new ArrayList<>();

        for (Cell[] row : map) {
            List<String> line = new ArrayList<>();
            for (Cell cell : row) {
                line.add(cell.getCelltype());
            }
            grid.add(line);
        }

        return grid;
    }

    public void restoreMapFromList(Player.MapType mapType, List<List<String>> data) {
        Cell[][] target = getCurrentMapGrid(mapType);
        Cell[][] clearMap = getClearMap(mapType);

        for (int x = 0; x < data.size(); x++) {
            List<String> row = data.get(x);
            for (int y = 0; y < row.size(); y++) {
                target[x][y].setCelltype(row.get(y));
                clearMap[x][y].setCelltype(row.get(y));
            }
        }
    }

    public Cell[][] getFightMap() {
        return fightMap;
    }
    public List<List<String>> getOgreMapData() {
        List<List<String>> grid = new ArrayList<>();
        for (Cell[] row : ogreMap) {
            List<String> line = new ArrayList<>();
            for (Cell cell : row) {
                line.add(cell.getCelltype());
            }
            grid.add(line);
        }
        return grid;
    }

    public List<List<String>> getRuinMapData() {
        List<List<String>> grid = new ArrayList<>();
        for (Cell[] row : ruinMap) {
            List<String> line = new ArrayList<>();
            for (Cell cell : row) {
                line.add(cell.getCelltype());
            }
            grid.add(line);
        }
        return grid;
    }
    public List<List<String>> getFrozenMapData() {
        List<List<String>> grid = new ArrayList<>();
        for (Cell[] row : frozenMap) {
            List<String> line = new ArrayList<>();
            for (Cell cell : row) {
                line.add(cell.getCelltype());
            }
            grid.add(line);
        }
        return grid;
    }

    // Установка игрока
    public void setPlayer(Player player) {
        this.player = player;
    }
}