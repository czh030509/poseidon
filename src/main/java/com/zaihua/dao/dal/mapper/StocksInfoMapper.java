package com.zaihua.dao.dal.mapper;

import com.zaihua.dao.entity.StocksInfo;
import com.zaihua.dao.entity.StocksInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StocksInfoMapper {
    int countByExample(StocksInfoExample example);

    int deleteByExample(StocksInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(StocksInfo record);

    int insertSelective(StocksInfo record);

    List<StocksInfo> selectByExample(StocksInfoExample example);

    StocksInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") StocksInfo record, @Param("example") StocksInfoExample example);

    int updateByExample(@Param("record") StocksInfo record, @Param("example") StocksInfoExample example);

    int updateByPrimaryKeySelective(StocksInfo record);

    int updateByPrimaryKey(StocksInfo record);
}