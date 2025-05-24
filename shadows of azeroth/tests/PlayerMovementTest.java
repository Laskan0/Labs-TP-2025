package tests;

import entities.Player;
import maps.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerMovementTest {
    private Player player;
    private Map map;

    @BeforeEach
    public void setUp() {
        player = new Player(0, 0); // Начальная позиция (0, 0)
        map = new Map(); // Стандартная карта OGRE_LANDS
    }

    @Test
    public void testPlayerCanMoveOnEmptyCell() {
        // Проверяем движение на свободную ячейку
        boolean result = player.playerMove(1, 0, map); // Движение вниз (x=1, y=0)
        assertTrue(result);
        assertEquals(1, player.getX());
        assertEquals(0, player.getY());
    }



    @Test
    public void testPlayerCannotMoveOutOfBounds() {
        // Попытка выйти за границы карты (OGRE_MAP_SIZE = 8)
        boolean result = player.playerMove(-1, 0, map); // x=-1 — вне карты
        assertFalse(result);
        assertEquals(0, player.getX()); // Не вышли за пределы
    }

    @Test
    public void testPlayerCanMoveOnRoad() {
        // Установим дорогу на (1, 0)
        map.ogreMap[1][0].setCelltype("🛣️"); // Дорога

        // Пытаемся пойти на дорогу
        boolean result = player.playerMove(1, 0, map);
        assertTrue(result);
        assertEquals(1, player.getX());
        assertEquals(0, player.getY());
    }
}