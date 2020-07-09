package com.bigdata.boot.chapter26.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super("Access denied when requesting the resource.");
    }
}
