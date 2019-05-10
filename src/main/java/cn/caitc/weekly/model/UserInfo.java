package cn.caitc.weekly.model;

import java.util.Date;

public class UserInfo {

	// 周报番号
	private String userId;

	// 周报番号
	private String userName;

	// 部门号
	private String groupId;

	// 部门名称
	private String groupName;

	// 表示番号
	private int orderNo;

	// 权限级别
	private String authority;

	//添加人
	private String inserterId;

	//添加时间
	private Date insertTime;

	//更新者
	private String updaterId;

	//更新时间
	private Date updateTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getInserterId() {
		return inserterId;
	}

	public void setInserterId(String inserterId) {
		this.inserterId = inserterId;
	}



	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
}
