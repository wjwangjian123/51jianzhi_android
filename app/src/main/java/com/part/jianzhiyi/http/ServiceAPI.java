package com.part.jianzhiyi.http;


import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.ActJobListEntity;
import com.part.jianzhiyi.model.entity.ActivityEntity;
import com.part.jianzhiyi.model.entity.AddFavouriteResponseEntity;
import com.part.jianzhiyi.model.entity.AddSignEntity;
import com.part.jianzhiyi.model.entity.BannerEntity;
import com.part.jianzhiyi.model.entity.CategoryEntity;
import com.part.jianzhiyi.model.entity.ChatJobInfoEntity;
import com.part.jianzhiyi.model.entity.ChoiceEntity;
import com.part.jianzhiyi.model.entity.ConfigEntity;
import com.part.jianzhiyi.model.entity.DaySignEntity;
import com.part.jianzhiyi.model.entity.IpResponseEntity;
import com.part.jianzhiyi.model.entity.JobDetailEntity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;
import com.part.jianzhiyi.model.entity.JobListResponseEntity2;
import com.part.jianzhiyi.model.entity.JoinJobEntity;
import com.part.jianzhiyi.model.entity.LableEntity;
import com.part.jianzhiyi.model.entity.MsgResponseEntity;
import com.part.jianzhiyi.model.entity.MyitemEntity;
import com.part.jianzhiyi.model.entity.ResumeEntity;
import com.part.jianzhiyi.model.entity.SearchEntity;
import com.part.jianzhiyi.model.entity.UMEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.model.entity.ViewedEntity;
import com.part.jianzhiyi.model.entity.VocationEntity;
import com.part.jianzhiyi.model.entity.integral.ExcitationInfoEntity;
import com.part.jianzhiyi.model.entity.integral.MyIntegralEntity;
import com.part.jianzhiyi.model.entity.integral.SignEntity;
import com.part.jianzhiyi.model.entity.integral.SignInfoEntity;
import com.part.jianzhiyi.model.entity.moku.KuaibaoEntity;
import com.part.jianzhiyi.model.entity.moku.MokuBillListEntity;
import com.part.jianzhiyi.model.entity.moku.MokuTaskListEntity;
import com.part.jianzhiyi.model.entity.moku.TxBindingEntity;
import com.part.jianzhiyi.model.entity.moku.TxInfoEntity;
import com.part.jianzhiyi.model.entity.moku.TxLogEntity;
import com.part.jianzhiyi.model.entity.moku.TxTypeEntity;
import com.part.jianzhiyi.model.entity.moku.WalletEntity;
import com.part.jianzhiyi.model.entity.pay.AuthInfoEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @author
 * @Description:
 * @Version: 1.0
 */
public interface ServiceAPI {

    @GET
    Observable<IpResponseEntity> getIp(@Url String url);

    /**
     * ???????????????
     *
     * @param phone ?????????
     * @return
     */
    @GET("api/index/sendMsg")
    Observable<ResponseData<String>> sendSms(@Query("appid") String appid, @Query("tel") String phone, @Query("os") String os, @Query("times") String times, @Query("token") String token, @Query("imei") String imei, @Query("androidid") String deviceid);

    /*
     * ??????
     *
     * @param phone ?????????
     * @return
     */
    @GET("api/Apiv2/yloginV2")
    Observable<String> login(@Query("appid") String appid, @Query("telphone") String phone, @Query("code") String code, @Query("os") String os, @Query("imei") String imei, @Query("androidid") String deviceid, @Query("ip") String ip, @Query("pv") String pv, @Query("pe") String pe, @Query("pt") String pt, @Query("ua") String ua, @Query("ua2") String ua2, @Query("push_id") String push_id, @Query("oaid") String oaid);

