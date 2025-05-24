package tests;

import static org.junit.jupiter.api.Assertions.*;
import maps.BattleMap;
import entities.Player;
import maps.Map;
import org.junit.jupiter.api.Test;
import java.util.List;
public class PotionEffectTest {
    @Test
    public void testPotionEffectLastsFiveTurns() {
        Player player = new Player(2, 2);
        Map map = new Map();
        BattleMap battleMap = new BattleMap(map, player, List.of(), "👻");
        battleMap.activatePotionEffect(true); // Активируем эффект

        for (int i = 0; i < 5; i++) {
            battleMap.decreasePotionEffectTurns();
        }

        assertFalse(battleMap.isPotionEffectActive());
        assertEquals(0, battleMap.getPotionEffectTurns());
    }
}