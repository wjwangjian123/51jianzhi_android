package com.part.jianzhiyi.modulemerchants.mvp.model;

import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.modulemerchants.http.HttpAPI;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MMinMoneyEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MMyWalletEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MZfbPayEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MerWalletContract;
import com.part.jianzhiyi.modulemerchants.utils.ParamsUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;


public class MerWalletModel implements MerWalletContract.IMerWalletModel {

    @Override
    public Observable<MMinMoneyEntity> getMinMoney() {
        return HttpAPI.getInstence().getServiceApi().getMinMoney(PreferenceUUID.getInstence().getToken());
    }

    @Override
    public Observable<MMyWalletEntity> getMyMoney(String start_time, String end_time, String type, int page) {
        Map<String, String> headers = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = String.valueOf((1 + Math.random() * (10 - 1 + 1)));
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", timestamp);
        map.put("nonce", nonce);
        map.put("token", PreferenceUUID.getInstence().getToken());
        if (start_time != null && start_time != "") {
            map.put("start_time", start_time);
        }
        if (end_time != null && end_time != "") {
            map.put("end_time", end_time);
        }
        map.put("type", type);
        map.put("page", page);
        String paramsSerializeString = ParamsUtil.getParamsSerializeString(map);
        String sing = "baseApi!@#-";
        String string = sing + paramsSerializeString;
        String encode = Tools.md5(string);
        String s = encode.toUpperCase();
        headers.put("nonce", nonce);
        headers.put("timestamp", timestamp);
        headers.put("signature", s);
        return HttpAPI.getInstence().getServiceApi().getMyMoney(headers, PreferenceUUID.getInstence().getToken(), start_time, end_time, type, page);
    }

    @Override
    public Observable<MZfbPayEntity> getOrder(String money) {
        Map<String, String> headers = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = String.valueOf((1 + Math.random() * (10 - 1 + 1)));
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", timestamp);
        map.put("nonce", nonce);
        map.put("token", PreferenceUUID.getInstence().getToken());
        map.put("money", money);
        String paramsSerializeString = ParamsUtil.getParamsSerializeString(map);
        String sing = "baseApi!@#-";
        String string = sing + paramsSerializeString;
        String encode = Tools.md5(string);
        String s = encode.toUpperCase();
        headers.put("nonce", nonce);
        headers.put("timestamp", timestamp);
        headers.put("signature", s);
        return HttpAPI.getInstence().getServiceApi().getOrder(headers, PreferenceUUID.getInstence().getToken(), money);
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
