package com.example.herud.player;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

/**
 * Created by Herud on 2018-05-29.
 */

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        super.onCreate();

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);


        Notification n  = new Notification.Builder(this)
                .setContentTitle("The player is working")
                .setContentText(MainActivity.currTitle)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentIntent(pIntent)
                .setAutoCancel(false)
                .build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        //MainActivity.mainPlayer.start();
        return 1;
    }

    public void onStart(Intent intent, int startId) {
        // TO DO
    }
    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {

    }
    public void onPause() {

    }
    @Override
    public void onDestroy() {

    }

    @Override
    public void onLowMemory() {

    }
}
