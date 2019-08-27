package com.blog.pojo;

import lombok.Data;

@Data
public class BlogInfo {
	// 主键
	private String id;
	// 分类
	private String blogType;
	// 图片
	private String blogImg;
	// 标题
	private String blogTitle;
	// 内容
	private String blogContent;
	// 发表时间
	private String publishDate;
	// 发表人
	private String userId;
}
