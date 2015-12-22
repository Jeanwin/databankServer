package test.java.org.utmost.util;

import java.util.HashMap;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.utmost.util.ClassUtil;

public class ClassUtilTest {
	//ClassUtil classUtil ;
	@Before
	public void setUp() throws Exception {
		//classUtil = PowerMockito.spy(new ClassUtil());
		
	}
	
	public Object testThis(HashMap hm) {
		return hm;
	}
	
	public int testThat() {
		return 1;
	}
	
	@Test
	public void testInvokeMethod() throws Exception{
		HashMap hm = new HashMap();
		hm.put("dd", "asdf");
		ClassUtil cu = new ClassUtil();
		ClassUtilTest test = new ClassUtilTest();
		HashMap result = (HashMap) ClassUtil.invokeMethod(test, "testThis", hm);
		Assert.assertEquals(result, hm);
		int resultWithNull = (Integer) ClassUtil.invokeMethod(test, "testThat", null);
		Assert.assertEquals(1, resultWithNull);
	}
}
