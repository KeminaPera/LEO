package com.keminapera.constellation.leo.service.storage.cache;

import com.keminapera.constellation.leo.dao.LogisticsDao;
import com.keminapera.constellation.leo.pojo.Logistics;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 使用spring的缓存机制保留可能经常访问的数据
 *
 * @author KeminaPera
 * @date 2019/10/2 6:28
 */
@Component
public class LogisticsCache {
    @Autowired
    private LogisticsDao logisticsDao;

    /**
     * 添加物流
     * @param logistics 物流实体类
     */
    public void insert(Logistics logistics) {
        logisticsDao.insert(logistics);
    }

    /**
     * 根据快递单号和公司获取物流
     * @param number 快递单号
     * @param company 公司
     * @return 物流实体类
     */
    @Nullable
    public Logistics getLogisticsByNumAndCom(String number, int company) {
        return logisticsDao.selectByNumAndCom(number, company);
    }

    /**
     * 更新物流的最新进展状况和时间
     * @param number 快递单号
     * @param time 最新时间
     * @param desc 最新进展状况
     */
    public void update(String number, Date time, String desc) {
        logisticsDao.update(number, time, desc);
    }
}
