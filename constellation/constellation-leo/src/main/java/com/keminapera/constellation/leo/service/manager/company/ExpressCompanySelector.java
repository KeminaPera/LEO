package com.keminapera.constellation.leo.service.manager.company;

import com.keminapera.constellation.leo.service.manager.IExpressCompany;
import com.keminapera.constellation.leo.service.manager.company.shunfeng.IShunfengExpressCompany;
import com.keminapera.constellation.leo.service.manager.company.yunda.IYundaExpressCompany;
import com.keminapera.constellation.leo.service.manager.company.zhongtong.IZhongTongExpressCompany;
import org.jetbrains.annotations.Nullable;
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

    /**
     * 快递公司
     *
     * @param company 快递公司号
     * @return 对应快递公司实体
     */
    @Nullable
    public IExpressCompany select(int company) {
        switch (company) {
            case 1:
                return yundaExpressCompany;
            case 4:
                return zhongTongExpressCompany;
            case 6:
                return shunfengExpressCompany;
            default:
                return null;
        }
    }
}
