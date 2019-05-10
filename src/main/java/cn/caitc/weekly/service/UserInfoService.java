package cn.caitc.weekly.service;

import cn.caitc.weekly.model.UserInfo;

import java.util.Date;
import java.util.List;

/**
 * The interface User info service.
 */
public interface UserInfoService {
    /**
     * 查询用户信息
     *
     * @param userId 用户号
     * @return the user info
     */
    UserInfo selectUserInfo(String userId);

    /**
     * Select user info.
     *
     * @param userId 用户号
     * @param userName 用户名
     * @param groupId 部门ID
     * @return the list
     */
    List<UserInfo> selectUserInfoBySearch(String userId, String userName, String groupId);


    /**
     * Insert user. 添加员工
     *
     * @param userId the user id
     * @param userName the user name
     * @param groupId the group id
     * @param orderNo the order no
     * @param authority the authority
     * @param inserterId the inserter id
     * @param insertTime the insert time
     */
    void insertUser(String userId, String userName, String groupId,int orderNo,String authority,String inserterId,Date insertTime);

    /**
     * Update user. 更新员工信息
     *
     * @param userId the user id
     * @param userName the user name
     * @param groupId the group id
     * @param authority the authority
     * @param updaterId the updater id
     * @param updateTime the update time
     */
    void updateUser(String userId, String userName, String groupId,String authority,String updaterId,Date updateTime);

    /**
     * Delete user.删除员工
     *
     * @param userId the user id
     */
    void deleteUser(String userId);

    /**
     * Select max order.
     *
     * @param groupId the group id
     * @return the user info
     */
    UserInfo selectMaxOrder(String groupId);

}
