package org.utmost.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * obtain input stream or transfer to byte array
 * 
 * @author bull
 *
 */
public class InputStreamUtil {
	/**
	 * Transfer inputstream to byte array
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByte(InputStream is) throws IOException {
		byte[] b = new byte[is.available()];
		is.read(b, 0, b.length);
		return b;
	}
	/**
	 * Parse byte array to inputstream
	 * 
	 * @param b
	 * @return
	 */
	public static InputStream toInputStream(byte[] b) {
		InputStream is = new ByteArrayInputStream(b);
		return is;
	}
}
