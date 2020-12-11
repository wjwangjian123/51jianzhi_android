package com.part.jianzhiyi.modulemerchants.model.entity;

/**
 * Created by jyx on 2020/11/25
 *
 * @author jyx
 * @describe
 */
public class MIDFaPositiveEntity {

    private String code;
    private String msg;
    private CorporateBean corporate;

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

    public CorporateBean getCorporate() {
        return corporate;
    }

    public void setCorporate(CorporateBean corporate) {
        this.corporate = corporate;
    }

    public static class CorporateBean {

        private String f_name;
        private String ldentity_imgz;
        private String f_numid;

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
    }
}
