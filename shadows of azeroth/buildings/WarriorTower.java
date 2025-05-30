package buildings;

import entities.Player;
import maps.Map;
import time.GameTime;
import save.SaveManager;
import java.util.concurrent.*;
import java.util.Random;
import java.util.Scanner;

public class WarriorTower {
    private final int cost = 30; // Цена взаимодействия
    private final GameTime gameTime;
    private final Random random = new Random();
    private boolean isProcessingQueue = false;
    private int currentQueueSize = 0;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public WarriorTower(GameTime gameTime) {
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
            System.out.println("Вы вышли из башни.");
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
                System.out.println("Теперь вы можете взаимодействовать с башней воинов.");

                // Сохраняем время начала ожидания
                int cur_min = gameTime.getMinute();

                // Ждём 5 секунд реального времени перед выдачей бонуса
                scheduler.schedule(() -> {
                    if (player.getCoins() >= cost) {
                        player.setDmg(player.getDmg() + 15);
                        player.addCoins(-cost);
                        System.out.println("Вы получили +15 к урону!");

                    } else {
                        System.out.println("Недостаточно монет. Нужно 30 монет.");

                    }

                    map.displayCurrentMap(player);
                    System.out.println("\nУправляй: w/a/s/d (ход), 1 (атака), 2 (сплеш), p (состояние), save (сохранить), load (загрузить), records (рекорды)");

                }, 1, TimeUnit.SECONDS); // Ждём 5 секунд

                ((ScheduledThreadPoolExecutor) scheduler).shutdown(); // Завершаем пул после выполнения
            }
        }, 0, 3, TimeUnit.SECONDS); // Обработка очереди каждые 3 секунды
    }

    public int getCurrentQueueSize() {
        return currentQueueSize;
    }

}