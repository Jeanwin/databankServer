package org.utmost.monitor.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import com.thoughtworks.xstream.XStream;

/**
 * network info, interface data, flow
 * 
 * use Sigar obtain network info
 * 
 */
public class NetInterfaceData {

	private NetInterfaceConfig config;
	private NetInterfaceStat stat;
	private long rxbps;
	private long txbps;
	/**
	 * constructor
	 */
	public NetInterfaceData() {}
	/**
	 * populate sigar start state and sigar end state
	 * @param sigar
	 * @param name
	 * @throws SigarException
	 */
	public void populate(Sigar sigar, String name) throws SigarException {

		config = sigar.getNetInterfaceConfig(name);

		try {

			long start = System.currentTimeMillis();
			NetInterfaceStat statStart = sigar.getNetInterfaceStat(name);  //start state network card info
			long rxBytesStart = statStart.getRxBytes();
			long txBytesStart = statStart.getTxBytes();
			Thread.sleep(100);
			//Thread.sleep(1000);
			long end = System.currentTimeMillis();
			NetInterfaceStat statEnd = sigar.getNetInterfaceStat(name);	//obtain end state network card info
			long rxBytesEnd = statEnd.getRxBytes();
			long txBytesEnd = statEnd.getTxBytes();

			rxbps = (rxBytesEnd - rxBytesStart) * 8 / (end - start) * 100;
			txbps = (txBytesEnd - txBytesStart) * 8 / (end - start) * 100;
			
//			rxbps = (rxBytesEnd - rxBytesStart) * 8 / (end - start) * 1000;
//			txbps = (txBytesEnd - txBytesStart) * 8 / (end - start) * 1000;
			stat = sigar.getNetInterfaceStat(name);
		} catch (SigarException e) {

		} catch (Exception e) {

		}
	}
	/**
	 * static method, obtain NetInterfaceData object
	 * @param sigar
	 * @param name
	 * @return
	 * @throws SigarException
	 */
	public static NetInterfaceData gather(Sigar sigar, String name)
			throws SigarException {

		NetInterfaceData data = new NetInterfaceData();
		data.populate(sigar, name);
		return data;
	}
	/**
	 * obtain config info
	 * @return
	 */
	public NetInterfaceConfig getConfig() {
		return config;
	}
	/**
	 * obtain state info
	 * @return
	 */
	public NetInterfaceStat getStat() {
		return stat;
	}

	public long getRxbps() {
		return rxbps;
	}

	public long getTxbps() {
		return txbps;
	}
	/**
	 * test function
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Sigar sigar = new Sigar();
		String[] netIfs = sigar.getNetInterfaceList(); //obtain network card
		for (String name : netIfs) {
			//System.out.println(name);
		}
		// NetInterfaceConfig nif=sigar.getNetInterfaceConfig("eth0");
		// NetInterfaceStat nifs=sigar.getNetInterfaceStat("lo0");
		// System.out.println(nif.getAddress());
		// System.out.println(nifs.getSpeed());
		List netIfList = new ArrayList();
		for (String name : netIfs) {
			NetInterfaceData netIfData1 = NetInterfaceData.gather(sigar, name);
			netIfList.add(netIfData1);
		}
		XStream xstream = new XStream();
		// xstream.alias("NetInterfaceDatas", List.class);
		// xstream.alias("NetInterfaceData", NetInterfaceData.class);
		System.out.println(xstream.toXML(netIfList));
	}
}
