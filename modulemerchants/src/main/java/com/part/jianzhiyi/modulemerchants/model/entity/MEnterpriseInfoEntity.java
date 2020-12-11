package com.part.jianzhiyi.modulemerchants.model.entity;

/**
 * Created by jyx on 2020/11/19
 *
 * @author jyx
 * @describe
 */
public class MEnterpriseInfoEntity {

    private String code;
    private String msg;
    private DataBean data;
    private CompanyInfoBean company_info;

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

    public CompanyInfoBean getCompany_info() {
        return company_info;
    }

    public void setCompany_info(CompanyInfoBean company_info) {
        this.company_info = company_info;
    }

    public static class DataBean {

        private String company_num;
        private String names;
        private String company;
        private String yy_img;

        public String getCompany_num() {
            return company_num;
        }

        public void setCompany_num(String company_num) {
            this.company_num = company_num;
        }

        public String getNames() {
            return names;
        }

        public void setNames(String names) {
            this.names = names;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getYy_img() {
            return yy_img;
        }

        public void setYy_img(String yy_img) {
            this.yy_img = yy_img;
        }
    }

    public static class CompanyInfoBean {

        private String company_num;
        private String scope;
        private String create_time;
        private String name;
        private String create_money;
        private String address;
        private String company;
        private String times;
        private String type;

        public String getCompany_num() {
            return company_num;
        }

        public void setCompany_num(String company_num) {
            this.company_num = company_num;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreate_money() {
            return create_money;
        }

        public void setCreate_money(String create_money) {
            this.create_money = create_money;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
