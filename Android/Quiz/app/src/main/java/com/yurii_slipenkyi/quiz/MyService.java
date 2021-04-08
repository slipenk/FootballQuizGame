package com.yurii_slipenkyi.quiz;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;

public class MyService extends Service {

    private MediaPlayer mediaPlayer;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        int i = (int)extras.get(Questions_activity.EXTRA_SERVICE);
        if (i == Category.UCL) {
            mediaPlayer = MediaPlayer.create(this, R.raw.ucl);
        } else {
            mediaPlayer = MediaPlayer.create(this, R.raw.hey_jude);
        }
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        mediaPlayer.stop();
    }
}