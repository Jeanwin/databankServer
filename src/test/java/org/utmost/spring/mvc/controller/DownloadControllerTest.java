package test.java.org.utmost.spring.mvc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.utmost.common.SpringContext;
import org.utmost.spring.mvc.controller.DownloadController;
import org.utmost.util.PathUtil;

import com.vgc.databank.service.MonitorService;
import com.vgc.databank.util.DownloadListUtil;
import com.vgc.databank.util.Main;
@RunWith(PowerMockRunner.class)
public class DownloadControllerTest {
	DownloadController downloadController;
	HttpServletRequest request;
	HttpServletResponse response;
	@Before
	public void setUp(){
		downloadController = PowerMockito.spy(new DownloadController());
		request = PowerMockito.mock(HttpServletRequest.class);
		response = PowerMockito.mock(HttpServletResponse.class);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testToUtf8String(){
		String str = downloadController.toUtf8String("test");
		Assert.assertEquals("test",str);
	}
	@PrepareForTest({PathUtil.class,DownloadController.class})
	@Test
	public void testReadImage() throws Exception{
		PowerMockito.when(request.getParameter(Mockito.anyString())).thenReturn("test.txt");
		PowerMockito.mockStatic(PathUtil.class);
		PowerMockito.when(PathUtil.getUploadPath()).thenReturn("test/test02");
		FileInputStream fis = PowerMockito.mock(FileInputStream.class);
		PowerMockito.whenNew(FileInputStream.class).withAnyArguments().thenReturn(fis);
		ServletOutputStream os = PowerMockito.mock(ServletOutputStream.class);
		PowerMockito.when(response.getOutputStream()).thenReturn(os);
		PowerMockito.when(fis.read(Mockito.isA(byte[].class))).thenReturn(1).thenReturn(-1);
		downloadController.readImage(request, response);
	}
	@Test
	@PrepareForTest({SpringContext.class,PathUtil.class,DownloadController.class})
	public void testDownload() throws Exception{
		PowerMockito.when(request.getParameter("path")).thenReturn("test/test02");
		PowerMockito.when(request.getParameter("name")).thenReturn("downloadFile.pdf");
		PowerMockito.when(request.getParameter("uuid")).thenReturn("52414552445");
		PowerMockito.when(request.getParameter("author")).thenReturn("rita");
		PowerMockito.when(request.getParameter("useruuid")).thenReturn("1010001");
		PowerMockito.when(request.getParameter("last_modify_date_str")).thenReturn("2012-12-12");
		PowerMockito.when(request.getParameter("action")).thenReturn("view");
		
		MonitorService monitorService = PowerMockito.mock(MonitorService.class);
		PowerMockito.mockStatic(SpringContext.class);
		PowerMockito.when(SpringContext.getBean("MonitorService")).thenReturn(monitorService);
		DownloadListUtil downloadListUtil = PowerMockito.mock(DownloadListUtil.class);
		PowerMockito.when(SpringContext.getBean("DownloadListUtil")).thenReturn(downloadListUtil);
		PowerMockito.mockStatic(PathUtil.class);
		PowerMockito.when(PathUtil.getFileSaveRoot()).thenReturn("test/test02");
		FileInputStream fis = PowerMockito.mock(FileInputStream.class);
		PowerMockito.whenNew(FileInputStream.class).withAnyArguments().thenReturn(fis);
		ServletOutputStream sos = PowerMockito.mock(ServletOutputStream.class);
		PowerMockito.when(response.getOutputStream()).thenReturn(sos);
		PowerMockito.when(fis.read(Mockito.isA(byte[].class))).thenReturn(1).thenReturn(-1);
		downloadController.download(request, response);
		
		PowerMockito.when(request.getParameter("action")).thenReturn("download");
		PowerMockito.when(monitorService.getOriginalFileName(Mockito.anyString())).thenReturn("filename.pdf");
		downloadController.download(request, response);
		//file not found
		PowerMockito.when(fis.read(Mockito.isA(byte[].class))).thenThrow(new FileNotFoundException("junit test, don't care")).thenReturn(1).thenReturn(-1);
		downloadController.download(request, response);
	}
	@Test
	@PrepareForTest({DownloadController.class,PathUtil.class})
	public void testDownloadExcel() throws Exception{
		PowerMockito.when(request.getParameter("url")).thenReturn("test/test02/test.txt");
		FileInputStream fis = PowerMockito.mock(FileInputStream.class);
		PowerMockito.whenNew(FileInputStream.class).withAnyArguments().thenReturn(fis);
		ServletOutputStream sos = PowerMockito.mock(ServletOutputStream.class);
		PowerMockito.when(response.getOutputStream()).thenReturn(sos);
		PowerMockito.when(fis.read(Mockito.isA(byte[].class))).thenReturn(1).thenReturn(-1);
		downloadController.downloadExcel(request, response);
	}
	@Test
	@PrepareForTest({DownloadController.class,PathUtil.class})
	public void testZipDownload() throws Exception{
		PowerMockito.when(request.getParameter("url")).thenReturn("readme.txt");
		PowerMockito.mockStatic(PathUtil.class);
		PowerMockito.when(PathUtil.getWebPath()).thenReturn("test/test02");
		FileInputStream fis = PowerMockito.mock(FileInputStream.class);
		PowerMockito.whenNew(FileInputStream.class).withAnyArguments().thenReturn(fis);
		ServletOutputStream sos = PowerMockito.mock(ServletOutputStream.class);
		PowerMockito.when(response.getOutputStream()).thenReturn(sos);
		PowerMockito.when(fis.read(Mockito.isA(byte[].class))).thenReturn(1).thenReturn(-1);
		File file = PowerMockito.mock(File.class);
		PowerMockito.whenNew(File.class).withAnyArguments().thenReturn(file);
		PowerMockito.when(file.exists()).thenReturn(true);
		Main main = PowerMockito.mock(Main.class);
		PowerMockito.whenNew(Main.class).withAnyArguments().thenReturn(main);
		downloadController.zipDownload(request, response);
	}
	@Test
	@PrepareForTest({DownloadController.class,PathUtil.class})
	public void testDlzip() throws Exception{
		PowerMockito.when(request.getParameter("url")).thenReturn("readme.txt");
		PowerMockito.mockStatic(PathUtil.class);
		PowerMockito.when(PathUtil.getWebPath()).thenReturn("test/test02/");
		ServletOutputStream sos = PowerMockito.mock(ServletOutputStream.class);
		PowerMockito.when(response.getOutputStream()).thenReturn(sos);
		FileInputStream fis = PowerMockito.mock(FileInputStream.class);
		PowerMockito.whenNew(FileInputStream.class).withAnyArguments().thenReturn(fis);
		PowerMockito.when(fis.read(Mockito.isA(byte[].class))).thenReturn(2).thenReturn(-1);
		downloadController.dlzip(request, response);
	}
	@Test
	@PrepareForTest({DownloadController.class,PathUtil.class})
	public void testZip() throws Exception{
		File file = PowerMockito.mock(File.class);
		PowerMockito.whenNew(File.class).withAnyArguments().thenReturn(file);
		FileInputStream fis = PowerMockito.mock(FileInputStream.class);
		PowerMockito.whenNew(FileInputStream.class).withAnyArguments().thenReturn(fis);
		ServletOutputStream sos = PowerMockito.mock(ServletOutputStream.class);
		PowerMockito.when(response.getOutputStream()).thenReturn(sos);
		PowerMockito.when(fis.read(Mockito.isA(byte[].class))).thenReturn(1).thenReturn(-1);
		downloadController.zip(request, response);
		
		//file not found
		//takeTryCatch exception
		PowerMockito.when(fis.read(Mockito.isA(byte[].class))).thenThrow(new FileNotFoundException("junit test, don't care"));
		downloadController.zip(request, response);
		//takeTryCatch exception doesn't happen
		PowerMockito.when(fis.read(Mockito.isA(byte[].class))).thenThrow(new FileNotFoundException("junit test, don't care")).thenReturn(1).thenReturn(-1);
		PowerMockito.mockStatic(PathUtil.class);
		PowerMockito.when(PathUtil.getWebPath()).thenReturn("test/test02");
		downloadController.zip(request, response);
	}
	@Test
	@PrepareForTest({DownloadController.class,PathUtil.class})
	public void testFindImgSource() throws Exception{
		PowerMockito.when(request.getParameter("path")).thenReturn("test/test02");
		PowerMockito.when(request.getParameter("name")).thenReturn("file.txt");
		PowerMockito.mockStatic(PathUtil.class);
		PowerMockito.when(PathUtil.getWebPath()).thenReturn("test/");
		FileInputStream fis = PowerMockito.mock(FileInputStream.class);
		PowerMockito.whenNew(FileInputStream.class).withAnyArguments().thenReturn(fis);
		ServletOutputStream sos = PowerMockito.mock(ServletOutputStream.class);
		PowerMockito.when(response.getOutputStream()).thenReturn(sos);
		PowerMockito.when(fis.read(Mockito.isA(byte[].class))).thenReturn(1).thenReturn(-1);
		downloadController.findImgSource(request, response);
	}
}
