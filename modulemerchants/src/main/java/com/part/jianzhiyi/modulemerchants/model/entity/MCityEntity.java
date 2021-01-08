package com.part.jianzhiyi.modulemerchants.model.entity;

import java.util.List;

/**
 * Created by jyx on 2021/1/7
 *
 * @author jyx
 * @describe
 */
public class MCityEntity {

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

        private int one_city_id;
        private String one_city_names;
        private List<TowCityBean> tow_city;

        public int getOne_city_id() {
            return one_city_id;
        }

        public void setOne_city_id(int one_city_id) {
            this.one_city_id = one_city_id;
        }

        public String getOne_city_names() {
            return one_city_names;
        }

        public void setOne_city_names(String one_city_names) {
            this.one_city_names = one_city_names;
        }

        public List<TowCityBean> getTow_city() {
            return tow_city;
        }

        public void setTow_city(List<TowCityBean> tow_city) {
            this.tow_city = tow_city;
        }

        public static class TowCityBean {

            private int tow_city_id;
            private String tow_city_names;

            public int getTow_city_id() {
                return tow_city_id;
            }

            public void setTow_city_id(int tow_city_id) {
                this.tow_city_id = tow_city_id;
            }

            public String getTow_city_names() {
                return tow_city_names;
            }

            public void setTow_city_names(String tow_city_names) {
                this.tow_city_names = tow_city_names;
            }
        }
    }
}
