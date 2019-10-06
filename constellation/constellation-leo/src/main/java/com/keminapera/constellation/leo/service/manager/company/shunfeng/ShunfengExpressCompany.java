package com.keminapera.constellation.leo.service.manager.company.shunfeng;

import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.service.manager.AbstractExpressCompany;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * 顺丰快递接口实现类
 *
 * @author KeminaPera
 * @date 2019/10/5 17:20
 */
@Component
public class ShunfengExpressCompany extends AbstractExpressCompany implements IShunfengExpressCompany {

    @Override
    public LogisticsVo queryLogistics(@NotNull String number) {
        return null;
    }

    @Override
    protected String buildRequestParamAndGetResult(String number) {
        return null;
    }
}
