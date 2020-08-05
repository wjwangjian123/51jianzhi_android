package com.part.jianzhiyi.model.request;

/**
 * Created by jyx on 2020/6/10
 *
 * @author jyx
 * @describe 第二版修改简历参数
 */
public class UResumeRequest {

    private String user_id;
    private String name;
    private String sex;
    private String age;
    private String school_year;
    private String school_name;
    private String experience;
    private String introduce;
    private String os = "1";
    private int profession;
    private int job_status;
    private String job_type;
    private String myitem;
    private String expect;

    public UResumeRequest(String user_id, String name, String sex, String age, String school_year, String school_name, String experience, String introduce, int profession, int job_status, String job_type, String myitem, String expect) {
        this.user_id = user_id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.school_year = school_year;
        this.school_name = school_name;
        this.experience = experience;
        this.introduce = introduce;
        this.profession = profession;
        this.job_status = job_status;
        this.job_type = job_type;
        this.myitem = myitem;
        this.expect = expect;
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

    public int getProfession() {
        return profession;
    }

    public void setProfession(int profession) {
        this.profession = profession;
    }

    public int getJob_status() {
        return job_status;
    }

    public void setJob_status(int job_status) {
        this.job_status = job_status;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public String getMyitem() {
        return myitem;
    }

    public void setMyitem(String myitem) {
        this.myitem = myitem;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }
}
