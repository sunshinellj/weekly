package cn.caitc.weekly.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.caitc.weekly.dao.EventDao;
import cn.caitc.weekly.model.Event;
import cn.caitc.weekly.service.EventService;

@Service
public class EventServiceImpl implements EventService {
	@Autowired
	private EventDao eventDao;
	
	/**
	 *插入事件*/
	public boolean insertEvent(Event event){
		return eventDao.insertEvent(event);
	}
	
	public List<Event> selectEventList(){
		return eventDao.selectEventList();
	}
	
	/**
	 * 根据起始时间查询事件数量
	 */
	public int selectByStartDate(Date beginDate){
		Event event = new Event();
		event.setBeginDate(beginDate);
		return eventDao.selectByStartDate(event);
	}

	/**
	 * 修改事件
	 * @param event Event
	 */
	public void editEvent(Event event){

		 eventDao.editEvent(event);
	}

	/**
	 * 删除事件
	 * @param beginDate Date
	 */
	public void deleteEvent(Date beginDate){
		Event event = new Event();
		event.setBeginDate(beginDate);
		eventDao.deleteEvent(event);
	}
}