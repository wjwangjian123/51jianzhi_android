package com.part.jianzhiyi.modulemerchants.http;


import com.part.jianzhiyi.modulemerchants.model.base.ResponseData;
import com.part.jianzhiyi.modulemerchants.model.entity.MAuthInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MAuthSuccessEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MBannerEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MCheckVersionEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MCompanyInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MConfigEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MEnterpriseInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MFileEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MGetEnterpriseInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MIDFaPositiveEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MIDPositiveEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MJobInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MJobListEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableContactEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MLableSalaryEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MMinMoneyEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MMyWalletEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MSwitchMerchantsEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MUserInfoEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.MZfbPayEntity;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * @author
 * @Description:
 * @Version: 1.0
 */
public interface ServiceAPI {

    /**
     * 意见反馈
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/opinion")
    Observable<ResponseData> getOpinion(@HeaderMap Map<String, String> headers, @Field("token") String token, @Field("reason") String reason, @Field("con") String con, @Field("phone") String phone);

    /**
     * 首页banner
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/banner/index")
    Observable<MBannerEntity> getIndexBanner(@Field("token") String token);

    /**
     * 获取用户最少支付金额
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/buspay/min_money")
    Observable<MMinMoneyEntity> getMinMoney(@Field("token") String token);

    /**
     * 用户签署发布任务协议
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/is_sing")
    Observable<ResponseData> getIsSing(@HeaderMap Map<String, String> headers, @Field("token") String token);

    /**
     * 刷新任务
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/Job/jobRefresh")
    Observable<ResponseData> getJobRefresh(@HeaderMap Map<String, String> headers, @Field("token") String token, @Field("job_id") String job_id);

    /**
     * 获取企业认证信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/check/enterprise_info")
    Observable<MGetEnterpriseInfoEntity> getEnterpriseInfo(@HeaderMap Map<String, String> headers, @Field("token") String token);

    /**
     * 获取个人认证信息接口
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/check/get_numid")
    Observable<MAuthInfoEntity> getNumid(@HeaderMap Map<String, String> headers, @Field("token") String token);

    /**
     * 职位标签下一步
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/job/check_job")
    Observable<ResponseData> getCheckJob(@HeaderMap Map<String, String> headers, @Field("token") String token, @Field("label_id") String label_id, @Field("job_id") String job_id);

    /**
     * 商户上传头像
     *
     * @return
     */
    @POST("api/user/avatar")
    Observable<ResponseData> getAvatar(@Body RequestBody requestBody);

    /**
     * 商户信息接口
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/user/userinfo")
    Observable<MUserInfoEntity> getMerUserinfo(@HeaderMap Map<String, String> headers, @Field("token") String token);

    /**
     * 上下线
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/Job/sx")
    Observable<ResponseData> getJobSx(@HeaderMap Map<String, String> headers, @Field("status") int status, @Field("job_id") String job_id, @Field("token") String token);

    /**
     * 法人身份证上传-正面
     *
     * @return
     */
    @POST("api/check/corporate")
    Observable<MIDFaPositiveEntity> getCorporate(@Body RequestBody requestBody);

