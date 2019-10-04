package com.keminapera.constellation.leo.service.storage.cache;

import com.keminapera.constellation.leo.dao.LogisticsInfoDao;
import com.keminapera.constellation.leo.pojo.LogisticsInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 物流信息缓存类
 *
 * @author KeminaPera
 * @date 2019/10/2 6:16
 */
@Component
public class LogisticsInfoCache {
    private LogisticsInfoDao logisticsInfoDao;

    public LogisticsInfoCache(LogisticsInfoDao logisticsInfoDao) {
        this.logisticsInfoDao = logisticsInfoDao;
    }

    /**
     * 根据快递单号查询物流信息
     * @param number 快递单号
     * @return 物流信息列表
     */
    public List<LogisticsInfo> getLogisticsInfo(String number) {
        return logisticsInfoDao.selectByNum(number);
    }

    /**
     * 批量添加物流信息
     * @param logisticsInfoList 物流信息列表
     */
    public void insertBatch(List<LogisticsInfo> logisticsInfoList) {
        logisticsInfoDao.insertBatch(logisticsInfoList);
    }
}
