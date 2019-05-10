package cn.caitc.weekly.controller;

import cn.caitc.weekly.constant.CommonConstant;
import cn.caitc.weekly.model.Authority;
import cn.caitc.weekly.model.DeptList;
import cn.caitc.weekly.model.UserInfo;
import cn.caitc.weekly.service.DeptListService;
import cn.caitc.weekly.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


/**
 * The type Employee controller.
 */
@Controller
public class EmployeeController {
    @Autowired
    UserInfoService userInfoService;

    @Autowired
    DeptListService deptListService;

    @RequestMapping("/employee")
    public ModelAndView init(HttpServletRequest request) {
        String userId = "";
        String userName = "";
        String groupId = "";
        //从session中获取登录用户的相关信息
        HttpSession httpSession = request.getSession();
        UserInfo loginInfo = (UserInfo) httpSession
                .getAttribute(CommonConstant.SESSION_USER_INFO);
        String authority = loginInfo.getAuthority();

        if(!authority.equals("1")){
            groupId = loginInfo.getGroupId();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        List<UserInfo> userInfoList = userInfoService.selectUserInfoBySearch(userId, userName, groupId);

        //调用权限控制部门列表
        List<DeptList> deptList = showDepList(loginInfo);
        //权限列表
        List<Authority> authorityList = showAuthority(authority);

        map.put("userInfoList", userInfoList);
        map.put("deptList", deptList);
        map.put("authorityList", authorityList);
        return new ModelAndView("/employee", map);
    }

    //查询员工信息
    @RequestMapping("/doSearchEmployee")
    public ModelAndView search(HttpServletRequest request, String message) {
        String userId = request.getParameter("userId");
        String userName = request.getParameter("userName");
        String groupId = request.getParameter("deptId");
        List<UserInfo> userInfoList = userInfoService.selectUserInfoBySearch(userId, userName, groupId);
        //调用权限控制部门列表
        HttpSession httpSession = request.getSession();
        UserInfo loginInfo = (UserInfo) httpSession
                .getAttribute(CommonConstant.SESSION_USER_INFO);
        List<DeptList> deptList = showDepList(loginInfo);
        //权限列表
        List<Authority> authorityList = showAuthority(loginInfo.getAuthority());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userInfoList", userInfoList);
        map.put("deptList", deptList);
        map.put("userId", userId);
        map.put("userName", userName);
        map.put("groupId", groupId);
        map.put("message", message);
        map.put("authorityList", authorityList);
        return new ModelAndView("/employee", map);
    }

    //新增后保存,修改后保存
    @RequestMapping("/addEmployee")
    public ModelAndView save(HttpServletRequest request) {
        String userId = request.getParameter("employeeId");
        String userName = request.getParameter("employeeName");
        String groupId = request.getParameter("groupId");
        String authority = request.getParameter("authority");
        String message = "";
        //添加时，判断用户是否存在
        if (userInfoService.selectUserInfo(userId) != null && request.getParameter("flag").equals("add")) {
            message = "user.exist";
        } else {
            //获取添加用户
            UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
                    CommonConstant.SESSION_USER_INFO);
            String systemUserId = userInfo.getUserId();
            if (request.getParameter("flag").equals("add")) {
                //获取当前本部门员工orderNo的最大值
                int maxOrderNo = userInfoService.selectMaxOrder(groupId).getOrderNo();
                int orderNo = maxOrderNo + 1;
                userInfoService.insertUser(userId, userName, groupId, orderNo, authority, systemUserId, new Date());
            } else {
                userInfoService.updateUser(userId, userName, groupId, authority, systemUserId, new Date());
            }
        }

        return search(request, message);
    }

    //删除用户
    @RequestMapping("/deleteUser")
    public ModelAndView delete(HttpServletRequest request) {
        String userId = request.getParameter("userNo");
        userInfoService.deleteUser(userId);
        String message = "";
        return search(request, message);
    }

    //受权限控制的部门列表
    private List<DeptList> showDepList(UserInfo loginInfo) {
        //从session中获取登录用户的相关信息
        String authority = loginInfo.getAuthority();
        String loginGroupId = loginInfo.getGroupId();
        String loginGroupName = loginInfo.getGroupName();
        //部门数据的显示受权限的影响
        List<DeptList> deptList = new ArrayList<DeptList>();
        if ("1".equals(authority)) {
            deptList = deptListService.selectDeptList();
        } else {
            DeptList dept = new DeptList();
            dept.setGroupId(loginGroupId);
            dept.setGroupName(loginGroupName);
            deptList.add(dept);
        }
        return deptList;
    }

    //权限显示列表
    private List<Authority> showAuthority(String authority) {
        //从session中获取登录用户的相关信息
        List<Authority> authorityList = new ArrayList<Authority>();

        Authority autho3 = new Authority();
        autho3.setAuthorityId("3");
        autho3.setAuthorityName("普通员工");
        authorityList.add(autho3);

        Authority autho2 = new Authority();
        autho2.setAuthorityId("2");
        String str2 = "部门管理员";
        autho2.setAuthorityName(str2);
        authorityList.add(autho2);

        if (authority.equals("1")) {
            Authority autho1 = new Authority();
            autho1.setAuthorityId("1");
            autho1.setAuthorityName("系统管理员");
            authorityList.add(autho1);
        }
        return authorityList;
    }
}
