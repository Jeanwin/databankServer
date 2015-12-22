/**
 * @author Zhao Wei
 * 
 * This class is used in documents download list
 *
 */
package com.vgc.databank.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utmost.portal.service.AutoService;

import com.vgc.databank.util.DownloadListUtil;
/**
 * 
 * @description Download List Service
 * @author  
 * @version 3.2.0
 * @date Jan 29, 2015 10:46:00 AM
 */
@Service("DownloadListService")
@SuppressWarnings("rawtypes")
public class DownloadListService{
	private static Logger logger = Logger.getLogger(DownloadListService.class);

	@Autowired
	private AutoService autoService;
	
	@Autowired
	private DownloadListUtil downloadListUtil;

	/**
	 * Add selected documents to download list
	 * 
	 * @param list documents list passed from front
	 */
	@SuppressWarnings("unchecked")
	public void addToList(List<HashMap> list) {
		List<HashMap> duplicateList = new ArrayList<HashMap>();
		for(HashMap map : list){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  //date format
			map.put("add_date_str",sdf.format(new Date()));
			map.put("is_history","0");
			duplicateList.add(downloadListUtil.hasDuplicate(map));
		}
		//delete duplicate record in database, save the new one
		deleteFromList(duplicateList);
		autoService.saveAll("U_USER_DOWNLOAD_LIST", list);
	}

	/**
	 * Delete selected documents from download list
	 * 
	 * @param list documents list passed from front
	 */
	public void deleteFromList(List<HashMap> list) {
		autoService.deleteAll("U_USER_DOWNLOAD_LIST", list);
	}
	
	/**
	 * Get count of download list of certain user
	 * 
	 * @param userUuid
	 * @return count:String
	 */
	@SuppressWarnings("unchecked")
	public String getCount(String userUuid){
		String count = "0";
		StringBuilder hql = new StringBuilder();
		hql.append("select new map(count(*) as count) from U_USER_DOWNLOAD_LIST where user_uuid = '");
		hql.append(userUuid).append("' ");
		hql.append("and is_history = '0' ");
		List<HashMap> list = autoService.findByHql(hql.toString());
		if(list.size() > 0){
			count = list.get(0).get("count").toString();
			logger.debug("count："+count);
		}
		return count;
	}
	
	/**
	 * This public function is used to get path for front display
	 * 
	 * @param funcUuid
	 * @return path:String
	 */
	public String getPath(String funcUuid){
		return downloadListUtil.getPath(funcUuid,"","|||");
	}

	/**
	 * Get download list by userUUID
	 * document title is now linked to B_UPLOAD_FILES for original file names
	 * @param userUuid
	 * @return list map of document in a list
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap> getDownloadList(String userUuid) {
		StringBuilder hql = new StringBuilder();
		hql.append(" select dl.uuid as uuid,  ");
		hql.append("        dl.func_uuid as func_uuid,  ");
		hql.append("        dl.user_uuid as user_uuid,  ");
		hql.append("        dl.document_type as document_type,  ");
		hql.append("        uf.file_original_name as document_title,  ");
		hql.append("        uf.file_name as document_name,  ");
		hql.append("        dl.is_history as is_history,  ");
		hql.append("        dl.add_date_str as add_date_str,  ");
		hql.append("        dl.download_date_str as download_date_str ,dl.document_title as file_title");
		hql.append(" from U_USER_DOWNLOAD_LIST dl left join B_UPLOAD_FILES uf on dl.document_title = uf.file_name ");
		hql.append(" where dl.user_uuid = '").append(userUuid).append("' ");
		hql.append(" and dl.is_history = '0'  ");
		hql.append(" order by document_title asc ");
		List<HashMap> list = changeListToMap(autoService.findBySQL(hql.toString()));
		list = downloadListUtil.addPathToMap(list);
		return list;
	}
	
	private List<HashMap> changeListToMap(List list){
		List<HashMap> maps = new ArrayList<HashMap>();
		if(list!=null){
			for(Object obj:list){
				if(obj!=null){
					Object[] objList =  (Object[]) obj;
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("uuid", objList[0]);
					map.put("func_uuid", objList[1]);
					map.put("user_uuid", objList[2]);
					map.put("document_type", objList[3]);
					if(objList[4]==null){
						map.put("document_title", objList[9]);
					}else{
					  map.put("document_title", objList[4]);
					}
					if(objList[5]==null){
						map.put("document_name", objList[9]);
					}else{
					    map.put("document_name", objList[5]);
					}
					map.put("is_history", objList[6]);
					map.put("add_date_str", objList[7]);
					map.put("download_date_str", objList[8]);
					maps.add(map);
				}
				
				
			}
		}
		return maps;
	}
	
	/**
	 * Update data to make new download list 
	 * 
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	public void changeStatusToDownloaded(List<HashMap> list){
		for(HashMap map:list){
			map.put("is_history", "1");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  //date format
			map.put("download_date_str",sdf.format(new Date()));
			autoService.update("U_USER_DOWNLOAD_LIST", map);
		}
	}

}
