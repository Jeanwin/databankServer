/**
 * LoginServlet.java
 * com.vgc.databank.service
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 Nov 20, 2009 		Administrator
 *
 * Copyright (c) 2009, TNT All Rights Reserved.
 */

package com.vgc.databank.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ClassName:LoginServlet Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 * 
 * @author Administrator
 * @version
 * @since Ver 1.1
 * @Date Nov 20, 2009 1:28:56 PM
 * 
 * @see
 * @deprecated
 */
public class LoginServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
		return;
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("gb2312");
		response.setContentType("text/html");
		String loginname = request.getParameter("loginname");
		String password = request.getParameter("password");
		//if username and password both are not null, verify username and password if are right,
		//if both are right, put user info into session,
		//if wrong, return to login page
		if (loginname != null && password != null) {
			
		} else {
			response.sendRedirect("login.htm");
			return;
		}
		Map<String, String> usermap = new HashMap<String, String>();
		usermap.put("loginname", loginname);
		usermap.put("password", password);
		request.getSession(true).setAttribute("usermap", usermap);
	}
	/**
	 * check user login
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void checklogin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String usermap = (String) session.getAttribute("usermap");
		System.out.println("useruuid>>" + usermap);
		PrintWriter out = response.getWriter();
		out.print(usermap);
		out.flush();
		out.close();
	}
}
