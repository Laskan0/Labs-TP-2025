package tests;

import static org.junit.jupiter.api.Assertions.*;
import entities.Player;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    @Test
    public void testInitialHealth() {
        Player player = new Player(0, 0);
        assertEquals(100, player.getHealth());
    }
}