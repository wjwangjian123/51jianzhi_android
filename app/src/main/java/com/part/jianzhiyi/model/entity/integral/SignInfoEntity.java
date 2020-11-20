package com.part.jianzhiyi.model.entity.integral;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jyx on 2020/11/17
 *
 * @author jyx
 * @describe
 */
public class SignInfoEntity implements Serializable {

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

    public static class DataBean implements Serializable {
        /**
         * day : 1
         * con : 明天签到可获得 3 职豆，记得要来呀！
         * getDayJf : [{"day":1,"jf":3,"max":0},{"day":2,"jf":3,"max":0},{"day":3,"jf":20,"max":0},{"day":4,"jf":3,"max":0},{"day":5,"jf":30,"max":1},{"day":6,"jf":3,"max":0},{"day":7,"jf":50,"max":1}]
         */

        private int day;
        private String con;
        private List<GetDayJfBean> getDayJf;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getCon() {
            return con;
        }

        public void setCon(String con) {
            this.con = con;
        }

        public List<GetDayJfBean> getGetDayJf() {
            return getDayJf;
        }

        public void setGetDayJf(List<GetDayJfBean> getDayJf) {
            this.getDayJf = getDayJf;
        }

        public static class GetDayJfBean implements Serializable {
            /**
             * day : 1
             * jf : 3
             * max : 0
             */
            private String day;
            private String jf;
            private int max;

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getJf() {
                return jf;
            }

            public void setJf(String jf) {
                this.jf = jf;
            }

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }
        }
    }
}
