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
import org.utmost.portal.service.RuleService;
@RunWith(PowerMockRunner.class)
public class RuleServiceTest {
	RuleService ruleService ;
	@Before
	public void setUp() throws IOException{
		ruleService = PowerMockito.spy(new RuleService());
	}
	
	@Test
	@PrepareForTest(RuleEngine.class)
	public void testExec() throws Exception{
		PowerMockito.mockStatic(RuleEngine.class);
		String str = ruleService.exec("test");
		Assert.assertNull(str);
	}
	
}
