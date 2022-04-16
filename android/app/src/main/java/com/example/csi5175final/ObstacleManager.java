package com.example.csi5175final;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class ObstacleManager {
    //higher index = lower on screen = higher y value
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;

    private long startTime;
    private long initTime;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;

        startTime = System.currentTimeMillis();
        initTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateObstacles();
    }

    /**
     * function for checking if player hit cat paws or not.
     *
     * @param player the data of current player.
     * @return true if player hit any cat paws; false if not.
     */
    public boolean playerCollide(Player player){
        for(Obstacle ob: obstacles){
            if(ob.playerCollide(player)) return true;
        }
        return false;
    }

    /**
     * function for adding new cat paws based on Y position.
     */
    private void populateObstacles() {
        int currY = -5 * Constants.SCREEN_HEIGHT/4;
        while(currY < 0) {
            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public void update() {
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        //speed up the fallen cat paws by adding speed
        float speed = (float)(Math.sqrt(1 + (startTime - initTime)/1000.0))*Constants.SCREEN_HEIGHT/(10000.0f);
        for(Obstacle ob : obstacles) {
            ob.incrementY(speed * elapsedTime);
        }
        if(obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT) {
            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight, xStart, obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size() - 1);
        }
    }

    public void draw(Canvas canvas) {
        for(Obstacle ob : obstacles)
            ob.draw(canvas);
    }

    /**
     * function for getting user score.
     *
     * @return the time last for user to alive will be the final score of the game.
     */
    public String getScore() {
        long end = System.currentTimeMillis();
        String timeLast = Long.toString((end - initTime) / 1000);
        return timeLast;
    }
}
