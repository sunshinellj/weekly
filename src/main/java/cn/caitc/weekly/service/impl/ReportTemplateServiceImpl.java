package cn.caitc.weekly.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.caitc.weekly.dao.ReportTemplateDao;
import cn.caitc.weekly.model.ReportTemplate;
import cn.caitc.weekly.service.ReportTemplateService;

@Service
public class ReportTemplateServiceImpl implements ReportTemplateService {

	@Autowired
	private ReportTemplateDao reportTemplateDao;
	/**
	 * 查询模板一级提纲
	 */
	public List<ReportTemplate> selectTempPart(String groupId) {

		ReportTemplate reportLate = new ReportTemplate();
		reportLate.setGroupId(groupId);
		return reportTemplateDao.selectTempPart(reportLate);
	}
	/**
	 * 查询模板二级提纲
	 */
	public List<ReportTemplate> selectTempPoint(String groupId) {

		ReportTemplate reportLate = new ReportTemplate();
		reportLate.setGroupId(groupId);
		return reportTemplateDao.selectTempPoint(reportLate);
	}
	
	public void readByDoc(InputStream is, String groupId,String userName,String fileName) throws Exception {

		HWPFDocument doc = new HWPFDocument(is);
		Range range = doc.getRange();
		//读表格
		this.readTable(range, groupId,userName,fileName);
		this.closeStream(is);
	}
	/**
	 * 读表格 每一个回车符代表一个段落，所以对于表格而言，每一个单元格至少包含一个段落，每行结束都是一个段落。
	 * 
	 * @param range Range,groupId String,
	 */
	private void readTable(Range range, String groupId,String userName,String fileName) {
		//
		TableIterator tableIter = new TableIterator(range);
		Table table;
		TableRow row;
		TableCell cell;

		List<String> template = new ArrayList<String>();
		while (tableIter.hasNext()) {
			table = tableIter.next();
			int rowNum = table.numRows();
			for (int j = 0; j < rowNum; j++) {
				row = table.getRow(j);
				int cellNum = row.numCells();
				for (int k = 0; k < cellNum; k++) {
					cell = row.getCell(k);
					String text = cell.text().trim();
					Pattern pattern = Pattern.compile("(^\\$+.*)|(.*\\r+$)");
					Matcher matcher = pattern.matcher(text);
					text = matcher.replaceAll("");

					template.add(text);
				}
			}

		}
		/**
		 * 获取模板各个表格的内容
		 */
		String partOneName = template.get(0);
		String[] pointOneName = template.get(1).split("\r");
		String partTwoName = template.get(2);
		String[] pointTwoName = template.get(3).split("\r");

		ReportTemplate reportLate = new ReportTemplate();

		reportLate.setGroupId(groupId);
		reportLate.setPartId("1");
		reportLate.setPartName(partOneName);
		reportLate.setInsertUser(userName);
		reportLate.setInsertDate(new Date());
		reportLate.setTempName(fileName);
		//获取最大版本号并且+1
		int maxVersion = reportTemplateDao.selectVersionNo(groupId);
		reportLate.setVersionNo(maxVersion + 1);
		
		/**
		 * 循环插入模板一级提纲
		 */
		for (int i = 1,j=1; i <= pointOneName.length; i+=2,j++) {

			String uuid = UUID.randomUUID().toString();
			uuid = uuid.replace("-", "");
			reportLate.setTempId(uuid);
			reportLate.setPointId("1." + j);
			reportLate.setPointName(pointOneName[i - 1]);

			insertTemplate(reportLate);
		}

		reportLate.setPartId("2");
		reportLate.setPartName(partTwoName);
		
		/**
		 * 循环插入模板二级提纲
		 */
		for (int i = 1; i <= pointTwoName.length; i++) {
			String uuid = UUID.randomUUID().toString();
			uuid = uuid.replace("-", "");
			reportLate.setTempId(uuid);
			reportLate.setPointId("");
			reportLate.setPointName(pointTwoName[i - 1]);
			insertTemplate(reportLate);
		}
	}

	/**
	 * 关闭输入流
	 * 
	 * @param is InputStream
	 */
	private void closeStream(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean insertTemplate(ReportTemplate reportLate) {

		return reportTemplateDao.insertTemplate(reportLate);
	}

	public List<ReportTemplate> selectTempPartNoPoint(String groupId) {
		ReportTemplate reportLate = new ReportTemplate();
		reportLate.setGroupId(groupId);
		return reportTemplateDao.selectTempPartNoPoint(reportLate);
	}

	public List<ReportTemplate> selectTempPartByFileNo(String fileNo) {
		ReportTemplate reportTempLate = new ReportTemplate();
		reportTempLate.setFileNo(fileNo);
		return reportTemplateDao.selectTempPartByFileNo(reportTempLate);
	}

	public List<ReportTemplate> selectTempPointByFileNo(String fileNo) {
		ReportTemplate reportTempLate = new ReportTemplate();
		reportTempLate.setFileNo(fileNo);
		return reportTemplateDao.selectTempPointByFileNo(reportTempLate);
	}

	public List<ReportTemplate> selectTempPartNoPointByFileNo(String fileNo) {
		ReportTemplate reportTempLate = new ReportTemplate();
		reportTempLate.setFileNo(fileNo);
		return reportTemplateDao.selectTempPartNoPointByFileNo(reportTempLate);
	}

	public int selectVersionNo(String groupId) {

		return reportTemplateDao.selectVersionNo(groupId);
	}

	public void updateTemplatePart(ReportTemplate reportTemplate) {
		reportTemplateDao.updateTemplatePart(reportTemplate);
	}

	public void updateTemplatePoint(ReportTemplate reportTemplate) {
		reportTemplateDao.updateTemplatePoint(reportTemplate);
	}

	/**
	 * Insert temp file.
	 *
	 * @param groupId the group id
	 * @param versionNo the version no
	 * @param tempFile the temp file
	 */
	public void insertTempFile(String groupId, int versionNo, Object tempFile){
		ReportTemplate temp=new ReportTemplate();
		temp.setGroupId(groupId);
		temp.setVersionNo(versionNo);
		temp.setTmpFile(tempFile);
		reportTemplateDao.insertTempFile(temp);
	}

	/**
	 * Select temp file.
	 *
	 * @param groupId the group id
	 * @param versionNo the version no
	 * @return the report template
	 */
	public ReportTemplate selectTempFile(String groupId, int versionNo){
		ReportTemplate temp=new ReportTemplate();
		temp.setGroupId(groupId);
		temp.setVersionNo(versionNo);
		return reportTemplateDao.selectTempFile(temp);
	}
}
