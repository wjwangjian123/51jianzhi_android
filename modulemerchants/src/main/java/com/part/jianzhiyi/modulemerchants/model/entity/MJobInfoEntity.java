package com.part.jianzhiyi.modulemerchants.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jyx on 2020/11/26
 *
 * @author jyx
 * @describe
 */
public class MJobInfoEntity implements Serializable {

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

    public static class DataBean implements Serializable {

        private String title;
        private String method;
        private String label_id;
        private String time;
        private String sex;
        private String price;
        private String age;
        private String content;
        private int contact_type;
        private String number;
        private String place;
        private String duration;
        private String id;
        private String label_name;
        private String price2;
        private String label_pid;
        private String age1;
        private String age2;
        private int one_city_id;
        private int tow_city_id;
        private String one_city_name;
        private String tow_city_name;
        private List<String> contact;

        public int getOne_city_id() {
            return one_city_id;
        }

        public void setOne_city_id(int one_city_id) {
            this.one_city_id = one_city_id;
        }

        public int getTow_city_id() {
            return tow_city_id;
        }

        public void setTow_city_id(int tow_city_id) {
            this.tow_city_id = tow_city_id;
        }

        public String getOne_city_name() {
            return one_city_name;
        }

        public void setOne_city_name(String one_city_name) {
            this.one_city_name = one_city_name;
        }

        public String getTow_city_name() {
            return tow_city_name;
        }

        public void setTow_city_name(String tow_city_name) {
            this.tow_city_name = tow_city_name;
        }

        public String getAge1() {
            return age1;
        }

        public void setAge1(String age1) {
            this.age1 = age1;
        }

        public String getAge2() {
            return age2;
        }

        public void setAge2(String age2) {
            this.age2 = age2;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getLabel_id() {
            return label_id;
        }

        public void setLabel_id(String label_id) {
            this.label_id = label_id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
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

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getContact_type() {
            return contact_type;
        }

        public void setContact_type(int contact_type) {
            this.contact_type = contact_type;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLabel_name() {
            return label_name;
        }

        public void setLabel_name(String label_name) {
            this.label_name = label_name;
        }

        public String getPrice2() {
            return price2;
        }

        public void setPrice2(String price2) {
            this.price2 = price2;
        }

        public String getLabel_pid() {
            return label_pid;
        }

        public void setLabel_pid(String label_pid) {
            this.label_pid = label_pid;
        }

        public List<String> getContact() {
            return contact;
        }

        public void setContact(List<String> contact) {
            this.contact = contact;
        }
    }
}
