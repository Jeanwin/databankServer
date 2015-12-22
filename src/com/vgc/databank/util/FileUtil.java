/**
 * @author Zhao Wei
 *
 * This class is used for download list to make copy and download
 */
package com.vgc.databank.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utmost.util.PathUtil;

import com.vgc.databank.service.MonitorService;


@Service("FileUtil")
@SuppressWarnings("rawtypes")
public class FileUtil {
	
	private static Logger logger = Logger.getLogger(FileUtil.class);

	@Autowired
	private MonitorService monitorService;
	@Autowired
	private DownloadListUtil downloadListUtil;


	/**
	 * Zip files
	 * 
	 * @param list documents list passed from front
	 * @return uuid used to name zip file
	 */
	public String zipFile(List<HashMap> list, String user_UUID) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHssmm");
		String randomuuid = sdf.format(new Date());
		String webPath = PathUtil.getWebPath();
		//Get pathName
		list = downloadListUtil.addPathToMap(list);
		makeCopy(list,user_UUID,randomuuid);
		
		try {
			Main main = new Main(webPath + "TEMP/" + randomuuid + ".zip");
			main.createZipOut();
			main.packToolFiles(webPath + "TEMP/" + randomuuid, "");
			main.closeZipOut();
		} catch (FileNotFoundException e) {
			logger.warn("File Not Found:[" + webPath + "UPLOAD/" + webPath
					+ "TEMP/" + randomuuid + "]");
			return "readme.txt";
		} catch (Exception e) {
			logger.warn("can't zip files");
			return "readme.txt";
		}
		return randomuuid + ".zip";
		
		
		
	}
	
	/**
	 * Make copy of existed files
	 * 
	 * @param list
	 * @param user_UUID
	 * @param randomuuid
	 */
	public void makeCopy(List<HashMap> list,String user_UUID,String randomuuid){
		for (int i = 0; i < list.size(); i++) {
			HashMap hm = list.get(i);
			monitorService.renewalDownload((String) list.get(i).get("path"),
					(String) list.get(i).get("document_name"),
					(String) list.get(i).get("last_modify_author"), user_UUID,
					(String) list.get(i).get("last_modify_date_str"));
			
			copyFile((String) hm.get("document_name"), (String) hm.get("path"),
					user_UUID, randomuuid);
		}
	}
	
	/**
	 * Copy file to temp dic
	 * 
	 * @param filename
	 * @param pathname
	 * @param user_UUID
	 */
	public void copyFile(String filename, String pathname, String user_UUID,String randomuuid) {
		
		String webPath = PathUtil.getWebPath();
		String des = webPath + "TEMP/" + randomuuid + "/" + pathname;
		logger.debug(des + "*****" + "*" + pathname);
		
		File fi = new File(des);
		if (!fi.exists()) {
			//if mkdirs fail,return 
			if (!fi.mkdirs()){
				logger.warn("Failed to make dictionary");
				return ;
			}
		}
		
		String uploadPath = PathUtil.getUploadPath();
		String src = uploadPath + "/" + filename;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(src);
			fos = new FileOutputStream(des + "/" + monitorService.getOriginalFileName(filename));
			IOUtils.copy(fis, fos);
		} catch (FileNotFoundException e) {
			logger.warn("File Not Found:" + uploadPath + "/" + filename);
		} catch (IOException e) {
			logger.warn("Copy:" + uploadPath + "/" + filename + "failed !");
		} finally { //close all stream
			try {
				if(fis != null){
					fis.close();
				}
				if(fos != null){
					fos.close();
				}
			} catch(IOException e) {
				logger.warn("Close io failed");
			}
		}
	}

}
