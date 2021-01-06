package com.part.jianzhiyi.corecommon.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shixinxin
 * @function 正则工具类
 */
public class RegularUtils {
    /**
     * 是不是手机号
     *
     * @param mobiles 输入手机号
     * @return 是/否
     */
    public static boolean isMobileNumber(String mobiles) {
        String pattern = "1[0-9]{10}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isEmail(String email) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }


    public static boolean isNumber(String str) {
        String p = "^[0-9]*$";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


    public static boolean isChar(String str) {
        String pattern = "[A-Z]";
        return isMatches(str, pattern);
    }


    private static boolean isMatches(String str, String p) {
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isPassword(String password) {
        String regex = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',.<>/?~！@#￥%……&*（）——+|{}【】‘；：”“'。，、？]){6,20}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }

}
