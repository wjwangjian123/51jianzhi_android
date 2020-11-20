package com.part.jianzhiyi.model.entity.integral;

import java.util.List;

/**
 * Created by jyx on 2020/10/27
 *
 * @author jyx
 * @describe
 */
public class MyIntegralEntity {

    /**
     * code : 200
     * msg : 请求成功
     * data : {"data":[{"id":"59","user_id":"209","status":"1","type":"1","is_yx":"0","create_time":"2020-11-17 14:26:16","ymd":"2020-11-17","num":"3","ugoods_id":"0","job_id":"0","jfname":" 3职豆","typename":"每日签到"}],"integral":null}
     */

    private String code;
    private String msg;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * data : [{"id":"59","user_id":"209","status":"1","type":"1","is_yx":"0","create_time":"2020-11-17 14:26:16","ymd":"2020-11-17","num":"3","ugoods_id":"0","job_id":"0","jfname":" 3职豆","typename":"每日签到"}]
         * integral : null
         */

        private String integral;
        private List<DataBean> data;

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 59
             * user_id : 209
             * status : 1
             * type : 1
             * is_yx : 0
             * create_time : 2020-11-17 14:26:16
             * ymd : 2020-11-17
             * num : 3
             * ugoods_id : 0
             * job_id : 0
             * jfname :  3职豆
             * typename : 每日签到
             */

            private String id;
            private String user_id;
            private String status;
            private String type;
            private String is_yx;
            private String create_time;
            private String ymd;
            private String num;
            private String ugoods_id;
            private String job_id;
            private String jfname;
            private String typename;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getIs_yx() {
                return is_yx;
            }

            public void setIs_yx(String is_yx) {
                this.is_yx = is_yx;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getYmd() {
                return ymd;
            }

            public void setYmd(String ymd) {
                this.ymd = ymd;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getUgoods_id() {
                return ugoods_id;
            }

            public void setUgoods_id(String ugoods_id) {
                this.ugoods_id = ugoods_id;
            }

            public String getJob_id() {
                return job_id;
            }

            public void setJob_id(String job_id) {
                this.job_id = job_id;
            }

            public String getJfname() {
                return jfname;
            }

            public void setJfname(String jfname) {
                this.jfname = jfname;
            }

            public String getTypename() {
                return typename;
            }

            public void setTypename(String typename) {
                this.typename = typename;
            }
        }
    }
}
