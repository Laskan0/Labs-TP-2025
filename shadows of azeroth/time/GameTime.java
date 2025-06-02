package time;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameTime {
    private int hour = 21;     // Начинается с 9:00
    private int minute = 55;
    private boolean running = true;

    private final List<TimeChangeListener> listeners = new ArrayList<>();//список слушателей

    public interface TimeChangeListener {
        default void onTimeUpdate(int hour, int minute) {

        }
    }

    public void addListener(TimeChangeListener listener) {
        listeners.add(listener);
    }

    public void start() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);//создаёт пул из одного потока 9именно один поток гарантирует последовательное изменение времени)
        scheduler.scheduleAtFixedRate(() -> {//планирует задачу с фиксированной частотой
            minute += 1;
            if (minute >= 60) {
                minute = 0;
                hour += 1;
                if (hour >= 24) hour = 0;
            }
            notifyListeners();
        }, 0, 1000, TimeUnit.MILLISECONDS); // 1 минута реального времени = 1 час игрового времени
    }

    private void notifyListeners() {
        for (TimeChangeListener listener : listeners) {
            listener.onTimeUpdate(hour, minute);
        }//уведомление слушателей, иетрация по всем подписчикам и вызов их метода, выполняется в том же потоке что и обновление времени
    }

    public boolean isWithinWorkingHours(int startHour, int endHour) {
        return hour >= startHour && hour < endHour;
    }
    //проверка времени
    public boolean isBetween(int startHour, int startMin, int endHour, int endMin) {
        int currentMinutes = hour * 60 + minute;//текущее время в минутах
        int startTotal = startHour * 60 + startMin;//начало диапозона в минутах
        int endTotal = endHour * 60 + endMin;//конец диапазона в минутах
        return currentMinutes >= startTotal && currentMinutes < endTotal;
    }//проверка времени, конвертирует в минуты

    public String getCurrentTime() {
        return String.format("%02d:%02d", hour, minute);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
}