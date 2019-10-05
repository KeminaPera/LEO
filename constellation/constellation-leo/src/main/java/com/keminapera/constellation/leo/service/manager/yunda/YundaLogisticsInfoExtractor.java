package com.keminapera.constellation.leo.service.manager.yunda;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.keminapera.constellation.leo.comon.CompanyEnum;
import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.pojo.Logistics;
import com.keminapera.constellation.leo.pojo.LogisticsInfo;
import com.keminapera.constellation.leo.service.manager.AbstractLogisticsInfoExtractor;
import com.keminapera.constellation.leo.service.manager.ILogisticsInfoExtractor;
import com.keminapera.constellation.leo.util.KeyGeneratorUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 韵达快递物流信息提取器
 *
 * @author KeminaPera
 * @date 2019/10/5 13:44
 */
@Component
public class YundaLogisticsInfoExtractor extends AbstractLogisticsInfoExtractor implements ILogisticsInfoExtractor {
    @Override
    public LogisticsVo doExtractorLogistics(String receivedResult) {
        JSONObject wuliu = getSuccessData(receivedResult);
        LogisticsVo logisticsVo = new LogisticsVo();
        Logistics logistics = new Logistics();
        logistics.setCompany(CompanyEnum.YUNDA.getCompanyNumber());
        String number = wuliu.getString(ParseProperties.MAILNO);
        logistics.setNumber(number);
        List<LogisticsInfo> logisticsInfoList = doExtractorLogisticsInfoList(receivedResult, false);
        LogisticsInfo latestLogisticsInfo = logisticsInfoList.get(0);
        logistics.setLatestTime(latestLogisticsInfo.getTime());
        logistics.setLatestProgress(latestLogisticsInfo.getDesc());
        //todo:物流状态现在无法获取到
        LogisticsInfo firstLogisticsInfo = logisticsInfoList.get(logisticsInfoList.size() - 1);
        logistics.setSendTime(firstLogisticsInfo.getTime());
        logisticsVo.setLogistics(logistics);
        logisticsVo.setLogisticsInfoList(logisticsInfoList);
        return logisticsVo;
    }

    @Override
    public List<LogisticsInfo> doExtractorLogisticsInfoList(String receivedResult, boolean logisticsInfoExisted) {
        JSONObject wuliu = getSuccessData(receivedResult);
        String number = wuliu.getString(ParseProperties.MAILNO);
        List<LogisticsInfo> logisticsInfoList = new ArrayList<>(16);
        JSONArray steps = wuliu.getJSONArray(ParseProperties.STEPS);
        for (int i = 0; i < steps.size(); i++) {
            LogisticsInfo logisticsInfo = new LogisticsInfo();
            logisticsInfo.setId(KeyGeneratorUtil.genetateStringKey());
            JSONObject step = steps.getJSONObject(i);
            logisticsInfo.setTime(step.getDate(ParseProperties.TIME));
            logisticsInfo.setDesc(step.getString(ParseProperties.REMARK));
            logisticsInfo.setNumberLogistics(number);
            logisticsInfoList.add(logisticsInfo);
        }
        return logisticsInfoList;
    }

    @Override
    public JSONObject getSuccessData(String receivedResult) {
        JSONObject jsonData = JSON.parseObject(receivedResult);
        boolean success = jsonData.getBooleanValue(ParseProperties.SUCCESS);
        if (success) {
            return jsonData.getJSONObject(ParseProperties.BODY)
                    .getJSONObject(ParseProperties.DATA)
                    .getJSONObject(ParseProperties.WULIU);
        } else {
            throw new RuntimeException("获取物流信息失败！请稍后重试");
        }
    }

    private static class ParseProperties {
        private static final String SUCCESS = "success";
        private static final String BODY = "body";
        private static final String DATA = "data";
        private static final String WULIU = "wuliu";
        private static final String MAILNO = "mailno";
        private static final String STEPS = "steps";
        private static final String TIME = "time";
        private static final String REMARK = "remark";
        private static final String STATUS = "status";
    }
}
