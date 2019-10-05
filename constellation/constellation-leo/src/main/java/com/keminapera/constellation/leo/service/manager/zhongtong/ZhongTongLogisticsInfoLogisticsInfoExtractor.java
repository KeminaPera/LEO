package com.keminapera.constellation.leo.service.manager.zhongtong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.keminapera.constellation.leo.comon.CompanyEnum;
import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.exception.HttpException;
import com.keminapera.constellation.leo.pojo.Logistics;
import com.keminapera.constellation.leo.pojo.LogisticsInfo;
import com.keminapera.constellation.leo.service.manager.AbstractLogisticsInfoExtractor;
import com.keminapera.constellation.leo.service.manager.ILogisticsInfoExtractor;
import com.keminapera.constellation.leo.util.KeyGeneratorUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 中通快递物流信息提取器
 *
 * @author KeminaPera
 * @date 2019/10/3 8:15
 */
@Component
public final class ZhongTongLogisticsInfoLogisticsInfoExtractor extends AbstractLogisticsInfoExtractor implements ILogisticsInfoExtractor {
    /**
     * 从数据中提取物流信息（本系统还没有该快递单号的任何信息）
     * @param data 第三方返回的结果
     * @return {@link LogisticsVo} 物流信息包装类
     */
    @Override
    public LogisticsVo doExtractorLogistics(String data) {
        if (StringUtils.isBlank(data)) {
            throw new HttpException();
        }
        JSONObject json = JSON.parseObject(data);
        if(!json.getBooleanValue(ParseProperties.STATUS)) {
            throw new HttpException();
        }
        LogisticsVo logisticsVo = new LogisticsVo();
        Logistics logistics = new Logistics();
        JSONObject jsonObject = json.getJSONObject(ParseProperties.RESULT);
        logistics.setState(jsonObject.getIntValue(ParseProperties.STATE));
        String number = jsonObject.getString(ParseProperties.NUMBER);
        logistics.setNumber(number);
        logistics.setCompany(CompanyEnum.ZHONGTONG.getCompanyNumber());
        List<LogisticsInfo> logisticsInfoList = doExtractorLogisticsInfoList(data, false);
        LogisticsInfo latestLogisticsInfo = logisticsInfoList.get(0);
        logistics.setLatestTime(latestLogisticsInfo.getTime());
        logistics.setLatestProgress(latestLogisticsInfo.getDesc());
        logistics.setSendTime(logisticsInfoList.get(logisticsInfoList.size() - 1).getTime());

        logisticsVo.setLogistics(logistics);
        logisticsVo.setLogisticsInfoList(logisticsInfoList);
        return logisticsVo;
    }

    /**
     * 提取物流信息（本系统已存在该快递信息）
     * @param data 第三方返回的结果
     * @return 物流信息列表
     */
    @Override
    public List<LogisticsInfo> doExtractorLogisticsInfoList(String data, boolean logisticsInfoExisted) {
        JSONObject json = JSON.parseObject(data);
        if(!json.getBooleanValue(ParseProperties.STATUS)) {
            throw new HttpException();
        }
        JSONObject jsonObject = json.getJSONObject(ParseProperties.RESULT);
        String number = jsonObject.getString(ParseProperties.NUMBER);
        List<LogisticsInfo> logisticsInfoList = new ArrayList<>(16);
        JSONArray logisticsRecord = jsonObject.getJSONArray(ParseProperties.LOGISTICS);
        for (int i = 0; i < logisticsRecord.size(); i++) {
            JSONArray record = logisticsRecord.getJSONArray(i);
            for (int j = 0; j < record.size(); j++) {
                LogisticsInfo logisticsInfo = new LogisticsInfo();
                logisticsInfo.setId(KeyGeneratorUtil.genetateStringKey());
                JSONObject message = record.getJSONObject(j);
                Date time = message.getDate(ParseProperties.LOGISTICS_TIME);
                logisticsInfo.setTime(time);
                String desc = message.getString(ParseProperties.LOGISTICS_DESC);
                logisticsInfo.setDesc(desc);
                logisticsInfo.setNumberLogistics(number);
                logisticsInfoList.add(logisticsInfo);
            }
        }
        return logisticsInfoList;
    }

    private static class ParseProperties {
        private static final String RESULT = "result";
        private static final String STATUS = "status";
        private static final String STATE = "prescriptionStatus";
        private static final String LOGISTICS = "logisticsRecord";
        private static final String LOGISTICS_TIME = "scanDate";
        private static final String LOGISTICS_DESC = "stateDescription";
        private static final String NUMBER = "billCode";
    }
}
