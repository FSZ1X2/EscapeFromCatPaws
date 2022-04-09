package com.example.csi5175final;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
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
    private int x = 15;
    private int y = 15;
    private int radius = 30;

    //initial class
    public BallView(Context context) {
        super(context);
        this.context = context;
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

    //custom onDraw() for this view
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //set background to white
        canvas.drawColor(Color.WHITE);

        //set ball to red
        paint = new Paint();
        paint.setColor(Color.RED);

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

        //repaint the ball by the leave position
        if (x >= 18 && y >= 18 && x <= width - 18 && y <= height - 18) {
            //use postInvalidate() to repeat painting the ball follow user finger
            postInvalidate();
        }
        //return super.onTouchEvent(event);
        return true;
    }
}