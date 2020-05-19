package com.keminapera.constellation.leo.service.manager;

import com.keminapera.constellation.leo.service.manager.company.ExpressCompanySelector;
import com.keminapera.constellation.leo.service.manager.website.WebSiteSelector;
import org.springframework.stereotype.Component;

/**
 * 物流查询策略实现类
 *
 * @author KeminaPera
 * @date 2019/10/6 23:23
 */
@Component
public class QueryLogisticsStrategy implements IStrategy {
    private ExpressCompanySelector expressCompanySelector;
    private WebSiteSelector webSiteSelector;

    public QueryLogisticsStrategy(ExpressCompanySelector expressCompanySelector, WebSiteSelector webSiteSelector) {
        this.expressCompanySelector = expressCompanySelector;
        this.webSiteSelector = webSiteSelector;
    }

    /**
     * 优先使用快递公司查询，如果没有该快公司，则通过快递100网站查询
     *
     * @param company 快递公司号
     * @return 目标对象
     */
    public IExpressCompany getLogisticsByDefaultStrategy(int company) {
        IExpressCompany targetExpressCompany = expressCompanySelector.select(company);
        if (targetExpressCompany == null) {
            return webSiteSelector.getWebsite(1);
        }
        return targetExpressCompany;
    }
}
