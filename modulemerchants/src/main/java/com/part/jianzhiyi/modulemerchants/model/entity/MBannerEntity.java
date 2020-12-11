package com.part.jianzhiyi.modulemerchants.model.entity;

import java.util.List;

/**
 * Created by jyx on 2020/11/27
 *
 * @author jyx
 * @describe
 */
public class MBannerEntity {

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

        private String name;
        private String image;
        private String urls;
        private String type;
        private int url_redirect;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUrls() {
            return urls;
        }

        public void setUrls(String urls) {
            this.urls = urls;
        }

        public int getUrl_redirect() {
            return url_redirect;
        }

        public void setUrl_redirect(int url_redirect) {
            this.url_redirect = url_redirect;
        }
    }
}
