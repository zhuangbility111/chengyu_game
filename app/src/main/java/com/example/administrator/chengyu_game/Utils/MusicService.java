package com.example.administrator.chengyu_game.Utils;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.administrator.chengyu_game.R;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener{

    public class MyMusicBinder extends Binder {
        //返回Service对象
        public MusicService getService(){
            return MusicService.this;
        }
    }

    private MediaPlayer mediaPlayer;
    public MyMusicBinder myMusicBinder;

    //Service初始化时调用
    @Override
    public void onCreate(){
        super.onCreate();
        //初始化时就创建一个MediaPlayer进行资源链接
        myMusicBinder = new MyMusicBinder();
        mediaPlayer = MediaPlayer.create(this, R.raw.bgm);
        mediaPlayer.setOnCompletionListener(this);
    }


    public MusicService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        if(!mediaPlayer.isPlaying()){
            // 开始播放
            mediaPlayer.start();
            // 允许循环播放
            mediaPlayer.setLooping(true);
        }
        return myMusicBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub
        //松绑Service，会触发onDestroy()
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //先停止 再释放
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        mediaPlayer.release();

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        stopSelf();
    }
}
