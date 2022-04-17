package com.example.csi5175final;

import static com.example.csi5175final.MainActivity.backgroundMusicOn;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.csi5175final.services.BackGroundMusic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayGame extends AppCompatActivity {

    //customize ball color based on user selection
    private int ballColor = Color.WHITE;
    public static AppCompatActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        ContentResolver contentResolver = getContentResolver();
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(contentResolver,
                Settings.Secure.ANDROID_ID);
        getImage(android_id);
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

    private void getImage(String id){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                String results = "";
                try{
                    String url = "https://heroic-arbor-303003.uc.r.appspot.com/image?id=" + id;
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

                String finalResults = results.replace("[\"", "").replace("\"]", "");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if( finalResults!=null && finalResults.length() > 0){
                            String[] formatted = finalResults.split(",");
                            int color1 = Integer.parseInt(formatted[0]);
                            int color2 = Integer.parseInt(formatted[1]);
                            int color3 = Integer.parseInt(formatted[2]);
                            int selection = Integer.parseInt(formatted[3]);
                            int picker = Integer.parseInt(formatted[4]);
                            int picker2 = Integer.parseInt(formatted[5]);

                            generateBitMap(color1,color2,color3,selection,picker,picker2);
                        }

                    }
                });
            }
        });
    }

    private void generateBitMap(int color1, int color2, int color3, int selection, int picker, int picker2){
        Drawable paw1 = PlayGame.this.getDrawable(R.drawable.nekotype1);

        switch (selection){
            case 1: paw1 = PlayGame.this.getDrawable(R.drawable.nekotype1);break;
            case 2: paw1 = PlayGame.this.getDrawable(R.drawable.nekotype2);break;
            case 3: paw1 = PlayGame.this.getDrawable(R.drawable.nekotype3);break;
            case 4: paw1 = PlayGame.this.getDrawable(R.drawable.nekotype4);break;
        }
        Bitmap bmp = ((BitmapDrawable) paw1).getBitmap();
        android.graphics.Bitmap.Config bitmapConfig = bmp.getConfig();
        // set default bitmap config if none
        if(bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // convert resource bitmaps are to mutable one
        bmp = bmp.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bmp);
        //add color changes for type1 paws
        int[] colorIndex= {color1, color2, color3};
        Paint paint = new Paint();
        //add color

        if(selection < 4){
            int colorID = picker;
            paint.setColor(colorIndex[colorID]);
            int c1X = bmp.getWidth()/4;
            int c1Y = bmp.getHeight()/2;
            int c1R = bmp.getWidth()/3;
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            canvas.drawCircle(c1X, c1Y, c1R, paint);
            //add source type of cat paws
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
            canvas.drawBitmap(bmp, 0, 0, paint);
        } else{
            //add first color
            paint.setColor(color1);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            canvas.drawRect(0,0,bmp.getWidth(),bmp.getHeight(), paint);
            //add second color
            paint.setColor(color2);
            int c2X = bmp.getWidth()/2;
            int c2Y = 3 * bmp.getHeight()/4;
            int rand = picker;
            int c2R = bmp.getHeight()/5 + rand;
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            canvas.drawCircle(c2X, c2Y, c2R, paint);
            //add third color
            paint.setColor(color3);
            int c3X = 4 * bmp.getWidth()/5;
            int c3Y = bmp.getHeight()/4;
            int rand2 = picker2;
            int c3R = bmp.getHeight()/5 + rand2;
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            canvas.drawCircle(c3X, c3Y, c3R, paint);
            //add source type of cat paws
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
            canvas.drawBitmap(bmp, 0, 0, paint);
        }

        BitmapFactory bf = new BitmapFactory();

        Constants.db = bmp;
    }
}