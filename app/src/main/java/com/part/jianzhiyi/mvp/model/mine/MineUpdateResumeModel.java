package com.part.jianzhiyi.mvp.model.mine;

import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.entity.ResumeEntity;
import com.part.jianzhiyi.model.request.UResumeRequest;
import com.part.jianzhiyi.model.request.UpdateResumeRequest;
import com.part.jianzhiyi.mvp.contract.mine.MineUpdateResumeContract;
import com.part.jianzhiyi.mvp.model.user.UserModel;
import com.part.jianzhiyi.preference.PreferenceUUID;

import io.reactivex.Observable;

/**
 * @author:
 * @content：内容
 * @vision:V1.0.1
 **/
public class MineUpdateResumeModel extends UserModel implements MineUpdateResumeContract.IMineUpdateResumeModel {
    @Override
    public Observable<ResponseData<String>> updateResume(UpdateResumeRequest request) {
        return HttpAPI.getInstence().getServiceApi().updateResume(Constants.APPID, request.getUser_id(), request.getName(), request.getSex(), request.getAge(), request.getSchool_year(), request.getSchool_name(), request.getExperience(), request.getIntroduce(), Constants.OS, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }

    @Override
    public Observable<ResumeEntity> updateResumeV2(UResumeRequest request) {
        return HttpAPI.getInstence().getServiceApi().updateResumeV2(request.getUser_id(), request.getName(), request.getSex(), request.getAge(), request.getSchool_year(), request.getSchool_name(), request.getExperience(), request.getIntroduce(), Constants.APPID, request.getProfession(), request.getJob_status(), request.getJob_type(), Constants.OS, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }

    @Override
    public Observable<ResponseData<LoginResponseEntity>> userInfo(String userid) {
        return HttpAPI.getInstence().getServiceApi().userInfo(Constants.APPID, userid, Constants.OS, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()), PreferenceUUID.getInstence().getPush_id());
    }
}
