package com.part.jianzhiyi.model.entity;

/**
 * Created by jyx on 2020/6/10
 *
 * @author jyx
 * @describe
 */
public class ResumeEntity {

    /**
     * code : 200
     * data : {"sex":"男","age":"19","profession":"2","job_status":"2","job_type":"3","showResume":false}
     */

    private String msg;
    private String code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

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
         * sex : 男
         * age : 19
         * profession : 2
         * job_status : 2
         * job_type : 3
         * showResume : false
         */

        private String sex;
        private String age;
        private String profession;
        private String job_status;
        private String job_type;
        private boolean showResume;
        private int profession_type;
        private int job_status_type;
        private String job_position_type;

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

        public boolean isShowResume() {
            return showResume;
        }

        public void setShowResume(boolean showResume) {
            this.showResume = showResume;
        }
    }
}
