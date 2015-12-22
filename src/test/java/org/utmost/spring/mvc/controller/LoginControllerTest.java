package test.java.org.utmost.spring.mvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.utmost.common.SpringContext;
import org.utmost.spring.mvc.controller.LoginController;

import com.vgc.databank.service.MonitorService;
@RunWith(PowerMockRunner.class)
public class LoginControllerTest {
	LoginController loginController;
	HttpServletRequest request;
	HttpServletResponse response;
	MonitorService monitorService;
	@Before
	public void setUp(){
		loginController = PowerMockito.spy(new LoginController());
		request = PowerMockito.mock(HttpServletRequest.class);
		response = PowerMockito.mock(HttpServletResponse.class);
		monitorService = PowerMockito.mock(MonitorService.class);
	}
	
	@Test
	@PrepareForTest(SpringContext.class) 
	public void testLogin() throws IOException{
		PowerMockito.mockStatic(SpringContext.class);
		
		PowerMockito.when(SpringContext.getBean("MonitorService")).thenReturn(monitorService);
		
		HttpSession session = PowerMockito.mock(HttpSession.class);
		PowerMockito.when(request.getSession()).thenReturn(session);
		PowerMockito.when(session.getId()).thenReturn("");
		PowerMockito.when(request.getParameter("useruuid")).thenReturn("aa");
		loginController.login(request,response);
	}
}
