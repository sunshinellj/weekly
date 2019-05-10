package cn.caitc.weekly.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.caitc.weekly.model.ReportList;
import cn.caitc.weekly.model.ReportTemplate;

public interface ReportListService {
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
    List<ReportList> doSelectReportList(String deptId, String rptId,
                                        String rptName, Date startDate, Date endDate);

    /**
     * 查询周报列表
     *
     * @param fileNo 周报番号
     */
    void deleteReport(String fileNo);

    /**
     * 更新周报文件（更新者、更新时间）
     *
     * @param updateUser 更新者
     * @param updateDate 更新时间
     * @param fileNo 番号
     */
    void updateFile(String updateUser, Date updateDate, String fileNo);

    /**
     * 查看该员工该份周报是否已经存在
     *
     * @param userId 用户ID
     * @param year 年
     * @param month 月
     * @param issue 期
     * @return  ReportList
     */
    ReportList isHave(String userId, String year, String month,
                      String issue);

    /**
     * 插入周报文件和周报内容，由于这两个表的关系，将它们放在一个事物中处理
     */
    void insertFileAndContent(String fileNo, String fileName, String deptId,
                              String rptId, Date rptTime, String year, String month,
                              String issue, int versionNo, List<ReportTemplate> tempPoint, List<ReportTemplate> tempPartNoPoint, HttpServletRequest request);

    /**
     * 查询用户的周报数量
     *
     * @param rptId 周报ID
     * @param rptTime 周报时间
     * @return int
     */
    int reportCount(String rptId, int rptTime);
}
