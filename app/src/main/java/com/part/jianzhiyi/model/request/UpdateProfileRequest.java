package com.part.jianzhiyi.model.request;

/**
 * @author:
 * @content：内容
 * @vision:V1.0.1
 **/
public class UpdateProfileRequest {

    private String username;
    private String user_id;
    private String signature;
    private String phone;
    private String email;
    private String os="1";


    public UpdateProfileRequest(String user_id,String username, String signature, String phone, String email) {
        this.username=username;
        this.user_id = user_id;
        this.signature = signature;
        this.phone = phone;
        this.email = email;
    }

    public UpdateProfileRequest(String username) {
        this.username = username;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
