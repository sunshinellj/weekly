package cn.caitc.weekly.service.impl;

import cn.caitc.weekly.dao.ReportDao;
import cn.caitc.weekly.model.Report;
import cn.caitc.weekly.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportDao reportDao;

    /**
     * 插入周报内容
     *
     * @param report 周报内容
     */
    public void insertContent(Report report) {
        reportDao.insertContent(report);
    }

    /**
     * */
    public List<Report> selectReportContent(String fileNo) {
        Report report = new Report();
        report.setFileNo(fileNo);
        return reportDao.selectReportContent(report);
    }

    public List<Report> selectContentNoPoint(String fileNo) {
        Report report = new Report();
        report.setFileNo(fileNo);
        return reportDao.selectContentNoPoint(report);
    }

    /**
     * 更新周报内容
     */
    public void updateContent(Report report) {
        reportDao.updateContent(report);
    }

    public void updateContentNoPoint(Report report) {
        reportDao.updateContentNoPoint(report);
    }

    /**
     * 删除周报内容
     */
    public void deleteContent(String fileNo) {
        Report report = new Report();
        report.setFileNo(fileNo);
        reportDao.deleteContent(report);
    }


}
