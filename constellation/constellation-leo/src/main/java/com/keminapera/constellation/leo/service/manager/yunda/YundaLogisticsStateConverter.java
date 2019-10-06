package com.keminapera.constellation.leo.service.manager.yunda;

import com.keminapera.constellation.leo.comon.LogisticsStateEnum;

/**
 * 韵达物流状态转换器
 * 转换成本系统状态{@link LogisticsStateEnum}
 *
 * @author KeminaPera
 * @date 2019/10/6 17:23
 */
class YundaLogisticsStateConverter {
    /**
     * 已揽件
     */
    private static final String GOT = "got";
    /**
     * 下一站
     */
    private static final String NEXT = "next";
    /**
     * 邮出
     */
    private static final String OUT = "out";
    /**
     * 寄入
     */
    private static final String IN = "in";
    /**
     * 派送
     */
    private static final String DELIVER = "deliver";
    /**
     * 签收
     */
    private static final String SIGNED = "signed";

    static int getLocalLogisticsState(String originalState, int logisticsInfoSize) {
        int localState;
        switch (originalState) {
            case GOT:
                localState = processGotState(logisticsInfoSize);
                break;
            case NEXT:
            case OUT:
            case IN:
                localState = LogisticsStateEnum.TRANSITING.getState();
                break;
            case DELIVER:
                localState = LogisticsStateEnum.DELIVERING.getState();
                break;
            case SIGNED:
                localState = LogisticsStateEnum.FINISHED.getState();
                break;
            default:
                throw new RuntimeException("参数异常");
        }
        return localState;
    }

    /**
     * 由于韵达的物流信息中在运输途中也会存在GOT状态，需要做特殊处理
     *
     * @param logisticsInfoSize 物流信息的列表大小
     * @return 物流状态
     */
    private static int processGotState(int logisticsInfoSize) {
        if (logisticsInfoSize > 1) {
            return LogisticsStateEnum.TRANSITING.getState();
        } else {
            return LogisticsStateEnum.ACCEPTED.getState();
        }
    }
}
