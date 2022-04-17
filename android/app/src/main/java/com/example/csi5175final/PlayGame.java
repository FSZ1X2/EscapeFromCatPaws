package com.example.csi5175final;

import static com.example.csi5175final.MainActivity.backgroundMusicOn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.csi5175final.services.BackGroundMusic;

import java.util.Random;

public class PlayGame extends AppCompatActivity {

    //customize ball color based on user selection
    private int ballColor = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);

        ImageView yarn = findViewById(R.id.yarn_image);
        yarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //randomly pick colors for user
                Random rnd = new Random();
                int colorR = rnd.nextInt(256);
                int colorG = rnd.nextInt(256);
                int colorB = rnd.nextInt(256);
                ballColor = Color.argb(255, colorR, colorG, colorB);
                int color = Color.argb(150, colorR, colorG, colorB);
                //show the colored yarn ball
                yarn.setColorFilter(color);
            }
        });

        //start game
        Button game_start = findViewById(R.id.startGame_button);
        game_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //package color value and send to game
                Intent game = new Intent(PlayGame.this, GameScene.class);
                Bundle bundle = new Bundle();
                bundle.putInt("color", ballColor);
                game.putExtra("data", bundle);
                //start game scene
                startActivity(game);
            }
        });
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
                startActivity(new Intent(PlayGame.this, HomePage.class));
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
}