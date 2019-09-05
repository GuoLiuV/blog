package com.blog.utils;

public enum ResultEnum {
    FAIL("0", "失败"),
    SUCCESS("1", "成功"),
    ERROR("2", "错误");

    private String value;
    private String text;

    private ResultEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return this.value;
    }

    public String getText() {
        return this.text;
    }
}
