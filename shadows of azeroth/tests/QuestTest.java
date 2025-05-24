package tests;

import static org.junit.jupiter.api.Assertions.*;

import maps.Map;
import other_things.Quests;
import entities.Player;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

public class QuestTest {
    @Test
    public void testQuestCompletesOnlyWithAllCorrectAnswers() {
        Quests quests = new Quests(new Scanner("эхо\nзеркало\nпустая\n"));
        Player player = new Player(0, 0);
        boolean result = quests.startFirstQuest(player, new Map());
        assertTrue(result);
        assertTrue(player.isHasArtifact());
    }

    @Test
    public void testQuestFailsWithWrongAnswer() {
        Quests quests = new Quests(new Scanner("неверно\n2\nневерно\n2\nневерно\n2"));
        Player player = new Player(0, 0);
        boolean result = quests.startFirstQuest(player, new Map());
        assertFalse(result);
        assertFalse(player.isHasArtifact());
    }
}