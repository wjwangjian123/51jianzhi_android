package com.part.jianzhiyi.model.entity.moku;

import java.util.List;

/**
 * Created by jyx on 2020/9/7
 *
 * @author jyx
 * @describe
 */
public class MokuBillListEntity {

    /**
     * code : 200
     * msg :
     * data : [{"id":"2","user_id":"172","money":"1.5","type":"0","create_time":"2020-09-10 16:41:15","task_id":"0","icon":"","show_name":""},{"id":"1","user_id":"172","money":"1.5","type":"1","create_time":"2020-09-04 16:52:46","task_id":"2955","icon":"http://sdk-m-prod.oss-cn-shenzhen.aliyuncs.com/icon/961591666782810.png","show_name":"8.13新建测试任务-简单注册_1597290219614"}]
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
         * id : 2
         * user_id : 172
         * money : 1.5
         * type : 0
         * create_time : 2020-09-10 16:41:15
         * task_id : 0
         * icon :
         * show_name :
         */

        private String id;
        private String user_id;
        private String money;
        private int type;
        private String create_time;
        private String task_id;
        private String icon;
        private String show_name;

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

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getTask_id() {
            return task_id;
        }

        public void setTask_id(String task_id) {
            this.task_id = task_id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getShow_name() {
            return show_name;
        }

        public void setShow_name(String show_name) {
            this.show_name = show_name;
        }
    }
}
