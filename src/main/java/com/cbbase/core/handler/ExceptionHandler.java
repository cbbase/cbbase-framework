package com.cbbase.core.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.cbbase.core.common.BusinessException;
import com.cbbase.core.container.RestResponse;
import com.cbbase.core.tools.JsonUtil;
import com.cbbase.core.tools.ServletUtil;

/**
 * 全局异常处理
 * @author changbo
 *
 */
public class ExceptionHandler implements HandlerExceptionResolver {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		logger.error("", ex);
		RestResponse resp = new RestResponse();
        if(ex instanceof BusinessException) {
        	resp.setCode(1);
        	resp.setMsg(ex.getMessage());
        }else {
        	resp.setCode(-1);
        	resp.setMsg("请求失败");
        }
		ServletUtil.returnString(response, JsonUtil.toJson(resp));
        return null;
	}

}
