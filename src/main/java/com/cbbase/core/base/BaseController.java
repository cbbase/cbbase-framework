package com.cbbase.core.base;

import java.awt.image.RenderedImage;
import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cbbase.core.common.GlobalManager;
import com.cbbase.core.container.PageContainer;
import com.cbbase.core.container.RestResponse;
import com.cbbase.core.tools.ServletUtil;
import com.cbbase.core.tools.StringUtil;


/**
 * 
 * @author changbo
 * 
 */
public abstract class BaseController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected HttpServletRequest getRequest() {
		return GlobalManager.getRequest();
	}
	
	protected HttpServletResponse getResponse() {
		return GlobalManager.getResponse();
	}
    
    /**
     * 	 初始化分页查询的参数
     * @return
     */
    protected PageContainer getPageContainer(Object param) {
    	PageContainer pageContainer = new PageContainer();
    	pageContainer.setCurrentPage(StringUtil.toInt(getRequest().getParameter("currentPage")));
    	pageContainer.setPageSize(StringUtil.toInt(getRequest().getParameter("pageSize")));
    	pageContainer.setParam(param);
    	return pageContainer;
    }
	
    protected String getClientIp() {
		return ServletUtil.getClientIp(getRequest());
	}
    
    protected void returnFile(File file){
    	ServletUtil.returnFile(getResponse(), file);
    }
    
    protected RestResponse getFail(String msg){
    	return getFail(1, msg);
    }
    
    protected RestResponse getFail(int code, String msg){
    	return new RestResponse(code, msg);
    }
    
    protected RestResponse getSuccess(Object object){
    	if(object instanceof RestResponse) {
        	return (RestResponse) object;
    	}
    	return new RestResponse(object);
    }
    
    protected RestResponse getRestResponse(int code, String msg, Object data) {
    	return new RestResponse(code, msg, data);
    }
    
    protected void returnImg(RenderedImage image, String format){
    	ServletUtil.returnImg(getResponse(), image, format);
    }
    
    protected void returnImg(File imageFile){
    	ServletUtil.returnImg(getResponse(), imageFile);
    }
    
    protected void setAllowCORS(HttpServletResponse response){
		ServletUtil.setAllowCORS(response);
    }
}
