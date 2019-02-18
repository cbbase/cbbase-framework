package com.cbbase.core.container;


/**
 *	 分页容器
 * @author changbo
 * 
 */
public class PageContainer extends RestResponse {
	

	public static final int DEFAULT_PAGE_SIZE = 10;
	
	private int pageCount;//页面总数
	private int rowCount;//数据总数
	private int currentPage;//当前页
	private int pageSize;//每页行数
	
	private int startRow;//开始行数
	private int endRow;//结束行数
	private String sortType;//排序方式：ASC升序，DESC降序
	private String sortField;//排序字段(数据库列)
	
	private Object param;//查询参数(查询结果为父类中的data字段)
	
	public PageContainer(){
		this.pageCount = 0;
		this.rowCount = 0;
		this.currentPage = 0;
		this.pageSize = DEFAULT_PAGE_SIZE;
	}

	public int getPageCount() {
		this.pageCount = rowCount%pageSize == 0 ? rowCount/pageSize : rowCount/pageSize + 1;
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public void setData(Object data) {
		super.setData(data);
		pageCount = getPageCount();
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public int getStartRow() {
		if(this.currentPage > 0){
			this.startRow = (this.currentPage-1)*this.pageSize;
		}else{
			this.startRow = (this.currentPage)*this.pageSize;
		}
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		this.endRow = getStartRow()+pageSize;
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}


}
