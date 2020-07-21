package com.part.jianzhiyi.mvp.contract.mine;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.request.UpdateProfileRequest;
import com.part.jianzhiyi.mvp.model.user.IUserModel;

import io.reactivex.Observable;
import retrofit2.http.Body;

/**
 * @author:
 * @content：内容
 * @vision:V1.0.1
 **/
public interface MineUpdateProfileContract {
    interface IMineUpdateProfileModel extends IModel, IUserModel {
        Observable<ResponseData<String>> updateProfile(@Body UpdateProfileRequest request);
    }

    interface IMineUpdateProfileView extends IView {
        void updateSuccess();
        void updateUserInfo(LoginResponseEntity entity);
    }


}
