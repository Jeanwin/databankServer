package test.java.org.utmost.portal.service;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.utmost.portal.service.RuleEngine;
@RunWith(PowerMockRunner.class)
public class RuleEngineTest {
	RuleEngine ruleEngine ;
	@Before
	public void setUp() throws IOException{
		ruleEngine = PowerMockito.spy(new RuleEngine());
	}
	
	@Test
	@PrepareForTest(RuleEngine.class)
	public void testExecFile() throws Exception{
		PowerMockito.mockStatic(RuleEngine.class);
		String str = RuleEngine.execFile("test");
		Assert.assertNull(str);
	}
	
	@Test
	@PrepareForTest(RuleEngine.class)
	public void testExec() throws Exception{
		PowerMockito.mockStatic(RuleEngine.class);
		String str = RuleEngine.exec("test");
		Assert.assertNull(str);
	}
	
}
