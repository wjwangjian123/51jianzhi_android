package com.part.jianzhiyi.constants;

/**
 * @author:shixinxin
 * @content：内容
 * @date:、'
 * @vision:V1.0.1
 * @modified time:2019/9/23
 * @modified by:shixinxin
 **/
public class Constants {

//    public static final String URI = "http://api.sw193.com/";
    public static final String URI = "http://51testapi.sw193.com/";

    public static final String IP_URI = "http://ip-api.com/json/";
    public static final String HTML_URL = URI + "api/index/userPolicy";
    public static final String HTML_USER_URL = URI + "api/index/agreements?appid=";
    public static final String HTML_PRIVACY_URL = URI + "api/index/privacys?appid=";
    public static final String DB_NAME = "51jianzhi-db";
    public static final String APPID = "81";
    //友盟的渠道名称
    public static final String UMENG_NAME = "test";
    //隐私协议是否展示公司信息 1：展示    0：不展示
    public static final String STATUS = "1";
    public static final String OS = "1";
    public static final String OURVERSION = "2.0.2";
    //广告
    public static final String TTAD_APPID = "5117468";
    //美洽
    public static final String MEIQIA_KEY = "a3cc71037e800a8b68f2d70c58592520";

    public static int PAGE_INDEX = 1;
    public static int INFO_RESULT_CODE = 1220;//用于 修改信息返回的code
    public static String _INFO_EDIT_CONTENT = "infoEditContent";//用于表示 修改信息的二级页面 回传回来的 内容


    //    position:1是搜索，2轮播图跳入，3是从高薪招聘，4是从极速上岗，5是为你推介，6是精选,7是猜你喜欢（详情里的）8是小编推荐，9是每周排行，10是简单易做
    public static final String POSITION_SERACH = "1";
    public static final String POSITION_BANNER = "2";
    public static final String POSITION_HIGH_PAY = "3";//高薪招聘
    public static final String POSITION_SPEED_WORK = "4";//极速上岗
    public static final String POSITION_CHOICE = "6";//精选
    public static final String POSITION_HOME_RECOMMEND = "5";//首页为你推荐
    public static final String POSITION_HOME_SIMPLE = "10";  //首页简单易做
    public static final String POSITION_CHAT = "11";//聊天
    public static final String POSITION_MESSAGE_RECEIVER = "12";//推送 通知栏进入
    public static final String POSITION_POPUP_PUSH = "13";//推送 辅助通道进入
    public static final String POSITION_HOME_TODAY = "14";//首页今日必做
    public static final String POSITION_HOME_LABLE = "15";//首页兼职推荐标签
    public static final String POSITION_HOME_ACTION = "16";//首页兼职推荐标签

    public static final String POSITION_RANK = "9";//排行列表
    public static final String POSITION_CHOICE_RECOMMEND = "8";//小编推荐

    public static final String TYPE_HOME_RECOMMEND = "1";//为你推荐
    public static final String TYPE_HIGH_PAY = "2";//高薪招聘
    public static final String TYPE_CHOICE_SPECIAL = "4";//精选
    public static final String TYPE_SPEED_WORK = "3";//极速上岗
    public static final String TYPE_HOME_SIMPLE = "5";//简单易做
    public static final String TYPE_CHOICE = "6";//小编
    public static final String TYPE_RANK = "7";//排行列表
    public static final String TYPE_LABLE = "8";//首页分类
}
