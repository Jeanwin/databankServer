package org.apache.tools.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;

/**
 * 
 * @description Zip Manager
 * @author  Wang Jiqiang
 * @version 3.2.0
 * @date Apr 7, 2015 11:06:08 AM
 */
public class ZipManager {
	private String srcPath;
	private String desPath;
	private int buffer;

	public ZipManager() {
		srcPath = "";
		desPath = "";
		buffer = 1024;
	}

	/**
	 * utf-8 transfer
	 * @param s
	 * @return
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
//					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		String s_utf8 = sb.toString();
		sb.delete(0, sb.length());
		sb.setLength(0);
		sb = null;
		return s_utf8;
	}
	
	
	/**
	 * set src Path
	 * @param srcPath
	 */
	public void setsrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	/**
	 * get src Path
	 * @return
	 */
	public String getsrcPath() {
		return srcPath;
	}

	/**
	 * set desc Path
	 * @param desPath
	 */
	public void setdesPath(String desPath) {
		this.desPath = desPath;
	}

	/**
	 * get des Path
	 * @return
	 */
	public String getdesPath() {
		return desPath;
	}

	//is des Path Null
	private boolean isdesPathNull() {
		return (desPath.length() == 0);
	}

	//is src Path Null
	private boolean issrcPathNull() {
		return (srcPath.length() == 0);
	}

	/**
	 * 
	 * @description zip ,return 0, source is invalid
     *         return -1,target is invalid
     *         return >0,unzip time
	 * @author Wang Jiqiang
	 * @return
	 */
	public long zip() {
		long start = System.currentTimeMillis();
		if (issrcPathNull()) {
			return 0;
		}
		if (isdesPathNull()) {
			return -1;
		}
		try {
			FileOutputStream fos = new FileOutputStream(new File(desPath));
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
					fos, buffer));
			
		//	out.setEncoding("utf-8");
			File inputFile = new File(srcPath);
			zip(out, inputFile, "");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		return (long) (end - start);
	}

	//zip
	private long zip(ZipOutputStream out, File inputFile, String base) {
		long start = System.currentTimeMillis();
		try {
			if (inputFile.isDirectory()) {
				File[] file = inputFile.listFiles();
				out.putNextEntry(new ZipEntry(base + File.separator));
				base = (base.length() == 0) ? "" : (base + File.separator);
				for (int i = 0; i < file.length; i++) {
					zip(out, file[i], base + file[i].getName());
				}
			} else {
				System.out.println(base);
				out.putNextEntry(new ZipEntry(base));
				byte[] data = new byte[buffer];
				BufferedInputStream bis = new BufferedInputStream(
						new FileInputStream(inputFile), buffer);
				int cnt;
				while ((cnt = bis.read(data, 0, buffer)) != -1) {
					out.write(data, 0, buffer);
				}
				out.flush();
				bis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		return (end - start);
	}

	/**
	 * unzip
	 @return   return 0, source is invalid
	 *         return -1,target is invalid
	 *         return >0,unzip time
	 */
	public long unzip() {
		long start = System.currentTimeMillis();
		if (issrcPathNull()) {
			return 0;
		}
		if (isdesPathNull()) {
			return -1;
		}
		File outFile = new File(desPath);
		if (!outFile.exists()) {
			outFile.mkdirs();
		}
		try {
			ZipFile zipFile = new ZipFile(srcPath);
			Enumeration en = zipFile.getEntries();
			ZipEntry zipEntry = null;
			createDirectory(desPath.replace("\\", "/"), "");
			while (en.hasMoreElements()) {
				zipEntry = (ZipEntry) en.nextElement();
				int index = zipEntry.getName().lastIndexOf("/");
				if (index == -1)
					index = 0;
				createDirectory(desPath.replace("\\", "/"), zipEntry.getName()
						.substring(0, index));
				if (zipEntry.isDirectory()) {
					continue;
				} else {
					File file = new File(outFile.getPath() + "/"
							+ zipEntry.getName());
					file.createNewFile();
					BufferedInputStream bis = new BufferedInputStream(zipFile
							.getInputStream(zipEntry), buffer);
					BufferedOutputStream out = new BufferedOutputStream(
							new FileOutputStream(file), buffer);
					int cnt;
					byte[] data = new byte[buffer];
					while ((cnt = bis.read(data, 0, buffer)) != -1) {
						out.write(data, 0, cnt);
					}
					bis.close();
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		return (long) (end - start);
	}

	//create Directory
	private static void createDirectory(String directory, String subDirectory) {
		String[] dir;
		File file = new File(directory);
		try {
			if ((subDirectory.length() == 0) && (!file.exists()))
				file.mkdirs();
			else if (subDirectory.length() != 0) {
				dir = subDirectory.replace("\\", "/").split("/");
				String temstr = directory;
				File temfile = null;
				for (int i = 0; i < dir.length; i++) {
					temstr += "/" + dir[i];
					temfile = new File(temstr);
					if (!temfile.exists()) {
						boolean bl = temfile.mkdir();
					} else {
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * delete ZipManager source
	 * @param file
	 * @return return FALSE unsuccessful
	 *         return TRUE  successful
	 */
	public boolean delete() {
		if (issrcPathNull()) {
			return false;
		}
		File file = new File(srcPath);
		if (!file.exists()) {
			return false;
		}
		delete(file);
		return true;
	}
	/**
	 * delete file
	 * @param file
	 * @return return FALSE unsuccessful
     *         return TRUE  successful
	 */
	public boolean delete(File file) {
		try {
			if (file.isDirectory()) {
				File[] f = file.listFiles();
				for (int i = 0; i < f.length; i++) {
					delete(f[i]);
				}
				file.delete();
			} else {
				file.delete();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	public static void main(String[] args) {
		ZipManager zip = new ZipManager();
		zip.setsrcPath("d:/sybase");
		zip.setdesPath("d:/sybase.zip");
		zip.zip();
	}
}
