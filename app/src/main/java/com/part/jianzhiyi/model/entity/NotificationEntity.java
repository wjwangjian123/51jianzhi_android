package com.part.jianzhiyi.model.entity;

/**
 * Created by jyx on 2020/6/22
 *
 * @author jyx
 * @describe
 */
public class NotificationEntity {

    /**
     * _ALIYUN_NOTIFICATION_ID_ : 885003
     * job_id : 4586
     * api_name : PushNoticeToAndroidRequest
     */

    private String _ALIYUN_NOTIFICATION_ID_;
    private String job_id;
    private String api_name;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String get_ALIYUN_NOTIFICATION_ID_() {
        return _ALIYUN_NOTIFICATION_ID_;
    }

    public void set_ALIYUN_NOTIFICATION_ID_(String _ALIYUN_NOTIFICATION_ID_) {
        this._ALIYUN_NOTIFICATION_ID_ = _ALIYUN_NOTIFICATION_ID_;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getApi_name() {
        return api_name;
    }

    public void setApi_name(String api_name) {
        this.api_name = api_name;
    }
}
