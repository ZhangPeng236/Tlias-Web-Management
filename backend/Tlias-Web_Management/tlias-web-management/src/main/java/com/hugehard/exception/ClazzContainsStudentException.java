package com.hugehard.exception;

public class ClazzContainsStudentException extends RuntimeException {
    public ClazzContainsStudentException() {
    }

    public ClazzContainsStudentException(String message) {
        super(message);
    }
}