    /**
     * ??????
     *
     * @param userid
     * @param type
     * @return
     */
    @GET("api/Apiv2/jobListV3")
    Observable<ResponseData<JobListResponseEntity2>> jobList(@Query("page") String page, @Query("appid") String appid, @Query("userid") String userid, @Query("type") String type, @Query("position") String position, @Query("sort_id") String sort_id, @Query("ip") String ip, @Query("os") String os, @Query("imei") String imei, @Query("androidid") String deviceid, @Query("label") String label);

    @GET("api/Apiv2/jobListV3")
    Observable<ResponseData<JobListResponseEntity2>> jobList1(@Query("page") String page, @Query("appid") String appid, @Query("userid") String userid, @Query("type") String type, @Query("position") String position, @Query("sort_id") String sort_id, @Query("ip") String ip, @Query("os") String os, @Query("imei") String imei, @Query("androidid") String deviceid, @Query("category") String category);

    /**
     * ????????????
     *
     * @param userid
     * @return
     */
    @GET("api/index/inviteJob")
    Observable<ResponseData<List<JobListResponseEntity>>> inviteJob(@Query("appid") String appid, @Query("userid") String userid, @Query("os") String os, @Query("ip") String ip, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * ?????????
     *
     * @param userid
     * @return
     */
    @GET("api/index/joinedJob")
    Observable<ResponseData<List<JobListResponseEntity>>> joinedJob(@Query("appid") String appid, @Query("userid") String userid, @Query("os") String os, @Query("ip") String ip, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * ?????????
     *
     * @param userid
     * @return
     */
    @GET("api/index/arrivedJob")
    Observable<ResponseData<List<JobListResponseEntity>>> arrivedJob(@Query("appid") String appid, @Query("userid") String userid, @Query("os") String os, @Query("ip") String ip, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * ?????????
     *
     * @param userid
     * @return
     */
    @GET("api/index/approvedJob")
    Observable<ResponseData<List<JobListResponseEntity>>> approvedJob(@Query("appid") String appid, @Query("userid") String userid, @Query("os") String os, @Query("ip") String ip, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * ?????????
     *
     * @param userid
     * @return
     */
    @GET("api/index/donedJob")
    Observable<ResponseData<List<JobListResponseEntity>>> donedJob(@Query("appid") String appid, @Query("userid") String userid, @Query("os") String os, @Query("ip") String ip, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * @param userid
     * @param key
     * @return
     */
    @GET("api/index/jobSearch")
    Observable<ResponseData<List<JobListResponseEntity>>> jobSearch(@Query("appid") String appid, @Query("userid") String userid, @Query("key") String key, @Query("os") String os, @Query("sort_id") String sort_id, @Query("ip") String ip, @Query("position") String position, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * ?????????
     *
     * @param userid
     * @param jobId
     * @return
     */
    @GET("api/Apiv2/jobDetailv2")
    Observable<ResponseData<VocationEntity>> jobDetail(@Query("appid") String appid, @Query("userid") String userid, @Query("jobId") String jobId, @Query("num") String num, @Query("os") String os, @Query("position") String position, @Query("sort_id") String sort_id, @Query("ip") String ip, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * ??????????????????
     *
     * @param userid
     * @return
     */
    @GET("api/index/msgList")
    Observable<ResponseData<List<MsgResponseEntity>>> msgList(@Query("appid") String appid, @Query("userid") String userid, @Query("os") String os, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST("api/index/updateResume")
    @FormUrlEncoded
    Observable<ResponseData<String>> updateResume(@Field("appid") String appid, @Field("user_id") String user_id, @Field("name") String name, @Field("sex") String sex, @Field("age") String age, @Field("school_year") String school_year, @Field("school_name") String school_name, @Field("experience") String experience, @Field("introduce") String introduce, @Field("os") String os, @Field("imei") String imei, @Field("androidid") String deviceid);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST("api/index/updateProfile")
    @FormUrlEncoded
    Observable<ResponseData<String>> updateProfile(@Field("appid") String appid, @Field("username") String username, @Field("user_id") String user_id, @Field("signature") String signature, @Field("phone") String phone, @Field("email") String email, @Field("os") String os, @Field("imei") String imei, @Field("androidid") String deviceid);

    /**
     * ??????????????????
     *
     * @return
     */
    @GET("api/index/jobfeedback")
    Observable<ResponseData<AddFavouriteResponseEntity>> jobfeedback(@Query("appid") String appid, @Query("userid") String userid, @Query("contact") String contact, @Query("content") String content, @Query("os") String os, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * ????????????
     *
     * @param userid ??????id
     * @return
     */
    @GET("api/index/addFavourite")
    Observable<ResponseData<AddFavouriteResponseEntity>> addFavourite(@Query("appid") String appid, @Query("user_id") String userid, @Query("job_id") String job_id, @Query("os") String os, @Query("ip") String ip, @Query("sort_id") String sort_id, @Query("imei") String imei, @Query("androidid") String deviceid);

    @GET("api/index/cancelFavourite")
    Observable<ResponseData<AddFavouriteResponseEntity>> cancelFavourite(@Query("appid") String appid, @Query("user_id") String userid, @Query("job_id") String job_id, @Query("os") String os, @Query("ip") String ip, @Query("sort_id") String sort_id, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * ????????????
     *
     * @param userid ??????id
     * @return
     */
    @GET("api/index/favourite")
    Observable<ResponseData<List<JobListResponseEntity>>> favourite(@Query("appid") String appid, @Query("userid") String userid, @Query("os") String os, @Query("ip") String ip, @Query("sort_id") String sort_id, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * ??????
     *
     * @param userid ??????id
     * @return
     */
    @GET("api/index/joinJob")
    Observable<ResponseData<AddFavouriteResponseEntity>> joinJob(@Query("appid") String appid, @Query("userid") String userid, @Query("jobid") String jobid, @Query("os") String os, @Query("ip") String ip, @Query("sort_id") String sort_id, @Query("contact") String contact, @Query("imei") String imei, @Query("androidid") String deviceid);


    /**
     * ??????
     *
     * @param userid ??????id
     * @return
     */
    @GET("api/index/recommendList")
    Observable<ResponseData<List<JobListResponseEntity>>> recommendList(@Query("appid") String appid, @Query("userid") String userid, @Query("jobid") String jobid, @Query("os") String os, @Query("ip") String ip, @Query("sort_id") String sort_id, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * ????????????
     *
     * @param user_id ??????id
     * @return
     */
    @GET("Api/index/joincopyContact")
    Observable<ResponseData<AddFavouriteResponseEntity>> joincopyContact(@Query("appid") String appid, @Query("user_id") String user_id, @Query("job_id") String job_id, @Query("os") String os, @Query("ip") String ip, @Query("sort_id") String sort_id, @Query("contact") String contact, @Query("imei") String imei, @Query("androidid") String deviceid, @Query("type") int type);


    @FormUrlEncoded
    @POST("Api/index/androidInfov2")
    Observable<ResponseData> androidInfo(@Field("position") String position, @Field("appid") String appid, @Field("imei") String imei, @Field("user_id") String user_id, @Field("phone") String phone, @Field("pv") String pv, @Field("pe") String pe, @Field("pt") String pt, @Field("androidid") String deviceid, @Field("ip") String ip, @Field("ua") String ua, @Field("ua2") String ua2, @Field("oaid") String oaid);

    @FormUrlEncoded
    @POST("api/index/getTitle")
    Observable<ResponseData<AddFavouriteResponseEntity>> getTitle(@Field("appid") String appid, @Field("imei") String imei, @Field("androidid") String deviceid);


    @POST("api/Apiv2/area")
    Observable<String> getCity();

    @FormUrlEncoded
    @POST("api/Apiv2/bannerV2")
    Observable<ResponseData<List<BannerEntity>>> getBanner(@Field("appid") String appId);

    @FormUrlEncoded
    @POST("Api/Abx/getH5")
    Observable<ResponseData> getBannerUrl(@Field("imei") String imei, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("api/Apiv2/choice")
    Observable<ResponseData<ChoiceEntity>> getChoice(@Field("position") String position, @Field("appid") String appId, @Field("sort_id") String sort_id, @Field("ip") String ip, @Field("type") String type, @Field("os") String os);


    @POST("api/Apiv2/homeCategory")
    Observable<ResponseData<List<CategoryEntity>>> getHomeCategory(@Query("appid") String appid);

    /**
     * ??????????????????????????????
     *
     * @return
     */
    @POST("api/Users/userInfo")
    @FormUrlEncoded
    Observable<UserInfoEntity> userInfo(@Field("appid") String appid, @Field("user_id") String user_id, @Field("os") String os, @Field("imei") String imei, @Field("androidid") String deviceid, @Field("push_id") String push_id);

    /**
     * ????????????
     *
     * @return
     */
    @GET("Api/Activity/getConfig")
    Observable<ConfigEntity> getConfig(@Query("appid") String appid, @Query("os") String os);

    /**
     * ????????????TOKEN
     *
     * @return
     */
    @POST("Api/users/verifys")
    @FormUrlEncoded
    Observable<UMEntity> verifys(@Field("appid") String appid, @Field("token") String token);

    /**
     * ?????????????????????
     *
     * @return
     */
    @POST("Api/Index/updateResumeV2")
    @FormUrlEncoded
    Observable<ResumeEntity> updateResumeV2(@Field("user_id") String user_id, @Field("name") String name, @Field("sex") String sex, @Field("age") String age, @Field("school_year") String school_year, @Field("school_name") String school_name, @Field("experience") String experience, @Field("introduce") String introduce, @Field("appid") String appid, @Field("profession") int profession, @Field("job_status") int job_status, @Field("job_type") String job_type, @Field("myitem") String myitem, @Field("expect") String expect, @Field("os") String os, @Field("imei") String imei, @Field("androidid") String deviceid);

    /**
     * ??????
     *
     * @return
     */
    @POST("Api/Detail/search")
    @FormUrlEncoded
    Observable<SearchEntity> search(@Field("appid") String appid, @Field("title") String title, @Field("lable") String lable, @Field("ip") String ip);

    /**
     * ????????????
     *
     * @return
     */
    @GET("Api/Detail/getLable")
    Observable<LableEntity> getLable();

    /**
     * ????????????
     *
     * @return
     */
    @GET("Api/Activity/getActivity")
    Observable<ActivityEntity> getAction();

    /**
     * ????????????????????????
     *
     * @return
     */
    @POST("Api/Detail/getActJob")
    @FormUrlEncoded
    Observable<ActJobListEntity> getActJobList(@Field("appid") String appid, @Field("id") String id, @Field("ip") String ip, @Field("type") String type);

    /**
     * ?????? ?????????
     *
     * @param userid ??????id
     * @return
     */
    @GET("Api/Detail/joinJobV2")
    Observable<JoinJobEntity> joinJobV2(@Query("appid") String appid, @Query("userid") String userid, @Query("jobid") String jobid, @Query("os") String os, @Query("ip") String ip, @Query("sort_id") String sort_id, @Query("contact") String contact, @Query("imei") String imei, @Query("androidid") String deviceid, @Query("push_type") String push_type);

    /**
     * ?????????????????????
     *
     * @param userid
     * @param jobId
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Detail/jobDetailv3")
    Observable<JobDetailEntity> jobDetailv(@Field("appid") String appid, @Field("userid") String userid, @Field("jobId") String jobId, @Field("num") String num, @Field("os") String os, @Field("position") String position, @Field("sort_id") String sort_id, @Field("ip") String ip, @Field("imei") String imei, @Field("androidid") String deviceid);

    /**
     * ?????????
     *
     * @param userid
     * @return
     */
    @GET("Api/Detail/ViewedJob")
    Observable<ViewedEntity> viewedJob(@Query("appid") String appid, @Query("userid") String userid, @Query("os") String os, @Query("ip") String ip, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * ????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Detail/getJobinfo")
    Observable<ChatJobInfoEntity> getChatJobinfo(@Field("appid") String appid, @Field("id") String id, @Field("os") String os);

    /**
     * ????????????OR??????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/Detail/setSessOrErrLog")
    Observable<ResponseData> getSussOrErrLog(@Field("os") String os, @Field("imei") String imei, @Field("oaid") String oaid, @Field("pv") String pv, @Field("pe") String pe, @Field("pt") String pt, @Field("type") String type, @Field("status") String status);

    /**
     * ????????????????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/Detail/myitem")
    Observable<MyitemEntity> getMyitem(@Field("type") String type);

    /**
     * ???????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/Users/getDaySign")
    Observable<DaySignEntity> getDaySign(@Field("user_id") String user_id);

    /**
     * ????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/Users/addDaySign")
    Observable<AddSignEntity> addDaySign(@Field("user_id") String user_id, @Field("day") String day, @Field("os") String os);

    /**
     * ??????????????????
     *
     * @return
     */
    @GET("api/Apiv2/getLabel")
    Observable<LableEntity> getHomeLabel();

    /**
     * ??????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Api/ThirdParty/taskList")
    Observable<MokuTaskListEntity> getTaskList(@Field("user_id") String user_id);

    /**
     * ??????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Api/ThirdParty/addTask")
    Observable<ResponseData> getAddTask(@Field("task_data_id") String task_data_id, @Field("user_id") String user_id, @Field("show_name") String show_name, @Field("icon") String icon, @Field("show_money") String show_money, @Field("desc") String desc, @Field("status") String status);

    /**
     * ??????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Api/ThirdParty/liushui")
    Observable<MokuBillListEntity> getLiushuiList(@Field("user_id") String user_id, @Field("status") String status);

    /**
     * ????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Api/ThirdParty/myMoney")
    Observable<WalletEntity> getMyMoney(@Field("user_id") String user_id);

    /**
     * ????????????
     *
     * @return
     */
    @POST("Api/ThirdParty/txkb")
    Observable<KuaibaoEntity> getTxkb();

    /**
     * ????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/pay/tx_type")
    Observable<TxTypeEntity> getTxtype(@Field("user_id") String user_id);

    /**
     * ???????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/pay/binding_ali")
    Observable<TxBindingEntity> getTxbinding(@Field("user_id") String user_id, @Field("code") String code);

    /**
     * ??????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/pay/tx_ali")
    Observable<TxInfoEntity> getTxInfo(@Field("user_id") String user_id);

    /**
     * ???????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/pay/tx_code")
    Observable<TxBindingEntity> getTxcode(@Field("user_id") String user_id, @Field("times") String times, @Field("token") String token);

    /**
     * ????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/pay/tx_app")
    Observable<TxBindingEntity> getTxapp(@Field("user_id") String user_id, @Field("code") String code, @Field("money") String money);

    /**
     * ????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/pay/tx_log")
    Observable<TxLogEntity> getTxLog(@Field("user_id") String user_id, @Field("page") int page);

    /**
     * ???????????????url
     *
     * @return
     */
    @POST("api/pay/infostr")
    Observable<AuthInfoEntity> getAuthInfo();

    /**
     * ??????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Integral/myIntegInfo")
    Observable<MyIntegralEntity> getMyIntegInfo(@Field("user_id") String user_id, @Field("page") int page);

    /**
     * ?????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Integral/addInteg")
    Observable<SignInfoEntity> getAddInteg(@Field("user_id") String user_id, @Field("type") int type, @Field("job_id") String job_id);

    /**
     * ?????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Integral/addInteg")
    Observable<ResponseData> getAddIntegBrowse(@Field("user_id") String user_id, @Field("type") int type, @Field("job_id") String job_id);

    /**
     * ???????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/Integral/excitationInfo")
    Observable<ExcitationInfoEntity> getExcitationInfo(@Field("user_id") String user_id);

    /**
     * ????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Integral/qdInfos")
    Observable<SignEntity> getSignInfos(@Field("user_id") String user_id);

}
