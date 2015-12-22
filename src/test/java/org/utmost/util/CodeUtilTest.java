package test.java.org.utmost.util;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.utmost.util.CodecUtil;

public class CodeUtilTest {
	CodecUtil codecUtil ;
	@Before
	public void setUp(){
		codecUtil = PowerMockito.spy(new CodecUtil());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testMd5Hex(){
		String str = codecUtil.md5Hex("123456");
		Assert.assertEquals("e10adc3949ba59abbe56e057f20f883e",str);
	}
}
