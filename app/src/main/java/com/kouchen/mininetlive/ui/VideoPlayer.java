package com.kouchen.mininetlive.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoTextureView;

/**
 * Created by Nathen
 * On 2016/04/18 16:15
 */
public class VideoPlayer extends PLVideoTextureView implements PLMediaPlayer.OnErrorListener, PLMediaPlayer.OnCompletionListener {


    public ImageView thumbImageView;

    public VideoPlayer(Context context) {
        super(context);
        init();
    }

    public VideoPlayer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public VideoPlayer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setOnCompletionListener(this);
        setOnErrorListener(this);
    }

    private void setOption(int isLiveStream) {
        AVOptions options = new AVOptions();
        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
        // Some optimization with buffering mechanism when be set to 1
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, isLiveStream == 0 ? 1 : 0);
        if (isLiveStream == 0) {
            options.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
        }
        // 1 -> hw codec enable, 0 -> disable [recommended]
        //int codec = getIntent().getIntExtra("mediaCodec", 0);
        options.setInteger(AVOptions.KEY_MEDIACODEC, 0);

        // whether start play automatically after prepared, default value is 1
        options.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);
        setAVOptions(options);

    }

    public void setUp(int isLiveStream, String videoPath, String title) {
        setOption(isLiveStream);
        setVideoPath(videoPath);
        start();
    }

    @Override
    public boolean onError(PLMediaPlayer plMediaPlayer, int i) {
        return false;
    }

    @Override
    public void onCompletion(PLMediaPlayer plMediaPlayer) {

    }
}
