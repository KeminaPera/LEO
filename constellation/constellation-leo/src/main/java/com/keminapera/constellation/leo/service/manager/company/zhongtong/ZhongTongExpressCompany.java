package com.keminapera.constellation.leo.service.manager.company.zhongtong;

import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.service.manager.AbstractExpressCompany;
import com.keminapera.constellation.leo.util.HttpUtil;
import com.keminapera.constellation.leo.util.RequestParamBuilderUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
    private ZhongTongLogisticsInfoLogisticsInfoExtractor zhongTongLogisticsInfoExtractor;

    public ZhongTongExpressCompany(ZhongTongLogisticsInfoLogisticsInfoExtractor zhongTongLogisticsInfoExtractor) {
        this.zhongTongLogisticsInfoExtractor = zhongTongLogisticsInfoExtractor;
    }
    @Override
    public LogisticsVo queryLogistics(@NotNull String number) {
        String result = buildRequestParamAndGetResult(number);
        return zhongTongLogisticsInfoExtractor.doExtractorLogistics(result);
    }

    @Override
    protected String buildRequestParamAndGetResult(String number) {
        Map<String, String> requestParam = RequestParamBuilderUtil.builder("billCode", number);
        return HttpUtil.sendPost(url, requestParam);
    }
}
