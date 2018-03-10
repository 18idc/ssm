package com.q18idc.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.q18idc.ssm.entity.Classes;
import com.q18idc.ssm.entity.Result;
import com.q18idc.ssm.entity.ResultJson;
import com.q18idc.ssm.entity.User;
import com.q18idc.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


}