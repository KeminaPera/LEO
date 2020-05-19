package com.keminapera.constellation.leo.service.manager.website.kuaidi100;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.pojo.Logistics;
import com.keminapera.constellation.leo.pojo.LogisticsInfo;
import com.keminapera.constellation.leo.service.manager.AbstractLogisticsInfoExtractor;
import com.keminapera.constellation.leo.service.manager.ILogisticsInfoExtractor;
import com.keminapera.constellation.leo.util.KeyGeneratorUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 从第三方返回结果中提取物流信息
 *
 * @author KeminaPera
 * @date 2019/10/2 10:06
 */
@Component
public final class Kuaidi100LogisticsInfoLogisticsInfoExtractor extends AbstractLogisticsInfoExtractor implements ILogisticsInfoExtractor {
    private Kuaidi100LogisticsInfoLogisticsInfoExtractor() {
    }

    @Override
    @NotNull
    public List<LogisticsInfo> doExtractorOrderedLogisticsInfoList(@NotNull String receivedResult, Map<String, Object> contextParam) {
        JSONObject successData = getSuccessData(receivedResult);
        List<LogisticsInfo> logisticsInfoList = new ArrayList<>(16);
        JSONArray jsonarray = successData.getJSONArray(ParseProperties.LOGISTICS_INFO);
        for(int i = 0; i < jsonarray.size(); i++) {
            LogisticsInfo logisticsInfo = new LogisticsInfo();
            logisticsInfo.setId(KeyGeneratorUtil.genetateStringKey());
            JSONObject infoJson = jsonarray.getJSONObject(i);
            logisticsInfo.setTime(new Date(infoJson.getLongValue(ParseProperties.TIME) * 1000));
            logisticsInfo.setDesc(infoJson.getString(ParseProperties.DESC));
            logisticsInfo.setNumberLogistics(contextParam.get("number").toString());
            logisticsInfoList.add(logisticsInfo);
        }
        return logisticsInfoList;
    }

    @Override
    public LogisticsVo doExtractorLogistics(@NotNull String receivedResult, Map<String, Object> contextParam) {
        JSONObject successData = getSuccessData(receivedResult);
        LogisticsVo logisticsVo = new LogisticsVo();
        Logistics logistics = new Logistics();
        logistics.setNumber(contextParam.get("number").toString());
        int originState = successData.getInteger(ParseProperties.STATE);
        logistics.setState(Kuaidi100LogisticsStateConverter.getLocalLogisticsState(originState));
        logistics.setCompany(ExpressCompanyEnum.getNumber(successData.get(ParseProperties.COM).toString()));
        logistics.setSendTime(successData.getDate(ParseProperties.SEND_TIME));
        List<LogisticsInfo> logisticsInfoList = doExtractorOrderedLogisticsInfoList(receivedResult, contextParam);
        LogisticsInfo latestLogisticsInfo = logisticsInfoList.get(0);
        logistics.setLatestTime(latestLogisticsInfo.getTime());
        logistics.setLatestProgress(latestLogisticsInfo.getDesc());
        logisticsVo.setLogistics(logistics);
        logisticsVo.setLogisticsInfoList(logisticsInfoList);
        return logisticsVo;
    }

    @Override
    public JSONObject getSuccessData(String receivedResult) {
        String targetString = getTargetString(receivedResult);
        JSONObject jsonObject = JSON.parseObject(targetString);
        if (jsonObject.getIntValue(ParseProperties.STATUS) == 0) {
            return jsonObject.getJSONObject(ParseProperties.DATA).getJSONObject(ParseProperties.INFO);
        }
        throw new RuntimeException("该单号暂无物流进展，请稍后再试，或检查公司和单号是否有误");
    }

    /**
     * 由于从快递100拿到的结果不能直接使用fastjson进行转化，需要进行处理
     *
     * @param decodeString 解码后的string
     * @return 目标string
     */
    private String getTargetString(String decodeString) {
        int startPosition = decodeString.indexOf("(") + 1;
        return StringUtils.substring(decodeString, startPosition, decodeString.length() - 1);
    }

    public static class ParseProperties {
        /**
         * 获取第三方数据的状态200：正常
         */
        private static final String STATUS = "status";
        /**
         * 存放物流信息的字段
         */
        private static final String DATA = "data";
        /**
         * 存放物流信息和物流状态
         */
        private static final String INFO = "info";
        /**
         * 快递公司的英文简拼
         */
        private static final String COM = "com";
        /**
         * 物流状态
         */
        private static final String STATE = "state";
        /**
         * 发货时间
         */
        private static final String SEND_TIME = "send_time";
        /**
         * 最新进展
         */
        private static final String LATEST_PROGRESS = "latest_progress";
        /**
         * 物流信息的描述
         */
        private static final String LOGISTICS_INFO = "context";
        /**
         * 每段物流信息的时间
         */
        private static final String TIME = "time";
        /**
         * 物流信息描述
         */
        private static final String DESC = "desc";
    }
}
