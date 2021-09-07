package com.melon.cararrivalremind;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

// 定义一个定时广播的接收器
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            MediaUtil.playRing(context);
        }
    }
}
