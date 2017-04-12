package com.zaihua.dao.dal.mapper;

import com.zaihua.dao.entity.KDay;
import com.zaihua.dao.entity.KDayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface KDayMapper {
    int countByExample(KDayExample example);

    int deleteByExample(KDayExample example);

    int deleteByPrimaryKey(Long id);

    int insert(KDay record);

    int insertSelective(KDay record);

    List<KDay> selectByExample(KDayExample example);

    KDay selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") KDay record, @Param("example") KDayExample example);

    int updateByExample(@Param("record") KDay record, @Param("example") KDayExample example);

    int updateByPrimaryKeySelective(KDay record);

    int updateByPrimaryKey(KDay record);
}