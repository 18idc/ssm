package com.q18idc.ssm.service.impl;

import com.q18idc.ssm.entity.User;
import com.q18idc.ssm.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:Spring.xml")
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void pageAllUser() {
        User user = new User();
        user.setPage(1);
        user.setRows(10);
        List<User> list = userService.pageAllUser(user);
        System.out.println(list.size());
    }
}