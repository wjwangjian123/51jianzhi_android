package com.part.jianzhiyi.corecommon.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/10/13
 * @modified by:shixinxin
 **/
public class CopyTextLibrary {
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private Context context;
    private String textView;

    public CopyTextLibrary(Context context, String textView) {
        this.context = context;
        this.textView = textView;
    }

    public void init() {
        myClipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        String text;
        text = textView;

        myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);

        Toast.makeText(context, text + " 已复制", Toast.LENGTH_SHORT).show();
    }
}
