package com.keminapera.constellation.leo.service.storage;

import com.keminapera.constellation.leo.pojo.LogisticsInfo;
import com.keminapera.constellation.leo.service.storage.cache.LogisticsInfoCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 中间层--存储数据
 *
 * @author KeminaPera
 * @date 2019/10/2 6:18
 */
@Component
public class LogisticsInfoStorage {
    @Autowired
    private LogisticsInfoCache logisticsInfoCache;

    /**
     * 根据快递单号查询物流信息
     * @param number 快递单号
     * @return 物流信息列表
     */
    public List<LogisticsInfo> getLogisticsInfo(String number){
        return logisticsInfoCache.getLogisticsInfo(number);
    }

    public void insertBatch(List<LogisticsInfo> logisticsInfoList) {
        logisticsInfoCache.insertBatch(logisticsInfoList);
    }
}
