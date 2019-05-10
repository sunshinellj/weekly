package cn.caitc.weekly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.caitc.weekly.dao.UserDao;
import cn.caitc.weekly.model.User;
import cn.caitc.weekly.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
    private UserDao userDao;
	
	public List<User> selectUser() {
		return userDao.selectUser();
	}

	public int insertUser(User user) {
		return userDao.insertUser(user);
	}

}
