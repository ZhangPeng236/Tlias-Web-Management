package com.hugehard.exception;

public class DeptContainsEmpException extends RuntimeException {
    public DeptContainsEmpException() {
    }

    public DeptContainsEmpException(String message) {
        super(message);
    }
}