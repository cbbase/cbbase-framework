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
import com.cbbase.core.tools.StringUtil;

/**
 * 全局异常处理
 * @author changbo
 *
 */
public class ExceptionHandler implements HandlerExceptionResolver {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) {
		logger.error("", exception);
		String msg = "请求失败";
		int code = -1;
		if(exception instanceof BusinessException) {
			msg = exception.getMessage();
			code = 1;
		}
		RestResponse resp = new RestResponse(code, msg);
		ServletUtil.returnString(response, JsonUtil.toJson(resp));
        return null;
	}

}
