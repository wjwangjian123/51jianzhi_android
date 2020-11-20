package com.part.jianzhiyi.mvp.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.BannerEntity;
import com.part.jianzhiyi.model.entity.CategoryEntity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.model.entity.LableEntity;
import com.part.jianzhiyi.model.entity.SearchEntity;
import com.part.jianzhiyi.model.entity.integral.SignEntity;
import com.part.jianzhiyi.model.entity.integral.SignInfoEntity;
import com.part.jianzhiyi.mvp.contract.HomeContract;
import com.part.jianzhiyi.mvp.model.HomeModel;
import com.part.jianzhiyi.preference.PreferenceUUID;

import java.util.List;


/**
 * @author:shixinxin
 * @content：内容
 * @vision:V1.0.1
 **/
public class HomePresenter extends BasePresenter<HomeContract.IHomeModel, HomeContract.IHomeView> {
    public HomePresenter(HomeContract.IHomeView mView) {
        super(mView, new HomeModel());
    }

    public void jobList(String type, String position, int page, String label) {
        mModel.jobList(PreferenceUUID.getInstence().getUserId(), type, position, page, label)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<JobListResponseEntity2>>() {
                    @Override
                    public void onNext(ResponseData<JobListResponseEntity2> responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                JobListResponseEntity2 data = responseData.getData();
                                weakReferenceView.get().updateNewList(position, data.getData());
                                weakReferenceView.get().updateAdvertising(position, data.getAdvertising());
                            }
                        }
                    }
                }));
    }

    public void getBanner() {
        mModel.getBanner()
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<List<BannerEntity>>>() {
                    @Override
                    public void onNext(ResponseData<List<BannerEntity>> responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                List<BannerEntity> data = responseData.getData();
                                weakReferenceView.get().updateBanner(data);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.i("tag", "解析异常");
                    }
                }));
    }

    public void getBannerUrl(String imei) {
        mModel.getBannerUrl(imei)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetBannerUrl(responseData);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.i("tag", "解析异常");
                    }
                }));
    }

    public void getCategory() {
        mModel.getHomeCategory()
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData<List<CategoryEntity>>>() {
                    @Override
                    public void onNext(ResponseData<List<CategoryEntity>> responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                List<CategoryEntity> data = responseData.getData();
                                weakReferenceView.get().updateCategory(data);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.i("tag", "解析异常");
                    }
                }));
    }

    public void search(String title, String lable) {
        mModel.search(title, lable)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<SearchEntity>() {
                    @Override
                    public void onNext(SearchEntity searchEntity) {
                        if (TextUtils.equals(searchEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updatesearch(searchEntity);
                            }
                        }
                    }
                }));
    }

    public void getHomeLabel() {
        mModel.getHomeLabel()
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<LableEntity>() {
                    @Override
                    public void onNext(LableEntity lableEntity) {
                        if (TextUtils.equals(lableEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetHomeLabel(lableEntity);
                            }
                        }
                    }

                }));
    }

    public void getSignInfos(String user_id) {
        mModel.getSignInfos(user_id)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<SignEntity>() {
                    @Override
                    public void onNext(SignEntity signEntity) {
                        if (TextUtils.equals(signEntity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetSignInfos(signEntity);
                            }
                        }
                    }

                }));
    }

    public void getAddInteg(String user_id, int type, String job_id) {
        mModel.getAddInteg(user_id, type, job_id)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<SignInfoEntity>() {
                    @Override
                    public void onNext(SignInfoEntity entity) {
                        if (TextUtils.equals(entity.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetAddInteg(entity);
                            }
                        }else {
                            if (isAttach()) {
                                weakReferenceView.get().updategetAddInteg(entity);
                            }
                        }
                    }
                }));
    }
}
