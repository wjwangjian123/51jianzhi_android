package com.part.jianzhiyi.mvp.model;

import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.model.entity.ViewedEntity;
import com.part.jianzhiyi.mvp.contract.ChoiceContract;
import com.part.jianzhiyi.preference.PreferenceUUID;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/9/28
 * @modified by:shixinxin
 **/
public class JobListModel implements ChoiceContract.IChoiceModel {
    @Override
    public Observable<ResponseData<JobListResponseEntity2>> jobList(String userid, String type, String position, int page) {
        return HttpAPI.getInstence().getServiceApi().jobList(String.valueOf(page), Constants.APPID, PreferenceUUID.getInstence().getUserId(), type, position, "0", HttpAPI.ip, Constants.OS, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }

    @Override
    public Observable<ViewedEntity> viewedJob(String userid) {
        return HttpAPI.getInstence().getServiceApi().viewedJob(Constants.APPID, userid, Constants.OS, HttpAPI.ip, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }

    @Override
    public Observable<ResponseData<List<JobListResponseEntity>>> inviteJob(String userid) {
        return HttpAPI.getInstence().getServiceApi().inviteJob(Constants.APPID, userid, Constants.OS, HttpAPI.ip, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }

    @Override
    public Observable<ResponseData<List<JobListResponseEntity>>> joinedJob(String userid) {
        return HttpAPI.getInstence().getServiceApi().joinedJob(Constants.APPID, userid, Constants.OS, HttpAPI.ip, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }

    @Override
    public Observable<ResponseData<List<JobListResponseEntity>>> arrivedJob(String userid) {
        return HttpAPI.getInstence().getServiceApi().arrivedJob(Constants.APPID, userid, Constants.OS, HttpAPI.ip, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }

    @Override
    public Observable<ResponseData<List<JobListResponseEntity>>> approvedJob(String userid) {
        return HttpAPI.getInstence().getServiceApi().approvedJob(Constants.APPID, userid, Constants.OS, HttpAPI.ip, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }

    @Override
    public Observable<ResponseData<List<JobListResponseEntity>>> donedJob(String userid) {
        return HttpAPI.getInstence().getServiceApi().donedJob(Constants.APPID, userid, Constants.OS, HttpAPI.ip, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }
}
