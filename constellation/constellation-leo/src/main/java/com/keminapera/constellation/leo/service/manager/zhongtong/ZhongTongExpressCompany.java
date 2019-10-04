package com.keminapera.constellation.leo.service.manager.zhongtong;

import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.pojo.LogisticsInfo;
import com.keminapera.constellation.leo.service.manager.AbstractExpressCompany;
import com.keminapera.constellation.leo.util.HttpUtil;
import com.keminapera.constellation.leo.util.RequestParamBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 中通快递的具体实现类
 *
 * @author KeminaPera
 * @date 2019/10/3 8:06
 */
@Component
public class ZhongTongExpressCompany extends AbstractExpressCompany implements IZhongTongExpressCompany {
    @Value("${zhongtong.url}")
    private String url = "https://hdgateway.zto.com/WayBill_GetDetail";
    /**
     * 物流信息提取器
     */
    private LogisticsInfoExtractor logisticsInfoExtractor;
    public ZhongTongExpressCompany(LogisticsInfoExtractor logisticsInfoExtractor) {
        this.logisticsInfoExtractor = logisticsInfoExtractor;
    }
    @Override
    public LogisticsVo queryLogistics(String number) {
        Map<String, String> requestParam = RequestParamBuilder.builder("billCode", number);
        String result = HttpUtil.sendPost(url, requestParam);
        return logisticsInfoExtractor.doExtractorLogistics(result);
    }

    @Override
    public List<LogisticsInfo> queryLogisticsInfoList(String number) {
        Map<String, String> requestParam = RequestParamBuilder.builder("billCode", number);
        String result = HttpUtil.sendPost(url, requestParam);
        return logisticsInfoExtractor.doExtractorLogisticsInfoList(result);
    }
}
