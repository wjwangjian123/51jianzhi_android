package com.part.jianzhiyi.mvp.model;

import com.part.jianzhiyi.app.ODApplication;
import com.part.jianzhiyi.constants.Constants;
import com.part.jianzhiyi.corecommon.utils.Tools;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.MsgResponseEntity;
import com.part.jianzhiyi.mvp.contract.SystemNotifyContract;

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
public class SystemNotifyModel implements SystemNotifyContract.ISystemNotifyModel {
    @Override
    public Observable<ResponseData<List<MsgResponseEntity>>> msgList(String userid) {
        return HttpAPI.getInstence().getServiceApi().msgList(Constants.APPID,userid,Constants.OS, Tools.getIMEI(ODApplication.context()), Tools.getDeviceID(ODApplication.context()));    }
}
