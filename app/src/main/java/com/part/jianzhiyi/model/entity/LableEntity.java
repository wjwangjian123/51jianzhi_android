package com.part.jianzhiyi.model.entity;

import java.util.List;

/**
 * Created by jyx on 2020/6/11
 *
 * @author jyx
 * @describe
 */
public class LableEntity {

    /**
     * code : 200
     * msg : 请求成功
     * data : [{"id":"1","title":"IT","pid":"0","create_time":"2020-06-05 15:12:25","update_time":null,"status":"1"},{"id":"2","title":"家政","pid":"0","create_time":"2020-06-05 15:12:51","update_time":null,"status":"0"}]
     */

    private String code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * title : IT
         * pid : 0
         * create_time : 2020-06-05 15:12:25
         * update_time : null
         * status : 1
         */

        private String id;
        private String title;
        private String pid;
        private String create_time;
        private String update_time;
        private String status;
        private int select = 0;

        public int getSelect() {
            return select;
        }

        public void setSelect(int select) {
            this.select = select;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
