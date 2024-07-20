package com.kiss.personalproject.servlet;

import com.kiss.personalproject.bean.zz.*;
import com.kiss.personalproject.config.SecurityConfig;
import com.kiss.personalproject.dao.zz.AccountLoginDAO;
import com.kiss.personalproject.dao.zz.AccountSaltDAO;
import com.kiss.personalproject.dao.zz.FunctionDAO;
import com.kiss.personalproject.dao.zz.FunctionRolesDAO;
import com.kiss.personalproject.utils.AccountUtils;
import com.kiss.personalproject.utils.AppUtils;
import com.kiss.personalproject.utils.SecurityUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static Logger log = Logger.getLogger(LoginServlet.class.getName());

    public LoginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/views/admin/pages-login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = ObjectUtils.toString(request.getParameter("userName"), "");
        String password = ObjectUtils.toString(request.getParameter("password"), "");

        AccountLoginDAO accountLoginDAO = new AccountLoginDAO(request);
        AccountLogin accountLogin = accountLoginDAO.getDocument(AccountLogin.COL_NAME_USER_ID_IN_ID, userName);
        log.info("AccountLogin: " + accountLogin);

        AccountSaltDAO accountSaltDAO = new AccountSaltDAO(request);
        AccountSalt accountSalt = accountSaltDAO.getDocument(AccountSalt.COL_NAME_USER_ID_IN_ID, userName);
        log.info("AccountSalt: " + accountSalt);

        String hashPassword = SecurityUtils.hashPassword(accountSalt.getSalt_id(), password);
        log.info("hash password: " + hashPassword);

        if (accountLogin == null ||  !hashPassword.equals(accountLogin.getPassword())) {
            String errorMessage = "Invalid userName or Password";
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher  = this.getServletContext().getRequestDispatcher("/views/admin/pages-login.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // new update salt and session id
        accountSalt.setUpdate_id(LoginServlet.class.getSimpleName());
        accountLogin.setPassword(password);
        accountLogin.setUpdate_id(LoginServlet.class.getSimpleName());
        AccountUtils.updateNewLogin(request, accountLogin, accountSalt);
        accountLoginDAO.saveHistory(accountLogin, AppUtils.getClientIp(request) + " account login!!!");

        // get all function has permission
        FunctionRolesDAO functionRolesDAO = new FunctionRolesDAO(request);
        FunctionDAO functionDAO = new FunctionDAO(request);
        HttpSession session = request.getSession();
        Account account = AppUtils.getLoginedUser(session);
        for(String role : account.getRoles()) {
            List<FunctionRoles> functionRoles = functionRolesDAO.getDocuments(FunctionRoles.COL_NAME_ROLE_ID_IN_ID, role);
            for(FunctionRoles functionRole : functionRoles) {
                Function function = functionDAO.getDocument(Function.COL_NAME_FUNCTION_ID_IN_ID, functionRole.getFunctionId());
                log.info("=====================");
                log.info("role id: " + functionRole.getRoleId());
                log.info("function id: " + functionRole.getFunctionId());
                log.info("function: " + function);
                log.info("======================");
                if(ObjectUtils.isNotEmpty(function)) {
                    SecurityConfig.setStoreRoleForUrlPattern(functionRole.getRoleId(), function.getFunctionUrl());
                }
            }
        }

        //
        int redirectId = -1;
        try {
            redirectId = Integer.parseInt(request.getParameter("redirectId"));
        } catch (Exception e) {
        }
        String requestUri = AppUtils.getRedirectAfterLoginUrl(request.getSession(), redirectId);
        if (requestUri != null) {
            response.sendRedirect(requestUri);
        } else {
            // Mặc định sau khi đăng nhập thành công
            // chuyển hướng về trang /userInfo
            response.sendRedirect(request.getContextPath() + "/home");
        }

    }

}