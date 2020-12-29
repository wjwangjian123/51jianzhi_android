package com.part.jianzhiyi.mvp.model;

import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.ActJobListEntity;
import com.part.jianzhiyi.model.entity.ActivityEntity;
import com.part.jianzhiyi.model.entity.ConfigEntity;
import com.part.jianzhiyi.model.entity.DelUserEntity;
import com.part.jianzhiyi.model.entity.IpResponseEntity;
import com.part.jianzhiyi.model.entity.integral.SignInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MCheckVersionEntity;
import com.part.jianzhiyi.mvp.contract.MainContract;

import io.reactivex.Observable;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/9/23
 * @modified by:shixinxin
 **/
public class MainModel implements MainContract.IMainModel {
    @Override
    public Observable<IpResponseEntity> getIp() {
        return HttpAPI.getInstence().getServiceApi().getIp(Constants.IP_URI);
    }

    @Override
    public Observable<ActivityEntity> getAction() {
        return HttpAPI.getInstence().getServiceApi().getAction(Constants.OS, Constants.OURVERSION, Constants.APPID);
    }

    @Override
    public Observable<ActJobListEntity> getActJobList(String id, String type) {
        return HttpAPI.getInstence().getServiceApi().getActJobList(Constants.APPID, id, HttpAPI.ip, type);
    }

    @Override
    public Observable<ConfigEntity> getConfig() {
        return HttpAPI.getInstence().getServiceApi().getConfig(Constants.APPID, "2");
    }

    @Override
    public Observable<ResponseData> getaddMd(String type) {
        return HttpAPI.getInstence().getServiceApi().getaddMd(type, Constants.OS);
    }

    @Override
    public Observable<SignInfoEntity> getAddInteg(String user_id, int type, String job_id) {
        return HttpAPI.getInstence().getServiceApi().getAddInteg(user_id, type, job_id);
    }

    @Override
    public Observable<MCheckVersionEntity> getCheck() {
        return HttpAPI.getInstence().getServiceApi().getCheck(Constants.OS, Constants.APPID);
    }

    @Override
    public Observable<DelUserEntity> getIsDel() {
        return HttpAPI.getInstence().getServiceApi().getIsDel(PreferenceUUID.getInstence().getUserId());
    }
}
