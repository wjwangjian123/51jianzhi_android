package com.part.jianzhiyi.mvp.model.mine;

import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddFavouriteResponseEntity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;
import com.part.jianzhiyi.mvp.contract.mine.MineFavouriteContract;

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
public class MineFavouriteModel implements MineFavouriteContract.IMineFavouriteModel {
    @Override
    public Observable<ResponseData<AddFavouriteResponseEntity>> addFavourite(String userid, String job_id, String sortId) {
        return HttpAPI.getInstence().getServiceApi().addFavourite(Constants.APPID, userid, job_id, Constants.OS, HttpAPI.ip, sortId, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }

    @Override
    public Observable<ResponseData<AddFavouriteResponseEntity>> cancelFavourite(String userid, String job_id, String sortId) {
        return HttpAPI.getInstence().getServiceApi().cancelFavourite(Constants.APPID, userid, job_id, Constants.OS, HttpAPI.ip, "2", Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }

    @Override
    public Observable<ResponseData<List<JobListResponseEntity>>> favourite(String userid) {
        return HttpAPI.getInstence().getServiceApi().favourite(Constants.APPID, userid, Constants.OS, HttpAPI.ip, "0", Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }


}
