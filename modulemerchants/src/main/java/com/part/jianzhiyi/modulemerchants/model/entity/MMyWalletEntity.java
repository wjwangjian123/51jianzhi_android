package com.part.jianzhiyi.modulemerchants.model.entity;

import java.util.List;

/**
 * Created by jyx on 2020/11/26
 *
 * @author jyx
 * @describe
 */
public class MMyWalletEntity {

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

        private BusInfoBean busInfo;
        private List<SubListBean> subList;

        public BusInfoBean getBusInfo() {
            return busInfo;
        }

        public void setBusInfo(BusInfoBean busInfo) {
            this.busInfo = busInfo;
        }

        public List<SubListBean> getSubList() {
            return subList;
        }

        public void setSubList(List<SubListBean> subList) {
            this.subList = subList;
        }

        public static class BusInfoBean {

            private int id;
            private String money;
            private String security;
            private String today_money;
            private String estimate_money;
            private String msg;
            private String date;
            private int is_copy;
            private int is_join;
            private int is_click;
            private int is_pay;
            private String start_time;
            private String end_time;

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public int getIs_pay() {
                return is_pay;
            }

            public void setIs_pay(int is_pay) {
                this.is_pay = is_pay;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getSecurity() {
                return security;
            }

            public void setSecurity(String security) {
                this.security = security;
            }

            public String getToday_money() {
                return today_money;
            }

            public void setToday_money(String today_money) {
                this.today_money = today_money;
            }

            public String getEstimate_money() {
                return estimate_money;
            }

            public void setEstimate_money(String estimate_money) {
                this.estimate_money = estimate_money;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getIs_copy() {
                return is_copy;
            }

            public void setIs_copy(int is_copy) {
                this.is_copy = is_copy;
            }

            public int getIs_join() {
                return is_join;
            }

            public void setIs_join(int is_join) {
                this.is_join = is_join;
            }

            public int getIs_click() {
                return is_click;
            }

            public void setIs_click(int is_click) {
                this.is_click = is_click;
            }
        }

        public static class SubListBean {

            private String create_time;
            private String money;
            private String yy_money;
            private String uid;
            private String type;
            private String exposure_num;
            private String join;
            private String copy;
            private String id;
            private String y_money;
            private String seller_id;
            private String date;
            private String type_msg;
            private int is_copy;
            private int is_join;
            private int is_click;

            public String getType_msg() {
                return type_msg;
            }

            public void setType_msg(String type_msg) {
                this.type_msg = type_msg;
            }

            public int getIs_copy() {
                return is_copy;
            }

            public void setIs_copy(int is_copy) {
                this.is_copy = is_copy;
            }

            public int getIs_join() {
                return is_join;
            }

            public void setIs_join(int is_join) {
                this.is_join = is_join;
            }

            public int getIs_click() {
                return is_click;
            }

            public void setIs_click(int is_click) {
                this.is_click = is_click;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getY_money() {
                return y_money;
            }

            public void setY_money(String y_money) {
                this.y_money = y_money;
            }

            public String getSeller_id() {
                return seller_id;
            }

            public void setSeller_id(String seller_id) {
                this.seller_id = seller_id;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getYy_money() {
                return yy_money;
            }

            public void setYy_money(String yy_money) {
                this.yy_money = yy_money;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getExposure_num() {
                return exposure_num;
            }

            public void setExposure_num(String exposure_num) {
                this.exposure_num = exposure_num;
            }

            public String getJoin() {
                return join;
            }

            public void setJoin(String join) {
                this.join = join;
            }

            public String getCopy() {
                return copy;
            }

            public void setCopy(String copy) {
                this.copy = copy;
            }
        }
    }
}
