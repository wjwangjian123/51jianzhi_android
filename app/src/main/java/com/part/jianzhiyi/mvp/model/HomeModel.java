package com.part.jianzhiyi.mvp.model;

import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.BannerEntity;
import com.part.jianzhiyi.model.entity.CategoryEntity;
import com.part.jianzhiyi.model.entity.CityIdEntity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.model.entity.LableEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.model.entity.integral.SignEntity;
import com.part.jianzhiyi.model.entity.integral.SignInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.SearchEntity;
import com.part.jianzhiyi.mvp.contract.HomeContract;
import com.part.jianzhiyi.mvp.model.user.UserModel;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author:shixinxin
 * @content：内容
 **/
public class HomeModel extends UserModel implements HomeContract.IHomeModel {
    @Override
    public Observable<ResponseData<JobListResponseEntity2>> jobList(String userid, String type, String position, int page, String label) {
        return HttpAPI.getInstence().getServiceApi().jobList(String.valueOf(page), Constants.APPID, PreferenceUUID.getInstence().getUserId(), type, position, "0", HttpAPI.ip, Constants.OS, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()), label);
    }

    @Override
    public Observable<ResponseData<List<BannerEntity>>> getBanner() {
        return HttpAPI.getInstence().getServiceApi().getBanner(Constants.APPID, Constants.OS, Constants.OURVERSION);
    }

    @Override
    public Observable<ResponseData> getBannerUrl(String imei) {
        return HttpAPI.getInstence().getServiceApi().getBannerUrl(imei, PreferenceUUID.getInstence().getUserId());
    }

    @Override
    public Observable<ResponseData<List<CategoryEntity>>> getHomeCategory() {
        return HttpAPI.getInstence().getServiceApi().getHomeCategory(Constants.APPID);
    }

    @Override
    public Observable<SearchEntity> search(String title, String lable) {
        return HttpAPI.getInstence().getServiceApi().search(Constants.APPID, title, lable, HttpAPI.ip);
    }

    @Override
    public Observable<LableEntity> getHomeLabel() {
        return HttpAPI.getInstence().getServiceApi().getHomeLabel();
    }

    @Override
    public Observable<SignEntity> getSignInfos(String user_id) {
        return HttpAPI.getInstence().getServiceApi().getSignInfos(user_id);
    }

    @Override
    public Observable<SignInfoEntity> getAddInteg(String user_id, int type, String job_id) {
        return HttpAPI.getInstence().getServiceApi().getAddInteg(user_id, type, job_id);
    }

    @Override
    public Observable<ResponseData> getaddMd(String type) {
        return HttpAPI.getInstence().getServiceApi().getaddMd(type, Constants.OS);
    }

    @Override
    public Observable<UserInfoEntity> userInfo(String userid) {
        return HttpAPI.getInstence().getServiceApi().userInfo(Constants.APPID, userid, Constants.OS, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()), PreferenceUUID.getInstence().getPush_id());
    }

    @Override
    public Observable<CityIdEntity> getCityId(String city_name) {
        return HttpAPI.getInstence().getServiceApi().getCityId(city_name);
    }

    @Override
    public Observable<ResponseData> getUserCity(int city_id, String user_id) {
        return HttpAPI.getInstence().getServiceApi().getUserCity(city_id, user_id);
    }
}
