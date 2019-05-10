package cn.caitc.weekly.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.caitc.weekly.constant.CommonConstant;
import cn.caitc.weekly.model.UserInfo;

public class SessionTimeoutInterceptor extends HandlerInterceptorAdapter {
	public String[] excludedUrls;// 还没发现可以直接配置不拦截的资源，所以在代码里面来排除
	public String loginUrl;

	public void setExcludedUrls(String[] excludedUrls) {
		this.excludedUrls = excludedUrls;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {

		String requestUrl = request.getRequestURI().replace(
				request.getContextPath(), "");

		if (null != excludedUrls && excludedUrls.length >= 1) {
			for (String url : excludedUrls) {
				if (requestUrl.contains(url)) {
					return true;
				}
			}
		}

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				CommonConstant.SESSION_USER_INFO);
		if (userInfo != null) {
			return true; // 返回true，则这个方面调用后会接着调用postHandle(), afterCompletion()
		} else {
			response.sendRedirect(request.getContextPath() + loginUrl);
			return false;
			// 未登录 跳转到登录页面
			// throw new SessionTimeoutException();// 返回到配置文件中定义的路径
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

}
