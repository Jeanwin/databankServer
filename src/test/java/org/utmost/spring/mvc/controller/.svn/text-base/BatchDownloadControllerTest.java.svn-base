package test.java.org.utmost.spring.mvc.controller;

import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.utmost.spring.mvc.controller.BatchDownloadController;

public class BatchDownloadControllerTest {
	BatchDownloadController batchDownloadController;
	HttpServletRequest request;
	HttpServletResponse response;
	@Before
	public void setUp(){
		batchDownloadController = PowerMockito.spy(new BatchDownloadController());
		request = PowerMockito.mock(HttpServletRequest.class);
		response = PowerMockito.mock(HttpServletResponse.class);
	}
	
	@Test
	public void testDload() throws Exception{
		ServletOutputStream out = PowerMockito.mock(ServletOutputStream.class);
		FileInputStream is = PowerMockito.mock(FileInputStream.class);
		
		PowerMockito.when(response.getOutputStream()).thenReturn(out);
		PowerMockito.whenNew(FileInputStream.class).withArguments(PowerMockito.mock(String.class)).thenReturn(is);
		batchDownloadController.dload(request,response);
	}
}
