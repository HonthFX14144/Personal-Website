package com.kiss.personalproject.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kiss.personalproject.bean.zz.*;
import com.kiss.personalproject.dao.zz.AccountInfoDAO;
import com.kiss.personalproject.dao.zz.AccountLoginDAO;
import com.kiss.personalproject.dao.zz.AccountRolesDAO;
import com.kiss.personalproject.dao.zz.AccountSaltDAO;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class AccountUtils {
    static Logger log = Logger.getLogger(AccountUtils.class.getName());

    public static void updateNewLogin(ServletRequest request, AccountLogin accountLogin, AccountSalt accountSalt) throws JsonProcessingException {
        HttpServletRequest req = (HttpServletRequest) request;
        AppUtils.deleteSessionLogin(req.getSession(), accountLogin.getSession_id());

        Date now = new Date();

        // cập nhật salt id mới
        if(ObjectUtils.isNotEmpty(accountSalt)) {
            String radomSalt = SecurityUtils.getRandomSaf();
            accountSalt.setSalt_id(radomSalt);
            accountSalt.setUpdate_time(now);
            accountSalt = new AccountSaltDAO(request).updateDocument(accountSalt, AccountSalt.COL_NAME_USER_ID_IN_ID, accountLogin.getUser_id());
            log.info("AccountSalt: " + accountSalt);
        }

        // cập nhật session id mới
        String sessionId = SecurityUtils.createNewSessionId();
        accountLogin.setSession_id(sessionId);
        accountLogin.setSession_time(SecurityUtils.getNextSessionTime());
        accountLogin.setUpdate_time(now);
        if(ObjectUtils.isNotEmpty(accountSalt)
                && StringUtils.isNotBlank(accountSalt.getSalt_id())
                && StringUtils.isNotBlank(accountLogin.getPassword())) {
            accountLogin.setPassword(SecurityUtils.hashPassword(accountSalt.getSalt_id(), accountLogin.getPassword()));
        }
        log.info("AccountLogin: " + accountLogin);
        AccountLoginDAO accountLoginDAO = new AccountLoginDAO(request);
        accountLogin = accountLoginDAO.updateDocument(accountLogin, AccountLogin.COL_NAME_USER_ID_IN_ID, accountLogin.getUser_id());

        // lấy danh sách roles của user
        List<AccountRoles> accountRoles = new AccountRolesDAO(request).getDocuments(AccountRoles.COL_NAME_USER_ID_IN_ID, accountLogin.getUser_id());
        log.info("accountRoles: " + accountRoles);
        List<String> roles = new ArrayList<>();
        accountRoles.forEach(accountRole -> roles.add(accountRole.getRoles_id()));

        // lấy thông tin user
        AccountInfo accountInfo = new AccountInfoDAO(request).getDocument(AccountInfo.COL_NAME_USER_ID_IN_ID, accountLogin.getUser_id());

        // thiết lập thông tin login
        Account account = new Account();
        account.setUserId(accountLogin.getUser_id());
        account.setName(accountInfo.getName());
        account.setAddress(accountInfo.getAddress());
        account.setEmail(accountInfo.getEmail());
        account.setPhone(accountInfo.getPhone());
        account.setGender(accountInfo.getGender());
        account.setBirthDate(accountInfo.getBirthDay());
        account.setRoles(roles);
        account.setSessionId(accountLogin.getSession_id());
        account.setSessionTime(accountLogin.getSession_time());

        // lưu vào session
        AppUtils.storeLoginedUser(req.getSession(), account, sessionId);
    }
}
