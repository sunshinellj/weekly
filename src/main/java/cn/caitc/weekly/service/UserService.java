package cn.caitc.weekly.service;

import java.util.List;

import cn.caitc.weekly.model.User;

public interface UserService {
	
	public List<User> selectUser();
	
	public int insertUser(User user);
	
}
