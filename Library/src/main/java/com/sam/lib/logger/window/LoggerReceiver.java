package com.sam.lib.logger.window;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sam.lib.logger.window.ui.LoggerSwitchActivity;


public class LoggerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startActivity(new Intent(context, LoggerSwitchActivity.class));
    }

}