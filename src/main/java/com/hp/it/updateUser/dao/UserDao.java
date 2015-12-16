package com.hp.it.updateUser.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.it.updateUser.model.User;
import com.hp.it.util.Configure;
import com.hp.it.util.Dbutil;



public class UserDao {
	
	private static final Logger log = LoggerFactory.getLogger(UserDao.class);
	
	public static int updateUserInfo(String email){
		email = "xi-yaoh@hpe.com";
		System.out.println(email);
		String url = Configure.get("jdbc");    
	    String username = Configure.get("username");   
	    String password = Configure.get("dbPasswd");
	    Connection con = null;
	    Statement stat = null;  
        ResultSet rs = null; 
	    try{   
	    	Class.forName("com.mysql.jdbc.Driver");
	    	con =  DriverManager.getConnection(url , username , password );   
	        stat=con.createStatement();  
            rs = stat.executeQuery("SELECT * FROM users WHERE email = '"+email +"'");  
            if(rs!=null){  
                rs.first();  
                String content=rs.getString(rs.findColumn("email")); 
                String newContent = content;
                int uid = rs.getInt(rs.findColumn("id"));
                log.info("email:"+content);;
                int lastIndex = content.lastIndexOf("hp.com");
                if(lastIndex > 0){
                	newContent = content.replace("hp.com", "hpe.com");
                    int u = stat.executeUpdate("UPDATE users set email = '"+ newContent +"' where id = '"+uid+"'");
                    updateIdentities(uid);
                }
            }else {
            	return 0;
            }
	       }catch(SQLException se){   
	        se.printStackTrace() ;   
	     } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(stat != null){
				try {
					stat.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	    return 0;
	}
	
	public static int updateIdentities(int user_id){
		user_id = 280;
		String url = "jdbc:mysql://C0004716.itcs.hp.com:3306/gitlab4721" ;    
	    String username = "gitlab" ;   
	    String password = "gitlab" ;
	    Connection con = null;
	    Statement stat = null;  
        ResultSet rs = null; 
	    try{   
	    	Class.forName("com.mysql.jdbc.Driver");
	    	con =  DriverManager.getConnection(url , username , password ) ;   
	        stat=con.createStatement();  
            rs = stat.executeQuery("SELECT * FROM identities WHERE user_id = "+user_id);  
            if(rs!=null){  
                rs.first();  
                String extern_uid=rs.getString(rs.findColumn("extern_uid")); 
                String newContent = extern_uid;
                System.out.println(newContent);
                int lastIndex = extern_uid.lastIndexOf("hp.com");
                if(lastIndex > 0){
                	newContent = extern_uid.replaceAll("hp.com", "hpe.com");
                }
                System.out.println();
                int u = stat.executeUpdate("UPDATE identities set extern_uid = '"+ newContent +"' where user_id = " + user_id);
                System.out.println(u);  
            }else {
            	return 0;
            }
	       }catch(SQLException se){   
	        System.out.println("数据库连接失败！");   
	        se.printStackTrace() ;   
	     } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(stat != null){
				try {
					stat.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	    return 0;
	}
	
	public List<User> userList(){
		String sql = "select id, email from users order by id asc";
		ResultSet rs = Dbutil.select(sql);
		log.info(sql);
		List<User> userList = new ArrayList<User>();
		try {
			while(rs.next()){
				userList.add(new User(rs.getInt(rs.findColumn("id")), rs.getString(rs.findColumn("email")), null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Dbutil.close(null, null, rs);
		}
		return userList;
	}
	
	public int updateUser(List<User> userList){
		Connection con = Dbutil.getConnetcion();
		Statement stat = null;
		int u = 1;
		try {
			stat = con.createStatement();
			for(User user : userList){
				String sql = "update users set email = '"+user.getEmail()+"',notification_email = '"+user.getEmail()+"' where id = "+user.getId();
				 int i = stat.executeUpdate(sql);
				 log.info(sql);
				 if(i == 0){
					 u = i;
				 }
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Update users failed!", e);
		}finally{
			Dbutil.close(con, stat, null);
		}
		return u;
	}
	
	public int updateIdentities(List<User> userList) {
		Connection con = Dbutil.getConnetcion();
		Statement stat = null;
		int u = 1;
		try {
			stat = con.createStatement();
			for(User user : userList){
				String sql = "update identities set extern_uid = '"+user.getExtern_uid()+"' where user_id = "+user.getId();
				 int i = stat.executeUpdate(sql);
				 log.info(sql);
				 if(i == 0){
					 u = i;
				 }
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Update Identities failed!", e);
		}finally{
			Dbutil.close(con, stat, null);
		}
		return u;
	}
	
	public static void main(String[] args) {
		updateUserInfo("");
	   
	}
}
