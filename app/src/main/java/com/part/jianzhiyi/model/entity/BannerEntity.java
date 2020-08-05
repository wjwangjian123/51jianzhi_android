package com.part.jianzhiyi.model.entity;

/**
 * @author:shixinxin
 * @Date :2020-03-26
 **/
public class BannerEntity {


    /**
     * id : 5
     * banner : 1
     * job_id : 635
     * image : http://jxw-img.oss-cn-beijing.aliyuncs.com/LJZ/20200326/1585220600.jpg
     * type : 1
     * urls :
     * app_id : 2
     * update_time : 2020-03-26 19:03:23
     * create_time : 0000-00-00 00:00:00
     * types : 1
     * advert_order : 1
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
    private int url_redirect;

    public int getUrl_redirect() {
        return url_redirect;
    }

    public void setUrl_redirect(int url_redirect) {
        this.url_redirect = url_redirect;
    }

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
