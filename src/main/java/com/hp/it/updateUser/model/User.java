package com.hp.it.updateUser.model;

public class User {

	/**
	 * 用户id
	 */
	private int id;
	
	/**
	 * 用户email
	 */
	private String email;
	
	/**
	 * 本地数据库中的extern_uid;
	 */
	private String extern_uid;
	
	/**
	 * ldap中的extern_uid与本地数据库中的uid是否一致，默认0是一致的
	 */
	private int flag = 0;
	
	public User(){
		
	}
	
	public User(int id,String email, String extern_uid){
		this.id = id;
		this.email = email;
		this.extern_uid = extern_uid;
	}

	public int getId() {
		return id;
	}

	public void setId(int Id) {
		this.id = Id;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getExtern_uid() {
		return extern_uid;
	}

	public void setExtern_uid(String extern_uid) {
		this.extern_uid = extern_uid;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
}
