package tests;

import static org.junit.jupiter.api.Assertions.*;
import entities.Player;
import org.junit.jupiter.api.Test;

public class PotionTest {
    @Test
    public void testHealingPotionIncreasesHealth() {
        Player player = new Player(0, 0);
        player.setHealingPotion(1);
        player.setHealth(50);
        player.setHealingPotion(player.getHealingPotion() - 1);
        player.setHealth(player.getHealth() + 50);
        assertEquals(100, player.getHealth());
        assertEquals(0, player.getHealingPotion());
    }
}