package com.keminapera.constellation.leo.service.manager;

import com.keminapera.constellation.leo.comon.ExpressCompanyEnum;
import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.pojo.LogisticsInfo;
import com.keminapera.constellation.leo.util.HttpUtil;
import com.keminapera.constellation.leo.util.LogisticsInfoExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
public class KuaiDi100 extends AbstractExpressCompany implements IKuaiDi100 {
    @Value("kuaidi100.url")
    private String url = "https://www.kuaidi100.com/query";

    @Override
    public List<LogisticsInfo> queryLogisticdInfo(String number, int company) {
        Map<String, String> paramMap = buildParamMap(number, company);
        String result = HttpUtil.sendGet(url, paramMap);
        log.error("从快递100网站获取到数据", result);
        return LogisticsInfoExtractor.doExtractor(result);
    }

    @Override
    public LogisticsVo queryLogisticdInfoNothing(String number, int company) {
        Map<String, String> paramMap = buildParamMap(number, company);
        String result = HttpUtil.sendGet(url, paramMap);
        log.error("从快递100网站获取到数据", result);
        return LogisticsInfoExtractor.doExtractorWithNothing(result);
    }

    public static void main(String[] args) {
        KuaiDi100 kuaiDi100 = new KuaiDi100();
        kuaiDi100.queryLogisticdInfo("", 1);
    }

    @Override
    public LogisticsVo queryLogistics(String number) {
        return null;
    }
    private Map buildParamMap(String number, int company) {
        Map<String, String> paramMap = new HashMap(16);
        paramMap.put("type",ExpressCompanyEnum.getCom(company));
        paramMap.put("postid", number);
        return paramMap;
    }
}
