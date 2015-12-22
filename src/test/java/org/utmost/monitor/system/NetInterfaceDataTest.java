package test.java.org.utmost.monitor.system;

import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.utmost.monitor.system.NetInterfaceData;
@RunWith(PowerMockRunner.class)
public class NetInterfaceDataTest {
	NetInterfaceData netInterfaceData ;
	Sigar sigar;
	@Before
	public void setUp(){
		netInterfaceData = PowerMockito.spy(new NetInterfaceData());
		sigar= PowerMockito.mock(Sigar.class);

	}
	
	@Test
	@PrepareForTest({NetInterfaceData.class})
	public void testBuildReportDataByHql() throws Exception{
		NetInterfaceStat statStart = PowerMockito.mock(NetInterfaceStat.class); 
		PowerMockito.when(sigar.getNetInterfaceStat("231")).thenReturn(statStart);
		netInterfaceData.populate(sigar,"231");
	}
	
	@Test
	@PrepareForTest({NetInterfaceData.class})
	public void testGather() throws Exception{
		NetInterfaceData data = PowerMockito.mock(NetInterfaceData.class); 
		PowerMockito.whenNew(NetInterfaceData.class).withNoArguments().thenReturn(data);
		NetInterfaceData.gather(sigar,"231");
	}
	
	@Test
	public void testGetConfig() throws Exception{
		netInterfaceData.getConfig();
	}
	
	@Test
	public void testGetStat() throws Exception{
		netInterfaceData.getStat();
	}
	
	@Test
	public void testGetRxbps() throws Exception{
		netInterfaceData.getRxbps();
	}
	
	@Test
	public void testGetTxbps() throws Exception{
		netInterfaceData.getTxbps();
	}
	
}
	