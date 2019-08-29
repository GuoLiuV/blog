package com.blog.utils;


import cn.afterturn.easypoi.excel.entity.TemplateExportParams;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


public class ExcelExportUtil {
	public static StringBuffer	filePackage;
	public static StringBuffer	filename;
	public static String config; 
    private static ExcelExportUtil instance = null; // 单例对象
	private static File file; // 操作文件
 
    /**
     * 私有化构造方法
     * 
     * @param file
     *            文件对象
     */
    private ExcelExportUtil(File file) {
        super();
        this.file = file;
    }
 
    public File getFile() {
        return file;
    }
 
    public void setFile(File file) {
        this.file = file;
    }
 
    /**
     * 获取单例对象并进行初始化
     * 
     * @param file
     *            文件对象
     * @return 返回初始化后的单例对象
     */
    public static ExcelExportUtil getInstance(File file) {
        if (instance == null) {
            // 当单例对象为null时进入同步代码块
            synchronized (ExcelExportUtil.class) {
                // 再次判断单例对象是否为null，防止多线程访问时多次生成对象
                if (instance == null) {
                    instance = new ExcelExportUtil(file);
                }
            }
        } else {
            // 如果操作的文件对象不同，则重置文件对象
            if (!file.equals(instance.getFile())) {
                instance.setFile(file);
            }
        }
        return instance;
    }
 
    /**
     * 获取单例对象并进行初始化
     * 
     * @param filePath
     *            文件路径
     * @return 返回初始化后的单例对象
     */
    public static ExcelExportUtil getInstance(String filePath) {
        return getInstance(new File(filePath));
    }
	


