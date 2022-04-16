package com.example.csi5175final;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

public class Player implements GameObject {

    private Rect rectangle;
    private int color;
    private Bitmap ball;

    private void scaleRect(Rect rect) {
        float whRatio = (float)(ball.getWidth())/ball.getHeight();
        if(rect.width() > rect.height())
            rect.left = rect.right - (int)(rect.height() * whRatio);
        else
            rect.top = rect.bottom - (int)(rect.width() * (1/whRatio));
    }

    public Rect getRectangle() {
        return rectangle;
    }

    /**
     * function for smaller player's yarn ball.
     */
    public void setRectangle() {
        int l = rectangle.left;
        int t = rectangle.top;
        int r = rectangle.right;
        int b = rectangle.bottom;
        int width = rectangle.width()/2;
        int height = rectangle.height()/2;
        rectangle.set(l + width/4, t + height/4, r - width/4, b - height/4);
    }

    /**
     * function for checking if the game can still continue or not.
     *
     * @return whether or not the player's yarn ball is alive.
     */
    public boolean checkHP(){
        int width = rectangle.width()/2;
        if(width < 4) return true;
        return false;
    }

    /**
     * function for creating player.
     *
     * @param rectangle the collision box of player.
     * @param color the color of player's yarn ball.
     */
    public Player(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;

        BitmapFactory bf = new BitmapFactory();
        ball = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ball_nocolor);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        ColorFilter filter = new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY);
        paint.setColorFilter(filter);
        scaleRect(rectangle);
        canvas.drawBitmap(ball, null, rectangle, paint);
    }

    @Override
    public void update() {

    }

    /**
     * function for updating player position.
     */
    public void update(Point point) {
        //l,t,r,b
        int width = rectangle.width()/2;
        int height = rectangle.height()/2;
        rectangle.set(point.x - width, point.y - height, point.x + width, point.y + height);
    }
}
