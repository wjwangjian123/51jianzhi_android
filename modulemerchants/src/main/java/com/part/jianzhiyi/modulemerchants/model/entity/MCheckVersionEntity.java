package com.part.jianzhiyi.modulemerchants.model.entity;

import java.util.List;

/**
 * Created by jyx on 2020/12/18
 *
 * @author jyx
 * @describe
 */
public class MCheckVersionEntity {

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

        private String os;
        private String title;
        private int is_forced;
        private int is_show;
        private int android_version;
        private String app_url;
        private List<String> content;

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIs_forced() {
            return is_forced;
        }

        public void setIs_forced(int is_forced) {
            this.is_forced = is_forced;
        }

        public int getIs_show() {
            return is_show;
        }

        public void setIs_show(int is_show) {
            this.is_show = is_show;
        }

        public int getAndroid_version() {
            return android_version;
        }

        public void setAndroid_version(int android_version) {
            this.android_version = android_version;
        }

        public String getApp_url() {
            return app_url;
        }

        public void setApp_url(String app_url) {
            this.app_url = app_url;
        }

        public List<String> getContent() {
            return content;
        }

        public void setContent(List<String> content) {
            this.content = content;
        }
    }
}
