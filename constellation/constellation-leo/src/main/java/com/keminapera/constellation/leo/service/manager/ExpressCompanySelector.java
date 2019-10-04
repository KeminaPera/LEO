package com.keminapera.constellation.leo.service.manager;

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
    private IZhongTongExpressCompany zhongTongExpressCompany;

    public IExpressCompany select(int company) {
        return zhongTongExpressCompany;
    }
}
