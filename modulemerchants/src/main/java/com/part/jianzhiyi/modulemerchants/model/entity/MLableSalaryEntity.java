package com.part.jianzhiyi.modulemerchants.model.entity;

import java.util.List;

/**
 * Created by jyx on 2020/11/30
 *
 * @author jyx
 * @describe
 */
public class MLableSalaryEntity {

    private String code;
    private String msg;
    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
