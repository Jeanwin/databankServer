package org.utmost.spring.mvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.utmost.common.SpringContext;

import com.vgc.databank.service.MonitorService;

public class LoginController extends MultiActionController {
	
	@Autowired
	private MonitorService monitorService;
	
	
	/**
	 * login check
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void login(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");	//set encoding 'utf-8', prevent messy code
		response.setCharacterEncoding("UTF-8");	//set encoding 'utf-8', prevent messy code
		request.getSession().getId();
		String user_UUID = request.getParameter("useruuid");
		String IP = null;
		String sessionID = null;
		if(user_UUID!=null&&!"".equals(user_UUID)&&!"777".equals(user_UUID)){
			IP = request.getRemoteAddr();
			sessionID=request.getSession().getId();
			MonitorService monitorService = (MonitorService)SpringContext.getBean("MonitorService");  //get monitorService from spring context
			monitorService.recordLogInfo(user_UUID, sessionID, IP);			
		}
	}
}
