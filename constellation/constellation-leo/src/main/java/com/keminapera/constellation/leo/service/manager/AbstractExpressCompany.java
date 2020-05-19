package com.keminapera.constellation.leo.service.manager;

/**
 * 快递信息查询父接口的抽象实现类
 *
 * @author KeminaPera
 * @date 2019/10/2 8:47
 */
public abstract class AbstractExpressCompany implements IExpressCompany {

    /**
     * 构造请求的参数并发送请求获取结果
     *
     * @param number 快递单号
     * @return 获取到的结果
     */
    protected abstract String buildRequestParamAndGetResult(String number);
}
