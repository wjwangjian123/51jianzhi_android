package com.part.jianzhiyi.model.entity;

import java.util.List;

/**
 * @author:shixinxin
 * @Date :2020-03-27
 **/
public class ChoiceEntity {

    /**
     * weekly : [{"id":"594","avatar":"","title":"做任务赚佣金秒到账","time":"","city":"","price":"1元/单","sex":"不限","company":"杭州麦子汇网络技术有限公司","tag":"","content":"&lt;p&gt;&lt;span style=&quot;font-size: 14px;&quot;&gt;&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;span style=&quot;font-size: 14px;&quot;&gt;下载app试玩，玩游戏赚钱，微信小程序等各种玩法&lt;br/&gt;发射卫星也有收益，三级卫星每月可领取30元&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;span style=&quot;font-size: 14px;&quot;&gt;M币来源多多，可兑换超多超值好物&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;span style=&quot;font-size: 14px;&quot;&gt;闲暇时刻轻松赚取零花钱，收益高、提现快，赶快邀请好友一起赚钱，赢取白富美，走上人生巅峰吧！&lt;/span&gt;&lt;br/&gt;&lt;/p&gt;&lt;p&gt;&amp;nbsp;&lt;/p&gt;","method":"日结","type":"1","contact":"http://1t.click/b6Fm","contact_type":"4","isfavourite":null,"order_number":"242","desc":"麦子赚","price1":"1","price2":"元/单"}]
     * xiaobian : [{"id":"604","avatar":"","title":"高薪日结！空闲时间兼职","time":"不限","city":"","price":"100元/日","sex":"男女不限","company":"增欲华兴（北京）科技有限公司","tag":"","content":"&lt;p style=&quot;margin-top: 0px; margin-bottom: 0px; caret-color: rgb(0, 0, 0); white-space: normal; text-size-adjust: auto; font-stretch: normal; font-size: 13px; line-height: normal; font-family: &amp;quot;Helvetica Neue&amp;quot;;&quot;&gt;&lt;span style=&quot;font-family: 宋体, SimSun; font-size: 16px;&quot;&gt;&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;;text-align: justify;font-family: Calibri;font-size: 14px;white-space: normal&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;报名成功后，请主动添加客服，了解工作内容，添加客服才算成功！&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;【工作待遇】&lt;/span&gt;&lt;br/&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;每天300--500不等。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;有劳有得，多做多得！正规，安全，放心！&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;【工作内容】&lt;/span&gt;&lt;br/&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;在家手机可做，轻松赚钱，当时操作，及时收款；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;无经验者可学，操作简单易学，专业导师带队，让你快速学会并开始获得收益；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;&amp;nbsp;&lt;/span&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;想利用空闲时间兼职赚钱让生活变的更美好，就选择我们正规团队，带你轻松赚钱！&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;长期招聘，坚持长期做，月薪过万不是梦。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;&amp;nbsp;&lt;/span&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;【适合人群】&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;本职位适合上班族，失业人员，宝妈，也欢迎有上进心、想赚钱的小伙伴&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;注：不招学生，学生勿扰，招聘22岁以上人员&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;text-align: justify; font-family: Calibri; font-size: 14px; white-space: normal;&quot;&gt;&lt;br/&gt;&lt;/p&gt;","method":"日结","type":"6","contact":"18048325313\r,13069814194\r,18147384002\r,18680379448\r,17747175401\r,18148265202\r,13069658243\r,13067534030\r,18131242613\r,18947968025","contact_type":"1","isfavourite":null,"order_number":"3","desc":"ass","price1":"100","price2":"元/日"}]
     * choice : [{"id":"571","avatar":"","title":"在家躺赚！高薪日结小任务","time":"不限","city":"","price":"280元/天","sex":"不限","company":"深圳世纪宏源科技有限公司","tag":"","content":"&lt;p style=&quot;;text-align: justify;font-family: Calibri;font-size: 14px;white-space: normal&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;span style=&quot;color: rgb(255, 0, 0);&quot;&gt;本招聘不接受未成年，低于20岁请误报名，否则不通过&lt;/span&gt;&lt;/p&gt;&lt;p&gt;成功添加客服为好友才算报名成功！&lt;br/&gt;&lt;/p&gt;&lt;p&gt;项目须知:&lt;/p&gt;&lt;p&gt;1：只需要一部手机，听一首歌的时间，即可操作。学习到团队的成功秘诀，即刻让你轻松赚到钱&lt;/p&gt;&lt;p&gt;2：有专业的团队带您学会操作流程，30分钟分钟轻松获得收益！&lt;/p&gt;&lt;p&gt;3:专业的团队研究精准的大数据所运算得出的可靠可行的规律，高明的导师一对一带你赚钱，买车买房！&lt;/p&gt;&lt;p&gt;4:跟着团队走，捷足先登，稳步前行!&lt;/p&gt;&lt;p&gt;特别通知： 不限工作地址，工作时间，只要你有业余时间。&lt;/p&gt;&lt;p style=&quot;margin-top: 0px; margin-bottom: 0px; font-stretch: normal; font-size: 13px; line-height: normal; font-family: &amp;quot;Helvetica Neue&amp;quot;;&quot;&gt;&lt;br/&gt;&lt;/p&gt;&lt;p style=&quot;margin-top: 0px; margin-bottom: 0px; font-stretch: normal; font-size: 13px; line-height: normal; font-family: &amp;quot;Helvetica Neue&amp;quot;;&quot;&gt;&lt;br/&gt;&lt;/p&gt;","method":"日结","type":"4","contact":"84815356474,84816012039,84398097091,84398095306,84795990035,84398216894","contact_type":"1","isfavourite":null,"order_number":"49","desc":"lbx","price1":"280","price2":"元/天"}]
     * advertising : {"id":"10","banner":"2","job_id":"696","image":"http://jxw-img.oss-cn-beijing.aliyuncs.com/LJZ/20200326/1585220484.png","type":"1","urls":"","app_id":"8","update_time":"2020-03-26 19:01:58","create_time":"0000-00-00 00:00:00","types":"2","advert_order":"2"}
     */

