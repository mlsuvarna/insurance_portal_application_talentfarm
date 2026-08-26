package com.insurance.insuranceportal.exception;

public class PolicyTypeNotFoundException
        extends RuntimeException {

    public PolicyTypeNotFoundException(String message) {
        super(message);
    }
}