package com.keminapera.constellation.leo.service.manager;

import com.keminapera.constellation.leo.service.manager.kusaidi100.IKuaiDi100;
import com.keminapera.constellation.leo.service.manager.shunfeng.IShunfengExpressCompany;
import com.keminapera.constellation.leo.service.manager.yunda.IYundaExpressCompany;
import com.keminapera.constellation.leo.service.manager.zhongtong.IZhongTongExpressCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 快递公司选择器
 *
 * @author KeminaPera
 * @date 2019/10/3 10:56
 */
@Component
public class ExpressCompanySelector {
    @Autowired
    private IYundaExpressCompany yundaExpressCompany;
    @Autowired
    private IZhongTongExpressCompany zhongTongExpressCompany;
    @Autowired
    private IShunfengExpressCompany shunfengExpressCompany;
    @Autowired
    private IKuaiDi100 kuaiDi100;

    public IExpressCompany select(int company) {
        switch (company) {
            case 1:
                return yundaExpressCompany;
            case 4:
                return zhongTongExpressCompany;
            case 6:
                return shunfengExpressCompany;
            default:
                return kuaiDi100;
        }
    }
}
