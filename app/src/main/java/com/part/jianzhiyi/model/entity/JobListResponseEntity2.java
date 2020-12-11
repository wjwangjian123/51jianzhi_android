package com.part.jianzhiyi.model.entity;

import java.util.List;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/10/12
 * @modified by:shixinxin
 **/
public class JobListResponseEntity2 {

    private AdvertisingBean advertising;
    private List<DataBean> data;

    public AdvertisingBean getAdvertising() {
        return advertising;
    }

    public void setAdvertising(AdvertisingBean advertising) {
        this.advertising = advertising;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class AdvertisingBean {

        private String id;
        private String banner;
        private String job_id;
        private String image;
        private String type;
        private String urls;
        private String app_id;
        private String update_time;
        private String create_time;
        private String types;
        private String advert_order;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public String getJob_id() {
            return job_id;
        }

        public void setJob_id(String job_id) {
            this.job_id = job_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrls() {
            return urls;
        }

        public void setUrls(String urls) {
            this.urls = urls;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }

        public String getAdvert_order() {
            return advert_order;
        }

        public void setAdvert_order(String advert_order) {
            this.advert_order = advert_order;
        }

        @Override
        public String toString() {
            return "AdvertisingBean{" +
                    "id='" + id + '\'' +
                    ", banner='" + banner + '\'' +
                    ", job_id='" + job_id + '\'' +
                    ", image='" + image + '\'' +
                    ", type='" + type + '\'' +
                    ", urls='" + urls + '\'' +
                    ", app_id='" + app_id + '\'' +
                    ", update_time='" + update_time + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", types='" + types + '\'' +
                    ", advert_order='" + advert_order + '\'' +
                    '}';
        }
    }

    public static class DataBean {

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
        private int uiType = 0;
        private String place;

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public DataBean() {
        }

        public DataBean(int uiType) {
            this.uiType = uiType;
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

        public int getUiType() {
            return uiType;
        }

        public void setUiType(int uiType) {
            this.uiType = uiType;
        }


        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", title='" + title + '\'' +
                    ", time='" + time + '\'' +
                    ", city='" + city + '\'' +
                    ", price='" + price + '\'' +
                    ", sex='" + sex + '\'' +
                    ", company='" + company + '\'' +
                    ", tag='" + tag + '\'' +
                    ", content='" + content + '\'' +
                    ", method='" + method + '\'' +
                    ", type='" + type + '\'' +
                    ", contact='" + contact + '\'' +
                    ", contact_type='" + contact_type + '\'' +
                    ", isfavourite=" + isfavourite +
                    ", order_number='" + order_number + '\'' +
                    ", desc='" + desc + '\'' +
                    ", price1='" + price1 + '\'' +
                    ", price2='" + price2 + '\'' +
                    ", uiType=" + uiType +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "JobListResponseEntity2{" +
                "advertising=" + advertising +
                ", data=" + data +
                '}';
    }
}
