package com.melon.cararrivalremind;

import android.app.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.text.TextUtils;
import android.util.Log;
import android.widget.*;
import android.view.View.*;
import android.view.View;

import androidx.annotation.RequiresApi;

public class MainActivity extends Activity {
    EditText et1, et2, et3;
    private Button mBt1;
    private Button mBt2;
    private Button mBt3;

    boolean isGoToWork; //true是上班

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findView();
    }

    void findView() {
        et1 = findViewById(R.id.et_1);
        et2 = findViewById(R.id.et_2);
        et3 = findViewById(R.id.et_3);

        mBt1 = findViewById(R.id.bt_1);
        mBt2 = findViewById(R.id.bt_2);
        mBt3 = findViewById(R.id.bt_3);
        mBt1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                isGoToWork = true;
                timeDown();
                Toast.makeText(MainActivity.this, "计时开始", Toast.LENGTH_SHORT).show();
            }
        });

        mBt2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                isGoToWork = false;
                timeDown();
                Toast.makeText(MainActivity.this, "计时开始", Toast.LENGTH_SHORT).show();
            }
        });

        mBt3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MediaUtil.stopRing();
            }
        });
    }

    void timeDown() {
        mBt1.setEnabled(false);
        mBt2.setEnabled(false);

        String time1 = et1.getText().toString().trim();
        String time2 = et2.getText().toString().trim();
        String time3 = et3.getText().toString().trim();

        if (TextUtils.isEmpty(time1) && TextUtils.isEmpty(time2) && TextUtils.isEmpty(time3)) {
            //取默认时间
            if (isGoToWork) {
                time1 = "25";
                time2 = "53";
            } else {
                time1 = "10";
                time2 = "33";
            }

            time3 = "73";

            et1.setText(time1);
            et2.setText(time2);
            et3.setText(time3);
        }

        final int t1 = Integer.parseInt(time1);
        final int t2 = Integer.parseInt(time2);
        final int t3 = Integer.parseInt(time3);

        final int[] ts = new int[3];
        ts[0] = t1;
        ts[1] = t2;
        ts[2] = t3;

        // 定时执行
        Intent intent = new Intent(this, AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        for (int i = 0; i < 3; i++) {
            PendingIntent pIntent = PendingIntent.getBroadcast(this, i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + ts[i] * 60 * 1000, pIntent);
        }
    }
}
