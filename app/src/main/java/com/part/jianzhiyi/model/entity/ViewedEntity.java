package com.part.jianzhiyi.model.entity;

import java.util.List;

/**
 * Created by jyx on 2020/6/16
 *
 * @author jyx
 * @describe
 */
public class ViewedEntity {

    /**
     * code : 200
     * msg : 请求成功
     * data : [{"id":"544","title":"礼仪、迎宾","price":"320元/天","time":"长期","method":"日结","cateid":"544","place":"不限","uid":"8","name":"令俊个人工作室","img":"http://www.ejianzhi.net/Public/images/tx.png","price1":"320","price2":"元/天"},{"id":"572","title":"急聘！高薪在家兼职 一单一结","price":"200元/天","time":"长期","method":"日结","cateid":"572","place":"不限","uid":"15","name":"牛逗个人工作室","img":"","price1":"200","price2":"元/天"}]
     */

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

    public static class DataBean{
        /**
         * id : 544
         * title : 礼仪、迎宾
         * price : 320元/天
         * time : 长期
         * method : 日结
         * cateid : 544
         * place : 不限
         * uid : 8
         * name : 令俊个人工作室
         * img : http://www.ejianzhi.net/Public/images/tx.png
         * price1 : 320
         * price2 : 元/天
         */

        private String id;
        private String title;
        private String price;
        private String time;
        private String method;
        private String cateid;
        private String place;
        private String uid;
        private String name;
        private String img;
        private String price1;
        private String price2;
        private String data;
        private String msg1;
        private String msg2;

        public String getMsg1() {
            return msg1;
        }

        public void setMsg1(String msg1) {
            this.msg1 = msg1;
        }

        public String getMsg2() {
            return msg2;
        }

        public void setMsg2(String msg2) {
            this.msg2 = msg2;
        }

        public String getData() {
            return data;
        }

        public void setData(String date) {
            this.data = date;
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

        public String getCateid() {
            return cateid;
        }

        public void setCateid(String cateid) {
            this.cateid = cateid;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getPrice1() {
            return price1;
        }

        public void setPrice1(String price1) {
            this.price1 = price1;
        }

        public String getPrice2() {
            return price2;
        }

        public void setPrice2(String price2) {
            this.price2 = price2;
        }
    }
}
