package com.part.jianzhiyi.modulemerchants.mvp.model;

import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.modulemerchants.http.HttpAPI;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MSetContract;
import com.part.jianzhiyi.modulemerchants.utils.ParamsUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;


public class MSetModel implements MSetContract.ISetModel {

    @Override
    public Observable<ResponseData> getPassword(String type, String pass, String new_pass, String old_pass, String code, String username) {
        Map<String, String> headers = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = String.valueOf((1 + Math.random() * (10 - 1 + 1)));
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", timestamp);
        map.put("nonce", nonce);
        map.put("token", PreferenceUUID.getInstence().getToken());
        map.put("type", type);
        if (pass != null && pass != "") {
            map.put("pass", pass);
        }
        if (new_pass != null && new_pass != "") {
            map.put("new_pass", new_pass);
        }
        if (old_pass != null && old_pass != "") {
            map.put("old_pass", old_pass);
        }
        map.put("phone", PreferenceUUID.getInstence().getUserPhone());
        if (code != null && code != "") {
            map.put("code", code);
        }
        if (username != null && username != "") {
            map.put("username", username);
        }
        String paramsSerializeString = ParamsUtil.getParamsSerializeString(map);
        String sing = "baseApi!@#-";
        String string = sing + paramsSerializeString;
        String encode = Tools.md5(string);
        String s = encode.toUpperCase();
        headers.put("nonce", nonce);
        headers.put("timestamp", timestamp);
        headers.put("signature", s);
        return HttpAPI.getInstence().getServiceApi().getPassword(headers, PreferenceUUID.getInstence().getToken(), type, pass, new_pass, old_pass, PreferenceUUID.getInstence().getUserPhone(), code, username);
    }

    @Override
    public Observable<ResponseData> getCode() {
        Map<String, String> headers = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = String.valueOf((1 + Math.random() * (10 - 1 + 1)));
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", timestamp);
        map.put("nonce", nonce);
        map.put("token", PreferenceUUID.getInstence().getToken());
        String paramsSerializeString = ParamsUtil.getParamsSerializeString(map);
        String sing = "baseApi!@#-";
        String string = sing + paramsSerializeString;
        String encode = Tools.md5(string);
        String s = encode.toUpperCase();
        headers.put("nonce", nonce);
        headers.put("timestamp", timestamp);
        headers.put("signature", s);
        return HttpAPI.getInstence().getServiceApi().getCode(headers, PreferenceUUID.getInstence().getToken());
    }
}
