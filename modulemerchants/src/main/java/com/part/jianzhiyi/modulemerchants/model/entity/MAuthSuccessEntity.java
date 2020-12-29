package com.part.jianzhiyi.modulemerchants.model.entity;

/**
 * Created by jyx on 2020/12/28
 *
 * @author jyx
 * @describe
 */
public class MAuthSuccessEntity {

    private String code;
    private String msg;
    private int audit_status;

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

    public int getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(int audit_status) {
        this.audit_status = audit_status;
    }
}
