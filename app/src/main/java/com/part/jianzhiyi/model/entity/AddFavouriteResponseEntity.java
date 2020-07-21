package com.part.jianzhiyi.model.entity;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/10/13
 * @modified by:shixinxin
 **/
public class AddFavouriteResponseEntity {
    private String mes;
    private String contact;

    public AddFavouriteResponseEntity() {
    }

    public AddFavouriteResponseEntity(String msg) {
        this.mes = msg;
    }

    public String getMsg() {
        return mes;
    }

    public void setMsg(String msg) {
        this.mes = msg;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
