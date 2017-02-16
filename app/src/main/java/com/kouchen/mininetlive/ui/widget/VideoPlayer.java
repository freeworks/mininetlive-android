package com.kouchen.mininetlive.ui.widget;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kouchen.mininetlive.R;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoView;

/**
 * Created by cainli on 16/7/12.
 */
public class VideoPlayer extends RelativeLayout {

    private static final String TAG = "VideoPlayer";

    private MediaController mMediaController;
    private PLVideoView mVideoView;

    private PLMediaPlayer mPLMediaPlayer;

    private boolean startOnPrepared = true;

    private String videoPath;

    private ImageView cover;

    private boolean canplay;

    public static final int FULLSCREEN_REQUESTCODE = 1000;

    public VideoPlayer(Context context) {
        super(context);
        init(context);
    }

    public VideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VideoPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_video_player, this);

        mVideoView = (PLVideoView) findViewById(R.id.videoView);
        // Set some listeners
        mVideoView.setOnInfoListener(mOnInfoListener);
        mVideoView.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
        mVideoView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        mVideoView.setOnCompletionListener(mOnCompletionListener);
        mVideoView.setOnSeekCompleteListener(mOnSeekCompleteListener);
        mVideoView.setOnErrorListener(mOnErrorListener);
        mVideoView.setOnPreparedListener(mOnPreparedListener);
        cover = (ImageView) findViewById(R.id.cover);
    }

    public void setup(final String videoPath, final String title, final boolean isLiveStreaming, boolean isFullScreen, boolean canplay) {
        this.canplay = canplay;
        this.videoPath = videoPath;
        AVOptions options = new AVOptions();
        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 60 * 1000);
        options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 60 * 1000);
        // Some optimization with buffering mechanism when be set to 1
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, isLiveStreaming ? 1 : 0);
        if (isLiveStreaming) {
            options.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
        }
        options.setInteger(AVOptions.KEY_CACHE_BUFFER_DURATION, 2000);
        options.setInteger(AVOptions.KEY_MAX_CACHE_BUFFER_DURATION, 4000);
        // 1 -> hw codec enable, 0 -> disable [recommended]
        //int codec = getIntent().getIntExtra("mediaCodec", 0);
        options.setInteger(AVOptions.KEY_MEDIACODEC, 0);
        // whether start play automatically after prepared, default value is 1
        startOnPrepared = canplay;
        options.setInteger(AVOptions.KEY_START_ON_PREPARED, canplay ? 1 : 0);
        mVideoView.setAVOptions(options);

