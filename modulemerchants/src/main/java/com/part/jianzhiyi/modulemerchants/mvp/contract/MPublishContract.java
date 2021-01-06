package com.part.jianzhiyi.modulemerchants.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableContactEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableSalaryEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MUserInfoEntity;

import io.reactivex.Observable;

public interface MPublishContract {

    interface IMPublishModel extends IModel {
        Observable<MLableEntity> getMLabel(String type);

        Observable<MLableSalaryEntity> getMLabelMethod(String type);

        Observable<MLableSalaryEntity> getMLabelSalary(String type);

        Observable<MLableContactEntity> getMLabelContact(String type);

        Observable<ResponseData> getIsSing();

        Observable<ResponseData> getCheckJob(String label_id, String job_id, String ther);

        Observable<ResponseData> getAddJob(String title, String method, String time, String sex, String price, String content, String contact, int contact_type, String number, String place, String duration, String type, String id, String label_id, String price2, String age1, String age2);

        Observable<MUserInfoEntity> getMerUserinfo();

        Observable<ResponseData> getmdAdd(String type);

        Observable<ResponseData> getTextFilter(String title, String content, String duration, String place, String contact);
    }

    interface IMPublishView extends IView {
        void updategetMLabel(MLableEntity mLableEntity);

        void updategetMLabelMethod(MLableSalaryEntity mLableSalaryEntity);

        void updategetMLabelSalary(MLableSalaryEntity mLableSalaryEntity);

        void updategetMLabelContact(MLableContactEntity mLableContactEntity);

        void updategetIsSing(ResponseData responseData);

        void updategetCheckJob(ResponseData responseData);

        void updategetAddJob(ResponseData responseData);

        void updategetMerUserinfo(MUserInfoEntity mUserInfoEntity);

        void updategetmdAdd(ResponseData responseData);

        void updategetTextFilter(ResponseData responseData);
    }
}
