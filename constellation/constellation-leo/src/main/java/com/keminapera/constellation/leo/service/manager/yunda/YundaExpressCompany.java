package com.keminapera.constellation.leo.service.manager.yunda;

import com.alibaba.fastjson.JSON;
import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.service.manager.AbstractExpressCompany;
import com.keminapera.constellation.leo.util.HttpUtil;
import com.keminapera.constellation.leo.util.RequestParamBuilderUtil;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 韵达快递公司具体实现类
 *
 * @author KeminaPera
 * @date 2019/10/5 13:42
 */
@Component
@ConfigurationProperties(prefix = "yunda")
@Setter
public class YundaExpressCompany extends AbstractExpressCompany implements IYundaExpressCompany {

    private static final String ACTION = "action";
    private static final String REQ_TIME = "req_time";
    private static final String APPID = "appid";
    private static final String DATA = "data";
    private static final String MAILNO = "mailNo";
    private static final String SORT = "sort";

    /**
     * 韵达物流信息提取器
     */
    private YundaLogisticsInfoExtractor yundaLogisticsInfoExtractor;
    private String url;
    private String action;
    private String appid;
    private String sort;

    public YundaExpressCompany(YundaLogisticsInfoExtractor yundaLogisticsInfoExtractor) {
        this.yundaLogisticsInfoExtractor = yundaLogisticsInfoExtractor;
    }

    @Override
    public LogisticsVo queryLogistics(@NotNull String number) {
        String result = buildRequestParamAndGetResult(number);
        return yundaLogisticsInfoExtractor.doExtractorLogistics(result);
    }

    @Override
    protected String buildRequestParamAndGetResult(String number) {
        List<String> keyList = Arrays.asList(ACTION, REQ_TIME, APPID, DATA);
        String reqTime = String.valueOf(System.currentTimeMillis());
        Map<String, String> dataMap = new HashMap<>(16);
        dataMap.put(MAILNO, number);
        dataMap.put(SORT, sort);
        String data = JSON.toJSONString(dataMap);
        List<String> valueList = Arrays.asList(action, reqTime, appid, data);
        Map<String, String> requestParam = RequestParamBuilderUtil.builder(keyList, valueList);
        return HttpUtil.sendPost(url, requestParam);
    }
}
