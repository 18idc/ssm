package com.q18idc.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.q18idc.ssm.dao.UserMapper;
import com.q18idc.ssm.entity.*;
import com.q18idc.ssm.service.UserService;
import org.hswebframework.expands.office.excel.ExcelIO;
import org.hswebframework.expands.office.excel.config.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * User控制器
 * @author q18idc.com QQ993143799
 * Created by q18idc.com QQ993143799 on 2018/2/15
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户列表  返回 json
     *
     * @param user
     * @return
     */
    @RequestMapping(value = {"list"})
    @ResponseBody
    public ResultJson list(User user) {
        ResultJson json = new ResultJson();
        List<User> list = userService.pageAllUser(user);
        PageInfo<User> info = new PageInfo<User>(list);
        json.setTotal((int) info.getTotal());
        json.setRows(info.getList());
        return json;
    }

    /**
     * 添加或修改用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = {"addUpdate"})
    @ResponseBody
    public Result add(User user) {
        Result json = new Result();
        String b = userService.addUpdateUser(user);
        if (b.indexOf("成功") != -1) {
            json.setMessage(b);
            json.setSuccess(true);
        } else {
            json.setMessage(b);
            json.setSuccess(false);
        }
        return json;
    }

    /**
     * 删除用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = {"del"})
    @ResponseBody
    public Result del(User user) {
        Result json = new Result();
        boolean b = userService.delUser(user);
        if (b) {
            json.setMessage("删除成功");
            json.setSuccess(true);
        } else {
            json.setMessage("删除失败");
            json.setSuccess(false);
        }
        return json;
    }

    /**
     * 性别统计
     * @return
     */
    @RequestMapping(value = {"sex"})
    @ResponseBody
    public List<Map<Object, Object>> sex() {
        int nan = userService.sexCount("男");
        int nv = userService.sexCount("女");
        Map<Object, Object> map = new HashMap<>();
        map.put("name", "男");
        map.put("value", nan);
        Map<Object, Object> map2 = new HashMap<>();
        map2.put("name", "女");
        map2.put("value", nv);
        List<Map<Object, Object>> mapList = new ArrayList<>();
        mapList.add(map);
        mapList.add(map2);
        return mapList;
    }

    /**
     * 一对一查询 查询出班级下的老师  假设一个班只能有一个老师来教
     * @return
     */
    @RequestMapping(value = {"OneToOne"})
    @ResponseBody
    public List<Classes> OneToOne(){
        Classes classes = new Classes();
        classes.setCname("软件");
        return userService.classesOneToOne(classes);
    }

    /**
     * 一对多查询  查询指定班级下的所有学生
     * @return
     */
    @RequestMapping(value = {"OneToMany"})
    @ResponseBody
    public Classes OneToMany(){
        Classes classes = new Classes();
        classes.setCid(1);
        return userService.classesOneToMany(classes);
    }

    /**
     * 多对多查询  根据用户ID获取用户组
     * @return
     */
    @RequestMapping(value = {"ManyToMany"})
    @ResponseBody
    public List<Groups> ManyToMany(){
        User user = new User();
        user.setId(1);
       return userService.selectManyToMany(user);
    }

    /**
     * 文件上传  导入Excel
     */
    @RequestMapping(value = {"upload"})
    @ResponseBody
    public Map<String ,Object> upload(HttpServletRequest request) throws Exception {
        Map<String ,Object> map = new HashMap<>();
        int sun = 0;//总条数
        int success = 0;//成功条数
        int error = 0;//失败条数
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                //一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                if (file != null) {
                    List<Map<String, Object>> list = ExcelIO.read2Map(file.getInputStream());
                    sun = list.size();
                    for (Map<String, Object> stringObjectMap : list) {
                        User user  = new User();
                        user.setUsername(stringObjectMap.get("用户名").toString());
                        user.setPassword(stringObjectMap.get("密码").toString());
                        user.setPhone(stringObjectMap.get("电话").toString());
                        user.setEmail(stringObjectMap.get("邮箱").toString());
                        user.setSex(stringObjectMap.get("性别").toString());
                        user.setBirthday((Date) stringObjectMap.get("生日"));
                        String s = userService.addUpdateUser(user);
                        if(s.indexOf("成功") == -1){
                            error++;
                        }else {
                            success++;
                        }
                    }
                }
            }
            map.put("flag",true);
            map.put("msg","总条数：" + sun + " 成功：" + success + "条 失败：" + error + "条");
//            sun = 0;
//            success = 0;
//            error = 0;
            return map;
        } else {
            map.put("flag",false);
            map.put("msg","导入失败");
//            sun = 0;
//            success = 0;
//            error = 0;
            return map;
        }

    }

    /*
    * 导出Excel  默认导出全部
    */
    @RequestMapping("export")
    public void  download(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<Header> headers = new LinkedList<>();
        List<Object> data = new ArrayList<>();
        List<User> datas = userMapper.selectByExample(null);
        for (User user : datas) {
            data.add(user);
        }

        headers.add(new Header("用户名","username"));
        headers.add(new Header("密码","password"));
        headers.add(new Header("电话","phone"));
        headers.add(new Header("邮箱","email"));
        headers.add(new Header("性别","sex"));
        headers.add(new Header("生日","birthday"));

        String fileName = "测试.xlsx";

        //设置响应头和客户端保存文件名
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName*=UTF-8''" + URLEncoder.encode(fileName,"UTF-8"));
        try {
            OutputStream os = response.getOutputStream();
            ExcelIO.write(os, headers, data);
            os.flush();
            os.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

}