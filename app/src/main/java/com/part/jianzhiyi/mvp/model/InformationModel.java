package com.part.jianzhiyi.mvp.model;

import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddFavouriteResponseEntity;
import com.part.jianzhiyi.model.entity.ChatJobInfoEntity;
import com.part.jianzhiyi.model.entity.MsgResponseEntity;
import com.part.jianzhiyi.model.entity.ViewedEntity;
import com.part.jianzhiyi.mvp.contract.InformationContract;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/9/28
 * @modified by:shixinxin
 **/
public class InformationModel implements InformationContract.IInformationModel{
    @Override
    public Observable<ResponseData<List<MsgResponseEntity>>> msgList(String userid) {
        return HttpAPI.getInstence().getServiceApi().msgList(Constants.APPID,userid,Constants.OS, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));    }

    @Override
    public Observable<ChatJobInfoEntity> getChatJobinfo(String id) {
        return HttpAPI.getInstence().getServiceApi().getChatJobinfo(Constants.APPID,id);
    }

    @Override
    public Observable<ViewedEntity> viewedJob(String userid) {
        return HttpAPI.getInstence().getServiceApi().viewedJob(Constants.APPID,userid,Constants.OS,HttpAPI.ip, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));    }

    @Override
    public Observable<ResponseData<AddFavouriteResponseEntity>> joincopyContact(String appid, String user_id, String job_id, String sortId, String contact, int type) {
        return HttpAPI.getInstence().getServiceApi().joincopyContact(Constants.APPID, user_id, job_id, Constants.OS, HttpAPI.ip, sortId, contact, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()),type);
    }

}
