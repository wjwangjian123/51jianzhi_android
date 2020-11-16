package com.part.jianzhiyi.mvp.model;

import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.mvp.contract.RankContract;
import com.part.jianzhiyi.mvp.model.user.UserModel;
import com.part.jianzhiyi.preference.PreferenceUUID;

import io.reactivex.Observable;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/10/13
 * @modified by:shixinxin
 **/
public class RankModel extends UserModel implements RankContract.IRankModel{

    @Override
    public Observable<ResponseData<JobListResponseEntity2>> jobList(String type, String position, int page,String label) {
        return HttpAPI.getInstence().getServiceApi().jobList(String.valueOf(page), Constants.APPID, PreferenceUUID.getInstence().getUserId(),type,position,"0",HttpAPI.ip,Constants.OS, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()),label);
    }
}
