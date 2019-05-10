package cn.caitc.weekly.service.impl;

import cn.caitc.weekly.constant.CommonConstant;
import cn.caitc.weekly.dao.*;
import cn.caitc.weekly.model.InitDate;
import cn.caitc.weekly.model.Report;
import cn.caitc.weekly.model.ReportList;
import cn.caitc.weekly.model.ReportTemplate;
import cn.caitc.weekly.service.CombineReportService;
import cn.caitc.weekly.service.InitDateService;
import cn.caitc.weekly.service.ReportTemplateService;
import oracle.sql.BLOB;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CombineReportServiceImpl implements CombineReportService {
	@Autowired
	ReportTemplateDao reportTemplateDao;

	@Autowired
	ReportDao reportDao;

	@Autowired
	ReportListDao reportListDao;

	@Autowired
	DeptListDao deptListDao;

	@Autowired
	InitDateService initDateService;

	@Autowired
	ReportTemplateService reportTemplateService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.caitc.weekly.service.CombineReportService#ReportToWord(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.String, java.lang.String)
	 */
	public String reportToWord(HttpServletRequest request,
			HttpServletResponse response, String groupId) throws IOException {

		String fileNosAccept;
		if (StringUtils.isEmpty(request.getParameterValues("fileNos")[0])) {
			fileNosAccept = request.getParameter("fileNos");
		} else {
			fileNosAccept = request.getParameterValues("fileNos")[0];
		}
		String[] fileNoSplit = fileNosAccept.split("[,]");

		List fileNoList = Arrays.asList(fileNoSplit);
		List<ReportList> reportList = reportListDao.sortFileNo(fileNoList);

		// 判断模板ID是否一致(由于数据库中存储的模板ID是乱码，所以这里的temId取的
		String tempIdFirst = reportList.get(0).getTempId();
		int indexFile;
		for (indexFile = 1; indexFile < reportList.size(); indexFile++) {
			if (!tempIdFirst.equals(reportList.get(indexFile).getTempId())) {
				return "template.is.different";
			}
		}
		// 获取排序后的番号
		String[] fileNosResult = new String[reportList.size()];
		int indexFileSort;
		for (indexFileSort = 0; indexFileSort < reportList.size(); indexFileSort++) {
			fileNosResult[indexFileSort] = reportList.get(indexFileSort)
					.getFileNo();
		}
		String versionNo = String.valueOf(reportList.get(0).getVersionNo());
		String deptId = request.getParameter("deptId");
		String groupName = deptListDao.selectById(deptId);

		// 获取周报时间段
		int year = Integer.parseInt(reportList.get(0).getYear());
		int month = Integer.parseInt(reportList.get(0).getMonth());
		int issue = Integer.parseInt(reportList.get(0).getIssue());
		InitDate initDate = initDateService.selectTimeZone(String.valueOf(year), String.valueOf(month),
				String.valueOf(issue));
		SimpleDateFormat df = new SimpleDateFormat(
				CommonConstant.DATE_FORMAT_YYYYMMDD);

		String timeZone = df.format(initDate.getStartDate()) + "-" + df.format(initDate.getEndDate());
		String[] timeSplit = timeZone.split("[-]");
		String startDate = timeSplit[0];
		String endDate = timeSplit[1];
		try {
			//调用dao

			ReportTemplate reportTemplate = reportTemplateService.selectTempFile(deptId,Integer.parseInt(versionNo));
			BLOB blob = (BLOB) reportTemplate.getTmpFile();
			InputStream is = blob.getBinaryStream();

			HWPFDocument doc = new HWPFDocument(is);
			Range range = doc.getRange();
			// 把range范围内的${reportDate}替换为当前的日期
			range.replaceText("${year}",
					new SimpleDateFormat("yyyy").format(new Date()));
			range.replaceText("${month}",
					new SimpleDateFormat("MM").format(new Date()));
			range.replaceText("${day}",
					new SimpleDateFormat("dd").format(new Date()));
			range.replaceText("${groupName}", groupName);
			range.replaceText("${startDate}", startDate);
			range.replaceText("${endDate}", endDate);

			// 含有二级提纲的部分的拼接结果
			List<Report> comReport = combineReport(fileNosResult);
			ReportTemplate reportLate = new ReportTemplate();
			reportLate.setFileNo(fileNosResult[0]);
			List<ReportTemplate> tempPart = reportTemplateDao
					.selectTempPartByFileNo(reportLate);
			List<ReportTemplate> tempPoint = reportTemplateDao
					.selectTempPointByFileNo(reportLate);
			// 遍历一级提纲
			int indexPa;
			int indexPo;
			int indexRe;
			for (indexPa = 0; indexPa < tempPart.size(); indexPa++) {
				// 遍历二级提纲
				for (indexPo = 0; indexPo < tempPoint.size(); indexPo++) {
					// 对于每一条周报内容
					for (indexRe = 0; indexRe < comReport.size(); indexRe++) {
						String pointId = comReport.get(indexRe).getPointId();
						String comWorkNote = comReport.get(indexRe)
								.getWorkNote();
						if (comReport.get(indexRe).getPartId()
								.equals(tempPart.get(indexPa).getPartId())
								&& pointId.equals(tempPoint.get(indexPo)
										.getPointId())) {
							// 获取二级提纲的后一部分
							if (StringUtils.isNotEmpty(pointId)) {
								String[] a = pointId.split("[.]");
								if (StringUtils.isNotEmpty(a[1])) {
									String id = a[1];
									// 进行替换
									if (StringUtils.isEmpty(comWorkNote)) {
										range.replaceText("${A" + id + "}", "");
									} else {
										range.replaceText("${A" + id + "}",
												comWorkNote);
									}
								}
							}
						}
					}
				}

			}

			// 替换不含有二级提纲的部分
			List<Report> comReportNoPoint = combineReportNoPoint(fileNosResult);
			ReportTemplate reportLateNoPoint = new ReportTemplate();
			reportLateNoPoint.setFileNo(fileNosResult[0]);
			List<ReportTemplate> tempPartNoPoint = reportTemplateDao
					.selectTempPartByFileNo(reportLate);
			int indexPaNoPoint;
			int indexReNoPoint;
			for (indexPaNoPoint = 0; indexPaNoPoint < tempPartNoPoint.size(); indexPaNoPoint++) {
				for (indexReNoPoint = 0; indexReNoPoint < comReportNoPoint
						.size(); indexReNoPoint++) {
					String advice = comReportNoPoint.get(indexReNoPoint)
							.getWorkNote();
					if (comReportNoPoint.get(indexReNoPoint).getPartId()
							.equals(tempPart.get(indexPaNoPoint).getPartId())) {
						range.replaceText("${B}", advice);
					}
				}
			}

			// 输出到前台
			try {
				// 输出的文件名，按照格式来

				String fileName = groupName + "周报" + timeZone + ".doc";

				if (request.getHeader("User-Agent").toUpperCase()
						.indexOf("SAFARI") > 0) {
					fileName = new String(fileName.getBytes("GBK"),
							"iso-8859-1");
				} else if (request.getHeader("User-Agent").toUpperCase()
						.indexOf("CHROME") > 0) {
					fileName = new String(fileName.getBytes("UTF-8"),
							"ISO8859-1");
				} else {
					fileName = URLEncoder.encode(fileName, "UTF-8");
				}
				response.setContentType("application/x-msdownload;");
				response.setHeader("Content-disposition",
						"attachment; filename=" + fileName);
				doc.write(response.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (is != null) {
					is.close();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "template.is.missing";
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 实现周报拼接,拼接的是有二级提纲的部分
	 * 
	 * @param fileNos 文件No
	 * @return 文件信息
	 */
	public List<Report> combineReport(String[] fileNos) {
		// 根据番号取出对应的模板,已经判断过模板的一致性，只选取第一个模板
		ReportTemplate reportLate = new ReportTemplate();
		reportLate.setFileNo(fileNos[0]);
		List<ReportTemplate> tempPart = reportTemplateDao
				.selectTempPartByFileNo(reportLate);
		List<ReportTemplate> tempPoint = reportTemplateDao
				.selectTempPointByFileNo(reportLate);
		// 新建一个Report对象，存放合并后的结果
		List<Report> combinedReport = new ArrayList<Report>();
		// 遍历一级提纲
		int indexPa;
		int indexPo;
		int i;
		int indexRe;
		for (indexPa = 0; indexPa < tempPart.size(); indexPa++) {
			// 遍历二级提纲
			for (indexPo = 0; indexPo < tempPoint.size(); indexPo++) {
				String partId = tempPart.get(indexPa).getPartId();
				String pointId = tempPoint.get(indexPo).getPointId();
				// 每一个二级提纲下，周报内容拼接后存放在该字符串中
				String sWorkNote = "";
				String resultWorkNote = "";
				// 遍历所选周报番号
				for (i = 0; i < fileNos.length; i++) {
					// 根据番号取出对应的报表内容
					Report report = new Report();
					report.setFileNo(fileNos[i]);
					List<Report> reportList = reportDao
							.selectReportContent(report);
					// 根据查询出来的每一个周报，遍历周报内容
					for (indexRe = 0; indexRe < reportList.size(); indexRe++) {
						String workNote = reportList.get(indexRe).getWorkNote();
						// 当一级提纲、二级提纲分别匹配，周报内容不为空时
						if (partId.equals(reportList.get(indexRe).getPartId())
								&& pointId.equals(reportList.get(indexRe)
										.getPointId())
								&& !StringUtils.isEmpty(workNote)) {
							sWorkNote = sWorkNote + workNote;

						}
					}
				}
				// 每一个二级提纲下又要拼接
				String[] splitedWorkNote = sWorkNote.split("[|]");
				int j;
				for (j = 1; j <= splitedWorkNote.length; j++) {
					String ssw = splitedWorkNote[j - 1];
					if (!StringUtils.isEmpty(splitedWorkNote[j - 1])) {
						resultWorkNote = resultWorkNote + j + "." + ssw
								+ "\r";
					}
				}
				Report comReport = new Report();
				comReport.setPartId(partId);
				comReport.setPointId(pointId);
				comReport.setWorkNote(resultWorkNote);
				combinedReport.add(comReport);
			}
		}

		return combinedReport;
	}

	/**
	 * 拼接不含二级提纲的部分
	 * 
	 * @param fileNos 文件No
	 * @return 文件信息
	 */
	public List<Report> combineReportNoPoint(String[] fileNos) {
		// 不含二级提纲的
		ReportTemplate reportLateNoPoint = new ReportTemplate();
		reportLateNoPoint.setFileNo(fileNos[0]);
		List<ReportTemplate> tempPartNoPoint = reportTemplateDao
				.selectTempPartNoPointByFileNo(reportLateNoPoint);

		// 新建一个Report对象，存放合并后的结果
		List<Report> combinedReportNoPoint = new ArrayList<Report>();
		int indexPa;
		int i;
		int indexRe;
		for (indexPa = 0; indexPa < tempPartNoPoint.size(); indexPa++) {
			String partId = tempPartNoPoint.get(indexPa).getPartId();
			String advice = "";
			for (i = 0; i < fileNos.length; i++) {
				Report reportNoPoint = new Report();
				reportNoPoint.setFileNo(fileNos[i]);
				List<Report> reportListNoPoint = reportDao
						.selectContentNoPoint(reportNoPoint);
				for (indexRe = 0; indexRe < reportListNoPoint.size(); indexRe++) {
					String workNote = reportListNoPoint.get(indexRe)
							.getWorkNote();
					if (partId.equals(reportListNoPoint.get(indexRe)
							.getPartId()) && !StringUtils.isEmpty(workNote)) {
						int num = i + 1;
						advice = advice + num + "." + workNote + "\r";
					}
				}
			}

			Report comReport = new Report();
			comReport.setPartId(partId);
			comReport.setWorkNote(advice);
			combinedReportNoPoint.add(comReport);
		}
		return combinedReportNoPoint;
	}

}