    private AdvertisingBean advertising;
    private List<WeeklyBean> weekly;
    private List<XiaobianBean> xiaobian;
    private List<ChoiceBean> choice;

    public AdvertisingBean getAdvertising() {
        return advertising;
    }

    public void setAdvertising(AdvertisingBean advertising) {
        this.advertising = advertising;
    }

    public List<WeeklyBean> getWeekly() {
        return weekly;
    }

    public void setWeekly(List<WeeklyBean> weekly) {
        this.weekly = weekly;
    }

    public List<XiaobianBean> getXiaobian() {
        return xiaobian;
    }

    public void setXiaobian(List<XiaobianBean> xiaobian) {
        this.xiaobian = xiaobian;
    }

    public List<ChoiceBean> getChoice() {
        return choice;
    }

    public void setChoice(List<ChoiceBean> choice) {
        this.choice = choice;
    }

    public static class AdvertisingBean {
        /**
         * id : 10
         * banner : 2
         * job_id : 696
         * image : http://jxw-img.oss-cn-beijing.aliyuncs.com/LJZ/20200326/1585220484.png
         * type : 1
         * urls :
         * app_id : 8
         * update_time : 2020-03-26 19:01:58
         * create_time : 0000-00-00 00:00:00
         * types : 2
         * advert_order : 2
         */

        private String id;
        private String banner;
        private String job_id;
        private String image;
        private String type;
        private String urls;
        private String app_id;
        private String update_time;
        private String create_time;
        private String types;
        private String advert_order;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public String getJob_id() {
            return job_id;
        }

        public void setJob_id(String job_id) {
            this.job_id = job_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrls() {
            return urls;
        }

        public void setUrls(String urls) {
            this.urls = urls;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }

        public String getAdvert_order() {
            return advert_order;
        }

        public void setAdvert_order(String advert_order) {
            this.advert_order = advert_order;
        }
    }

