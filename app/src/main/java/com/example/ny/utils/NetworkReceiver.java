package com.example.ny.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NetworkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("test ==  ", "NetworkReceiver");
        if (intent.getAction()!= null && intent.getAction().equals(SyncService.ACTION_STOP)){
            context.stopService(new Intent(context, SyncService.class));
        }

    }
}
