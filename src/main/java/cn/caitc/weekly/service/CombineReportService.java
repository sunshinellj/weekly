package cn.caitc.weekly.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CombineReportService {
	 String reportToWord(HttpServletRequest request,HttpServletResponse response,String groupId) throws IOException;
}
