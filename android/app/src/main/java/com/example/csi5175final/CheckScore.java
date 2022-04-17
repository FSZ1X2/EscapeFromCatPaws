package com.example.csi5175final;

import static com.example.csi5175final.MainActivity.backgroundMusicOn;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csi5175final.services.BackGroundMusic;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CheckScore extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_score);
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        //back to lobby
        Button back = findViewById(R.id.leave_scorePage_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckScore.this, HomePage.class));
            }
        });

        TextView topScore = findViewById(R.id.score_records);

        getScore(android_id, false);

        Button checkYour = findViewById(R.id.player_record);
        checkYour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getScore(android_id, false);
            }
        });

        Button checkGol = findViewById(R.id.allUser_record);
        checkGol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getScore(android_id, true);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 1:
                if(resultCode == -1){
                    Uri fileUri = data.getData();
                    String filePath = fileUri.getPath();
                    Intent i = new Intent(this, BackGroundMusic.class);
                    i.putExtra("command", "update" + filePath);
                    this.startService(i);
                }
        }
    }

    //setup menu bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // back to homepage setting
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);
        return true;
    }
    //setup actions for each selections
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.toggleMusic:
                Intent i = new Intent(this, BackGroundMusic.class);
                if(backgroundMusicOn){
                    i.putExtra("command", "stop");
                    this.startService(i);
                    backgroundMusicOn = false;
                } else{
                    i.putExtra("command", "start");
                    this.startService(i);
                    backgroundMusicOn = true;
                    Toast.makeText(this, R.string.music_start, Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.backHome: //back to lobby
                startActivity(new Intent(CheckScore.this, HomePage.class));
                return true;
            case R.id.quitGame: // close the app
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getScore(String id, boolean global){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                String results = "";
                try{
                    String url = global? "HTTP://18.191.10.52:3000/topg" : "HTTP://18.191.10.52:3000/top?id=" + id;
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
                        TextView scores = CheckScore.this.findViewById(R.id.score_records);
                        TextView scores_head = CheckScore.this.findViewById(R.id.user_score_board);

                        if(global){
                            scores_head.setText(R.string.gol_score_head);
                        }else{
                            scores_head.setText(R.string.loc_score_head);
                        }
                        String formatted = finalResults.replace("[", "").replace("]", "").replace(",","\n");
                        scores.setText(formatted);
                    }
                });
            }
        });
    }
}