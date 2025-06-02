package buildings;

import entities.Player;
import maps.Map;
import time.GameTime;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

public class Totem {
    private final int cost = 10; // Цена взаимодействия

    private final GameTime gameTime;
    private final Random random = new Random();
    private boolean isProcessingQueue = false;
    private int currentQueueSize = 0;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    private volatile boolean interactionActive = false;
    private ScheduledFuture<?> queueTaskFuture = null;

    // Рабочие часы тотема (локальное "время работы", например с 7 до 20)
    private final int workStartHour = 7;
    private final int workEndHour = 20;

    public Totem(GameTime gameTime, Map map, Player player) {
        this.gameTime = gameTime;
        scheduleTimeCheck(map, player); // Проверяем закрытие каждую секунду
    }

    public synchronized void interact(Player player, Map map) {

        if (!canWorkNow()) {
            System.out.println("Тотем сейчас не работает.");
            map.displayCurrentMap(player);
            return;
        }

        if (interactionActive) {
            System.out.println("Вы уже взаимодействуете с тотемом.");
            return;
        }

        interactionActive = true;

        if (!isProcessingQueue) {
            currentQueueSize = random.nextInt(4); // Очередь от 0 до 3
            isProcessingQueue = true;
        }

        System.out.println("Перед вами " + currentQueueSize + " человек в очереди. Хотите подождать? (1 - да, 2 - нет)");
        String choice = new Scanner(System.in).nextLine();

        if ("1".equals(choice)) {
            startQueueProcessing(player, map);
        } else {
            System.out.println("Вы ушли от тотема.");
            interactionActive = false;
            map.displayCurrentMap(player);
        }
    }

    // Проверяет, работает ли здание сейчас
    private boolean canWorkNow() {
        return gameTime.isWithinWorkingHours(workStartHour, workEndHour);
    }

    private void startQueueProcessing(Player player, Map map) {
        queueTaskFuture = scheduler.scheduleAtFixedRate(() -> {
            if (!interactionActive) {
                queueTaskFuture.cancel(true);
                return;
            }

            if (currentQueueSize > 0) {
                currentQueueSize--;
                System.out.println("Очередь уменьшилась. Осталось: " + currentQueueSize);
            }

            if (currentQueueSize == 0) {
                isProcessingQueue = false;
                queueTaskFuture.cancel(true);

                // Ждём 5 секунд перед восстановлением HP
                scheduler.schedule(() -> {
                    if (!interactionActive) return;

                    if (player.getCoins() >= cost) {
                        player.setHealth(player.getHealth() + 20);
                        player.addCoins(-cost);
                        System.out.println("Вы получили +20 HP!");
                    } else {
                        System.out.println("Недостаточно монет. Нужно 10 монет.");
                    }

                    interactionActive = false;
                    map.displayCurrentMap(player);
                    System.out.println("\nУправляй: w/a/s/d (ход), 1 (атака), 2 (сплеш), p (состояние), save (сохранить), load (загрузить), records (рекорды)");

                }, 5, TimeUnit.SECONDS);
            }
        }, 0, 3, TimeUnit.SECONDS);
    }

    // Проверка времени каждую секунду
    private void scheduleTimeCheck(Map map,Player player) {
        scheduler.scheduleAtFixedRate(() -> {
            if (!canWorkNow() && interactionActive) {
                System.out.println("Тотем временно не работает... Вас отозвали.");

                if (queueTaskFuture != null) {
                    queueTaskFuture.cancel(true);
                }

                interactionActive = false;

                scheduler.schedule(() -> map.displayCurrentMap(player), 1, TimeUnit.SECONDS);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    public int getCurrentQueueSize() {
        return currentQueueSize;
    }
}