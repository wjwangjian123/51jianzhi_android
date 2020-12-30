package com.part.jianzhiyi.modulemerchants.model.entity;

/**
 * Created by jyx on 2020/12/29
 *
 * @author jyx
 * @describe
 */
public class MConfigEntity {

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

        private int is_os;
        private String about_us;

        public int getIs_os() {
            return is_os;
        }

        public void setIs_os(int is_os) {
            this.is_os = is_os;
        }

        public String getAbout_us() {
            return about_us;
        }

        public void setAbout_us(String about_us) {
            this.about_us = about_us;
        }
    }
}