	public static Workbook putInExcelData(String temPath,Map<String,Object> dataMap) {
		// 获取导出excel指定模版，第二个参数true代表显示一个Excel中的所有 sheet
		TemplateExportParams params = new TemplateExportParams(temPath, true);
		try {
			// 简单模板导出方法
			return cn.afterturn.easypoi.excel.ExcelExportUtil.exportExcel(params, dataMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void method(Map<String,Object> dataMap) {
		
		
	}
	
	/**
	 * 通用-通过模板导出方法
	 * 
	 * @return
	 */
	/*public static InputStream export(ExcelFile excelFile
			, HttpServletRequest request
			, HttpServletResponse response) {
		try {
			if(!ExcelExportUtil.isEmpty(excelFile.getTitle()))
				//ExcelUtils.addValue("title", excelFile.getTitle());
			if(excelFile.getHeadTitle()!=null)
				//ExcelUtils.addValue("headTitle", excelFile.getHeadTitle());
			if(!StringUtils.isEmpty(excelFile.getFile())) {
				ExcelExportUtil.config = excelFile.getFilePackage()+ excelFile.getFile();
			} else {

			}
			// 为每个用户创建一个导出文件夹
			excelFile.setFilePackage("D:/exportReport"
					+ "/" + new SimpleDateFormat("yyyyMM").format(new Date()) + "/excel");
			excelFile.setFilename(excelFile.getExcelFileName() + ".xls");
			ExcelExportUtil.filePackage = new StringBuffer(excelFile.getFilePackage());
			ExcelExportUtil.filename = new StringBuffer(excelFile.getFilename());
			File filex = new File(String.valueOf(filePackage));
			if(!filex.exists()) {
				filex.mkdirs();
			}
			System.out.println("----------------------------");
			System.out.println("Start export excel.....");
			String finalFileName = null;  

			final String userAgent = request.getHeader("USER-AGENT");  
            if(StringUtils.contains(userAgent, "MSIE")){//IE浏览器  
	            finalFileName = URLEncoder.encode(excelFile.getExcelFileName(),"UTF8");  
	        }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
	            finalFileName = new String(excelFile.getExcelFileName().getBytes(), "ISO8859-1");  
	        }else{  
	            finalFileName = URLEncoder.encode(excelFile.getExcelFileName(),"UTF8");//其他浏览器  
	        }  
			response.setHeader("Content-Disposition", "attachment; filename=\"" + finalFileName + ".xls\"");
			response.setContentType("applicationnd/vnd.ms-excel");
			
			FileOutputStream out = new FileOutputStream(filePackage + "/" + filename);
			//ExcelUtils.export(config, out);
			if(excelFile.isFlag()) {
	            InputStream inputStream = new FileInputStream(filePackage	 + "/" + filename);
				OutputStream ouputStream = response.getOutputStream();
				byte[] b = new byte[1024];
				int length;  
	            while((length = inputStream.read(b)) > 0){  
	            	ouputStream.write(b, 0, length);  
	            }
	            inputStream.close();  
				ouputStream.flush();     
		        ouputStream.close();
		        System.out.println("End export excel!");
				System.out.println("----------------------------");
		        return inputStream;
			}
			System.out.println("End export excel!");
			System.out.println("----------------------------");
			return null;
		} catch (Exception ex) {
			System.out.println("Excel error");
			System.out.println("----------------------------");
			UnitedLogger.error(ex);
		}
		return null;
	}*/

	public  static void aa(int i,Integer j){
		i++;j++;
		System.out.println("aa"+i+"--"+j);
	}
	public static void main(String[] args) {
		int i=1,j=2;
		System.out.println("main"+i+"--"+j);
		aa(i,j);
		System.out.println("main"+i+"--"+j);
	}
	/**
	 * 
	 * @Title: readExcel
	 * @Description: 解析excel
	 * @param file
	 * @return 
	 * @return Map<String,Object> 返回类型
	 */
//	public static List<Map<String,Object>> readExcel() throws Exception {
//		//根据其完整路径获得该workbook
//		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
//		//读取第一个sheet
//		Sheet sheet = workbook.getSheetAt(0);
//		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); 
//		//逐行处理excel数据 
//		for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			Row row = sheet.getRow(i);//获得行
//			int cellCount = row.getPhysicalNumberOfCells();//获得改行总列数
//			for (int j = 0; j < cellCount; j++) {
//				Cell cell = row.getCell(j);//获得一列               
//				map.put("cell_key_" + j, cell.getStringCellValue());
//			}
//			resultList.add(map);
//		}
//		return resultList;
//	}
	
	/**
	 * 
	 * @Title: exportExcelTeam
	 * @Description: 此Method中ComPliance - Confirmation Team 共用
	 * @param obj
	 * @param title
	 * @param file
	 * @param request
	 * @param response void 返回类型
	 */
//	public static void exportExcelTeam(Object object,String file, String title, HttpServletRequest request
//			, HttpServletResponse response) {
//		Map<String, Object> maps = new HashMap<String, Object>();
//        JSONObject jsonObject = JSONObject.parseObject(JSONUtils.toJson(object));//转换成JSONObject对象
//        Iterator<Entry<String, Object>> it = jsonObject.entrySet().iterator();  
//		while (it.hasNext()) {
//			Map.Entry<String, Object> param = (Map.Entry<String, Object>) it.next();
//			if(param.getValue() instanceof String) {//转换数据
//				if("0".equals(param.getValue())){
//					maps.put(param.getKey(), "Y");
//				} else if ("1".equals(param.getValue())){
//					maps.put(param.getKey(), "N");
//				} else if ("2".equals(param.getValue())){
//					maps.put(param.getKey(), "N/A");
//				}
//			}
//		}
//		Object objs[] = new Object[1];
//		objs[0] = JSONUtils.toJSONObject(JSONUtils.toJson(maps));//数据集
//		Object obj = JSONUtils.toJSONObject(JSONUtils.toJson(new ExportExcelDealTitle()));//头部标题
//		ExcelFile ef = new ExcelFile();
//		ef.setIsObj(2);//单个对象
//		ef.setFile(file);//文件全称
//		ef.setTitle(title);//标题
//		ef.setObjs(objs);
//		ef.setHeadTitle(obj);
//		Utils.export(ef, request, response);//导出
//	}
	
	/**
	 * 是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null || "".equals(obj)) {
			return true;
		}
		if (obj instanceof Collection) {
			@SuppressWarnings("rawtypes")
			Collection coll = (Collection) obj;
			if (coll.size() <= 0) {
				return true;
			}
		}
		if (obj instanceof Map) {
			@SuppressWarnings("rawtypes")
			Map map = (Map) obj;
			return map.isEmpty();
		}
		if(obj instanceof Object []) {
			Object[] objs = (Object [])obj;
			boolean flag = false;
			if(objs.length <= 0) {
				flag = true;
			}
			for (Object objl : objs) {
				if(objl instanceof String) {
					String str = (String)objl;
					if (str == null || str == "null" || "".equals(str) || str.length() <= 0) {
						flag = true;
						break;
					}
				}
			}
			return flag;
		}
		return false;
	}
	
	/**
	 * 是否是整数
	 * 
	 * @param strs
	 * @return
	 */
	/*public static boolean isNumber(String... strs) {
		if (isEmpty(strs)) {
			return false;
		}
		Pattern pattern = Pattern.compile("\\d*");
		boolean isNumber = true;
		for (String str : strs) {
			isNumber = pattern.matcher(str).matches();
		}
		return isNumber;
	}*/
	
	/**
	 * 
	 * @Title: replaceSymbol
	 * @Description: 替换符号
	 * @param str
	 * @param oldSymbol
	 * @param newSymbol
	 * @return String 返回类型
	 */
	public static String replaceSymbol(String str,String oldSymbol,String newSymbol) {
		if(!ExcelExportUtil.isEmpty(str)) {
			str = str.replace(oldSymbol, newSymbol);
		}
		return str;
	}
	
	/**
	 * 
	 * @Title: splitString
	 * @Description: 分割字符串
	 * @param str 字符串
	 * @param splitStr 分割符号
	 * @return 
	 * @return String[] 返回类型
	 */
	public static String[] splitString(String str, String splitStr) {
		String[] strs = null;
		if(!ExcelExportUtil.isEmpty(str) && !ExcelExportUtil.isEmpty(splitStr)) {
			strs = str.split(splitStr);
		}
		return strs;
	}
	
	/**
	 * 
	 * @Title: splitDocument
	 * @Description: 分割文档
	 * @param doc 文档
	 * @param reg 分割符号
	 * @return 
	 * @return List<DocumentEntity> 返回类型
	 */
//	public static List<DocumentEntity> splitDocument(DocumentEntity doc, String reg) {
//		String[] filenames = doc.getFileName().split(reg);
//		String[] filepaths = doc.getFilePath().split(reg);
//		String[] filesizes = doc.getFileSize().split(reg);
//		List<DocumentEntity> docs = new ArrayList<DocumentEntity>();
//		if(filenames.length == filepaths.length && 
//				filenames.length == filesizes.length) {
//			for (int i = 0; i < filenames.length; i++) {
//				DocumentEntity tranDoc = new DocumentEntity();
//				tranDoc.setFileName(filenames[i]);
//				tranDoc.setFilePath(filepaths[i]);
//				tranDoc.setFileSize(filesizes[i]);
//				docs.add(tranDoc);
//			}
//		}
//		return docs;
//	}
  
	/**
	 * 
	 * @Title: isExcel2003
	 * @Description: 是否是2003的excel，返回true是2003 
	 * @param fileName 文件的名称
	 * @return 
	 * @return boolean 返回类型
	 */
    public static boolean isExcel2003(String fileName) {  
        return fileName.matches("^.+\\.(?i)(xls)$");  
    }  
  
    /**
     * 
     * @Title: isExcel2007
     * @Description: 是否是2007的excel，返回true是2007
     * @param fileName 文件的名称
     * @return 
     * @return boolean 返回类型
     */
    public static boolean isExcel2007(String fileName) {  
        return fileName.matches("^.+\\.(?i)(xlsx)$");  
    }
    
    /**
	 * 删除集合中相同的值
	 * @Title: removeDuplicate
	 * @Date 2016-9-1 上午1:06:15
	 * @param list
	 * @return 
	 * @return List<Object> 返回类型
	 */
	public static <T> List<T> removeDuplicate(List<T> list)   {
		HashSet hash = new HashSet(list); 
		list.clear(); 
		list.addAll(hash);
	    return list;
	}
    

    /**
     * 除以1000
     * @return
     */
	public static String divideThousand(String data) {
        if (ExcelExportUtil.isEmpty(data)) {
            return data;
        }
        data = data.replaceAll(",", "");
        data = data.trim();
        Long result = Long.valueOf(data);
        return String.valueOf(result / 1000);
    }
	/**
	 * export导出请求头设置
	 *
	 * @param response
	 * @param workbook
	 * @param fileName
	 * @throws Exception
	 */
	private static void export(HttpServletResponse response, Workbook workbook, String fileName) throws Exception {
		response.reset();
		response.setContentType("application/x-msdownload");
		fileName = fileName + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO-8859-1") + ".xls");
		ServletOutputStream outStream = null;
		try {
			outStream = response.getOutputStream();
			workbook.write(outStream);
		} finally {
			outStream.close();
		}
	}



	/**
	 * 通用-通过模板导出方法
	 *
	 * @return
	 */
	public static InputStream export(ExcelFile excelFile
			, HttpServletRequest request
			, HttpServletResponse response
			, Workbook workbook) {
		try {
			//ExcelUtils.addValue("title", excelFile.getTitle());
			if (!ExcelExportUtil.isEmpty(excelFile.getTitle())) {
				//ExcelUtils.addValue("headTitle", excelFile.getHeadTitle());
				if (excelFile.getHeadTitle() != null) {
					if (!StringUtils.isEmpty(excelFile.getFile())) {
						ExcelExportUtil.config = excelFile.getFilePackage() + excelFile.getFile();
					}
				}
			}
			// 为每个用户创建一个导出文件夹
			/*excelFile.setFilePackage("D:/exportReport"
					+ "/" + new SimpleDateFormat("yyyyMM").format(new Date()) + "/excel");
			excelFile.setFilename(excelFile.getExcelFileName() + ".xls");
			ExcelExportUtil.filePackage = new StringBuffer(excelFile.getFilePackage());
			ExcelExportUtil.filename = new StringBuffer(excelFile.getFilename());
			File filex = new File(String.valueOf(excelFile.getFilePackage()));
			if (!filex.exists()) {
				filex.mkdirs();
			}*/
			String finalFileName = null;
			final String userAgent = request.getHeader("USER-AGENT");
			if (StringUtils.contains(userAgent, "MSIE")) {//IE浏览器
				finalFileName = URLEncoder.encode(excelFile.getExcelFileName(), "UTF8");
			} else if (StringUtils.contains(userAgent, "Mozilla")) {//google,火狐浏览器
				finalFileName = new String(excelFile.getExcelFileName().getBytes(), "ISO8859-1");
			} else {
				finalFileName = URLEncoder.encode(excelFile.getExcelFileName(), "UTF8");//其他浏览器
			}
			response.setHeader("Content-Disposition", "attachment; filename=\"" + finalFileName + ".xls\"");
			response.setContentType("applicationnd/vnd.ms-excel");
			ServletOutputStream out = response.getOutputStream();
			workbook.write(out);
			return null;
		} catch (Exception ex) {
			System.out.println("Excel error");
			ex.printStackTrace();
		}
		return null;
	}

	public static void finalExport(Map dataMap,HttpServletRequest request, HttpServletResponse response){
		try {
			//新建对象时把模板名称加进去
			ExcelFile excelFile = new ExcelFile(dataMap.get("templeName").toString(), "", true);
			excelFile.setExcelFileName(dataMap.get("excelFileName").toString());
			//获取根路径
			String xlsPath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
			if (StringUtils.isNotEmpty(xlsPath)) {
				xlsPath = xlsPath + "xls/";
				xlsPath = xlsPath.substring(1, xlsPath.length());
			}
			excelFile.setFilePackage(xlsPath);
			//放置数据和模板
			Workbook wb = ExcelExportUtil.putInExcelData(excelFile.getFilePackage() + excelFile.getFile(), dataMap);
			//导出文件
			ExcelExportUtil.export(excelFile, request, response, wb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
