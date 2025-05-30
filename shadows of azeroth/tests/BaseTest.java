package  tests;

import entities.Player;
import maps.Map;
import time.GameTime;

public class BaseTest {
    protected Player createTestPlayer() {
        return new Player(0, 0);
    }

    protected Map createTestMap() {
        return new Map();
    }

    protected GameTime createTestGameTime(int initialMinute) {
        return new GameTime() {
            @Override
            public int getMinute() {
                return initialMinute;
            }
        };
    }

    protected GameTime createRealGameTime() {
        return new GameTime();
    }
}