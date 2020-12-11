package com.part.jianzhiyi.modulemerchants.model.entity;

import java.util.List;

/**
 * Created by jyx on 2020/11/26
 *
 * @author jyx
 * @describe
 */
public class MJobListEntity {

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

        private BinfoBean binfo;
        private List<DataBean> data;

        public BinfoBean getBinfo() {
            return binfo;
        }

        public void setBinfo(BinfoBean binfo) {
            this.binfo = binfo;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class BinfoBean {

            private int status;
            private int is_copy;
            private int is_join;
            private int is_click;
            private String start_time;
            private String end_time;

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getIs_copy() {
                return is_copy;
            }

            public void setIs_copy(int is_copy) {
                this.is_copy = is_copy;
            }

            public int getIs_join() {
                return is_join;
            }

            public void setIs_join(int is_join) {
                this.is_join = is_join;
            }

            public int getIs_click() {
                return is_click;
            }

            public void setIs_click(int is_click) {
                this.is_click = is_click;
            }
        }

        public static class DataBean {

            private String title;
            private String id;
            private int status;
            private String reason;
            private String type;
            private String exposure_num;
            private int is_pay;
            private String zd;
            private int sx;
            private String join;
            private String copy;
            private String reason_title;
            private int is_copy;
            private int is_join;
            private int is_click;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getExposure_num() {
                return exposure_num;
            }

            public void setExposure_num(String exposure_num) {
                this.exposure_num = exposure_num;
            }

            public int getIs_pay() {
                return is_pay;
            }

            public void setIs_pay(int is_pay) {
                this.is_pay = is_pay;
            }

            public String getZd() {
                return zd;
            }

            public void setZd(String zd) {
                this.zd = zd;
            }

            public int getSx() {
                return sx;
            }

            public void setSx(int sx) {
                this.sx = sx;
            }

            public String getJoin() {
                return join;
            }

            public void setJoin(String join) {
                this.join = join;
            }

            public String getCopy() {
                return copy;
            }

            public void setCopy(String copy) {
                this.copy = copy;
            }

            public String getReason_title() {
                return reason_title;
            }

            public void setReason_title(String reason_title) {
                this.reason_title = reason_title;
            }

            public int getIs_copy() {
                return is_copy;
            }

            public void setIs_copy(int is_copy) {
                this.is_copy = is_copy;
            }

            public int getIs_join() {
                return is_join;
            }

            public void setIs_join(int is_join) {
                this.is_join = is_join;
            }

            public int getIs_click() {
                return is_click;
            }

            public void setIs_click(int is_click) {
                this.is_click = is_click;
            }
        }
    }
}
