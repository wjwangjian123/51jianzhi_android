package com.part.jianzhiyi.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.ActJobListEntity;
import com.part.jianzhiyi.model.entity.ActivityEntity;
import com.part.jianzhiyi.model.entity.ConfigEntity;
import com.part.jianzhiyi.model.entity.DelUserEntity;
import com.part.jianzhiyi.model.entity.IpResponseEntity;
import com.part.jianzhiyi.model.entity.integral.SignInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MCheckVersionEntity;

import io.reactivex.Observable;

/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public interface MainContract {
    interface IMainModel extends IModel {
        Observable<IpResponseEntity> getIp();

        Observable<ActivityEntity> getAction();

        Observable<ActJobListEntity> getActJobList(String id, String type);

        Observable<ConfigEntity> getConfig();

        Observable<ResponseData> getaddMd(String type);

        Observable<SignInfoEntity> getAddInteg(String user_id, int type, String job_id);

        Observable<MCheckVersionEntity> getCheck();

        Observable<DelUserEntity> getIsDel();

    }

    interface IMainView extends IView {
        void updategetAction(ActivityEntity activityEntity);

        void updategetActJobList(ActJobListEntity actJobListEntity);

        void updategetConfig(ConfigEntity configEntity);

        void updategetaddMd(ResponseData responseData);

        void updategetAddInteg(SignInfoEntity responseData);

        void updategetCheck(MCheckVersionEntity mCheckVersionEntity);

        void updategetIsDel(DelUserEntity delUserEntity);

    }
}
