package com.example.csi5175final.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.csi5175final.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BackGroundMusic extends Service {

    private MediaPlayer player;
    private boolean ownUri = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String com = intent.getStringExtra("command");

        if (com != null && com.equals("start")) {
            player.start();
        } else if(com.equals("stop")) {
            player.pause();
        }

        if(com != null && com.contains("update")){
            String url = com.substring(6);
            Uri uri = Uri.parse(url);
            try {
                if(player != null){
                    player.release();
                }
                player = new MediaPlayer();
                player.setDataSource(this, uri);
                ownUri = true;
            } catch (IOException e) {
                e.printStackTrace();
                ownUri = false;
            }
            player.prepareAsync();
            player.setOnPreparedListener(preparedPlayer -> {
                preparedPlayer.setLooping(true);
            });
        }


        return super.onStartCommand(intent, flags, startId);
    }
}
