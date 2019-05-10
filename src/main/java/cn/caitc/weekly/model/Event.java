package cn.caitc.weekly.model;

import java.util.Date;

public class Event {
	//开始日期
	private Date beginDate;
	
	//结束日期
	private Date endDate;
	
	//说明
	private String commentValue;

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCommentValue() {
		return commentValue;
	}

	public void setCommentValue(String commentValue) {
		this.commentValue = commentValue;
	}
	
	
}
