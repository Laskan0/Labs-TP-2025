package tests;

import buildings.Totem;
import entities.Player;
import maps.Map;
import org.junit.jupiter.api.Test;
import time.GameTime;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class TotemTest extends BaseTest {

    @Test
    void testTotem_HealthRestoredAfterDelay() throws InterruptedException {
        // Подготовка
        GameTime gameTime = createRealGameTime();
        Totem totem = new Totem(gameTime);
        Player player = createTestPlayer();
        Map map = createTestMap();

        // Имитируем ввод "1" (да)
        String input = "1\n";
        InputStream mockIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(mockIn);

        player.setCoins(10); // Достаточно монет

        // Вызов тестируемого метода
        totem.interact(player, map);

        CountDownLatch latch = new CountDownLatch(1);

        Thread.sleep(3500); // Очередь
        Thread.sleep(5500); // Задержка 5 секунд

        // Проверки
        assertTrue(player.getHealth() > 100, "Здоровье должно увеличиться на 20");
        assertEquals(0, totem.getCurrentQueueSize(), "Очередь должна быть пустой");

        latch.countDown();
        assertTrue(latch.await(1, TimeUnit.SECONDS));
    }
}