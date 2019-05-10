package cn.caitc.weekly.service.impl;

import cn.caitc.weekly.constant.CommonConstant;
import cn.caitc.weekly.dao.EventDao;
import cn.caitc.weekly.dao.InitDateDao;
import cn.caitc.weekly.model.Event;
import cn.caitc.weekly.model.InitDate;
import cn.caitc.weekly.service.InitDateService;
import cn.caitc.weekly.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Administrator
 */
@Service
public class InitDateServiceImpl implements InitDateService {
    @Autowired
    InitDateDao initDateDao;

    @Autowired
    EventDao eventDao;

    public int selectCountIssue(String year, String month) {
        InitDate initDate = new InitDate();
        initDate.setYear(year);
        initDate.setMonth(month);
        return initDateDao.selectCountIssue(initDate);
    }

    /**
     * 根据指定日期寻找它所在的年、月、期
     *
     * @param year String
     * @return InitDate
     */
    public InitDate selectTimeZone(String year, String month, String issue) {
        InitDate initDate = new InitDate();
        initDate.setYear(year);
        initDate.setMonth(month);
        initDate.setIssue(issue);
        return initDateDao.selectTimeZone(initDate);
    }

    /**
     * 根据开始时间查这一期的年、月、期
     */
    public InitDate selectByBeginDate(Date startDate) {
        InitDate initDate = new InitDate();
        initDate.setStartDate(startDate);
        return initDateDao.selectByBeginDate(initDate);
    }

    /**
     * 根据开始时间更新这一期的结束时间
     */
    public void updateEndDate() {
        // InitDate initDate = new InitDate();
        // initDate.setStartDate(startDate);
        initDateDao.updateEndDate();
    }

    /**
     * 更新合并之后后面的期数
     */
    public void updateIssue(String year, String month, String issue) {
        InitDate initDate = new InitDate();
        initDate.setYear(year);
        initDate.setMonth(month);
        initDate.setIssue(issue);
        initDateDao.updateIssue(initDate);
    }

    /**
     * 删除合并的那期
     */
    public void deleteEvent(String year, String month, String issue) {
        InitDate initDate = new InitDate();
        initDate.setYear(year);
        initDate.setMonth(month);
        initDate.setIssue(issue);
        initDateDao.deleteEvent(initDate);
    }

    /**
     * 删除事件时候的恢复结束日期
     *
     * @param initDate InitDate
     */
    public void updateByStartDate(InitDate initDate) {
        initDateDao.updateByStartDate(initDate);
    }

    /**
     * 删除事件后的恢复初始化表
     *
     * @param initDate InitDate
     */
    public void insertInitDate(InitDate initDate) {
        initDateDao.insertInitDate(initDate);
    }

    /**
     * 更新删除之后后面的期数
     *
     * @param initdate InitDate
     */
    public void updateDeleteIssue(InitDate initdate) {
        initDateDao.updateIssue(initdate);
    }

    /**
     * 由于插入t_event表的同时要更新t_initdate表，所以将这些操作放在同一事务中处理
     *
     * @param eventStartDateValue 开始时间
     * @param eventEndDateValue   结束时间
     * @param eventTextAreaValue  事件说明
     * @return String
     * @throws ParseException
     */

    public String insertAndUpdate(String eventStartDateValue,
                                  String eventEndDateValue, String eventTextAreaValue)
            throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(
                CommonConstant.DATE_FORMAT_YYYYMMDD);
        Calendar cal = Calendar.getInstance();
        String message = "";
        Event event = new Event();
        InitDate initDate = new InitDate();

        Date beginDate = format.parse(eventStartDateValue);
        Date endDate = format.parse(eventEndDateValue);
        // 根据起始时间得到年、月、期
        initDate.setStartDate(beginDate);
        initDate = initDateDao.selectByBeginDate(initDate);

