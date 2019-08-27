package com.blog.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelFile {
	private Object[] objs;// 数据对象
	private String filePackage;//文件包
	private String filename;//文件名称
	private String file;//文件
	private String title;//title
	private Object headTitle;//头 标题
	private Integer isObj;//1 默认为单个List, 2 单个对象, 若有多个集合或对象集合组成，请自行往下增加
	private boolean flag = true;//默认为true,是否输出文件
	private String excelFileName; //导出名称
	public Integer getIsObj() {
		return isObj;
	}
	public void setIsObj(Integer isObj) {
		this.isObj = isObj;
	}
	
	public ExcelFile(String file,String title,boolean flag){
		this.file = file;
		this.title = title;
		this.flag = flag;
	}
	
	public ExcelFile(Object[] objs, String file, String title,
			Object headTitle, Integer isObj) {
		super();
		this.objs = objs;
		this.file = file;
		this.title = title;
		this.headTitle = headTitle;
		this.isObj = isObj;
	}
	
	public ExcelFile(Object[] objs, String file, String title, 
			Object headTitle, Integer isObj, boolean flag) {
		super();
		this.objs = objs;
		this.file = file;
		this.title = title;
		this.headTitle = headTitle;
		this.isObj = isObj;
		this.flag = flag;
	}
	public ExcelFile() {
		super();
	}
	public Object[] getObjs() {
		return objs;
	}
	public void setObjs(Object[] objs) {
		this.objs = objs;
	}
	public String getFilePackage() {
		return filePackage;
	}
	public void setFilePackage(String filePackage) {
		this.filePackage = filePackage;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public Object getHeadTitle() {
		return headTitle;
	}
	public void setHeadTitle(Object headTitle) {
		this.headTitle = headTitle;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getExcelFileName() {
		if(StringUtils.isEmpty(excelFileName)) {
			excelFileName = title + "-(" + new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + ")";
		}
		return excelFileName;
	}
	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}
	
}
