package ru.a1exs.flappybird;

import android.graphics.Rect;

public class GameModel {

    private static final double v = 60;
    private static final double g = 130;

    int width = 1000;
    int height = 2000;


    private long lastTapTime = 0;
    private double lastTapY = 50;

    GameState getCurrentState() {
        return new GameStateImpl();
    }

    void onTap() {
        lastTapY = calculateHeight();
        lastTapTime = System.currentTimeMillis();
    }

    private double calculateHeight() {
        if (lastTapTime == 0) {
            return 50;
        }
        final double timeDiff = (System.currentTimeMillis() - lastTapTime) / 1000.0;
        return lastTapY + v * timeDiff - g * (timeDiff * timeDiff) / 2;
    }

    private class GameStateImpl implements GameState {

        Rect birdRect;
        BirdFlap birdFlap;

        public GameStateImpl() {

            final long time = System.currentTimeMillis();
            final double birdY = calculateHeight();

            if (time % 1000 < 250) {
                birdFlap = BirdFlap.DOWN;
            } else if (time % 1000 < 500) {
                birdFlap = BirdFlap.MID;
            } else if (time % 1000 < 750) {
                birdFlap = BirdFlap.UP;
            } else {
                birdFlap = BirdFlap.MID;
            }

            final int centerX = width / 2;
            final int centerY = (int) (height / 100.0 * (100 - birdY));

            final int birdWidth = 168;
            final int birdHeight = 118;

            birdRect = new Rect(
                    centerX - birdWidth / 2,
                    centerY - birdHeight / 2,
                    centerX + birdWidth / 2,
                    centerY + birdHeight / 2
            );

        }

        @Override
        public Rect getBirdRect() {
            return birdRect;
        }

        @Override
        public double getBirdAngle() {
            return 0;
        }

        @Override
        public BirdFlap getBirdFlap() {
            return birdFlap;
        }
    }
}
