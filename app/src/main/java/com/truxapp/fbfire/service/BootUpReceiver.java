package com.truxapp.fbfire.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.truxapp.fbfire.MainActivity;

/**
 * Created by sharadsingh on 17/06/17.
 */

public class BootUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

        /***** For start Service  ****/
        Intent myIntent = new Intent(context, MyService.class);
        context.startService(myIntent);
    }
}
