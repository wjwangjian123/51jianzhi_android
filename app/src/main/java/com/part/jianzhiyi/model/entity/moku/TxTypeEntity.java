package com.part.jianzhiyi.model.entity.moku;

/**
 * Created by jyx on 2020/9/18
 *
 * @author jyx
 * @describe
 */
public class TxTypeEntity {

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

        private String ali_user_id;
        private String wechat_openid;
        private String money;
        private int tx_check;
        private String tx_msg;
        private int ali;
        private int wechat;
        private int ali_switch;
        private int wechat_switch;
        private String ali_msg;
        private String wechat_msg;

        public String getAli_user_id() {
            return ali_user_id;
        }

        public void setAli_user_id(String ali_user_id) {
            this.ali_user_id = ali_user_id;
        }

        public String getWechat_openid() {
            return wechat_openid;
        }

        public void setWechat_openid(String wechat_openid) {
            this.wechat_openid = wechat_openid;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getTx_check() {
            return tx_check;
        }

        public void setTx_check(int tx_check) {
            this.tx_check = tx_check;
        }

        public String getTx_msg() {
            return tx_msg;
        }

        public void setTx_msg(String tx_msg) {
            this.tx_msg = tx_msg;
        }

        public int getAli() {
            return ali;
        }

        public void setAli(int ali) {
            this.ali = ali;
        }

        public int getWechat() {
            return wechat;
        }

        public void setWechat(int wechat) {
            this.wechat = wechat;
        }

        public int getAli_switch() {
            return ali_switch;
        }

        public void setAli_switch(int ali_switch) {
            this.ali_switch = ali_switch;
        }

        public int getWechat_switch() {
            return wechat_switch;
        }

        public void setWechat_switch(int wechat_switch) {
            this.wechat_switch = wechat_switch;
        }

        public String getAli_msg() {
            return ali_msg;
        }

        public void setAli_msg(String ali_msg) {
            this.ali_msg = ali_msg;
        }

        public String getWechat_msg() {
            return wechat_msg;
        }

        public void setWechat_msg(String wechat_msg) {
            this.wechat_msg = wechat_msg;
        }
    }
}
