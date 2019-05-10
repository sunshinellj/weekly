package cn.caitc.weekly.service;


import java.io.InputStream;
import java.util.List;

import cn.caitc.weekly.model.ReportTemplate;

public interface ReportTemplateService {
	/**
	 *
	 * 查询模板*/
	 List<ReportTemplate> selectTempPart(String groupId);
	 List<ReportTemplate> selectTempPoint(String groupId);
	/**
	 *
	 * 插入模板*/
	 boolean insertTemplate(ReportTemplate reportLate);
	/**
	 *
	 * 读取word模板*/
	 void readByDoc(InputStream is,String groupId,String userName,String fileName) throws Exception;
	/**
	 * 
	 * 根据周报番号查询模板*/
	 List<ReportTemplate> selectTempPartNoPoint(String groupId);
	 List<ReportTemplate> selectTempPartByFileNo(String fileNo);
	 List<ReportTemplate> selectTempPointByFileNo(String fileNo);
	 List<ReportTemplate> selectTempPartNoPointByFileNo(String fileNo);
	/**
	 * 
	 * 查询当前最大版本号*/
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
	 * @param groupId the group id
	 * @param versionNo the version no
	 * @param tempFile the temp file
     */
	void insertTempFile(String groupId,int versionNo,Object tempFile);

	/**
	 * Select temp file.
	 *
	 * @param groupId the group id
	 * @param versionNo the version no
	 * @return the report template
     */
	ReportTemplate selectTempFile(String groupId,int versionNo);
}
