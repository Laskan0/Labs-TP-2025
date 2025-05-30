package buildings;

import entities.Player;
import maps.Map;
import time.GameTime;
import save.SaveManager;
import java.util.concurrent.*;
import java.util.Random;
import java.util.Scanner;

public class Totem {
    private final int cost = 10; // Цена взаимодействия
    private final GameTime gameTime;
    private final Random random = new Random();
    private boolean isProcessingQueue = false;
    private int currentQueueSize = 0;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public Totem(GameTime gameTime) {
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
            System.out.println("Вы ушли от тотема.");
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
                System.out.println("Теперь вы можете взаимодействовать с тотемом.");

                // Сохраняем начальное время
                int cur_min = gameTime.getMinute();

                // Запускаем задачу, которая будет ждать 5 игровых минут (реальных секунд)
                scheduler.schedule(() -> {
                    if (player.getCoins() >= cost) {
                        player.setHealth(player.getHealth() + 20);
                        player.addCoins(-cost);
                        System.out.println("Вы получили +20 HP!");

                    } else {
                        System.out.println("Недостаточно монет. Нужно 10 монет.");

                    }

                    map.displayCurrentMap(player);
                    System.out.println("\nУправляй: w/a/s/d (ход), 1 (атака), 2 (сплеш), p (состояние), save (сохранить), load (загрузить), records (рекорды)");

                }, 5, TimeUnit.SECONDS);

                ((ScheduledThreadPoolExecutor) scheduler).shutdown();
            }
        }, 0, 3, TimeUnit.SECONDS); // Обработка очереди каждые 3 секунды
    }
    public int getCurrentQueueSize() {
        return currentQueueSize;
    }
}