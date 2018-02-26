package com.q18idc.ssm.service;

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
}
