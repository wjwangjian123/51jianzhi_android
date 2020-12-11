package com.part.jianzhiyi.corecommon.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.part.jianzhiyi.corecommon.constants.Constants;
import com.part.jianzhiyi.corecommon.utils.Tools;

/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class PreferenceCity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static PreferenceCity preferenceUUID;

    private PreferenceCity() {
        sharedPreferences = Tools.getApplicationByReflection().getApplicationContext().getSharedPreferences(Constants.SP_UUID_FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static PreferenceCity getInstence() {
        if (preferenceUUID == null) {
            preferenceUUID = new PreferenceCity();
        }
        return preferenceUUID;
    }

    public void setCity(String city) {
        editor.putString("city", city);
        editor.apply();
    }

    public String getCity() {
        return sharedPreferences.getString("city", "北京市");
    }


}
