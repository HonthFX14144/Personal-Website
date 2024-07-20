package com.kiss.personalproject.bean.zz;

import java.util.Date;

public class AccountInfo {
    public static final String COL_NAME_ID = "_id";
    public static final String COL_NAME_USER_ID_IN_ID = "_id.user_id";
    public static final String COL_NAME_USER_ID = "user_id";
    public static final String COL_NAME_NAME = "name";
    public static final String COL_NAME_BIRTH_DAY = "birth_day";
    public static final String COL_NAME_GENDER = "gender";
    public static final String COL_NAME_PHONE = "phone";
    public static final String COL_NAME_EMAIL = "email";
    public static final String COL_NAME_ADDRESS = "address";
    public static final String COL_NAME_NATION = "nation";
    public static final String COL_NAME_UPDATE_ID = "update_id";
    public static final String COL_NAME_UPDATE_TIME = "update_time";

    private Object _id;
    private String userId;
    private String name;
    private Date birthDay;
    private int gender;
    private String phone;
    private String email;
    private String address;
    private String nation;
    private String updateId;
    private Date updateTime;

    public Object get_id() {
        return _id;
    }

    public void set_id(Object _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthday) {
        this.birthDay = birthday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "_id=" + _id +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", birthDay=" + birthDay +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", nation='" + nation + '\'' +
                ", updateId='" + updateId + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
