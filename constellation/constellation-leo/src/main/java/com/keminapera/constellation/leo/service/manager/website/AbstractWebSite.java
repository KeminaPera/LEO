package com.keminapera.constellation.leo.service.manager.website;

import com.keminapera.constellation.leo.service.manager.AbstractExpressCompany;

/**
 * 第三方物流网站抽象类
 *
 * @author KeminaPera
 * @date 2019/10/5 15:42
 */
public abstract class AbstractWebSite extends AbstractExpressCompany implements IWebSite {
    /**
     * 构造请求的参数并发送请求获取结果
     *
     * @param number  快递单号
     * @param company 快递公司号
     * @return 获取到的结果
     */
    protected abstract String buildRequestParamAndGetResult(String number, int company);

    @Override
    protected String buildRequestParamAndGetResult(String number) {
        //todo:后期如果能实现快递单号的识别就能实现该功能
        return buildRequestParamAndGetResult(number, -1);
    }
}
