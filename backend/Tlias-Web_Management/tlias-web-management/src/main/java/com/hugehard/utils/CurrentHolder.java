package com.hugehard.utils;

public class CurrentHolder {
    private static final ThreadLocal<Integer> CURRENT_LOCAL = new ThreadLocal<>();

    public static void setCurrentEmployeeId(Integer employeeId) {
        CURRENT_LOCAL.set(employeeId);
    }

    public static Integer getCurrentEmployeeId() {
        return CURRENT_LOCAL.get();
    }

    public static void removeCurrentEmployeeId() {
        CURRENT_LOCAL.remove();
    }
}