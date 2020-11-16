package com.part.jianzhiyi.mvp.model.user;

import android.util.Log;

import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.ConfigEntity;
import com.part.jianzhiyi.model.entity.UMEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.mvp.contract.user.LoginContract;
import com.part.jianzhiyi.preference.PreferenceUUID;

import io.reactivex.Observable;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/9/25
 * @modified by:shixinxin
 **/
public class LoginModel extends UserModel implements LoginContract.ILoginModel {
    @Override
    public Observable<ResponseData<String>> sendSms(String phone) {
        long currentTimeMillis = System.currentTimeMillis();
        String content = currentTimeMillis + "gzhl" + phone.substring(0, 6);
        Log.i(HttpAPI.class.getSimpleName(), "content:" + content);
        return HttpAPI.getInstence().getServiceApi().sendSms(Constants.APPID, phone, Constants.OS, String.valueOf(currentTimeMillis), Tools.md5(content), Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }

    @Override
    public Observable<String> login(String phone, String code) {
        return HttpAPI.getInstence(false).getScaleServiceApi().login(Constants.APPID, phone, code, Constants.OS, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()), HttpAPI.ip, Tools.getPhoneOSVersion(), Tools.getManufacturer(), Tools.getPhoneType(), Tools.getUa(), Tools.getUa2(ODApplication.context()), PreferenceUUID.getInstence().getPush_id(), PreferenceUUID.getInstence().getOaid());
    }

    @Override
    public Observable<ConfigEntity> getConfig() {
        return HttpAPI.getInstence().getServiceApi().getConfig(Constants.APPID, "2");
    }

    @Override
    public Observable<UMEntity> verifys(String token) {
        return HttpAPI.getInstence().getServiceApi().verifys(Constants.APPID, token);
    }

    @Override
    public Observable<ResponseData> getSussOrErrLog(String type, String status) {
        return HttpAPI.getInstence().getServiceApi().getSussOrErrLog(Constants.OS, Tools.getIMEI(ODApplication.context()), PreferenceUUID.getInstence().getOaid(), Tools.getPhoneOSVersion(), Tools.getManufacturer(), Tools.getPhoneType(), type, status);
    }

    @Override
    public Observable<UserInfoEntity> userInfo(String userid) {
        return HttpAPI.getInstence().getServiceApi().userInfo(Constants.APPID, userid, Constants.OS, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()), PreferenceUUID.getInstence().getPush_id());
    }

}
