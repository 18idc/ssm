package com.q18idc.ssm.dao;

import com.q18idc.ssm.entity.Classes;
import com.q18idc.ssm.entity.ClassesExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassesMapper {
    long countByExample(ClassesExample example);

    int deleteByExample(ClassesExample example);

    int deleteByPrimaryKey(Integer cid);

    int insert(Classes record);

    int insertSelective(Classes record);

    List<Classes> selectByExample(ClassesExample example);

    Classes selectByPrimaryKey(Integer cid);

    int updateByExampleSelective(@Param("record") Classes record, @Param("example") ClassesExample example);

    int updateByExample(@Param("record") Classes record, @Param("example") ClassesExample example);

    int updateByPrimaryKeySelective(Classes record);

    int updateByPrimaryKey(Classes record);


    List<Classes> selectOneToOne(ClassesExample classesExample);

    Classes selectOneToManyByCid(Classes classes);
}