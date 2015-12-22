package com.vgc.databank.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utmost.portal.service.AutoService;
import org.utmost.spring.mvc.controller.DownloadController;
import org.utmost.util.DateUtil;
import org.utmost.util.PathUtil;

import com.vgc.databank.util.DownloadListUtil;
import com.vgc.databank.util.FuncUtil;
import com.vgc.databank.util.Main;

/**
 * batch download function class
 * 
 * @description Batch Download Service
 * @author  
 * @version 3.2.0
 * @date Jan 29, 2015 10:24:10 AM
 */
@Service("BatchDownloadService")
@SuppressWarnings({"unchecked","rawtypes"})
public class BatchDownloadService {
	private static Logger logger = Logger.getLogger(BatchDownloadService.class);

	/**
	 * Spring automatic assembly these service 
	 */
	@Autowired
	private AutoService autoService;

	@Autowired
	private BatchDownloadUtil batchDownloadUtil;

	@Autowired
	private MonitorService monitorService;

	@Autowired
	private DataInfoService dataInfoService;
	
	@Autowired
    private GanttService ganttService;
	
	@Autowired
	public FuncUtil funcUtil;
	
	@Autowired
	private DownloadListUtil downloadListUtil;
	/**
     * print 
     * 
     * @return
     */	
	public static void print() {
		String props = System.getProperties().toString();
		String[] arr = props.split(",");
		for (String prop : arr) {
			System.out.println(prop);
		}
	}

