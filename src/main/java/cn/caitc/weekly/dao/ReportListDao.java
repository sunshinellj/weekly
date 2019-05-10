package cn.caitc.weekly.dao;

import cn.caitc.weekly.model.ReportList;

import java.util.List;

public interface ReportListDao {

    /**
     * 查询周报列表
     *
     * @param reportList ReportList
     * @return 周报信息列表
     */
    List<ReportList> doSelectReportList(ReportList reportList);

    /**
     * 删除周报
     *
     * @param reportList ReportList
     */
    void deleteReport(ReportList reportList);

    /**
     * 插入周报文件
     * @param reportList the report list
     */
    void insertFile(ReportList reportList);

    /**
     * 更新周报文件
     * 更新者，更新时间
     * @param reportList the report list
     */
    void updateFile(ReportList reportList);

    /**
     * 查看该员工该份周报是否已经存在
     * @param reportList the report list
     * @return the report list
     */
    ReportList isHave(ReportList reportList);


    /**
     * @param array 一组周报编号
     * @return List<ReportList>
     */
    List<ReportList> sortFileNo(List array);

    /**
     * 查询用户的周报数量
     *
     * @param reportList ReportList
     * @return int
     */
    int reportCount(ReportList reportList);
}
