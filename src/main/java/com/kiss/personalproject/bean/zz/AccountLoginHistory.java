package com.kiss.personalproject.bean.zz;

import java.io.Serializable;
import java.util.Date;

public class AccountLoginHistory implements Serializable {
    public static final String GENDER_MALE = "M";
    public static final String GENDER_FEMALE = "F";
    public static final String COL_NAME_ID = "_id";
    public static final String COL_NAME_USER_ID = "user_id";
    public static final String COL_NAME_LOG_TIME = "log_time";
    public static final String COL_NAME_USER_ID_IN_ID = "_id.user_id";
    public static final String COL_NAME_LOG_TIME_IN_ID = "_id.log_time";
    public static final String COL_NAME_PASSWORD = "password";
    public static final String COL_NAME_SESSION_ID = "session_id";
    public static final String COL_NAME_SESSION_TIME = "session_time";
    public static final String COL_NAME_UPDATE_TIME = "update_time";
    public static final String COL_NAME_UPDATE_ID = "update_id";
    public static final String COL_NAME_LOG_MESSAGES = "log_messages";

    private Object _id;
    private String user_id;
    private String password;
    private String session_id;
    private Date session_time;
    private String update_id;
    private Date update_time;
    private String log_messages;
    private Date log_time;

    public AccountLoginHistory() {

    }

    public AccountLoginHistory(String user_id, String password) {
        this.user_id = user_id;
        this.password = password;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public Date getSession_time() {
        return session_time;
    }

    public void setSession_time(Date session_time) {
        this.session_time = session_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(String update_id) {
        this.update_id = update_id;
    }

    public String getLog_messages() {
        return log_messages;
    }

    public void setLog_messages(String log_messages) {
        this.log_messages = log_messages;
    }

    public Date getLog_time() {
        return log_time;
    }

    public void setLog_time(Date log_time) {
        this.log_time = log_time;
    }

    @Override
    public String toString() {
        return "UserAccount "
                + "[_id=" + _id
                + ", userID=" + user_id
                + ", password=" + password
                + ", sessionID=" + session_id
                + ", sessionTime=" + session_time
                + ", updateTime=" + update_time
                + ", updateID=" + update_id + "]";
    }
}

