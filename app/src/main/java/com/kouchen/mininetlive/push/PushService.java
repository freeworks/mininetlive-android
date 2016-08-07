package com.kouchen.mininetlive.push;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kouchen.mininetlive.utils.Helper;
import com.umeng.message.UTrack;
import com.umeng.message.UmengBaseIntentService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.client.BaseConstants;
import org.json.JSONObject;

/**
 * Created by cainli on 16/8/6.
 */
public class PushService extends UmengBaseIntentService {
//    private static final String TAG = PushService.class.getName();
//
//// 如果需要打开Activity，请调用Intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)；否则无法打开Activity。
//
//    @Override
//    protected void onMessage(Context context, Intent intent) {
//        // 需要调用父类的函数，否则无法统计到消息送达
//        super.onMessage(context, intent);
//        try {
//            //可以通过MESSAGE_BODY取得消息体
//            String message = intent.getStringExtra(BaseConstants.MESSAGE_BODY);
//            UMessage msg = new UMessage(new JSONObject(message));
//            UTrack.getInstance(context).trackMsgClick(msg);
//            Log.d(TAG, "message="+message);    //消息体
//            Log.d(TAG, "custom="+msg.custom);    //自定义消息的内容
//            Log.d(TAG, "title="+msg.title);    //通知标题
//            Log.d(TAG, "text="+msg.text);    //通知内容
//            // code  to handle message here
//            // ...
//            // 完全自定义消息的处理方式，点击或者忽略
//            boolean isClickOrDismissed = true;
//            if(isClickOrDismissed) {
//
//                // 完全自定义消息的点击统计
//                UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
//            } else {
//                //完全自定义消息的忽略统计
//                UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
//            }
//
//            // 使用完全自定义消息来开启应用服务进程的示例代码
//            // 首先需要设置完全自定义消息处理方式
//            // mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
//            // code to handle to start/stop service for app process
//            JSONObject json = new JSONObject(msg.custom);
//            String topic = json.getString("topic");
//            if(topic != null && topic.equals("appName:startService")) {
//                // 在友盟portal上新建自定义消息，自定义消息文本如下
//                //{"topic":"appName:startService"}
//                if(Helper.isServiceRunning(context, NotificationService.class.getName()))
//                    return;
//                Intent intent1 = new Intent();
//                intent1.setClass(context, NotificationService.class);
//                context.startService(intent1);
//            } else if(topic != null && topic.equals("appName:stopService")) {
//                // 在友盟portal上新建自定义消息，自定义消息文本如下
//                //{"topic":"appName:stopService"}
//                if(!Helper.isServiceRunning(context, NotificationService.class.getName()))
//                    return;
//                Intent intent1 = new Intent();
//                intent1.setClass(context, NotificationService.class);
//                context.stopService(intent1);
//            }
//        } catch (Exception e) {
//            Log.e(TAG, e.getMessage());
//        }
//    }
}
