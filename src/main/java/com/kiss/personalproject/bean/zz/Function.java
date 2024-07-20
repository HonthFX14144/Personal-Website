package com.kiss.personalproject.bean.zz;

import java.util.Date;

public class Function {
    public static final String COL_NAME_ID = "_id";
    public static final String COL_NAME_FUNCTION_ID_IN_ID = "_id.function_id";
    public static final String COL_NAME_FUNCTION_ID = "function_id";
    public static final String COL_NAME_FUNCTION_NAME = "function_name";
    public static final String COL_NAME_FUNCTION_URL = "url";
    public static final String COL_NAME_FUNCTION_DESC = "description";
    public static final String COL_NAME_UPDATE_ID = "update_id";
    public static final String COL_NAME_UPDATE_TIME = "update_time";


    private Object _id;
    private String functionId;
    private String functionName;
    private String functionUrl;
    private String functionDesc;
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

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionUrl() {
        return functionUrl;
    }

    public void setFunctionUrl(String functionUrl) {
        this.functionUrl = functionUrl;
    }

    public String getFunctionDesc() {
        return functionDesc;
    }

    public void setFunctionDesc(String functionDesc) {
        this.functionDesc = functionDesc;
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
