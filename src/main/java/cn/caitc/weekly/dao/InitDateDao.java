package cn.caitc.weekly.dao;

import cn.caitc.weekly.model.InitDate;

public interface InitDateDao {

    /**
     * 查询指定年月，有多少期周报
     *
     * @param initdate 指定年月
     * @return  int
     */
    int selectCountIssue(InitDate initdate);

    /**
     * 根据指定 年、月、期，查询起止日期
     *
     * @param initdate 指定年月期
     * @return InitDate
     */
    InitDate selectTimeZone(InitDate initdate);

    /**
     * 根据开始时间查这一期的年、月、期
     * @param initdate the initdate
     * @return the init date
     */
    InitDate selectByBeginDate(InitDate initdate);

    /**
     * 根据开始时间更新这一期的结束时间
     */
    void updateEndDate();

    /**
     * 更新合并之后后面的期数
     * @param initdate the initdate
     */
    void updateIssue(InitDate initdate);

    /**
     * 删除合并的那期
     * @param initdate the initdate
     */
    void deleteEvent(InitDate initdate);

    /**
     * 根据指定日期寻找它所在的年、月、期
     *
     * @param initDate 指定日期
     * @return  InitDate
     */
    InitDate selectYearMonthIssueByDate(InitDate initDate);

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