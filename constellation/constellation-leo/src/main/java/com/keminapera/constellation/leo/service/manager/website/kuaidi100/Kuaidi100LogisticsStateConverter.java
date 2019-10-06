package com.keminapera.constellation.leo.service.manager.website.kuaidi100;

import com.keminapera.constellation.leo.comon.LogisticsStateEnum;

/**
 * 快递100物流状态转换器实现类
 *
 * @author KeminaPera
 * @date 2019/10/6 16:33
 */
class Kuaidi100LogisticsStateConverter {
    /**
     * 在途
     */
    private static final int ZAITU = 0;
    /**
     * 揽收
     */
    private static final int LANSHOU = 1;
    /**
     * 疑难
     */
    private static final int YINAN = 2;
    /**
     * 签收
     */
    private static final int QIANSHOU = 3;
    /**
     * 退签
     */
    private static final int QUIQIAN = 4;
    /**
     * 派件
     */
    private static final int PAIJIAN = 5;
    /**
     * 退回
     */
    private static final int TUIHUI = 6;

    static int getLocalLogisticsState(int originalState) {
        switch (originalState) {
            case ZAITU:
                return LogisticsStateEnum.TRANSITING.getState();
            case LANSHOU:
                return LogisticsStateEnum.ACCEPTED.getState();
            case YINAN:
                return LogisticsStateEnum.UNKNOW.getState();
            case QIANSHOU:
                return LogisticsStateEnum.FINISHED.getState();
            case QUIQIAN:
                return LogisticsStateEnum.REFUSED.getState();
            case PAIJIAN:
                return LogisticsStateEnum.DELIVERING.getState();
            case TUIHUI:
                return LogisticsStateEnum.BACKING.getState();
            default:
                //todo:自定义参数异常
                throw new RuntimeException("参数异常");
        }
    }
}
