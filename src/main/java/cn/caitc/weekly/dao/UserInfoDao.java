package cn.caitc.weekly.dao;

import cn.caitc.weekly.model.UserInfo;

import java.util.List;

public interface UserInfoDao {

    /**
     * 简单查询用户
     *
     * @return 查询结果
     */
    UserInfo selectUserInfo(UserInfo userInfo);

    /**
     * Select user info by search.
     *
     * @param userInfo the user info
     * @return the list
     */
    List<UserInfo> selectUserInfoBySearch(UserInfo userInfo);

    /**
     * Insert user.添加新员工
     *
     * @param userInfo the user info
     */
    void insertUser(UserInfo userInfo);

    /**
     * Update user.更新员工
     *
     * @param userInfo the user info
     */
    void updateUser(UserInfo userInfo);

    /**
     * Delete user.删除员工
     *
     * @param userInfo the user info
     */
    void deleteUser(UserInfo userInfo);

    /**
     * Select max order. 查询指定部门员工最大的orderNo
     *
     * @param userInfo the user info
     * @return the user info
     */
    UserInfo selectMaxOrder(UserInfo userInfo);
}
