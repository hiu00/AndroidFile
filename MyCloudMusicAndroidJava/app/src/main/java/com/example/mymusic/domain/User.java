package com.example.mymusic.domain;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 用户模型
 */
public class User extends BaseModel {
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户的密码，登录，注册向服务端传递
     */
    private String password;

    /**
     * 验证码
     * 找回密码时才会用到
     */
    private String code;

    /**
     * 描述
     */
    private String description;

    /**
     * 头像
     */
    private String Avatar;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    //辅助方法
    public String getDescriptionFormat(){
        if (TextUtils.isEmpty(description)){
            return "这个人很懒，没有填写个人介绍！";
        }
        return description;
    }
    //--end辅助方法
}
