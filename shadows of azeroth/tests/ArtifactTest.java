package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import other_things.Quests;
import entities.Player;
import maps.Map;

import java.util.Scanner;


public class ArtifactTest {
    @Test
    public void testQuestCompletionGrantsArtifact() {
        Player player = new Player(0, 0);
        Quests quests = new Quests(new Scanner("эхо\nзеркало\nпустая\n"));
        boolean result = quests.startFirstQuest(player, new Map());
        assertTrue(result); // Квест пройден
        assertTrue(player.isHasArtifact()); // Артефакт получен
    }
}