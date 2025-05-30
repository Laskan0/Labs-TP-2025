package buildings;

import entities.Player;
import maps.Map;
import time.GameTime;
import save.SaveManager;
import java.util.concurrent.*;
import java.util.Random;
import java.util.Scanner;

public class Alchemist {
    private final int cost = 25; // Цена взаимодействия
    private final GameTime gameTime;
    private final Random random = new Random();
    private boolean isProcessingQueue = false;
    private int currentQueueSize = 0;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public Alchemist(GameTime gameTime) {
        this.gameTime = gameTime;
    }

    public synchronized void interact(Player player, Map map) {
        if (!isProcessingQueue) {
            currentQueueSize = new Random().nextInt(4); // Очередь от 0 до 3 человек
            isProcessingQueue = true;
        }

        System.out.println("Перед вами " + currentQueueSize + " человек в очереди. Хотите подождать? (1 - да, 2 - нет)");
        String choice = new Scanner(System.in).nextLine();

        if ("1".equals(choice)) {
            startQueueProcessing(player, map);
        } else {
            System.out.println("Вы ушли от алхимика.");
            map.displayCurrentMap(player);
        }
    }

    private void startQueueProcessing(Player player, Map map) {
        scheduler.scheduleAtFixedRate(() -> {
            if (currentQueueSize > 0) {
                currentQueueSize--;
                System.out.println("Очередь уменьшилась. Осталось: " + currentQueueSize);
            }

            if (currentQueueSize == 0) {
                isProcessingQueue = false;
                System.out.println("Теперь вы можете взаимодействовать с алхимиком.");

                // Запоминаем текущее игровое время
                int cur_min = gameTime.getMinute();

                // Ждём 5 секунд, прежде чем дать зелья
                scheduler.schedule(() -> {
                    if (player.getCoins() >= cost) {
                        player.setBoostDmgPotion(player.getBoostDmgPotion() + 1);
                        player.setHealingPotion(player.getHealingPotion() + 1);
                        player.addCoins(-cost);
                        System.out.println("Вы получили зелья!");

                    } else {
                        System.out.println("Недостаточно монет. Нужно 25 монет.");

                    }

                    map.displayCurrentMap(player);
                    System.out.println("\nУправляй: w/a/s/d (ход), 1 (атака), 2 (сплеш), p (состояние), save (сохранить), load (загрузить), records (рекорды)");

                }, 7, TimeUnit.SECONDS); // Ждём 5 секунд

                ((ScheduledThreadPoolExecutor) scheduler).shutdown(); // Останавливаем пул после выполнения
            }
        }, 0, 3, TimeUnit.SECONDS); // Обработка очереди каждые 3 секунды
    }
    public int getCurrentQueueSize() {
        return currentQueueSize;
    }
}