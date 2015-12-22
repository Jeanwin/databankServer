package org.utmost.monitor.system;

import org.hyperic.sigar.Sigar;
/**
 * sigar function class
 * @author bull
 *
 */
public class SigarInstance {

	private Sigar sigar = null;
	/**
	 * obtain sigar object 
	 * if sigar instance is null, new Sigar(),
	 * else direct return sigar
	 * @return
	 */
	public Sigar getInstance() {
		if (sigar == null)
			sigar = new Sigar();
		return sigar;
	}

	/**
	 * test obtain instance
	 * @param args
	 */
	public static void main(String[] args) {
		// SigarFactory.getInstance();
	}

}
