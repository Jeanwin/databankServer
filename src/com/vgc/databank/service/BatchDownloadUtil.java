package com.vgc.databank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utmost.portal.service.AutoService;
import org.utmost.util.DateUtil;

/**
 * 
 * @description Batch Download Util
 * @author  
 * @version 3.2.0
 * @date Jan 29, 2015 10:23:31 AM
 */
@Service("BatchDownloadUtil")
public class BatchDownloadUtil {
	
	@Autowired
	private AutoService autoService;
	
	private String path = "";	//path
	/**
	 * obtain the full path name
	 * @param FUNC_UUID
	 * @return
	 */
	public String getTotalPathName(String FUNC_UUID) {
		path=allPathByFuncuuid(FUNC_UUID);
		return path;
		
	}
	/**
	 * obtain Chinese the full path name
	 * @param FUNC_UUID
	 * @return
	 */
	public String getTotalCnPathName(String FUNC_UUID) {
		String cnPath = allCnPathByFuncuuid(FUNC_UUID);
		return cnPath;
		
	}
	/**
	 * obtain the full path by func uuid
	 * 
	 * @param FUNC_UUID
	 * @return path: full path
	 */
	private String allPathByFuncuuid(String FUNC_UUID) {
		//get node name passed first
		List<HashMap<String, String>> nodeList = autoService.findByHql("select new map (u.uuid as funcuuid,u.pid as funcpid,u.funcname as funcname) from U_PORTAL_FUNC u where u.uuid='"
								+ FUNC_UUID + "'");

		//save current node name first
		if (nodeList.size() == 1) {
			String tempName = nodeList.get(0).get("funcname");
			String regName = tempName.replaceAll("/", " ");
			path =  regName + "/" + path;
			//judging current node is root or not, if root node return, or recursive
			if (!nodeList.get(0).get("funcpid").equals("0"))
				allPathByFuncuuid(nodeList.get(0).get("funcpid"));  //recursive to root node
		}
		return path;
	}
	/**
	 * obtain the full Chinese path by func uuid
	 * 
	 * @param FUNC_UUID
	 * @return
	 */
	private String allCnPathByFuncuuid(String FUNC_UUID) {
		String cnPath = "";
		//get current node name passed first
		List<HashMap<String, String>> nodeList = autoService.findByHql("select new map (u.uuid as funcuuid,u.pid as funcpid,u.funccnname as funccnname) from U_PORTAL_FUNC u where u.uuid='"
								+ FUNC_UUID + "'");

		//save current node name first
		if (nodeList.size() == 1) {
			String tempName = nodeList.get(0).get("funccnname");
			String regName = tempName.replaceAll("/", " ");
			cnPath =  regName + "/";
			//judging current node is root or not, if root node return, or recursive
			if (!nodeList.get(0).get("funcpid").equals("0"))   //judge if is root node
				cnPath = allCnPathByFuncuuid(nodeList.get(0).get("funcpid")) + cnPath;
		}
		return cnPath;
	}
	/**
	 * obtain the last modify date string by func uuid
	 * 
	 * @param func_UUID
	 * @return
	 */
	public String getLastTimeString(String func_UUID){
		String last_modify_date_str = null;
		String hql = "select new map(b.last_modify_date_str as last_modify_date_str) from B_COMMONDATA b where b.func_uuid='"+func_UUID+"'";	
		List<HashMap> list = autoService.findByHql(hql);
		List<String> keyStr = new ArrayList<String>();		//date format need to modify 
		keyStr.add("last_modify_date_str");
		DateUtil.transferStrDate(list,keyStr);  //translate date format util
		if (list.size() == 1 && list.get(0).get("last_modify_date_str") != null) {
			last_modify_date_str = (String) list.get(0).get("last_modify_date_str");
		}
		return last_modify_date_str;
	}
}
