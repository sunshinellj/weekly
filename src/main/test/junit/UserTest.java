package junit;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.caitc.weekly.model.User;
import cn.caitc.weekly.service.UserService;

public class UserTest {

	private UserService userService;
	
    @Before
    public void before(){                                                                   
        try {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:conf/spring.xml"
                ,"classpath:conf/spring-mybatis.xml"});
        userService = (UserService) context.getBean("userServiceImpl");
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
    }
     
    @Test
    public void addUser(){
    	List<User> userList = userService.selectUser();
        System.out.println(userList.size());
    }
}
