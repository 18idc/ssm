package com.q18idc.ssm.service.impl;

import com.q18idc.ssm.dao.ClassesMapper;
import com.q18idc.ssm.dao.GroupsMapper;
import com.q18idc.ssm.dao.UserMapper;
import com.q18idc.ssm.entity.*;
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

    @Autowired
    private GroupsMapper groupsMapper;

    @Autowired
    private UserMapper userMapper;


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

    /**
     * 一对多查询  查询指定班级下的所有学生
     */
    @Test
    public void OneToMany(){
        Classes classes1 = new Classes();
        classes1.setCid(1);
        Classes classes = classesMapper.selectOneToManyByCid(classes1);
        for (Student student : classes.getStudents()) {
            System.out.println(student.getSname());
        }
    }

    /**
     * 多对多查询  根据用户ID获取用户组
     */
    @Test
    public void ManyToMany(){
        User user = new User();
        user.setId(1);
        List<Groups> groups = userMapper.selectManyToMany(user);
        for (Groups group : groups) {
            System.out.println(group.getGname());
        }
    }
}