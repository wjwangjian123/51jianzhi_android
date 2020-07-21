package com.part.jianzhiyi.model.entity;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/10/13
 * @modified by:shixinxin
 **/
public class MsgResponseEntity {

    /**
     * id : 52
     * user_id : 0
     * create_time : 1541752187
     * msg : 测试效果反馈
     * app_id : null
     */

    private String id;
    private String user_id;
    private String create_time;
    private String msg;
    private Object app_id;
    private String details;

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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getApp_id() {
        return app_id;
    }

    public void setApp_id(Object app_id) {
        this.app_id = app_id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
