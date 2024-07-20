package com.kiss.personalproject.bean.zz;

import java.util.Date;

public class AccountRoles {
    public static final String COL_NAME_ID = "_id";
    public static final String COL_NAME_USER_ID_IN_ID = "_id.user_id";
    public static final String COL_NAME_ROLES_ID_IN_ID = "_id.roles_id";
    public static final String COL_NAME_ROLES_ID = "roles_id";
    public static final String COL_NAME_USER_ID = "user_id";
    public static final String COL_NAME_UPDATE_ID = "update_id";
    public static final String COL_NAME_UPDATE_DATE = "update_date";

    private Object _id;
    private String roles_id;
    private String user_id;
    private String update_id;
    private Date update_date;

    public Object get_id() {
        return _id;
    }

    public void set_id(Object _id) {
        this._id = _id;
    }

    public String getRoles_id() {
        return roles_id;
    }

    public void setRoles_id(String roles_id) {
        this.roles_id = roles_id;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

    public String getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(String update_id) {
        this.update_id = update_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "AccountRoles "
                + "[_id=" + _id
                + ", roles_id=" + roles_id
                + ", user_id=" + user_id
                + ", update_id=" + update_id
                + ", update_date=" + update_date + "]";
    }
}
