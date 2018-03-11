package com.q18idc.ssm.service;

import com.q18idc.ssm.entity.Classes;
import com.q18idc.ssm.entity.Groups;
import com.q18idc.ssm.entity.User;

import java.util.List;

/**
 * @author q18idc.com QQ993143799
 * Created by q18idc.com QQ993143799 on 2018/2/15
 */
public interface UserService {
    /**
     * 分页获取用户列表
     * @param user 用户实体
     * @return
     */
    List<User> pageAllUser(User user);

    /**
     * 添加或修改用户 返回成功或失败
     * @param user 用户实体
     * @return
     */
    String addUpdateUser(User user);

    /**
     * 删除用户 返回成功或失败
     * @param user
     * @return
     */
    boolean delUser(User user);

    /**
     * 根据性别统计数量
     * @param sex 性别
     * @return 统计数量
     */
    Integer sexCount(String sex);

    /**
     * 一对一查询 查询出班级下的老师  假设一个班只能有一个老师来教
     * @param classes
     * @return
     */
    List<Classes> classesOneToOne(Classes classes);

    /**
     * 一对多查询 查询指定班级下的所有学生
     * @param classes
     * @return
     */
    Classes classesOneToMany(Classes classes);

    /**
     * 多对多查询  根据用户ID获取用户组
     * @param user
     * @return
     */
    List<Groups> selectManyToMany(User user);
}
