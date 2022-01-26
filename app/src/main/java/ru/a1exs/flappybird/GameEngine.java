package ru.a1exs.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

public class GameEngine implements View.OnClickListener, SurfaceHolder.Callback {

    private final GameEngineTicker ticker = new GameEngineTicker();
    private final GameModel gameModel = new GameModel();
    private final BitmapHolder bitmapHolder;
    private final Paint paint = new Paint();
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
        final GameState gameState = gameModel.getCurrentState();

        canvas.drawColor(Color.BLACK);

        drawBird(canvas, gameState);
        drawPipes(canvas, gameState);


        holder.unlockCanvasAndPost(canvas);
    }

    private void drawPipes(Canvas canvas, GameState gameState) {
        final GameState.PipeModel[] pipes = gameState.getPipes();

        for (GameState.PipeModel pipe : pipes) {
            Rect pipeUp = new Rect(
                    (int) (pipe.x - GameState.PipeModel.width / 2),
                    0,
                    (int) (pipe.x + GameState.PipeModel.width / 2),
                    (int) (pipe.y - pipe.spaceHeight / 2)
            );
            Rect pipeDown = new Rect(
                    (int) (pipe.x - GameState.PipeModel.width / 2),
                    (int) (pipe.y + pipe.spaceHeight / 2),
                    (int) (pipe.x + GameState.PipeModel.width / 2),
                    canvas.getHeight()
            );

            canvas.drawBitmap(bitmapHolder.pipeGreen, null, pipeUp, paint);
            canvas.drawBitmap(bitmapHolder.pipeGreen, null, pipeDown, paint);
        }
    }

    private void drawBird(Canvas canvas, GameState gameState) {
        Bitmap bird = null;

        switch (gameState.getBirdFlap()) {
            case UP:
                bird = bitmapHolder.birdUp;
                break;
            case MID:
                bird = bitmapHolder.birdMid;
                break;
            case DOWN:
                bird = bitmapHolder.birdDown;
                break;
        }

        canvas.drawBitmap(bird, null, gameState.getBirdRect(), paint);
    }

    @Override
    public void onClick(View view) {
        gameModel.onTap();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        this.holder = holder;
        ticker.start();

        final Rect size = holder.getSurfaceFrame();
        gameModel.height = size.height();
        gameModel.width = size.width();
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
