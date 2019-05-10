package cn.caitc.weekly.service;

import java.util.List;

import cn.caitc.weekly.model.Report;

public interface ReportService {
	/**
	 * 插入周报内容
	 * 
	 * @param report 周报
	 * */
	public void insertContent(Report report);

	/**
	 * 查询出周报内容（含有二级提纲）
	 */
	public List<Report> selectReportContent(String fileNo);
	/**
	 * 查询出周报内容（不含二级提纲）*/
	public List<Report> selectContentNoPoint(String fileNo);
	
	/**
	 * 更新周报内容*/
	public void updateContent(Report report);
	
	
	/**
	 * 更新周报内容*/
	public void updateContentNoPoint(Report report);
	
	/**删除周报内容*/
	public void deleteContent(String fileNo);
	

}