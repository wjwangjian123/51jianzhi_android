package com.part.jianzhiyi.corecommon.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/10/13
 * @modified by:shixinxin
 **/
public class DateUtils {

    /**
     * 获取文字版时间
     *
     * @param seconds 传入日期
     * @return 文字版时间
     */
    public static String getTextDate(long seconds) {
        Date date = new Date(seconds * 1000);
        int days = differentToDaysByMillisecond(date);
        SimpleDateFormat sdf = new SimpleDateFormat();
        if (days > 1) {
            sdf.applyPattern("HH:mm");
            return sdf.format(date);
        }
        sdf.applyPattern("HH:mm");
        String format = sdf.format(date);
        return days == 0 ? format : "昨天 " + format;
    }

    /**
     * 通过时间秒毫秒数判断距离今天的间隔
     *
     * @param date1
     * @return
     */
    private static int differentToDaysByMillisecond(Date date1) {
        Date date2 = new Date();
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
    }

    public static String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }

    public static String getDateMonth() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }
}
