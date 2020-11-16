package com.part.jianzhiyi.model.entity.moku;

/**
 * Created by jyx on 2020/9/10
 *
 * @author jyx
 * @describe
 */
public class WalletEntity {

    /**
     * code : 200
     * msg :
     * data : {"money":"0","allmoney":1.5}
     */

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
        /**
         * money : 0
         * allmoney : 1.5
         */

        private String money;
        private String allmoney;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getAllmoney() {
            return allmoney;
        }

        public void setAllmoney(String allmoney) {
            this.allmoney = allmoney;
        }
    }
}
