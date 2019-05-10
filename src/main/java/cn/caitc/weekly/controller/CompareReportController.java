package cn.caitc.weekly.controller;

import cn.caitc.weekly.constant.CommonConstant;
import cn.caitc.weekly.model.*;
import cn.caitc.weekly.service.*;
import cn.caitc.weekly.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CompareReportController {

    @Autowired
    private ReportTemplateService reportTemplateService;

    @Autowired
    private ReportListService reportListService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private InitDateService initDateService;

    @RequestMapping("/compareReport")
    public ModelAndView compare_report(
            @RequestParam(value = "fileNo") String fileNo) throws ParseException {

        String userId = fileNo.substring(fileNo.length() - 6, fileNo.length());

        UserInfo userInfo = userInfoService.selectUserInfo(userId);

        String userName = userInfo.getUserName();

        String groupName = userInfo.getGroupName();

        List<Report> reportList = reportService.selectReportContent(fileNo);

        // 定义该集合存储拼接后结果
        List<Report> reportListNew = new ArrayList<Report>();

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
        Date rptTime = tempPointByFileNo.get(0).getRptTime();

        DateFormat format = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYYYMMDDCHINA);
        String time = format.format(rptTime);
        int rptDate = Integer.parseInt(year + month + issue);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportListNew", reportListNew);
        map.put("tempPart", tempPartByFileNo);
        map.put("tempPoint", tempPointByFileNo);
        map.put("reportListNoPoint", reportListNoPoint);
        map.put("fileNo", fileNo);

        map.put("userName", userName);
        map.put("groupName", groupName);
        map.put("time", time);

        /**
         * 处理上周的周报
         */
        int yearValue = Integer.parseInt(year);
        int monthValue = Integer.parseInt(month);
        int issueValue = Integer.parseInt(issue);

        InitDate initDate = initDateService.selectTimeZone(year, month, issue);
        Date startDate = initDate.getStartDate();
        Date endDate = initDate.getEndDate();
        String dateTime = "";
        SimpleDateFormat df = new SimpleDateFormat(
                CommonConstant.DATE_FORMAT_YYYYMMDD);
        if (startDate != null && endDate != null) {
            dateTime = df.format(startDate) + "至" + df.format(endDate);
        }
        map.put("dateTime", dateTime);

        int repCount = reportListService.reportCount(userId, rptDate);

        if (repCount > 0) {
            String preDateTime = getPreDate(userId, yearValue, monthValue, issueValue);
            map.put("preDateTime", preDateTime);

            //判断上一期周报是否存在

            String preFileNo = getPreFileNo(userId, yearValue, monthValue, issueValue);

            List<Report> preReportList = reportService.selectReportContent(preFileNo);

            // 定义该集合存储拼接后结果
            List<Report> reportListOld = new ArrayList<Report>();

            for (Report preRowReport : preReportList) {
                String presWorkNote = preRowReport.getWorkNote();
                if (!StringUtils.isEmpty(presWorkNote)) {
                    String[] ssWorkNote = preRowReport.getWorkNote().split("[|]");

                    for (String aSsWorkNote : ssWorkNote) {
                        Report preReport = new Report();
                        preReport.setPartId(preRowReport.getPartId());
                        preReport.setPointId(preRowReport.getPointId());
                        preReport.setWorkNote(aSsWorkNote);
                        reportListOld.add(preReport);
                    }
                } else {
                    Report preReport = new Report();
                    preReport.setPartId(preRowReport.getPartId());
                    preReport.setPointId(preRowReport.getPointId());
                    preReport.setWorkNote(presWorkNote);
                    reportListOld.add(preReport);
                }
            }

            List<ReportTemplate> preTempPartByFileNo = reportTemplateService
                    .selectTempPartByFileNo(preFileNo);

            List<ReportTemplate> preTempPointByFileNo = reportTemplateService
                    .selectTempPointByFileNo(preFileNo);
            String oldTime = format.format(preTempPointByFileNo.get(0).getRptTime());
            // 显示没有二级提纲的一级提纲的内容
            List<Report> preReportListNoPoint = reportService
                    .selectContentNoPoint(preFileNo);

            map.put("reportListOld", reportListOld);
            map.put("preTempPart", preTempPartByFileNo);
            map.put("preTempPoint", preTempPointByFileNo);
            map.put("preReportListNoPoint", preReportListNoPoint);
            map.put("preFileNo", preFileNo);
            map.put("oldTime", oldTime);
            return new ModelAndView("/compareReport", map);
        } else {
            return new ModelAndView("/compareReport", map);
        }
    }

    public String getPreFileNo(String userId, int yearValue, int monthValue, int issueValue) {
        String preFileNo;
        //获得上个月的最大期数
        int count = CommonUtil.mondayCount(yearValue, monthValue - 1);
        //得到上一期的日期

        if (monthValue > 1) {
            if (issueValue > 1)
                issueValue -= 1;
            else {
                monthValue -= 1;
                issueValue = count;
            }
        } else if (monthValue == 1) {
            if (issueValue > 1) {
                issueValue -= 1;
            } else {
                yearValue -= 1;
                monthValue = 12;
                issueValue = CommonUtil.mondayCount(yearValue, monthValue);
            }
        }

        String year = String.valueOf(yearValue);
        String month = String.valueOf(monthValue);
        String issue = String.valueOf(issueValue);
        ReportList preList = reportListService.isHave(userId, year, month, issue);

        if (preList != null) {
            preFileNo = preList.getFileNo();
        } else {
            return getPreFileNo(userId, yearValue, monthValue, issueValue);
        }

        return preFileNo;
    }

    public String getPreDate(String userId, int yearValue, int monthValue, int issueValue) {
        String preDate = "";
        //获得上个月的最大期数
        int count = CommonUtil.mondayCount(yearValue, monthValue - 1);
        //得到上一期的日期

        if (monthValue > 1) {
            if (issueValue > 1)
                issueValue -= 1;
            else {
                monthValue -= 1;
                issueValue = count;
            }
        } else if (monthValue == 1) {
            if (issueValue > 1) {
                issueValue -= 1;
            } else {
                yearValue -= 1;
                monthValue = 12;
                issueValue = CommonUtil.mondayCount(yearValue, monthValue);
            }
        }

        String year = String.valueOf(yearValue);
        String month = String.valueOf(monthValue);
        String issue = String.valueOf(issueValue);
        ReportList preList = reportListService.isHave(userId, year, month, issue);

        if (preList != null) {
            InitDate initDate = initDateService.selectTimeZone(year, month, issue);
            Date startDate = initDate.getStartDate();
            Date endDate = initDate.getEndDate();
            SimpleDateFormat df = new SimpleDateFormat(
                    CommonConstant.DATE_FORMAT_YYYYMMDD);
            if (startDate != null && endDate != null) {
                preDate = df.format(startDate) + "至" + df.format(endDate);
            }

        } else {
            return getPreDate(userId, yearValue, monthValue, issueValue);
        }

        return preDate;
    }
}