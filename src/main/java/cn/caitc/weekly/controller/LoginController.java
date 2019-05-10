package cn.caitc.weekly.controller;

import cn.caitc.weekly.constant.CommonConstant;
import cn.caitc.weekly.model.UserInfo;
import cn.caitc.weekly.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/login")
    public ModelAndView init_login() {
        return new ModelAndView("/login");

    }

    @RequestMapping("/loginCheck")
    public ModelAndView in_login(HttpSession httpSession, @RequestParam(value = "userId") String userId) {

        UserInfo userInfo = userInfoService.selectUserInfo(userId.trim());

        //清除查询时，session为reportVo的值
        httpSession.setAttribute("reportVo", null);

        if (userInfo == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", messageSource.getMessage("rptId.not.find", new Object[]{userId}, Locale.CHINESE));
            return new ModelAndView("/login", map);
        } else {
            //将对象存储在session中
            httpSession.setAttribute(CommonConstant.SESSION_USER_INFO, userInfo);
            return new ModelAndView("redirect:/init_reportList");
        }
    }


}
