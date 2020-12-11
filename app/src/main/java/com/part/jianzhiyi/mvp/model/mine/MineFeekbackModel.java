package com.part.jianzhiyi.mvp.model.mine;

import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddFavouriteResponseEntity;
import com.part.jianzhiyi.mvp.contract.mine.MineFeekbackContract;
import com.part.jianzhiyi.mvp.model.user.UserModel;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;

import io.reactivex.Observable;

/**
 * @author:
 * @content：内容
 * @vision:V1.0.1
 **/
public class MineFeekbackModel extends UserModel implements MineFeekbackContract.IMineFeekbackModel {

    @Override
    public Observable<ResponseData<AddFavouriteResponseEntity>> jobfeedback(String userid, String content, String contact) {
        return HttpAPI.getInstence().getServiceApi().jobfeedback(Constants.APPID, PreferenceUUID.getInstence().getUserId(), contact, content, Constants.OS, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }

    @Override
    public Observable<ResponseData> getaddMd(String type) {
        return HttpAPI.getInstence().getServiceApi().getaddMd(type, Constants.OS);
    }
}
