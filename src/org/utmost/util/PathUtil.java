package org.utmost.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.utmost.report.service.ReportService;
/**
 * path util
 * all methods are decorated static
 * @author bull
 *
 */
public class PathUtil {
	private static Logger logger = Logger.getLogger(PathUtil.class);

	private static String fileSaveRoot = null;
	public static String ENCODING_ISO_8859_1 = "iso-8859-1";
	public static String ENCODING_UTF_8 = "utf-8";
	/**
	 * get web root
	 * @return
	 */
	public static String getWebPath() {
		String path = ReportService.class.getResource("/org/utmost/util/PathUtil.class").getPath().toString();
		path = path.substring(0, path.lastIndexOf("WEB-INF"));
		path = path.replaceAll("%20", " ");   //replace unicode of spacer
		return path;
	}
	/**
	 * parse path and get class path
	 * @return
	 */
	public static String getClassPath() {
		String path = PathUtil.class.getResource("/org/utmost/util/PathUtil.class").toString();
		path = path.substring(0, path.indexOf("org/utmost/util/PathUtil.class"));
		path = path.replaceAll("%20", " ");
		return path.substring(5, path.length());
	}

	public static String getClassFilePath() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL sourceUrl = loader.getResource("");
		try {
			return URLDecoder.decode(sourceUrl.getFile(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getRootPath() {
		return System.getProperty("Path");
	}

	/**
	 * get file save path.
	 * 
	 * @return /fileRoot/UPLOAD
	 */
	public static String getUploadPath() {
		String path = null;
		path = getFileSaveRoot() + "UPLOAD";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path;
	}

	/**
	 * get the file save path root, used by getUploadPath()!
	 * 
	 * @return /filePath/
	 */
	public static String getFileSaveRoot() {
		if (fileSaveRoot == null) {
			Properties props = new Properties();
			try {
				String fileLocation = "conf/"+System.getProperty("spring.profiles.active")+"/migration.properties";
				props.load(PathUtil.class.getClassLoader().getResourceAsStream(fileLocation));
			} catch (IOException e) {
				logger.error("file \"conf/{spring.profiles.active}/migration.properties\" not found!");
				throw new RuntimeException("file \"conf/{spring.profiles.active}/migration.properties\" not found!", e);
			}
			fileSaveRoot = props.getProperty("file.save.root");
			if (fileSaveRoot == null) {
				logger.error("file.save.root not defined!");
				throw new RuntimeException("file.save.root not defined!");
			}
		}
		return fileSaveRoot;
	}

	/**
	 * change char encoding
	 * 
	 * @param arg
	 * @param sourceCharset
	 * @param destCharset
	 * @return
	 */
	public static String convertEncoding(String arg, String sourceCharset,String destCharset) {
		if (arg != null) {
			String tmp = null;
			try {
				tmp = new String(arg.getBytes(sourceCharset), destCharset);
			} catch (UnsupportedEncodingException e) {
				logger.error("Encoding type is not supported.sourceCharset["
						+ sourceCharset + "],destCharset[" + destCharset + "]");
				throw new RuntimeException(e);
			}
			return tmp;
		}
		return null;
	}
	/**
	 * util test
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(PathUtil.getClassFilePath());
		System.out.println(PathUtil.getClassPath());
		System.out.println(PathUtil.getRootPath());
		System.out.println(PathUtil.getUploadPath());
	}
}
