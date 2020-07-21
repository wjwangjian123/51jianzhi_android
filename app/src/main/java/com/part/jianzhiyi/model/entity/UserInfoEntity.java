package com.part.jianzhiyi.model.entity;

/**
 * Created by jyx on 2020/4/26
 *
 * @author jyx
 * @describe
 */
public class UserInfoEntity {

    /**
     * code : 200
     * data : {"id":"6666","username":"","signature":"","create_time":"","update_time":"2019-09-20 13:39:27","phone":"13009081413","email":"","status":"0000","name":"王槐轩","sex":"男","age":"27","school_year":"2013","school_name":"吉林艺术学院","experience":"咖啡师","introduce":"懂艺术的咖啡师","app_id":"3","chan_id":"0","campagin_id":"0","imei":null,"idfa":null,"os":"0","androidid":null,"resume_complete":"77%"}
     */

    private String code;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 6666
         * username :
         * signature :
         * create_time :
         * update_time : 2019-09-20 13:39:27
         * phone : 13009081413
         * email :
         * status : 0000
         * name : 王槐轩
         * sex : 男
         * age : 27
         * school_year : 2013
         * school_name : 吉林艺术学院
         * experience : 咖啡师
         * introduce : 懂艺术的咖啡师
         * app_id : 3
         * chan_id : 0
         * campagin_id : 0
         * imei : null
         * idfa : null
         * os : 0
         * androidid : null
         * resume_complete : 77%
         */

        private String id;
        private String username;
        private String signature;
        private String create_time;
        private String update_time;
        private String phone;
        private String email;
        private String status;
        private String name;
        private String sex;
        private String age;
        private String school_year;
        private String school_name;
        private String experience;
        private String introduce;
        private String app_id;
        private String chan_id;
        private String campagin_id;
        private Object imei;
        private Object idfa;
        private String os;
        private Object androidid;
        private String resume_complete;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSchool_year() {
            return school_year;
        }

        public void setSchool_year(String school_year) {
            this.school_year = school_year;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getChan_id() {
            return chan_id;
        }

        public void setChan_id(String chan_id) {
            this.chan_id = chan_id;
        }

        public String getCampagin_id() {
            return campagin_id;
        }

        public void setCampagin_id(String campagin_id) {
            this.campagin_id = campagin_id;
        }

        public Object getImei() {
            return imei;
        }

        public void setImei(Object imei) {
            this.imei = imei;
        }

        public Object getIdfa() {
            return idfa;
        }

        public void setIdfa(Object idfa) {
            this.idfa = idfa;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public Object getAndroidid() {
            return androidid;
        }

        public void setAndroidid(Object androidid) {
            this.androidid = androidid;
        }

        public String getResume_complete() {
            return resume_complete;
        }

        public void setResume_complete(String resume_complete) {
            this.resume_complete = resume_complete;
        }
    }
}
