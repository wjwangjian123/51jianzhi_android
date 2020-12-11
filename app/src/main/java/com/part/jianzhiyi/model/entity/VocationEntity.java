package com.part.jianzhiyi.model.entity;

import java.util.List;

/**
 * @author:shixinxin
 * @Date :2020-03-26
 **/
public class VocationEntity {

    private InfoBean info;
    private List<LoveBean> love;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<LoveBean> getLove() {
        return love;
    }

    public void setLove(List<LoveBean> love) {
        this.love = love;
    }


    public static class InfoBean {

        private String id;
        private String avatar;
        private String title;
        private String time;
        private String city;
        private String price;
        private String sex;
        private String company;
        private String tag;
        private String content;
        private String method;
        private String type;
        private String contact;
        private String contact_type;
        private String isfavourite;
        private String number;
        private String duration;
        private String place;
        private String age;
        private String desc;
        private String isJoin;
        private String is_copy;
        private String price1;
        private String price2;
        private boolean check_join;
        private String join_msg;
        private int error_type;
        private boolean is_show;

        public boolean isIs_show() {
            return is_show;
        }

        public void setIs_show(boolean is_show) {
            this.is_show = is_show;
        }

        public int getError_type() {
            return error_type;
        }

        public void setError_type(int error_type) {
            this.error_type = error_type;
        }

        public boolean isCheck_join() {
            return check_join;
        }

        public void setCheck_join(boolean check_join) {
            this.check_join = check_join;
        }

        public String getJoin_msg() {
            return join_msg;
        }

        public void setJoin_msg(String join_msg) {
            this.join_msg = join_msg;
        }

        public String getIs_copy() {
            return is_copy;
        }

        public void setIs_copy(String is_copy) {
            this.is_copy = is_copy;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getContact_type() {
            return contact_type;
        }

        public void setContact_type(String contact_type) {
            this.contact_type = contact_type;
        }

        public String getIsfavourite() {
            return isfavourite;
        }

        public void setIsfavourite(String isfavourite) {
            this.isfavourite = isfavourite;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getIsJoin() {
            return isJoin;
        }

        public void setIsJoin(String isJoin) {
            this.isJoin = isJoin;
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

    public static class LoveBean {

        private String id;
        private String avatar;
        private String title;
        private String time;
        private String city;
        private String price;
        private String sex;
        private String company;
        private String tag;
        private String content;
        private String method;
        private String type;
        private String contact;
        private String contact_type;
        private String isfavourite;
        private String order_number;
        private String desc;
        private String price1;
        private String price2;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getContact_type() {
            return contact_type;
        }

        public void setContact_type(String contact_type) {
            this.contact_type = contact_type;
        }

        public String getIsfavourite() {
            return isfavourite;
        }

        public void setIsfavourite(String isfavourite) {
            this.isfavourite = isfavourite;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
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
