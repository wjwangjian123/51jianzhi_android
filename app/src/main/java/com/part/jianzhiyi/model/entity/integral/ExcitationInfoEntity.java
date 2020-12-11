package com.part.jianzhiyi.model.entity.integral;

import java.util.List;

/**
 * Created by jyx on 2020/11/17
 *
 * @author jyx
 * @describe
 */
public class ExcitationInfoEntity {

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

        private String ll;
        private String ll1;
        private String jz;
        private String jz1;
        private String yxjz;
        private String yxjz1;
        private String integral;
        private String webUrl;
        private QdBean qd;
        private List<String> banner;

        public String getLl() {
            return ll;
        }

        public void setLl(String ll) {
            this.ll = ll;
        }

        public String getLl1() {
            return ll1;
        }

        public void setLl1(String ll1) {
            this.ll1 = ll1;
        }

        public String getJz() {
            return jz;
        }

        public void setJz(String jz) {
            this.jz = jz;
        }

        public String getJz1() {
            return jz1;
        }

        public void setJz1(String jz1) {
            this.jz1 = jz1;
        }

        public String getYxjz() {
            return yxjz;
        }

        public void setYxjz(String yxjz) {
            this.yxjz = yxjz;
        }

        public String getYxjz1() {
            return yxjz1;
        }

        public void setYxjz1(String yxjz1) {
            this.yxjz1 = yxjz1;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        public QdBean getQd() {
            return qd;
        }

        public void setQd(QdBean qd) {
            this.qd = qd;
        }

        public List<String> getBanner() {
            return banner;
        }

        public void setBanner(List<String> banner) {
            this.banner = banner;
        }

        public static class QdBean {

            private String day;
            private String con;

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getCon() {
                return con;
            }

            public void setCon(String con) {
                this.con = con;
            }
        }
    }
}
