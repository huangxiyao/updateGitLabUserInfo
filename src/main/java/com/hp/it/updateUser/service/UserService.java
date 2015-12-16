package com.hp.it.updateUser.service;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.it.updateUser.dao.UserDao;
import com.hp.it.updateUser.model.User;
import com.hp.it.util.Configure;
import com.hp.it.util.LdapConnection;

public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	private UserDao userDao = new UserDao();
	
	
	//从数据库中获取当前的所有用户信息
	public List<User> getUserList(){
		return userDao.userList();
	}
	
	//读取当前用户的信心并去ldap中执行查询,并获取在ldap系统中已经变为hpe的用户信息
	public List<User> queryLdapUser(List<User> userList){
		log.info("execute the ldap query");
		//去除邮件后缀已经是hpe的用户
		Iterator<User> userItor = userList.iterator();
		log.info("remove the user which the user's email address endup with '@hpe.com'");
		while(userItor.hasNext()){
			User user = userItor.next();
			if(user.getEmail().lastIndexOf("@hpe.com") > 0){
				userItor.remove();
			}else{
				//初始化用户邮件地址没有发生变化
				user.setFlag(0);
			}
		}
		//获取ldap连接
		LdapConnection con = null;
		try {
			con = new LdapConnection(Configure.get("initContextFactory"), Configure.get("providerUrl"), "uid=xi-yaoh@hp.com,ou=People,o=hp.com", "hxy@1988");
			//查询剩余用户在ladp服务器中的名字信息
			for(User user : userList){
				String filter = user.getEmail().replace("@hp.com", "@hpe.com");
				filter = "(uid="+filter+")";
				NamingEnumeration results;
				log.debug("filter:"+filter);
				results = con.search(Configure.get("baseDn"), filter);
				if (results.hasMoreElements()) {
		            SearchResult searchResults = (SearchResult) (results.next());
		            user.setEmail(searchResults.getAttributes().get("mail").get(searchResults.getAttributes().get("mail").size()-1).toString());
		            user.setFlag(1);
		            user.setExtern_uid("uid="+user.getEmail()+","+Configure.get("baseDn"));
		            log.debug("email:"+user.getEmail()+"   userDn:"+user.getExtern_uid());
		        }
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
			throw new RuntimeException("LdapConnection failed!",e);
		}finally{
			if(con != null){
				con.closeConnection();
			}
		}
		//将邮件地址没有发生改变的移除userList
		Iterator<User> userItorTmp = userList.iterator();
		log.info("remove the user where email address is not changed in ldap!");
		while(userItorTmp.hasNext()){
			User user = userItorTmp.next();
			if(user.getFlag() == 0){
				userItorTmp.remove();
			}
		}
		return userList;
	}
	
	public int updateUserInfo(List<User> userList){
		int i = 0;
		//更新user表
		i = userDao.updateUser(userList);
		//更新identities表
		if(i > 0){
			log.info("update user successful");
		}else{
			log.info("no user to update");
		}
		
		return i;
	}
	
	public int updateIdentities(List<User> userList){
		int i = 0;
		i = userDao.updateIdentities(userList);
		if(i > 0){
			log.info("update identities successful");
		}else{
			log.info("no identities to update");
		}
		return i;
	}
	
	public static void main(String[] args) {
		TimerTask task = new TimerTask() {  
            @Override  
            public void run() {  
                // task to run goes here  
            	log.info("start to execute the program!");
        		UserService us = new UserService();
        		List<User> userList = us.getUserList();
        		//执行ldap查询
        		us.queryLdapUser(userList);
        		log.info("print users who email adress has changed!");
        		for(User user : userList){
        			log.debug(user.getEmail()+"  flag:"+ user.getFlag()+"====="+user.getExtern_uid());
        		}
        		//更新users
        		us.updateUserInfo(userList);
        		//更新表identities
        		us.updateIdentities(userList);
        		log.info("finished");
            }  
        };  
        Timer timer = new Timer();  
        long delay = 0;  
        long intevalPeriod = Long.parseLong(Configure.get("intevalPeriod"));  
        // schedules the task to be run in an interval  
        timer.scheduleAtFixedRate(task, delay, intevalPeriod); 
	}
}
