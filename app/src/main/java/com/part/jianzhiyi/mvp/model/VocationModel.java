package com.part.jianzhiyi.mvp.model;

import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddFavouriteResponseEntity;
import com.part.jianzhiyi.model.entity.JobDetailEntity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;
import com.part.jianzhiyi.model.entity.JoinJobEntity;
import com.part.jianzhiyi.model.entity.VocationEntity;
import com.part.jianzhiyi.mvp.contract.VocationContract;
import com.part.jianzhiyi.mvp.model.user.UserModel;
import com.part.jianzhiyi.preference.PreferenceUUID;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/10/13
 * @modified by:shixinxin
 **/
public class VocationModel extends UserModel implements VocationContract.IVocationModel {
    @Override
    public Observable<ResponseData<AddFavouriteResponseEntity>> addFavourite(String userid, String job_id, String sortId) {
        return HttpAPI.getInstence().getServiceApi().addFavourite(Constants.APPID, userid, job_id, Constants.OS, HttpAPI.ip, sortId, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }

    @Override
    public Observable<ResponseData<AddFavouriteResponseEntity>> joinJob(String userid, String jobid, String sortId, String contact) {
        return HttpAPI.getInstence().getServiceApi().joinJob(Constants.APPID, userid, jobid, Constants.OS, HttpAPI.ip, sortId, contact, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }

    @Override
    public Observable<JoinJobEntity> joinJobV2(String userid, String jobid, String sortId, String contact) {
        return HttpAPI.getInstence().getServiceApi().joinJobV2(Constants.APPID, userid, jobid, Constants.OS, HttpAPI.ip, sortId, contact, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()), "4");
    }

    @Override
    public Observable<ResponseData<VocationEntity>> jobDetail(String jobId, String position, String sortId) {
        return HttpAPI.getInstence().getServiceApi().jobDetail(Constants.APPID, PreferenceUUID.getInstence().getUserId(), jobId, "4", Constants.OS, position, sortId, HttpAPI.ip, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }

    @Override
    public Observable<ResponseData<AddFavouriteResponseEntity>> cancelFavourite(String userid, String job_id, String sortId) {
        return HttpAPI.getInstence().getServiceApi().cancelFavourite(Constants.APPID, userid, job_id, Constants.OS, HttpAPI.ip, sortId, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }

    @Override
    public Observable<ResponseData<List<JobListResponseEntity>>> recommendList(String jobid) {
        return HttpAPI.getInstence().getServiceApi().recommendList(Constants.APPID, PreferenceUUID.getInstence().getUserId(), jobid, Constants.OS, HttpAPI.ip, "0", Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }

    @Override
    public Observable<JobDetailEntity> jobDetailv(String jobId, String position, String sort_id) {
        return HttpAPI.getInstence().getServiceApi().jobDetailv(Constants.APPID, PreferenceUUID.getInstence().getUserId(), jobId, "4", Constants.OS, position, sort_id, HttpAPI.ip, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }

    @Override
    public Observable<ResponseData> getSussOrErrLog(String type, String status) {
        return HttpAPI.getInstence().getServiceApi().getSussOrErrLog(Constants.OS, Tools.getIMEI(ODApplication.context()), PreferenceUUID.getInstence().getOaid(), Tools.getPhoneOSVersion(), Tools.getManufacturer(), Tools.getPhoneType(), type, status);
    }

    @Override
    public Observable<ResponseData<AddFavouriteResponseEntity>> joincopyContact(String appid, String user_id, String job_id, String sortId, String contact, int type) {
        return HttpAPI.getInstence().getServiceApi().joincopyContact(Constants.APPID, user_id, job_id, Constants.OS, HttpAPI.ip, sortId, contact, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()), type);
    }
}
