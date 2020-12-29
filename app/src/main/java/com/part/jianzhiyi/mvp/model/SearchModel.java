package com.part.jianzhiyi.mvp.model;

import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;
import com.part.jianzhiyi.model.entity.LableEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.SearchEntity;
import com.part.jianzhiyi.mvp.contract.SearchContract;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author:
 * @content：内容
 * @vision:V1.0.1
 **/
public class SearchModel implements SearchContract.ISearchModel{
    @Override
    public Observable<ResponseData<List<JobListResponseEntity>>> jobSearch(String userid, String key) {
        return HttpAPI.getInstence().getServiceApi().jobSearch(Constants.APPID,userid,key,Constants.OS,"0", HttpAPI.ip,Constants.POSITION_SERACH, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));    }

    @Override
    public Observable<LableEntity> getLable() {
        return HttpAPI.getInstence().getServiceApi().getLable();
    }

    @Override
    public Observable<SearchEntity> search(String title, String lable) {
        return HttpAPI.getInstence().getServiceApi().search(Constants.APPID,title, lable, HttpAPI.ip);
    }

    @Override
    public Observable<ResponseData> getaddMd(String type) {
        return HttpAPI.getInstence().getServiceApi().getaddMd(type, Constants.OS);
    }
}
