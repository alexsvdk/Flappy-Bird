package ru.a1exs.flappybird;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapHolder {

    public Bitmap birdDown;
    public Bitmap birdMid;
    public Bitmap birdUp;

    public Bitmap pipeGreen;

    void init(Context context){
        final Resources res = context.getResources();

        birdDown = BitmapFactory.decodeResource(res, R.drawable.bluebird_downflap);
        birdMid = BitmapFactory.decodeResource(res, R.drawable.bluebird_midflap);
        birdUp = BitmapFactory.decodeResource(res, R.drawable.bluebird_upflap);

        pipeGreen = BitmapFactory.decodeResource(res, R.drawable.pipe_green);

    }

}
