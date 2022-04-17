package com.example.csi5175final;

import static com.example.csi5175final.MainActivity.backgroundMusicOn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;
import androidx.navigation.ui.AppBarConfiguration;
import com.example.csi5175final.databinding.HomePageBinding;
import com.example.csi5175final.services.BackGroundMusic;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

public class HomePage extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_ACTIVITY_RECOGNITION = 45;
    private HomePageBinding binding;
    public static HomePage Instance;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 1:
                if(resultCode == -1){
                    Uri filePath = data.getData();
                    Intent i = new Intent(this, BackGroundMusic.class);
                    i.putExtra("command", "update" + filePath.toString());
                    this.startService(i);
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, R.string.need_access, Toast.LENGTH_LONG);
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_ACTIVITY_RECOGNITION);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Instance = this;
        setContentView(R.layout.home_page);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_ACTIVITY_RECOGNITION);
        }

        binding = HomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: add background music toggle feature here
                Toast.makeText(Instance, "music start", Toast.LENGTH_LONG);
                //toggle background music
                Intent i = new Intent(Instance, BackGroundMusic.class);
                if(backgroundMusicOn){
                    i.putExtra("command", "stop");
                    Instance.startService(i);
                    backgroundMusicOn = false;
                    binding.fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.ic_lock_silent_mode));
                } else{
                    i.putExtra("command", "start");
                    Instance.startService(i);
                    backgroundMusicOn = true;
                    binding.fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.ic_lock_silent_mode_off));
                    Toast.makeText(Instance, R.string.music_start, Toast.LENGTH_LONG).show();
                }
            }
        });

        //enter game
        Button gameButton = findViewById(R.id.start);
        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, PlayGame.class));
            }
        });

        //edit images
        Button editButton = findViewById(R.id.editing);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, EditImage.class));
            }
        });

        //check scores
        Button scoreButton = findViewById(R.id.checkScores);
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, CheckScore.class));
            }
        });

        //go to github page
        Button referButton = findViewById(R.id.references);
        referButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/FSZ1X2/EscapeFromCatPaws")));
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
            case R.id.toggleMusic: //TODO: add toggle music feature
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
                startActivity(new Intent(HomePage.this, HomePage.class));
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