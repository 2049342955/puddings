package com.demo.core.web;

public enum ResponseState {
    ERROR("error"),
    SUCCESS("success");

    private String value;

    private ResponseState(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
