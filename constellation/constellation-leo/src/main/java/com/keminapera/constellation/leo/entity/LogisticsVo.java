package com.keminapera.constellation.leo.entity;

import com.keminapera.constellation.leo.pojo.Logistics;
import com.keminapera.constellation.leo.pojo.LogisticsInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 物流信息包装类
 *
 * @author KeminaPera
 * @date 2019/10/2 6:58
 */
@Data
public class LogisticsVo {
    /**
     * 物流
     */
    private Logistics logistics;
    /**
     * 物流信息列表
     */
    private List<LogisticsInfo> logisticsInfoList = new ArrayList<>(16);
}
