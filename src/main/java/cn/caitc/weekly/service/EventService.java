package cn.caitc.weekly.service;

import java.util.Date;
import java.util.List;

import cn.caitc.weekly.model.Event;

public interface EventService {
	/**
	 *插入事件*/
	 boolean insertEvent(Event event);
	
	 List<Event> selectEventList();
	
	/**
	 * 根据起始时间查询事件数量
	 */
	 int selectByStartDate(Date beginDate);

	/**
	 * 修改事件
	 * @param event Event
	 */
	void editEvent(Event event);

	/**
	 * 删除事件
	 * @param beginDate Date
	 */
	void deleteEvent(Date beginDate);
}