package test.java.org.utmost.monitor.system;

import org.hyperic.sigar.Sigar;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.utmost.monitor.system.SigarInstance;
public class SigarInstanceTest {
	SigarInstance sigarInstance ;
	Sigar sigar;
	@Before
	public void setUp(){
		sigarInstance = PowerMockito.spy(new SigarInstance());
		sigar= PowerMockito.mock(Sigar.class);

	}
	
	@Test
	public void testBuildReportDataByHql() throws Exception{
		Sigar result = sigarInstance.getInstance();
		Assert.assertNotNull(result);
	}
	
}
	