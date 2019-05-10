package cn.caitc.weekly.service;

import java.text.ParseException;
import java.util.Date;

import cn.caitc.weekly.model.InitDate;

public interface InitDateService {
	/**
	 * 查询指定年月，有多少期周报
	 *
	 * @param year 年
	 * @param month 月
	 * @return int
	 */
	int selectCountIssue(String year, String month);

	/**
	 * 根据指定 年、月、期，查询起止日期
	 *
	 * @param year 年
	 * @param month 月
	 * @param issue 期
	 * @return InitDate
	 */
	InitDate selectTimeZone(String year, String month, String issue);


	/**
	 * 由于插入t_event表的同时要更新t_initdate表，所以将这些操作放在同一事务中处理
	 *
	 * @return String
	 * @throws ParseException
	 */
	String insertAndUpdate(String eventStartDateValue, String eventEndDateValue
			, String eventTextAreaValue) throws ParseException;

	/**
	 * 根据指定日期寻找它所在的年、月、期
	 *
	 * @param date 指定日期
	 * @return  InitDate
	 */
	InitDate selectYearMonthIssueByDate(Date date);

	/**
	 * 根据开始时间查这一期的年、月、期
	 *
	 */
	InitDate selectByBeginDate(Date startDate);

	/**
	 * 根据开始时间更新这一期的结束时间
	 *
	 */
	void updateEndDate();

	/**
	 * 更新合并之后后面的期数
	 *
	 */
	void updateIssue(String year,String month,String issue);

	/**
	 * 删除合并的那期
	 *
	 */
	void deleteEvent(String year,String month,String issue);

	/**
	 * 修改结束日期的更新处理
	 * @param eventEditStartDateValue String
	 * @param eventEditEndDateValue String
	 * @param eventEditTextAreaValue String
	 * @throws ParseException
	 */
	public void editUpdate(String eventStartDateValue,String eventEditStartDateValue,
						   String eventEndDateValue,
						   String eventEditEndDateValue, String eventEditTextAreaValue)
			throws ParseException;
	/**
	 * 修改开始日期的更新处理
	 * @param eventEditStartDateValue String
	 * @param eventEditEndDateValue String
	 * @param eventEditTextAreaValue String
	 * @throws ParseException
	 */
	void editStartUpdate(String eventStartDateValue,String eventEditStartDateValue,
						   String eventEndDateValue,
						   String eventEditEndDateValue, String eventEditTextAreaValue)
			throws ParseException;

	/**
	 * 公共修改更新的方法
	 * @param eventStartDateValue String
	 * @param eventEndDateValue String
	 * @throws ParseException
	 */
	void commentUpdate(String eventStartDateValue,String eventEditStartDateValue,String eventEndDateValue,
					   String eventEditEndDateValue,String eventEditTextAreaValue)
			throws ParseException;
	/**
	 * 删除事件时候的更新和恢复
	 * @param deleteStartDate String
	 * @param deleteEndDate String
	 */
	void deleteAndUpdate(String deleteStartDate,String deleteEndDate) throws ParseException;
	/**
	 * 删除事件时候的恢复结束日期
	 * @param initDate InitDate
	 */
	void updateByStartDate(InitDate initDate);
	/**
	 * 删除事件后的恢复初始化表
	 * @param initDate InitDate
	 */
	void insertInitDate(InitDate initDate);

	/**
	 * 更新删除之后后面的期数
	 * @param initdate InitDate
	 */
	void updateDeleteIssue(InitDate initdate);

}