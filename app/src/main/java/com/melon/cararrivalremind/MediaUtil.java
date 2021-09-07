package com.melon.cararrivalremind;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

public class MediaUtil {

    private static MediaPlayer mMediaPlayer;

    //开始播放
    public static void playRing(Context context) {
        try {
            Log.d("Melon", "playRing");
            //用于获取手机默认铃声的Uri
            Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(context, alert);
            //告诉mediaPlayer播放的是铃声流
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //停止播放
    public static void stopRing() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }
}