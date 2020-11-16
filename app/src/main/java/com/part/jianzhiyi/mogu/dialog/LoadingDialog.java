package com.part.jianzhiyi.mogu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.part.jianzhiyi.R;

import java.util.concurrent.ScheduledExecutorService;

import androidx.annotation.NonNull;


/**
 * @author Trailwalker
 * @des 自定义loading窗
 */
public class LoadingDialog extends Dialog {

    private ScheduledExecutorService mScheduledThreadPool;

    private LoadingDialog(@NonNull Context context) {
        super(context, R.style.MyDialogTheme);
    }

    public static class Builder {

        private final Context mContext;

        public Builder(Context context) {
            this.mContext = context;
        }


        public LoadingDialog create() {
            final LoadingDialog dialog = new LoadingDialog(mContext);

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.dialog_loading, null);

            SpinKitView spinKit = layout.findViewById(R.id.spin_kit);
            Sprite wave = new Wave();
            spinKit.setIndeterminateDrawable(wave);

            dialog.setContentView(layout);

            return dialog;
        }

    }

}
