package maps;
import entities.Player;
import entities.Spirit;

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
    private void createMaps() {
        createOgreMap();
        createRuinMap();
        createFrozenMap();
        createFightMap();
    }

    private void createOgreMap() {

        for(int x = 0; x < OGRE_MAP_SIZE; x++) {
            for(int y = 0; y < OGRE_MAP_SIZE; y++) {
                ogreMap[x][y] = new Cell("-");
                clearOgreMap[x][y] = new Cell("-");
            }
        }


        ogreMap[3][3].setCelltype("\uD83D\uDD25");
        clearOgreMap[3][3].setCelltype("\uD83D\uDD25");


        for(int y = 0; y < OGRE_MAP_SIZE; y++) {
            ogreMap[0][y].setCelltype("\uD83C\uDF32");
            ogreMap[1][y].setCelltype("\uD83C\uDF32");
            ogreMap[6][y].setCelltype("\uD83C\uDF32");
            ogreMap[7][y].setCelltype("\uD83C\uDF32");

            clearOgreMap[0][y].setCelltype("\uD83C\uDF32");
            clearOgreMap[1][y].setCelltype("\uD83C\uDF32");
            clearOgreMap[6][y].setCelltype("\uD83C\uDF32");
            clearOgreMap[7][y].setCelltype("\uD83C\uDF32");
        }


        for(int x = 0; x < OGRE_MAP_SIZE; x++) {
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

        clearOgreMap[2][1].setCelltype("\uD83C\uDFD5\uFE0F");
        clearOgreMap[2][3].setCelltype("\uD83C\uDFD5\uFE0F");
        clearOgreMap[5][1].setCelltype("\uD83C\uDFD5\uFE0F");
        clearOgreMap[3][6].setCelltype("\uD83C\uDFD5\uFE0F");
        clearOgreMap[4][5].setCelltype("\uD83C\uDFD5\uFE0F");
        clearOgreMap[5][3].setCelltype("\uD83C\uDFD5\uFE0F");
        clearOgreMap[5][4].setCelltype("\uD83C\uDFD5\uFE0F");

        // Тралл
        ogreMap[3][2].setCelltype("\uD83E\uDDCC");
        clearOgreMap[3][2].setCelltype("\uD83E\uDDCC");

        // Мобы-огры
        ogreMap[4][1].setCelltype("\uD83D\uDC38");
        ogreMap[5][2].setCelltype("\uD83D\uDC38");
        ogreMap[5][6].setCelltype("\uD83D\uDC38");
        ogreMap[4][6].setCelltype("\uD83D\uDC38");
        ogreMap[3][5].setCelltype("\uD83D\uDC38");
        ogreMap[3][1].setCelltype("\uD83D\uDC38");
        ogreMap[2][2].setCelltype("\uD83D\uDC38");

        clearOgreMap[4][1].setCelltype("\uD83D\uDC38");
        clearOgreMap[5][2].setCelltype("\uD83D\uDC38");
        clearOgreMap[5][6].setCelltype("\uD83D\uDC38");
        clearOgreMap[4][6].setCelltype("\uD83D\uDC38");
        clearOgreMap[3][5].setCelltype("\uD83D\uDC38");
        clearOgreMap[3][1].setCelltype("\uD83D\uDC38");
        clearOgreMap[2][2].setCelltype("\uD83D\uDC38");


        // Дороги

        ogreMap[2][4].setCelltype("\uD83D\uDFE8");
        ogreMap[2][5].setCelltype("\uD83D\uDFE8");
        ogreMap[2][6].setCelltype("\uD83D\uDFE8");
        ogreMap[3][4].setCelltype("\uD83D\uDFE8");
        ogreMap[4][4].setCelltype("\uD83D\uDFE8");
        ogreMap[4][2].setCelltype("\uD83D\uDFE8");
        ogreMap[4][3].setCelltype("\uD83D\uDFE8");
        ogreMap[1][5].setCelltype("\uD83D\uDFE8");

        clearOgreMap[2][4].setCelltype("\uD83D\uDFE8");
        clearOgreMap[2][5].setCelltype("\uD83D\uDFE8");
        clearOgreMap[2][6].setCelltype("\uD83D\uDFE8");
        clearOgreMap[3][4].setCelltype("\uD83D\uDFE8");
        clearOgreMap[4][4].setCelltype("\uD83D\uDFE8");
        clearOgreMap[4][2].setCelltype("\uD83D\uDFE8");
        clearOgreMap[4][3].setCelltype("\uD83D\uDFE8");
        clearOgreMap[1][5].setCelltype("\uD83D\uDFE8");


        // Особые объекты
        ogreMap[2][7].setCelltype("\uD80C\uDE78");
        ogreMap[0][5].setCelltype("\uD83C\uDF0C");
        clearOgreMap[2][7].setCelltype("\uD80C\uDE78");
        clearOgreMap[0][5].setCelltype("\uD83C\uDF0C");
        ogreMap[6][3].setCelltype("🛒");
        clearOgreMap[6][3].setCelltype("🛒");

        // Кузнец
        ogreMap[1][4].setCelltype("⚒\uFE0F");
        clearOgreMap[1][4].setCelltype("⚒\uFE0F");
    }

    private void createRuinMap() {
        for(int x = 0; x < RUINS_MAP_SIZE; x++) {
            for(int y = 0; y < RUINS_MAP_SIZE; y++) {
                ruinMap[x][y] = new Cell("-");
                clearRuinMap[x][y] = new Cell("-");

            }
        }

        ruinMap[0][0].setCelltype("\uD83D\uDC80");
        ruinMap[0][1].setCelltype("\u26B0");
        ruinMap[0][2].setCelltype("\uD83C\uDF32");
        ruinMap[0][3].setCelltype("\u26B0");
        ruinMap[0][4].setCelltype("\uD83C\uDF32");

        ruinMap[1][0].setCelltype("\u26B0");
        ruinMap[1][1].setCelltype("\uD83D\uDD2E");
        ruinMap[1][2].setCelltype("\uD83D\uDC80");
        ruinMap[1][3].setCelltype("\uD83C\uDF32");
        ruinMap[1][4].setCelltype("\u26B0");

        ruinMap[2][0].setCelltype("\uD83C\uDF32");
        ruinMap[2][1].setCelltype("\uD83D\uDC80");
        ruinMap[2][2].setCelltype("\uD83D\uDD4C");
        ruinMap[2][3].setCelltype("\uD83D\uDC80");
        ruinMap[2][4].setCelltype("\uD83C\uDF32");

        ruinMap[3][0].setCelltype("\u26B0");
        ruinMap[3][1].setCelltype("\uD83C\uDF32");
        ruinMap[3][2].setCelltype("\uD83D\uDD2E");
        ruinMap[3][3].setCelltype("\u26B0");
        ruinMap[3][4].setCelltype("\uD83D\uDC80");

        ruinMap[4][0].setCelltype("\uD83D\uDC80");
        ruinMap[4][1].setCelltype("\uD83C\uDF32");
        ruinMap[4][2].setCelltype("\u26B0");
        ruinMap[4][3].setCelltype("\uD83D\uDC80");
        ruinMap[4][4].setCelltype("\uD80C\uDE78");


        clearRuinMap[0][0].setCelltype("\uD83D\uDC80");
        clearRuinMap[0][1].setCelltype("\u26B0");
        clearRuinMap[0][2].setCelltype("\uD83C\uDF32");
        clearRuinMap[0][3].setCelltype("\u26B0");
        clearRuinMap[0][4].setCelltype("\uD83C\uDF32");

        clearRuinMap[1][0].setCelltype("\u26B0");
        clearRuinMap[1][1].setCelltype("\uD83D\uDD2E");
        clearRuinMap[1][2].setCelltype("\uD83D\uDC80");
        clearRuinMap[1][3].setCelltype("\uD83C\uDF32");
        clearRuinMap[1][4].setCelltype("\u26B0");

        clearRuinMap[2][0].setCelltype("\uD83C\uDF32");
        clearRuinMap[2][1].setCelltype("\uD83D\uDC80");
        clearRuinMap[2][2].setCelltype("\uD83D\uDD4C");
        clearRuinMap[2][3].setCelltype("\uD83D\uDC80");
        clearRuinMap[2][4].setCelltype("\uD83C\uDF32");

        clearRuinMap[3][0].setCelltype("\u26B0");
        clearRuinMap[3][1].setCelltype("\uD83C\uDF32");
        clearRuinMap[3][2].setCelltype("\uD83D\uDD2E");
        clearRuinMap[3][3].setCelltype("\u26B0");
        clearRuinMap[3][4].setCelltype("\uD83D\uDC80");

        clearRuinMap[4][0].setCelltype("\uD83D\uDC80");
        clearRuinMap[4][1].setCelltype("\uD83C\uDF32");
        clearRuinMap[4][2].setCelltype("\u26B0");
        clearRuinMap[4][3].setCelltype("\uD83D\uDC80");
        clearRuinMap[4][4].setCelltype("\uD80C\uDE78");



    }

    private void createFrozenMap(){
        for(int x = 0; x < FROZEN_MAP_SIZE; x++) {
            for(int y = 0; y < FROZEN_MAP_SIZE; y++) {
                frozenMap[x][y] = new Cell("-");
                clearFrozenMap[x][y] = new Cell("-");



            }
        }

        frozenMap[0][0].setCelltype("\u2744");
        frozenMap[0][1].setCelltype("-");
        frozenMap[0][2].setCelltype("\uD83E\uDEA7");
        frozenMap[0][3].setCelltype("\u2744");
        frozenMap[0][4].setCelltype("\uD83C\uDFD4");
        frozenMap[0][5].setCelltype("\uD83D\uDC3E");
        frozenMap[0][6].setCelltype("\uD83C\uDF32");
        frozenMap[0][7].setCelltype("\u2744");

        frozenMap[1][0].setCelltype("\uD83C\uDFD4");
        frozenMap[1][1].setCelltype("\uD83E\uDEA7");
        frozenMap[1][2].setCelltype("\uD83D\uDD7B");
        frozenMap[1][3].setCelltype("\u2744");
        frozenMap[1][4].setCelltype("\uD83C\uDFD4");
        frozenMap[1][5].setCelltype("\uD83E\uDEA7");
        frozenMap[1][6].setCelltype("\uD83D\uDC3E");
        frozenMap[1][7].setCelltype("\uD83C\uDFD4");

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
        frozenMap[4][4].setCelltype("\uD83E\uDDB9");
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


        clearFrozenMap[0][0].setCelltype("\u2744");
        clearFrozenMap[0][1].setCelltype("\uD83C\uDFD4");
        clearFrozenMap[0][2].setCelltype("\uD83E\uDEA7");
        clearFrozenMap[0][3].setCelltype("\u2744");
        clearFrozenMap[0][4].setCelltype("\uD83C\uDFD4");
        clearFrozenMap[0][5].setCelltype("\uD83D\uDC3E");
        clearFrozenMap[0][6].setCelltype("\uD83C\uDF32");
        clearFrozenMap[0][7].setCelltype("\u2744");

        clearFrozenMap[1][0].setCelltype("\uD83C\uDFD4");
        clearFrozenMap[1][1].setCelltype("\uD83E\uDEA7");
        clearFrozenMap[1][2].setCelltype("\uD83D\uDD7B");
        clearFrozenMap[1][3].setCelltype("\u2744");
        clearFrozenMap[1][4].setCelltype("\uD83C\uDFD4");
        clearFrozenMap[1][5].setCelltype("\uD83E\uDEA7");
        clearFrozenMap[1][6].setCelltype("\uD83D\uDC3E");
        clearFrozenMap[1][7].setCelltype("\uD83C\uDFD4");

        clearFrozenMap[2][0].setCelltype("\uD83E\uDEA7");
        clearFrozenMap[2][1].setCelltype("\uD83C\uDF32");
        clearFrozenMap[2][2].setCelltype("\uD83D\uDC3E");
        clearFrozenMap[2][3].setCelltype("\uD83C\uDFD4");
        clearFrozenMap[2][4].setCelltype("\uD83E\uDEA7");
        clearFrozenMap[2][5].setCelltype("\uD83D\uDD7B");
        clearFrozenMap[2][6].setCelltype("\u2744");
        clearFrozenMap[2][7].setCelltype("\uD83C\uDF32");

        clearFrozenMap[3][0].setCelltype("\uD83C\uDF32");
        clearFrozenMap[3][1].setCelltype("\uD83D\uDC3E");
        clearFrozenMap[3][2].setCelltype("\uD83C\uDFD4");
        clearFrozenMap[3][3].setCelltype("\uD83D\uDD2E");
        clearFrozenMap[3][4].setCelltype("\uD83C\uDFD4");
        clearFrozenMap[3][5].setCelltype("\uD83D\uDC3E");
        clearFrozenMap[3][6].setCelltype("\uD83C\uDF32");
        clearFrozenMap[3][7].setCelltype("\uD83D\uDC3E");

        clearFrozenMap[4][0].setCelltype("\uD83C\uDFD4");
        clearFrozenMap[4][1].setCelltype("\uD83E\uDEA7");
        clearFrozenMap[4][2].setCelltype("\uD83D\uDC3E");
        clearFrozenMap[4][3].setCelltype("\uD83C\uDFD4");
        clearFrozenMap[4][4].setCelltype("\uD83E\uDDB9");
        clearFrozenMap[4][5].setCelltype("\uD83D\uDC3E");
        clearFrozenMap[4][6].setCelltype("\uD83C\uDFD4");
        clearFrozenMap[4][7].setCelltype("\uD83E\uDEA7");

        clearFrozenMap[5][0].setCelltype("\uD83D\uDC3E");
        clearFrozenMap[5][1].setCelltype("\uD83C\uDF32");
        clearFrozenMap[5][2].setCelltype("\uD83D\uDD7B");
        clearFrozenMap[5][3].setCelltype("\uD83E\uDEA7");
        clearFrozenMap[5][4].setCelltype("\uD83D\uDC3E");
        clearFrozenMap[5][5].setCelltype("\uD83C\uDFD4");
        clearFrozenMap[5][6].setCelltype("\uD83E\uDEA7");
        clearFrozenMap[5][7].setCelltype("\uD83C\uDF32");

        clearFrozenMap[6][0].setCelltype("\uD83C\uDFD4");
        clearFrozenMap[6][1].setCelltype("\uD83E\uDEA7");
        clearFrozenMap[6][2].setCelltype("\uD83D\uDC3E");
        clearFrozenMap[6][3].setCelltype("\uD83C\uDF32");
        clearFrozenMap[6][4].setCelltype("\uD83D\uDC3E");
        clearFrozenMap[6][5].setCelltype("\uD83C\uDFD4");
        clearFrozenMap[6][6].setCelltype("\uD83E\uDEA7");
        clearFrozenMap[6][7].setCelltype("\uD83D\uDC3E");

        clearFrozenMap[7][0].setCelltype("\u2744");
        clearFrozenMap[7][1].setCelltype("\uD83C\uDFD4");
        clearFrozenMap[7][2].setCelltype("\uD83D\uDC3E");
        clearFrozenMap[7][3].setCelltype("\uD83C\uDF32");
        clearFrozenMap[7][4].setCelltype("\uD83D\uDC3E");
        clearFrozenMap[7][5].setCelltype("\uD83C\uDFD4");
        clearFrozenMap[7][6].setCelltype("\uD83E\uDEA7");
        clearFrozenMap[7][7].setCelltype("\u2744");



    }

    private void createFightMap() {
        for (int x = 0; x < FIGHT_MAP_SIZE; x++) {
            for (int y = 0; y < FIGHT_MAP_SIZE; y++) {
                Cell empty = new Cell("-");
                fightMap[x][y]      = empty;
                clearFightMap[x][y] = new Cell(empty.getCelltype());
            }
        }
    }




    public void displayCurrentMap(Player player) {
        Cell[][] currentMap = null;
        Cell[][] clearMap = null;
        int size = 0;

        // Определяем текущую карту и её размеры
        switch (player.getCurrentMapType()) {
            case OGRE_LANDS:
                currentMap = ogreMap;
                clearMap = clearOgreMap;
                size = OGRE_MAP_SIZE;
                break;
            case RUINS:
                currentMap = ruinMap;
                clearMap = clearRuinMap;
                size = RUINS_MAP_SIZE;
                break;
            case FROZEN_MAP:
                currentMap = frozenMap;
                clearMap = clearFrozenMap;
                size = FROZEN_MAP_SIZE;
                break;
            case FIGHT_MAP:
                currentMap = fightMap;
                clearMap = clearFightMap;
                size = FIGHT_MAP_SIZE;
                break;

        }

        // Сбрасываем карту к исходному состоянию
        resetMap(currentMap, clearMap, size);

        // Обновляем позицию игрока
        updatePlayerPosition(currentMap, player.getX(), player.getY());


        // Рисуем карту
        drawMap(currentMap, size);
    }


    private void resetMap(Cell[][] map, Cell[][] clearMap, int size) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (map[x][y].getCelltype().equals("\uD83E\uDDB9\uD83C\uDFFB\u200D♂\uFE0F")) { // Символ игрока
                    map[x][y].setCelltype(clearMap[x][y].getCelltype());
                }
            }
        }
    }



    private void updatePlayerPosition(Cell[][] map, int x, int y) {
        if (x >= 0 && x < map.length && y >= 0 && y < map[0].length) {
            map[x][y].setCelltype("\uD83E\uDDB9\uD83C\uDFFB\u200D♂\uFE0F"); // Символ игрока
        }
    }

    private void drawMap(Cell[][] map, int size) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                System.out.print(" " + map[x][y].getCelltype() + " ");
            }
            System.out.println();
        }
    }


    public int getCurrentMapMaxX(Player.MapType mapType) {
        return switch (mapType) {
            case OGRE_LANDS -> OGRE_MAP_SIZE;
            case RUINS -> RUINS_MAP_SIZE;
            case FROZEN_MAP -> FROZEN_MAP_SIZE;
            case FIGHT_MAP ->  FIGHT_MAP_SIZE;
        };
    }

    public int getCurrentMapMaxY(Player.MapType mapType) {
        return switch (mapType) {
            case OGRE_LANDS -> OGRE_MAP_SIZE;
            case RUINS -> RUINS_MAP_SIZE;
            case FROZEN_MAP -> FROZEN_MAP_SIZE;
            case FIGHT_MAP -> FIGHT_MAP_SIZE;
        };
    }

    public Cell[][] getCurrentMapGrid(Player.MapType mapType) {
        return switch (mapType) {
            case OGRE_LANDS -> ogreMap; // что за стрелочки??
            case RUINS -> ruinMap;
            case FROZEN_MAP -> frozenMap;
            case FIGHT_MAP -> fightMap;

        };
    }

    public void resetFightMap() {
        for (int x = 0; x < FIGHT_MAP_SIZE; x++) {
            for (int y = 0; y < FIGHT_MAP_SIZE; y++) {
                fightMap[x][y].setCelltype(clearFightMap[x][y].getCelltype());
            }
        }
    }

    public void setFightCell(int x, int y, String symbol) {
        fightMap[x][y].setCelltype(symbol);
    }



    public void setPlayer(Player player) {
        this.player = player;
    }


}