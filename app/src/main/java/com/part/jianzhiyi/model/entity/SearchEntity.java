package com.part.jianzhiyi.model.entity;

import java.util.List;

/**
 * Created by jyx on 2020/6/11
 *
 * @author jyx
 * @describe
 */
public class SearchEntity {

    /**
     * code : 200
     * msg : 请求成功
     * data : [{"id":"570","title":"高薪兼职\u2014在家可做简单上手","price":"280元/天","time":"不限","method":"日结","cateid":"570","price1":"280","price2":"元/天"},{"id":"594","title":"做任务赚佣金秒到账","price":"1元/单","time":"","method":"日结","cateid":"594","price1":"1","price2":"元/单"},{"id":"3431","title":"在家可做，厂商代发，简单无压力","price":"380元/天","time":"长期","method":"当天结算","cateid":"3431","price1":"380","price2":"元/天"}]
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

    public static class DataBean {
        /**
         * id : 570
         * title : 高薪兼职—在家可做简单上手
         * price : 280元/天
         * time : 不限
         * method : 日结
         * cateid : 570
         * price1 : 280
         * price2 : 元/天
         */

        private String id;
        private String title;
        private String price;
        private String time;
        private String method;
        private String cateid;
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
