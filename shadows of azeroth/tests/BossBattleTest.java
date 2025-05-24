package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import other_things.BossBattle;
import entities.Player;

public class BossBattleTest {
    @Test
    public void testBossIsDeadWhenHealthZero() {
        BossBattle battle = new BossBattle();
        battle.setBattleActive(false);
        battle.setBossHealth(0);
        assertEquals(0, battle.isWonLich());
    }
}