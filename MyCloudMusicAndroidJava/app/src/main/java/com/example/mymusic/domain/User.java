package com.example.mymusic.domain;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

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

    /**
     * 省
     */
    private String province;

    /**
     * 省编码
     *
     * SerializedName是GSON框架的功能
     * 如果使用其他JSON框架可能不支持
     *
     * 作用是指定序列化和反序列化时字段
     * 也就说说在JSON中该字段为area_code
     * 当然也可以不使用这个功能
     * 字段就定义为area_code
     * 只是在Java中推荐使用驼峰命名法
     *
     */
    @SerializedName("province_code")
    private String provinceCode;

    /**
     * 市
     */
    private String city;

    /**
     * 市编码
     */
    @SerializedName("city_code")
    private String cityCode;

    /**
     * 区
     */
    private String area;

    /**
     * 区编码
     */
    @SerializedName("area_code")
    private String areaCode;

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
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
