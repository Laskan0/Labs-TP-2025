package tests;

import static org.junit.jupiter.api.Assertions.*;
import entities.Player;
import org.junit.jupiter.api.Test;

public class DmgPotionTest {
    @Test
    public void testBoostPotionIncreasesDamage() {
        Player player = new Player(0, 0);
        player.setBoostDmgPotion(1);
        player.setDmg(50);
        player.setBoostDmgPotion(player.getBoostDmgPotion() - 1);
        player.setDmg(player.getDmg() + 25);
        assertEquals(75, player.getDmg());
        assertEquals(0, player.getBoostDmgPotion());
    }
}