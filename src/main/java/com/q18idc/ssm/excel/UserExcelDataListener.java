package com.q18idc.ssm.excel;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import com.q18idc.ssm.entity.User;
import com.q18idc.ssm.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class UserExcelDataListener implements ReadListener<UserExcelData> {
    private UserService userService;

    public UserExcelDataListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void invoke(UserExcelData data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));

        User user = new User();
        user.setUsername(data.getUsername());
        user.setPassword(data.getPassword());
        user.setPhone(data.getPhone());
        user.setEmail(data.getEmail());
        user.setSex(data.getSex());
        user.setBirthday(data.getBirthday());

        userService.addUpdateUser(user);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
