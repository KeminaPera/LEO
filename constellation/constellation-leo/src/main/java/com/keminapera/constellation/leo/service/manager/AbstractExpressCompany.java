package com.keminapera.constellation.leo.service.manager;

import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.pojo.LogisticsInfo;

import java.util.List;

/**
 * 快递信息查询父接口的抽象实现类
 *
 * @author KeminaPera
 * @date 2019/10/2 8:47
 */
public class AbstractExpressCompany implements IExpressCompany {
    @Override
    public LogisticsVo queryLogistics(String number) {
        return null;
    }

    @Override
    public List<LogisticsInfo> queryLogisticsInfoList(String number) {
        return null;
    }
}
