package com.example.csi5175final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameOver extends AppCompatActivity {

    String current_score = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        //show user score
        Intent intent = getIntent();
        TextView result = findViewById(R.id.score_view);
        current_score = intent.getStringExtra("score");
        String preview = "Your score is:\n " + current_score;
        result.setText(preview);

        //restart game
        Button bt_restart = findViewById(R.id.restart_game);
        bt_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //restart game
                startActivity(new Intent(GameOver.this, GameScene.class));
            }
        });

        //share game
        Button bt_share = findViewById(R.id.share_game);
        bt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri share = saveImage(makeShareImage());
                shareImageUri(share);
            }
        });

        //save user current score
        Button bt_saveScore = findViewById(R.id.save_current_score);
        bt_saveScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get user id
                ContentResolver contentResolver = getContentResolver();
                @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(contentResolver,
                        Settings.Secure.ANDROID_ID);
                //post user current score to server
                int score = Integer.parseInt(current_score);
                postScore(android_id, score);
            }
        });

        //back to lobby
        Button bt_leave = findViewById(R.id.leave_game);
        bt_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //restart game
                startActivity(new Intent(GameOver.this, HomePage.class));
            }
        });
    }

    /**
     * function for getting the bitmap for sharing.
     *
     * @return bitmap generated by the app.
     */
    private Bitmap makeShareImage(){
        //get template from resource
        BitmapFactory bf = new BitmapFactory();
        Bitmap shareImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.share);
        Canvas canvas = new  Canvas(shareImg);
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.BLACK);
        //draw current score into it
        canvas.drawText("Your score is:\n " + current_score, 100, 500+paint.descent()-paint.ascent(),paint);
        return shareImg;
    }

    /**
     * function for saving the image as PNG to the app's cache directory.
     * @param image Bitmap to save.
     * @return Uri of the saved file or null
     */
    private Uri saveImage(Bitmap image) {
        File imagesFolder = new File(getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder, "shared_image.png");

            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(this, "com.example.csi5175final.provider", file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }

    /**
     * function for checking if the external storage is writable.
     * @return true if storage is writable, false otherwise
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * function for sharing the PNG image from Uri.
     * @param uri Uri of image to share.
     */
    private void shareImageUri(Uri uri){
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/png");
        startActivity(intent);
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
                Intent intent_file = new Intent(Intent.ACTION_GET_CONTENT);
                intent_file.setType("audio/*");
                intent_file.addCategory(Intent.CATEGORY_OPENABLE);

                Intent finalIntent = Intent.createChooser(intent_file, "Select background music");

                startActivityForResult(finalIntent, 1);
                return true;
            case R.id.backHome: //back to lobby
                startActivity(new Intent(GameOver.this, HomePage.class));
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

    //post user current score to the database
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
                        Toast.makeText(GameOver.this, R.string.post_score, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}