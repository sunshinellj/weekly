package cn.caitc.weekly.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.caitc.weekly.dao.ReportDao;
import cn.caitc.weekly.dao.ReportListDao;
import cn.caitc.weekly.model.Report;
import cn.caitc.weekly.model.ReportList;
import cn.caitc.weekly.model.ReportTemplate;
import cn.caitc.weekly.service.ReportListService;


/**
 * The type Report list service impl.
 * @author Administrator
 */
@Service
public class ReportListServiceImpl implements ReportListService {

    @Autowired
    private ReportListDao reportListDao;

    @Autowired
    protected ReportDao reportDao;

    /**
     * 查询周报列表
     *
     * @param deptId    部门ID
     * @param rptId     周报人ID
     * @param rptName   周报人名字
     * @param startDate 查询开始日期
     * @param endDate   查询结束日期
     * @return 周报列表
     */
    public List<ReportList> doSelectReportList(String deptId, String rptId,
                                               String rptName, Date startDate, Date endDate) {

        ReportList reportList = new ReportList();
        reportList.setGroupId(deptId);
        reportList.setReporter(rptName);
        reportList.setStartDate(startDate);
        reportList.setEndDate(endDate);
        reportList.setReporterId(rptId);
        return reportListDao.doSelectReportList(reportList);
    }

    /**
     * 删除周报列表
     *
     * @param fileNo 周报番号
     */
    public void deleteReport(String fileNo) {
        ReportList reportList = new ReportList();
        reportList.setFileNo(fileNo);
        reportListDao.deleteReport(reportList);
    }

    /**
     * 更新周报文件（更新者、更新时间）
     */
    public void updateFile(String updateUser, Date updateDate, String fileNo) {
        ReportList reportList = new ReportList();
        reportList.setUpdateUser(updateUser);
        reportList.setUpdateDate(updateDate);
        reportList.setFileNo(fileNo);

        reportListDao.updateFile(reportList);

    }

    /**
     * 查看该员工该份周报是否已经存在
     */
    public ReportList isHave(String userId, String year, String month,
                             String issue) {

        ReportList reportList = new ReportList();
        reportList.setReporterId(userId);
        reportList.setYear(year);
        reportList.setMonth(month);
        reportList.setIssue(issue);
        return reportListDao.isHave(reportList);
    }

    /**
     * 插入周报文件和周报内容，由于这两个表的关系，将它们放在一个事物中处理
     */
    public void insertFileAndContent(String fileNo, String fileName,
                                     String deptId, String rptId, Date rptTime, String year,
                                     String month, String issue, int versionNo,
                                     List<ReportTemplate> tempPoint,
                                     List<ReportTemplate> tempPartNoPoint, HttpServletRequest request) {
        // 插入周报文件
        ReportList reportList = new ReportList();
        reportList.setFileNo(fileNo);
        reportList.setFileName(fileName);
        reportList.setGroupId(deptId);
        reportList.setReporterId(rptId);
        reportList.setReportTime(rptTime);
        reportList.setYear(year);
        reportList.setMonth(month);
        reportList.setIssue(issue);
        reportList.setVersionNo(versionNo);

        reportListDao.insertFile(reportList);

        // 插入周报内容（含二级提纲）
        for (ReportTemplate aTempPoint : tempPoint) {
            // getParameterValues相同name的值的数组
            String pointId = aTempPoint.getPointId();
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
                workNote = sb.toString();
                Report report = new Report();
                report.setFileNo(fileNo);
                report.setPartId(partId);
                report.setPointId(pointId);
                report.setWorkNote(workNote);
                report.setInsertUser(rptId);
                report.setInsertDate(new Date());

                reportDao.insertContent(report);
            }
        }

        // 插入周报内容（不含二级提纲）
        for (ReportTemplate aTempPartNoPoint : tempPartNoPoint) {
            String partId = aTempPartNoPoint.getPartId();
            String advice = request.getParameter(partId);

            Report report = new Report();
            report.setFileNo(fileNo);
            report.setPartId(partId);
            report.setPointId("");
            report.setWorkNote(advice);
            report.setInsertUser(rptId);
            report.setInsertDate(new Date());

            reportDao.insertContent(report);
        }
    }

    /**
     * 查询用户的周报数量
     *
     * @param rptId   周报人ID
     * @param rptTime 周报时间
     * @return 周报数量
     */
    public int reportCount(String rptId, int rptTime) {
        ReportList reportList = new ReportList();
        reportList.setReporterId(rptId);
        reportList.setRptTime(rptTime);

        return reportListDao.reportCount(reportList);
    }

}
