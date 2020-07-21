package com.part.jianzhiyi.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

/**
 * Created by jyx on 2020/6/17
 *
 * @author jyx
 * @describe
 */
@Entity
public class MessageResponseEntity {
    @Id(autoincrement = true)
    public Long id;
    private String headimg;
    private int headimg1;
    private String username;
    private String company;
    private String title;
    private String price;
    private String time;
    private String dateMonth;
    private String msg1;
    private String msg2;
    private String msg3;
    private int isRed;
    @Index(unique = true)//设置唯一性
    private String companyId;
    private int headimg2;
    private String contact;
    @Generated(hash = 1581513170)
    public MessageResponseEntity(Long id, String headimg, int headimg1,
            String username, String company, String title, String price,
            String time, String dateMonth, String msg1, String msg2, String msg3,
            int isRed, String companyId, int headimg2, String contact) {
        this.id = id;
        this.headimg = headimg;
        this.headimg1 = headimg1;
        this.username = username;
        this.company = company;
        this.title = title;
        this.price = price;
        this.time = time;
        this.dateMonth = dateMonth;
        this.msg1 = msg1;
        this.msg2 = msg2;
        this.msg3 = msg3;
        this.isRed = isRed;
        this.companyId = companyId;
        this.headimg2 = headimg2;
        this.contact = contact;
    }
    @Generated(hash = 150253341)
    public MessageResponseEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getHeadimg() {
        return this.headimg;
    }
    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }
    public int getHeadimg1() {
        return this.headimg1;
    }
    public void setHeadimg1(int headimg1) {
        this.headimg1 = headimg1;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getCompany() {
        return this.company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPrice() {
        return this.price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getDateMonth() {
        return this.dateMonth;
    }
    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }
    public String getMsg1() {
        return this.msg1;
    }
    public void setMsg1(String msg1) {
        this.msg1 = msg1;
    }
    public String getMsg2() {
        return this.msg2;
    }
    public void setMsg2(String msg2) {
        this.msg2 = msg2;
    }
    public String getMsg3() {
        return this.msg3;
    }
    public void setMsg3(String msg3) {
        this.msg3 = msg3;
    }
    public String getCompanyId() {
        return this.companyId;
    }
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    public int getHeadimg2() {
        return this.headimg2;
    }
    public void setHeadimg2(int headimg2) {
        this.headimg2 = headimg2;
    }
    public int getIsRed() {
        return this.isRed;
    }
    public void setIsRed(int isRed) {
        this.isRed = isRed;
    }
    public String getContact() {
        return this.contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }


}
