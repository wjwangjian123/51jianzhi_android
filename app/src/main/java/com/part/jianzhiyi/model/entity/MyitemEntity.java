package com.part.jianzhiyi.model.entity;

import java.util.List;

/**
 * Created by jyx on 2020/7/21
 *
 * @author jyx
 * @describe
 */
public class MyitemEntity {

    /**
     * code : 200
     * msg : 请求成功
     * data : [{"id":"1","item":"线上职位","pid":"0","create_time":"2020-06-28 10:57:05","is_del":"1","children":[{"id":"2","item":"PHP开发","pid":"1","create_time":"2020-06-28 10:57:58","is_del":"1"}]}]
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
         * item : 线上职位
         * pid : 0
         * create_time : 2020-06-28 10:57:05
         * is_del : 1
         * children : [{"id":"2","item":"PHP开发","pid":"1","create_time":"2020-06-28 10:57:58","is_del":"1"}]
         */

        private String id;
        private String item;
        private String pid;
        private String create_time;
        private String is_del;
        private List<ChildrenBean> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
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

        public String getIs_del() {
            return is_del;
        }

        public void setIs_del(String is_del) {
            this.is_del = is_del;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * id : 2
             * item : PHP开发
             * pid : 1
             * create_time : 2020-06-28 10:57:58
             * is_del : 1
             */

            private String id;
            private String item;
            private String pid;
            private String create_time;
            private String is_del;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getItem() {
                return item;
            }

            public void setItem(String item) {
                this.item = item;
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

            public String getIs_del() {
                return is_del;
            }

            public void setIs_del(String is_del) {
                this.is_del = is_del;
            }
        }
    }
}
