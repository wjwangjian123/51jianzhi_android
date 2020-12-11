package com.part.jianzhiyi.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.BannerEntity;
import com.part.jianzhiyi.model.entity.CategoryEntity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.model.entity.LableEntity;
import com.part.jianzhiyi.model.entity.SearchEntity;
import com.part.jianzhiyi.model.entity.integral.SignEntity;
import com.part.jianzhiyi.model.entity.integral.SignInfoEntity;
import com.part.jianzhiyi.mvp.model.user.IUserModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public interface HomeContract {
    interface IHomeModel extends IModel, IUserModel {
        Observable<ResponseData<JobListResponseEntity2>> jobList(String userid, String type, String position, int page, String label);

        Observable<ResponseData<List<BannerEntity>>> getBanner();

        Observable<ResponseData> getBannerUrl(String imei);

        Observable<ResponseData<List<CategoryEntity>>> getHomeCategory();

        Observable<SearchEntity> search(String title, String lable);

        Observable<LableEntity> getHomeLabel();

        Observable<SignEntity> getSignInfos(String user_id);

        Observable<SignInfoEntity> getAddInteg(String user_id, int type, String job_id);

        Observable<ResponseData> getaddMd(String type);
    }

    interface IHomeView extends IView {
        void updateNewList(String position, List<JobListResponseEntity2.DataBean> dataBeanList);

        void updateAdvertising(String postion, JobListResponseEntity2.AdvertisingBean bean);

        void updateBanner(List<BannerEntity> bannerEntityList);

        void updategetBannerUrl(ResponseData responseData);

        void updateCategory(List<CategoryEntity> categoryEntityList);

        void updatesearch(SearchEntity searchEntity);

        void updategetHomeLabel(LableEntity searchEntity);

        void updategetSignInfos(SignEntity signEntity);

        void updategetAddInteg(SignInfoEntity responseData);

        void updategetaddMd(ResponseData responseData);
    }
}
