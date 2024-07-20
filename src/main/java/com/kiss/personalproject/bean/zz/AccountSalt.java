package com.kiss.personalproject.bean.zz;

import java.util.Date;

public class AccountSalt {
    public static final String COL_NAME_ID = "_id";
    public static final String COL_NAME_USER_ID = "user_id";
    public static final String COL_NAME_USER_ID_IN_ID = "_id.user_id";
    public static final String COL_NAME_SALT_ID = "salt_id";
    public static final String COL_NAME_UPDATE_TIME = "update_time";
    public static final String COL_NAME_UPDATE_ID = "update_id";

    private Object _id;
    private String user_id;
    private String salt_id;
    private String update_id;
    private Date update_time;

    public Object get_id() {
        return _id;
    }

    public void set_id(Object _id) {
        this._id = _id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSalt_id() {
        return salt_id;
    }

    public void setSalt_id(String salt_id) {
        this.salt_id = salt_id;
    }

    public String getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(String update_id) {
        this.update_id = update_id;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "AccountSalt{" +
                "_id=" + _id +
                ", user_id='" + user_id + '\'' +
                ", salt_id='" + salt_id + '\'' +
                ", update_id='" + update_id + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}
