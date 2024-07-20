package com.kiss.personalproject.bean.zz;

import java.util.Date;

public class FunctionRoles {
    public static final String COL_NAME_ID = "_id";
    public static final String COL_NAME_ROLE_ID_IN_ID = "_id.role_id";
    public static final String COL_NAME_FUNCTION_ID = "function_id";
    public static final String COL_NAME_ROLE_ID = "role_id";
    public static final String COL_NAME_UPDATE_ID = "update_id";
    public static final String COL_NAME_UPDATE_TIME = "update_time";


    private Object _id;
    private String functionId;
    private String roleId;
    private String updateId;
    private Date updateTime;

    public Object get_id() {
        return _id;
    }

    public void set_id(Object _id) {
        this._id = _id;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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
}
