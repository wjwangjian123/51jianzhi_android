package com.part.jianzhiyi.corecommon.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.core.content.ContextCompat;

public class LogUtils {
    private static String TAG = "LogToFile";
    private static Context mContext;

    private static String logPath = null;//log日志存放路径

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.CHINA);//日期格式;

    private static Date date = new Date();//因为log日志是使用日期命名的，使用静态成员变量主要是为了在整个程序运行期间只存在一个.log文件中;

    /**
     * 初始化，须在使用之前设置，最好在Application创建时调用
     *
     * @param context
     */
    public static void init(Context context) {
        logPath = getFilePath(context) + "/ALogs";//获得文件储存路径,在后面加"/Logs"建立子文件夹
        mContext=context;
    }

    /**
     * 获得文件存储路径
     *
     * @return
     */
    private static String getFilePath(Context context) {

        return Environment.getExternalStorageDirectory().getAbsolutePath();//获得外部存储路径,默认路径为
    }

    private static final char VERBOSE = 'v';

    private static final char DEBUG = 'd';

    private static final char INFO = 'i';

    private static final char WARN = 'w';

    private static final char ERROR = 'e';

    public static void v(String tag, String msg) {
        writeToFile(VERBOSE, tag, msg);
    }

    public static void d(String tag, String msg) {
        writeToFile(DEBUG, tag, msg);
    }

    public static void i(String tag, String msg) {
        writeToFile(INFO, tag, msg);
    }

    public static void w(String tag, String msg) {
        writeToFile(WARN, tag, msg);
    }

    public static void e(String tag, String msg) {
        writeToFile(ERROR, tag, msg);
    }

    /**
     * 将log信息写入文件中
     *
     * @param type
     * @param tag
     * @param msg
     */
    private static void writeToFile(char type, String tag, String msg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        if (null == logPath) {
            Log.e(TAG, "logPath == null ，未初始化LogToFile");
            return;
        }

        String fileName = logPath + "/log_crash.log";//log日志名，使用时间命名，保证不重复
        SimpleDateFormat sdf=new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        Log.e(TAG, "log日志路径：" + fileName);
        String log =format+"   "+ tag + " " + msg+"\n" ;//log日志内容，可以自行定制

        //如果父路径不存在
        File file = new File(logPath);
        if (!file.exists()) {
            file.mkdirs();//创建父路径
        }

        FileOutputStream fos = null;//FileOutputStream会自动调用底层的close()方法，不用关闭
        BufferedWriter bw = null;
        try {

            fos = new FileOutputStream(fileName, true);//这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(log);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();//关闭缓冲流
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}

