package com.part.jianzhiyi.model.entity.moku;

import java.util.List;

/**
 * Created by jyx on 2020/9/18
 *
 * @author jyx
 * @describe
 */
public class TxInfoEntity {

    private String code;
    private String msg;
    private DataBean data;
    private List<MoneyBean> money;

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

    public List<MoneyBean> getMoney() {
        return money;
    }

    public void setMoney(List<MoneyBean> money) {
        this.money = money;
    }

    public static class DataBean {

        private String ali_user_id;
        private String wechat_openid;
        private String money;
        private String ali_nick;
        private String phone;

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

        public String getAli_nick() {
            return ali_nick;
        }

        public void setAli_nick(String ali_nick) {
            this.ali_nick = ali_nick;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public static class MoneyBean {

        private String money;
        private String money_s;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getMoney_s() {
            return money_s;
        }

        public void setMoney_s(String money_s) {
            this.money_s = money_s;
        }
    }
}
