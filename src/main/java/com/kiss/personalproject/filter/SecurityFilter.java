package com.kiss.personalproject.filter;

import com.kiss.personalproject.bean.zz.Account;
import com.kiss.personalproject.bean.zz.AccountLogin;
import com.kiss.personalproject.dao.zz.AccountLoginDAO;
import com.kiss.personalproject.request.UserRoleRequestWrapper;
import com.kiss.personalproject.utils.AccountUtils;
import com.kiss.personalproject.utils.AppUtils;
import com.kiss.personalproject.utils.SecurityUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@WebFilter("/*")
public class SecurityFilter implements Filter {
    static Logger log = Logger.getLogger(SecurityFilter.class.getName());

    public SecurityFilter() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();

        String servletPath = request.getServletPath();
        log.info("servletPath: " + servletPath);

        // Thông tin người dùng đã được lưu trong Session
        // (Sau khi đăng nhập thành công).
        String sessionId = AppUtils.getSessionLogin(session);
        log.info("sessionId: " + sessionId);
        Account loginedUser = AppUtils.getLoginedUser(session);
        log.info("loginedUser: " + loginedUser);

        if (servletPath.equals("/login")) {
            chain.doFilter(request, response);
            return;
        }
        if(servletPath.equals("/logout")) {
            if (ObjectUtils.isNotEmpty(loginedUser)) {
                AccountLoginDAO accountLoginDAO = new AccountLoginDAO(req);
                AccountLogin accountLogin = accountLoginDAO.getDocument(AccountLogin.COL_NAME_USER_ID_IN_ID, loginedUser.getUserId());
                accountLoginDAO.saveHistory(accountLogin, AppUtils.getClientIp(request) + " account logout!!!");
            }
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest wrapRequest = request;
        if (ObjectUtils.isNotEmpty(loginedUser)) {
            // User Name
            String userName = loginedUser.getUserId();
            // Các vai trò (Role).
            List<String> roles = loginedUser.getRoles();
            // Gói request cũ bởi một Request mới với các thông tin userName và Roles.
            wrapRequest = new UserRoleRequestWrapper(userName, roles, request);
        }

        // Các trang bắt buộc phải đăng nhập.
        if (SecurityUtils.isSecurityPage(request)) {
            // Nếu người dùng chưa đăng nhập,
            // Redirect (chuyển hướng) tới trang đăng nhập.
            AccountLogin accountLogin = null;
            AccountLoginDAO accountLoginDAO = null;
            if(ObjectUtils.isNotEmpty(loginedUser)) {
                accountLoginDAO = new AccountLoginDAO(req);
                accountLogin = accountLoginDAO.getDocument(AccountLogin.COL_NAME_USER_ID_IN_ID, loginedUser.getUserId());
                log.info("accountLogin: " + accountLogin);
            }

            if (ObjectUtils.isEmpty(accountLogin)
                    || StringUtils.isBlank(sessionId)
                    || !accountLogin.getSession_id().equals(sessionId)
                    || accountLogin.getSession_time().compareTo(new Date()) < 0) {
                String requestUri = request.getRequestURI();
                // Lưu trữ trang hiện tại để redirect đến sau khi đăng nhập thành công.
                int redirectId = AppUtils.storeRedirectAfterLoginUrl(request.getSession(), requestUri);
                response.sendRedirect(wrapRequest.getContextPath() + "/login?redirectId=" + redirectId);
                return;
            }

            if(accountLogin.getSession_time().compareTo(SecurityUtils.getNextTime(new Date(), SecurityUtils.TIME_LIMIT_MIN)) <= 0) {
                accountLogin.setUpdate_id(SecurityFilter.class.getSimpleName());
                AccountUtils.updateNewLogin(request, accountLogin, null);
                accountLoginDAO.saveHistory(accountLogin, AppUtils.getClientIp(request) + " update session id, servlet path=" + servletPath);
            }

            // Kiểm tra người dùng có vai trò hợp lệ hay không?
            boolean hasPermission = SecurityUtils.hasPermission(wrapRequest);
            if (!hasPermission) {
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/views/pages-error-404.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }

        chain.doFilter(wrapRequest, response);
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

}
