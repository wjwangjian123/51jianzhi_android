package com.part.jianzhiyi.mvp.contract.mine;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.entity.MyitemEntity;
import com.part.jianzhiyi.model.entity.ResumeEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.model.request.UResumeRequest;
import com.part.jianzhiyi.model.request.UpdateResumeRequest;
import com.part.jianzhiyi.mvp.model.user.IUserModel;

import io.reactivex.Observable;
import retrofit2.http.Body;

/**
 * @author:
 * @content：内容
 * @vision:V1.0.1
 **/
public interface MineUpdateResumeContract {
    interface IMineUpdateResumeModel extends IModel, IUserModel {
        Observable<ResponseData<String>> updateResume(@Body UpdateResumeRequest request);

        Observable<ResumeEntity> updateResumeV2(@Body UResumeRequest request);

        Observable<UserInfoEntity> userInfo(String userid);

        Observable<MyitemEntity> getMyitem(String type);

        Observable<ResponseData> getaddMd(String type);
    }

    interface IMineUpdateResumeView extends IView {
        void updateSuccess();

        void updateUserInfo(LoginResponseEntity entity);

        void updateupdateResumeV2(ResumeEntity resumeEntity);

        void updateUserInfoPer(UserInfoEntity userInfoEntity);

        void updategetMyitem(MyitemEntity myitemEntity);

        void updategetaddMd(ResponseData responseData);
    }


}
