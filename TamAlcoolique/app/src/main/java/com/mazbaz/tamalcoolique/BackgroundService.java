package com.mazbaz.tamalcoolique;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class BackgroundService extends Service {
    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                MainActivity.user.addHungerLevel(-1);
                MainActivity.user.addAlcoholLevel(-1);
                MainActivity.user.addUrineLevel(-1);
                MainActivity.refreshDisplayedDatas();
            }
        }, 300000, 300000);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
