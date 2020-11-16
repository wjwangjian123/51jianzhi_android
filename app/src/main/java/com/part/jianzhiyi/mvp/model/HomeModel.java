package com.part.jianzhiyi.mvp.model;

import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.BannerEntity;
import com.part.jianzhiyi.model.entity.CategoryEntity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.model.entity.LableEntity;
import com.part.jianzhiyi.model.entity.SearchEntity;
import com.part.jianzhiyi.mvp.contract.HomeContract;
import com.part.jianzhiyi.mvp.model.user.UserModel;
import com.part.jianzhiyi.preference.PreferenceUUID;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author:shixinxin
 * @content：内容
 **/
public class HomeModel extends UserModel implements HomeContract.IHomeModel {
    @Override
    public Observable<ResponseData<JobListResponseEntity2>> jobList(String userid, String type, String position, int page,String label) {
        return HttpAPI.getInstence().getServiceApi().jobList(String.valueOf(page), Constants.APPID, PreferenceUUID.getInstence().getUserId(), type, position, "0", HttpAPI.ip, Constants.OS, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()),label);
    }

    @Override
    public Observable<ResponseData<List<BannerEntity>>> getBanner() {
        return HttpAPI.getInstence().getServiceApi().getBanner(Constants.APPID);
    }

    @Override
    public Observable<ResponseData> getBannerUrl(String imei) {
        return HttpAPI.getInstence().getServiceApi().getBannerUrl(imei,PreferenceUUID.getInstence().getUserId());
    }

    @Override
    public Observable<ResponseData<List<CategoryEntity>>> getHomeCategory() {
        return HttpAPI.getInstence().getServiceApi().getHomeCategory(Constants.APPID);
    }

    @Override
    public Observable<SearchEntity> search(String title, String lable) {
        return HttpAPI.getInstence().getServiceApi().search(Constants.APPID,title, lable, HttpAPI.ip);
    }

    @Override
    public Observable<LableEntity> getHomeLabel() {
        return HttpAPI.getInstence().getServiceApi().getHomeLabel();
    }
}
