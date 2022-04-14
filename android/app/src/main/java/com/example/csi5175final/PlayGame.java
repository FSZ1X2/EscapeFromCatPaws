package com.example.csi5175final;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csi5175final.BallView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayGame extends AppCompatActivity {

    //customize ball color based on user selection
    private int ballColor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //hide title bar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) actionBar.hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);

        ImageView yarn = findViewById(R.id.yarn_image);
        yarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random rnd = new Random();
                int colorR = rnd.nextInt(256);
                int colorG = rnd.nextInt(256);
                int colorB = rnd.nextInt(256);
                ballColor = Color.argb(255, colorR, colorG, colorB);
                int color = Color.argb(50, colorR, colorG, colorB);
                yarn.setColorFilter(color);
            }
        });

        //start game
        Button game_start = findViewById(R.id.startGame_button);
        game_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear up play scene
                TextView intro_text = findViewById(R.id.intro);
                intro_text.setVisibility(View.INVISIBLE);
                yarn.setVisibility(View.INVISIBLE);
                game_start.setVisibility(View.INVISIBLE);
                //start game
                startActivity(new Intent(PlayGame.this, GameScene.class));
                //add ball to game and start
//                ConstraintLayout container  = (ConstraintLayout) findViewById(R.id.game_scene);
//                BallView ballView = new BallView(PlayGame.this, ballColor);
//                container.addView(ballView);
            }
        });
    }

    private void postScore(String id, int score){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String url = "HTTP://18.191.10.52:3000/score";
                    String urlParameters  = "id="+id+"&num2=" + score;
                    InputStream stream = null;
                    byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
                    int postDataLength = postData.length;
                    URL urlObj = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("charset", "utf-8");
                    conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);

                    conn.connect();

                    try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                        wr.write( postData );
                        wr.flush();
                    }

                    stream = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
                    String result = reader.readLine();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PlayGame.this, R.string.post_score, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

//    //setup menu bar
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        // back to homepage setting
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_bar, menu);
//        return true;
//    }
//    //setup actions for each selections
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.toggleMusic: //TODO: add toggle music feature
//
//                return true;
//            case R.id.backHome: //back to lobby
//                startActivity(new Intent(PlayGame.this, HomePage.class));
//                return true;
//            case R.id.quitGame: // close the app
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}