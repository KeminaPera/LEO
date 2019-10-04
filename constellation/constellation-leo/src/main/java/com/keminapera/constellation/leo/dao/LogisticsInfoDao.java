package com.keminapera.constellation.leo.dao;

import com.keminapera.constellation.leo.mapper.LogisticsInfoMapper;
import com.keminapera.constellation.leo.pojo.LogisticsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 物流信息dao
 *
 * @author KeminaPera
 * @date 2019/10/2 6:12
 */
@Repository
public class LogisticsInfoDao {
    @Autowired
    private LogisticsInfoMapper logisticsInfoMapper;

    /**
     * 根据快递单号查询物流信息
     * @param number 快递单号
     * @return 物流信息列表
     */
    public List<LogisticsInfo> selectByNum(String number) {
        return logisticsInfoMapper.selectByNum(number);
    }

    /**
     * 批量添加物流信息
     * @param logisticsInfoList 物流信息列表
     */
    public void insertBatch(List<LogisticsInfo> logisticsInfoList) {
        //todo: 批量添加
        for (LogisticsInfo logisticsInfo : logisticsInfoList) {
            logisticsInfoMapper.insert(logisticsInfo);
        }
    }
}
