/**
 * @author Zhao Wei
 *
 * This class is some function for download list service
 */
package com.vgc.databank.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utmost.portal.service.AutoService;
import org.utmost.spring.mvc.controller.DownloadController;



@Service("DownloadListUtil")
@SuppressWarnings("rawtypes")
public class DownloadListUtil {
	
	private static Logger logger = Logger.getLogger(DownloadListUtil.class);

	@Autowired
	private AutoService autoService;
	
	private String path = "";
	/**
	 * Check if the current map is existed in database.If so,get record by title and return for delete
	 * 
	 * @param map
	 * @return duplicateMap
	 */
	@SuppressWarnings("unchecked")
	public HashMap hasDuplicate(HashMap map){
		HashMap duplicateMap = new HashMap();
		String userUuid = map.get("user_uuid").toString();
		String title = map.get("document_title").toString();
		StringBuilder hql = new StringBuilder();
		hql.append("from U_USER_DOWNLOAD_LIST where user_uuid = '");
		hql.append(userUuid).append("' ");
		hql.append("and document_title = '");
		hql.append(title).append("' ");
		hql.append("and is_history = '0' ");
		
		List<HashMap> list = autoService.findByHql(hql.toString());
		logger.debug("list size: " + list.size());
		if(list.size() > 0){
			duplicateMap = list.get(0);
		}
		return duplicateMap;
	}
	
	/**
	 * Add a key "path" to map in order to use existed function
	 * 
	 * @param list
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap> addPathToMap(List<HashMap> list){
		List<HashMap> returnList = new ArrayList<HashMap>();
		for(HashMap map:list){
			String funcUuid = map.get("func_uuid").toString();
			String filePath = getPath(funcUuid,"","/");
			map.put("path", filePath);
			returnList.add(map);
		}
		return returnList;
	}
	
	/**
	 * Set variable "path" to path of funcUUID
	 * 
	 * @param funcUuid
	 * @param filePath supposed to be "" when the function is the first time called
	 * @param delimiter used to split dictionary level
	 */
	@SuppressWarnings("unchecked")
	public String getPath(String funcUuid,String filePath,String delimiter){
		StringBuilder hql = new StringBuilder();
		hql.append("select new map (uuid as uuid ,pid as pid ,funcname as funcname)from U_PORTAL_FUNC where uuid='");
		hql.append(funcUuid).append("' ");
		List<HashMap> list = autoService.findByHql(hql.toString());
		if(list.size() > 0){
			HashMap result = list.get(0);
			if("0".equals(result.get("pid").toString())){
				filePath = result.get("funcname").toString()+delimiter+filePath;
				path = filePath;
				logger.debug("path is: " + path);
			}else{
				filePath = result.get("funcname").toString()+delimiter+filePath;
				getPath(result.get("pid").toString(),filePath,delimiter);
			}
		}
		
		return path;
	}

}