        if (initDate != null) {
            event.setBeginDate(beginDate);
            event.setEndDate(endDate);
            event.setCommentValue(eventTextAreaValue);
            eventDao.insertEvent(event);

            initDate.setStartDate(beginDate);
            initDate = initDateDao.selectByBeginDate(initDate);
            // 得到修改后的开始日期、结束日期在这一年的周数
            cal.setFirstDayOfWeek(Calendar.MONDAY);
            cal.setTime(format.parse(eventStartDateValue));
            int weekCount = cal.get(Calendar.WEEK_OF_YEAR);

            cal.setTime(format.parse(eventEndDateValue));
            int nextWeekCount = cal.get(Calendar.WEEK_OF_YEAR);

            String year = initDate.getYear();
            String month = initDate.getMonth();
            // 得到下一期的年、月、期
            Date nextEventDate = CommonUtil.getNextWeekDay((format
                    .parse(eventStartDateValue)));
            String[] nextEventString = CommonUtil.getYearMonthWeek(nextEventDate);
            String nextMonth = nextEventString[1];
            String nextIssue = nextEventString[2];
            // 得到下一期的期数个数
            int issueCount = CommonUtil.mondayCount(year, nextMonth);

            // 不管起止日期跨周还是跨月开始时间的这条记录都要更新
            initDateDao.updateEndDate();

            // 判断起止日期是否在同一周
            if (weekCount != nextWeekCount) {

                initDate.setYear(year);
                initDate.setMonth(nextMonth);
                initDate.setIssue(nextIssue);
                // 先在初始化表中删除下一期
                initDateDao.deleteEvent(initDate);

                InitDate newInitDate = new InitDate();
                // 判断是否跨月,跨月则下个月的期数只减1再更新期数，不跨月则这个月的期数减2再更新期数
                if (month.equals(nextMonth)) {
                    for (int index = 0; index < issueCount - 2; index++) {
                        newInitDate.setYear(year);
                        newInitDate.setMonth(nextMonth);
                        newInitDate.setIssue(String.valueOf((Integer
                                .parseInt(nextIssue) + index + 1)));
                        initDateDao.updateIssue(newInitDate);
                    }
                } else {
                    for (int index = 0; index < issueCount - 1; index++) {
                        newInitDate.setYear(year);
                        newInitDate.setMonth(nextMonth);
                        newInitDate.setIssue(String.valueOf((Integer
                                .parseInt(nextIssue) + index + 1)));
                        initDateDao.updateIssue(newInitDate);
                    }
                }
            }
        } else {
            message = "event.is.combine";
        }
        return message;
    }

    /**
     * 修改结束日期的更新处理
     *
     * @param eventStartDateValue     修改前的开始时间
     * @param eventEditStartDateValue 修改前的结束时间
     * @param eventEndDateValue       修改后的开始时间
     * @param eventEditEndDateValue   修改后的结束时间
     * @param eventEditTextAreaValue  事件说明
     * @throws ParseException
     */
    public void editUpdate(String eventStartDateValue, String eventEditStartDateValue, String eventEndDateValue,
                           String eventEditEndDateValue, String eventEditTextAreaValue)
            throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(
                CommonConstant.DATE_FORMAT_YYYYMMDD);

        Event event = new Event();
        Date beginDate = format.parse(eventEditStartDateValue);
        Date endDate = format.parse(eventEditEndDateValue);
        //先删除原来的的事件再插入新的记录
        event.setBeginDate(format.parse(eventEditStartDateValue));
        eventDao.deleteEvent(event);

        event.setBeginDate(beginDate);
        event.setEndDate(endDate);
        event.setCommentValue(eventEditTextAreaValue);
        //
        eventDao.insertEvent(event);
        //调用公共的更新方法
        commentUpdate(eventStartDateValue, eventEditStartDateValue, eventEndDateValue, eventEditEndDateValue, eventEditTextAreaValue);

    }

    /**
     * 修改结束日期的更新处理
     *
     * @param eventStartDateValue     修改前的开始时间
     * @param eventEditStartDateValue 修改前的结束时间
     * @param eventEndDateValue       修改后的开始时间
     * @param eventEditEndDateValue   修改后的结束时间
     * @param eventEditTextAreaValue  事件说明
     * @throws ParseException
     */
    public void editStartUpdate(String eventStartDateValue, String eventEditStartDateValue,
                                String eventEndDateValue,
                                String eventEditEndDateValue, String eventEditTextAreaValue)
            throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(
                CommonConstant.DATE_FORMAT_YYYYMMDD);
        Event event = new Event();
        InitDate initDate = new InitDate();
        Date beginDate = format.parse(eventEditStartDateValue);
        Date endDate = format.parse(eventEditEndDateValue);
        //先删除原来的的事件再插入新的记录
        event.setBeginDate(format.parse(eventStartDateValue));
        eventDao.deleteEvent(event);
        // 根据起始时间得到年、月、期
        initDate.setStartDate(beginDate);

        event.setBeginDate(beginDate);
        event.setEndDate(endDate);
        event.setCommentValue(eventEditTextAreaValue);
        eventDao.insertEvent(event);
        //调用公共的更新方法
        commentUpdate(eventStartDateValue, eventEditStartDateValue, eventEndDateValue, eventEditEndDateValue, eventEditTextAreaValue);

    }

    /**
     * 公共的更新方法
     *
     * @param eventStartDateValue     修改前的开始时间
     * @param eventEditStartDateValue 修改前的结束时间
     * @param eventEndDateValue       修改后的开始时间
     * @param eventEditEndDateValue   修改后的结束时间
     * @param eventEditTextAreaValue  事件说明
     * @throws ParseException
     */
    public void commentUpdate(String eventStartDateValue, String eventEditStartDateValue, String eventEndDateValue,
                              String eventEditEndDateValue, String eventEditTextAreaValue)
            throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(
                CommonConstant.DATE_FORMAT_YYYYMMDD);
        InitDate initDate = new InitDate();
        Calendar cal = Calendar.getInstance();
        Date beginDate = format.parse(eventStartDateValue);
        initDate.setStartDate(beginDate);
        initDate = initDateDao.selectByBeginDate(initDate);
        //得到修改后的开始日期、结束日期在这一年的周数
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(format.parse(eventEditStartDateValue));
        int weekCount = cal.get(Calendar.WEEK_OF_YEAR);

        cal.setTime(format.parse(eventEditEndDateValue));
        int nextWeekCount = cal.get(Calendar.WEEK_OF_YEAR);

        //修改之前开始日期、结束日期在这一年的周数
        cal.setTime(format.parse(eventStartDateValue));
        int editWeekCount = cal.get(Calendar.WEEK_OF_YEAR);
        cal.setTime(format.parse(eventEndDateValue));
        int editNextWeekCount = cal.get(Calendar.WEEK_OF_YEAR);

        String year = initDate.getYear();
        String month = initDate.getMonth();
        String issue = initDate.getIssue();
        // 得到下一期的年、月、期
        Date nextEventDate = CommonUtil.getNextWeekDay((format
                .parse(eventStartDateValue)));
        String[] nextEventString = CommonUtil.getYearMonthWeek(nextEventDate);
        String nextMonth = nextEventString[1];
        String nextIssue = nextEventString[2];
        // 得到下一期的期数个数
        int issueCount = CommonUtil.mondayCount(year, nextMonth);

        // 不管起止日期跨周还是跨月开始时间的这条记录都要更新
        initDateDao.updateEndDate();

        //日期从小改到大，要删除一条记录再更新
        if (weekCount < editWeekCount || nextWeekCount > editNextWeekCount) {
            //修改结束日期
            if (nextWeekCount > editNextWeekCount) {
                initDate.setYear(year);
                initDate.setMonth(nextMonth);
                initDate.setIssue(nextIssue);
                // 先在初始化表中删除下一期
                initDateDao.deleteEvent(initDate);

                InitDate newInitDate = new InitDate();
                // 判断是否跨月,跨月则下个月的期数只减1再更新期数，不跨月则这个月的期数减2再更新期数
                if (month.equals(nextMonth)) {
                    for (int index = 0; index < issueCount - 2; index++) {
                        newInitDate.setYear(year);
                        newInitDate.setMonth(nextMonth);
                        newInitDate.setIssue(String.valueOf((Integer
                                .parseInt(nextIssue) + index + 1)));
                        initDateDao.updateIssue(newInitDate);
                    }
                } else {
                    for (int index = 0; index < issueCount - 1; index++) {
                        newInitDate.setYear(year);
                        newInitDate.setMonth(nextMonth);
                        newInitDate.setIssue(String.valueOf((Integer
                                .parseInt(nextIssue) + index + 1)));
                        initDateDao.updateIssue(newInitDate);
                    }
                }
            }
            //修改开始日期
            else {
                initDate.setYear(year);
                initDate.setMonth(month);
                initDate.setIssue(issue);
                // 先在初始化表中删除本期在修改上期的结束日期
                initDateDao.deleteEvent(initDate);
                InitDate updateInitDate = new InitDate();
                Date editBeginDate = format.parse(eventEditStartDateValue);
                Date editEndDate = format.parse(eventEditEndDateValue);
                updateInitDate.setStartDate(editBeginDate);
                updateInitDate = initDateDao.selectByBeginDate(updateInitDate);
                updateInitDate.setEndDate(editEndDate);
                updateInitDate.setCommentValue(eventEditTextAreaValue);
                initDateDao.updateByStartDate(updateInitDate);

                editBeginDate = CommonUtil.getNextWeekDay(beginDate);
                //更新修改之后的期数
                for (int index = 0; index <=issueCount - Integer.parseInt(nextIssue); index++) {
                    updateInitDate.setYear(year);
                    updateInitDate.setMonth(nextMonth);
                    updateInitDate.setIssue(String.valueOf((Integer
                            .parseInt(nextIssue) + index)));

                    updateInitDate.setStartDate(editBeginDate);
                    initDateDao.updateIssue(updateInitDate);
                    editBeginDate = CommonUtil.getNextWeekDay(editBeginDate);
                }
            }
        }
        //日期从大改到小，要插入一条数据再更新
        else {
            // 修改结束日期
            if (nextWeekCount < editNextWeekCount) {
                InitDate updateInitDate = new InitDate();
                Date editBeginDate = format.parse(eventStartDateValue);
                Date editEndDate = format.parse(eventEditEndDateValue);
                updateInitDate.setStartDate(editBeginDate);
                updateInitDate = initDateDao.selectByBeginDate(updateInitDate);
                updateInitDate.setEndDate(editEndDate);
                updateInitDate.setCommentValue(eventEditTextAreaValue);
                // 先在初始化表中修改本期的结束日期
                initDateDao.updateByStartDate(updateInitDate);
                //获得修改前下一期的日期
                editBeginDate = CommonUtil.getNextWeekDay(nextEventDate);
                //更新修改之后的期数
                for (int index = 0; index < issueCount - Integer.parseInt(nextIssue); index++) {
                    updateInitDate.setYear(year);
                    updateInitDate.setMonth(nextMonth);
                    updateInitDate.setIssue(String.valueOf((Integer
                            .parseInt(nextIssue) + index + 1)));

                    updateInitDate.setStartDate(editBeginDate);
                    initDateDao.updateDeleteIssue(updateInitDate);
                    editBeginDate = CommonUtil.getNextWeekDay(editBeginDate);
                }

                //插入下一期
                InitDate insertInitDate = new InitDate();
                insertInitDate.setYear(year);
                insertInitDate.setMonth(nextMonth);
                insertInitDate.setIssue(nextIssue);
                Date nextStartDate = CommonUtil.getNextWeekDay(format.parse(eventEditStartDateValue));
                //获得下期的初始开始和结束日期
                insertInitDate.setStartDate(nextStartDate);
                cal.setTime(nextStartDate);
                cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                insertInitDate.setEndDate(cal.getTime());

                insertInitDate.setCommentValue("");
                initDateDao.insertInitDate(insertInitDate);
            }
            //修改开始日期
            else if (weekCount > editWeekCount) {
                InitDate updateInitDate = new InitDate();
                updateInitDate.setStartDate(beginDate);

                cal.setTime(beginDate);
                cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                updateInitDate.setEndDate(cal.getTime());
                updateInitDate.setCommentValue("");
                // 先在初始化表中修改本期的结束日期
                initDateDao.updateByStartDate(updateInitDate);

                //更新修改之后的期数
                for (int index = 0; index <issueCount - Integer.parseInt(nextIssue); index++) {
                    updateInitDate.setYear(year);
                    updateInitDate.setMonth(nextMonth);
                    updateInitDate.setIssue(String.valueOf((Integer
                            .parseInt(nextIssue) + index +1)));
                    beginDate = CommonUtil.getNextWeekDay(nextEventDate);
                    updateInitDate.setStartDate(beginDate);
                    initDateDao.updateDeleteIssue(updateInitDate);

                }

                //插入下一期
                Date editBeginDate = format.parse(eventEditStartDateValue);
                Date editEndDate = format.parse(eventEditEndDateValue);
                InitDate insertInitDate = new InitDate();
                insertInitDate.setYear(year);
                insertInitDate.setMonth(nextMonth);
                insertInitDate.setIssue(nextIssue);
                insertInitDate.setStartDate(editBeginDate);
                insertInitDate.setEndDate(editEndDate);
                insertInitDate.setCommentValue(eventEditTextAreaValue);
                initDateDao.insertInitDate(insertInitDate);
            }
        }
    }

    /**
     * 删除事件时候的更新和恢复
     *
     * @param deleteStartDate String
     * @param deleteEndDate   String
     */
    public void deleteAndUpdate(String deleteStartDate, String deleteEndDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(
                CommonConstant.DATE_FORMAT_YYYYMMDD);

        Event event = new Event();
        InitDate initDate = new InitDate();
        Calendar cal = Calendar.getInstance();
        //删除事件
        Date beginDate = format.parse(deleteStartDate);
        event.setBeginDate(format.parse(deleteStartDate));
        eventDao.deleteEvent(event);

        initDate.setStartDate(beginDate);
        initDate = initDateDao.selectByBeginDate(initDate);

        // 得到删除事件的开始日期、结束日期在这一年的周数
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(format.parse(deleteStartDate));

        int weekCount = cal.get(Calendar.WEEK_OF_YEAR);
        cal.setTime(format.parse(deleteEndDate));
        int nextWeekCount = cal.get(Calendar.WEEK_OF_YEAR);

        //获得初始化结束的日期
        cal.setTime(format.parse(deleteStartDate));
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        initDate.setEndDate(cal.getTime());
        initDate.setCommentValue("");
        String year = initDate.getYear();
        // 得到下一期的年、月、期
        Date nextEventDate = CommonUtil.getNextWeekDay((format
                .parse(deleteStartDate)));
        String[] nextEventString = CommonUtil.getYearMonthWeek(nextEventDate);
        String nextMonth = nextEventString[1];
        String nextIssue = nextEventString[2];
        // 得到下一期的期数个数
        int issueCount = CommonUtil.mondayCount(year, nextMonth);

        // 不管起止日期跨周还是跨月开始时间的这条记录都要更新
        initDateDao.updateByStartDate(initDate);
        // 判断起止日期是否在同一周
        InitDate initDateNew = new InitDate();
        String[] nextDate = CommonUtil.weekToDayConver(Integer.parseInt(year),
                Integer.parseInt(nextMonth), Integer.parseInt(nextIssue), format);
        initDateNew.setStartDate(format.parse(nextDate[0]));
        initDateNew.setEndDate(format.parse(nextDate[1]));
        if (weekCount != nextWeekCount) {

            initDateNew.setYear(year);
            initDateNew.setMonth(nextMonth);
            initDateNew.setIssue(nextIssue);
            // 先在初始化表中插入下一期
            initDateDao.insertInitDate(initDateNew);

            Date updateStartDate = CommonUtil.getNextWeekDay(format.parse(nextDate[0]));

            InitDate updateInitDate = new InitDate();
            updateInitDate.setStartDate(updateStartDate);
            updateInitDate = initDateDao.selectByBeginDate(updateInitDate);

            // 不管是否跨月，都是在插入后的那一期开始更新期数

            for (int index = 0; index < issueCount - Integer.parseInt(nextIssue); index++) {
                updateInitDate.setYear(year);
                updateInitDate.setMonth(nextMonth);
                updateInitDate.setIssue(String.valueOf((Integer
                        .parseInt(nextIssue) + index + 1)));
                initDateDao.updateDeleteIssue(updateInitDate);
                updateStartDate = CommonUtil.getNextWeekDay(updateStartDate);
                updateInitDate.setStartDate(updateStartDate);
            }
        }
    }

    /**
     * 根据指定日期寻找它所在的年、月、期
     *
     * @param date Date
     * @return InitDate
     */

    public InitDate selectYearMonthIssueByDate(Date date) {
        InitDate initdate = new InitDate();
        initdate.setDate(date);
        return initDateDao.selectYearMonthIssueByDate(initdate);
    }

}