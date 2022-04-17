package com.example.csi5175final;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    private Rect r = new Rect();
    private int playerColor = Color.WHITE; // save user selected yarn ball color

    private Player player; // setup player
    private Point playerPoint; // setup player initial position
    private ObstacleManager obstacleManager; // setup cat paws

    private boolean movingPlayer = false; // check if player is moving
    private boolean gameOver = false; // check if game is end

    private Context game_context = this.getContext(); // get game scene context

    public GamePanel(Context context, int color) {
        super(context);

        getHolder().addCallback(this);

        Constants.CURRENT_CONTEXT = context;

        thread = new MainThread(getHolder(), this);

        playerColor = color;
        player = new Player(new Rect(100,100,350,350), playerColor);
        playerPoint = new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);

        obstacleManager = new ObstacleManager(450, 450, 200);

        setFocusable(true);
    }

    /**
     * function for reset everything for the game.
     */
    public void reset(){
        player = new Player(new Rect(100,100,350,350), playerColor);
        playerPoint = new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(450, 450, 200);
        movingPlayer = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), GamePanel.this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(true) {
            try {
                thread.setRunning(false);
                thread.join();
            }catch(Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && player.getRectangle().contains((int)event.getX(),(int)event.getY()))
                    movingPlayer = true;
                if(gameOver){
                    //pass score to game over scene
                    Intent intent = new Intent(game_context, GameOver.class);
                    intent.putExtra("score",passScore());
                    reset();
                    gameOver = false;
                    game_context.startActivity(intent);
                    System.exit(0);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(!gameOver && movingPlayer)
                    playerPoint.set((int)event.getX(),(int)event.getY());
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }

        return true;
    }

    public void update() {
        if(!gameOver){
            player.update(playerPoint);
            obstacleManager.update();

            if(obstacleManager.playerCollide(player)){
                player.setRectangle();
                if(player.checkHP())
                    gameOver = true;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //set background image
        Bitmap backgroud = ((BitmapDrawable)getResources().getDrawable( R.drawable.background )).getBitmap();
        Paint backpaint = new Paint();
        canvas.drawBitmap(backgroud, 0, 0, backpaint);

        player.draw(canvas);
        obstacleManager.draw(canvas);

        if(gameOver){
            //show game over scene
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.BLACK);
            drawCenterText(canvas,paint,"Game Over");
        }
    }

    /**
     * function for showing game over text on the center of the canvas.
     *
     * @param canvas surfaceView canvas.
     * @param paint color of the text.
     * @param text "Game Over" text.
     */
    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }

    /**
     * function for passing user current score to game over scene.
     * @return obstacleManager.getScore() function for getting score.
     */
     private String passScore(){ return obstacleManager.getScore(); }
}