package buildings;

import entities.Player;
import maps.Map;
import time.GameTime;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

public class Alchemist {
    private final int cost = 25; // Цена взаимодействия
    private final GameTime gameTime;
    private final Random random = new Random();
    private boolean isProcessingQueue = false;
    private int currentQueueSize = 0;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2); // 2 потока: очередь + время

    private volatile boolean interactionActive = false; //Происходит ли взаимодействие с алхимиком
    private ScheduledFuture<?> queueTaskFuture = null; //Объявляем интерфейс хранения задачи(присвоим значение позже)

    public Alchemist(GameTime gameTime, Player player, Map map) {
        this.gameTime = gameTime;
        scheduleTimeCheck(map,player); // Проверка времени каждую секунду
    }

    public synchronized void interact(Player player, Map map) {

        if (!canWorkNow()) {
            System.out.println("Алхимик сейчас не работает.");
            map.displayCurrentMap(player);
            return;
        }

        if (interactionActive) {
            System.out.println("Вы уже взаимодействуете с алхимиком.");
            return;
        }

        interactionActive = true; //Начали взаимодействие с алхимиком

        if (!isProcessingQueue) {
            currentQueueSize = random.nextInt(4); // Очередь от 0 до 3
            isProcessingQueue = true;
        }

        System.out.println("Перед вами " + currentQueueSize + " человек в очереди. Хотите подождать? (1 - да, 2 - нет)");
        String choice = new Scanner(System.in).nextLine();

        if ("1".equals(choice)) {
            startQueueProcessing(player, map);

        } else {
            System.out.println("Вы ушли от алхимика.");
            interactionActive = false;
            map.displayCurrentMap(player);
        }
    }

    private boolean canWorkNow() {
        int currentHour = gameTime.getHour();
        return currentHour >= 9 && currentHour < 18;
    }

    private void startQueueProcessing(Player player, Map map) {
        queueTaskFuture = scheduler.scheduleAtFixedRate(() -> { //Присвоили интерфейсу задачу для отслеживания очереди. Если алхимик зачупапится во время очереди то нас выгонят(т.е. отменяем задачу)
            if (!interactionActive) {
                queueTaskFuture.cancel(true);//если взаимодействие с алхимиком прервано, то отменяем задачу отслеживания очереди
                return;
            }

            if (currentQueueSize > 0) {
                currentQueueSize--;
                System.out.println("Очередь уменьшилась. Осталось: " + currentQueueSize);
            }

            if (currentQueueSize == 0) {
                isProcessingQueue = false;
                queueTaskFuture.cancel(true);//прождали очередь - отменили задачу обработки очереди.

                // Ждём 7 секунд перед выдачей зелий
                scheduler.schedule(() -> {
                    if (!interactionActive) return;

                    if (player.getCoins() >= cost) {
                        player.setBoostDmgPotion(player.getBoostDmgPotion() + 1);
                        player.setHealingPotion(player.getHealingPotion() + 1);
                        player.addCoins(-cost);
                        System.out.println("Вы получили зелья!");
                    } else {
                        System.out.println("Недостаточно монет. Нужно 25 монет.");
                    }

                    interactionActive = false;
                    map.displayCurrentMap(player);
                    System.out.println("\nУправляй: w/a/s/d (ход), 1 (атака), 2 (сплеш), p (состояние), save (сохранить), load (загрузить), records (рекорды)");

                }, 7, TimeUnit.SECONDS);
            }
        }, 0, 3, TimeUnit.SECONDS);
    }

    private void scheduleTimeCheck(Map map, Player player) { // Проверяем не откинулся ли алхимик
        scheduler.scheduleAtFixedRate(() -> {
            if (gameTime.getHour() >= 18 && interactionActive) {
                System.out.println("Алхимик закрывается... Вас выгнали.");



                if (queueTaskFuture != null) {
                    queueTaskFuture.cancel(true); // Если откинулся, то отменяем задачу
                }

                interactionActive = false;

                // Отправляем игрока обратно на карту через 1 секунду
                System.out.println("\nУправляй: w/a/s/d (ход), 1 (атака), 2 (сплеш), p (состояние), save (сохранить), load (загрузить), records (рекорды)");
                scheduler.schedule(() -> map.displayCurrentMap(player), 1, TimeUnit.SECONDS);
                scheduler.shutdown();
            }
        }, 0, 1, TimeUnit.SECONDS); // Каждую секунду проверяем время
    }
    public int getCurrentQueueSize() {
        return currentQueueSize;
    }
    public void shutdown() {
        scheduler.shutdown(); // Завершаем пул, дождавшись окончания активных задач
    }
}