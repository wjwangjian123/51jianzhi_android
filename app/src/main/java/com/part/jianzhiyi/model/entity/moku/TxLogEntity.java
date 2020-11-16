package com.part.jianzhiyi.model.entity.moku;

import java.util.List;

/**
 * Created by jyx on 2020/9/18
 *
 * @author jyx
 * @describe
 */
public class TxLogEntity {

    /**
     * code : 200
     * msg : ok
     * data : [{"id":"2","user_id":"173","money":" 10.00","status":"2","create_time":"2020-09-17 14:26:22","update_time":"2020-09-17 15:54:52","ali_user_id":"2088102176858141","sub_msg":"账户状态异常","title":"提现失败（账户状态异常）","time":"2020-09-17 15:54:52"},{"id":"1","user_id":"173","money":"-10.00","status":"1","create_time":"2020-09-16 16:34:18","update_time":"2020-09-17 16:41:29","ali_user_id":"2088102176858141","sub_msg":"账户状态异常","title":"提现成功","time":"2020-09-17 16:41:29"}]
     * map : {"page":1,"pagetotal":1,"count":"2"}
     */

    private String code;
    private String msg;
    private MapBean map;
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

    public MapBean getMap() {
        return map;
    }

    public void setMap(MapBean map) {
        this.map = map;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class MapBean {
        /**
         * page : 1
         * pagetotal : 1
         * count : 2
         */

        private int page;
        private int pagetotal;
        private String count;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPagetotal() {
            return pagetotal;
        }

        public void setPagetotal(int pagetotal) {
            this.pagetotal = pagetotal;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }

    public static class DataBean {
        /**
         * id : 2
         * user_id : 173
         * money :  10.00
         * status : 2
         * create_time : 2020-09-17 14:26:22
         * update_time : 2020-09-17 15:54:52
         * ali_user_id : 2088102176858141
         * sub_msg : 账户状态异常
         * title : 提现失败（账户状态异常）
         * time : 2020-09-17 15:54:52
         */

        private String id;
        private String user_id;
        private String money;
        private String status;
        private String create_time;
        private String update_time;
        private String ali_user_id;
        private String sub_msg;
        private String title;
        private String time;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getAli_user_id() {
            return ali_user_id;
        }

        public void setAli_user_id(String ali_user_id) {
            this.ali_user_id = ali_user_id;
        }

        public String getSub_msg() {
            return sub_msg;
        }

        public void setSub_msg(String sub_msg) {
            this.sub_msg = sub_msg;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
