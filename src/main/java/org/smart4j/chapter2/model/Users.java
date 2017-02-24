package org.smart4j.chapter2.model;

public class Users {

	private Integer Id;
	private String userName;
	private String loginName;
	private String userPassword;
	private String userLevel;
	private String userLock;
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}
	public String getUserLock() {
		return userLock;
	}
	public void setUserLock(String userLock) {
		this.userLock = userLock;
	}
	@Override
	public String toString() {
		return "Users [Id=" + Id + ", userName=" + userName + ", logName=" + loginName + ", userPassword=" + userPassword
				+ ", userLevel=" + userLevel + ", userLoc=" + userLock + "]";
	}
	
}
