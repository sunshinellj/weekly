package cn.caitc.weekly.controller;

import cn.caitc.weekly.constant.CommonConstant;
import cn.caitc.weekly.model.*;
import cn.caitc.weekly.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class NewReportController {

    @Autowired
    private ReportTemplateService reportTemplateService;

    @Autowired
    private ReportListService reportListService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private CombineReportService combineReportService;

    @Autowired
    private InitDateService initDateService;

    @RequestMapping("/isHaveAjax")
    @ResponseBody
    public String isHaveAjax(HttpServletRequest request) throws IOException, ParseException {

        String month = StringUtils
                .defaultString(request.getParameter("month"));
        String year = StringUtils.defaultString(request.getParameter("year"));
        String issue = StringUtils
                .defaultString(request.getParameter("issue"));
        String userId = StringUtils.defaultString(request
                .getParameter("userId"));

        String timeZone = "";

        // 获取某月有多少周
        int countMonday = initDateService.selectCountIssue(year, month);

        // 获取某一期的时间区间
        InitDate initDate = initDateService.selectTimeZone(year, month,
                issue);
        Date startDate = initDate.getStartDate();
        Date endDate = initDate.getEndDate();
        SimpleDateFormat df = new SimpleDateFormat(
                CommonConstant.DATE_FORMAT_YYYYMMDD);
        if (startDate != null && endDate != null) {
            timeZone = df.format(startDate) + "~" + df.format(endDate);
        }
        // 获取每个月有多少周
        String sCountMonday = String.valueOf(countMonday);
        String message = timeZone + "~";
        if (isHave(userId, year, month, issue)) {
            message = message + "exist";
        }
        message = message + "~" + sCountMonday;

        return message;
    }

    @RequestMapping("/newReport")
    public ModelAndView init(HttpSession httpSession, String message) {
        UserInfo userInfo = (UserInfo) httpSession
                .getAttribute(CommonConstant.SESSION_USER_INFO);
        String userName = userInfo.getUserName();
        String userId = userInfo.getUserId();
        String groupName = userInfo.getGroupName();
        String groupId = userInfo.getGroupId();

        // 获取系统时间
        Date date = new Date();
        DateFormat format = new SimpleDateFormat(
                CommonConstant.DATE_FORMAT_YYYYMMDDCHINA);
        String time = format.format(date);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", userName);
        map.put("groupName", groupName);
        map.put("userId", userId);
        map.put("time", time);

        // 获取一级提纲
        List<ReportTemplate> tempPart = reportTemplateService
                .selectTempPart(groupId);
        List<ReportTemplate> tempPoint = reportTemplateService
                .selectTempPoint(groupId);
        map.put("tempPart", tempPart);
        map.put("tempPoint", tempPoint);
        // 写周报的日期一般为周一，要获取上周的周一
        Calendar cal=Calendar.getInstance();
        int weekNow=cal.get(cal.WEEK_OF_MONTH);
        int weekLast=weekNow-1;
        cal.set(Calendar.WEEK_OF_MONTH,weekLast);
        InitDate initDate = initDateService.selectYearMonthIssueByDate(cal.getTime());

        String year = initDate.getYear();
        String month = initDate.getMonth();
        String issue = initDate.getIssue();
        map.put("year", year);
        map.put("month", month);
        map.put("issue", issue);
        map.put("message", message);
        map.put("countIssue", initDateService.selectCountIssue(year, month));

        return new ModelAndView("/newReport", map);


    }

    @RequestMapping("/saveAndReturn")
    public ModelAndView saveAndReturn(HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
                CommonConstant.SESSION_USER_INFO);
        String userId = userInfo.getUserId();
        String userName = userInfo.getUserName();
        String groupId = userInfo.getGroupId();
        // 插入一份周报文件
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String issue = request.getParameter("issue");
        String fileName;

        // 判断周报是否已经存在
        if (!isHave(userId, year, month, issue)) {
            // 替换生成周报文件名
            // 获取本周报周的最后一天
            InitDate initDate = initDateService.selectTimeZone(year, month,
                    issue);
            SimpleDateFormat df = new SimpleDateFormat(
                    CommonConstant.DATE_FORMAT_YYYYMMDD);

            String timeZone = df.format(initDate.getStartDate()) + "-" + df.format(initDate.getEndDate());
            fileName = CommonConstant.FILENAME.replaceAll("\\/", userName);
            fileName = fileName.replaceAll("\\.", timeZone);
            System.out.println(fileName);

            String fileNo = year + month + issue + userId;
            // 版本号的取得
            List<ReportTemplate> tempPoint = reportTemplateService
                    .selectTempPoint(groupId);
            int versionNo = tempPoint.get(0).getVersionNo();
            // 获取没有下设二级提纲的一级提纲
            List<ReportTemplate> tempPartNoPoint = reportTemplateService
                    .selectTempPartNoPoint(groupId);
            reportListService.insertFileAndContent(fileNo, fileName, groupId,
                    userId, new Date(), year, month, issue, versionNo,
                    tempPoint, tempPartNoPoint, request);

            return new ModelAndView("redirect:/returnSearch_reportList");
        } else {

            return init(request.getSession(), "report.is.have");
        }
    }

    @RequestMapping("/editReport")
    public ModelAndView edit_init(@RequestParam(value = "fileNo") String fileNo, String message) {
        // 从右开始截取6个字符
        String userId = fileNo.substring(fileNo.length() - 6);
        String userName = userInfoService.selectUserInfo(userId).getUserName();
        String groupName = userInfoService.selectUserInfo(userId)
                .getGroupName();
        String deptId = userInfoService.selectUserInfo(userId).getGroupId();
        List<Report> reportList = reportService.selectReportContent(fileNo);

        // 定义该集合存储拼接后结果
        List<Report> reportListNew = new ArrayList();

        for (Report rowReport : reportList) {
            String sWorkNote = rowReport.getWorkNote();
            if (!StringUtils.isEmpty(sWorkNote)) {
                String[] ssWorkNote = rowReport.getWorkNote().split("[|]");

                for (String aSsWorkNote : ssWorkNote) {
                    Report report = new Report();
                    report.setPartId(rowReport.getPartId());
                    report.setPointId(rowReport.getPointId());
                    report.setWorkNote(aSsWorkNote);
                    reportListNew.add(report);
                }
            } else {
                Report report = new Report();
                report.setPartId(rowReport.getPartId());
                report.setPointId(rowReport.getPointId());
                report.setWorkNote(sWorkNote);
                reportListNew.add(report);
            }
        }

        List<ReportTemplate> tempPartByFileNo = reportTemplateService
                .selectTempPartByFileNo(fileNo);

        List<ReportTemplate> tempPointByFileNo = reportTemplateService
                .selectTempPointByFileNo(fileNo);

        // 显示没有二级提纲的一级提纲的内容
        List<Report> reportListNoPoint = reportService
                .selectContentNoPoint(fileNo);
        String year = tempPointByFileNo.get(0).getYear();
        String month = tempPointByFileNo.get(0).getMonth();
        String issue = tempPointByFileNo.get(0).getIssue();

        DateFormat format = new SimpleDateFormat(
                CommonConstant.DATE_FORMAT_YYYYMMDDCHINA);
        String time = format.format(tempPointByFileNo.get(0).getRptTime());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportListNew", reportListNew);
        map.put("tempPart", tempPartByFileNo);
        map.put("tempPoint", tempPointByFileNo);
        map.put("reportListNoPoint", reportListNoPoint);
        map.put("fileNo", fileNo);

        map.put("userName", userName);
        map.put("groupName", groupName);
        map.put("year", year);
        map.put("month", month);
        map.put("issue", issue);
        map.put("time", time);
        map.put("deptId", deptId);
        map.put("message", message);
        return new ModelAndView("/editReport", map);
    }

    /**
     * 修改后保存的处理。
     *
     * @param request HttpServletRequest
     * @return ModelAndView
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/editAndReturn")
    public ModelAndView editSaveReturn(HttpServletRequest request)
            throws UnsupportedEncodingException {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
                CommonConstant.SESSION_USER_INFO);
        String userId = userInfo.getUserId();

        // 修改周报文件：更新者，更新时间
        String fileNo = request.getParameter("fileNo");
        reportListService.updateFile(userId, new Date(), fileNo);

        // 更新周报内容
        List<ReportTemplate> tempPointByFileNo = reportTemplateService
                .selectTempPointByFileNo(fileNo);

        for (ReportTemplate aTempPointByFileNo : tempPointByFileNo) {
            // getParameterValues相同name的值的数组
            String pointId = aTempPointByFileNo.getPointId();
            String partId = pointId.split("[.]")[0];
            String[] a = request.getParameterValues(pointId);
            String workNote;
            StringBuilder sb = new StringBuilder();
            if (a != null) {
                for (String anA : a) {
                    if (!StringUtils.isEmpty(anA)) {
                        sb.append(anA);
                        sb.append("|");
                    }
                }
            }
            workNote = sb.toString();
            Report report = new Report();
            report.setFileNo(fileNo);
            report.setPartId(partId);
            report.setPointId(pointId);
            report.setWorkNote(workNote);
            report.setUpdateUser(userId);
            report.setUpdateDate(new Date());

            reportService.updateContent(report);
        }

        // 更新没有二级提纲的一级提纲的内容
        // 获取没有下设二级提纲的一级提纲

        List<ReportTemplate> tempPartNoPoint = reportTemplateService
                .selectTempPartNoPointByFileNo(fileNo);
        for (ReportTemplate aTempPartNoPoint : tempPartNoPoint) {
            String partId = aTempPartNoPoint.getPartId();
            String advice = request.getParameter(partId);
            Report report = new Report();
            report.setFileNo(fileNo);
            report.setPartId(partId);
            report.setPointId("");
            report.setWorkNote(advice);
            report.setUpdateUser(userId);
            report.setUpdateDate(new Date());

            reportService.updateContentNoPoint(report);
        }

        return new ModelAndView("redirect:/returnSearch_reportList");
    }

    /**
     * @param userId 用户ID
     * @param year   年
     * @param month  月
     * @param issue  日
     * @return true:数据存在;fasle:数据不存在
     */
    private boolean isHave(String userId, String year, String month,
                           String issue) {

        boolean flg = false;
        ReportList reportList = reportListService.isHave(userId, year, month,
                issue);
        if (reportList != null) {

            flg = true;
        }
        return flg;
    }

    @RequestMapping("/download")
    public ModelAndView downReport(HttpServletRequest request, HttpSession httpSession,
                                   HttpServletResponse response) throws IOException {
        UserInfo userInfo = (UserInfo) httpSession
                .getAttribute(CommonConstant.SESSION_USER_INFO);
        String groupId = userInfo.getGroupId();
        String fileNo = request.getParameter("fileNo");
        // 将数据写入模板文件
        String msgTemp = combineReportService.reportToWord(request, response,
                groupId);
        return StringUtils.isEmpty(msgTemp) ? null : edit_init(
                fileNo, msgTemp);
    }

}
