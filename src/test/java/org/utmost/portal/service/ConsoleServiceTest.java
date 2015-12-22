package test.java.org.utmost.portal.service;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.utmost.portal.service.ConsoleService;

public class ConsoleServiceTest {
	ConsoleService consoleService ;
	@Before
	public void setUp(){
		consoleService = PowerMockito.spy(new ConsoleService());
	}
	
	@Test
	public void testStartConsole() throws Exception{
		consoleService.startConsole();
	}
	
	@Test
	public void testStopConsole() throws Exception{
		consoleService.stopConsole();
	}
	
	@Test
	public void testClear() throws Exception{
		consoleService.clear();
	}
	
	@Test
	public void testGetConsoleInfo() throws Exception{
		String str = consoleService.getConsoleInfo();
		Assert.assertEquals("Console is closed!", str);
	}
}
