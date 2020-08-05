package com.part.jianzhiyi;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.model.entity.NotificationEntity;
import com.part.jianzhiyi.mvp.ui.activity.ActionListActivity;
import com.part.jianzhiyi.mvp.ui.activity.MainActivity;
import com.part.jianzhiyi.mvp.ui.activity.VocationActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.Map;

/**
 * Created by jyx on 2019/10/22
 *
 * @author jyx
 * @describe
 */
public class MyMessageReceiver extends MessageReceiver {

    // 消息接收部分的LOG_TAG
    public static final String REC_TAG = "receiver";

    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        // TODO 处理推送通知
        Log.e("MyMessageReceiver", "Receive notification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);
    }

    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        Log.e("MyMessageReceiver", "onMessage, messageId: " + cPushMessage.getMessageId() + ", title: " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
    }

    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
        if (extraMap != null) {
            NotificationEntity notificationEntity = JSON.parseObject(extraMap, NotificationEntity.class);
            String job_id = notificationEntity.getJob_id();
            String type = notificationEntity.getType();
            if (type.equals("2")) {
                Intent intent1 = new Intent(context, MainActivity.class);
                intent1.putExtra("type", 2);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            } else if (type.equals("3")) {
                Intent intent2 = new Intent(context, ActionListActivity.class);
                intent2.putExtra("id", job_id);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
            } else {
                MobclickAgent.onEvent(context, "push_in_vocation");
                Intent intent = new Intent(context, VocationActivity.class);
                intent.putExtra("id", job_id);
                intent.putExtra("position", Constants.POSITION_MESSAGE_RECEIVER);
                intent.putExtra("sortId", "");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationClickedWithNoAction, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Log.e("MyMessageReceiver", "onNotificationReceivedInApp, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap + ", openType:" + openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl);
    }

    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        Log.e("MyMessageReceiver", "onNotificationRemoved");
    }

}
