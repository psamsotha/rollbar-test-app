package com.demo.provider;

public class Flag {

    private final boolean  isPresent;

    public Flag(String param) {
        isPresent = param != null;
    }

    public boolean isPresent() {
        return isPresent;
    }
}
