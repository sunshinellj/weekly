package cn.caitc.weekly.controller;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.caitc.weekly.constant.CommonConstant;
import cn.caitc.weekly.model.DeptList;
import cn.caitc.weekly.model.ReportTemplate;
import cn.caitc.weekly.model.UserInfo;
import cn.caitc.weekly.service.DeptListService;
import cn.caitc.weekly.service.ReportTemplateService;

@Controller
public class UploadReportController {

	@Autowired
	private ReportTemplateService reportTemplateService;

	@Autowired
	private DeptListService deptListService;

	@Autowired
	private MessageSource messageSource;
	/**
	 * 上传页面初始化
	 * @param httpSession HttpSession
	 * @return ModelAndView
	 */
	@RequestMapping("/initUpload")
	public ModelAndView init(HttpSession httpSession) {
		UserInfo userInfo = (UserInfo) httpSession
				.getAttribute(CommonConstant.SESSION_USER_INFO);
		String userName = userInfo.getUserName();
		String groupId = userInfo.getGroupId();
		String groupName = userInfo.getGroupName();
		String authority = userInfo.getAuthority();
		
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String time = format.format(date);
		List<DeptList> deptList = new ArrayList<DeptList>(); 
		if("1".equals(authority)){
			deptList = deptListService.selectDeptList();		
		} else {
			DeptList dept = new DeptList();
			dept.setGroupId(groupId);
			dept.setGroupName(groupName);
			deptList.add(dept);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deptList", deptList);
		map.put("userName", userName);
		map.put("groupName", groupName);
		map.put("authority", authority);
		map.put("time", time);
		return new ModelAndView("/upload", map);
	}
	/**
	 * 上传文件
	 * @param httpSession HttpSession
	 * @param request HttpServletRequest
	 * @param myfile MultipartFile
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("/doUpload")
	public ModelAndView upload(HttpSession httpSession,
			HttpServletRequest  request, @RequestParam MultipartFile myfile)
			throws Exception {
		UserInfo userInfo = (UserInfo) httpSession
				.getAttribute(CommonConstant.SESSION_USER_INFO);

		List<DeptList> deptList = deptListService.selectDeptList();
		
		/**
		 * 根据部门ID得到部门名字
		 */
		String selectedId =request.getParameter("deptId");
		String selectedGroup = deptListService.selectById(request.getParameter("deptId"));
		/**
		 * 获取当前时间的年月日
		 */
		Calendar now = Calendar.getInstance();
		String year = Integer.toString(now.get(Calendar.YEAR));
		String month = Integer.toString(now.get(Calendar.MONTH) + 1);		
		String day = Integer.toString(now.get(Calendar.DAY_OF_MONTH));
		String groupId = userInfo.getGroupId();
		String ueserName =userInfo.getUserName();
		String fileName = myfile.getOriginalFilename();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("selectedGroup", selectedGroup);
		map.put("selectedId", selectedId);
		map.put("deptList", deptList);
		map.put("userName", userInfo.getUserName());
		map.put("groupName", userInfo.getGroupName());
		map.put("year", year);
		map.put("month", month);
		map.put("day", day);
		map.put("fileName", fileName);
		
		/**
		 * 判断文件是否为空
		 */
		if (myfile.isEmpty()) {
			map.put("message", messageSource.getMessage("template.not.upload",
					new Object[] { groupId }, Locale.CHINESE));

			return new ModelAndView("/upload", map);

		}
		/**
		 * 判断后缀名是否为.doc
		 */
		String suffix = myfile.getOriginalFilename().substring(
				myfile.getOriginalFilename().lastIndexOf("."));
		if (!(StringUtils.lowerCase(suffix).equals(".doc")||StringUtils.lowerCase(suffix).equals(".docx"))) {
			map.put("message", messageSource.getMessage("template.not.format",
					new Object[] { groupId }, Locale.CHINESE));	
			return new ModelAndView("/upload", map);
		/**
		 *  进一步判断文件大小是否小于2M		 
		*/	
		} else if (myfile.getSize() > CommonConstant.FILE_SIZE){			
			map.put("message", messageSource.getMessage(
					"templateSize.too.large", new Object[] { groupId },
					Locale.CHINESE));
			return new ModelAndView("/upload", map);
		}
		else{
			InputStream is = myfile.getInputStream();
			
			try {
				//解析模板文件
				reportTemplateService.readByDoc(is, selectedId,ueserName,fileName);
			} catch (Exception ex) {
				map.put("message", messageSource.getMessage(
						"template.not.match", new Object[] { groupId },
						Locale.CHINESE));
				return new ModelAndView("/upload", map);
			}
			
			/**
			 * 将要上传的文件保存在数据库中*/
			String groupNo = selectedId;
			String maxVersion = Integer.toString(reportTemplateService.selectVersionNo(groupNo));
			InputStream isFile = myfile.getInputStream();
			byte[] b = FileCopyUtils.copyToByteArray(isFile);    //FileCopyUtils   为spring下的一个工具类。
			if (b == null || b.length == 0) {
				//return 0;
			}
			try {
				reportTemplateService.insertTempFile(groupNo,Integer.parseInt(maxVersion),b);
			} catch (Exception e) {  
	            e.printStackTrace();  
	        }  

		}
		List<ReportTemplate> tempPart = reportTemplateService
				.selectTempPart(selectedId);
		List<ReportTemplate> tempPoint = reportTemplateService
				.selectTempPoint(selectedId);
		map.put("tempPart", tempPart);
		map.put("tempPoint", tempPoint);

		return new ModelAndView("/uploadSucess", map);
	}
	/**
	 * 修改后保存更新
	 * @param httpSession HttpSession
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/saveTemplate")
	public ModelAndView editSaveReturn(HttpSession httpSession,
			HttpServletRequest request) throws UnsupportedEncodingException {
		UserInfo userInfo = (UserInfo) httpSession
				.getAttribute(CommonConstant.SESSION_USER_INFO);
		Map<String, Object> map = new HashMap<String, Object>();
		String userName = userInfo.getUserName();
		String groupName = userInfo.getGroupName();
		String groupId = userInfo.getGroupId();
		String selectedId =request.getParameter("sId");
		String selectedGroup = request.getParameter("sGroup");
		
		/**
		 * 获取当前时间的年月日
		 */
		Calendar now = Calendar.getInstance();
		String year = Integer.toString(now.get(Calendar.YEAR));
		String month = Integer.toString(now.get(Calendar.MONTH) + 1);		
		String day = Integer.toString(now.get(Calendar.DAY_OF_MONTH));
		
