package com.q18idc.ssm.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.github.pagehelper.PageInfo;
import com.q18idc.ssm.dao.UserMapper;
import com.q18idc.ssm.entity.*;
import com.q18idc.ssm.excel.UserExcelData;
import com.q18idc.ssm.excel.UserExcelDataListener;
import com.q18idc.ssm.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * User控制器
 *
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
        json.setTotal((int)info.getTotal());
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
     *
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
     *
     * @return
     */
    @RequestMapping(value = {"OneToOne"})
    @ResponseBody
    public List<Classes> OneToOne() {
        Classes classes = new Classes();
        classes.setCname("软件");
        return userService.classesOneToOne(classes);
    }

    /**
     * 一对多查询  查询指定班级下的所有学生
     *
     * @return
     */
    @RequestMapping(value = {"OneToMany"})
    @ResponseBody
    public Classes OneToMany() {
        Classes classes = new Classes();
        classes.setCid(1);
        return userService.classesOneToMany(classes);
    }

    /**
     * 多对多查询  根据用户ID获取用户组
     *
     * @return
     */
    @RequestMapping(value = {"ManyToMany"})
    @ResponseBody
    public List<Groups> ManyToMany() {
        User user = new User();
        user.setId(1);
        return userService.selectManyToMany(user);
    }

    /**
     * 文件上传  导入Excel
     */
    @PostMapping(value = {"upload"})
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file) throws Exception {
        Map<String, Object> map = new HashMap<>();
        int sun = 0;//总条数
        int success = 0;//成功条数
        int error = 0;//失败条数

        if (file != null) {
            EasyExcel.read(file.getInputStream(), UserExcelData.class, new UserExcelDataListener(userService)).sheet().doRead();
        }

        map.put("flag", true);
        map.put("msg", "总条数：" + sun + " 成功：" + success + "条 失败：" + error + "条");
        //            sun = 0;
        //            success = 0;
        //            error = 0;
        return map;

    }

    /*
     * 导出Excel  默认导出全部
     */
    @RequestMapping("export")
    public void download2(HttpServletResponse response) {
        List<User> allUserData = userMapper.selectByExample(null);
        if (allUserData != null && !allUserData.isEmpty()) {

            List<UserExcelData> data = ListUtils.newArrayList();
            allUserData.forEach(item -> {
                UserExcelData excelData = new UserExcelData();
                excelData.setUsername(item.getUsername());
                excelData.setPassword(item.getPassword());
                excelData.setPhone(item.getPhone());
                excelData.setEmail(item.getEmail());
                excelData.setSex(item.getSex());
                excelData.setBirthday(item.getBirthday());
                data.add(excelData);
            });

            try {

                String fileName = "测试.xlsx";

                //设置响应头和客户端保存文件名
                response.setCharacterEncoding("utf-8");
                response.setContentType("multipart/form-data");
                response.setContentType("application/vnd.ms-excel;charset=utf-8");
                response.setHeader("Content-Disposition", "attachment;fileName*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8"));

                EasyExcel.write(response.getOutputStream(), UserExcelData.class).sheet("模板").doWrite(data);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}