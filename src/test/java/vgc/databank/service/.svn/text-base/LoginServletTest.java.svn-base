package test.java.vgc.databank.service;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.vgc.databank.service.LoginServlet;
@RunWith(PowerMockRunner.class)
public class LoginServletTest {
	LoginServlet loginServlet ;
	HttpServletRequest request;
	HttpServletResponse response;
	HttpSession session;
	@Before
	public void setUp(){
		loginServlet = PowerMockito.spy(new LoginServlet());
		request = PowerMockito.mock(HttpServletRequest.class);
		response = PowerMockito.mock(HttpServletResponse.class);
		session = PowerMockito.mock(HttpSession.class);
	}
	
	@Test
	@PrepareForTest(LoginServlet.class)
	public void testDoGet() throws Exception{
		PowerMockito.mockStatic(LoginServlet.class);
		
		loginServlet.doGet(request,response);
	}
	
	@Test
	@PrepareForTest(LoginServlet.class)
	public void tesDoPost() throws Exception{
		PowerMockito.mockStatic(LoginServlet.class);
		PowerMockito.when(request.getParameter("loginname")).thenReturn("test");
		PowerMockito.when(request.getParameter("password")).thenReturn("test");
		PowerMockito.when(request.getSession(true)).thenReturn(session);

		loginServlet.doPost(request,response);
	}
	
	@Test
	@PrepareForTest(LoginServlet.class)
	public void testChecklogin() throws Exception{
		PrintWriter PrintWriter = PowerMockito.mock(PrintWriter.class);
		
		PowerMockito.mockStatic(LoginServlet.class);
		PowerMockito.when(request.getSession(false)).thenReturn(session);
		PowerMockito.when(session.getAttribute("usermap")).thenReturn("test");
		PowerMockito.when(response.getWriter()).thenReturn(PrintWriter);

		loginServlet.checklogin(request,response);
	}
	
}
