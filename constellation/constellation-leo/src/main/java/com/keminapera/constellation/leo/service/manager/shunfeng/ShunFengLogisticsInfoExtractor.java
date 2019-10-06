package com.keminapera.constellation.leo.service.manager.shunfeng;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.keminapera.constellation.leo.comon.CompanyEnum;
import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.pojo.Logistics;
import com.keminapera.constellation.leo.pojo.LogisticsInfo;
import com.keminapera.constellation.leo.service.manager.AbstractLogisticsInfoExtractor;
import com.keminapera.constellation.leo.service.manager.ILogisticsInfoExtractor;
import com.keminapera.constellation.leo.service.manager.kusaidi100.Kuaidi100LogisticsInfoLogisticsInfoExtractor;
import com.keminapera.constellation.leo.util.KeyGeneratorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 顺丰物流信息提取器
 *
 * @author KeminaPera
 * @date 2019/10/5 17:22
 */
public class ShunFengLogisticsInfoExtractor extends AbstractLogisticsInfoExtractor implements ILogisticsInfoExtractor {
    @Override
    public LogisticsVo doExtractorLogistics(String receivedResult, Map<String, Object> contextParam) {
        JSONObject successData = getSuccessData(receivedResult);
        LogisticsVo logisticsVo = new LogisticsVo();
        Logistics logistics = new Logistics();
        String number = successData.getString(ParseProperties.NUMBER);
        logistics.setNumber(number);
        logistics.setCompany(CompanyEnum.SHUNFENG.getCompanyNumber());
        logistics.setSendTime(successData.getDate(ParseProperties.SEND_TIME));
        logistics.setState(successData.getInteger(ParseProperties.STATE));

        List<LogisticsInfo> logisticsInfoList = doExtractorOrderedLogisticsInfoList(receivedResult);
        LogisticsInfo latestLogisticsInfo = logisticsInfoList.get(0);
        logistics.setLatestTime(latestLogisticsInfo.getTime());
        logistics.setLatestProgress(latestLogisticsInfo.getDesc());

        logisticsVo.setLogistics(logistics);
        logisticsVo.setLogisticsInfoList(logisticsInfoList);
        return logisticsVo;
    }

    @Override
    public List<LogisticsInfo> doExtractorOrderedLogisticsInfoList(String receivedResult, Map<String, Object> contextParam) {
        JSONObject successData = getSuccessData(receivedResult);
        List<LogisticsInfo> logisticsInfoList = new ArrayList<>(1);
        String number = successData.getString(ParseProperties.NUMBER);
        JSONArray logisticsInfoJson = successData.getJSONArray(ParseProperties.LOGISTICS_INFO);
        for (int i = 0; i < logisticsInfoJson.size(); i++) {
            LogisticsInfo logisticsInfo = new LogisticsInfo();
            logisticsInfo.setId(KeyGeneratorUtil.genetateStringKey());
            JSONObject info = logisticsInfoJson.getJSONObject(i);
            logisticsInfo.setTime(info.getDate(ParseProperties.TIME));
            logisticsInfo.setDesc(info.getString(ParseProperties.DESC));
            logisticsInfo.setNumberLogistics(number);
            logisticsInfoList.add(logisticsInfo);
        }
        return logisticsInfoList;
    }

    @Override
    public JSONObject getSuccessData(String receivedResult) {
        JSONArray jsonArray = JSON.parseArray(receivedResult);
        return jsonArray.getJSONObject(0);
    }

    /**
     * 解析物流信息的参数
     * {DESC,TIME,NUMBER}必须与
     * {@link Kuaidi100LogisticsInfoLogisticsInfoExtractor.ParseProperties}名称一致
     */
    private static class ParseProperties {
        private static final String NUMBER = "id";
        private static final String STATE = "billFlag";
        private static final String SEND_TIME = "recipientTime";
        private static final String LOGISTICS_INFO = "routes";
        private static final String TIME = "scanDateTime";
        private static final String DESC = "remark";
    }
}
