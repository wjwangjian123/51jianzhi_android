package com.part.jianzhiyi.model.entity.integral;

/**
 * Created by jyx on 2020/10/27
 *
 * @author jyx
 * @describe
 */
public class MyExchangeEntity {

    /**
     * code : 200
     * msg : 请求成功
     * data : [{"id":"1","goods_id":"1","user_id":"142","intergeal":"66","create_time":"2020-10-26 11:06:37","goods_code":"asda","goods_code_id":"2","goods_name":"测试"," img_url":"https://tanlu-img.oss-accelerate.aliyuncs.com/goods/20201022/106fee0ef45ff9873291023729e03451.jpeg"}]
     */

    private String code;
    private String msg;
    private java.util.List<DataBean> data;

    public static class DataBean {
        /**
         * id : 1
         * goods_id : 1
         * user_id : 142
         * intergeal : 66
         * create_time : 2020-10-26 11:06:37
         * goods_code : asda
         * goods_code_id : 2
         * goods_name : 测试
         *  img_url : https://tanlu-img.oss-accelerate.aliyuncs.com/goods/20201022/106fee0ef45ff9873291023729e03451.jpeg
         */

        private String id;
        private String goods_id;
        private String user_id;
        private String intergeal;
        private String create_time;
        private String goods_code;
        private String goods_code_id;
        private String goods_name;
        private String img_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getIntergeal() {
            return intergeal;
        }

        public void setIntergeal(String intergeal) {
            this.intergeal = intergeal;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getGoods_code() {
            return goods_code;
        }

        public void setGoods_code(String goods_code) {
            this.goods_code = goods_code;
        }

        public String getGoods_code_id() {
            return goods_code_id;
        }

        public void setGoods_code_id(String goods_code_id) {
            this.goods_code_id = goods_code_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }
    }
}
