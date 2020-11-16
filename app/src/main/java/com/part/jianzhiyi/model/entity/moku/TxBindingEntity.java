package com.part.jianzhiyi.model.entity.moku;

/**
 * Created by jyx on 2020/9/18
 *
 * @author jyx
 * @describe
 */
public class TxBindingEntity {

    /**
     * code : 202
     * msg : token失效请重新授权
     */

    private String code;
    private String msg;

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
}
