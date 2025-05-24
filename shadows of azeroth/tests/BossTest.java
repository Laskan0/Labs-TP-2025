package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import other_things.BossBattle;
import entities.Player;

public class BossTest {
    @Test
    public void testVictoryEndsGame() {
        BossBattle battle = new BossBattle();
        battle.setBossHealth(0); // Босс повержен
        battle.setBattleActive(false);
        Player player = new Player(0, 0);
        boolean gameOver = battle.isWonLich() == 0;
        assertTrue(gameOver);
    }
}