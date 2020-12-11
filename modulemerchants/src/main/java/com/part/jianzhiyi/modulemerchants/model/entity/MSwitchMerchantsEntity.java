package com.part.jianzhiyi.modulemerchants.model.entity;

/**
 * Created by jyx on 2020/11/19
 *
 * @author jyx
 * @describe
 */
public class MSwitchMerchantsEntity {

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

        private String token;
        private String url;
        private String start_time;
        private String end_time;
        private BusInfoBean bus_info;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public BusInfoBean getBus_info() {
            return bus_info;
        }

        public void setBus_info(BusInfoBean bus_info) {
            this.bus_info = bus_info;
        }

        public static class BusInfoBean {

            private int id;
            private String name;
            private String phone;
            private int bus_identity;
            private int is_enterprise;
            private String number;
            private int status;
            private String ht_reason;
            private int numid_status;
            private int is_sing;
            private int is_auth;

            public int getIs_sing() {
                return is_sing;
            }

            public void setIs_sing(int is_sing) {
                this.is_sing = is_sing;
            }

            public String getHt_reason() {
                return ht_reason;
            }

            public void setHt_reason(String ht_reason) {
                this.ht_reason = ht_reason;
            }

            public int getNumid_status() {
                return numid_status;
            }

            public void setNumid_status(int numid_status) {
                this.numid_status = numid_status;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getBus_identity() {
                return bus_identity;
            }

            public void setBus_identity(int bus_identity) {
                this.bus_identity = bus_identity;
            }

            public int getIs_enterprise() {
                return is_enterprise;
            }

            public void setIs_enterprise(int is_enterprise) {
                this.is_enterprise = is_enterprise;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getIs_auth() {
                return is_auth;
            }

            public void setIs_auth(int is_auth) {
                this.is_auth = is_auth;
            }
        }
    }
}
