package com.blog.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.util.ClassUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @Description :
 * @Author :付亚东
 * @Date :2019/8/15
 **/
public class WordExportUtil {
    public static void export(HttpServletRequest request, HttpServletResponse response, WordFile wordFile,Map data) throws Exception{
        if(wordFile==null) throw new  Exception("导出配置不能为null");
        if(wordFile.getTemplateName()==null|| StringUtils.isEmpty(wordFile.getTemplateName())) throw new  Exception("导出模板名称不能为null");
        if(wordFile.getExportFileName()==null|| StringUtils.isEmpty(wordFile.getExportFileName())) throw new  Exception("导出名称不能为null");
        //获取路径
        String wordPath=  ClassUtils.getDefaultClassLoader().getResource("").getPath() + "model/word/";
        XWPFDocument xwpfDocument=cn.afterturn.easypoi.word.WordExportUtil.exportWord07(wordPath+wordFile.getTemplateName(),data);
        String finalFileName = null;
        final String userAgent = request.getHeader("USER-AGENT");
        if (StringUtils.contains(userAgent, "MSIE")) {//IE浏览器
            finalFileName = URLEncoder.encode(wordFile.getExportFileName(), "UTF8");
        } else if (StringUtils.contains(userAgent, "Mozilla")) {//google,火狐浏览器
            finalFileName = new String(wordFile.getExportFileName().getBytes(), "ISO8859-1");
        } else {
            finalFileName = URLEncoder.encode(wordFile.getExportFileName(), "UTF8");//其他浏览器
        }
        response.setHeader("Content-Disposition", "attachment; filename=\"" + finalFileName + ".docx\"");
        response.setContentType("application/force-download");
        OutputStream outputStream = response.getOutputStream();
        xwpfDocument.write(outputStream);
        xwpfDocument.close();
        outputStream.close();
    }
}
