package cn.caitc.weekly.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import cn.caitc.weekly.dao.UserInfoDao;
import cn.caitc.weekly.model.UserInfo;
import cn.caitc.weekly.service.UserInfoService;

/**
 * The type User info service impl.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * 查询用户信息
     *
     * @param userId 用户号
     */
    public UserInfo selectUserInfo(String userId) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        return userInfoDao.selectUserInfo(userInfo);
    }

    /**
     * Select user info.
     *
     * @param userId   用户号
     * @param userName 用户名
     * @param groupId  部门ID
     * @return the list
     */
    public List<UserInfo> selectUserInfoBySearch(String userId, String userName, String groupId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserName(userName);
        userInfo.setGroupId(groupId);
        return userInfoDao.selectUserInfoBySearch(userInfo);
    }

    /**
     * Insert user. 添加员工
     *
     * @param userId     the user id
     * @param userName   the user name
     * @param groupId    the group id
     * @param orderNo    the order no
     * @param authority  the authority
     * @param inserterId the inserter id
     * @param insertTime the insert time
     */
    public void insertUser(String userId, String userName, String groupId, int orderNo, String authority, String inserterId, Date insertTime) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserName(userName);
        userInfo.setGroupId(groupId);
        userInfo.setAuthority(authority);
        userInfo.setOrderNo(orderNo);
        userInfo.setInserterId(inserterId);
        userInfo.setInsertTime(insertTime);
        userInfoDao.insertUser(userInfo);
    }

    /**
     * Update user. 更新员工信息
     *
     * @param userId     the user id
     * @param userName   the user name
     * @param groupId    the group id
     * @param authority  the authority
     * @param updaterId  the updater id
     * @param updateTime the update time
     */
    public void updateUser(String userId, String userName, String groupId, String authority, String updaterId, Date updateTime) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserName(userName);
        userInfo.setGroupId(groupId);
        userInfo.setAuthority(authority);
        userInfo.setUpdaterId(updaterId);
        userInfo.setUpdateTime(updateTime);
        userInfoDao.updateUser(userInfo);
    }

    /**
     * Delete user.删除员工
     *
     * @param userId the user id
     */
    public void deleteUser(String userId){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfoDao.deleteUser(userInfo);
    }
    /**
     * Select max order.
     *
     * @param groupId the group id
     * @return the user info
     */
    public UserInfo selectMaxOrder(String groupId){
        UserInfo userInfo = new UserInfo();
        userInfo.setGroupId(groupId);

        return userInfoDao.selectMaxOrder(userInfo);
    }
}