		List<ReportTemplate> tempPart = reportTemplateService
				.selectTempPart(selectedId);
		List<ReportTemplate> tempPoint = reportTemplateService
				.selectTempPoint(selectedId);
		ReportTemplate reportLate = new ReportTemplate();

		reportLate.setGroupId(groupId);
		reportLate.setUpdateUser(userName);
		reportLate.setUpdateDate(new Date());
		/**
		 * 根据一级模板ID更新一级模板内容
		 */
		for (int i = 0; i < tempPart.size(); i++) {
			String partId = tempPart.get(i).getPartId();
			String newPart = request.getParameter("textPart" + i);
			reportLate.setPartId(partId);
			reportLate.setPartName(newPart);
			reportTemplateService.updateTemplatePart(reportLate);
		}
		/**
		 * 根据二级模板ID更新二级模板内容
		 */
		for (int j = 0; j < tempPoint.size(); j++) {
			String pointId = tempPoint.get(j).getPointId();
			String newPoint = request.getParameter("textPoint" + j);
			reportLate.setPointId(pointId);
			reportLate.setPointName(newPoint);
			reportTemplateService.updateTemplatePoint(reportLate);
		}

		List<DeptList> deptList = deptListService.selectDeptList();
		List<ReportTemplate> tempNewPart = reportTemplateService
				.selectTempPart(selectedId);
		List<ReportTemplate> tempNewPoint = reportTemplateService
				.selectTempPoint(selectedId);
		map.put("deptList", deptList);
		map.put("userName", userName);
		map.put("groupName", groupName);
		map.put("year", year);
		map.put("month", month);
		map.put("day", day);
		map.put("selectedGroup", selectedGroup);
		map.put("tempPart", tempNewPart);
		map.put("tempPoint", tempNewPoint);
		return new ModelAndView("/uploadSucess", map);
	}
}
