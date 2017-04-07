package com.zaihua.dao.entity;

import com.zaihua.dao.dal.mapper.KDayMapper;
import com.zaihua.model.stock.Stocks;
import com.zaihua.utils.KDayDbUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by carl on 07/04/2017.
 */
@Repository
public class KDayDao {
    @Autowired
    private KDayMapper kDayMapper;

    public KDay selectBySymbol(String symbol) {
        KDayExample kDayExample = new KDayExample();
        kDayExample.createCriteria().andSymbolEqualTo(symbol);


        List<KDay> kDayList = kDayMapper.selectByExample(kDayExample);

        if(CollectionUtils.isEmpty(kDayList)){
            return null;
        }
        return kDayList.get(0);
    }

}
