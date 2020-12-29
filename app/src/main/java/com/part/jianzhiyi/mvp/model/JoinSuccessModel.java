package com.part.jianzhiyi.mvp.model;

import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddFavouriteResponseEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.SearchEntity;
import com.part.jianzhiyi.mvp.contract.JoinSuccessContract;

import io.reactivex.Observable;

/**
 * @author:
 * @content：内容
 * @vision:V1.0.1
 **/
public class JoinSuccessModel implements JoinSuccessContract.IJoinSuccessModel{

    @Override
    public Observable<SearchEntity> search(String title, String lable) {
        return HttpAPI.getInstence().getServiceApi().search(Constants.APPID,title, lable, HttpAPI.ip);
    }

    @Override
    public Observable<ResponseData<AddFavouriteResponseEntity>> joincopyContact(String appid, String user_id, String job_id, String sortId, String contact, int type) {
        return HttpAPI.getInstence().getServiceApi().joincopyContact(Constants.APPID, user_id, job_id, Constants.OS, HttpAPI.ip, sortId, contact, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()),type);
    }

    @Override
    public Observable<ResponseData> getaddMd(String type) {
        return HttpAPI.getInstence().getServiceApi().getaddMd(type, Constants.OS);
    }
}
