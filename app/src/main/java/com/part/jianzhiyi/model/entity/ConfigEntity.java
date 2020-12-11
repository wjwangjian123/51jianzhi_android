package com.part.jianzhiyi.model.entity;

import java.util.List;

/**
 * Created by jyx on 2020/6/9
 *
 * @author jyx
 * @describe
 */
public class ConfigEntity {

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

        private String id;
        private String ymappkey;
        private String code;
        private String appsecret;
        private String start_content;
        private String start_comm;
        private List<ContentBean> content;
        private int android_version;
        private String android_title;
        private String android_desc;
        private String android_url;
        private int show_wx;
        private int is_sw;

        public int getIs_sw() {
            return is_sw;
        }

        public void setIs_sw(int is_sw) {
            this.is_sw = is_sw;
        }

        public int getShow_wx() {
            return show_wx;
        }

        public void setShow_wx(int show_wx) {
            this.show_wx = show_wx;
        }

        public String getAndroid_url() {
            return android_url;
        }

        public void setAndroid_url(String android_url) {
            this.android_url = android_url;
        }

        public int getAndroid_version() {
            return android_version;
        }

        public void setAndroid_version(int android_version) {
            this.android_version = android_version;
        }

        public String getAndroid_title() {
            return android_title;
        }

        public void setAndroid_title(String android_title) {
            this.android_title = android_title;
        }

        public String getAndroid_desc() {
            return android_desc;
        }

        public void setAndroid_desc(String android_desc) {
            this.android_desc = android_desc;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getYmappkey() {
            return ymappkey;
        }

        public void setYmappkey(String ymappkey) {
            this.ymappkey = ymappkey;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getAppsecret() {
            return appsecret;
        }

        public void setAppsecret(String appsecret) {
            this.appsecret = appsecret;
        }

        public String getStart_content() {
            return start_content;
        }

        public void setStart_content(String start_content) {
            this.start_content = start_content;
        }

        public String getStart_comm() {
            return start_comm;
        }

        public void setStart_comm(String start_comm) {
            this.start_comm = start_comm;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean {

            private String problem;
            private String answer;

            public String getProblem() {
                return problem;
            }

            public void setProblem(String problem) {
                this.problem = problem;
            }

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }
        }
    }
}
