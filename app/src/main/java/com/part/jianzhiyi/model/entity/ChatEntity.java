package com.part.jianzhiyi.model.entity;

import java.io.Serializable;

/**
 * Created by jyx on 2020/6/17
 *
 * @author jyx
 * @describe
 */
public class ChatEntity {

    private int img;
    private String img1;
    private String msg1;
    private DataList mDataList;
    private DataBean mDataBean;

    public String getMsg1() {
        return msg1;
    }

    public void setMsg1(String msg1) {
        this.msg1 = msg1;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public DataList getDataList() {
        return mDataList;
    }

    public void setDataList(DataList dataList) {
        mDataList = dataList;
    }

    public DataBean getDataBean() {
        return mDataBean;
    }

    public void setDataBean(DataBean dataBean) {
        mDataBean = dataBean;
    }

    public static class DataList implements Serializable {
        private int img;
        private String img1;
        private String msg1;

        public int getImg() {
            return img;
        }

        public void setImg(int img) {
            this.img = img;
        }

        public String getImg1() {
            return img1;
        }

        public void setImg1(String img1) {
            this.img1 = img1;
        }

        public String getMsg1() {
            return msg1;
        }

        public void setMsg1(String msg1) {
            this.msg1 = msg1;
        }
    }
    public static class DataBean implements Serializable {
        /**
         * id : 594
         * title : 做任务赚佣金秒到账
         * contact_type : 4
         * contact : http://1t.click/b6Fm
         */

        private String id;
        private String title;
        private int contact_type;
        private String contact;
        private String place;
        private String company;
        private String msg1;
        private String msg2;
        private String price;
        private String method;
        private String sex;
        private String content;
        private int img;
        private String img1;

        public int getImg() {
            return img;
        }

        public void setImg(int img) {
            this.img = img;
        }

        public String getImg1() {
            return img1;
        }

        public void setImg1(String img1) {
            this.img1 = img1;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public String getMsg2() {
            return msg2;
        }

        public void setMsg2(String msg2) {
            this.msg2 = msg2;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
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

        public int getContact_type() {
            return contact_type;
        }

        public void setContact_type(int contact_type) {
            this.contact_type = contact_type;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }
    }
}
