package com.kouchen.mininetlive.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kouchen.mininetlive.R;
import com.pili.pldroid.player.IMediaController;
import java.util.Locale;

/**
 * You can write a custom MediaController instead of this class
 * A MediaController widget must implement all the interface defined by
 * com.pili.pldroid.player.IMediaController
 */
public class MediaController extends FrameLayout implements IMediaController {

    private static final String TAG = "PLMediaController";
    private MediaPlayerControl mPlayer;
    private Context mContext;
    private ProgressBar mProgress;
    private TextView mEndTime, mCurrentTime;
    private long mDuration;
    private boolean mShowing;
    private boolean mDragging;
    private boolean mInstantSeeking = true;
    private static int sDefaultTimeout = 3000;
    private static final int SEEK_TO_POST_DELAY_MILLIS = 200;

    private static final int FADE_OUT = 1;
    private static final int SHOW_PROGRESS = 2;
    private ImageView mPauseButton;

    private AudioManager mAM;
    private Runnable mLastSeekBarRunnable;
    private boolean mDisableProgress = false;

    public ViewGroup topContainer, bottomContainer;

    private View loadProgress;

    private ImageView fullscreenView;
    private View backView;

    private ImageView thumb;
    private ImageView cover;
    private TextView titleView;

    public MediaController(Context context, View view,String title,boolean disableProgressBar,boolean isFullScreen) {
        super(context);
        this.mDisableProgress = disableProgressBar;
        initController(context, view,title,isFullScreen);
    }

    private boolean initController(Context context, View view,String title,boolean isFullScreen) {
        mContext = context.getApplicationContext();
        mAM = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);

        bottomContainer = (ViewGroup) view.findViewById(R.id.layout_bottom);
        mPauseButton = (ImageView) view.findViewById(R.id.start);
        if (mPauseButton != null) {
            mPauseButton.requestFocus();
            mPauseButton.setOnClickListener(mPauseListener);
        }

        loadProgress = view.findViewById(R.id.loading);
        mProgress = (ProgressBar) view.findViewById(R.id.progress);
        if (mProgress != null) {
            if (mProgress instanceof SeekBar) {
                SeekBar seeker = (SeekBar) mProgress;
                seeker.setOnSeekBarChangeListener(mSeekListener);
                seeker.setThumbOffset(1);
            }
            mProgress.setMax(1000);
            mProgress.setEnabled(!mDisableProgress);
        }

        mEndTime = (TextView) view.findViewById(R.id.total);
        mCurrentTime = (TextView) view.findViewById(R.id.current);

        topContainer = (ViewGroup) view.findViewById(R.id.layout_top);
        bottomContainer = (ViewGroup) view.findViewById(R.id.layout_bottom);

        thumb = (ImageView) view.findViewById(R.id.thumb);
        cover = (ImageView) view.findViewById(R.id.cover);

