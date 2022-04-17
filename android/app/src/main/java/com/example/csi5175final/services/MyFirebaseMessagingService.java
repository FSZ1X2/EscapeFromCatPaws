package com.example.csi5175final.services;

import android.app.Service;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private final String TAG = "FCMDemo";


    public void onMessageReceived(RemoteMessage remoteMessage) {

        // if app is running, deal with the message
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            // get push message key value
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            // get push message content
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }


    public void onNewToken(String s) {
        super.onNewToken(s);
        // when token change，get new token
        Log.d(TAG, "new Token: " + s);
    }
}
