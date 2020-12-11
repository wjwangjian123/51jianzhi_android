package com.part.jianzhiyi.mvp.presenter.mine;

import android.text.TextUtils;
import android.util.Log;

import com.part.jianzhiyi.base.BasePresenter;
import com.part.jianzhiyi.http.HttpAPI;
import com.part.jianzhiyi.http.ResultObserver;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.entity.MyitemEntity;
import com.part.jianzhiyi.model.entity.ResumeEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.model.request.UResumeRequest;
import com.part.jianzhiyi.mvp.contract.mine.MineUpdateResumeContract;
import com.part.jianzhiyi.mvp.model.mine.MineUpdateResumeModel;
import com.part.jianzhiyi.corecommon.preference.PreferenceUUID;

/**
 * @author:
 * @content：我的信息
 * @vision:V1.0.1
 **/
public class MineUpdateResumePresenter extends BasePresenter<MineUpdateResumeContract.IMineUpdateResumeModel, MineUpdateResumeContract.IMineUpdateResumeView> {
    public MineUpdateResumePresenter(MineUpdateResumeContract.IMineUpdateResumeView mView) {
        super(mView, new MineUpdateResumeModel());
    }

    public void loadUserInfo() {
        LoginResponseEntity loginResponseEntity = mModel.loadUserInfo();
        if (loginResponseEntity != null && isAttach()) {
            weakReferenceView.get().updateUserInfo(loginResponseEntity);
        } else {
            PreferenceUUID.getInstence().loginOut();
        }

    }

    public void updateResumeV2(String name, String sex, String age, String school_year, String school_name, String experience, String introduce,int profession,int job_status,String job_type,String myitem,String expect,String profession1,String job_status1,String job_type1) {
        UResumeRequest request = new UResumeRequest(PreferenceUUID.getInstence().getUserId(), name, sex, age, school_year, school_name, experience, introduce,profession,job_status,job_type,myitem,expect);
        mModel.updateResumeV2(request)
                .compose(schedulersTransformer(HttpAPI.LOADING_CUSTOM_TIME))
                .subscribe(getResult(new ResultObserver<ResumeEntity>() {
                    @Override
                    public void onNext(ResumeEntity resumeEntity) {
                        if (resumeEntity.getCode().equals(HttpAPI.REQUEST_SUCCESS)) {
                            LoginResponseEntity loginResponseEntity = mModel.loadUserInfo();
                            loginResponseEntity.setName(name);
                            loginResponseEntity.setSex(sex);
                            loginResponseEntity.setAge(age);
                            loginResponseEntity.setSchool_year(school_year);
                            loginResponseEntity.setSchool_name(school_name);
                            loginResponseEntity.setExperience(experience);
                            loginResponseEntity.setIntroduce(introduce);
                            loginResponseEntity.setProfession(profession1);
                            loginResponseEntity.setJob_status(job_status1);
                            loginResponseEntity.setJob_type(job_type1);
                            mModel.insertOrUpdateDb(loginResponseEntity);
                            PreferenceUUID.getInstence().changeShowResume(resumeEntity.getData().isShowResume());
                            weakReferenceView.get().updateupdateResumeV2(resumeEntity);
                        } else {
                            if (isAttach()) {
                                weakReferenceView.get().showToast(resumeEntity.getMsg());
                            }
                        }
                    }
                }));
    }

    public void userInfo(String userid) {
        mModel.userInfo(userid)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<UserInfoEntity>() {
                    @Override
                    public void onNext(UserInfoEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                PreferenceUUID.getInstence().changeShowResume(responseData.getData().isShowResume());
                                weakReferenceView.get().updateUserInfoPer(responseData);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e("tag", "解析异常");
                    }
                }));
    }

    public void getMyitem(String type) {
        mModel.getMyitem(type)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<MyitemEntity>() {
                    @Override
                    public void onNext(MyitemEntity responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetMyitem(responseData);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.e("tag", "解析异常");
                    }
                }));
    }

    public void getaddMd(String type) {
        mModel.getaddMd(type)
                .compose(schedulersTransformer(HttpAPI.LOADING_NONE_TIME))
                .subscribe(getResult(new ResultObserver<ResponseData>() {
                    @Override
                    public void onNext(ResponseData responseData) {
                        if (TextUtils.equals(responseData.getCode(), HttpAPI.REQUEST_SUCCESS)) {
                            if (isAttach()) {
                                weakReferenceView.get().updategetaddMd(responseData);
                            }
                        }
                    }
                }));
    }
}