    public static class WeeklyBean {
        /**
         * id : 594
         * avatar :
         * title : 做任务赚佣金秒到账
         * time :
         * city :
         * price : 1元/单
         * sex : 不限
         * company : 杭州麦子汇网络技术有限公司
         * tag :
         * content : &lt;p&gt;&lt;span style=&quot;font-size: 14px;&quot;&gt;&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;span style=&quot;font-size: 14px;&quot;&gt;下载app试玩，玩游戏赚钱，微信小程序等各种玩法&lt;br/&gt;发射卫星也有收益，三级卫星每月可领取30元&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;span style=&quot;font-size: 14px;&quot;&gt;M币来源多多，可兑换超多超值好物&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;span style=&quot;font-size: 14px;&quot;&gt;闲暇时刻轻松赚取零花钱，收益高、提现快，赶快邀请好友一起赚钱，赢取白富美，走上人生巅峰吧！&lt;/span&gt;&lt;br/&gt;&lt;/p&gt;&lt;p&gt;&amp;nbsp;&lt;/p&gt;
         * method : 日结
         * type : 1
         * contact : http://1t.click/b6Fm
         * contact_type : 4
         * isfavourite : null
         * order_number : 242
         * desc : 麦子赚
         * price1 : 1
         * price2 : 元/单
         */

        private String id;
        private String avatar;
        private String title;
        private String time;
        private String city;
        private String price;
        private String sex;
        private String company;
        private String tag;
        private String content;
        private String method;
        private String type;
        private String contact;
        private String contact_type;
        private Object isfavourite;
        private String order_number;
        private String desc;
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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getContact_type() {
            return contact_type;
        }

        public void setContact_type(String contact_type) {
            this.contact_type = contact_type;
        }

        public Object getIsfavourite() {
            return isfavourite;
        }

