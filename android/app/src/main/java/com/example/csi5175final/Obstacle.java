package com.example.csi5175final;

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
//    //determine which side the cat paws will appear
//    // left:0 and right:1
//    private int LoR = new Random().nextInt(2);
    private Bitmap paw;
    private AppCompatActivity activity;


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
        if(MainActivity.instance != null){
            activity = MainActivity.instance;
        } else {
            activity = PlayGame.instance;
        }

//        if(LoR==0)
            rectangle = new Rect(startX, startY, startX + playerGap, startY + rectHeight);
//        else
//            rectangle = new Rect(startX + playerGap, startY, Constants.SCREEN_WIDTH, startY + rectHeight);

        //TODO: randomly get cat paws from database
        BitmapFactory bf = new BitmapFactory();
        paw = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.nekotype1);


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


    private void getImage(String id){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                String results = "";
                try{
                    String url = "https://heroic-arbor-303003.uc.r.appspot.com/image?id=" + id;
                    InputStream stream = null;
                    URL urlObj = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
                    conn.setDoInput(true);
                    conn.setRequestMethod("GET");


                    conn.connect();

                    stream = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
                    results = reader.readLine();


                } catch (Exception e) {
                    e.printStackTrace();
                }

                String finalResults = results;
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        String[] formatted = finalResults.split(",");
                        int color1 = Integer.parseInt(formatted[0]);
                        int color2 = Integer.parseInt(formatted[1]);
                        int color3 = Integer.parseInt(formatted[2]);
                        int selection = Integer.parseInt(formatted[3]);
                        int picker = Integer.parseInt(formatted[4]);
                        int picker2 = Integer.parseInt(formatted[5]);

                        generateBitMap(color1,color2,color3,selection,picker,picker2);
                    }
                });
            }
        });
    }

    private Bitmap generateBitMap(int color1, int color2, int color3, int selection, int picker, int picker2){
        Drawable paw = getDrawable(R.drawable.nekotype1);

        switch (selection){
            case 1: paw = getDrawable(R.drawable.nekotype1);
            case 2: paw = getDrawable(R.drawable.nekotype2);
            case 3: paw = getDrawable(R.drawable.nekotype3);
            case 4: paw = getDrawable(R.drawable.nekotype4);
        }
        Bitmap bmp = ((BitmapDrawable) paw).getBitmap();
        android.graphics.Bitmap.Config bitmapConfig = bmp.getConfig();
        // set default bitmap config if none
        if(bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // convert resource bitmaps are to mutable one
        bmp = bmp.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bmp);
        //add color changes for type1 paws
        int[] colorIndex= {color1, color2, color3};
        Paint paint = new Paint();
        //add color

        if(selection < 4){
            int colorID = picker;
            paint.setColor(colorIndex[colorID]);
            int c1X = bmp.getWidth()/4;
            int c1Y = bmp.getHeight()/2;
            int c1R = bmp.getWidth()/3;
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            canvas.drawCircle(c1X, c1Y, c1R, paint);
            //add source type of cat paws
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
            canvas.drawBitmap(bmp, 0, 0, paint);
        } else{
            //add first color
            paint.setColor(color1);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            canvas.drawRect(0,0,bmp.getWidth(),bmp.getHeight(), paint);
            //add second color
            paint.setColor(color2);
            int c2X = bmp.getWidth()/2;
            int c2Y = 3 * bmp.getHeight()/4;
            int rand = picker;
            int c2R = bmp.getHeight()/5 + rand;
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            canvas.drawCircle(c2X, c2Y, c2R, paint);
            //add third color
            paint.setColor(color3);
            int c3X = 4 * bmp.getWidth()/5;
            int c3Y = bmp.getHeight()/4;
            int rand2 = picker2;
            int c3R = bmp.getHeight()/5 + rand2;
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            canvas.drawCircle(c3X, c3Y, c3R, paint);
            //add source type of cat paws
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
            canvas.drawBitmap(bmp, 0, 0, paint);
        }

        return bmp;
    }
}
