package com.keminapera.constellation.leo.service.manager.website;

import com.keminapera.constellation.leo.service.manager.website.kuaidi100.IKuaiDi100;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 第三方网站选择器
 *
 * @author KeminaPera
 * @date 2019/10/6 23:10
 */
@Component
public class WebSiteSelector {
    @Autowired
    private IKuaiDi100 kuaiDi100;

    public IWebSite getWebsite(int website) {
        switch (website) {
            case 1:
                return kuaiDi100;
            default:
                throw new RuntimeException("没有对应的网站");
        }
    }
}
