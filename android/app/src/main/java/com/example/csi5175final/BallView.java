package com.example.csi5175final;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class BallView extends View {
    private Paint paint;
    Context context;

    //initial the yarn ball
    private int x = 0;
    private int y = 0;
    private int radius = 100; //HP
    private int color = 0;

    //initial border
    private int borderL= 0;
    private int borderR= 0;
    private int borderT= 0;
    private int borderB= 0;

    //initial class
    public BallView(Context context, int ballColor) {
        super(context);
        this.context = context;
        this.color = ballColor;
        //setup the border based on window
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        this.borderL = 2*radius;
        this.borderR = size.x - 2*radius;
        this.borderT = 2*radius;
        this.borderB = size.y - 2*radius;
        //setup ball position
        this.x = size.x/2;
        this.y = size.y - 2*radius;
    }

    //initial xml
    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    //setup styles
    public BallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setRadius(int num){
        this.radius = this.radius - num;
    }

    private void checkBorder(int moveX, int moveY){
        //update the border first
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        borderL = 2*radius;
        borderR = size.x - 2*radius;
        borderT = 2*radius;
        borderB = size.y - 2*radius;
        //check if the ball out of border
        if(moveX < borderL) x = borderL;
        if(moveX > borderR) x = borderR;
        if(moveY < borderT) y = borderT;
        if(moveY > borderB) y = borderB;
    }

    //custom onDraw() for this view
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //set background image
        Bitmap backgroud = ((BitmapDrawable)getResources().getDrawable( R.drawable.background )).getBitmap();
        Paint backpaint = new Paint();
        canvas.drawBitmap(backgroud, 0, 0, backpaint);

        //set ball color
        paint = new Paint();
        paint.setColor(color);

        //anti-aliasing
        paint.setAntiAlias(true);
        //draw the ball
        canvas.drawCircle(x,y,radius, paint);
    }

    //custom onTouchEvent()
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //check onTouch point
        switch (event.getAction()) {
            //implement MotionEvent.ACTION_DOWN
            //record initial position
            case MotionEvent.ACTION_DOWN:
                x = (int) event.getX();
                y = (int) event.getY();

            //record move action and position
            case MotionEvent.ACTION_MOVE:
                x = (int) event.getX();
                y = (int) event.getY();

            //record leave position
            case MotionEvent.ACTION_UP:
                //get current x,y values and refer to axis
                x = (int) event.getX();
                y = (int) event.getY();
                break;
        }

        //get the height, width of the screen
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //repaint the ball inside the borderline
        checkBorder(x,y);
        postInvalidate();

        return true;
    }
}