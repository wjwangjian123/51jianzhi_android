package com.part.jianzhiyi.model.request;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/10/11
 * @modified by:shixinxin
 **/
public class UpdateResumeRequest {
    private String user_id;
    private String name;
    private String sex;
    private String age;
    private String school_year;
    private String school_name;
    private String experience;
    private String introduce;
    private String os="1";

    public UpdateResumeRequest(String user_id, String name, String sex, String age, String school_year, String school_name, String experience, String introduce) {
        this.user_id = user_id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.school_year = school_year;
        this.school_name = school_name;
        this.experience = experience;
        this.introduce = introduce;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSchool_year() {
        return school_year;
    }

    public void setSchool_year(String school_year) {
        this.school_year = school_year;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
}
