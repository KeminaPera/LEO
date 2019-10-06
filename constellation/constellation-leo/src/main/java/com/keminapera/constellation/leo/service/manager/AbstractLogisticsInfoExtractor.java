package com.keminapera.constellation.leo.service.manager;

import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.pojo.LogisticsInfo;

import java.util.List;

/**
 * 物流信息提取器抽象类
 *
 * @author KeminaPera
 * @date 2019/10/4 18:06
 */
public abstract class AbstractLogisticsInfoExtractor implements ILogisticsInfoExtractor {
    @Override
    public LogisticsVo doExtractorLogistics(String receivedResult) {
        return doExtractorLogistics(receivedResult, null);
    }

    @Override
    public List<LogisticsInfo> doExtractorOrderedLogisticsInfoList(String receivedResult) {
        return doExtractorOrderedLogisticsInfoList(receivedResult, null);
    }
}
