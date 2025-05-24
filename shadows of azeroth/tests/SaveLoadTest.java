package tests;

import entities.Player;
import maps.Map;
import other_things.Diologies;
import other_things.Quests;
import other_things.BossBattle;
import save.SaveManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class SaveLoadTest {
    private Player player;
    private Map map;
    private Diologies dialogs;
    private Quests quests;
    private BossBattle bossBattle;

    @BeforeEach
    public void setUp() {
        // Инициализация всех компонентов игры
        player = new Player(2, 2);
        player.setUsername("pomogite");
        player.setHealth(150);
        player.setDmg(75);
        player.addCoins(100);
        player.setHealingPotion(1);
        player.setBoostDmgPotion(1);
        player.setHasArtifact(true);

        // Инициализация карты
        map = new Map();
        player.setCurrentMapType(Player.MapType.OGRE_LANDS);

        // Инициализация диалогов
        dialogs = new Diologies();
        dialogs.setThrallStage(1); // Тралл на стадии 1
        dialogs.setSmithStage(2); // Кузнец на стадии 2

        // Инициализация квеста
        quests = new Quests(new Scanner(System.in));
        quests.setQuestState(3); // Квест завершён

        // Инициализация босса
        bossBattle = new BossBattle();
        bossBattle.setBossHealth(0); // Босс повержен
        bossBattle.setStunUsed(true);
        bossBattle.setExhaustionUsed(false);
    }

    @Test
    public void testSaveAndLoadGameRestoresAllData() {
        // Сохраняем игру
        SaveManager.saveGame(player, map, dialogs, quests, bossBattle, "1");

        // Создаём новые объекты для загрузки
        Player loadedPlayer = new Player(0, 0);
        loadedPlayer.setUsername("pomogite");
        Map loadedMap = new Map();
        Diologies loadedDialogs = new Diologies();
        Quests loadedQuests = new Quests(new Scanner(System.in));
        BossBattle loadedBoss = new BossBattle();

        // Загружаем игру
        boolean success = SaveManager.loadGame(loadedPlayer, loadedMap, loadedDialogs, loadedQuests, loadedBoss,"1");
        assertTrue(success);

        // Проверяем, что данные восстановились
        assertEquals(player.getX(), loadedPlayer.getX());
        assertEquals(player.getY(), loadedPlayer.getY());
        assertEquals(player.getUsername(), loadedPlayer.getUsername());
        assertEquals(player.getHealth(), loadedPlayer.getHealth());
        assertEquals(player.getDmg(), loadedPlayer.getDmg());
        assertEquals(player.getCoins(), loadedPlayer.getCoins());
        assertEquals(player.isHasArtifact(), loadedPlayer.isHasArtifact());

        // Проверяем карту
        assertEquals(map.getCurrentMapMaxX(player.getCurrentMapType()), loadedMap.getCurrentMapMaxX(loadedPlayer.getCurrentMapType()));
        assertEquals(map.getCurrentMapMaxY(player.getCurrentMapType()), loadedMap.getCurrentMapMaxY(loadedPlayer.getCurrentMapType()));

        // Проверяем диалоги
        assertEquals(dialogs.getThrallStage(), loadedDialogs.getThrallStage());
        assertEquals(dialogs.getSmithStage(), loadedDialogs.getSmithStage());

        // Проверяем квест
        assertEquals(quests.getQuestState(), loadedQuests.getQuestState());


    }

    @Test
    public void testLoadNonExistentSaveReturnsFalse() {
        Player player = new Player(0, 0);
        player.setUsername("nonexistent");
        Map map = new Map();
        Diologies dialogs = new Diologies();
        Quests quests = new Quests(new Scanner(System.in));
        BossBattle boss = new BossBattle();

        boolean result = SaveManager.loadGame(player, map, dialogs, quests, boss, "1");
        assertFalse(result); // Загрузка не должна пройти
    }
}