        public void setIsfavourite(Object isfavourite) {
            this.isfavourite = isfavourite;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
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

    public static class XiaobianBean {
        /**
         * id : 604
         * avatar :
         * title : 高薪日结！空闲时间兼职
         * time : 不限
         * city :
         * price : 100元/日
         * sex : 男女不限
         * company : 增欲华兴（北京）科技有限公司
         * tag :
         * content : &lt;p style=&quot;margin-top: 0px; margin-bottom: 0px; caret-color: rgb(0, 0, 0); white-space: normal; text-size-adjust: auto; font-stretch: normal; font-size: 13px; line-height: normal; font-family: &amp;quot;Helvetica Neue&amp;quot;;&quot;&gt;&lt;span style=&quot;font-family: 宋体, SimSun; font-size: 16px;&quot;&gt;&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;;text-align: justify;font-family: Calibri;font-size: 14px;white-space: normal&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;报名成功后，请主动添加客服，了解工作内容，添加客服才算成功！&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;【工作待遇】&lt;/span&gt;&lt;br/&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;每天300--500不等。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;有劳有得，多做多得！正规，安全，放心！&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;【工作内容】&lt;/span&gt;&lt;br/&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;在家手机可做，轻松赚钱，当时操作，及时收款；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;无经验者可学，操作简单易学，专业导师带队，让你快速学会并开始获得收益；&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;&amp;nbsp;&lt;/span&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;想利用空闲时间兼职赚钱让生活变的更美好，就选择我们正规团队，带你轻松赚钱！&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;长期招聘，坚持长期做，月薪过万不是梦。&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;&amp;nbsp;&lt;/span&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;【适合人群】&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;本职位适合上班族，失业人员，宝妈，也欢迎有上进心、想赚钱的小伙伴&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;white-space: normal; text-align: justify; font-family: Calibri; font-size: 14px;&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;注：不招学生，学生勿扰，招聘22岁以上人员&lt;/span&gt;&lt;/p&gt;&lt;p style=&quot;text-align: justify; font-family: Calibri; font-size: 14px; white-space: normal;&quot;&gt;&lt;br/&gt;&lt;/p&gt;
         * method : 日结
         * type : 6
         * contact : 18048325313,13069814194,18147384002,18680379448,17747175401,18148265202,13069658243,13067534030,18131242613,18947968025
         * contact_type : 1
         * isfavourite : null
         * order_number : 3
         * desc : ass
         * price1 : 100
         * price2 : 元/日
         */

        private String id;
        private String avatar;
        private String title;
        private String time;
        private String city;
        private String price;
        private String sex;
        private String company;
        private String tag;
        private String content;
        private String method;
        private String type;
        private String contact;
        private String contact_type;
        private Object isfavourite;
        private String order_number;
        private String desc;
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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getContact_type() {
            return contact_type;
        }

        public void setContact_type(String contact_type) {
            this.contact_type = contact_type;
        }

        public Object getIsfavourite() {
            return isfavourite;
        }

        public void setIsfavourite(Object isfavourite) {
            this.isfavourite = isfavourite;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
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

    public static class ChoiceBean {
        /**
         * id : 571
         * avatar :
         * title : 在家躺赚！高薪日结小任务
         * time : 不限
         * city :
         * price : 280元/天
         * sex : 不限
         * company : 深圳世纪宏源科技有限公司
         * tag :
         * content : &lt;p style=&quot;;text-align: justify;font-family: Calibri;font-size: 14px;white-space: normal&quot;&gt;&lt;span style=&quot;font-family: 宋体; font-size: 16px;&quot;&gt;&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;span style=&quot;color: rgb(255, 0, 0);&quot;&gt;本招聘不接受未成年，低于20岁请误报名，否则不通过&lt;/span&gt;&lt;/p&gt;&lt;p&gt;成功添加客服为好友才算报名成功！&lt;br/&gt;&lt;/p&gt;&lt;p&gt;项目须知:&lt;/p&gt;&lt;p&gt;1：只需要一部手机，听一首歌的时间，即可操作。学习到团队的成功秘诀，即刻让你轻松赚到钱&lt;/p&gt;&lt;p&gt;2：有专业的团队带您学会操作流程，30分钟分钟轻松获得收益！&lt;/p&gt;&lt;p&gt;3:专业的团队研究精准的大数据所运算得出的可靠可行的规律，高明的导师一对一带你赚钱，买车买房！&lt;/p&gt;&lt;p&gt;4:跟着团队走，捷足先登，稳步前行!&lt;/p&gt;&lt;p&gt;特别通知： 不限工作地址，工作时间，只要你有业余时间。&lt;/p&gt;&lt;p style=&quot;margin-top: 0px; margin-bottom: 0px; font-stretch: normal; font-size: 13px; line-height: normal; font-family: &amp;quot;Helvetica Neue&amp;quot;;&quot;&gt;&lt;br/&gt;&lt;/p&gt;&lt;p style=&quot;margin-top: 0px; margin-bottom: 0px; font-stretch: normal; font-size: 13px; line-height: normal; font-family: &amp;quot;Helvetica Neue&amp;quot;;&quot;&gt;&lt;br/&gt;&lt;/p&gt;
         * method : 日结
         * type : 4
         * contact : 84815356474,84816012039,84398097091,84398095306,84795990035,84398216894
         * contact_type : 1
         * isfavourite : null
         * order_number : 49
         * desc : lbx
         * price1 : 280
         * price2 : 元/天
         */

        private String id;
        private String avatar;
        private String title;
        private String time;
        private String city;
        private String price;
        private String sex;
        private String company;
        private String tag;
        private String content;
        private String method;
        private String type;
        private String contact;
        private String contact_type;
        private Object isfavourite;
        private String order_number;
        private String desc;
        private String price1;
        private String price2;
        private int uiType = 0;
        private String place;

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public ChoiceBean() {
        }

        public ChoiceBean(int uiType) {
            this.uiType = uiType;
        }

        public int getUiType() {
            return uiType;
        }

        public void setUiType(int uiType) {
            this.uiType = uiType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getContact_type() {
            return contact_type;
        }

        public void setContact_type(String contact_type) {
            this.contact_type = contact_type;
        }

        public Object getIsfavourite() {
            return isfavourite;
        }

        public void setIsfavourite(Object isfavourite) {
            this.isfavourite = isfavourite;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
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
