package com.kiss.personalproject.config;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SecurityConfig {

    // String: Role
    // List<String>: urlPatterns.
    private static final Map<String, List<String>> mapConfig = new HashMap<String, List<String>>();

    public static Set<String> getAllAppRoles() {
        return mapConfig.keySet();
    }

    public static List<String> getUrlPatternsForRole(String role) {
        return mapConfig.get(role);
    }

    public static void setStoreRoleForUrlPattern(String role, String urlPattern) {
        List<String> urlPatterns = getUrlPatternsForRole(role);
        if(ObjectUtils.isEmpty(urlPatterns)) {
            urlPatterns = new ArrayList<>();
        }
        if (!urlPatterns.contains(urlPattern)) {
            urlPatterns.add(urlPattern);
            mapConfig.put(role, urlPatterns);
        }
    }

}