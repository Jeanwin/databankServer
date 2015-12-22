package test.java.org.utmost.util.cmd;
/**
 * 
 */

import org.commontemplate.util.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.utmost.util.cmd.ReadProperties;
/**
 * @author BULL
 *
 */
@RunWith(PowerMockRunner.class)
public class ReadPropertiesTest {

	ReadProperties readProperties ;
	@Before
	public void setUp() throws Exception {
		readProperties = PowerMockito.spy(new ReadProperties());
	}
	
	@Test
	public void testParseFileName(){
		String str = readProperties.getValue("org/utmost/util/cmd/cmd.properties", "rarpath");
		Assert.assertNotNull(str);
	}
	

}
