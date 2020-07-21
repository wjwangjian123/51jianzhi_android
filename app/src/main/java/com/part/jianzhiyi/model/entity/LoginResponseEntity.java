package com.part.jianzhiyi.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author:
 * @content：内容
 * @vision:V1.0.1
 **/
@Entity
public class LoginResponseEntity {

    /**
     * id : 5312
     * username :
     * signature :
     * create_time :
     * update_time : 2019-09-12 18:06:28
     * phone : 15712883024
     * email :
     * status : 0000
     * name : 鲤鱼
     * sex : 女
     * age : 37
     * school_year : 1970
     * school_name : 借记卡
     * experience : 今生今世时间和计算机技术看看看看
     * introduce : 今生今世计算机睡觉就睡觉
     * appid : 3
     */
    @Id(autoincrement = true)
    public Long id;
    //    private String id;
    private String username;
    private String signature;
    private String create_time;
    private String update_time;
    private String phone;
    private String email;
    private String status;
    private String name;
    private String sex;
    private String age;
    private String school_year;
    private String school_name;
    private String experience;
    private String introduce;
    private String appid;
    private String resume_complete;
    private boolean showResume;
    private String profession;
    private String job_status;
    private String job_type;
    private int profession_type;
    private int job_status_type;
    private String job_position_type;
    private String resume_active;
    @Generated(hash = 1972212668)
    public LoginResponseEntity(Long id, String username, String signature,
            String create_time, String update_time, String phone, String email,
            String status, String name, String sex, String age, String school_year,
            String school_name, String experience, String introduce, String appid,
            String resume_complete, boolean showResume, String profession,
            String job_status, String job_type, int profession_type,
            int job_status_type, String job_position_type, String resume_active) {
        this.id = id;
        this.username = username;
        this.signature = signature;
        this.create_time = create_time;
        this.update_time = update_time;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.school_year = school_year;
        this.school_name = school_name;
        this.experience = experience;
        this.introduce = introduce;
        this.appid = appid;
        this.resume_complete = resume_complete;
        this.showResume = showResume;
        this.profession = profession;
        this.job_status = job_status;
        this.job_type = job_type;
        this.profession_type = profession_type;
        this.job_status_type = job_status_type;
        this.job_position_type = job_position_type;
        this.resume_active = resume_active;
    }
    @Generated(hash = 1746094175)
    public LoginResponseEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getSignature() {
        return this.signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }
    public String getCreate_time() {
        return this.create_time;
    }
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
    public String getUpdate_time() {
        return this.update_time;
    }
    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getSchool_year() {
        return this.school_year;
    }
    public void setSchool_year(String school_year) {
        this.school_year = school_year;
    }
    public String getSchool_name() {
        return this.school_name;
    }
    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }
    public String getExperience() {
        return this.experience;
    }
    public void setExperience(String experience) {
        this.experience = experience;
    }
    public String getIntroduce() {
        return this.introduce;
    }
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
    public String getAppid() {
        return this.appid;
    }
    public void setAppid(String appid) {
        this.appid = appid;
    }
    public String getResume_complete() {
        return this.resume_complete;
    }
    public void setResume_complete(String resume_complete) {
        this.resume_complete = resume_complete;
    }
    public boolean getShowResume() {
        return this.showResume;
    }
    public void setShowResume(boolean showResume) {
        this.showResume = showResume;
    }
    public String getProfession() {
        return this.profession;
    }
    public void setProfession(String profession) {
        this.profession = profession;
    }
    public String getJob_status() {
        return this.job_status;
    }
    public void setJob_status(String job_status) {
        this.job_status = job_status;
    }
    public String getJob_type() {
        return this.job_type;
    }
    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }
    public int getProfession_type() {
        return this.profession_type;
    }
    public void setProfession_type(int profession_type) {
        this.profession_type = profession_type;
    }
    public int getJob_status_type() {
        return this.job_status_type;
    }
    public void setJob_status_type(int job_status_type) {
        this.job_status_type = job_status_type;
    }
    public String getJob_position_type() {
        return this.job_position_type;
    }
    public void setJob_position_type(String job_position_type) {
        this.job_position_type = job_position_type;
    }
    public String getResume_active() {
        return this.resume_active;
    }
    public void setResume_active(String resume_active) {
        this.resume_active = resume_active;
    }

}
