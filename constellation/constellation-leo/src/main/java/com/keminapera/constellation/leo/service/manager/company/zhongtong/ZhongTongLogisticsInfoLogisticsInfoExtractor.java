package com.keminapera.constellation.leo.service.manager.company.zhongtong;

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
import com.keminapera.constellation.leo.service.manager.website.kuaidi100.Kuaidi100LogisticsInfoLogisticsInfoExtractor;
import com.keminapera.constellation.leo.util.KeyGeneratorUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 中通快递物流信息提取器
 *
 * @author KeminaPera
 * @date 2019/10/3 8:15
 */
@Component
public final class ZhongTongLogisticsInfoLogisticsInfoExtractor extends AbstractLogisticsInfoExtractor implements ILogisticsInfoExtractor {

    @Override
    public LogisticsVo doExtractorLogistics(String receivedResult, Map<String, Object> contextParam) {
        JSONObject successData = getSuccessData(receivedResult);
        LogisticsVo logisticsVo = new LogisticsVo();
        Logistics logistics = new Logistics();
        logistics.setState(successData.getIntValue(ParseProperties.STATE));
        String number = successData.getString(ParseProperties.NUMBER);
        logistics.setNumber(number);
        logistics.setCompany(CompanyEnum.ZHONGTONG.getCompanyNumber());
        List<LogisticsInfo> logisticsInfoList = doExtractorOrderedLogisticsInfoList(receivedResult);
        LogisticsInfo latestLogisticsInfo = logisticsInfoList.get(0);
        logistics.setLatestTime(latestLogisticsInfo.getTime());
        logistics.setLatestProgress(latestLogisticsInfo.getDesc());
        logistics.setSendTime(logisticsInfoList.get(logisticsInfoList.size() - 1).getTime());

        logisticsVo.setLogistics(logistics);
        logisticsVo.setLogisticsInfoList(logisticsInfoList);
        return logisticsVo;
    }

    @Override
    public List<LogisticsInfo> doExtractorOrderedLogisticsInfoList(String receivedResult, Map<String, Object> contextParam) {
        JSONObject successData = getSuccessData(receivedResult);
        String number = successData.getString(ParseProperties.NUMBER);
        List<LogisticsInfo> logisticsInfoList = new ArrayList<>(16);
        JSONArray logisticsRecord = successData.getJSONArray(ParseProperties.LOGISTICS);
        for (int i = 0; i < logisticsRecord.size(); i++) {
            JSONArray record = logisticsRecord.getJSONArray(i);
            for (int j = 0; j < record.size(); j++) {
                LogisticsInfo logisticsInfo = new LogisticsInfo();
                logisticsInfo.setId(KeyGeneratorUtil.genetateStringKey());
                JSONObject message = record.getJSONObject(j);
                Date time = message.getDate(ParseProperties.TIME);
                logisticsInfo.setTime(time);
                String desc = message.getString(ParseProperties.DESC);
                logisticsInfo.setDesc(desc);
                logisticsInfo.setNumberLogistics(number);
                logisticsInfoList.add(logisticsInfo);
            }
        }
        return logisticsInfoList;
    }

    @Override
    public JSONObject getSuccessData(String receivedResult) {
        JSONObject json = JSON.parseObject(receivedResult);
        if (!json.getBooleanValue(ParseProperties.STATUS)) {
            //todo:自定义专门的异常类
            throw new HttpException();
        }
        return json.getJSONObject(ParseProperties.RESULT);
    }

    /**
     * 解析物流信息的参数
     * {DESC,TIME,NUMBER}必须与
     * {@link Kuaidi100LogisticsInfoLogisticsInfoExtractor.ParseProperties}名称一致
     */
    private static class ParseProperties {
        private static final String RESULT = "result";
        private static final String STATUS = "status";
        private static final String STATE = "prescriptionStatus";
        private static final String LOGISTICS = "logisticsRecord";
        private static final String TIME = "scanDate";
        private static final String DESC = "stateDescription";
        private static final String NUMBER = "billCode";
    }
}
