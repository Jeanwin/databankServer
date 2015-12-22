package test.java.org.utmost.util.cmd;
/**
 * 
 */

import org.commontemplate.util.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.utmost.util.cmd.RarToFile;
/**
 * @author BULL
 *
 */
@RunWith(PowerMockRunner.class)
public class RarToFileTest {

	RarToFile rarToFile ;
	@Before
	public void setUp() throws Exception {
		rarToFile = PowerMockito.spy(new RarToFile());
	}
	
	@Test
	@PrepareForTest(RarToFile.class)
	public void testParseFileName(){
		String str = RarToFile.getCmdPath();
		Assert.assertNotNull(str);
	}
	
	@Test
	@PrepareForTest(RarToFile.class)
	public void testRARFile(){
		RarToFile.RARFile("","","");
	}
	

}
