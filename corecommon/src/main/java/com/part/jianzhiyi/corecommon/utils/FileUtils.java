package com.part.jianzhiyi.corecommon.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author:shixinxin
 * @Date :2020-03-24
 **/
public class FileUtils {
    /**
     * 从asset中获取json
     * @param context 上下文对象
     * @param fileName asset文件名
     * @return
     */
    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String result = "";
            while ((line = bufReader.readLine()) != null) {
                result += line;
                Log.e("tag", "此时获取的内容" + line);
            }
            bufReader.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("tag", "错误日志：" + e.getMessage());
        }
        return null;
    }


}
