package com.keminapera.constellation.leo.service.manager;

import com.alibaba.fastjson.JSONObject;
import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.pojo.LogisticsInfo;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * 物流信息提取器接口
 *
 * @author KeminaPera
 * @date 2019/10/4 18:04
 */
public interface ILogisticsInfoExtractor {
    /**
     * 从第三方返回的数据解析出物流信息
     *
     * @param receivedResult 第三方返回数据
     * @return 物流信息包装类
     */
    LogisticsVo doExtractorLogistics(String receivedResult);

    /**
     * 从第三方返回数据中解析出物流信息（物流单号本系统已存在）
     *
     * @param receivedResult 第三方返回数据
     * @return 物流信息列表
     */
    List<LogisticsInfo> doExtractorOrderedLogisticsInfoList(String receivedResult);

    /**
     * 判断是否成功拿到数据，如果成功，返回可以提取物流信息的对象
     * 否则，抛出异常
     *
     * @param receivedResult 第三方数据
     * @return json对象
     */
    JSONObject getSuccessData(String receivedResult);

    /**
     * 从第三方返回数据中解析出物流信息
     * 排好序的物流信息（按时间降序）
     *
     * @param receivedResult 第三方返回数据
     * @param contextParam   上下文参数
     *                       如果没有需要上下文传递的参数，
     *                       请调用{@link #doExtractorOrderedLogisticsInfoList(String)}
     * @return 物流信息列表
     */
    List<LogisticsInfo> doExtractorOrderedLogisticsInfoList(String receivedResult, @Nullable Map<String, Object> contextParam);

    /**
     * 从第三方返回的数据解析出物流信息
     *
     * @param receivedResult 第三方返回数据
     * @param contextParam   上下文参数
     *                       如果没有需要上下文传递的参数，
     *                       请调用{@link #doExtractorOrderedLogisticsInfoList(String)}
     * @return 物流信息包装类
     */
    LogisticsVo doExtractorLogistics(String receivedResult, @Nullable Map<String, Object> contextParam);
}
