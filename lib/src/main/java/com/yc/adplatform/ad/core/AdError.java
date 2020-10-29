package com.yc.adplatform.ad.core;

public class AdError {
    public static final int AD_INIT_ERROR = 1002;
    public static final int AD_ERROR = 1004;

    private String code;
    private String message;
    private Throwable throwable;
    private int tripartiteCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public void setTripartiteCode(int tripartiteCode) {
        this.tripartiteCode = tripartiteCode;
    }

    public int getTripartiteCode() {
        return tripartiteCode;
    }
}