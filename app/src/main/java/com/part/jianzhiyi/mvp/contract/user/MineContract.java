package com.part.jianzhiyi.mvp.contract.user;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.mvp.model.user.IUserModel;

import io.reactivex.Observable;

/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public interface MineContract {
    interface IMineModel extends IModel, IUserModel {
        //        LoginResponseEntity loadUserInfo(String userId);
        Observable<ResponseData<LoginResponseEntity>> userInfo(String userid);
    }

    interface IMineView extends IView {
        void updateUserInfo(LoginResponseEntity entity);

        void updateUserInfoPer(LoginResponseEntity loginResponseEntity);
    }
}
