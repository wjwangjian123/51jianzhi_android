package com.part.jianzhiyi.model.entity;

public class CategoryEntity {

    /**
     * id : 1
     * image : http://jxw-img.oss-cn-beijing.aliyuncs.com/LJZ/20200401/1585707120.jpg
     * back : http://jxw-img.oss-cn-beijing.aliyuncs.com/LJZ/20200401/1585707253.jpg
     * create_time : 2020-04-01 10:21:09
     * update_time : 2020-04-01 10:21:09
     * status : 1
     * name : 高额任务
     * introduction : 高额任务简介
     */

    private String id;
    private String image;
    private String back;
    private String create_time;
    private String update_time;
    private String status;
    private String name;
    private String introduction;
    private String type;
    private String position;
    private String rdtype;

    public String getRdtype() {
        return rdtype;
    }

    public void setRdtype(String rdtype) {
        this.rdtype = rdtype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
