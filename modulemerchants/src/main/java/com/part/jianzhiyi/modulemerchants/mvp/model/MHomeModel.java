package com.part.jianzhiyi.modulemerchants.mvp.model;

import android.util.Log;

import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.modulemerchants.http.HttpAPI;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MBannerEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MJobInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MJobListEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MHomeContract;
import com.part.jianzhiyi.modulemerchants.utils.ParamsUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;


public class MHomeModel implements MHomeContract.IMHomeModel {

    @Override
    public Observable<MBannerEntity> getIndexBanner() {
        Log.d("token", PreferenceUUID.getInstence().getToken());
        return HttpAPI.getInstence().getServiceApi().getIndexBanner(PreferenceUUID.getInstence().getToken());
    }

    @Override
    public Observable<ResponseData> getJobRefresh(String job_id) {
        Map<String, String> headers = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = String.valueOf((1 + Math.random() * (10 - 1 + 1)));
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", timestamp);
        map.put("nonce", nonce);
        map.put("token", PreferenceUUID.getInstence().getToken());
        map.put("job_id", job_id);
        String paramsSerializeString = ParamsUtil.getParamsSerializeString(map);
        String sing = "baseApi!@#-";
        String string = sing + paramsSerializeString;
        String encode = Tools.md5(string);
        String s = encode.toUpperCase();
        headers.put("nonce", nonce);
        headers.put("timestamp", timestamp);
        headers.put("signature", s);
        return HttpAPI.getInstence().getServiceApi().getJobRefresh(headers, PreferenceUUID.getInstence().getToken(), job_id);
    }

    @Override
    public Observable<ResponseData> getJobSx(int status, String job_id) {
        Map<String, String> headers = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = String.valueOf((1 + Math.random() * (10 - 1 + 1)));
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", timestamp);
        map.put("nonce", nonce);
        map.put("status", status);
        map.put("job_id", job_id);
        map.put("token", PreferenceUUID.getInstence().getToken());
        String paramsSerializeString = ParamsUtil.getParamsSerializeString(map);
        String sing = "baseApi!@#-";
        String string = sing + paramsSerializeString;
        String encode = Tools.md5(string);
        String s = encode.toUpperCase();
        headers.put("nonce", nonce);
        headers.put("timestamp", timestamp);
        headers.put("signature", s);
        return HttpAPI.getInstence().getServiceApi().getJobSx(headers, status, job_id, PreferenceUUID.getInstence().getToken());
    }

    @Override
    public Observable<MJobInfoEntity> getMJobInfo(String id) {
        Map<String, String> headers = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = String.valueOf((1 + Math.random() * (10 - 1 + 1)));
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", timestamp);
        map.put("nonce", nonce);
        map.put("id", id);
        map.put("token", PreferenceUUID.getInstence().getToken());
        String paramsSerializeString = ParamsUtil.getParamsSerializeString(map);
        String sing = "baseApi!@#-";
        String string = sing + paramsSerializeString;
        String encode = Tools.md5(string);
        String s = encode.toUpperCase();
        headers.put("nonce", nonce);
        headers.put("timestamp", timestamp);
        headers.put("signature", s);
        return HttpAPI.getInstence().getServiceApi().getMJobInfo(headers, id, PreferenceUUID.getInstence().getToken());
    }

    @Override
    public Observable<MJobListEntity> getMJobList(String type, String start_time, String end_time) {
        Map<String, String> headers = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = String.valueOf((1 + Math.random() * (10 - 1 + 1)));
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", timestamp);
        map.put("nonce", nonce);
        map.put("token", PreferenceUUID.getInstence().getToken());
        map.put("type", type);
        if (start_time != null && start_time != "") {
            map.put("start_time", start_time);
        }
        if (end_time != null && end_time != "") {
            map.put("end_time", end_time);
        }
        String paramsSerializeString = ParamsUtil.getParamsSerializeString(map);
        String sing = "baseApi!@#-";
        String string = sing + paramsSerializeString;
        String encode = Tools.md5(string);
        String s = encode.toUpperCase();
        headers.put("nonce", nonce);
        headers.put("timestamp", timestamp);
        headers.put("signature", s);
        return HttpAPI.getInstence().getServiceApi().getMJobList(headers, PreferenceUUID.getInstence().getToken(), type, start_time, end_time);
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
