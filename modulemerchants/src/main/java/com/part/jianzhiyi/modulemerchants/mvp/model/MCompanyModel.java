package com.part.jianzhiyi.modulemerchants.mvp.model;

import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.modulemerchants.constants.Constants;
import com.part.jianzhiyi.modulemerchants.http.HttpAPI;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MCompanyInfoEntity;
import com.part.jianzhiyi.modulemerchants.mvp.contract.MCompanyContract;
import com.part.jianzhiyi.modulemerchants.utils.ParamsUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;


public class MCompanyModel implements MCompanyContract.IMCompanyModel {

    @Override
    public Observable<MCompanyInfoEntity> getCompanyInfo(String uid, String job_id) {
        Map<String, String> headers = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = String.valueOf((1 + Math.random() * (10 - 1 + 1)));
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", timestamp);
        map.put("nonce", nonce);
        if (uid != null && uid != "") {
            map.put("uid", uid);
        }
        if (job_id != null && job_id != "") {
            map.put("job_id", job_id);
        }
        if (PreferenceUUID.getInstence().getUserId() != null && PreferenceUUID.getInstence().getUserId() != "") {
            map.put("user_id", PreferenceUUID.getInstence().getUserId());
        }
        map.put("app_id", Constants.APPID);
        String paramsSerializeString = ParamsUtil.getParamsSerializeString(map);
        String sing = "baseApi!@#-";
        String string = sing + paramsSerializeString;
        String encode = Tools.md5(string);
        String s = encode.toUpperCase();
        headers.put("nonce", nonce);
        headers.put("timestamp", timestamp);
        headers.put("signature", s);
        return HttpAPI.getInstence().getServiceApi().getCompanyInfo(headers, uid, job_id, PreferenceUUID.getInstence().getUserId(), Constants.APPID);
    }

    @Override
    public Observable<ResponseData> getIntroduced(String company_desc) {
        return HttpAPI.getInstence().getServiceApi().getIntroduced(PreferenceUUID.getInstence().getToken(), company_desc);
    }
}
