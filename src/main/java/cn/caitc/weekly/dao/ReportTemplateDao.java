package cn.caitc.weekly.dao;

import java.util.List;

import cn.caitc.weekly.model.ReportTemplate;

public interface ReportTemplateDao{

	/**
	 *查询模板*/
	 List<ReportTemplate> selectTempPart(ReportTemplate reportLate);
	 List<ReportTemplate> selectTempPoint(ReportTemplate reportLate);
	 List<ReportTemplate> selectTempPartNoPoint(ReportTemplate reportLate);
	/**
	 *插入模板*/
	 boolean insertTemplate(ReportTemplate reportLate);

	/**
	 * 根据周报番号查询模板
	 */
	 List<ReportTemplate> selectTempPartByFileNo(ReportTemplate reportLate);
	 List<ReportTemplate> TempPartByFileNo(ReportTemplate reportLate);
	 List<ReportTemplate> selectTempPointByFileNo(ReportTemplate reportLate);
	 List<ReportTemplate> selectTempPartNoPointByFileNo(ReportTemplate reportLate);
	
	/**
	 * 
	 * 查询当前最大的版本号
	 */
	 int selectVersionNo(String groupId);
	/**
	 * 
	 * 更新模板part部分*/
	 void updateTemplatePart(ReportTemplate reportTemplate);
	/**
	 * 
	 * 更新模板point部分*/
	 void updateTemplatePoint(ReportTemplate reportTemplate);

	/**
	 * Insert temp file.
	 *
	 * @param reportTemplate the report template
     */
	void insertTempFile(ReportTemplate reportTemplate);


	/**
	 * Select temp file.
	 *
	 * @param reportTemplate the report template
	 * @return the report template
     */
	ReportTemplate selectTempFile(ReportTemplate reportTemplate);
}
