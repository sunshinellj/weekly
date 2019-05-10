package cn.caitc.weekly.model;

import java.util.Date;

public class InitDate {
	// 年
	private String year;

	// 月
	private String month;

	// 期
	private String issue;

	// 开始日期
	private Date startDate;

	// 结束日期
	private Date endDate;

	// 某月有多少期
	private int countIssue;
	
	//说明
	private String commentValue;
		
	//普通日期
	private Date date;
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getCountIssue() {
		return countIssue;
	}

	public void setCountIssue(int countIssue) {
		this.countIssue = countIssue;
	}
	public String getCommentValue() {
		return commentValue;
	}

	public void setCommentValue(String commentValue) {
		this.commentValue = commentValue;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}