package com.keminapera.constellation.leo.mapper;

import com.keminapera.constellation.leo.pojo.LogisticsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 物流信息mapper
 *
 * @author KeminaPera
 * @date 2019/10/2 10:54
 */
@Repository
@Mapper
public interface LogisticsInfoMapper {
    /**
     * 插入物流信息
     * @param logisticsInfo 物流信息实体
     */
    void insert(LogisticsInfo logisticsInfo);

    /**
     * 根据快递单号查询物流信息
     * @param number 快递单号
     * @return 物流信息列表
     */
    List<LogisticsInfo> selectByNum(String number);

    /**
     * 批量插入物流信息
     * @param logisticsInfoList 物流信息列表
     */
    void insertBatch(List<LogisticsInfo> logisticsInfoList);
}
