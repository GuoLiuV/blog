package com.blog.controller;

import com.blog.pojo.BlogInfo;
import com.blog.pojo.User;
import com.blog.service.UserService;
import com.blog.utils.DataTablePage;
import com.blog.utils.ExcelExportUtil;
import com.blog.utils.WordExportUtil;
import com.blog.utils.WordFile;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("export")
public class ExportController {

    @Resource
    public UserService userService;

    /**
     * 用户界面
     */
    @RequestMapping(value = "init_page.do")
    public String userPage() {
        return "back/user_list_page";
    }

    /**
     * 用户list
     */
    @ResponseBody
    @RequestMapping(value = "user_list.json")
    public DataTablePage<User> userList(Model model, HttpServletRequest request, User user) {
        DataTablePage<User> pageList = new DataTablePage<>(request);
        try {
            PageInfo<User> pagePojoList = userService.queryAllByLimit(pageList.getPageNum(), pageList.getLength(), user);
            pageList.transfer(pageList, pagePojoList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", e.getMessage());
        }
        return pageList;
    }

    /**
     * 导出豁免函
     * @param request
     * @param response
     */
    @RequestMapping(value = "/exportBlogFile.do", produces = {"application/vnd.ms-excel;charset=UTF-8"})
    public void exportReportScore(HttpServletRequest request, HttpServletResponse response) {
        /*, @RequestParam(value = "id", defaultValue = "") String id*/
        try {
            URL url = this.getClass().getClassLoader().getResource("model/xls/");

            File file = new File(url.getPath());
            String fileName = file.getName();
            String filePath = file.getPath();
            //所需数据，按照 key和 value存进去，有两个必须的数据 templeName  excelFileName
            Map<String, Object> dataMap = new HashMap<String, Object>();
            BlogInfo blog = new BlogInfo();
            blog.setBlogTitle("-- 标题 --");
            blog.setBlogContent("--------------------文章内容----------------------！");
            //blog = reportScoreService.selectById(id);
            dataMap.put("bean", blog);
            dataMap.put("templeName", "reportscore.xls");
            dataMap.put("excelFileName", "我的博客报表-(" + LocalDate.now() + ")");
            ExcelExportUtil.finalExport(dataMap,request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出DOC文档
     * @param request
     * @param response
     * @param id
     */
    @RequestMapping(value = "/exportWord.do", produces = {"application/force-download;charset=UTF-8"})
    public void exportSeStaffReport(HttpServletRequest request, HttpServletResponse response,
                                    String id){
        BlogInfo blog = new BlogInfo();
        Map dataMap = new HashMap();
        try {
            //seStaffReport = seStaffReportService.selectById(id);
            //putSeStaffReportDataMap(dataMap,seStaffReport);
            WordFile wordFile=new WordFile();
            wordFile.setTemplateName("sestaffreport.docx");
            wordFile.setExportFileName("敏感人申告");
            WordExportUtil.export(request,response,wordFile,dataMap);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
