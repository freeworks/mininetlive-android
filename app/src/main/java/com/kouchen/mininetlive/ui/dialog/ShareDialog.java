package com.kouchen.mininetlive.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kouchen.mininetlive.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;


/**
 * Created by cainli on 16/7/14.
 */
public class ShareDialog extends Dialog implements PlatformActionListener {

    private static final String TAG = "ShareDialog";

    @BindView(R.id.wechat)
    TextView wechat;
    @BindView(R.id.moment)
    TextView moment;
    @BindView(R.id.weibo)
    TextView weibo;
    private String title;
    private String desc;
    private String imgPath;
    private String id;

    public ShareDialog(Context context) {
        super(context, R.style.payDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.share_layout);
        ButterKnife.bind(this);
        setCancelable(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }

    @OnClick(R.id.weibo)
    public void weibo() {
        SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
        sp.setTitle(title);
        sp.setText(desc);
        sp.setUrl("http://www.weiwanglive.com/share.html?aid="+id);
        sp.setImageUrl(imgPath);
        sp.setShareType(Platform.SHARE_WEBPAGE);
        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        weibo.setPlatformActionListener(this);
        weibo.share(sp);
    }

    @OnClick(R.id.wechat)
    public void wechat() {
        Wechat.ShareParams sp = new Wechat.ShareParams();
        sp.setTitle(title);
        sp.setText(desc);
        sp.setUrl("http://www.weiwanglive.com/share.html?aid="+id);
        sp.setImageUrl(imgPath);
        sp.setShareType(Platform.SHARE_WEBPAGE);
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(this);
        wechat.share(sp);
    }

    @OnClick(R.id.moment)
    public void moment() {
        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
        sp.setTitle(title);
        sp.setText(desc);
        sp.setUrl("http://106.75.19.205:80/share.html?aid="+id);
        sp.setImageUrl(imgPath);
        sp.setShareType(Platform.SHARE_WEBPAGE);
        Platform wechat = ShareSDK.getPlatform(WechatMoments.NAME);
        wechat.setPlatformActionListener(this);
        wechat.share(sp);
    }

    public void show(String id,String title,String desc,String imgPath) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.imgPath = imgPath;
        super.show();
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Toast.makeText(getContext(),"分享成功",Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Toast.makeText(getContext(),"分享失败",Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onError: ",throwable );
    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
