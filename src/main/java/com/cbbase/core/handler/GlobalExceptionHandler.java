package com.cbbase.core.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cbbase.core.common.BusinessException;
import com.cbbase.core.container.RestResponse;

/**
 * 全局异常处理
 * @author changbo
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
    /**
     * 未知异常
     * 
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RestResponse handleException(Exception ex) {
    	logger.error("handleException:", ex);
        return new RestResponse(1, null, "请求失败");
    }

    /**
     * 处理所有业务异常
     * 
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public RestResponse handleBusinessException(BusinessException e) {
    	logger.error("handleBusinessException:", e);
        return new RestResponse(e.getErrorCode(), null, e.getMessage());
    }

}
