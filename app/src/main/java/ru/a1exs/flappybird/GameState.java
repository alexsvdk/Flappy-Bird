package ru.a1exs.flappybird;

import android.graphics.Rect;

public interface GameState {

    enum BirdFlap {
        UP, MID, DONW
    }

    /// Птичка
    public Rect getBirdRect();

    public double getBirdAngle();

    public BirdFlap getBirdFlap();


}
