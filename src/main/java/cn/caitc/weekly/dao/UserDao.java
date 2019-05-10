package cn.caitc.weekly.dao;

import java.util.List;

import cn.caitc.weekly.model.User;

public interface UserDao {

    /**
     * 检索用户信息
     * 
     * @return 用户信息
     */
    List<User> selectUser();

    /**
     * 登录用户信息
     * 
     * @param user User
     * @return 登录结果
     */
    int insertUser(User user);
    
}
