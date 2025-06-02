package buildings;

import entities.Player;
import maps.Map;
import time.GameTime;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

public class WarriorTower {
    private final int cost = 30; // Цена взаимодействия
    private final GameTime gameTime;
    private final Random random = new Random();
    private boolean isProcessingQueue = false;
    private int currentQueueSize = 0;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    private volatile boolean interactionActive = false;
    private ScheduledFuture<?> queueTaskFuture = null;

    // Рабочее время башни: с 10:00 до 22:00
    private final int workStartHour = 10;
    private final int workEndHour = 22;

    public WarriorTower(GameTime gameTime, Map map, Player player) {
        this.gameTime = gameTime;
        scheduleTimeCheck(map, player); // Проверяем закрытие каждую секунду
    }

    public synchronized void interact(Player player, Map map) {

        if (!canWorkNow()) {
            System.out.println("Башня воинов сейчас не работает.");
            map.displayCurrentMap(player);
            return;
        }

        if (interactionActive) {
            System.out.println("Вы уже взаимодействуете с башней.");
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
            System.out.println("Вы вышли из башни воинов.");
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

                // Ждём 1 секунду перед выдачей бонуса
                scheduler.schedule(() -> {
                    if (!interactionActive) return;

                    if (player.getCoins() >= cost) {
                        player.setDmg(player.getDmg() + 15);
                        player.addCoins(-cost);
                        System.out.println("Вы получили +15 к урону!");
                    } else {
                        System.out.println("Недостаточно монет. Нужно 30 монет.");
                    }

                    interactionActive = false;
                    map.displayCurrentMap(player);
                    System.out.println("\nУправляй: w/a/s/d (ход), 1 (атака), 2 (сплеш), p (состояние), save (сохранить), load (загрузить), records (рекорды)");

                }, 1, TimeUnit.SECONDS);
            }
        }, 0, 3, TimeUnit.SECONDS);
    }

    // Проверка каждую секунду: если время вышло — выгоняем игрока
    private void scheduleTimeCheck(Map map, Player player) {
        scheduler.scheduleAtFixedRate(() -> {
            if (!canWorkNow() && interactionActive) {
                System.out.println("Башня воинов временно не работает... Вас отозвали.");

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