package com.part.jianzhiyi.mvp.model.user;

import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddSignEntity;
import com.part.jianzhiyi.model.entity.DaySignEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.mvp.contract.user.MineContract;

import io.reactivex.Observable;


/**
 * @author:
 * @content：我的页面数据
 * @vision:V1.0.1
 **/
public class MineModel extends UserModel implements MineContract.IMineModel {
    @Override
    public Observable<UserInfoEntity> userInfo(String userid) {
        return HttpAPI.getInstence().getServiceApi().userInfo(Constants.APPID, userid, Constants.OS, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()), PreferenceUUID.getInstence().getPush_id());
    }

    @Override
    public Observable<DaySignEntity> getDaySign(String user_id) {
        return HttpAPI.getInstence().getServiceApi().getDaySign(user_id);
    }

    @Override
    public Observable<AddSignEntity> addDaySign(String user_id, String day) {
        return HttpAPI.getInstence().getServiceApi().addDaySign(user_id, day, Constants.OS);
    }

    @Override
    public Observable<ResponseData> getDelUser(String user_id) {
        return HttpAPI.getInstence().getServiceApi().getDelUser(user_id);
    }

    @Override
    public Observable<ResponseData> getaddMd(String type) {
        return HttpAPI.getInstence().getServiceApi().getaddMd(type, Constants.OS);
    }
}
