/**
 * PortalFuncService.java
 * com.vgc.databank.service
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2009-11-12 		Yangxb
 *
 * Copyright (c) 2009, TNT All Rights Reserved.
 */

package com.vgc.databank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utmost.common.CommService;

import com.vgc.databank.util.FuncUtil;

/**
 * 
 * @description tree and menu
 * @author  
 * @version 3.2.0
 * @date Jan 29, 2015 10:55:00 AM
 */
@Service("portalFuncService")
public class PortalFuncService extends CommService {
	@Autowired
	public IRightService rightService;
	
	@Autowired
	public FuncUtil funcUtil;


	/**
	 * 
	 * reload tree,manage tree
	 * 
	 * 
	 * 
	 * @return List newTree 
	 */
	public List reBuildTree(String FUNC_TYPE) {
		List allNodesList = funcUtil.queryAllFuncs(FUNC_TYPE);
		List newTree = allChildrenByPid("0", allNodesList);
		return newTree;

	}

	/**
	 * 
	 *  reload tree,manage tree
	 * 
	 * @param USER_UUID
	 * @param FUNC_UUID
	 * 
	 * @return List newTree
	 */
	public List reBuildTreeByUserID(String USER_UUID, String FUNC_UUID) {
		List allNodesList = funcUtil.queryFuncByUser(USER_UUID, FUNC_UUID);
		List newTree = allChildrenByPid("0", allNodesList);
		return newTree;

	}
	
	/**
	 * 
	 * reBuild Tree By Operation
	 * 
	 * @param USER_UUID
	 * @param FUNC_UUID
	 * 
	 * @return List newTree
	 */
	public List reBuildTreeByOperation(String USER_UUID, String FUNC_UUID) {
		List allNodesList = funcUtil.queryFuncByUserOperation(USER_UUID, FUNC_UUID, "5");
		List newTree = allChildrenByPid("0", allNodesList);
		// allNodesList = rightService.queryFuncsByFuncId(USER_UUID, "0");
		return newTree;

	}
	/**
	 * 
	 * get View User Menu
	 * 
	 * @param USER_UUID
	 * @param FUNC_UUID
	 * @param FUNCSCOPE
	 * 
	 * @return List newTree
	 */
	public List getViewUserMenu(String USER_UUID, String FUNC_UUID,String FUNCSCOPE) {
		List allNodesList = funcUtil.queryFuncByUser(USER_UUID, FUNC_UUID,FUNCSCOPE);
		List newTree = allChildrenByPid("0", allNodesList);
		// allNodesList = rightService.queryFuncsByFuncId(USER_UUID, "0");
		return newTree;

	}

	/**
	 * 
	 * all Children By Pid
	 * 
	 * @param pid：
	 *            
	 * @param allNodesList
	 * @return List newTree 
	 */
	public List allChildrenByPid(String pid, List allNodesList) {
		// main_id，get all collection
		List newTree = new ArrayList();
		for (int i = 0; i < allNodesList.size(); i++) {
		    HashMap nodeMap = (HashMap) allNodesList.get(i);
			if (nodeMap.get("funcpid") != null
					&& nodeMap.get("funcpid").equals(pid)) {
				if (nodeMap.get("nodetype").equals("1")
						|| nodeMap.get("nodetype").equals("2")) {
					List children = allChildrenByPid(nodeMap.get("funcuuid").toString(), allNodesList);
					if(children.size() != 0)
						nodeMap.put("children", children);
				}
				newTree.add(nodeMap);
			}
		}
		return newTree;
	}

}
