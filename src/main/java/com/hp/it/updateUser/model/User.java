package com.hp.it.updateUser.model;

public class User {

	/**
	 * �û�id
	 */
	private int id;
	
	/**
	 * �û�email
	 */
	private String email;
	
	/**
	 * �������ݿ��е�extern_uid;
	 */
	private String extern_uid;
	
	/**
	 * ldap�е�extern_uid�뱾�����ݿ��е�uid�Ƿ�һ�£�Ĭ��0��һ�µ�
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
