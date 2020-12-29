package com.part.jianzhiyi.model.entity;

import java.util.List;

/**
 * Created by jyx on 2020/6/11
 *
 * @author jyx
 * @describe
 */
public class JobDetailEntity {

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

        private InfoBean info;
        private List<JobListBean> job_list;
        private List<LoveBean> love;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public List<JobListBean> getJob_list() {
            return job_list;
        }

        public void setJob_list(List<JobListBean> job_list) {
            this.job_list = job_list;
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
            private String content;
            private String company;
            private String tag;
            private String method;
            private String status;
            private String type;
            private String create_time;
            private String update_time;
            private String contact;
            private String contact_type;
            private String contactXing;
            private String isfavourite;
            private String order_number;
            private String number;
            private String duration;
            private String place;
            private String app_id;
            private String desc;
            private String age;
            private String uid;
            private String cateid;
            private String blocked;
            private String is_customer;
            private String is_del;
            private String admin_puid;
            private String check_admin;
            private String price_time;
            private String weekly;
            private String is_copy;
            private String is_join;
            private String category;
            private String love;
            private String city_ids;
            private String reason;
            private String label;
            private String isJoin;
            private String price1;
            private String price2;
            private boolean check_join;
            private String join_msg;
            private int error_type;
            private boolean is_show;
            private int default_checknum;
            private String join_meet;
            private int is_company;

            public int getIs_company() {
                return is_company;
            }

            public void setIs_company(int is_company) {
                this.is_company = is_company;
            }

            public String getJoin_meet() {
                return join_meet;
            }

            public void setJoin_meet(String join_meet) {
                this.join_meet = join_meet;
            }

            public int getDefault_checknum() {
                return default_checknum;
            }

            public void setDefault_checknum(int default_checknum) {
                this.default_checknum = default_checknum;
            }

            public String getContactXing() {
                return contactXing;
            }

            public void setContactXing(String contactXing) {
                this.contactXing = contactXing;
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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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

            public String getMethod() {
                return method;
            }

            public void setMethod(String method) {
                this.method = method;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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

            public String getApp_id() {
                return app_id;
            }

            public void setApp_id(String app_id) {
                this.app_id = app_id;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getCateid() {
                return cateid;
            }

            public void setCateid(String cateid) {
                this.cateid = cateid;
            }

            public String getBlocked() {
                return blocked;
            }

            public void setBlocked(String blocked) {
                this.blocked = blocked;
            }

            public String getIs_customer() {
                return is_customer;
            }

            public void setIs_customer(String is_customer) {
                this.is_customer = is_customer;
            }

            public String getIs_del() {
                return is_del;
            }

            public void setIs_del(String is_del) {
                this.is_del = is_del;
            }

            public String getAdmin_puid() {
                return admin_puid;
            }

            public void setAdmin_puid(String admin_puid) {
                this.admin_puid = admin_puid;
            }

            public String getCheck_admin() {
                return check_admin;
            }

            public void setCheck_admin(String check_admin) {
                this.check_admin = check_admin;
            }

            public String getPrice_time() {
                return price_time;
            }

            public void setPrice_time(String price_time) {
                this.price_time = price_time;
            }

            public String getWeekly() {
                return weekly;
            }

            public void setWeekly(String weekly) {
                this.weekly = weekly;
            }

            public String getIs_copy() {
                return is_copy;
            }

            public void setIs_copy(String is_copy) {
                this.is_copy = is_copy;
            }

            public String getIs_join() {
                return is_join;
            }

            public void setIs_join(String is_join) {
                this.is_join = is_join;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getLove() {
                return love;
            }

            public void setLove(String love) {
                this.love = love;
            }

            public String getCity_ids() {
                return city_ids;
            }

            public void setCity_ids(String city_ids) {
                this.city_ids = city_ids;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
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

            public int getError_type() {
                return error_type;
            }

            public void setError_type(int error_type) {
                this.error_type = error_type;
            }

            public boolean isIs_show() {
                return is_show;
            }

            public void setIs_show(boolean is_show) {
                this.is_show = is_show;
            }
        }

        public static class JobListBean {

            private String id;
            private String title;
            private String price;
            private boolean is_join;
            private int browse_num;
            private String price1;
            private String price2;
            private String place;
            private int ivType = 0;

            public int getIvType() {
                return ivType;
            }

            public void setIvType(int ivType) {
                this.ivType = ivType;
            }

            public JobListBean(int ivType) {
                this.ivType = ivType;
            }

            public String getPlace() {
                return place;
            }

            public void setPlace(String place) {
                this.place = place;
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

            public boolean isIs_join() {
                return is_join;
            }

            public void setIs_join(boolean is_join) {
                this.is_join = is_join;
            }

            public int getBrowse_num() {
                return browse_num;
            }

            public void setBrowse_num(int browse_num) {
                this.browse_num = browse_num;
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
            private String place;

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
}
