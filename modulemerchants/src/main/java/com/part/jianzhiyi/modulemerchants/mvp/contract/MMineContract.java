package com.part.jianzhiyi.modulemerchants.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MCheckVersionEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MConfigEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MUserInfoEntity;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public interface MMineContract {

    interface IMMineModel extends IModel {
        Observable<MUserInfoEntity> getMerUserinfo();

        Observable<ResponseData> getAvatar(RequestBody requestBody);

        Observable<ResponseData> getOpinion(String reason, String con, String phone);

        Observable<ResponseData> getmdAdd(String type);

        Observable<MCheckVersionEntity> getCheck();

        Observable<MConfigEntity> getConfig();
    }

    interface IMMineView extends IView {
        void updategetMerUserinfo(MUserInfoEntity mUserInfoEntity);

        void updategetAvatar(ResponseData responseData);

        void updategetOpinion(ResponseData responseData);

        void updategetmdAdd(ResponseData responseData);

        void updategetCheck(MCheckVersionEntity mCheckVersionEntity);

        void updategetConfig(MConfigEntity mConfigEntity);
    }
}
