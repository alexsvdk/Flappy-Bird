package ru.a1exs.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.SurfaceView;

public class MainActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    BitmapHolder bitmapHolder = new BitmapHolder();
    GameEngine gameEngine = new GameEngine(bitmapHolder);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bitmapHolder.init(this);
        surfaceView = findViewById(R.id.surface);
        gameEngine.attachToSurface(surfaceView);
    }
}