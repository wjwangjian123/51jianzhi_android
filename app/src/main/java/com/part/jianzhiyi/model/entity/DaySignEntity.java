package com.part.jianzhiyi.model.entity;

/**
 * Created by jyx on 2020/7/21
 *
 * @author jyx
 * @describe
 */
public class DaySignEntity {

    /**
     * code : 200
     * data : {"day":0,"is_sign":0}
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
         * day : 0
         * is_sign : 0
         */

        private int day;
        private int is_sign;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getIs_sign() {
            return is_sign;
        }

        public void setIs_sign(int is_sign) {
            this.is_sign = is_sign;
        }
    }
}
