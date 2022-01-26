package ru.a1exs.flappybird;

import android.graphics.Rect;

public interface GameState {

    enum BirdFlap {
        UP, MID, DOWN
    }

    /// Птичка
    public Rect getBirdRect();

    public double getBirdAngle();

    public BirdFlap getBirdFlap();


    /// Трубы
    public PipeModel[] getPipes();

    public static class PipeModel {

        static final double width = 200;

        final double x;
        final double y;
        final double spaceHeight;

        public PipeModel(double x, double y, double spaceHeight) {
            this.x = x;
            this.y = y;
            this.spaceHeight = spaceHeight;
        }
    }

}
