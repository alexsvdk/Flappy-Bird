package ru.a1exs.flappybird;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

public class GameEngine implements View.OnClickListener, SurfaceHolder.Callback {

    final private GameEngineTicker ticker = new GameEngineTicker();
    private final BitmapHolder bitmapHolder;
    private SurfaceHolder holder;

    public GameEngine(BitmapHolder bitmapHolder) {
        this.bitmapHolder = bitmapHolder;
    }

    public void attachToSurface(SurfaceView surfaceView) {
        surfaceView.setOnClickListener(this);
        surfaceView.getHolder().addCallback(this);
    }

    private void drawFrame() {
        if (holder == null) {
            return;
        }
        final Canvas canvas = holder.lockCanvas();

        Paint paint = new Paint();
        canvas.drawBitmap(bitmapHolder.birdMid, 400, 400, paint);

        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        this.holder = holder;
        ticker.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int i, int i1, int i2) {
        this.holder = holder;
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        this.holder = null;
        ticker.shouldStop = true;
    }

    private class GameEngineTicker extends Thread {
        final int fps = 60;
        boolean isRunning = false;
        boolean shouldStop = false;

        @Override
        public void run() {
            isRunning = true;
            while (!shouldStop) {
                drawFrame();
                try {
                    Thread.sleep(1000 / fps);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isRunning = false;
            shouldStop = false;
        }
    }
}
