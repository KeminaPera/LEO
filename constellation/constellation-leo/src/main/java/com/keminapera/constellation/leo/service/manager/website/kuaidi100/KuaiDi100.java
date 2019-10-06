package com.keminapera.constellation.leo.service.manager.website.kuaidi100;

import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.service.manager.website.AbstractWebSite;
import com.keminapera.constellation.leo.util.EnCodingAndDecodingUtil;
import com.keminapera.constellation.leo.util.HttpUtil;
import com.keminapera.constellation.leo.util.RequestParamBuilderUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 快递100网址接口的具体实现类
 *
 * @author KeminaPera
 * @date 2019/10/2 8:54
 */
@Slf4j
@Component
public class KuaiDi100 extends AbstractWebSite implements IKuaiDi100 {
    @Value("${kuaidi100.url}")
    private String url;
    @Value("${kuaidi100.cb}")
    private String cb;
    @Value("${kuaidi100.appid}")
    private String appid;
    /**
     * 物流信息提取器
     */
    private Kuaidi100LogisticsInfoLogisticsInfoExtractor kuaidi100LogisticsInfoExtractor;

    public KuaiDi100(Kuaidi100LogisticsInfoLogisticsInfoExtractor kuaidi100LogisticsInfoExtractor) {
        this.kuaidi100LogisticsInfoExtractor = kuaidi100LogisticsInfoExtractor;
    }

    @Override
    public LogisticsVo queryLogistics(String number, int company) {
        String result = buildRequestParamAndGetResult(number, company);
        Map<String, Object> contextParam = new HashMap<>(16);
        contextParam.put("number", number);
        return kuaidi100LogisticsInfoExtractor.doExtractorLogistics(result, contextParam);
    }

    @Override
    protected String buildRequestParamAndGetResult(String number, int company) {
        List<String> keyList = Arrays.asList(RequestParam.CB, RequestParam.APPID, RequestParam.COM, RequestParam.NU);
        List<String> valueList = Arrays.asList(cb, appid, ExpressCompanyEnum.getCom(company), number);
        Map<String, String> paramMap = RequestParamBuilderUtil.builder(keyList, valueList);
        String result = HttpUtil.sendGet(url, paramMap);
        String decodeResult = EnCodingAndDecodingUtil.decodeUnicode2(result);
        log.error("从快递100网站{}获取到数据{}", url, decodeResult);
        return decodeResult;
    }

    @Override
    public LogisticsVo queryLogistics(@NotNull String number) {
        //TODO: 识别该快递单号是哪个快递公司的
        return null;
    }

    private static class RequestParam {
        private static final String CB = "cb";
        private static final String APPID = "appid";
        private static final String COM = "com";
        private static final String NU = "nu";
    }
}
