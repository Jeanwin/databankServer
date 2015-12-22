package test.java.org.utmost.util;

import java.io.File;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.utmost.util.PathUtil;
@RunWith(PowerMockRunner.class)
public class PathUtilTest {
	PathUtil pathUtil ;
	@Before
	public void setUp(){
		pathUtil = PowerMockito.spy(new PathUtil());
		
	}
	
//	@SuppressWarnings("static-access")
//	@Test
//	@PrepareForTest(PathUtil.class)
//	public void testGetWebPath(){
//		String webPath = pathUtil.getWebPath();
//		Assert.assertNotNull(webPath);
//	}
	
	@SuppressWarnings("static-access")
	@Test
	@PrepareForTest(PathUtil.class)
	public void testGetClassPath(){
		String webPath = pathUtil.getClassPath();
		Assert.assertNotNull(webPath);
	}
	
	@SuppressWarnings("static-access")
	@Test
	@PrepareForTest(PathUtil.class)
	public void testGetClassFilePath(){
		String filePath = pathUtil.getClassFilePath();
		//System.out.println(filePath);
		Assert.assertNotNull(filePath);
	}
	
	@SuppressWarnings("static-access")
	@Test
	@PrepareForTest(PathUtil.class)
	public void testGetRootPath(){
		String rootPath = pathUtil.getRootPath();
		Assert.assertNull(rootPath);
	}
	
	@Test
	@PrepareForTest({PathUtil.class,System.class})
	public void testGetUploadPath() throws Exception{
		Properties prop = PowerMockito.mock(Properties.class);
		PowerMockito.whenNew(Properties.class).withAnyArguments().thenReturn(prop);
		PowerMockito.mockStatic(System.class);
		PowerMockito.when(System.getProperty(Mockito.anyString())).thenReturn("dev");
		PowerMockito.when(prop.getProperty(Mockito.anyString())).thenReturn("test/test01");
		File file = PowerMockito.mock(File.class);
		PowerMockito.whenNew(File.class).withAnyArguments().thenReturn(file);
		PowerMockito.when(file.exists()).thenReturn(false);
		
		String path = PathUtil.getUploadPath();
		Assert.assertNotNull(path);
	}
	
	@Test
	@PrepareForTest({PathUtil.class,System.class})
	public void testGetFileSaveRoot() throws Exception{
		Properties prop = PowerMockito.mock(Properties.class);
		PowerMockito.whenNew(Properties.class).withAnyArguments().thenReturn(prop);
		PowerMockito.mockStatic(System.class);
		PowerMockito.when(System.getProperty(Mockito.anyString())).thenReturn("dev");
		PowerMockito.when(prop.getProperty(Mockito.anyString())).thenReturn("test/test01");
		
		String savePath = PathUtil.getFileSaveRoot();
		Assert.assertNotNull(savePath);
	}
	
	@Test
	@PrepareForTest(PathUtil.class)
	public void testConvertEncoding(){
		String savePath = PathUtil.convertEncoding("testStr","gb2312","utf-8");
		Assert.assertNotNull(savePath);
	}
}
