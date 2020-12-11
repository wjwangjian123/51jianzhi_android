package com.part.jianzhiyi.model.request;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/10/24
 * @modified by:shixinxin
 **/
public class FeedbackRequest {

    private String userid;
    private String content;
    private String contact;
    private String os;

    public FeedbackRequest(String userid, String content, String contact, String os) {
        this.userid = userid;
        this.content = content;
        this.contact = contact;
        this.os = os;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
}