//        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_ORIGIN);
        mMediaController = new MediaController(getContext(), findViewById(R.id.controller), title, isLiveStreaming, isFullScreen);
        if (!canplay) {
            mMediaController.hide();
            mMediaController.getTopContainer().setVisibility(VISIBLE);
            mMediaController.getLoadProgress().setVisibility(View.INVISIBLE);
        } else {
            mVideoView.setBufferingIndicator(mMediaController.getLoadProgress());
            mVideoView.setMediaController(mMediaController);
            mVideoView.setVideoPath(videoPath);
        }
        return;
    }

    public String getVideoPath() {
        return videoPath;
    }

    private PLMediaPlayer.OnInfoListener mOnInfoListener = new PLMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(PLMediaPlayer plMediaPlayer, int what, int extra) {
            Log.d(TAG, "onInfo: " + what + ", " + extra);
            mPLMediaPlayer = plMediaPlayer;
            if (what == 701 || what == 3 || what == 10002) {
                if (mMediaController != null && canplay) {
                    mMediaController.hide();
                }
            }
            return false;
        }
    };

    private PLMediaPlayer.OnErrorListener mOnErrorListener = new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer plMediaPlayer, int errorCode) {
            Log.e(TAG, "Error happened, errorCode = " + errorCode);
            switch (errorCode) {
                case PLMediaPlayer.ERROR_CODE_INVALID_URI:
                    printLog("Invalid URL !");
                    break;
                case PLMediaPlayer.ERROR_CODE_404_NOT_FOUND:
                    printLog("404 resource not found !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED:
                    printLog("Connection refused !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT:
                    printLog("Connection timeout !");
                    break;
                case PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST:
                    printLog("Empty playlist !");
                    break;
                case PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED:
                    printLog("Stream disconnected !");
                    break;
                case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                    printLog("Network IO Error !");
                    break;
                case PLMediaPlayer.ERROR_CODE_UNAUTHORIZED:
                    printLog("Unauthorized Error !");
                    break;
                case PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT:
                    printLog("Prepare timeout !");
                    break;
                case PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT:
                    printLog("Read frame timeout !");
                    break;
                case PLMediaPlayer.MEDIA_ERROR_UNKNOWN:
                default:
                    printLog("unknown error !");
                    break;
            }
            // Todo pls handle the error status here, retry or call finish()
            // If you want to retry, do like this:
            // mVideoView.setVideoPath(mVideoPath);
            // mVideoView.start();
            // Return true means the error has been handled
            // If return false, then `onCompletion` will be called
            Toast.makeText(getContext(), "播放异常!", Toast.LENGTH_SHORT).show();
            mVideoView.setVideoPath(videoPath);
            mVideoView.start();
            if (mMediaController != null && canplay) {
                mMediaController.show();
            }
            return true;
        }
    };

    private void printLog(final String tips) {
        Log.d(TAG, tips);
    }

    private PLMediaPlayer.OnCompletionListener mOnCompletionListener =
            new PLMediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(PLMediaPlayer plMediaPlayer) {
                    Log.d(TAG, "Play Completed !");
                }
            };

    private PLMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener =
            new PLMediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(PLMediaPlayer plMediaPlayer, int precent) {
                    Log.d(TAG, "onBufferingUpdate: " + precent);
                }
            };

    private PLMediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener =
            new PLMediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(PLMediaPlayer plMediaPlayer) {
                    Log.d(TAG, "onSeekComplete !");
                }
            };

    private PLMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener =
            new PLMediaPlayer.OnVideoSizeChangedListener() {
                @Override
                public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int width, int height) {
                    Log.d(TAG, "onVideoSizeChanged: " + width + "," + height);
                }
            };

    private PLMediaPlayer.OnPreparedListener mOnPreparedListener =
            new PLMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(PLMediaPlayer plMediaPlayer) {
                    if (mMediaController != null && canplay && startOnPrepared) {
                        cover.setVisibility(View.GONE);
                    }
                    if (!startOnPrepared) {
                        startOnPrepared = true;
                    }
                }
            };


    public void pause() {
        if (mVideoView != null) {
            mVideoView.pause();
        }
    }

    public void start() {
        if (mVideoView != null) {
            mVideoView.start();
        }
    }

    public ImageView getCover() {
        if (mMediaController != null && canplay) {
            return cover;
        }
        return null;
    }

    public void setFullScreenListener(OnClickListener onClickListener) {
        if (mMediaController != null && canplay) {
            mMediaController.setFullScreenListener(onClickListener);
        }
    }

    public void setOnBackListener(OnClickListener onClickListener) {
        if (mMediaController != null) {
            mMediaController.setOnBackListener(onClickListener);
        }
    }

    public boolean isPlaying() {
        if (mVideoView != null) {
            return mVideoView.isPlaying();
        }
        return false;
    }

    public long getCurrentPosition() {
        if (mVideoView != null) {
            return mVideoView.getCurrentPosition();
        }
        return 0;
    }

    public void setCurrentPosition(long position) {
        if (mVideoView != null) {
            mVideoView.seekTo(position);
        }
    }

    public void setCover(String frontCover) {
        cover.setVisibility(View.VISIBLE);
        Glide.with(getContext())
                .load(frontCover)
                .fitCenter()
                .placeholder(R.drawable.img_default)
                .crossFade()
                .into(cover);
    }

    public void disable() {
        if (mMediaController != null && canplay) {
            mMediaController.setViewEnable(false);
        }
    }

    public void enable() {
        if (mMediaController != null && canplay) {
            mMediaController.setViewEnable(true);
        }
    }

    public void setCoverVisiable(boolean b) {
        if (cover != null) {
            cover.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void release() {
       try {
           if (mPLMediaPlayer != null) {
               mPLMediaPlayer.release();
           }
       }catch (Exception e){
           Log.e(TAG,"",e);
       }
    }
}
