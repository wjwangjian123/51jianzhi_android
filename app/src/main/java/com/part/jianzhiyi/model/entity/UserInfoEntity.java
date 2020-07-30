package com.part.jianzhiyi.model.entity;

import java.util.List;

/**
 * Created by jyx on 2020/4/26
 *
 * @author jyx
 * @describe
 */
public class UserInfoEntity {

    /**
     * code : 200
     * data : {"id":"102","username":"","signature":"","create_time":"2020-07-15 16:25:35","update_time":"2020-07-15 17:02:45","phone":"17718437526","email":"","status":"0000","name":"","sex":"男","age":"18","school_year":"","school_name":"","experience":null,"introduce":null,"app_id":"3","chan_id":"37289","campagin_id":"","imei":null,"idfa":"C5A2D870-83AF-4789-B71A-85840752FE31","os":"1","androidid":null,"profession":"学生","job_status":"随便看看","job_type":"短期兼职工作","push_id":"75b232968b9c48d88a18a265ef961cb0","oaid":null,"myitem":[{"id":"3","item":"沟通能力强"},{"id":"4","item":"体力达人"},{"id":"5","item":"阳光开朗"}],"expect":[{"id":"4","item":"IOS开发"},{"id":"5","item":"跑步"}],"resume_active":"30","resume_complete":"28%","showResume":false,"profession_type":"1","job_status_type":"2","job_position_type":"2,3","bck":1,"join_num":0,"ylq":0,"ywc":0}
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
         * id : 102
         * username :
         * signature :
         * create_time : 2020-07-15 16:25:35
         * update_time : 2020-07-15 17:02:45
         * phone : 17718437526
         * email :
         * status : 0000
         * name :
         * sex : 男
         * age : 18
         * school_year :
         * school_name :
         * experience : null
         * introduce : null
         * app_id : 3
         * chan_id : 37289
         * campagin_id :
         * imei : null
         * idfa : C5A2D870-83AF-4789-B71A-85840752FE31
         * os : 1
         * androidid : null
         * profession : 学生
         * job_status : 随便看看
         * job_type : 短期兼职工作
         * push_id : 75b232968b9c48d88a18a265ef961cb0
         * oaid : null
         * myitem : [{"id":"3","item":"沟通能力强"},{"id":"4","item":"体力达人"},{"id":"5","item":"阳光开朗"}]
         * expect : [{"id":"4","item":"IOS开发"},{"id":"5","item":"跑步"}]
         * resume_active : 30
         * resume_complete : 28%
         * showResume : false
         * profession_type : 1
         * job_status_type : 2
         * job_position_type : 2,3
         * bck : 1
         * join_num : 0
         * ylq : 0
         * ywc : 0
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
        private String imei;
        private String idfa;
        private String os;
        private String androidid;
        private String profession;
        private String job_status;
        private String job_type;
        private String push_id;
        private String oaid;
        private String resume_active;
        private String resume_complete;
        private boolean showResume;
        private int profession_type;
        private int job_status_type;
        private String job_position_type;
        private int bck;
        private int join_num;
        private int ylq;
        private int ywc;
        private List<MyitemBean> myitem;
        private List<ExpectBean> expect;

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

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public String getIdfa() {
            return idfa;
        }

        public void setIdfa(String idfa) {
            this.idfa = idfa;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getAndroidid() {
            return androidid;
        }

        public void setAndroidid(String androidid) {
            this.androidid = androidid;
        }

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getJob_status() {
            return job_status;
        }

        public void setJob_status(String job_status) {
            this.job_status = job_status;
        }

        public String getJob_type() {
            return job_type;
        }

        public void setJob_type(String job_type) {
            this.job_type = job_type;
        }

        public String getPush_id() {
            return push_id;
        }

        public void setPush_id(String push_id) {
            this.push_id = push_id;
        }

        public String getOaid() {
            return oaid;
        }

        public void setOaid(String oaid) {
            this.oaid = oaid;
        }

        public String getResume_active() {
            return resume_active;
        }

        public void setResume_active(String resume_active) {
            this.resume_active = resume_active;
        }

        public String getResume_complete() {
            return resume_complete;
        }

        public void setResume_complete(String resume_complete) {
            this.resume_complete = resume_complete;
        }

        public boolean isShowResume() {
            return showResume;
        }

        public void setShowResume(boolean showResume) {
            this.showResume = showResume;
        }

        public int getProfession_type() {
            return profession_type;
        }

        public void setProfession_type(int profession_type) {
            this.profession_type = profession_type;
        }

        public int getJob_status_type() {
            return job_status_type;
        }

        public void setJob_status_type(int job_status_type) {
            this.job_status_type = job_status_type;
        }

        public String getJob_position_type() {
            return job_position_type;
        }

        public void setJob_position_type(String job_position_type) {
            this.job_position_type = job_position_type;
        }

        public int getBck() {
            return bck;
        }

        public void setBck(int bck) {
            this.bck = bck;
        }

        public int getJoin_num() {
            return join_num;
        }

        public void setJoin_num(int join_num) {
            this.join_num = join_num;
        }

        public int getYlq() {
            return ylq;
        }

        public void setYlq(int ylq) {
            this.ylq = ylq;
        }

        public int getYwc() {
            return ywc;
        }

        public void setYwc(int ywc) {
            this.ywc = ywc;
        }

        public List<MyitemBean> getMyitem() {
            return myitem;
        }

        public void setMyitem(List<MyitemBean> myitem) {
            this.myitem = myitem;
        }

        public List<ExpectBean> getExpect() {
            return expect;
        }

        public void setExpect(List<ExpectBean> expect) {
            this.expect = expect;
        }

        public static class MyitemBean {
            /**
             * id : 3
             * item : 沟通能力强
             */

            private String id;
            private String item;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getItem() {
                return item;
            }

            public void setItem(String item) {
                this.item = item;
            }
        }

        public static class ExpectBean {
            /**
             * id : 4
             * item : IOS开发
             */

            private String id;
            private String item;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getItem() {
                return item;
            }

            public void setItem(String item) {
                this.item = item;
            }
        }
    }
}
