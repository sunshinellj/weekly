package cn.caitc.weekly.controller;

import cn.caitc.weekly.constant.CommonConstant;
import cn.caitc.weekly.model.*;
import cn.caitc.weekly.service.*;
import cn.caitc.weekly.util.CommonUtil;
import cn.caitc.weekly.vo.ReportVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ReportListController {

    @Autowired
    private ReportListService reportListService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private DeptListService deptListService;

    @Autowired
    private CombineReportService combineReportService;

    @Autowired
    private EventService eventService;

    @Autowired
    private InitDateService initDateService;

    @RequestMapping("/init_reportList")
    public ModelAndView init_reportList(HttpSession httpSession)
            throws ParseException {

        // 将session对象取过来，在后台取session
        UserInfo userInfo = (UserInfo) httpSession
                .getAttribute(CommonConstant.SESSION_USER_INFO);
        String userId = userInfo.getUserId();
        String userName = userInfo.getUserName();
        String groupId = userInfo.getGroupId();
        String authority = userInfo.getAuthority();

        // 获取当前日期的上一周
        // 写周报的日期一般为周一，要获取上周的周一
        Calendar cal=Calendar.getInstance();
        int weekNow=cal.get(cal.WEEK_OF_MONTH);
        int weekLast=weekNow-1;
        cal.set(Calendar.WEEK_OF_MONTH,weekLast);

        String[] dateInit = CommonUtil.getYearMonthWeek(cal.getTime());
        String year = dateInit[0];
        String month = dateInit[1];
        String issue = dateInit[2];

        String timeZone = CommonUtil.weekToDay(Integer.parseInt(year),
                Integer.parseInt(month), Integer.parseInt(issue));

        String[] timeSplit = timeZone.split("[~]");
        String startDate = timeSplit[0];
        String endDate = timeSplit[1];
        SimpleDateFormat format = new SimpleDateFormat(
                CommonConstant.DATE_FORMAT_YYYYMMDD);
        Date dStart = format.parse(startDate);
        Date dEnd = format.parse(endDate);


        List<ReportList> reportList = reportListService.doSelectReportList(
                groupId, userId, userName, dStart, dEnd);

        List<DeptList> deptList = deptListService.selectDeptList();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("deptList", deptList);
        map.put("reportList", reportList);
        map.put("userName", userName);
        map.put("reporter", userName);
        map.put("userId", userId);
        map.put("groupId", groupId);
        map.put("reporterId", userId);
        map.put("authority", authority);
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        return new ModelAndView("/reportList", map);
    }

    @RequestMapping("/doSearch_reportList")
    public ModelAndView searchCondition(HttpSession httpSession,
                                        ReportVo reportVo, String msgTemp) throws ParseException {
        // 将查询条件保存在session 中
        httpSession.setAttribute("reportVo", reportVo);
        Map<String, Object> map = doSearch(httpSession, reportVo, msgTemp);
        return new ModelAndView("/reportList", map);
    }

    /**
     * 要返回到最近的查询结果页时，需要进行的处理
     *
     * @param httpSession HttpSession
     * @param msgTemp     String
     * @return ModelAndView
     * @throws ParseException
     */
    @RequestMapping("/returnSearch_reportList")
    public ModelAndView returnSearch(HttpSession httpSession, String msgTemp)
            throws ParseException {
        ReportVo reportVo = (ReportVo) httpSession.getAttribute("reportVo");
        if (reportVo != null) {
            Map<String, Object> map = doSearch(httpSession, reportVo, msgTemp);
            return new ModelAndView("/reportList", map);
        } else {
            return new ModelAndView("redirect:/init_reportList");
        }

    }

    /**
     * 查询方法：只处理查询过程，不与查询条件的来源和查询结果的出处相关
     *
     * @param httpSession HttpSession
     * @param reportVo    ReportVo
     * @param msgTemp     String
     * @return Map
     * @throws ParseException
     */
    public Map<String, Object> doSearch(HttpSession httpSession,
                                        ReportVo reportVo, String msgTemp) throws ParseException {

        UserInfo userInfo = (UserInfo) httpSession
                .getAttribute(CommonConstant.SESSION_USER_INFO);
        String userName = userInfo.getUserName();
        String message = "";
        String rptId = reportVo.getRptId();
        String rptName = reportVo.getRptName();
        String deptId = reportVo.getDeptId();
        String startDate = reportVo.getStartDate();
        String endDate = reportVo.getEndDate();
        String authority = userInfo.getAuthority();
        // 校验输入的字符串是否都是数字
        List<ReportList> reportList = null;
        //if (CommonUtil.isNumeric(rptId)) {
            SimpleDateFormat format = new SimpleDateFormat(
                    CommonConstant.DATE_FORMAT_YYYYMMDD);
            Date dStartDate = null;
            if (StringUtils.isNotEmpty(startDate)) {
                dStartDate = format.parse(startDate);
            }
            Date dEndDate = null;
            if (StringUtils.isNotEmpty(endDate)) {
                dEndDate = format.parse(endDate);
            }

            reportList = reportListService.doSelectReportList(deptId,
                    StringUtils.trim(rptId), StringUtils.trim(rptName),
                    dStartDate, dEndDate);
        //} else {
        //     message = "rptId.is.invalid";
        //}

        List<DeptList> deptList = deptListService.selectDeptList();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("deptList", deptList);
        map.put("reportList", reportList);
        map.put("userName", userName);
        map.put("reporter", rptName);

        map.put("groupId", deptId);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("reporterId", rptId);
        map.put("msgs", message);
        map.put("authority", authority);
        map.put("msgTemp", msgTemp);

        return map;
    }

    @RequestMapping("/delete_report")
    public ModelAndView deleteReport(HttpSession httpSession, ReportVo reportVo)
            throws ParseException {

        reportListService.deleteReport(reportVo.getFileNo());
        reportService.deleteContent(reportVo.getFileNo());
        return returnSearch(httpSession, "");
    }

    // 合并周报
    @RequestMapping("/combine")
    public ModelAndView showCombinedResult(HttpServletRequest request,
                                           HttpSession httpSession, HttpServletResponse response,
                                           ReportVo reportVo) throws IOException, ParseException {

        // 部门ID要用查询时用的值
        String groupId = reportVo.getDeptId();

        // 将数据写入模板文件
        String msgTemp = combineReportService.reportToWord(request, response,
                groupId);
        return StringUtils.isEmpty(msgTemp) ? null : returnSearch(httpSession,
                msgTemp);
    }

    //跳到系统维护
    @RequestMapping("/initReportGuide")
    public ModelAndView showReportGuide() {
        return new ModelAndView("/reportGuide");
    }

    //跳到事件维护
    @RequestMapping("/initMaintenance")
    public ModelAndView showMaintenance(HttpSession httpSession, String message) {
        // 将session对象取过来，在后台取session
        UserInfo userInfo = (UserInfo) httpSession
                .getAttribute(CommonConstant.SESSION_USER_INFO);
        SimpleDateFormat format = new SimpleDateFormat(
                CommonConstant.DATE_FORMAT_YYYYMMDD);
        Calendar cal = Calendar.getInstance();
        String userId = userInfo.getUserId();
        String userName = userInfo.getUserName();
        String groupId = userInfo.getGroupId();

        //设置默认的起始日期
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String initStartDate = format.format(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        String initEndDate = format.format(cal.getTime());
        List<DeptList> deptList = deptListService.selectDeptList();
        List<Event> eventList = eventService.selectEventList();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", userName);
        map.put("userId", userId);
        map.put("groupId", groupId);
        map.put("deptList", deptList);
        map.put("eventList", eventList);
        map.put("initStartDate", initStartDate);
        map.put("initEndDate", initEndDate);
        map.put("message", message);
        return new ModelAndView("/maintenance", map);
    }

    // 添加事件
    @RequestMapping("/saveEvent")
    public ModelAndView saveResult(HttpSession httpSession, ReportVo reportVo) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(
                CommonConstant.DATE_FORMAT_YYYYMMDD);
        // 往事件表中插入数据
        String eventStartDateValue = reportVo.getEventStartDate();
        String eventEndDateValue = reportVo.getEventEndDate();
        String eventTextAreaValue = reportVo.getEventTextArea();

        int eventCount = eventService.selectByStartDate(format
                .parse(eventStartDateValue));
        String message;
        if (eventCount > 0) {
            message = "event.exist";
        } else {
            message = initDateService.insertAndUpdate(eventStartDateValue,
                    eventEndDateValue, eventTextAreaValue);
        }
        return showMaintenance(httpSession, message);
    }

    /**
     * 保存修改事件
     *
     * @param request     HttpServletRequest
     * @param httpSession HttpSession
     * @param reportVo    ReportVo
     * @return ModelAndView
     * @throws ParseException
     */
    @RequestMapping("/saveEditEvent")
    public ModelAndView saveEditResult(HttpServletRequest request,
                                       HttpSession httpSession, ReportVo reportVo) throws ParseException {
        // 获得前台过来的数据
        SimpleDateFormat format = new SimpleDateFormat(
                CommonConstant.DATE_FORMAT_YYYYMMDD);
        //获得修改之前的开始时间
        String eventStartDateValue = request.getParameter("deleteEventStartDate");
        String eventEndDateValue = request.getParameter("deleteEventEndDate");
        String eventEditStartDateValue = reportVo.getEventEditStartDate();
        String eventEditEndDateValue = reportVo.getEventEditEndDate();
        String eventEditTextAreaValue = reportVo.getEventEditTextArea();

        //判断当前开始时间在事件表中存不存在
        int count = eventService.selectByStartDate(format.parse(eventEditStartDateValue));
        String message = "";
        if (count > 0) {

            initDateService.editUpdate(eventStartDateValue, eventEditStartDateValue, eventEndDateValue,
                    eventEditEndDateValue, eventEditTextAreaValue);
        } else {
            initDateService.editStartUpdate(eventStartDateValue, eventEditStartDateValue, eventEndDateValue, eventEditEndDateValue, eventEditTextAreaValue);
        }
        return showMaintenance(httpSession, message);
    }

    /**
     * 删除事件处理
     *
     * @param request     HttpServletRequest
     * @param httpSession httpSession
     * @return ModelAndView
     * @throws ParseException
     */
    @RequestMapping("/deleteEvent")
    public ModelAndView deleteResult(HttpServletRequest request,
                                     HttpSession httpSession) throws ParseException {

        String deleteStartDateValue = request.getParameter("deleteEventStartDate");
        String deleteEndDateValue = request.getParameter("deleteEventEndDate");
        String message = "";
        initDateService.deleteAndUpdate(deleteStartDateValue, deleteEndDateValue);

        return showMaintenance(httpSession, message);
    }

    @RequestMapping("/doLogout")
    public ModelAndView logout(HttpServletRequest request,HttpServletResponse response)
    {
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        return new ModelAndView(new RedirectView("login"));
    }
}
