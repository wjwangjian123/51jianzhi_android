package com.part.jianzhiyi.modulemerchants.model.entity;

/**
 * Created by jyx on 2020/11/27
 *
 * @author jyx
 * @describe
 */
public class MMinMoneyEntity {

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

        private String min_money;
        private String pay_msg;
        private String msg;
        private int is_security;

        public String getMin_money() {
            return min_money;
        }

        public void setMin_money(String min_money) {
            this.min_money = min_money;
        }

        public String getPay_msg() {
            return pay_msg;
        }

        public void setPay_msg(String pay_msg) {
            this.pay_msg = pay_msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getIs_security() {
            return is_security;
        }

        public void setIs_security(int is_security) {
            this.is_security = is_security;
        }
    }
}
