package com.keminapera.constellation.leo.dao;

import com.keminapera.constellation.leo.mapper.LogisticsMapper;
import com.keminapera.constellation.leo.pojo.Logistics;
import org.springframework.stereotype.Repository;

/**
 * 物流dao
 *
 * @author KeminaPera
 * @date 2019/10/2 6:25
 */
@Repository
public class LogisticsDao {
    private LogisticsMapper logisticsMapper;

    public LogisticsDao(LogisticsMapper logisticsMapper) {
        this.logisticsMapper = logisticsMapper;
    }

    /**
     * 添加物流
     * @param logistics 物流实体类
     */
    public void insert(Logistics logistics) {
        logisticsMapper.insert(logistics);
    }

    /**
     * 根据快递单号和公司查询物流信息
     * @param number 快递单号
     * @param company 公司
     * @return 物流实体类
     */
    public Logistics selectByNumAndCom(String number, int company) {
        return logisticsMapper.selectByNumAndCom(number, company);
    }

    /**
     * 更新物流的最新进展和时间
     * @param logistics 物流实体
     */
    public void updateLogisticsStateInfo(Logistics logistics) {
        logisticsMapper.updateLogisticsStateInfo(logistics.getNumber(), logistics.getLatestTime(),
                logistics.getLatestProgress(), logistics.getState());
    }
}
