package com.melon.cararrivalremind;

import android.app.*;
import android.os.*;
import android.text.TextUtils;
import android.util.Log;
import android.widget.*;
import android.view.View.*;
import android.view.View;

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
                mBt3.setVisibility(View.GONE);
            }
        });
    }

    int count;
    String countDown1, countDown2, countDown3;

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
        }

        final int t1 = Integer.parseInt(time1);
        final int t2 = Integer.parseInt(time2);
        final int t3 = Integer.parseInt(time3);

        /*
        FIXME 这种处理，当手机休眠后，就被挂起了，所以不会生效（小米10s）
         */
        new Thread() {
            public void run() {
                while (count <= t3 * 60) {
                    count++;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ((t1 * 60 - count) > 0) {
                                countDown1 = (t1 * 60 - count) / 60 + "分" + (t1 * 60 - count) % 60 + "秒";
                            } else if ((t1 * 60 - count) == 0) {
                                // 播放声音
                                play();
                            } else {
                                countDown1 = "0分0秒";
                            }

                            if ((t2 * 60 - count) > 0) {
                                countDown2 = (t2 * 60 - count) / 60 + "分" + (t2 * 60 - count) % 60 + "秒";
                            } else if ((t2 * 60 - count) == 0) {
                                // 播放声音
                                play();
                            } else {
                                countDown2 = "0分0秒";
                            }

                            if ((t3 * 60 - count) > 0) {
                                countDown3 = (t3 * 60 - count) / 60 + "分" + (t3 * 60 - count) % 60 + "秒";
                            } else if ((t3 * 60 - count) == 0) {
                                // 播放声音
                                play();
                            } else {
                                countDown3 = "0分0秒";
                            }

                            et1.setText(countDown1);
                            et2.setText(countDown2);
                            et3.setText(countDown3);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Log.d("Melon", "count： " + count);
                }
                Log.d("Melon", "倒计时结束");
            }
        }.start();
    }

    private void play() {
        MediaUtil.playRing(getApplicationContext());
        mBt3.setVisibility(View.VISIBLE);
    }
}
