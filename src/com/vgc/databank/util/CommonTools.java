/**
 * CommonTools.java
 * com.vgc.databank.util
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2009-11-18 		Yangxb
 *
 * Copyright (c) 2009, TNT All Rights Reserved.
 */

package com.vgc.databank.util;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utmost.common.CommService;
import org.utmost.portal.service.AutoService;
import org.utmost.util.DateUtil;
import org.utmost.util.PathUtil;

/**
 * ClassName:CommonTools Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 * 
 * @author Yangxb
 * @version
 * @since Ver 1.1
 * @Date 2009-11-18 下午02:18:08
 * 
 * @see
 */
@Service("CommonTools")
public class CommonTools extends CommService {

	private static List<String> legends = new ArrayList<String>();
	
	@Autowired
	private AutoService autoService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<HashMap> parseOtherFileName(Object[] filenames, String userId){
	    List<HashMap> newFileNames = new ArrayList<HashMap>();
	    List<HashMap> files = new ArrayList<HashMap>();
		if(filenames==null)
			return null;
		for(int i=0;i<filenames.length;i++){
			HashMap hm = (HashMap) filenames[i];
			String filename = (String) hm.get("fileName");
			String fileType = filename.substring(filename.lastIndexOf("."));
			String newFileName = getUniqueNumber()+fileType;
			HashMap newFileHashMap = new HashMap();
			newFileHashMap.put("tempid", hm.get("tempid"));
			newFileHashMap.put("fileName", newFileName);
			newFileNames.add(newFileHashMap);
			HashMap<String, Object> fileObject = new HashMap<String, Object>();
			fileObject.put("user_id", userId);
			fileObject.put("file_name", newFileName);
			fileObject.put("file_original_name", filename);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			fileObject.put("file_upload_time", df.format(new Date()));
			files.add(fileObject);
		}
		if(files!=null&&files.size()!=0){
	    	  autoService.saveAll("B_UPLOAD_FILES", files);
	    	}
		return newFileNames;
	}
	/**
	 * transfer file name
	 * obtain file name(VGC_system name_first directory_file name_stamptime(the file name to intercept before 50 characters))
	 * 
	 * @param filename
	 * @param userId user uuid
	 * @param mainFunc_UUID node UUID
	 * @return
	 */
    public List<HashMap> parseFileName(List<HashMap> filename, String userId, String funcuuid) {
    	List<HashMap> list = new ArrayList<HashMap>();
    	List<HashMap> files = new ArrayList<HashMap>();
    	if(filename==null)
    		return null;
    	for (int i = 0; i < filename.size(); i++) {
    		HashMap hm = (HashMap) filename.get(i);
			String filestring = hm.get("name").toString();
			String fileType = filestring.substring(filestring.lastIndexOf("."));
			String newFileName = getUniqueNumber()+fileType;
			hm.put(hm.get("kind"), newFileName);
			list.add(hm);
			HashMap<String, Object> fileObject = new HashMap<String, Object>();
			fileObject.put("user_id", userId);
			fileObject.put("file_name", newFileName);
			fileObject.put("file_original_name", filestring);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			fileObject.put("file_upload_time", df.format(new Date()));
			files.add(fileObject);
    	}
    	if(files!=null&&files.size()!=0){
    	  autoService.saveAll("B_UPLOAD_FILES", files);
    	}
    	return list;
    }
    
