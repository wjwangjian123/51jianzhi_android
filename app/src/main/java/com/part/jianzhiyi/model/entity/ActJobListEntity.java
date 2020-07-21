package com.part.jianzhiyi.model.entity;

import java.util.List;

/**
 * Created by jyx on 2020/6/11
 *
 * @author jyx
 * @describe
 */
public class ActJobListEntity {

    /**
     * code : 200
     * msg : 请求成功
     * data : {"data":[{"id":"594","title":"做任务赚佣金秒到账","price":"1元/单","time":"","method":"日结","price1":"1","price2":"元/单"},{"id":"885","title":"简单任务，宅家赚钱","price":"100元/天","time":"长期","method":"日结","price1":"100","price2":"元/天"}],"info":{"backimg":"http://jxw-img.oss-cn-beijing.aliyuncs.com/LJZ/20200412/1586682495.png"}}
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
         * data : [{"id":"594","title":"做任务赚佣金秒到账","price":"1元/单","time":"","method":"日结","price1":"1","price2":"元/单"},{"id":"885","title":"简单任务，宅家赚钱","price":"100元/天","time":"长期","method":"日结","price1":"100","price2":"元/天"}]
         * info : {"backimg":"http://jxw-img.oss-cn-beijing.aliyuncs.com/LJZ/20200412/1586682495.png"}
         */

        private InfoBean info;
        private List<DataBean> data;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class InfoBean {
            /**
             * backimg : http://jxw-img.oss-cn-beijing.aliyuncs.com/LJZ/20200412/1586682495.png
             */

            private String backimg;

            public String getBackimg() {
                return backimg;
            }

            public void setBackimg(String backimg) {
                this.backimg = backimg;
            }
        }

        public static class DataBean {
            /**
             * id : 594
             * title : 做任务赚佣金秒到账
             * price : 1元/单
             * time :
             * method : 日结
             * price1 : 1
             * price2 : 元/单
             */

            private String id;
            private String title;
            private String price;
            private String time;
            private String method;
            private String price1;
            private String price2;
            private String place;

            public String getPlace() {
                return place;
            }

            public void setPlace(String place) {
                this.place = place;
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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getMethod() {
                return method;
            }

            public void setMethod(String method) {
                this.method = method;
            }

            public String getPrice1() {
                return price1;
            }

            public void setPrice1(String price1) {
                this.price1 = price1;
            }

            public String getPrice2() {
                return price2;
            }

            public void setPrice2(String price2) {
                this.price2 = price2;
            }
        }
    }
}
