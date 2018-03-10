package com.q18idc.ssm.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.q18idc.ssm.dao.ClassesMapper;
import com.q18idc.ssm.dao.UserMapper;
import com.q18idc.ssm.entity.Classes;
import com.q18idc.ssm.entity.ClassesExample;
import com.q18idc.ssm.entity.User;
import com.q18idc.ssm.entity.UserExample;
import com.q18idc.ssm.service.UserService;
import com.q18idc.ssm.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author q18idc.com QQ993143799
 * Created by q18idc.com QQ993143799 on 2018/2/15
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ClassesMapper classesMapper;

    /**
     * 分页获取用户列表
     *
     * @param user 用户实体
     * @return
     */
    @Override
    public List<User> pageAllUser(User user) {
        if (user != null) {
            int page = 1;
            int rows = 10;

            if (user.getPage() != null) {
                page = user.getPage();
            }

            if (user.getRows() != null) {
                rows = user.getRows();
            }

            String key = user.getKey();

            List<User> userList = null;

            PageHelper.startPage(page, rows);

            //根据关键字搜索
            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andUsernameLike("%" + key + "%");
            userExample.or().andPhoneLike("%" + key + "%");
            userExample.or().andEmailLike("%" + key + "%");

            //查询全部
            if (MyUtils.isEmpty(key)) {
                userExample = null;
            }

            userList = userMapper.selectByExample(userExample);

            return userList;
        }
        return null;
    }

    /**
     * 添加或修改用户 返回成功或失败
     *
     * @param user 用户实体
     * @return
     */
    @Override
    @Transactional
    public String addUpdateUser(User user) {
        if (user != null) {
            int i = -1;

            if (MyUtils.isEmpty(user.getUsername()) ||
                    MyUtils.isEmpty(user.getPassword()) ||
                    MyUtils.isEmpty(user.getPhone()) ||
                    MyUtils.isEmpty(user.getEmail()) ||
                    MyUtils.isEmpty(user.getSex()) ||
                    user.getBirthday() == null) {
                return "请将信息填写完整";
            }

            //判断用户名是否输入特殊字符
            if (MyUtils.isSpecialChar(user.getUsername())) {
                return "用户名请勿提交非法字符";
            }
            //判断密码是否输入特殊字符
            if (MyUtils.isSpecialChar(user.getPassword())) {
                return "密码请勿提交非法字符";
            }

            //判断是否正确选择性别
//            if (("男".equals(user.getSex())) == false || ("女".equals(user.getSex())) == false) {
//                return "请重新选择性别";
//            }

            //判断输入的是否是正确的邮箱
            if (MyUtils.isEmail(user.getEmail()) == false) {
                return "请输入正确的邮箱";
            }

            //判断电话输入的是否是数字
            if (MyUtils.checkNumer(user.getPhone())) {
                if (MyUtils.isMobileNum(user.getPhone()) == false) {
                    return "请输入正确的手机号";
                }
            } else {
                return "请输入正确的手机号";
            }

            if (user.getId() == null) {
                //添加用户
                i = userMapper.insertSelective(user);
                if (i > 0) {
                    return "添加成功";
                } else {
                    return "添加失败";
                }
            } else {
                //修改用户
                i = userMapper.updateByPrimaryKey(user);
                if (i > 0) {
                    return "修改成功";
                } else {
                    return "修改失败";
                }
            }
        }
        return "操作失败";
    }

    /**
     * 删除用户 返回成功或失败
     *
     * @param user
     * @return
     */
    @Transactional
    @Override
    public boolean delUser(User user) {
        if (user != null) {
            if (StringUtils.isNumber(user.getId().toString())) {
                int i = userMapper.deleteByPrimaryKey(user.getId());
                return i > 0;
            }
        }
        return false;
    }

    /**
     * 根据性别统计数量
     *
     * @param sex 性别
     * @return
     */
    @Override
    public Integer sexCount(String sex) {
        if (MyUtils.isNotEmpty(sex)) {
            UserExample userExample = new UserExample();
            UserExample.Criteria cri = userExample.createCriteria();
            cri.andSexEqualTo(sex);
            return (int) userMapper.countByExample(userExample);
        }
        return 0;
    }

    /**
     * 一对一查询 查询出班级下的老师  假设一个班只能有一个老师来教
     *
     * @param classes
     * @return
     */
    @Override
    public List<Classes> classesOneToOne(Classes classes) {
        if (classes != null) {
            ClassesExample classesExample = new ClassesExample();
            if (MyUtils.isNotEmpty(classes.getCname())) {
                //关键字搜索
                ClassesExample.Criteria criteria = classesExample.createCriteria();
                criteria.andCnameLike("%"+classes.getCname()+"%");
            }else {
                //查询全部
                classesExample = null;
            }
            return classesMapper.selectOneToOne(classesExample);
        }
        return null;
    }
}
