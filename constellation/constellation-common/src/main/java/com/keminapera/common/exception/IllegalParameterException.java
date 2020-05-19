package com.keminapera.common.exception;

import java.util.List;

/**
 * 非法的参数异常
 *
 * @author KeminaPera
 * @date 2019/10/4 19:01
 */
public class IllegalParameterException extends RuntimeException {

    public IllegalParameterException(String message) {
        super(message);
    }

    public IllegalParameterException(List<?> keyList, List<?> valueList) {
        super();
    }
}
