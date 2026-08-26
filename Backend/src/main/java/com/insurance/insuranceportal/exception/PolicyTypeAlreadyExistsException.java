package com.insurance.insuranceportal.exception;

public class PolicyTypeAlreadyExistsException
        extends RuntimeException {

    public PolicyTypeAlreadyExistsException(String message) {
        super(message);
    }
}