package com.kouchen.mininetlive.ui.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


/**
 * Created by cainli on 16/7/10.
 */
public class FullActiivty extends Activity {

    private static final String TAG = "FullActiivty";
    private String videoPath;
    private String title;
    private boolean isLivestream;
    private long position;
    private boolean isPlaying;
    private VideoPlayer player;

    public static void startActivityFromNormal(Activity context, String videoPath,String title, boolean isLiveStream,boolean isPlaying,long position) {
        Intent intent = new Intent(context, FullActiivty.class);
        intent.putExtra("videoPath",videoPath);
        intent.putExtra("title",title);
        intent.putExtra("isLiveStream",isLiveStream);
        intent.putExtra("currentPosition",position);
        intent.putExtra("isPlaying",isPlaying);
        context.startActivityForResult(intent,VideoPlayer.FULLSCREEN_REQUESTCODE);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        videoPath = getIntent().getStringExtra("videoPath");
        title = getIntent().getStringExtra("title");
        isLivestream = getIntent().getBooleanExtra("isLiveStream",false);
        position = getIntent().getLongExtra("currentPosition",0L);
        isPlaying = getIntent().getBooleanExtra("isPlaying",false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        try {
            player = new VideoPlayer(this);
            player.setup(videoPath,title,isLivestream,true,true);
            if(!isLivestream){
                player.setCurrentPosition(position);
            }
            if(isPlaying){
                player.start();
            }else{
                player.pause();
            }
            player.setFullScreenListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("isPlaying",player.isPlaying());
                    intent.putExtra("currentPosition",player.getCurrentPosition());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
            player.setOnBackListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("isPlaying",player.isPlaying());
                    intent.putExtra("currentPosition",player.getCurrentPosition());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
            setContentView(player);
        } catch (Exception e) {
            Log.e(TAG, "onCreate: ", e);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stopPlayback();
    }
}
