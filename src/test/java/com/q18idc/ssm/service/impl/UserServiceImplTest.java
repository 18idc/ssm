package com.q18idc.ssm.service.impl;

import com.q18idc.ssm.dao.ClassesMapper;
import com.q18idc.ssm.entity.Classes;
import com.q18idc.ssm.entity.ClassesExample;
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

    @Autowired
    private ClassesMapper classesMapper;

    @Test
    public void pageAllUser() {
        User user = new User();
        user.setPage(1);
        user.setRows(10);
        List<User> list = userService.pageAllUser(user);
        System.out.println(list.size());
    }

    /**
     * 一对一查询 查询出班级下的老师  假设一个班只能有一个老师来教
     */
    @Test
    public void OneToOne(){
        ClassesExample classesExample = new ClassesExample();

        //关键字搜索
//        ClassesExample.Criteria criteria = classesExample.createCriteria();
//        criteria.andCnameLike("%软件%");

        //查询全部
        classesExample = null;

        List<Classes> classes = classesMapper.selectOneToOne(classesExample);
        for (Classes aClass : classes) {
            System.out.println(aClass.getTeacher().getTname());
        }
    }
}