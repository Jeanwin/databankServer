package test.java.vgc.databank.util;
/**
 * 
 */

import java.io.IOException;
import java.util.HashMap;

import org.commontemplate.util.Assert;
import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.vgc.databank.util.ContactUtil;
/**
 * @author BULL
 *
 */
@SuppressWarnings({"rawtypes"})
@RunWith(PowerMockRunner.class)
public class ContactUtilTest {

	ContactUtil contactUtil ;
	@Before
	public void setUp() throws Exception {
		contactUtil = PowerMockito.spy(new ContactUtil());
	}
	
	@Test
	public void testParseFileName() throws DocumentException, IOException{
		HashMap map = contactUtil.saveContactUs("<contactUs>"
				+ "<name></name>"
				+ "<position>Technical Officer</position>"
				+ "<address>No.3A,Xi Liu Jie, Sanlitun Road Chaoyang District Beijing 100027, P. R. China</address>"
				+ "<phone>+86 10 6531 3761</phone>"
				+ "<mobile>+86 13436701108</mobile>"
				+ "<fax>+86 10 8532 2906</fax>"
				+ "<email>xiaolu.chen@volkswagen.com.cn</email>"
				+ "</contactUs>");
		Assert.assertEquals("success",map.get("isSuccess").toString());
	}
	
	@Test
	public void testObtainContactInfo() throws DocumentException{
		contactUtil.obtainContactInfo();
	}
	

}
