package com.part.jianzhiyi.mvp.model;

import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.entity.ActJobListEntity;
import com.part.jianzhiyi.model.entity.ActivityEntity;
import com.part.jianzhiyi.model.entity.ConfigEntity;
import com.part.jianzhiyi.model.entity.IpResponseEntity;
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
        return HttpAPI.getInstence().getServiceApi().getAction();
    }

    @Override
    public Observable<ActJobListEntity> getActJobList(String id) {
        return HttpAPI.getInstence().getServiceApi().getActJobList(Constants.APPID,id,HttpAPI.ip);
    }

    @Override
    public Observable<ConfigEntity> getConfig() {
        return HttpAPI.getInstence().getServiceApi().getConfig(Constants.APPID,"2");
    }
}
