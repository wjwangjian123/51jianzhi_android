package com.part.jianzhiyi.mvp.contract.user;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.ConfigEntity;
import com.part.jianzhiyi.model.entity.UMEntity;
import com.part.jianzhiyi.mvp.model.user.IUserModel;

import io.reactivex.Observable;
import retrofit2.http.Query;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/9/25
 * @modified by:shixinxin
 **/
public interface LoginContract {
    interface ILoginModel extends IModel, IUserModel {

        Observable<ResponseData<String>> sendSms(@Query("tel") String phone);
        Observable<String> login(String phone, String code);
        Observable<ConfigEntity> getConfig();
        Observable<UMEntity> verifys(String token);
        Observable<ResponseData> getSussOrErrLog(String type, String status);
    }

    interface ILoginView extends IView {
        void updategetConfig(ConfigEntity configEntity);
        void updateverifys(UMEntity umEntity);
        void updatelogin(boolean showResume);
    }
}
