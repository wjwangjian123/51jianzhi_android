package com.part.jianzhiyi.model.entity;

/**
 * Created by jyx on 2020/6/9
 *
 * @author jyx
 * @describe
 */
public class ActivityEntity {

    /**
     * code : 200
     * msg :
     * data : {"id":"1","image":"http://jxw-img.oss-cn-beijing.aliyuncs.com/LJZ/20200415/1586922190.png","title":"测试","create_time":"2020-06-09 16:16:47","update_time":null,"status":"1","backimg":"http://jxw-img.oss-cn-beijing.aliyuncs.com/LJZ/20200412/1586682495.png","type":"1"}
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
         * id : 1
         * image : http://jxw-img.oss-cn-beijing.aliyuncs.com/LJZ/20200415/1586922190.png
         * title : 测试
         * create_time : 2020-06-09 16:16:47
         * update_time : null
         * status : 1
         * backimg : http://jxw-img.oss-cn-beijing.aliyuncs.com/LJZ/20200412/1586682495.png
         * type : 1
         */

        private String id;
        private String image;
        private String title;
        private String create_time;
        private String update_time;
        private String status;
        private String backimg;
        private String type;
        private String url;
        private int url_redirect;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getUrl_redirect() {
            return url_redirect;
        }

        public void setUrl_redirect(int url_redirect) {
            this.url_redirect = url_redirect;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBackimg() {
            return backimg;
        }

        public void setBackimg(String backimg) {
            this.backimg = backimg;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
