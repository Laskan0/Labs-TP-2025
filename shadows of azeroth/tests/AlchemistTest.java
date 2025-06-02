package tests;


import buildings.Alchemist;
import entities.Player;
import maps.Map;
import org.junit.jupiter.api.Test;
import time.GameTime;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class AlchemistTest extends BaseTest {

    @Test
    void testAlchemist_GivesPotionsAfterDelay() throws InterruptedException {
        // Подготовка
        GameTime gameTime = createRealGameTime();

        Player player = createTestPlayer();
        Map map = createTestMap();
        Alchemist alchemist = new Alchemist(gameTime,player,map);

        // Имитируем ввод "1" (да)
        String input = "1\n";  // "\n" — имитация нажатия Enter
        InputStream mockIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(mockIn);

        player.setCoins(25); // Достаточно монет

        // Вызов тестируемого метода
        alchemist.interact(player, map);

        CountDownLatch latch = new CountDownLatch(1);

        Thread.sleep(3500); // Очередь
        Thread.sleep(5500); // Задержка 5 секунд

        // Проверки
        assertTrue(player.getHealingPotion() >= 1 && player.getBoostDmgPotion() >= 1,
                "Игрок должен получить хотя бы по одному зелью");
        assertEquals(0, alchemist.getCurrentQueueSize(), "Очередь должна быть пустой");

        latch.countDown();
        assertTrue(latch.await(1, TimeUnit.SECONDS));
    }

}