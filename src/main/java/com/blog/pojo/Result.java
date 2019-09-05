package com.blog.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 6288374846131788743L;
    private String message;
    private String messageCode;
    private String resultCode;
    private Object data;
}
