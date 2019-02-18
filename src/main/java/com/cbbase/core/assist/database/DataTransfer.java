package com.cbbase.core.assist.database;

public class DataTransfer {
	
	//导出数据参数
	private String fromDataBase;
	private String fromQuerySql;
	private String fromFile;

	//ftp获取导出文件参数
	private String fromServerIp;
	private String fromServerPort;
	private String fromServerPwd;
	private String fromFilePath;
	
	//导入数据参数
	private String toDataBase;
	private String toInsertSql;
	private String toFile;
	
	public boolean exportFile(){
		
		
		return true;
	}
	
	public boolean downloadFile(){
		
		return true;
	}

	public boolean importFile(){
		
		return true;
	}
	
	public boolean tansferData(){
		
		return true;
	}

	public String getFromDataBase() {
		return fromDataBase;
	}

	public void setFromDataBase(String fromDataBase) {
		this.fromDataBase = fromDataBase;
	}

	public String getFromQuerySql() {
		return fromQuerySql;
	}

	public void setFromQuerySql(String fromQuerySql) {
		this.fromQuerySql = fromQuerySql;
	}

	public String getFromFile() {
		return fromFile;
	}

	public void setFromFile(String fromFile) {
		this.fromFile = fromFile;
	}

	public String getFromServerIp() {
		return fromServerIp;
	}

	public void setFromServerIp(String fromServerIp) {
		this.fromServerIp = fromServerIp;
	}

	public String getFromServerPort() {
		return fromServerPort;
	}

	public void setFromServerPort(String fromServerPort) {
		this.fromServerPort = fromServerPort;
	}

	public String getFromServerPwd() {
		return fromServerPwd;
	}

	public void setFromServerPwd(String fromServerPwd) {
		this.fromServerPwd = fromServerPwd;
	}

	public String getFromFilePath() {
		return fromFilePath;
	}

	public void setFromFilePath(String fromFilePath) {
		this.fromFilePath = fromFilePath;
	}

	public String getToDataBase() {
		return toDataBase;
	}

	public void setToDataBase(String toDataBase) {
		this.toDataBase = toDataBase;
	}

	public String getToInsertSql() {
		return toInsertSql;
	}

	public void setToInsertSql(String toInsertSql) {
		this.toInsertSql = toInsertSql;
	}

	public String getToFile() {
		return toFile;
	}

	public void setToFile(String toFile) {
		this.toFile = toFile;
	}
	
}
