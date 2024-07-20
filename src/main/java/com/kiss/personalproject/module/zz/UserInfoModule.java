package com.kiss.personalproject.module.zz;

import com.kiss.personalproject.bean.zz.AccountInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.logging.Logger;

public class UserInfoModule {
    static Logger log = Logger.getLogger(UserInfoModule.class.getName());

    public AccountInfo setParamToAccountInfo(HttpServletRequest request) {
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setUserId(request.getParameter("userId"));
        accountInfo.setName(request.getParameter("name"));

        String birthday = request.getParameter("birthday");
        if (StringUtils.isNotBlank(birthday)) {
            accountInfo.setBirthDay(new Date(birthday));
        }

        String gender = request.getParameter("gender");
        if (StringUtils.isNotBlank(gender)) {
            accountInfo.setGender(Integer.parseInt(gender));
        }

        accountInfo.setNation(request.getParameter("nation"));
        accountInfo.setEmail(request.getParameter("email"));
        accountInfo.setPhone(request.getParameter("phone"));
        accountInfo.setAddress(request.getParameter("address"));
        accountInfo.setUpdateId(request.getParameter("updateId"));

        String updateTime = request.getParameter("updateTime");
        if (StringUtils.isNotBlank(updateTime)) {
            accountInfo.setUpdateTime(new Date(updateTime));
        }

        return accountInfo;
    }
}
