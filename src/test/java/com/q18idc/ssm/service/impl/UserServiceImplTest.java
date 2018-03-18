package com.q18idc.ssm.service.impl;

import com.q18idc.ssm.dao.ClassesMapper;
import com.q18idc.ssm.dao.GroupsMapper;
import com.q18idc.ssm.dao.UserMapper;
import com.q18idc.ssm.entity.*;
import com.q18idc.ssm.service.UserService;
import org.hswebframework.expands.office.excel.ExcelIO;
import org.hswebframework.expands.office.excel.config.Header;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
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

    /**
     * 导出excel
     */
    @Test
    public void export(){
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

         // 简单粗暴的写出
        try (OutputStream outputStream = new FileOutputStream("target/test_1.xlsx")) {
            ExcelIO.write(outputStream, headers, data);
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


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