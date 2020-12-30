package com.part.jianzhiyi.modulemerchants.mvp.model;

import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.modulemerchants.constants.Constants;
import com.part.jianzhiyi.modulemerchants.http.HttpAPI;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MCheckVersionEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MConfigEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MUserInfoEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MMineContract;
import com.part.jianzhiyi.modulemerchants.utils.ParamsUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;


public class MMineModel implements MMineContract.IMMineModel {

    @Override
    public Observable<MUserInfoEntity> getMerUserinfo() {
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
        return HttpAPI.getInstence().getServiceApi().getMerUserinfo(headers, PreferenceUUID.getInstence().getToken());
    }

    @Override
    public Observable<ResponseData> getAvatar(RequestBody requestBody) {
        return HttpAPI.getInstence().getServiceApi().getAvatar(requestBody);
    }

    @Override
    public Observable<ResponseData> getOpinion(String reason, String con, String phone) {
        Map<String, String> headers = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = String.valueOf((1 + Math.random() * (10 - 1 + 1)));
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", timestamp);
        map.put("nonce", nonce);
        map.put("token", PreferenceUUID.getInstence().getToken());
        map.put("reason", reason);
        map.put("con", con);
        if (!phone.isEmpty()) {
            map.put("phone", phone);
        }
        String paramsSerializeString = ParamsUtil.getParamsSerializeString(map);
        String sing = "baseApi!@#-";
        String string = sing + paramsSerializeString;
        String encode = Tools.md5(string);
        String s = encode.toUpperCase();
        headers.put("nonce", nonce);
        headers.put("timestamp", timestamp);
        headers.put("signature", s);
        return HttpAPI.getInstence().getServiceApi().getOpinion(headers, PreferenceUUID.getInstence().getToken(), reason, con, phone);
    }

    @Override
    public Observable<ResponseData> getmdAdd(String type) {
        Map<String, String> headers = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = String.valueOf((1 + Math.random() * (10 - 1 + 1)));
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", timestamp);
        map.put("nonce", nonce);
        map.put("type", type);
        map.put("token", PreferenceUUID.getInstence().getToken());
        String paramsSerializeString = ParamsUtil.getParamsSerializeString(map);
        String sing = "baseApi!@#-";
        String string = sing + paramsSerializeString;
        String encode = Tools.md5(string);
        String s = encode.toUpperCase();
        headers.put("nonce", nonce);
        headers.put("timestamp", timestamp);
        headers.put("signature", s);
        return HttpAPI.getInstence().getServiceApi().getmdAdd(headers, type, PreferenceUUID.getInstence().getToken());
    }

    @Override
    public Observable<MCheckVersionEntity> getCheck() {
        return HttpAPI.getInstence().getServiceApi().getCheck(Constants.OS, Constants.APPID);
    }

    @Override
    public Observable<MConfigEntity> getConfig() {
        return HttpAPI.getInstence().getServiceApi().getConfig(Constants.APPID);
    }
}
