package test.java.org.utmost.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.utmost.util.InputStreamUtil;
import org.utmost.util.PathUtil;

public class InputStreamUtilTest {
	InputStreamUtil inputStreamUtil ;
	@Before
	public void setUp(){
		inputStreamUtil = PowerMockito.spy(new InputStreamUtil());
	}
	
	@SuppressWarnings("static-access")
	@Test
	@PrepareForTest(PathUtil.class)
	public void testToByte() throws Exception{
		String str = this.getClass().getClassLoader().getResource("hibernate.properties").getFile();
		File file = new File(str);   
        InputStream in = new FileInputStream(file);  
        byte[] b = inputStreamUtil.toByte(in);
        System.out.println(new String(b));   
		Assert.assertNotNull(b);
	}
	
	@SuppressWarnings("static-access")
	@Test
	@PrepareForTest(PathUtil.class)
	public void testToInputStream() throws Exception{
		String str = this.getClass().getClassLoader().getResource("hibernate.properties").getFile();
		File file = new File(str);      
        InputStream in = new FileInputStream(file);  
        byte[] b = inputStreamUtil.toByte(in);
        InputStream inputStream = inputStreamUtil.toInputStream(b);
		Assert.assertNotNull(inputStream);
	}
	
}
