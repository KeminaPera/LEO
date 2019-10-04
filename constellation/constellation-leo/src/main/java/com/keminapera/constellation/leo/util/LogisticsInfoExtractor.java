package com.keminapera.constellation.leo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.keminapera.constellation.leo.comon.ExpressCompanyEnum;
import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.pojo.Logistics;
import com.keminapera.constellation.leo.pojo.LogisticsInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
 public final class LogisticsInfoExtractor {
    private LogisticsInfoExtractor(){}

    /**
     * 解析出物流信息（系统已经存在该物流信息）
     * @param result 第三方获取的结果
     * @return 物流信息列表
     */
    @NotNull
    public static List<LogisticsInfo> doExtractor(@Nullable String result) {
        List<LogisticsInfo> logisticsInfoList = new ArrayList<>(16);
        if (result == null) {
            return logisticsInfoList;
        }
        JSONObject jsonObject = JSON.parseObject(result);
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
    public static LogisticsVo doExtractorWithNothing(@Nullable String result) {
        LogisticsVo logisticsVo = new LogisticsVo();
        if (result == null) {
            return logisticsVo;
        }
        Logistics logistics = new Logistics();
        JSONObject jsonObject = JSON.parseObject(result);
        logistics.setNumber(jsonObject.get(ParseProperties.NU).toString());
        logistics.setState(jsonObject.getInteger(ParseProperties.STATE));
        logistics.setCompany(ExpressCompanyEnum.getNumber(jsonObject.get(ParseProperties.COM).toString()));
        logisticsVo.setLogistics(logistics);
        List<LogisticsInfo> logisticsInfoList = doExtractor(result);
        logisticsVo.setLogisticsInfoList(logisticsInfoList);
        return logisticsVo;
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
