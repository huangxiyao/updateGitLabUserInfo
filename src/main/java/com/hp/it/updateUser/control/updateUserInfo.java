package com.hp.it.updateUser.control;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hp.it.updateUser.dao.UserDao;

/**
 * Servlet implementation class updateUserInfo
 */
public class updateUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateUserInfo() {
    	
        super();
        System.out.println("------------------userInfo构造---------------");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("------------------get方法---------------");
		String email = "";
		UserDao up = new UserDao();
		int flag = 0;
		flag = up.updateUserInfo(email);
		PrintWriter out = response.getWriter();
		if(flag>1){
			out.println("update successfull!");
		}else{
			out.println("update failed!");
		}
        out.close();
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("------------------post方法---------------");
	}

}
