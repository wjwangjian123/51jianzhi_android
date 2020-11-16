package com.part.jianzhiyi.model.entity.moku;

import java.util.List;

/**
 * Created by jyx on 2020/9/10
 *
 * @author jyx
 * @describe
 */
public class KuaibaoEntity {

    /**
     * code : 200
     * msg :
     * data : [{"user_id":"172","money":"1.5","phone":"158****4904"}]
     */

    private String code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_id : 172
         * money : 1.5
         * phone : 158****4904
         */

        private String user_id;
        private String money;
        private String phone;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
