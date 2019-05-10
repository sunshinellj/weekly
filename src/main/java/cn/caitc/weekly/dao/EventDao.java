package cn.caitc.weekly.dao;

import cn.caitc.weekly.model.Event;

import java.util.List;

public interface EventDao {
    //插入事件
    boolean insertEvent(Event event);

    /**
     * 查询事件
     *
     * @return List<Event>
     */
    List<Event> selectEventList();

    /**
     * 根据起始时间查询事件数量
     *
     * @param event Event
     * @return int
     */
    int selectByStartDate(Event event);

    /**
     * 修改事件
     * @param event Event
     */
    void editEvent(Event event);

    /**
     * 删除事件
     * @param event Event
     */
    void deleteEvent(Event event);
}