package com.bigdata.boot.chapter26.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("Requested resource doesn't exist.");
    }
}
