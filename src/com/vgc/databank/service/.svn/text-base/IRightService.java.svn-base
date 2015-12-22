package com.vgc.databank.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * ClassName: IRightService
 * Function: Business interface relative with user authorization
 *
 * @author wangweimin
 * @Date 2009 Nov 4, 2009 3:13:37 PM
 * @Remark Contain all business method relative with user authorization in system
 */
public interface IRightService {
	
	/**
	 * login: Verify user login
	 *
	 * @param loginName
	 * @param password
	 * @return HashMap
	 * @throws 
	*/
	public HashMap login(String loginName, String password);
	/**
	 * query user rights
	 * 
	 * @param loginName
	 * @return
	 */
	public HashMap queryUserRight(String loginName);
	/**
	 * queryPortalRoles: Query role list
	 *
	 * @return List<HashMap> roles list
	 * @throws 
	*/
	public List<HashMap> queryPortalRoles();
	
	/**
	 * queryRoleUsers:Query user list under user group
	 *
	 * @param roleuuid user group uuid
	 * @return Map contains enabledUsers and disabledUsers lists
	 * @throws 
	*/
	public Map queryRoleUsers(String roleuuid,String userName);
	
	/**
	 * addPortalRole: Add new role
	 *
	 * @param portalRoleInfo Role info
	 * @return Long error code
	 * @throws 
	*/
	public long savePortalRole(HashMap portalRoleInfo);
	
	/**
	 * deletePortalRoleById:Delete role according to uuid
	 *
	 * @param uuid 
	 * @return Long error code
	 * @throws 
	*/
	public long deletePortalRoleById(String uuid);
	
	/**
	 * savePortalRefRoleUser: Save role and user reference table recorder
	 * TODO Add, update or delete role and user reference table recorder,
	 * 		update if has, add if not, delete if deleted in list
	 *
	 * @return Long error code
	 * @throws 
	*/
	public long savePortalRefRoleUser(HashMap<String,List> result);
	
	/**
	 * saveOperationRight: Save role and content function reference table recorder
	 * TODO Add, update or delete role and user reference table recorder,
	 * 		update if has, add if not, delete if deleted in list
	 *
	 * @param portalRefRoleFunc role and content function reference table list
	 * @return Long error code
	 * @throws 
	*/
	public Long saveOperationRight(HashMap<String,List> portalRefRoleFunc);
	 
	/**
	 * queryPortalOrg: Query all organization info
	 *
	 * @return List<HashMap> organization list
	 * @throws 
	*/
	public List<HashMap> queryPortalOrg();
	
}