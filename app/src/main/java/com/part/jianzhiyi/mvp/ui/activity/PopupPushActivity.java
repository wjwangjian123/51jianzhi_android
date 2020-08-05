package com.part.jianzhiyi.mvp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.sdk.android.push.AndroidPopupActivity;
import com.part.jianzhiyi.R;
import com.part.jianzhiyi.constants.Constants;

import java.util.Map;

/**
 * 移动辅助通道
 * 辅助弹窗
 */
public class PopupPushActivity extends AndroidPopupActivity {

    private String TAG = "PopupPushActivity";
    private String job_id;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent1 = new Intent(PopupPushActivity.this, MainActivity.class);
        intent1.putExtra("type", 2);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PopupPushActivity.this.startActivity(intent1);
    }

    /**
     * 实现通知打开回调方法，获取通知相关信息
     *
     * @param title   标题
     * @param summary 内容
     * @param extMap  额外参数
     */
    @Override
    protected void onSysNoticeOpened(String title, String summary, Map<String, String> extMap) {
        Log.d(TAG, "onSysNoticeOpened, title: " + title + ", content: " + summary + ", extMap: " + extMap);
        if (extMap!=null){
            job_id =extMap.get("job_id");
            type =extMap.get("type");
            Log.d(TAG,"job_id:"+job_id+",type:"+type);
            if (type.equals("2")){
                Intent intent1 = new Intent(PopupPushActivity.this, MainActivity.class);
                intent1.putExtra("type", 2);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PopupPushActivity.this.startActivity(intent1);
            }else if (type.equals("3")){
                Intent intent2 = new Intent(PopupPushActivity.this, ActionListActivity.class);
                intent2.putExtra("id", job_id);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PopupPushActivity.this.startActivity(intent2);
            }else {
                Intent intent = new Intent(PopupPushActivity.this, VocationActivity.class);
                intent.putExtra("id", job_id);
                intent.putExtra("position", Constants.POSITION_POPUP_PUSH);
                intent.putExtra("sortId", "");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PopupPushActivity.this.startActivity(intent);
            }
        }
        PopupPushActivity.this.finish();
    }
}