	/**
	 * Zip files 
	 * 
	 * @param list
	 * @return
	 */
	public String zipFile(List<HashMap> list, String user_UUID) {

		String randomuuid = null;
		/**
		 * format current system time
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHssmm");
		randomuuid = sdf.format(new Date());  //obtain system time as random uuid
		String webPath = PathUtil.getWebPath();
		HashMap hm = null;
		//Get pathName
		if (list == null || list.size() == 0) {  //if hasn't file to zip, return readme.txt notice user info
			return "readme.txt";
		}
		
		boolean checked = false;
		for (int i = 0; i < list.size(); i++) {
			hm = list.get(i);
			//Validate checked or not
			Integer chkstate = (Integer) hm.get("chk");
			if(chkstate.intValue()==1){
				checked = true;			
				monitorService.renewalDownload((String) list.get(i).get("path"),(String) list.get(i).get("file"),
						(String) list.get(i).get("last_modify_author"), user_UUID, (String) list.get(i).get("last_modify_date_str"));
				copyFile((String) hm.get("file"), (String) hm.get("path"), user_UUID, randomuuid);		//copy file
			};
		}
		if(!checked){
			return "readme.txt";
		}
		try {
			Main main = new Main(webPath + "TEMP/" + randomuuid + ".zip");
			main.createZipOut();
			main.packToolFiles(webPath + "TEMP/" + randomuuid, "");
			main.closeZipOut();
		} catch (FileNotFoundException e) {
			logger.error("File Not Found:[" + webPath + "UPLOAD/" + webPath
					+ "TEMP/" + randomuuid + "]");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return randomuuid + ".zip";
	}

	/**
	 * To zip files
	 * 
	 * @param src
	 * @param des
	 * @return
	 */
	public void toZip(String src, String des) {
		String webPath = PathUtil.getWebPath(); //obtain web path
		Process p = null;
		try {
			String cmd = webPath + "player/7za.exe a -tzip " + des + " -r0 " + src + "/*.*";
			System.out.println(cmd + "---");
	        p = Runtime.getRuntime().exec(cmd);  //execute cmd
	
			int i = p.waitFor();
			System.out.println(i + "--------------------");
			return;
		} catch (InterruptedException e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}catch (IOException e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
	}

	/**
	 * Copy file to temp directory
	 * 
	 * @param filename
	 * @param pathname
	 * @param user_UUID
	 * @param randomuuid: zip file name
	 * @return+
	 */
	private String copyFile(String filename, String pathname, String user_UUID,
			String randomuuid) {
		String webPath = PathUtil.getWebPath();
		String uploadPath = PathUtil.getUploadPath();  //obtain upload path

		FileInputStream fis = null;
		FileOutputStream fos = null;
		if (pathname != null) {

			String src = uploadPath + "/" + filename;  //source upload path
			String des = webPath + "TEMP/" + randomuuid + "/" + pathname;  //destination path
			System.out.println(des + "*****" + "*" + pathname);
			File fi = new File(des);
			if (!fi.exists()) { //if file isn't exist, create file
				fi.mkdirs();
			}
			try {
				fis = new FileInputStream(src); 	//inputstream read file
				fos = new FileOutputStream(des + "/" + monitorService.getOriginalFileName(filename));	//outputstream write file
				IOUtils.copy(fis, fos);  //execute real copy file
			} catch (FileNotFoundException e) {
				logger.error("File Not Found:" + uploadPath + "/" + filename);
			} catch (IOException e) {
				logger.error("Copy:" + uploadPath + "/" + filename + "!" + e);
			} finally {		//close all stream
				try {
					if (fos != null) {
						fos.close();
					}
				} catch (IOException e) {
					logger.error("Close:" + webPath + "TEMP/" + pathname + "/" + filename + "!" + e);
				}
				try {
					if (fis != null) {
						fis.close();
					}
				} catch (IOException e) {
					logger.error("Close:" + uploadPath + "/" + filename + "!" + e);
				}
			}
		}
		return randomuuid;
	}

	/**
	 * Get all leaf file path.
	 * 
	 * @param leafUUIDs
	 * @return
	 */
	public HashMap<String, String> getPath(String[] leafUUIDs) {
		HashMap<String, String> hm = new HashMap<String, String>();
		for (int i = 0; i < leafUUIDs.length; i++) {
			String key = leafUUIDs[i];
			//String value = batchDownloadUtil.getTotalPathName(key); //obtain the full path name
			String value = downloadListUtil.getPath(key, "", "/");
			hm.put(key, value);
		}
		return hm;
	}

	/**
	 * Get the Chinese path of leaf file path.
	 * 
	 * @param leafUUIDs
	 * @return
	 */
	public HashMap<String, String> getCnPath(String[] leafUUIDs) {
		HashMap<String, String> hm = new HashMap<String, String>();
		for (int i = 0; i < leafUUIDs.length; i++) {
			String key = leafUUIDs[i];
			String value = batchDownloadUtil.getTotalCnPathName(key);  //obtain Chinese the full path name
			hm.put(key, value);
		}
		return hm;
	}

	private String[] staticLeafNodes = null;
	private int count = 0;
	/**
	 * Get all file path and file name
	 * 
	 * @param leafUUIDs
	 * @return
	 */
	public List<HashMap<String, String>> getPathAndFileName(String node_UUID, String user_UUID) {
	    long startime = System.currentTimeMillis();  
		String nodeClassUUID = getClassUUID(node_UUID);
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String[] leafNodes = getLeafUUID(node_UUID, user_UUID);
		staticLeafNodes = leafNodes;
		count = 0;
		
		if (leafNodes == null) {
			return null;
		}
		//long t1 = System.currentTimeMillis()-startime;
		//System.out.println("getLeafUUID:"+t1);
		//long t2 = System.currentTimeMillis()-startime;
       // System.out.println("getPath:"+t2);
        //================================
        whileCycle(list, nodeClassUUID, leafNodes);
        //================================
		//long t3 = System.currentTimeMillis()-startime;
       // System.out.println("getPath:"+t3);
		return list;
	}
	/**
	 * put parameters
	 * @param list
	 */
	private void putParam(List<HashMap<String, String>> list){
		//put leaves
		StringBuilder nodes = new StringBuilder();
		for (int i = 0; i < staticLeafNodes.length; i++) {
			nodes.append(staticLeafNodes[i]).append(",");
		}
		nodes.deleteCharAt(nodes.length()-1);
		HashMap<String,String> leaves = new HashMap<String,String>();
		leaves.put("leaves", nodes.toString());
		//put count
		HashMap<String,String> leafCount = new HashMap<String,String>();
		leafCount.put("lCount", String.valueOf(count));
		list.add(leaves);
		list.add(leafCount);
	}
	
	/**
	 * get total count
	 * @param node_UUID
	 * @param user_UUID
	 * @return
	 */
	public String obtainTotal(String node_UUID, String user_UUID){
		int chCount = 0;
		long allTotal = 0;
		String nodeClassUUID = getClassUUID(node_UUID);
		String[] leafNodes = getLeafUUID(node_UUID, user_UUID);
		//constructor section hql
		StringBuilder secHql = new StringBuilder("('')");
		if(leafNodes != null && leafNodes.length > 0){
			secHql.deleteCharAt(secHql.length()-1);
			secHql.deleteCharAt(secHql.length()-1);
			secHql.deleteCharAt(secHql.length()-1);
			for (int i = 0; i < leafNodes.length; i++) {
				secHql.append("'").append(leafNodes[i]).append("',");
			}
			secHql.deleteCharAt(secHql.length()-1);
			secHql.append(")");
		}
		
		List<HashMap> files = getDataForCount(secHql.toString());
		for (int i = 0; i < files.size(); i++) {
			String download_gb_en = (String) files.get(i).get("download_gb_en");
			String download_gb_ch = (String) files.get(i).get("download_gb_ch");
			String download_ece_r = (String) files.get(i).get("download_ece_r");
			String download_eec = (String) files.get(i).get("download_eec");
			String summary = (String) files.get(i).get("summary");
			String download_others = (String) files.get(i).get("download_others");
			String doc = (String) files.get(i).get("doc_");
			String datauuid = (String) files.get(i).get("datauuid");
			String uuid = (String)files.get(i).get("uuid");
			@SuppressWarnings("unused")
			String s = null;
			if (download_gb_en != null) {
				allTotal++;
			}
			//count Chinese document
			if (download_gb_ch != null) {
				allTotal++;
				chCount++;
			}
			if (download_ece_r != null) {
				allTotal++;
			}
			if (download_eec != null) {
				allTotal++;
			}
			if (summary != null) {
				allTotal++;
			}
			if (download_others != null) {
				allTotal++;
			}
			if (doc != null) {
				String[] pathdoc = doc.split("/");
				for (int j = 0; j < pathdoc.length; j++) {
					if (pathdoc[j] != null) {
						allTotal++;
					}
				}
			}
			String classUUID = getClassUUID(uuid);
			/**
			 * if node is in the standard gantt, According uuid of the node to count total
			 */
			if (StaticFunUUID.ganttUUID.equals(classUUID)) {
				String hql = "select count(b.file_name) from B_GANTT_APPENDIX b where b.commondata_uuid='"
						+ datauuid + "'";
				List<Long> l = autoService.findByHql(hql);
				allTotal=allTotal+(l==null?0:l.get(0));
			}
		}
		if(nodeClassUUID != null && nodeClassUUID.equals(StaticFunUUID.standardUUID)){
			Set<String> keyGanttUUIDs = getGanttUUIDs(secHql.toString());
			String[] ganttDataUUID = getGanttDataUUIDs(keyGanttUUIDs);
			StringBuilder ganttHql = new StringBuilder("('')");
			if(ganttDataUUID != null && ganttDataUUID.length > 0){
				ganttHql.deleteCharAt(ganttHql.length()-1);
				ganttHql.deleteCharAt(ganttHql.length()-1);
				ganttHql.deleteCharAt(ganttHql.length()-1);
				for (int i = 0; i < ganttDataUUID.length; i++) {
					ganttHql.append("'").append(ganttDataUUID[i]).append("',");
				}
				ganttHql.deleteCharAt(ganttHql.length()-1);
				ganttHql.append(")");
			}
			String hql = "select count(*) " +
					"from B_GANTT_APPENDIX b, B_COMMONDATA c " +
					"where c.uuid=b.commondata_uuid and b.commondata_uuid in " + ganttHql;
			List<Long> l = autoService.findByHql(hql);
			allTotal = allTotal +(l==null?0:l.get(0));
		}
		String group = String.valueOf(allTotal) + "-" + String.valueOf(chCount);
		return group;
	}
	/**
	 * lazy load data when scroll bar scroll to bottom will call this method
	 * @param node_UUID
	 * @return
	 */
	public List<HashMap<String, String>> obtainSecPathAndFileName(String node_UUID,String leafNodes,String countP){
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String nodeClassUUID = getClassUUID(node_UUID);
		String[] leafNodesArr = leafNodes.split(",");
		count = Integer.valueOf(countP);
		staticLeafNodes = new String[leafNodesArr.length];
		for (int i = 0; i < leafNodesArr.length; i++) {
			staticLeafNodes[i] = leafNodesArr[i];
		}
		
		whileCycle(list, nodeClassUUID, leafNodesArr);
		return list;
	}
	/**
	 * loop for obtain data, and every loop iterate only two leafNodes
	 * @param list
	 * @param nodeClassUUID
	 * @param leafNodes
	 */
	private void whileCycle(List<HashMap<String, String>> list,String nodeClassUUID,String[] leafNodes){
		int recorder = 0;  //control only cycle two times
		for(;count < leafNodes.length;count++){
			//cycle two times, and list of size great than 10
			//need to dataField area has scroll bar
			if(recorder > 1 && list.size() > 15){
				break;
			}
			String key = leafNodes[count];
			String value = downloadListUtil.getPath(key, "", "/");
			String cnPath = batchDownloadUtil.getTotalCnPathName(key);
			String classUUID = getClassUUID(key);//
			List<HashMap> files = getData(key);
			for (int i = 0; i < files.size(); i++) {
				String title_en = (String) files.get(i).get("title_en");  //English title name
				String title_ch = (String) files.get(i).get("title_ch");	//Chinese title name
				String standard_no = (String) files.get(i).get("standard_no");
				
				String download_gb_en = (String) files.get(i).get("download_gb_en");
				String download_gb_ch = (String) files.get(i).get("download_gb_ch");
				String download_ece_r = (String) files.get(i).get("download_ece_r");
				String download_eec = (String) files.get(i).get("download_eec");
				String summary = (String) files.get(i).get("summary");
				String download_others = (String) files.get(i).get("download_others");
				String doc = (String) files.get(i).get("doc_");
				String last_modify_author = (String) files.get(i).get("last_modify_author");
				String last_modify_date_str = (String) files.get(i).get("last_modify_date_str");
				String datauuid = (String) files.get(i).get("datauuid");
				@SuppressWarnings("unused")
				String s = null;
				if (download_gb_en != null) {
					HashMap<String, String> pathAndFileNameHm = new HashMap<String, String>();
					
					pathAndFileNameHm.put("file_type", "GB_EN");
					pathAndFileNameHm.put("standard_no", standard_no);
					pathAndFileNameHm.put("title_en", title_en);  //English title
					pathAndFileNameHm.put("title_ch", title_ch);	//Chinese title
					pathAndFileNameHm.put("path", value);
					pathAndFileNameHm.put("cn_path", cnPath);	//Chinese path
					pathAndFileNameHm.put("file", download_gb_en);
					pathAndFileNameHm.put("last_modify_author",last_modify_author);
					pathAndFileNameHm.put("last_modify_date_str",last_modify_date_str);
					list.add(pathAndFileNameHm);
				}
				if (download_gb_ch != null) {
					HashMap<String, String> pathAndFileNameHm = new HashMap<String, String>();
					
					pathAndFileNameHm.put("file_type", "GB_CH");
					pathAndFileNameHm.put("standard_no", standard_no);
					pathAndFileNameHm.put("title_en", title_en);	//English title
					pathAndFileNameHm.put("title_ch", title_ch);	//Chinese title
					pathAndFileNameHm.put("path", value);
					pathAndFileNameHm.put("cn_path", cnPath);	//Chinese path
					pathAndFileNameHm.put("file", download_gb_ch);
					pathAndFileNameHm.put("last_modify_author",last_modify_author);
					pathAndFileNameHm.put("last_modify_date_str",last_modify_date_str);
					list.add(pathAndFileNameHm);
				}
				if (download_ece_r != null) {
					HashMap<String, String> pathAndFileNameHm = new HashMap<String, String>();

					pathAndFileNameHm.put("file_type", "ECE_R");
					pathAndFileNameHm.put("standard_no", standard_no);
					pathAndFileNameHm.put("title_en", title_en);  //English title
					pathAndFileNameHm.put("title_ch", title_ch);	//Chinese title
					pathAndFileNameHm.put("path", value);
					pathAndFileNameHm.put("cn_path", cnPath);	//Chinese path
					pathAndFileNameHm.put("file", download_ece_r);
					pathAndFileNameHm.put("last_modify_author",last_modify_author);
					pathAndFileNameHm.put("last_modify_date_str",last_modify_date_str);
					list.add(pathAndFileNameHm);
				}
				if (download_eec != null) {
					HashMap<String, String> pathAndFileNameHm = new HashMap<String, String>();

					pathAndFileNameHm.put("file_type", "EEC");
					pathAndFileNameHm.put("standard_no", standard_no);
					pathAndFileNameHm.put("title_en", title_en);	//English title
					pathAndFileNameHm.put("title_ch", title_ch);	//Chinese title
					pathAndFileNameHm.put("path", value);
					pathAndFileNameHm.put("cn_path", cnPath);	//Chinese path
					pathAndFileNameHm.put("file", download_eec);
					pathAndFileNameHm.put("last_modify_author",last_modify_author);
					pathAndFileNameHm.put("last_modify_date_str",last_modify_date_str);
					list.add(pathAndFileNameHm);
				}
				if (summary != null) {
					HashMap<String, String> pathAndFileNameHm = new HashMap<String, String>();
					
					pathAndFileNameHm.put("file_type", "SUMMARY");
					pathAndFileNameHm.put("standard_no", standard_no);
					pathAndFileNameHm.put("title_en", title_en);	//English title
					pathAndFileNameHm.put("title_ch", title_ch);	//Chinese title
					pathAndFileNameHm.put("path", value);
					pathAndFileNameHm.put("cn_path", cnPath);	//Chinese path
					pathAndFileNameHm.put("file", summary);
					pathAndFileNameHm.put("last_modify_author",last_modify_author);
					pathAndFileNameHm.put("last_modify_date_str",last_modify_date_str);
					list.add(pathAndFileNameHm);
				}
				if (download_others != null) {
					HashMap<String, String> pathAndFileNameHm = new HashMap<String, String>();

					pathAndFileNameHm.put("file_type", "OTHERS");
					pathAndFileNameHm.put("standard_no", standard_no);
					pathAndFileNameHm.put("title_en", title_en);	//English title
					pathAndFileNameHm.put("title_ch", title_ch);	//Chinese title
					pathAndFileNameHm.put("path", value);
					pathAndFileNameHm.put("cn_path", cnPath);	//Chinese path
					pathAndFileNameHm.put("file", download_others);
					pathAndFileNameHm.put("last_modify_author",last_modify_author);
					pathAndFileNameHm.put("last_modify_date_str",last_modify_date_str);
					list.add(pathAndFileNameHm);
				}
				if (doc != null) {
					String[] pathdoc = doc.split("/");
					for (int j = 0; j < pathdoc.length; j++) {
						if (pathdoc[j] != null) {
							HashMap<String, String> pathAndFileNameHm = new HashMap<String, String>();

							pathAndFileNameHm.put("file_type", "DOC");
							pathAndFileNameHm.put("standard_no", standard_no);
							pathAndFileNameHm.put("title_en", title_en);	//English title
							pathAndFileNameHm.put("title_ch", title_ch);	//Chinese title
							pathAndFileNameHm.put("path", value);
							pathAndFileNameHm.put("cn_path", cnPath);	//Chinese path
							pathAndFileNameHm.put("file", pathdoc[j]);
							pathAndFileNameHm.put("last_modify_author",last_modify_author);
							pathAndFileNameHm.put("last_modify_date_str",last_modify_date_str);
							list.add(pathAndFileNameHm);

						}
					}
				}
				/**
				 * if node is in the standard gantt, According uuid of the node to list all process file.
				 */
				if (StaticFunUUID.ganttUUID.equals(classUUID)) {
					String hql = "select new map(b.file_name as ganttfile) from B_GANTT_APPENDIX b where b.commondata_uuid='"
							+ datauuid + "'";
					List<HashMap> l = autoService.findByHql(hql);
					for (int u = 0; u < l.size(); u++) {
						HashMap<String, String> pathAndFileNameHm = new HashMap<String, String>();

						pathAndFileNameHm.put("file_type", "DRAFTING_GANTT");
						pathAndFileNameHm.put("standard_no", standard_no);
						pathAndFileNameHm.put("title_en", title_en);	//English title
						pathAndFileNameHm.put("title_ch", title_ch);	//Chinese title
						pathAndFileNameHm.put("path", value);
						pathAndFileNameHm.put("cn_path", cnPath);	//Chinese path
						pathAndFileNameHm.put("file",(String) l.get(u).get("ganttfile"));
						pathAndFileNameHm.put("last_modify_author",last_modify_author);
						pathAndFileNameHm.put("last_modify_date_str",last_modify_date_str);
						list.add(pathAndFileNameHm);
					}
				}
			}
			/**
			 * According funcname of the node to list all process file.
			 */
			if (nodeClassUUID != null && nodeClassUUID.equals(StaticFunUUID.standardUUID)) {
				String keyGanttUUID = getGanttUUID(key);
				String[] ganttDataUUID = getGanttDataUUID(keyGanttUUID);
				value = batchDownloadUtil.getTotalPathName(keyGanttUUID);
				cnPath = batchDownloadUtil.getTotalCnPathName(keyGanttUUID);
				for (int i = 0; i < ganttDataUUID.length; i++) {
					/**
					 * list last recode with modify person and time.
					 */
					String hql = "select new map(" +
							"c.standard_attribute1 as standard_attribute1 , " +
							"c.standard_attribute2 as standard_attribute2 , " + 
							"c.standard_attribute3 as standard_attribute3 , " + 
							"c.no_ as no_ , " + 
							"c.version_ as version_ , " + 
							"b.file_name as ganttfile, " +
							"c.title_en as title_en, " +
							"c.title_ch as title_ch, " +
							"c.last_modify_author as last_modify_author," +
							"c.last_modify_date_str as last_modify_date_str) " +
							"from B_GANTT_APPENDIX b, B_COMMONDATA c " +
							"where c.uuid=b.commondata_uuid and b.commondata_uuid='"
							+ ganttDataUUID[i] + "'";
					List<HashMap> l = autoService.findByHql(hql);
					l = dataInfoService.listDataParseNo(l);
					for (int u = 0; u < l.size(); u++) {
						HashMap<String, String> pathAndFileNameHm = new HashMap<String, String>();

						pathAndFileNameHm.put("file_type", "STANDARD_GANTT");
						pathAndFileNameHm.put("standard_no", (String) l.get(u).get("standard_no"));
						pathAndFileNameHm.put("title_en", (String) l.get(u).get("title_en")); //English title
						pathAndFileNameHm.put("title_ch", (String) l.get(u).get("title_ch"));	//Chinese title
						pathAndFileNameHm.put("path", value);
						pathAndFileNameHm.put("cn_path", cnPath);	//Chinese path
						pathAndFileNameHm.put("file",(String) l.get(u).get("ganttfile"));
						pathAndFileNameHm.put("last_modify_author", (String) l.get(u).get("last_modify_author"));
						pathAndFileNameHm.put("last_modify_date_str",(String) l.get(u).get("last_modify_date_str"));
						list.add(pathAndFileNameHm);
					}
				}
			}
			recorder++;
		}
		//put leaves and count
		putParam(list);
		
		List<String> keyStr = new ArrayList<String>();		//all is need to modify string date format
		keyStr.add("issuance_date_str");
		keyStr.add("execute_date_str");
		keyStr.add("last_modify_date_str");
		keyStr.add("pre_execute_date_str");
		keyStr.add("online_auto_execute_date_str");
		keyStr.add("publication_date_str");
		DateUtil.transferStrDate(list,keyStr);    //transfer date format
		//return list;
	}
	/**
	 * obtain all items uuid under a func by funcuuid
	 * 
	 * @param keyGanttUUID:funcuuid
	 * @return arr
	 */	
	private String[] getGanttDataUUID(String keyGanttUUID) {
		String hql = "select new map (b.uuid as uuid) from B_COMMONDATA b where b.func_uuid='" + keyGanttUUID + "'";
		List<HashMap> l = autoService.findByHql(hql);
		String[] arr = new String[l.size()];
		for (int i = 0; i < l.size(); i++) {
			arr[i] = (String) l.get(i).get("uuid");
		}
		return arr;
	}
	
	private String[] getGanttDataUUIDs(Set<String> keyGanttUUIDs){
		StringBuilder sb = new StringBuilder("('')");
		if(keyGanttUUIDs != null && keyGanttUUIDs.size() > 0){
			sb.deleteCharAt(sb.length()-1);
			sb.deleteCharAt(sb.length()-1);
			sb.deleteCharAt(sb.length()-1);
			for (String uuid : keyGanttUUIDs) {
				sb.append("'").append(uuid).append("',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(")");
		}
		String hql = "select new map (b.uuid as uuid) from B_COMMONDATA b where b.func_uuid in " + sb.toString();
		List<HashMap> l = autoService.findByHql(hql);
		String[] array = null;
		if(l != null){
			array = new String[l.size()];
			for (int i = 0; i < l.size(); i++) {
				array[i] = (String) l.get(i).get("uuid");
			}
		}
		return array;
	}
	/**
	 * obtain gantt uuid by node uuid
	 * 
	 * @param node_UUID
	 * @return
	 */	private String getGanttUUID(String node_UUID) {
		String hql = "select new map(p.uuid as classuuid) from U_PORTAL_FUNC u, U_PORTAL_FUNC p where u.funcname=p.funcname and u.uuid<>p.uuid and p.classuuid='0202' and u.uuid='"
				+ node_UUID + "'";
		List<HashMap> list = autoService.findByHql(hql);
		String nodeGanttUUID = null;
		if (list != null && list.size() == 1) {
			nodeGanttUUID = (String) list.get(0).get("classuuid");
		}
		return nodeGanttUUID;
	}
	private Set<String> getGanttUUIDs(String secHql){
		String uuidStr = secHql.substring(1, secHql.length()-1);
		String[] stand = uuidStr.split(",");
		Set<String> nodeGanttUUIDs = new HashSet<String>();
		for (int i = 0; i < stand.length; i++) {
			String hql = "select new map(p.uuid as classuuid) from U_PORTAL_FUNC u, U_PORTAL_FUNC p where u.funcname=p.funcname and u.uuid<>p.uuid and p.classuuid='0202' and u.uuid = " + stand[i];
			List<HashMap> list = autoService.findByHql(hql);
			if(list != null && list.size() == 1){
				nodeGanttUUIDs.add((String)list.get(0).get("classuuid"));
			}
		}
		return nodeGanttUUIDs;
	}
	
	/**
	 * 
	 * @description get Class UUID
	 * @author 
	 * @param leafUUID
	 * @return
	 */
	public String getClassUUID(String leafUUID) {
		String hql = "select new map(u.classuuid as classuuid) from U_PORTAL_FUNC u where u.uuid='" + leafUUID + "'";
		List<HashMap> list = autoService.findByHql(hql);
		String classUUID = null;
		if (list != null && list.size() == 1) {
			classUUID = (String) list.get(0).get("classuuid");
		}
		return classUUID;
	}
	
	private List<HashMap> getDataForCount(String secHql){
		StringBuilder hql = new StringBuilder();
		hql.append("select new map(");
		hql.append("b.func_uuid as uuid , ");
		hql.append("b.no_ as no_ , ");
		hql.append("b.version_ as version_ , ");
		hql.append("b.uuid as datauuid , ");
		hql.append("b.download_gb_en as download_gb_en ,");
		hql.append("b.download_gb_ch as download_gb_ch ,");
		hql.append("b.download_ece_r as download_ece_r ,");
		hql.append("b.download_eec as download_eec ,");
		hql.append("b.download_others as download_others ,");
		hql.append("b.doc_ as doc_ ,");
		hql.append("b.summary as summary )");
		hql.append(" from B_COMMONDATA b where b.state<>'1' and b.func_uuid in ").append(secHql);
		
		List<HashMap> list = autoService.findByHql(hql.toString());
		
		return list;
	}
	
	/**
	 * Get data of the leaf node.
	 * 
	 * @param leafUUID
	 * @return
	 */
	public List<HashMap> getData(String leafUUID) {
		StringBuilder hql = new StringBuilder();
		hql.append("select new map(");
		hql.append("b.standard_attribute1 as standard_attribute1 , ");
		hql.append("b.standard_attribute2 as standard_attribute2 , ");
		hql.append("b.standard_attribute3 as standard_attribute3 , ");
		hql.append("b.no_ as no_ , ");
		hql.append("b.version_ as version_ , ");
		hql.append("b.title_en as title_en , ");
		hql.append("b.title_ch as title_ch , ");
		hql.append("b.uuid as datauuid , ");
		hql.append("b.download_gb_en as download_gb_en ,");
		hql.append("b.download_gb_ch as download_gb_ch ,");
		hql.append("b.download_ece_r as download_ece_r ,");
		hql.append("b.download_eec as download_eec ,");
		hql.append("b.download_others as download_others ,");
		hql.append("b.doc_ as doc_ ,");
		hql.append("b.last_modify_author as last_modify_author , ");
		hql.append("b.last_modify_date_str as last_modify_date_str , ");
		hql.append("b.summary as summary )");
		hql.append(" from B_COMMONDATA b where b.state<>'1' and b.func_uuid='").append(leafUUID + "'" + "");

		List<HashMap> list = autoService.findByHql(hql.toString());
		
		list = dataInfoService.listDataParseNo(list);	//parse No_
		List<String> keyStr = new ArrayList<String>();		//all is need to modify string date format
		keyStr.add("issuance_date_str");
		keyStr.add("execute_date_str");
		keyStr.add("last_modify_date_str");
		keyStr.add("pre_execute_date_str");
		keyStr.add("online_auto_execute_date_str");
		keyStr.add("publication_date_str");
		DateUtil.transferStrDate(list,keyStr);
		return list;
	}

	/**
	 * Get all children leaf node 
	 * 
	 * @param func_UUID
	 * @param user_UUID
	 * @return
	 */
	public String[] getLeafUUID(String func_UUID, String user_UUID) {
		String[] str = null;
		String node_TYPE = getNodeTYPE(func_UUID);
		if ("3".equals(node_TYPE)) {
			logger.debug(func_UUID + " is leafNode");
			str = new String[] { func_UUID };
		} else {
			List<HashMap> list = funcUtil.queryChildFuncsByFuncId(user_UUID, func_UUID);
			if (list.size() > 0) {
				str = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					str[i] = (String) list.get(i).get("funcuuid");
				}
			}
		}
		return str;
	}

	/**
	 * Get node type
	 * 
	 * @param func_UUID
	 * @return
	 */
	public String getNodeTYPE(String func_UUID) {
		String hql = "select u.nodetype from U_PORTAL_FUNC as u where u.uuid='" + func_UUID + "'";
		logger.debug(" getNodeTYPE method:  " + hql);
		List list = autoService.findByHql(hql);
		String str = (String) list.get(0);
		if (str != null) {
			logger.debug(str);
		} else {
			logger.debug(" getNodeTYPE failure");
		}
		return str;
	}
	/**
	 * 
	 * @description del Temp
	 * @author 
	 * @return
	 */	
	public long delTemp() {
		long start = System.currentTimeMillis();
		ZipManager zip = new ZipManager();
		zip.delete(new File(PathUtil.getWebPath() + "\\" + "DOWNLOAD"));
		zip.delete(new File(PathUtil.getWebPath() + "\\" + "DOWNLOAD.zip"));
		return System.currentTimeMillis() - start;		//count delete zip file use time
	}
	
	/**
	 * query Gantt And Common Appendix By Uuid
	 * @author 
	 * @param commondata_uuid
	 * @return returnList<ganttHashMap<"current_date","appendix_list">,common_data<"column_name","column_value">>
	 */
	public List<HashMap> queryGanttsAndCommonAppendixByUuid(String commondata_uuid){
	    //get appendixes by commondata_uuid
	    List<HashMap> appendixList = ganttService.queryGanttAppendix(commondata_uuid);
	    List<HashMap> returnList = constructAppendixbyDate(appendixList);
	    StringBuffer hql = new StringBuffer();
	    //get common_data by commondata_uuid
        hql.append("from B_COMMONDATA where uuid='"+commondata_uuid+"'");
        List<HashMap<String,Object>> commondataList = autoService.findByHql(hql.toString());
        if(commondataList != null && commondataList.size()>0){
            HashMap<String,Object> commondata = commondataList.get(0);
            //Add additional variable to put uuid into map
            HashMap<String,Object> additional = new HashMap<String,Object>();
            //Get original name for each file,use regex to avoid magic number or word
            String regex = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}.*";
            List<String> fileNeedDownload = new ArrayList<String>();
            for(Entry<String, Object> data : commondata.entrySet()){
            	String key = data.getKey();
            	if(key!=null&&key.indexOf("_swf")>=0){
            		fileNeedDownload.add(key);
            	}
            }
            for(Entry<String, Object> data : commondata.entrySet()){
            	String key = data.getKey();
            	Object val = data.getValue();
            	//if the key is doc_, there's more than one file
            	if(val != null && !key.equals("doc_")){
            		String fileName = val.toString();
            		//if(fileName.matches(regex)){
            		if(isExistInDownloadFile(key,fileNeedDownload)){
            			String originalName = monitorService.getOriginalFileName(fileName);
            			additional.put(key+"_orig", originalName);
            		}
            		//}
            	}
            }
            commondata.putAll(additional);
            returnList.add(commondata);
        }
	    return returnList;
	}
	
	private boolean isExistInDownloadFile(String key,List<String> downloadList){
		if(downloadList!=null){
			for(String downloadFile : downloadList){
				if(key!=null&&downloadFile!=null&&downloadFile.indexOf(key)>=0){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * construct Appendix by Date 
	 * @author 
	 * @param appendixList:attachment list
	 * @return
	 */
	private List<HashMap> constructAppendixbyDate(List<HashMap> appendixList){
	    List<HashMap> returnList = new ArrayList<HashMap>();
	    HashMap<Long,List<HashMap<String,Object>>> tempMap = new HashMap<Long,List<HashMap<String,Object>>>();
	    //construct Map<"current_date","appendix_list">
	    for(HashMap<String,Object> appendixMap:appendixList){
	        Long currentDate = (Long)appendixMap.get("current_date"); //obatain current date
	        if(!tempMap.containsKey(currentDate)){
	            List<HashMap<String,Object>> tempList = new ArrayList<HashMap<String,Object>>();
	            tempList.add(appendixMap);
	            tempMap.put(currentDate, tempList);
	        }else{
	            List<HashMap<String,Object>> tempListtemp = tempMap.get(currentDate);
	            tempListtemp.add(appendixMap);
	        }
	    }
	    returnList.add(tempMap);
	    return returnList;
	}
}