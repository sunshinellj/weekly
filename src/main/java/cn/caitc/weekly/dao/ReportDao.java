package cn.caitc.weekly.dao;

import java.util.List;

import cn.caitc.weekly.model.Report;

public interface ReportDao {

    /**
     * 插入周报内容
     *
     * @param report 周报内容
     */
    void insertContent(Report report);

    /**
     * 查询出周报内容（含二级提纲）
     * @param report the report
     * @return the list
     */
    List<Report> selectReportContent(Report report);

    /**
     * 查询出 周报内容（不含二级提纲）
     * @param report the report
     * @return the list
     */
    List<Report> selectContentNoPoint(Report report);

    /**
     * 更新周报内容
     */
    void updateContent(Report report);

    void updateContentNoPoint(Report report);

    /**
     * 根据番号删除周报内容
     * @param report the report
     */
    void deleteContent(Report report);

}
