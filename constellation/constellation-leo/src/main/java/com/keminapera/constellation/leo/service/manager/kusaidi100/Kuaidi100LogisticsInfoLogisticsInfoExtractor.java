package com.keminapera.constellation.leo.service.manager.kusaidi100;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.pojo.Logistics;
import com.keminapera.constellation.leo.pojo.LogisticsInfo;
import com.keminapera.constellation.leo.service.manager.AbstractLogisticsInfoExtractor;
import com.keminapera.constellation.leo.service.manager.ILogisticsInfoExtractor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    /**
     * 解析出物流信息（系统已经存在该物流信息）
     * @param result 第三方获取的结果
     * @param logisticsInfoExisted 该物流信息在本地是否已存在
     * @return 物流信息列表
     */
    @Override
    @NotNull
    public List<LogisticsInfo> doExtractorLogisticsInfoList(@NotNull String result, boolean logisticsInfoExisted) {
        String targetResult = logisticsInfoExisted ? getTargetString(result) : result;
        List<LogisticsInfo> logisticsInfoList = new ArrayList<>(16);
        JSONObject jsonObject = JSON.parseObject(targetResult);
        JSONArray jsonarray = jsonObject.getJSONArray(ParseProperties.DATA);
        for(int i = 0; i < jsonarray.size(); i++) {
            LogisticsInfo logisticsInfo = new LogisticsInfo();
            logisticsInfo.setId(UUID.randomUUID().toString());
            JSONObject info = jsonarray.getJSONObject(i);
            Date time = new Date(info.get(ParseProperties.DATA_TIME).toString());
            logisticsInfo.setTime(time);
            logisticsInfo.setDesc(info.get(ParseProperties.DATA_CONTEXT).toString());
            logisticsInfo.setNumberLogistics(jsonObject.get(ParseProperties.NU).toString());
            logisticsInfoList.add(logisticsInfo);
        }
        return logisticsInfoList;
    }

    /**
     * 解析物流信息（本系统还未有快递任何信息）
     * @param result 第三方获取的结果
     * @return 物流信息
     */
    @Override
    public LogisticsVo doExtractorLogistics(@NotNull String result) {
        String targetResult = getTargetString(result);
        LogisticsVo logisticsVo = new LogisticsVo();
        Logistics logistics = new Logistics();
        JSONObject jsonObject = JSON.parseObject(targetResult);
        logistics.setNumber(jsonObject.get(ParseProperties.NU).toString());
        logistics.setState(jsonObject.getInteger(ParseProperties.STATE));
        logistics.setCompany(ExpressCompanyEnum.getNumber(jsonObject.get(ParseProperties.COM).toString()));
        logisticsVo.setLogistics(logistics);
        List<LogisticsInfo> logisticsInfoList = doExtractorLogisticsInfoList(targetResult, false);
        logisticsVo.setLogisticsInfoList(logisticsInfoList);
        return logisticsVo;
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

    private static class ParseProperties{
        /**
         * 快递单号
         */
        private static final String NU = "nu";
        /**
         * 快递公司的英文简拼
         */
        private static final String COM = "com";
        /**
         * 获取第三方数据的状态200：正常
         */
        private static final String STATUS = "status";
        /**
         * 物流状态
         */
        private static final String STATE = "state";
        /**
         * 存放物流信息的字段
         */
        private static final String DATA = "data";
        /**
         * 每段物流信息的时间
         */
        private static final String DATA_TIME = "time";
        /**
         * 物流信息的描述
         */
        private static final String DATA_CONTEXT = "context";
    }
}
