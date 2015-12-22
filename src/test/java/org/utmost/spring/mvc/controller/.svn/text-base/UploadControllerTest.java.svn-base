package test.java.org.utmost.spring.mvc.controller;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.utmost.spring.mvc.controller.UploadController;
import org.utmost.util.PathUtil;
@RunWith(PowerMockRunner.class)  

@SuppressWarnings("rawtypes")
public class UploadControllerTest {
	UploadController uploadController;
	HttpServletRequest request;
	HttpServletResponse response;
	DiskFileItemFactory factory;
	ServletFileUpload upload;
	File file;
	@Before
	public void setUp(){
		uploadController = PowerMockito.spy(new UploadController());
		request = PowerMockito.mock(HttpServletRequest.class);
		response = PowerMockito.mock(HttpServletResponse.class);
		factory = PowerMockito.mock(DiskFileItemFactory.class);
		upload = PowerMockito.mock(ServletFileUpload.class);
		file = PowerMockito.mock(File.class);
	}
	
	@Test
	@PrepareForTest({UploadController.class,PathUtil.class}) 
	public void testSingleFileUpload() throws Exception{
		List list = new ArrayList();
		PowerMockito.when(request.getParameter("fileName")).thenReturn("file"); 
		PowerMockito.whenNew(DiskFileItemFactory.class).withNoArguments().thenReturn(factory);
		PowerMockito.whenNew(ServletFileUpload.class).withArguments(factory).thenReturn(upload);
		PowerMockito.when(upload.parseRequest(request)).thenReturn(list);
		
		Properties props = PowerMockito.mock(Properties.class);
		PowerMockito.whenNew(Properties.class).withAnyArguments().thenReturn(props);
		PowerMockito.when(props.getProperty(Mockito.anyString())).thenReturn("test/test01");
		uploadController.singleFileUpload(request,response);
	}
	
	@Test
	@PrepareForTest({UploadController.class,PathUtil.class,System.class}) 
	public void testUpload() throws Exception{
		PowerMockito.mockStatic(PathUtil.class);
		PowerMockito.when(PathUtil.getUploadPath()).thenReturn("test/test01");
		String path = "test/test01";
		PowerMockito.when(request.getParameter("fileName")).thenReturn("fileName"); 
		PowerMockito.whenNew(File.class).withArguments(path).thenReturn(file);
		PowerMockito.when(file.exists()).thenReturn(true); 
		PowerMockito.when(uploadController.getFileUpload()).thenReturn(upload);
		
		List<FileItem> list = new ArrayList<FileItem>();
		DiskFileItem file1 = new DiskFileItem(path, path, false, path, 0, file);
		DiskFileItem file2 = new DiskFileItem(path, path, false, path, 0, file);
		DiskFileItem file3 = new DiskFileItem(path, path, false, path, 0, file);
		list.add(file1);
		list.add(file2);
		list.add(file3);
		PowerMockito.when(upload.parseRequest(request)).thenReturn(list);
		uploadController.upload(request,response);
	}
	
	
	@Test
	@PrepareForTest({UploadController.class,PathUtil.class}) 
	public void testUploadARC() throws Exception{
		PowerMockito.mockStatic(PathUtil.class);
		PowerMockito.when(PathUtil.getUploadPath()).thenReturn("test/test01");
		
		String path = "test/test01"+ "/upload";
		PowerMockito.when(request.getParameter("fileName")).thenReturn("fileName"); 
		PowerMockito.when(response.getWriter()).thenReturn(PowerMockito.mock(PrintWriter.class)); 

		PowerMockito.whenNew(File.class).withArguments(path).thenReturn(file);
		PowerMockito.when(file.exists()).thenReturn(true); 
		PowerMockito.when(uploadController.getFileUpload()).thenReturn(upload);
		List<FileItem> list = new ArrayList<FileItem>();
		DiskFileItem file1 = new DiskFileItem(path, path, false, path, 0, file);
		DiskFileItem file2 = new DiskFileItem(path, path, false, path, 0, file);
		DiskFileItem file3 = new DiskFileItem(path, path, false, path, 0, file);
		list.add(file1);
		list.add(file2);
		list.add(file3);
		PowerMockito.when(upload.parseRequest(request)).thenReturn(list);
	
		uploadController.uploadARC(request,response);
	}
	
	@Test
	@PrepareForTest(UploadController.class) 
	public void testUploadVGC() throws Exception{
		String path = PathUtil.getRootPath()+ "/upload";
		PowerMockito.when(request.getContextPath()).thenReturn("ContextPath"); 
		PowerMockito.when(response.getWriter()).thenReturn(PowerMockito.mock(PrintWriter.class)); 

		PowerMockito.whenNew(File.class).withArguments(path).thenReturn(file);
		PowerMockito.when(file.exists()).thenReturn(true); 
		PowerMockito.when(uploadController.getFileUpload()).thenReturn(upload);
		List<FileItem> list = new ArrayList<FileItem>();
		DiskFileItem file1 = new DiskFileItem("aaa.a", path, false, "aaa.a", 0, file);
		DiskFileItem file2 = new DiskFileItem("bbb.b", path, false, "bbb.b", 0, file);
		DiskFileItem file3 = new DiskFileItem("ccc.c", path, false, "ccc.c", 0, file);
		list.add(file1);
		list.add(file2);
		list.add(file3);
		PowerMockito.when(upload.parseRequest(request)).thenReturn(list);
	
		uploadController.uploadVGC(request,response);
	}
}
