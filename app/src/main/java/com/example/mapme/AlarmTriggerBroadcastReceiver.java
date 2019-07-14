package com.example.mapme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmTriggerBroadcastReceiver extends BroadcastReceiver {
    private final static String TAG = "ALARM_TRIGGER_BROADCAST";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "AlarmTrigger Broadcast receiver running");
        Toast.makeText(context, "Hello from the other side", Toast.LENGTH_LONG).show();
    }
}