    /**
     * 我的钱包
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/MyMoney/myMoney")
    Observable<MMyWalletEntity> getMyMoney(@HeaderMap Map<String, String> headers, @Field("token") String token, @Field("start_time") String start_time, @Field("end_time") String end_time, @Field("type") String type, @Field("page") int page);

    /**
     * 任务详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/Job/jobInfo")
    Observable<MJobInfoEntity> getMJobInfo(@HeaderMap Map<String, String> headers, @Field("id") String id, @Field("token") String token);

    /**
     * 获取订单
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/buspay/get_order")
    Observable<MZfbPayEntity> getOrder(@HeaderMap Map<String, String> headers, @Field("token") String token, @Field("money") String money);

    /**
     * 任务列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/Job/jobList")
    Observable<MJobListEntity> getMJobList(@HeaderMap Map<String, String> headers, @Field("token") String token, @Field("type") String type, @Field("start_time") String start_time, @Field("end_time") String end_time);

    /**
     * 添加或者修改任务
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/Job/addJob")
    Observable<ResponseData> getAddJob(@HeaderMap Map<String, String> headers, @Field("token") String token, @Field("title") String title, @Field("method") String method, @Field("time") String time, @Field("sex") String sex, @Field("price") String price, @Field("content") String content, @Field("contact") String contact, @Field("contact_type") int contact_type, @Field("number") String number, @Field("place") String place, @Field("duration") String duration, @Field("type") String type, @Field("id") String id, @Field("label_id") String label_id, @Field("price2") String price2, @Field("age1") String age1, @Field("age2") String age2);

    /**
     * 获取选择的标签
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/Job/getLabel")
    Observable<MLableEntity> getMLabel(@HeaderMap Map<String, String> headers, @Field("token") String token, @Field("type") String type);

    /**
     * 获取选择的标签
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/Job/getLabel")
    Observable<MLableSalaryEntity> getMLabelMethod(@HeaderMap Map<String, String> headers, @Field("token") String token, @Field("type") String type);

    /**
     * 获取选择的标签
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/Job/getLabel")
    Observable<MLableSalaryEntity> getMLabelSalary(@HeaderMap Map<String, String> headers, @Field("token") String token, @Field("type") String type);

    /**
     * 获取选择的标签
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/Job/getLabel")
    Observable<MLableContactEntity> getMLabelContact(@HeaderMap Map<String, String> headers, @Field("token") String token, @Field("type") String type);

    /**
     * 企业认证提交
     *
     * @return
     */
    @POST("api/check/check_enterprise")
    Observable<MAuthSuccessEntity> getCheckEnterprise(@Body RequestBody requestBody);

    /**
     * 个人认证提交
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/check/numid_success")
    Observable<MAuthSuccessEntity> getNumidSuccess(@HeaderMap Map<String, String> headers, @Field("token") String token, @Field("img_z") String img_z, @Field("name") String name, @Field("number") String number, @Field("img_f") String img_f, @Field("company") String company);

    /**
     * 企业图片上传
     *
     * @return
     */
    @POST("api/check/enterprise")
    Observable<MEnterpriseInfoEntity> getEnterprise(@Body RequestBody requestBody);

    /**
     * 身份证正面上传
     *
     * @return
     */
    @POST("api/check/baid_number")
    Observable<MIDPositiveEntity> getBaidNumber(@Body RequestBody requestBody);

    /**
     * 切换商户端接口
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/Login/userChabge")
    Observable<MSwitchMerchantsEntity> getUserChabge(@HeaderMap Map<String, String> headers, @Field("phone") String phone, @Field("os") String os);

    /**
     * 文件上传(身份证国徽面)
     *
     * @return
     */
    @POST("api/upload/upload")
    Observable<MFileEntity> getUpload(@Body RequestBody requestBody);

    /**
     * 商户端埋点
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Login/mdAdd")
    Observable<ResponseData> getmdAdd(@HeaderMap Map<String, String> headers, @Field("type") String type, @Field("token") String token);

    /**
     * 用户端埋点
     *
     * @return
     */
    @FormUrlEncoded
    @POST("http://51testapi.sw193.com/Api/Md/addMd")
    Observable<ResponseData> getaddMd(@Field("type") String type, @Field("os") String os);

    /**
     * 获取版本号
     *
     * @return
     */
    @FormUrlEncoded
    @POST("http://51testapi.sw193.com/api/version/check")
    Observable<MCheckVersionEntity> getCheck(@Field("os") String os, @Field("app_id") String app_id);

    /**
     * 公司详情页
     *
     * @return
     */
    @FormUrlEncoded
    @POST("user/business/get_company_info")
    Observable<MCompanyInfoEntity> getCompanyInfo(@HeaderMap Map<String, String> headers, @Field("uid") String uid, @Field("job_id") String job_id, @Field("user_id") String user_id, @Field("appid") String appid);

    /**
     * 商户端公共配置
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/Config/getConfig")
    Observable<MConfigEntity> getConfig(@Field("appid") String appid);

}
