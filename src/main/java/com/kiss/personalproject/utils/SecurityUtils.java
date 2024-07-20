package com.kiss.personalproject.utils;

import com.kiss.personalproject.config.SecurityConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class SecurityUtils {
    static Logger log = Logger.getLogger(SecurityUtils.class.getName());

    public static final int TIME_LIMIT_MIN = 10;
    public static final int TIME_LIMIT_MAX = 15;
    private static final String PERPER = "7nWZLcCK0vsPzIM";

    // Kiểm tra 'request' này có bắt buộc phải đăng nhập hay không.
    public static boolean isSecurityPage(HttpServletRequest request) {
        String urlPattern = UrlPatternUtils.getUrlPattern(request);

        Set<String> roles = SecurityConfig.getAllAppRoles();
        log.info("roles: " + roles.toString());
        if(ObjectUtils.isEmpty(roles)) return true;

        for (String role : roles) {
            List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);
            if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }

    // Kiểm tra 'request' này có vai trò phù hợp hay không?
    public static boolean hasPermission(HttpServletRequest request) {
        String urlPattern = UrlPatternUtils.getUrlPattern(request);

        Set<String> allRoles = SecurityConfig.getAllAppRoles();
        log.info("allRoles: " + allRoles.toString())    ;

        for (String role : allRoles) {
            if (!request.isUserInRole(role)) {
                continue;
            }
            List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);
            if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }

    public static String createNewSessionId() {
        return RandomStringUtils.randomAlphanumeric(1000);
    }

    public static Date getNextSessionTime() {
        return DateUtils.addMinutes(new Date(), SecurityUtils.TIME_LIMIT_MAX);
    }

    public static Date getNextTime(Date date, int time) {
        return DateUtils.addMinutes(date, time);
    }

    public static String hashPassword(String salt, String password) {
        return DigestUtils.md5Hex(PERPER + salt + password);
    }

    public static String getRandomSaf() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}