        fullscreenView = (ImageView) view.findViewById(R.id.fullscreen);
        if(isFullScreen){
            fullscreenView.setImageResource(R.drawable.jc_shrink);
        }else{
            fullscreenView.setImageResource(R.drawable.jc_enlarge);
        }
        backView = view.findViewById(R.id.back);
        titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(title);
        return true;
    }

    public ImageView getThumb() {
        return thumb;
    }

    public ImageView getCover() {
        return cover;
    }

    public View getLoadProgress() {
        return loadProgress;
    }


    private void disableUnsupportedButtons() {
        try {
            if (mPauseButton != null && !mPlayer.canPause()) mPauseButton.setEnabled(false);
        } catch (IncompatibleClassChangeError ex) {
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            long pos;
            switch (msg.what) {
                case FADE_OUT:
                    hide();
                    break;
                case SHOW_PROGRESS:
                    pos = setProgress();
                    if (!mDragging && mShowing) {
                        msg = obtainMessage(SHOW_PROGRESS);
                        sendMessageDelayed(msg, 1000 - (pos % 1000));
                        updatePausePlay();
                    }
                    break;
            }
        }
    };

    private long setProgress() {
        if (mPlayer == null || mDragging) return 0;

        long position = mPlayer.getCurrentPosition();
        long duration = mPlayer.getDuration();
        if (mProgress != null) {
            if (duration > 0) {
                long pos = 1000L * position / duration;
                mProgress.setProgress((int) pos);
            }
            int percent = mPlayer.getBufferPercentage();
            mProgress.setSecondaryProgress(percent * 10);
        }

        mDuration = duration;

        if (mEndTime != null) mEndTime.setText(generateTime(mDuration));
        if (mCurrentTime != null) mCurrentTime.setText(generateTime(position));

        return position;
    }

    private static String generateTime(long position) {
        int totalSeconds = (int) (position / 1000);

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return String.format(Locale.US, "%02d:%02d", minutes, seconds).toString();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        show(sDefaultTimeout);
        return true;
    }

    @Override
    public boolean onTrackballEvent(MotionEvent ev) {
        show(sDefaultTimeout);
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (event.getRepeatCount() == 0 && (keyCode == KeyEvent.KEYCODE_HEADSETHOOK
            || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
            || keyCode == KeyEvent.KEYCODE_SPACE)) {
            doPauseResume();
            show(sDefaultTimeout);
            if (mPauseButton != null) mPauseButton.requestFocus();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
                updatePausePlay();
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            hide();
            return true;
        } else {
            show(sDefaultTimeout);
        }
        return super.dispatchKeyEvent(event);
    }

    private OnClickListener mPauseListener = new OnClickListener() {
        public void onClick(View v) {
            doPauseResume();
            show(sDefaultTimeout);
        }
    };

    public void setFullScreenListener(OnClickListener onClickListener){
        fullscreenView.setOnClickListener(onClickListener);
    }

    public void setOnBackListener(OnClickListener onClickListener){
        backView.setOnClickListener(onClickListener);
    }


    private void updatePausePlay() {
        if (mPauseButton == null) return;
        if (mPlayer.isPlaying()) {
            mPauseButton.setImageResource(R.drawable.jc_click_pause_selector);
        } else {
            mPauseButton.setImageResource(R.drawable.jc_click_play_selector);
        }
    }

    private void doPauseResume() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        } else {
            mPlayer.start();
        }
        updatePausePlay();
    }

    private SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {

        public void onStartTrackingTouch(SeekBar bar) {
            mDragging = true;
            show(3600000);
            mHandler.removeMessages(SHOW_PROGRESS);
            if (mInstantSeeking) mAM.setStreamMute(AudioManager.STREAM_MUSIC, true);
        }

        public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
            if (!fromuser) return;

            final int newposition = (int) (mDuration * progress) / 1000;
            String time = generateTime(newposition);
            if (mInstantSeeking) {
                mHandler.removeCallbacks(mLastSeekBarRunnable);
                mLastSeekBarRunnable = new Runnable() {
                    @Override
                    public void run() {
                        mPlayer.seekTo(newposition);
                    }
                };
                mHandler.postDelayed(mLastSeekBarRunnable, SEEK_TO_POST_DELAY_MILLIS);
            }
            if (mCurrentTime != null) mCurrentTime.setText(time);
        }

        public void onStopTrackingTouch(SeekBar bar) {
            if (!mInstantSeeking) mPlayer.seekTo((int) (mDuration * bar.getProgress()) / 1000);

            show(sDefaultTimeout);
            mHandler.removeMessages(SHOW_PROGRESS);
            mAM.setStreamMute(AudioManager.STREAM_MUSIC, false);
            mDragging = false;
            mHandler.sendEmptyMessageDelayed(SHOW_PROGRESS, 1000);
        }
    };

    /**
     * Set the view that acts as the anchor for the control view.
     *
     * - This can for example be a VideoView, or your Activity's main view.
     * - AudioPlayer has no anchor view, so the view parameter will be null.
     *
     * @param view The view to which to anchor the controller when it is visible.
     */
    @Override
    public void setAnchorView(View view) {
    }

    @Override
    public void setMediaPlayer(MediaPlayerControl player) {
        mPlayer = player;
        updatePausePlay();
    }

    @Override
    public void show() {
        show(sDefaultTimeout);
    }

    /**
     * Show the controller on screen. It will go away automatically after
     * 'timeout' milliseconds of inactivity.
     *
     * @param timeout The timeout in milliseconds. Use 0 to show the controller until hide() is
     * called.
     */
    @Override
    public void show(int timeout) {
        if (!mShowing) {
            if (mPauseButton != null) {
                mPauseButton.requestFocus();
            }
            disableUnsupportedButtons();
            mPauseButton.setVisibility(View.VISIBLE);
            topContainer.setVisibility(View.VISIBLE);
            bottomContainer.setVisibility(View.VISIBLE);
            mShowing = true;
        }
        updatePausePlay();
        mHandler.sendEmptyMessage(SHOW_PROGRESS);

        if (timeout != 0) {
            mHandler.removeMessages(FADE_OUT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(FADE_OUT), timeout);
        }
    }

    @Override
    public boolean isShowing() {
        return mShowing;
    }

    @Override
    public void hide() {
        if (mShowing) {
            mHandler.removeMessages(SHOW_PROGRESS);
            mPauseButton.setVisibility(View.GONE);
            topContainer.setVisibility(View.GONE);
            bottomContainer.setVisibility(View.GONE);
            mShowing = false;
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (mPauseButton != null) {
            mPauseButton.setEnabled(enabled);
        }
        if (mProgress != null && !mDisableProgress) mProgress.setEnabled(enabled);
        disableUnsupportedButtons();
        super.setEnabled(enabled);
    }
}
