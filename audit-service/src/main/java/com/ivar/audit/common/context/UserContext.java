package com.ivar.audit.common.context;

public class UserContext {

    private static final ThreadLocal<String> CURRENT_USER =
            new ThreadLocal<>();

    private static final ThreadLocal<String> CURRENT_ROLE =
            new ThreadLocal<>();

    public static void setUser(String user) {
        CURRENT_USER.set(user);
    }

    public static String getUser() {
        return CURRENT_USER.get();
    }

    public static void setRole(String role) {
        CURRENT_ROLE.set(role);
    }

    public static String getRole() {
        return CURRENT_ROLE.get();
    }

    public static void clear() {
        CURRENT_USER.remove();
        CURRENT_ROLE.remove();
    }
}