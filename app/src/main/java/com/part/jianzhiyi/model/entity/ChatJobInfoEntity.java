package com.part.jianzhiyi.model.entity;

/**
 * Created by jyx on 2020/6/18
 *
 * @author jyx
 * @describe
 */
public class ChatJobInfoEntity {

    /**
     * code : 200
     * msg : 请求成功
     * data : {"id":"544","title":"礼仪、迎宾","price":"320元/天","time":"长期","method":"日结","place":"不限","sex":"不限","number":"20","content":"","price2":"元/天"}
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
         * id : 544
         * title : 礼仪、迎宾
         * price : 320元/天
         * time : 长期
         * method : 日结
         * place : 不限
         * sex : 不限
         * number : 20
         * content :
         * price2 : 元/天
         */

        private String id;
        private String title;
        private String price;
        private String time;
        private String method;
        private String place;
        private String sex;
        private String number;
        private String content;
        private String msg1;
        private String data;
        private String price1;
        private String price2;
        private String company;
        private String img;

        public String getPrice1() {
            return price1;
        }

        public void setPrice1(String price1) {
            this.price1 = price1;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getMsg1() {
            return msg1;
        }

        public void setMsg1(String msg1) {
            this.msg1 = msg1;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPrice2() {
            return price2;
        }

        public void setPrice2(String price2) {
            this.price2 = price2;
        }
    }
}
