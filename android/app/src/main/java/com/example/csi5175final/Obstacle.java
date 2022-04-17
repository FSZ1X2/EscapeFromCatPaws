package com.example.csi5175final;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Obstacle implements GameObject {
    private Rect rectangle;
    private Bitmap paw;
    private boolean loaded = false;


    private void scaleRect(Rect rect) {
        float whRatio = (float)(paw.getWidth())/paw.getHeight();
        if(rect.width() > rect.height())
            rect.left = rect.right - (int)(rect.height() * whRatio);
        else
            rect.top = rect.bottom - (int)(rect.width() * (1/whRatio));
    }

    public Rect getRectangle() {
        return rectangle;
    }

    /**
     * function for making cat paws going down to the bottom.
     *
     * @param y the changes on Y-axis for cat paws position.
     */
    public void incrementY(float y) {
        rectangle.top += y;
        rectangle.bottom +=y;
    }

    /**
     * function for creating new random cat paws.
     *
     * @param rectHeight the changes on Y-axis for cat paws position.
     * @param startX the X-axis for cat paws position.
     * @param startY the Y-axis for cat paws position.
     * @param playerGap use this param to make sure player will have a chance to avoid cat paws.
     */
    public Obstacle(int rectHeight, int startX, int startY, int playerGap) {
        rectangle = new Rect(startX, startY, startX + playerGap, startY + rectHeight);

        BitmapFactory bf = new BitmapFactory();

        // randomly pick one default color from local.
        paw = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.nekotmp1);
        switch (new Random().nextInt(5)){
            case 0:paw = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.nekotmp1); break;
            case 1:paw = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.nekotmp2); break;
            case 2:paw = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.nekotmp3); break;
            case 3:paw = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.nekotmp4); break;
            case 4: if(Constants.db != null) paw = Constants.db;  break;
        }


    }

    /**
     * function for checking if player hit the cat paw.
     *
     * @param player game object for player.
     */
    public boolean playerCollide(Player player) {
        return Rect.intersects(rectangle, player.getRectangle());
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        scaleRect(rectangle);
        canvas.drawBitmap(paw, null, rectangle, paint);
    }

    public void update() {

    }



}
