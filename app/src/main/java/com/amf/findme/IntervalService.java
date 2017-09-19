package com.amf.findme;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.concurrent.TimeUnit;

public class IntervalService extends IntentService {

    public IntervalService() {
        super("IntervalService");
    }

    protected void onHandleIntent(Intent intent) {
        intent = new Intent(this, LocationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, 0, TimeUnit.MINUTES.toMillis(1), pendingIntent);
    }

}