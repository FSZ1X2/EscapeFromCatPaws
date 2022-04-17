package com.example.csi5175final;

import static com.example.csi5175final.MainActivity.backgroundMusicOn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
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

    //values for change the screen lightness
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private float mLux;

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

        mSensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        // set up sensor to light sensor
        mSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_LIGHT);

        // check if the device can auto change brightness
        if(IsAutoBrightness(HomePage.this)){
            // close the device auto brightness change feature and use app's one
            stopAutoBrightness(HomePage.this);
        }
        // set up listener to the light sensor
        mSensorManager.registerListener(listener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    // the listener for getting sensor data for brightness
    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            if(event.sensor.getType() == Sensor.TYPE_LIGHT){
                //get brightness
                mLux = event.values[0];
                if (mLux <  20000){
                    setBrightness(HomePage.this, 80); //low brightness
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else{
                    setBrightness(HomePage.this,255); //high brightness
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(listener);
        }
    }

    /**
     * function for changing screen brightness
     *
     * @param activity the current activity
     * @param brightness the brightness we want the screen to be set to
     */
    public static  void setBrightness(Activity activity, int brightness){
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        activity.getWindow().setAttributes(lp);
    }

    /**
     * function for checking whether the auto brightness change feature is on or not
     *
     * @param context the current context of the activity
     * @return true if the auto brightness change feature is on
     */
    public static  boolean IsAutoBrightness(Context context){
        boolean IsAutoBrightness = false ;
        try{
            IsAutoBrightness = Settings.System.getInt(
                    context.getContentResolver(),

                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;

        }catch (Exception e){
            e.printStackTrace();
        }

        return  IsAutoBrightness;
    }

    /**
     * function for stopping auto screen brightness change
     *
     * @param context the current context of the activity
     */
    public static  void stopAutoBrightness(Context context){
        Settings.System.putInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
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
                    binding.fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.ic_lock_silent_mode));
                } else{
                    i.putExtra("command", "start");
                    this.startService(i);
                    backgroundMusicOn = true;
                    binding.fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.ic_lock_silent_mode_off));
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