package com.kiss.personalproject.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.kiss.personalproject.bean.zz.Account;
import com.kiss.personalproject.bean.zz.AccountLogin;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AppUtils {
    static Logger log = Logger.getLogger(AppUtils.class.getName());

    private static int REDIRECT_ID = 0;

    private static final Map<Integer, String> id_uri_map = new HashMap<Integer, String>();
    private static final Map<String, Integer> uri_id_map = new HashMap<String, Integer>();
    private static final String ATT_USER_LOGIN = "ATTRIBUTE_FOR_USER_LOGIN";
    private static final String ATT_SESSION_ID = "ATTRIBUTE_FOR_SESSION_ID";
    private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";

    public static final String ATT_MONGO_DB_CONNECTION = "ATTRIBUTE_FOR_MONGO_DB_CONNECTION";

    // Lưu trữ Connection vào attribute của request.
    // Thông tin lưu trữ này chỉ tồn tại trong thời gian yêu cầu (request)
    // cho tới khi dữ liệu được trả về trình duyệt người dùng.
    public static void storeDBConnection(ServletRequest request, Object database, String NameDB) {
        request.setAttribute(NameDB, database);
    }

    // Lấy đối tượng Connection đã được lưu trữ trong attribute của request.
    public static Object getStoredDBConnection(ServletRequest request, String NameDB) {
        Object conn = request.getAttribute(NameDB);
        return conn;
    }
    // Lưu trữ thông tin người dùng vào Session.
    public static void storeLoginedUser(HttpSession session, Account loginedUser, String sessionId) {
        // Trên JSP có thể truy cập thông qua ${loginedUser}
        session.setAttribute(ATT_USER_LOGIN, loginedUser);
        session.setAttribute(ATT_SESSION_ID, sessionId);
    }

    // Lấy thông tin người dùng lưu trữ trong Session.
    public static Account getLoginedUser(HttpSession session) {
        Account loginedUser = (Account) session.getAttribute(ATT_USER_LOGIN);
        return loginedUser;
    }

    public static String getSessionLogin(HttpSession session) {
        String sessionId = (String) session.getAttribute(ATT_SESSION_ID);
        return sessionId;
    }

    public static void deleteSessionLogin(HttpSession session, String sessionId) {
        log.info("Deleting session login: " + sessionId);
        session.removeAttribute(sessionId);
    }

    public static int storeRedirectAfterLoginUrl(HttpSession session, String requestUri) {
        Integer id = uri_id_map.get(requestUri);

        if (id == null) {
            id = REDIRECT_ID++;

            uri_id_map.put(requestUri, id);
            id_uri_map.put(id, requestUri);
            return id;
        }

        return id;
    }

    public static String getRedirectAfterLoginUrl(HttpSession session, int redirectId) {
        String url = id_uri_map.get(redirectId);
        if (url != null) {
            return url;
        }
        return null;
    }

    // Lưu thông tin người dùng vào Cookie.
    public static void storeUserCookie(HttpServletResponse response, AccountLogin user) {
        System.out.println("Store user cookie");
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, user.getUser_id());
        // 1 ngày (Đã đổi ra giây)
        cookieUserName.setMaxAge(24 * 60 * 60);
        response.addCookie(cookieUserName);
    }

    public static String getUserNameInCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ATT_NAME_USER_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // Xóa Cookie của người dùng
    public static void deleteUserCookie(HttpServletResponse response) {
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
        // 0 giây. (Cookie này sẽ hết hiệu lực ngay lập tức)
        cookieUserName.setMaxAge(0);
        response.addCookie(cookieUserName);
    }

    public static String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        log.info("ipAddress: " + ipAddress);
        return ipAddress;
    }
}
