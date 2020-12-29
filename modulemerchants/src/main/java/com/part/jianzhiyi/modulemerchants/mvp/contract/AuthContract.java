package com.part.jianzhiyi.modulemerchants.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MAuthInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MAuthSuccessEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MEnterpriseInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MFileEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MGetEnterpriseInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MIDFaPositiveEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MIDPositiveEntity;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public interface AuthContract {

    interface IAuthModel extends IModel {
        Observable<MIDPositiveEntity> getBaidNumber(RequestBody requestBody);

        Observable<MAuthSuccessEntity> getCheckEnterprise(Map<String, Object> obj);

        Observable<MAuthSuccessEntity> getNumidSuccess(String img_z, String name, String number, String img_f, String company);

        Observable<MEnterpriseInfoEntity> getEnterprise(RequestBody requestBody);

        Observable<MIDFaPositiveEntity> getCorporate(RequestBody requestBody);

        Observable<MAuthInfoEntity> getNumid();

        Observable<MFileEntity> getUpload(RequestBody requestBody);

        Observable<MGetEnterpriseInfoEntity> getEnterpriseInfo();

        Observable<ResponseData> getmdAdd(String type);
    }

    interface IAuthView extends IView {
        void updategetBaidNumber(MIDPositiveEntity midPositiveEntity);

        void updategetCheckEnterprise(MAuthSuccessEntity mAuthSuccessEntity);

        void updategetNumidSuccess(MAuthSuccessEntity mAuthSuccessEntity);

        void updategetEnterprise(MEnterpriseInfoEntity mEnterpriseInfoEntity);

        void updategetCorporate(MIDFaPositiveEntity midFaPositiveEntity);

        void updategetNumid(MAuthInfoEntity mAuthInfoEntity);

        void updategetUpload(MFileEntity mFileEntity);

        void updategetEnterpriseInfo(MGetEnterpriseInfoEntity mGetEnterpriseInfoEntity);

        void updategetmdAdd(ResponseData responseData);
    }
}
