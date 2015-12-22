package org.utmost.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
/**
 * Encrypt data
 * 
 * @author bull
 *
 */
@Service("CodecUtil")
public class CodecUtil {
	/**
	 * encrypt data in md5
	 * 
	 * @param data
	 * @return
	 */
	public static String md5Hex(String data) {
		return DigestUtils.md5Hex(data).toString();
	}
	/**
	 * test encrypt util
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(CodecUtil.md5Hex("123456"));
	}
}
