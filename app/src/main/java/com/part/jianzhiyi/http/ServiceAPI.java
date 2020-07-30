package com.part.jianzhiyi.http;


import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.ActJobListEntity;
import com.part.jianzhiyi.model.entity.ActivityEntity;
import com.part.jianzhiyi.model.entity.AddFavouriteResponseEntity;
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
import com.part.jianzhiyi.model.entity.LoginResponseEntity;
import com.part.jianzhiyi.model.entity.MsgResponseEntity;
import com.part.jianzhiyi.model.entity.MyitemEntity;
import com.part.jianzhiyi.model.entity.ResumeEntity;
import com.part.jianzhiyi.model.entity.SearchEntity;
import com.part.jianzhiyi.model.entity.UMEntity;
import com.part.jianzhiyi.model.entity.UserInfoEntity;
import com.part.jianzhiyi.model.entity.ViewedEntity;
import com.part.jianzhiyi.model.entity.VocationEntity;

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
     * 发送验证码
     *
     * @param phone 手机号
     * @return
     */
    @GET("api/index/sendMsg")
    Observable<ResponseData<String>> sendSms(@Query("appid") String appid, @Query("tel") String phone, @Query("os") String os, @Query("times") String times, @Query("token") String token, @Query("imei") String imei, @Query("androidid") String deviceid);

    /*
     * 登录
     *
     * @param phone 手机号
     * @return
     */
    @GET("api/Apiv2/yloginV2")
    Observable<String> login(@Query("appid") String appid, @Query("telphone") String phone, @Query("code") String code, @Query("os") String os, @Query("imei") String imei, @Query("androidid") String deviceid, @Query("ip") String ip, @Query("pv") String pv, @Query("pe") String pe, @Query("pt") String pt, @Query("ua") String ua, @Query("ua2") String ua2, @Query("push_id") String push_id, @Query("oaid") String oaid);

    /**
     * 列表
     *
     * @param userid
     * @param type
     * @return
     */
    @GET("api/Apiv2/jobListAndroidV2")
    Observable<ResponseData<JobListResponseEntity2>> jobList(@Query("page") String page, @Query("appid") String appid, @Query("userid") String userid, @Query("type") String type, @Query("position") String position, @Query("sort_id") String sort_id, @Query("ip") String ip, @Query("os") String os, @Query("imei") String imei, @Query("androidid") String deviceid);

    @GET("api/Apiv2/jobListAndroidV2")
    Observable<ResponseData<JobListResponseEntity2>> jobList(@Query("page") String page, @Query("appid") String appid, @Query("userid") String userid, @Query("type") String type, @Query("position") String position, @Query("sort_id") String sort_id, @Query("ip") String ip, @Query("os") String os, @Query("imei") String imei, @Query("androidid") String deviceid, @Query("category") String category);

    /**
     * 工作邀请
     *
     * @param userid
     * @return
     */
    @GET("api/index/inviteJob")
    Observable<ResponseData<List<JobListResponseEntity>>> inviteJob(@Query("appid") String appid, @Query("userid") String userid, @Query("os") String os, @Query("ip") String ip, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * 已报名
     *
     * @param userid
     * @return
     */
    @GET("api/index/joinedJob")
    Observable<ResponseData<List<JobListResponseEntity>>> joinedJob(@Query("appid") String appid, @Query("userid") String userid, @Query("os") String os, @Query("ip") String ip, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * 已到岗
     *
     * @param userid
     * @return
     */
    @GET("api/index/arrivedJob")
    Observable<ResponseData<List<JobListResponseEntity>>> arrivedJob(@Query("appid") String appid, @Query("userid") String userid, @Query("os") String os, @Query("ip") String ip, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * 已录取
     *
     * @param userid
     * @return
     */
    @GET("api/index/approvedJob")
    Observable<ResponseData<List<JobListResponseEntity>>> approvedJob(@Query("appid") String appid, @Query("userid") String userid, @Query("os") String os, @Query("ip") String ip, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * 已完成
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
     * 详情页
     *
     * @param userid
     * @param jobId
     * @return
     */
    @GET("api/Apiv2/jobDetailv2")
    Observable<ResponseData<VocationEntity>> jobDetail(@Query("appid") String appid, @Query("userid") String userid, @Query("jobId") String jobId, @Query("num") String num, @Query("os") String os, @Query("position") String position, @Query("sort_id") String sort_id, @Query("ip") String ip, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * 系统消息列表
     *
     * @param userid
     * @return
     */
    @GET("api/index/msgList")
    Observable<ResponseData<List<MsgResponseEntity>>> msgList(@Query("appid") String appid, @Query("userid") String userid, @Query("os") String os, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * 更新我的信息
     *
     * @return
     */
    @POST("api/index/updateResume")
    @FormUrlEncoded
    Observable<ResponseData<String>> updateResume(@Field("appid") String appid, @Field("user_id") String user_id, @Field("name") String name, @Field("sex") String sex, @Field("age") String age, @Field("school_year") String school_year, @Field("school_name") String school_name, @Field("experience") String experience, @Field("introduce") String introduce, @Field("os") String os, @Field("imei") String imei, @Field("androidid") String deviceid);

    /**
     * 更新我的信息
     *
     * @return
     */
    @POST("api/index/updateProfile")
    @FormUrlEncoded
    Observable<ResponseData<String>> updateProfile(@Field("appid") String appid, @Field("username") String username, @Field("user_id") String user_id, @Field("signature") String signature, @Field("phone") String phone, @Field("email") String email, @Field("os") String os, @Field("imei") String imei, @Field("androidid") String deviceid);

    /**
     * 更新我的信息
     *
     * @return
     */
    @GET("api/index/jobfeedback")
    Observable<ResponseData<AddFavouriteResponseEntity>> jobfeedback(@Query("appid") String appid, @Query("userid") String userid, @Query("contact") String contact, @Query("content") String content, @Query("os") String os, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * 添加收藏
     *
     * @param userid 用户id
     * @return
     */
    @GET("api/index/addFavourite")
    Observable<ResponseData<AddFavouriteResponseEntity>> addFavourite(@Query("appid") String appid, @Query("user_id") String userid, @Query("job_id") String job_id, @Query("os") String os, @Query("ip") String ip, @Query("sort_id") String sort_id, @Query("imei") String imei, @Query("androidid") String deviceid);

    @GET("api/index/cancelFavourite")
    Observable<ResponseData<AddFavouriteResponseEntity>> cancelFavourite(@Query("appid") String appid, @Query("user_id") String userid, @Query("job_id") String job_id, @Query("os") String os, @Query("ip") String ip, @Query("sort_id") String sort_id, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * 我的收藏
     *
     * @param userid 用户id
     * @return
     */
    @GET("api/index/favourite")
    Observable<ResponseData<List<JobListResponseEntity>>> favourite(@Query("appid") String appid, @Query("userid") String userid, @Query("os") String os, @Query("ip") String ip, @Query("sort_id") String sort_id, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * 报名
     *
     * @param userid 用户id
     * @return
     */
    @GET("api/index/joinJob")
    Observable<ResponseData<AddFavouriteResponseEntity>> joinJob(@Query("appid") String appid, @Query("userid") String userid, @Query("jobid") String jobid, @Query("os") String os, @Query("ip") String ip, @Query("sort_id") String sort_id, @Query("contact") String contact, @Query("imei") String imei, @Query("androidid") String deviceid);


    /**
     * 推荐
     *
     * @param userid 用户id
     * @return
     */
    @GET("api/index/recommendList")
    Observable<ResponseData<List<JobListResponseEntity>>> recommendList(@Query("appid") String appid, @Query("userid") String userid, @Query("jobid") String jobid, @Query("os") String os, @Query("ip") String ip, @Query("sort_id") String sort_id, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * 复制联系方式
     *
     * @param user_id 用户id
     * @return
     */
    @GET("api/index/copyContact")
    Observable<String> copyContact(@Query("appid") String appid, @Query("user_id") String user_id, @Query("job_id") String job_id, @Query("os") String os, @Query("ip") String ip, @Query("sort_id") String sort_id, @Query("contact") String contact, @Query("imei") String imei, @Query("androidid") String deviceid);


    /**
     * 监控报名
     *
     * @param user_id 用户id
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
     * 获取个人中心信息
     *
     * @return
     */
    @POST("api/Users/userInfo")
    @FormUrlEncoded
    Observable<UserInfoEntity> userInfo(@Field("appid") String appid, @Field("user_id") String user_id, @Field("os") String os, @Field("imei") String imei, @Field("androidid") String deviceid, @Field("push_id") String push_id);

    /**
     * 全局配置
     *
     * @return
     */
    @POST("Api/Activity/getConfig")
    @FormUrlEncoded
    Observable<ConfigEntity> getConfig(@Field("id") String id);

    /**
     * 验证友盟TOKEN
     *
     * @return
     */
    @POST("Api/users/verifys")
    @FormUrlEncoded
    Observable<UMEntity> verifys(@Field("appid") String appid, @Field("token") String token);

    /**
     * 更新简历第二版
     *
     * @return
     */
    @POST("Api/Index/updateResumeV2")
    @FormUrlEncoded
    Observable<ResumeEntity> updateResumeV2(@Field("user_id") String user_id, @Field("name") String name, @Field("sex") String sex, @Field("age") String age, @Field("school_year") String school_year, @Field("school_name") String school_name, @Field("experience") String experience, @Field("introduce") String introduce, @Field("appid") String appid, @Field("profession") int profession, @Field("job_status") int job_status, @Field("job_type") String job_type, @Field("os") String os, @Field("imei") String imei, @Field("androidid") String deviceid);

    /**
     * 搜索
     *
     * @return
     */
    @POST("Api/Detail/search")
    @FormUrlEncoded
    Observable<SearchEntity> search(@Field("appid") String appid, @Field("title") String title, @Field("lable") String lable, @Field("ip") String ip);

    /**
     * 获取标签
     *
     * @return
     */
    @GET("Api/Detail/getLable")
    Observable<LableEntity> getLable();

    /**
     * 活动接口
     *
     * @return
     */
    @GET("Api/Activity/getActivity")
    Observable<ActivityEntity> getAction();

    /**
     * 获取活动任务列表
     *
     * @return
     */
    @POST("Api/Detail/getActJob")
    @FormUrlEncoded
    Observable<ActJobListEntity> getActJobList(@Field("appid") String appid, @Field("id") String id, @Field("ip") String ip);

    /**
     * 报名 第二版
     *
     * @param userid 用户id
     * @return
     */
    @GET("Api/Detail/joinJobV2")
    Observable<JoinJobEntity> joinJobV2(@Query("appid") String appid, @Query("userid") String userid, @Query("jobid") String jobid, @Query("os") String os, @Query("ip") String ip, @Query("sort_id") String sort_id, @Query("contact") String contact, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * 职位详情第三版
     *
     * @param userid
     * @param jobId
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Detail/jobDetailv3")
    Observable<JobDetailEntity> jobDetailv(@Field("appid") String appid, @Field("userid") String userid, @Field("jobId") String jobId, @Field("num") String num, @Field("os") String os, @Field("position") String position, @Field("sort_id") String sort_id, @Field("ip") String ip, @Field("imei") String imei, @Field("androidid") String deviceid);

    /**
     * 被查看
     *
     * @param userid
     * @return
     */
    @GET("Api/Detail/ViewedJob")
    Observable<ViewedEntity> viewedJob(@Query("appid") String appid, @Query("userid") String userid, @Query("os") String os, @Query("ip") String ip, @Query("imei") String imei, @Query("androidid") String deviceid);

    /**
     * 沟通页面获取内容
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Detail/getJobinfo")
    Observable<ChatJobInfoEntity> getChatJobinfo(@Field("appid") String appid, @Field("id") String id);

    /**
     * 记录成功OR失败
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/Detail/setSessOrErrLog")
    Observable<ResponseData> getSussOrErrLog(@Field("os") String os, @Field("imei") String imei, @Field("oaid") String oaid, @Field("pv") String pv, @Field("pe") String pe, @Field("pt") String pt, @Field("type") String type, @Field("status") String status);

    /**
     * 获取（关于我和期望职位）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/Detail/myitem")
    Observable<MyitemEntity> getMyitem(@Field("type") String type);

    /**
     * 获取今天的签到情况
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/Users/getDaySign")
    Observable<DaySignEntity> getDaySign(@Field("user_id") String user_id);

    /**
     * 签到接口
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/Users/addDaySign")
    Observable<ResponseData> addDaySign(@Field("user_id") String user_id,@Field("day") String day);
}
