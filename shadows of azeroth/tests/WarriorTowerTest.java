package tests;

import buildings.WarriorTower;
import entities.Player;
import maps.Map;
import org.junit.jupiter.api.Test;
import time.GameTime;

import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.io.StringReader;
import static org.junit.jupiter.api.Assertions.*;

class WarriorTowerTest extends BaseTest {


    @Test
    void testWarriorTower_BonusAppliedAfterDelay() throws InterruptedException {
        // Подготовка
        GameTime gameTime = createRealGameTime();
        WarriorTower tower = new WarriorTower(gameTime);
        Player player = createTestPlayer();
        Map map = createTestMap();

        // Имитируем ввод "1" (да)
        String input = "1\n";  // "\n" — имитация нажатия Enter
        InputStream mockIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(mockIn); // Теперь всё верно!

        player.setCoins(30); // Достаточно монет

        // Вызов тестируемого метода
        tower.interact(player, map);

        CountDownLatch latch = new CountDownLatch(1);

        Thread.sleep(3500); // Очередь
        Thread.sleep(5500); // Задержка 5 секунд
        System.out.println(player.getDmg());
        // Проверки
        assertTrue(player.getDmg() >= 65, "Урон должен увеличиться на 15");
        assertEquals(0, tower.getCurrentQueueSize(), "Очередь должна быть пустой");

        latch.countDown();
        assertTrue(latch.await(1, TimeUnit.SECONDS));
    }
}