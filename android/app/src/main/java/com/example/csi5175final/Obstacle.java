package com.example.csi5175final;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

public class Obstacle implements GameObject {
    private Rect rectangle;
    //determine which side the cat paws will appear
    // left:0 and right:1
    private int LoR = new Random().nextInt(2);
    private Bitmap paw;

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
        if(LoR==0)
            rectangle = new Rect(0, startY, startX, startY + rectHeight);
        else
            rectangle = new Rect(startX + playerGap, startY, Constants.SCREEN_WIDTH, startY + rectHeight);

        //TODO: randomly get cat paws from database
        BitmapFactory bf = new BitmapFactory();
        paw = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.nekotype1);
    }

    /**
     * function for checking if play hit the cat paw.
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
