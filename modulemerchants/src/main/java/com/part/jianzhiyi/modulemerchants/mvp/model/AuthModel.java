package com.part.jianzhiyi.modulemerchants.mvp.model;

import com.google.gson.Gson;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.modulemerchants.http.HttpAPI;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MAuthInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MEnterpriseInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MFileEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MGetEnterpriseInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MIDFaPositiveEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MIDPositiveEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.AuthContract;
import com.part.jianzhiyi.modulemerchants.utils.ParamsUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class AuthModel implements AuthContract.IAuthModel {

    @Override
    public Observable<MIDPositiveEntity> getBaidNumber(RequestBody requestBody) {
        return HttpAPI.getInstence().getServiceApi().getBaidNumber(requestBody);
    }

    @Override
    public Observable<ResponseData> getCheckEnterprise(Map<String, Object> obj) {
        if (obj == null) {
            obj = new HashMap<>();
        }
        Gson gson = new Gson();
        String s = gson.toJson(obj);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        return HttpAPI.getInstence().getServiceApi().getCheckEnterprise(body);
    }

    @Override
    public Observable<ResponseData> getNumidSuccess(String img_z, String name, String number, String img_f, String company) {
        Map<String, String> headers = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = String.valueOf((1 + Math.random() * (10 - 1 + 1)));
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", timestamp);
        map.put("nonce", nonce);
        map.put("token", PreferenceUUID.getInstence().getToken());
        map.put("img_z", img_z);
        map.put("name", name);
        map.put("number", number);
        map.put("img_f", img_f);
        map.put("company", company);
        String paramsSerializeString = ParamsUtil.getParamsSerializeString(map);
        String sing = "baseApi!@#-";
        String string = sing + paramsSerializeString;
        String encode = Tools.md5(string);
        String s = encode.toUpperCase();
        headers.put("nonce", nonce);
        headers.put("timestamp", timestamp);
        headers.put("signature", s);
        return HttpAPI.getInstence().getServiceApi().getNumidSuccess(headers, PreferenceUUID.getInstence().getToken(), img_z, name, number, img_f, company);
    }

    @Override
    public Observable<MEnterpriseInfoEntity> getEnterprise(RequestBody requestBody) {
        return HttpAPI.getInstence().getServiceApi().getEnterprise(requestBody);
    }

    @Override
    public Observable<MIDFaPositiveEntity> getCorporate(RequestBody requestBody) {
        return HttpAPI.getInstence().getServiceApi().getCorporate(requestBody);
    }

    @Override
    public Observable<MAuthInfoEntity> getNumid() {
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
        return HttpAPI.getInstence().getServiceApi().getNumid(headers, PreferenceUUID.getInstence().getToken());
    }

    @Override
    public Observable<MFileEntity> getUpload(RequestBody requestBody) {
        return HttpAPI.getInstence().getServiceApi().getUpload(requestBody);
    }

    @Override
    public Observable<MGetEnterpriseInfoEntity> getEnterpriseInfo() {
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
        return HttpAPI.getInstence().getServiceApi().getEnterpriseInfo(headers, PreferenceUUID.getInstence().getToken());
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
}
