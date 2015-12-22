package org.utmost.util.cmd;

import java.io.InputStream;
import java.util.Properties;

/**
 * read properties
 * @author zhangwei
 */
public class ReadProperties {
	/**
	 * obtain properties file value by key
	 * @param properties
	 * @param key
	 * @return
	 */
	public String getValue(String properties, String key) {

		Properties prop = new Properties();
		try {
			ClassLoader cl = this.getClass().getClassLoader();		//obtain classloader
			InputStream is = cl.getResourceAsStream(properties);	//get inputstream
			// InputStream is = this.getClass().getResourceAsStream(properties);
			prop.load(is);
			if (is != null)
				is.close();
		} catch (Exception e) {
		}
		return prop.getProperty(key);		//get value
	}
	/**
	 * test
	 * @param args
	 */
	public static void main(String[] args) {
		ReadProperties pro = new ReadProperties();
		System.out.println(pro.getValue("org/utmost/util/cmd/cmd.properties","rarpath"));
	}
}