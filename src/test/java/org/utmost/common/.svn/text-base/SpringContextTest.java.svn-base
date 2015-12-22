package test.java.org.utmost.common;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;
import org.utmost.common.DBSupport;
import org.utmost.common.SpringContext;
import org.utmost.portal.service.ConsoleService;
import org.utmost.tpl.service.TemplateService;
@RunWith(PowerMockRunner.class)
public class SpringContextTest {
	SpringContext springContext;
	@Before
	public void setUp() throws Exception {
		springContext = PowerMockito.spy(new SpringContext());
	}
	
	@Test
	public void testGetList() throws Exception{
		springContext.setApplicationContext(PowerMockito.mock(ApplicationContext.class));
	}
	
	@Test
	@PrepareForTest(SpringContext.class)
	public void testReloadSpring() throws Exception{
		PowerMockito.mockStatic(SpringContext.class);
		PowerMockito.when(SpringContext.class, "getBean","DBSupport").thenReturn(PowerMockito.mock(DBSupport.class));
		PowerMockito.when(SpringContext.class, "getBean","ConsoleService").thenReturn(PowerMockito.mock(ConsoleService.class));
		PowerMockito.when(SpringContext.class, "getBean","TemplateService").thenReturn(PowerMockito.mock(TemplateService.class));

		SpringContext.reloadSpring();
	}
}
