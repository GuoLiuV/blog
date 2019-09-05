package com.blog.utils;

public class UnitedException extends Exception {
    private String errorCode;
    private String errorMessage;
    private String errorParam;

    public UnitedException(Throwable cause) {
        super(cause);
    }

    public UnitedException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public UnitedException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public UnitedException(String errorParam, String errorCode) {
        super(errorCode);
        this.errorParam = errorParam;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorParam() {
        return this.errorParam;
    }
}
