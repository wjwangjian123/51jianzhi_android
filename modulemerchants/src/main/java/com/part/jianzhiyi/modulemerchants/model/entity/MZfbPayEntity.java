package com.part.jianzhiyi.modulemerchants.model.entity;

/**
 * Created by jyx on 2020/5/20
 *
 * @author jyx
 * @describe
 */
public class MZfbPayEntity {

    private String code;
    private String msg;
    private String data;
    private String order_num;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }
}
