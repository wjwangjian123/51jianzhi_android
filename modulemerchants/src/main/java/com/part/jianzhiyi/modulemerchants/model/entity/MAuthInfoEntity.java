package com.part.jianzhiyi.modulemerchants.model.entity;

/**
 * Created by jyx on 2020/11/25
 *
 * @author jyx
 * @describe
 */
public class MAuthInfoEntity {

    private String code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private String name;
        private String number;
        private String img_z;
        private String img_f;
        private int numid_status;
        private String ht_reason;
        private String company;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getImg_z() {
            return img_z;
        }

        public void setImg_z(String img_z) {
            this.img_z = img_z;
        }

        public String getImg_f() {
            return img_f;
        }

        public void setImg_f(String img_f) {
            this.img_f = img_f;
        }

        public int getNumid_status() {
            return numid_status;
        }

        public void setNumid_status(int numid_status) {
            this.numid_status = numid_status;
        }

        public String getHt_reason() {
            return ht_reason;
        }

        public void setHt_reason(String ht_reason) {
            this.ht_reason = ht_reason;
        }
    }
}
