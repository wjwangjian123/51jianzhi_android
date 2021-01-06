package com.part.jianzhiyi.modulemerchants.model.entity;

/**
 * Created by jyx on 2020/11/26
 *
 * @author jyx
 * @describe
 */
public class MUserInfoEntity {

    private String code;
    private String msg;
    private UserinfoBean userinfo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public static class UserinfoBean {

        private int id;
        private String name;
        private String money;
        private int numid_status;
        private int enterprise_status;
        private String img;
        private String company;
        private int is_sing;
        private String phone;
        private int status;
        private int cert_status;
        private String msg;
        private int job_add;
        private String add_msg;
        private String job_msg;
        private int job_free;
        private String company_desc;

        public String getCompany_desc() {
            return company_desc;
        }

        public void setCompany_desc(String company_desc) {
            this.company_desc = company_desc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getNumid_status() {
            return numid_status;
        }

        public void setNumid_status(int numid_status) {
            this.numid_status = numid_status;
        }

        public int getEnterprise_status() {
            return enterprise_status;
        }

        public void setEnterprise_status(int enterprise_status) {
            this.enterprise_status = enterprise_status;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public int getIs_sing() {
            return is_sing;
        }

        public void setIs_sing(int is_sing) {
            this.is_sing = is_sing;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCert_status() {
            return cert_status;
        }

        public void setCert_status(int cert_status) {
            this.cert_status = cert_status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getJob_add() {
            return job_add;
        }

        public void setJob_add(int job_add) {
            this.job_add = job_add;
        }

        public String getAdd_msg() {
            return add_msg;
        }

        public void setAdd_msg(String add_msg) {
            this.add_msg = add_msg;
        }

        public String getJob_msg() {
            return job_msg;
        }

        public void setJob_msg(String job_msg) {
            this.job_msg = job_msg;
        }

        public int getJob_free() {
            return job_free;
        }

        public void setJob_free(int job_free) {
            this.job_free = job_free;
        }
    }
}
