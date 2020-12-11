package com.part.jianzhiyi.modulemerchants.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by jyx on 2020/11/26
 *
 * @author jyx
 * @describe
 */
public class ParamsUtil {
    public static String getParamsSerializeString(Map<String, Object> params) {
        StringBuffer buffer = new StringBuffer();
        List<String> keyList = new ArrayList<>();
        if (params == null || params.isEmpty()) {
            return null;
        }
        Set<String> keySetTry = new TreeSet<>();
        keySetTry.addAll(params.keySet());
        keyList.addAll(keySetTry);
        Collections.sort(keyList);
        for (int i = 0; i < keyList.size(); i++) {
            if (i == keyList.size() - 1) {
                buffer.append(keyList.get(i) + "=" + params.get(keyList.get(i)));
            } else {
                buffer.append(keyList.get(i) + "=" + params.get(keyList.get(i)) + "&");
            }
        }
        return buffer.toString();
    }
}