    private synchronized String getUniqueNumber(){
    	return  UUID.randomUUID().toString();
    }
	/**
	 * transfer file name
	 * obtain file name(VGC_system name_first directory_file name_stamptime(the file name to intercept before 50 characters))
	 * 
	 * @param filename
	 * @param mainFunc_UUID node UUID
	 * @return
	 */
	public List<HashMap> parseFileName(List<HashMap> filename, String funcuuid) {
		List<HashMap> list = new ArrayList<HashMap>();
		String dateStr = DateUtil.getNowDateForFN();
		String mainFunc_Name = "";
		if (funcuuid != null) {
			String hql = "select new map (m.funcname as funcname) from U_PORTAL_FUNC u, U_PORTAL_FUNC m where u.classuuid=m.uuid and u.uuid='"
					+ funcuuid + "'";
			List<HashMap> func = autoService.findByHql(hql);
			if (func != null && func.size() == 1) {
				mainFunc_Name = (String) func.get(0).get("funcname");
				mainFunc_Name = mainFunc_Name.replaceAll("/", " ");
			}
		}
		for (int i = 0; i < filename.size(); i++) {
			HashMap hm = (HashMap) filename.get(i);
			String filestring = hm.get("name").toString();
			// String file = String.valueOf(i);
			String file = filestring.substring(filestring.lastIndexOf(File.separator) + 1,
					filestring.lastIndexOf("."));
			String fileType = filestring.substring(filestring.lastIndexOf("."));
			StringBuilder sb = new StringBuilder();
			sb.append("VGC_CAT_");
			sb.append("Online_");
			sb.append(mainFunc_Name);
			sb.append("_");
			sb.append(file);
			sb.append("_");
			sb.append(dateStr);
			sb.append(fileType);
			hm.put(hm.get("kind"), sb.toString());
			list.add(hm);
		}
		return list;
	}

	/**
	 * transfer file name
	 * obtain file name(VGC_system name_first directory_file name_stamptime(the file name to intercept before 50 characters))
	 * 
	 * @deprecated
	 * @param item
	 * @return
	 * @author zhangwei
	 */
	public String parseFileName(String filename, String main_func_name) {
		String dateStr = DateUtil.getNowDateForFN();
		StringBuilder sb = new StringBuilder();
		sb.append("VGC_CAT_");
		sb.append("Online_");
		sb.append(main_func_name);
		sb.append("_");
		sb.append(filename);
		sb.append("_");
		sb.append(dateStr);
		return sb.toString();
	}

	/**
	 * transfer file name
	 * obtain file name(VGC_system name_first directory_file name_stamptime(the file name to intercept before 50 characters))
	 * @param FUNC_UUID
	 *            :String  node ID
	 * @return path：String this node directory structure, contain id node
	 * @author Yangxb
	 */
	public String getTotalPathName(String FUNC_UUID) {
		path = allPathByFuncuuid(FUNC_UUID);
		return path;

	}

	public String path = "";
	/**
	 * obtain the full path by func uuid
	 * @param FUNC_UUID
	 * @return
	 */
	public String allPathByFuncuuid(String FUNC_UUID) {
		// firstly, get node name passed
		List<HashMap<String, String>> nodeList = getDb().findByHql("select new map (u.uuid as funcuuid,u.pid as funcpid,u.funcname as funcname) from U_PORTAL_FUNC u where u.uuid='"
								+ FUNC_UUID + "'", false);

		// save current node name
		if (nodeList.size() == 1) {
			path = nodeList.get(0).get("funcname") + "/" + path;
			// judging current node if is root node, root node return, or not recursive
			if (!nodeList.get(0).get("funcpid").equals("0"))
				allPathByFuncuuid(nodeList.get(0).get("funcpid"));
		}
		return path;
	}
	/**
	 * upload
	 * @param rq
	 * @param rp
	 * @throws FileUploadException
	 */
	public void uploadVGC(HttpServletRequest rq, HttpServletResponse rp)
			throws FileUploadException {
		String path = PathUtil.getRootPath() + "/upload";
		File file = new File(path);
		if (!file.exists()) { //if file is not exist, create file
			file.mkdirs();
		}
		String filename = rq.getParameter("filename");
		List<FileItem> items = getFileUpload().parseRequest(rq);
		for (FileItem item : items) {
			if (!item.isFormField()) {
				File fi = new File(path + filename);
				try {
					item.write(fi);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("upload:" + fi.getAbsolutePath());
			}
		}
	}
	/**
	 * get ServletFileUpload obj, set header encoding 'utf-8'(for display Chinese normal)
	 * @return
	 */
	public ServletFileUpload getFileUpload() {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8"); // set encoding 'utf-8', prevent Chinese messy code
		return upload;
	}
	
	public static List<String> getLengendsValues(){
		if(legends.size()==0){
		   ResourceBundle legendsUS = ResourceBundle.getBundle("conf/locale/Legends",Locale.US);
		   legends.addAll(legendsUS.keySet());
		}
		return legends;
		
	}
}
