package com.example.csi5175final;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.csi5175final.BallView;

import java.util.Random;

public class PlayGame extends AppCompatActivity {

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
                int color = Color.argb(50, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
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
                //add ball to game and start
                ConstraintLayout container  = (ConstraintLayout) findViewById(R.id.game_scene);
                BallView ballView = new BallView(PlayGame.this);
                container.addView(ballView);
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