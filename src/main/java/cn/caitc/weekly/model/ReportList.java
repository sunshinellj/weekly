package cn.caitc.weekly.model;

import java.util.Date;

public class ReportList {

	// 周报番号
	private String fileNo;

	// 周报名称
	private String fileName;

	// 部门号
	private String groupId;

	// 部门名称
	private String groupName;

	// 周报人号
	private String reporterId;

	// 周报时间
	private Date reportTime;

	// 开始时间 用于保存查询的起始时间
	private Date startDate;

	// 结束时间 保存查询的结束时间
	private Date endDate;

	// 周报所用模板的版本号
	private int versionNo;

	// 周报人名
	private String reporter;

	// 周报年份
	private String year;

	// 周报月份
	private String month;

	// 周报期数
	private String issue;

	// 更新着
	private String updateUser;

	// 更新时间
	private Date updateDate;

	// 番号数组
	private String[] fileNos;

	// 显示顺序
	private String orderNo;

	// 模版名称
	private String tempName;

	// 模板id
	private String tempId;
	
	//周报日期
	private int rptTime;

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getReporterId() {
		return reporterId;
	}

	public void setReporterId(String reporterId) {
		this.reporterId = reporterId;
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

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

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

	public int getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(int versionNo) {
		this.versionNo = versionNo;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String[] getFileNos() {
		return fileNos;
	}

	public void setFileNos(String[] fileNos) {
		this.fileNos = fileNos;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public int getRptTime() {
		return rptTime;
	}

	public void setRptTime(int rptTime) {
		this.rptTime = rptTime;
	}
	
}
