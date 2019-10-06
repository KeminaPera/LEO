package com.keminapera.constellation.leo.service.storage;

import com.keminapera.constellation.leo.pojo.Logistics;
import com.keminapera.constellation.leo.service.storage.cache.LogisticsCache;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

/**
 * 物流存储类
 *
 * @author KeminaPera
 * @date 2019/10/2 6:31
 */
@Component
public class LogisticsStorage {
    /**
     * 物流缓存类
     */
    private LogisticsCache logisticsCache;

    public LogisticsStorage(LogisticsCache logisticsCache) {
        this.logisticsCache = logisticsCache;
    }

    /**
     * 添加物流
     * @param logistics 物流实体类
     */
    public void insert(@NotNull Logistics logistics) {
        logisticsCache.insert(logistics);
    }

    /**
     * 根据快递单号和公司查询物流
     * @param number 快递单号
     * @param type 公司
     * @return 物流
     */
    @Nullable
    public Logistics getLogisticsByNumAndCom(String number, int type) {
        return logisticsCache.getLogisticsByNumAndCom(number, type);
    }

    /**
     * 更新物流的最新进展状况和时间
     * @param logistics 物流实体对象
     */
    public void updateLogisticsStateInfo(Logistics logistics) {
        logisticsCache.updateLogisticsStateInfo(logistics);
    }
}
