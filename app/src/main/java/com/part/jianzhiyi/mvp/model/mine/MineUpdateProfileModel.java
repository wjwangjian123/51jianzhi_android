package com.part.jianzhiyi.mvp.model.mine;

import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.request.UpdateProfileRequest;
import com.part.jianzhiyi.mvp.contract.mine.MineUpdateProfileContract;
import com.part.jianzhiyi.mvp.model.user.UserModel;

import io.reactivex.Observable;

/**
 * @author:
 * @content：内容
 * @vision:V1.0.1
 **/
public class MineUpdateProfileModel extends UserModel implements MineUpdateProfileContract.IMineUpdateProfileModel {
    @Override
    public Observable<ResponseData<String>> updateProfile(UpdateProfileRequest request) {

        return HttpAPI.getInstence().getServiceApi().updateProfile(Constants.APPID, request.getUsername(), request.getUser_id(), request.getSignature(), request.getPhone(), request.getEmail(), Constants.OS, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));
    }

    @Override
    public Observable<ResponseData> getaddMd(String type) {
        return HttpAPI.getInstence().getServiceApi().getaddMd(type, Constants.OS);
    }
}
