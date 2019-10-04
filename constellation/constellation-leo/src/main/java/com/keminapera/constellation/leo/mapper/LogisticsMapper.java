package com.keminapera.constellation.leo.mapper;

import com.keminapera.constellation.leo.pojo.Logistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 物流mapper
 *
 * @author KeminaPera
 * @date 2019/10/2 6:23
 */
@Repository
@Mapper
public interface LogisticsMapper {
    /**
     * 添加物流
     * @param logistics 物流实体类
     */
    void insert(Logistics logistics);

    /**
     * 根据快递单号和公司查询物流信息
     *
     * @param number 快递单号
     * @param company 对应快递公司
     * @return 物流实体类
     */
    Logistics selectByNumAndCom(@Param("number") String number, @Param("company") int company);

    /**
     * 更新物流的最新进展和时间
     * @param number 快递单号
     * @param time 最新时间
     * @param desc 最新进展描述
     */
    void update(@Param("number") String number, @Param("time") Date time, @Param("desc") String desc);
}
