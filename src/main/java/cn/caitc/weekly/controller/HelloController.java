package cn.caitc.weekly.controller;

import cn.caitc.weekly.model.User;
import cn.caitc.weekly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HelloController {

    @Autowired
    private UserService userService;

    /**
     * 测试画面
     *
     * @param name 用户名
     * @return 测试画面
     */
    @RequestMapping("/hello")
    public ModelAndView greeting(@RequestParam(value = "name", defaultValue = "World") String name) {

        List<User> userList = userService.selectUser();
        if (userList == null || userList.isEmpty()) {
            User user = new User();
            user.setId("01");
            user.setNickname(name);
            userService.insertUser(user);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", name);
        map.put("msgs", "mobile.is.invalid");
        return new ModelAndView("/hello", map);
    }
}
