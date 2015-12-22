package org.utmost.monitor.system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;
/**
 * System monitor function class
 * @author bull
 *
 */
@Service("SystemMonitorService")
public class SystemMonitorService extends SigarInstance {
	/**
	 * obtain cup info
	 * @return
	 * @throws SigarException
	 */
	public String getCpuInfo() throws SigarException {
		CpuInfo[] infos = this.getInstance().getCpuInfoList();  //call father getInstance for getting cup info
		org.hyperic.sigar.CpuInfo info = infos[0];
		XStream xs = new XStream();
		xs.alias("CpuInfo", CpuInfo.class);
		return xs.toXML(info);	//translate cpu info to xml
	}
	/**
	 * obtain cup used rate
	 * @return
	 * @throws SigarException
	 */
	public String getCpuPerc() throws SigarException {
		CpuPerc cpu = this.getInstance().getCpuPerc();
		XStream xs = new XStream();
		xs.alias("CpuPerc", CpuPerc.class);
		return xs.toXML(cpu);  //translate cpu info to xml
	}
	/**
	 * obtain memory
	 * @return
	 * @throws SigarException
	 */
	public String getMem() throws SigarException {
		Mem mem = this.getInstance().getMem();
		XStream xs = new XStream();
		xs.alias("Mem", Mem.class);
		return xs.toXML(mem); //translate memory info to xml
	}
	/**
	 * obtain network card info
	 * @return
	 * @throws SigarException
	 */
	public String getNetData() throws SigarException {
		Sigar sigar = this.getInstance();
		XStream xs = new XStream();
		NetInterfaceData netIfData1 = NetInterfaceData.gather(sigar, "eth0");
		xs.alias("NetInterfaceData", NetInterfaceData.class);
		return xs.toXML(netIfData1);	//translate network card into xml
	}
	/**
	 * obtain disk io info
	 * @return
	 * @throws SigarException
	 */
	public String getDiskIO() throws SigarException {
		FileSystem[] fs = this.getInstance().getFileSystemList();
		// System.out.println(xs.toXML(fs));
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>" + "\n");
		for (int x = 0; x < fs.length; x++) {
			if (fs[x].getType() == fs[x].TYPE_LOCAL_DISK) {
				FileSystemUsage fsu = this.getInstance().getFileSystemUsage(
						fs[x].getDirName());
				sb.append("<node Driver=\"本地磁盘 (" + fs[x].getDevName()
						+ ")\" Size=\"" + fsu.getTotal() / 1024 / 1024
						+ "\" />" + "\n");
				// System.out.println(sb.toString());
			}
		}
		sb.append("</xml>" + "\n");
		return sb.toString();
	}
	/**
	 * translate to xml string
	 * @param obj
	 * @return
	 */
	public String toXML(Object obj) {
		XStream xs = new XStream();
		// System.out.println(xs.toXML(obj));
		return xs.toXML(obj);
	}
	/**
	 * obtain swap
	 * @return
	 * @throws SigarException
	 */
	public String getSwap() throws SigarException {
		Swap swap = this.getInstance().getSwap();
		XStream xs = new XStream();
		xs.alias("swap", Swap.class);
		return xs.toXML(swap);
	}
	/**
	 * obtain all info
	 * @return
	 * @throws SigarException
	 */
	public List<String> getAll() throws SigarException {
		List list = new ArrayList();
		list.add(this.getCpuPerc());
		list.add(this.getMem());
		if (this.getNetData() != null)
			list.add(this.getNetData());
		else {
			list.add("<xml></xml>");
		}
		list.add(this.getDiskIO());
		return list;
	}
	/**
	 * obtain system info
	 * @return
	 */
	public String getSysInfo() {
		Properties p=System.getProperties();
		Iterator it=p.entrySet().iterator();
		StringBuffer sb=new StringBuffer();
		while(it.hasNext()) {
			Map.Entry entry=(Entry) it.next();
			sb.append(entry.getKey()+"-->"+entry.getValue()+"\n");
			//System.out.println(entry.getKey()+"--"+entry.getValue());
		}
		return sb.toString();
	}
	/**
	 * for testing
	 * @param args
	 */
	public static void main(String[] args) {
		SystemMonitorService ms = new SystemMonitorService();
		ms.getSysInfo();
		try {
			// System.out.println(ms.getCpuInfo());
			// System.out.println(ms.getCpuPerc());
			// System.out.println(ms.getMem());
			// System.out.println(ms.getSwap());
			System.out.println(ms.getNetData());
			// System.out.println(ms.getDiskIO());
			// System.out.println(1073741824/1024/1024);
		} catch (SigarException e) {
			e.printStackTrace();
		}
	}
}
