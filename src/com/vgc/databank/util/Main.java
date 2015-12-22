package com.vgc.databank.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author zhangwei
 */

public class Main {

	public static void main(String[] args) throws FileNotFoundException,
			Exception {

		/*System.setProperty("sun.io.unicode.encoding", "UTF-8");*/
		Main main = new Main("D:/ch.rar");
		main.createZipOut();
		main.packToolFiles("D:/temp", "");
		main.closeZipOut();
//		Main main = new Main();
//		main.delete(new File("D:\\Documents and Settings\\zhangwei\\Temp"));
	}

	private OutputStream out = null;
	private BufferedOutputStream bos = null;
	private ZipArchiveOutputStream zaos = null;
	private String zipFileName = null;

	/**
	 * zip or unzip constructor
	 * 
	 * @param zipname
	 */
	public Main(String zipname) {
		/*{
			System.setProperty("sun.jnu.encoding", "UTF-8");
			System.setProperty("user.country", "US");
			System.setProperty("sun.jnu.encoding", "UTF-8");
			System.setProperty("file.encoding", "UTF-8");
			System.setProperty("user.language", "en");
		}*/
		this.zipFileName = zipname;
	}

	/**
	 * tool class constructor
	 */
	public Main() {
	}

	/**
	 * delete directory appointed
	 * 
	 * @param file
	 * @return return false delete failure, return true delete success
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
	/**
	 * create zip outputstream
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void createZipOut() throws FileNotFoundException, IOException {
		File f = new File(new String(zipFileName.getBytes("UTF-8")));
		out = new FileOutputStream(f);
		bos = new BufferedOutputStream(out);
		zaos = new ZipArchiveOutputStream(bos);
		zaos.setEncoding("UTF-8");
	}
	/**
	 * close stream resource
	 * @throws Exception
	 */
	public void closeZipOut() throws Exception {
		zaos.flush();
		zaos.close();
		bos.flush();
		bos.close();
		out.flush();
		out.close();
		/*{
			System.setProperty("sun.jnu.encoding", "GBK");
			System.setProperty("user.country", "CN");
			System.setProperty("sun.jnu.encoding", "GBK");
			System.setProperty("file.encoding", "GBK");
			System.setProperty("user.language", "zh");
		}*/
	}

	/**
	 * packaging a directory to zip file of one directory
	 * 
	 * @param dirpath
	 *            directory the absolute address
	 * @param pathName
	 *            directory of zip
	 */
	public void packToolFiles(String dirpath, String pathName)
			throws FileNotFoundException, IOException {
		if (StringUtils.isNotEmpty(pathName)) {
			pathName = pathName + File.separator;
		}
		packToolFiles(zaos, dirpath, pathName);
	}

	/**
	 * packaging a directory to zip file appointed
	 * 
	 * @param dirpath
	 *            directory the absolute directory
	 * @param pathName
	 *            zip file the abstract address
	 */
	public void packToolFiles(ZipArchiveOutputStream zaos, String dirpath,
			String pathName) throws FileNotFoundException, IOException {
		ByteArrayOutputStream tempbaos = new ByteArrayOutputStream();
		BufferedOutputStream tempbos = new BufferedOutputStream(tempbaos);
		File dir = new File(dirpath);
		// return file on the absolute address
		File[] files = dir.listFiles();
		if (files == null || files.length < 1) {
			return;
		}
		for (int i = 0; i < files.length; i++) {
			// judging this file if is a folder
			if (files[i].isDirectory()) {
				packToolFiles(zaos, files[i].getAbsolutePath(), pathName
						+ files[i].getName() + File.separator);
			} else {
				zaos.putArchiveEntry(new ZipArchiveEntry(pathName + files[i].getName()));
				IOUtils.copy(new FileInputStream(files[i].getAbsolutePath()),zaos);
				zaos.closeArchiveEntry();
			}
		}
		tempbaos.flush();
		tempbaos.close();
		tempbos.flush();
		tempbos.close();
	}

	/**
	 * unzip a zip file to directory appointed
	 * 
	 * @param zipfilename
	 *            zip file the abstract address
	 * @param outputdir
	 *            directory the absolute address
	 */
	public static void unZipToFolder(String zipfilename, String outputdir)
			throws IOException {
		File zipfile = new File(zipfilename);
		if (zipfile.exists()) {
			outputdir = outputdir + File.separator;
			FileUtils.forceMkdir(new File(outputdir));
			ZipFile zf = new ZipFile(zipfile, "UTF-8");
			Enumeration zipArchiveEntrys = zf.getEntries();
			while (zipArchiveEntrys.hasMoreElements()) {
				ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) zipArchiveEntrys.nextElement();
				if (zipArchiveEntry.isDirectory()) {
					FileUtils.forceMkdir(new File(outputdir + zipArchiveEntry.getName() + File.separator));
				} else {
					IOUtils.copy(zf.getInputStream(zipArchiveEntry), FileUtils
							.openOutputStream(new File(outputdir + zipArchiveEntry.getName())));
				}
			}
		} else {
			throw new IOException("指定的解压文件不存在：\t" + zipfilename);
		}
	}
}
