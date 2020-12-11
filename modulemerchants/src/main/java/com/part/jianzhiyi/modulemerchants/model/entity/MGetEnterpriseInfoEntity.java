package com.part.jianzhiyi.modulemerchants.model.entity;

/**
 * Created by jyx on 2020/11/26
 *
 * @author jyx
 * @describe
 */
public class MGetEnterpriseInfoEntity {

    private String code;
    private String msg;
    private DataBean data;
    private CompanyInfoBean company_info;
    private CorporateBean corporate;
    private ReasonBean reason;

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

    public CorporateBean getCorporate() {
        return corporate;
    }

    public void setCorporate(CorporateBean corporate) {
        this.corporate = corporate;
    }

    public ReasonBean getReason() {
        return reason;
    }

    public void setReason(ReasonBean reason) {
        this.reason = reason;
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

        private String company;
        private String company_num;
        private String type;
        private String name;
        private String scope;
        private String create_money;
        private String create_time;
        private String times;
        private String address;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCompany_num() {
            return company_num;
        }

        public void setCompany_num(String company_num) {
            this.company_num = company_num;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getCreate_money() {
            return create_money;
        }

        public void setCreate_money(String create_money) {
            this.create_money = create_money;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class CorporateBean {

        private String f_name;
        private String ldentity_imgz;
        private String f_numid;
        private String ldentity_imgf;

        public String getF_name() {
            return f_name;
        }

        public void setF_name(String f_name) {
            this.f_name = f_name;
        }

        public String getLdentity_imgz() {
            return ldentity_imgz;
        }

        public void setLdentity_imgz(String ldentity_imgz) {
            this.ldentity_imgz = ldentity_imgz;
        }

        public String getF_numid() {
            return f_numid;
        }

        public void setF_numid(String f_numid) {
            this.f_numid = f_numid;
        }

        public String getLdentity_imgf() {
            return ldentity_imgf;
        }

        public void setLdentity_imgf(String ldentity_imgf) {
            this.ldentity_imgf = ldentity_imgf;
        }
    }

    public static class ReasonBean {

        private String ht_reason;
        private int enterprise_status;

        public String getHt_reason() {
            return ht_reason;
        }

        public void setHt_reason(String ht_reason) {
            this.ht_reason = ht_reason;
        }

        public int getEnterprise_status() {
            return enterprise_status;
        }

        public void setEnterprise_status(int enterprise_status) {
            this.enterprise_status = enterprise_status;
        }
    }
}
