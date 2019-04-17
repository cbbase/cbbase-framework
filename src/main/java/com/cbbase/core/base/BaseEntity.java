package com.cbbase.core.base;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * id:主键
 * @author changbo
 * 
 */
public class BaseEntity implements Cloneable {
	
	private Long id;
	private Long createId;
	private String createName;
	private String createTime;
	private Long updateId;
	private String updateName;
	private String updateTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
