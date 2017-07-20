package com.example.iyashwant.myapplicationmusic;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;

public class MediaPlaybackService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    public static final String MPS_MESSAGE = "com.axeleroy.musicplayer.MediaPlaynackService.MESSAGE";
    public static final String MPS_RESULT = "com.axeleroy.musicplayer.MediaPlaynackService.RESULT";
    public static final String MPS_COMPLETED = "com.axeleroy.musicplayer.MediaPlaynackService.COMPLETED";

    MediaPlayer mMediaPlayer = null;
    Uri file;

    public class IDBinder extends Binder {

        MediaPlaybackService getService() {
            return MediaPlaybackService.this;
        }
    }
    IDBinder idBinder = new IDBinder();

    LocalBroadcastManager broadcastManager;

    @Override
    public void onCreate() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        stop();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return idBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        stop();
        return super.onUnbind(intent);
    }

    public void init(Uri file) {
        this.file = file;

        stop();


        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(getApplicationContext(), file);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.prepareAsync(); // prepare async to not block main thread
        } catch (IOException e) {
            e.printStackTrace();
            stop();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        mp.start();

        Thread updateThread = new Thread(sendUpdates);
        updateThread.start();
    }

    private Runnable sendUpdates = new Runnable() {
        @Override
        public void run() {
            while (mMediaPlayer != null) {
                sendElapsedTime();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void pause() {
        if (mMediaPlayer != null)
            mMediaPlayer.pause();
    }

    public void play() {
        if (mMediaPlayer != null)
            mMediaPlayer.start();
    }

    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            file = null;
        }
    }

    public void seekTo(int msec) {
        mMediaPlayer.seekTo(msec);
    }

    public boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }

    public Uri getFile() {
        return file;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Intent intent = new Intent(MPS_COMPLETED);
        broadcastManager.sendBroadcast(intent);
    }

    private void sendElapsedTime() {
        Intent intent = new Intent(MPS_RESULT);
        if (mMediaPlayer != null)
            intent.putExtra(MPS_MESSAGE, mMediaPlayer.getCurrentPosition());
        broadcastManager.sendBroadcast(intent);
    }

}
