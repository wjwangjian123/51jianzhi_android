package com.part.jianzhiyi.mvp.contract.user;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.AddSignEntity;
import com.part.jianzhiyi.model.entity.ConfigEntity;
import com.part.jianzhiyi.model.entity.DaySignEntity;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.model.entity.integral.SignInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MCheckVersionEntity;
import com.part.jianzhiyi.mvp.model.user.IUserModel;

import io.reactivex.Observable;

/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public interface MineContract {
    interface IMineModel extends IModel, IUserModel {
        Observable<UserInfoEntity> userInfo(String userid);

        Observable<DaySignEntity> getDaySign(String user_id);

        Observable<AddSignEntity> addDaySign(String user_id, String day);

        Observable<ResponseData> getDelUser(String user_id);

        Observable<ResponseData> getaddMd(String type);

        Observable<SignInfoEntity> getAddInteg(String user_id, int type, String job_id);

        Observable<MCheckVersionEntity> getCheck();

        Observable<ConfigEntity> getConfig();
    }

    interface IMineView extends IView {
        void updateUserInfo(LoginResponseEntity entity);

        void updateUserInfoPer(UserInfoEntity userInfoEntity);

        void updategetDaySign(DaySignEntity daySignEntity);

        void updateaddDaySign(AddSignEntity responseData);

        void updategetDelUser(ResponseData responseData);

        void updategetaddMd(ResponseData responseData);

        void updategetAddInteg(SignInfoEntity responseData);

        void updategetCheck(MCheckVersionEntity mCheckVersionEntity);

        void updategetConfig(ConfigEntity configEntity);
    }
}
