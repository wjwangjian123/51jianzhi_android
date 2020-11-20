package com.part.jianzhiyi.model.entity.integral;

/**
 * Created by jyx on 2020/11/17
 *
 * @author jyx
 * @describe
 */
public class SignEntity {

    /**
     * code : 200
     * msg : 请求成功
     * data : {"sing":false}
     */

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
        /**
         * sing : false
         */

        private boolean sing;

        public boolean isSing() {
            return sing;
        }

        public void setSing(boolean sing) {
            this.sing = sing;
        }
    }
}
