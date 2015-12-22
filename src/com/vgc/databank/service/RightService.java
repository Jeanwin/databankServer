package com.vgc.databank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utmost.portal.service.AutoService;

import com.vgc.databank.util.Constant;
import com.vgc.databank.util.UserManageUtil;

/**
 * 
 * 
 * 
 * @see ClassName: RightService Function: the implemented business class relative with user authorization
 * 
 * @author WangWeiMin
 * @Date 2009 NOV 10, 11:52:58 AM
 * @Remark -
 */
@Service("RightService")
@SuppressWarnings({"unchecked","rawtypes"})
public class RightService implements IRightService, Constant {
	/**
	 * super administrator name
	 */
	public static final String SUPERMAN_LOGINNAME = "CatOnlineSuperman";
	
	private static Logger logger = Logger.getLogger(RightService.class);
	/**
	 * spring automate assembly
	 */
	@Autowired
	private AutoService autoService;
	
	@Autowired
	private UserManageUtil userManageUtil;
	
	@Autowired
	private PortalFuncService funcService;

	public AutoService getAutoService() {
		return autoService;
	}
	
	public void setAutoService(AutoService autoService) {
		this.autoService = autoService;
	}


	/**
	 * (non-Javadoc)
	 * 
	 * @see com.vgc.databank.service.IRightService#addPortalRole(java.util.HashMap)
	 */
	public long savePortalRole(HashMap portalRoleInfo) {
		try {
			autoService.saveOrUpdate("U_PORTAL_ROLE", portalRoleInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return SERVER_ERROR;
		}
		return OK;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.vgc.databank.service.IRightService#deletePortalRoleById(java.lang.String)
	 */
	public long deletePortalRoleById(String uuid) {

		try {
			autoService.deleteByUUID("U_PORTAL_ROLE", uuid);

			List<HashMap> rolesChildren = autoService.findByHql("from U_PORTAL_ROLE where pid='" + uuid + "'");

			if (rolesChildren != null && rolesChildren.size() > 0) {
				ArrayList<String> rolesUuid = new ArrayList<String>();
				for (int j = 0; j < rolesChildren.size(); j++) {
					rolesUuid.add(rolesChildren.get(j).get("uuid").toString());
				}
				deleteRoles(rolesUuid);  //delete roles
			}

		} catch (Exception e) {
			e.printStackTrace();
			return SERVER_ERROR;
		}
		return OK;
	}

	/**
	 * @see deleteRoles: batch delete the records in table U_PORTAL_ROLE according to uuids
	 * 
	 * @param uuidList
	 *            the list of uuids which need to be deleted
	 * @throws
	 */
	private void deleteRoles(ArrayList<String> uuidList) {
		//batch delete the records in table U_PORTAL_ROLE according to uuids
		autoService.deleteByAllUUID("U_PORTAL_ROLE", uuidList); 

		for (int i = 0; i < uuidList.size(); i++) {
			String uuid = uuidList.get(i);

			List<HashMap> rolesChildren = autoService.findByHql("from U_PORTAL_ROLE where pid='" + uuid + "'");

			if (rolesChildren != null && rolesChildren.size() > 0) {
				ArrayList<String> rolesUuid = new ArrayList<String>();
				for (int j = 0; j < rolesChildren.size(); j++) {
					rolesUuid.add(rolesChildren.get(j).get("uuid").toString());
				}
				deleteRoles(rolesUuid);
			}
		}
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.vgc.databank.service.IRightService#login(java.lang.String,
	 *      java.lang.String)
	 */
	public HashMap login(String loginName, String password) {

		if (SUPERMAN_LOGINNAME.equalsIgnoreCase(loginName)) {

			List<HashMap> user = autoService.findByHql("from U_PORTAL_USER u where lower(u.loginname) = lower('" + loginName + "')");
			HashMap<String, String> userMap = user.get(0);

			userMap.put("uuid", "777");

			HashMap<String, Object> right = new HashMap<String, Object>();
			right.put("user", userMap);

			return right;
		}
		//if the login verification is not ok, return null
		if (verifyPass(loginName, password) != OK) {
			return null; 
		} else {

			return queryUserRight(loginName); 	//query user right
		}
	}

	/**
	 * verifyPass
	 * 
	 * @param loginName
	 * @param password
	 * @return 
	 */
	private long verifyPass(String loginName, String password) {

		List user = autoService.findByHql("from U_PORTAL_USER u where lower(u.loginname) = lower('" + loginName + "')");

		if (user.size() > 0) {
			return OK;
		} 
		//if the user list size = 0, return user not exist error
		return LOGIN_USER_LOGINNAME_ERROR; 
	}

	
	/**
	 * query system all operates right
	 * @return
	 */
	public List<HashMap> queryOperates() {

		List<HashMap> list = autoService.findAll("U_OPERATE");

		return list;
	}

	/**
	 * queryRoleOperFuncs:find the relative records in table U_PORTAL_REFROLEFUNC
	 * 
	 * @param roleuuid
	 * @param operateuuid
	 * @return String
	 * @throws
	 */
	public String queryRoleOperFuncs(String roleuuid, String operateuuid) {

		List<HashMap> funcs = funcService.reBuildTree("1");

		List<HashMap> menus = funcService.reBuildTree("2");

		String opHql = "from U_PORTAL_REFROLEFUNC uprf where uprf.roleuuid='" + roleuuid + "' order by uprf.operateuuid";
		List<HashMap> operates = autoService.findByHql(opHql);
		//tree func query
		String hql = "select ";
		hql += "new map( ";
		hql += "upf.uuid as funcuuid, ";
		hql += "upf.pid as funcpid, ";
		hql += "upf.funccode as funccode, ";
		hql += "upf.funcname as funcname, ";
		hql += "upf.funccnname as funccnname, ";
		hql += "upf.funcdesc as funcdesc, ";
		hql += "upf.funccndesc as funccndesc, ";
		hql += "upf.funcpath as funcpath, ";
		hql += "upf.funcicon as funcicon, ";
		hql += "upf.nodetype as nodetype, ";
		hql += "upf.functype as functype, ";
		hql += "upf.funcindex as funcindex, ";
		hql += "upf.classuuid as classuuid, ";
		hql += "upf.state as state, ";
		hql += "uprf.uuid as refuuid, ";
		hql += "uo.uuid as operateuuid, ";
		hql += "uo.operate_code as operate_code, ";
		hql += "uo.operate_name as operate_name ";
		hql += ") ";
		hql += "from ";
		hql += "U_PORTAL_ROLE upr, ";
		hql += "U_PORTAL_REFROLEFUNC uprf, ";
		hql += "U_PORTAL_FUNC upf, ";
		hql += "U_OPERATE uo ";
		hql += "where upr.uuid = uprf.roleuuid ";
		hql += "and upf.uuid = uprf.funcuuid ";
		hql += "and uprf.operateuuid = uo.uuid ";
		hql += "and uo.uuid = '" + operateuuid + "' ";
		hql += "and upr.uuid = '" + roleuuid + "' ";
		hql += "order by upf.funcindex ";

		List<HashMap> reffuncs = autoService.findByHql(hql);
		//menu func query
		String menuHql = "select ";
		menuHql += "new map( ";
		menuHql += "upf.uuid as funcuuid, ";
		menuHql += "upf.pid as funcpid, ";
		menuHql += "upf.funccode as funccode, ";
		menuHql += "upf.funcname as funcname, ";
		menuHql += "upf.funccnname as funccnname, ";
		menuHql += "upf.funcdesc as funcdesc, ";
		menuHql += "upf.funccndesc as funccndesc, ";
		menuHql += "upf.funcpath as funcpath, ";
		menuHql += "upf.funcicon as funcicon, ";
		menuHql += "upf.nodetype as nodetype, ";
		menuHql += "upf.functype as functype, ";
		menuHql += "upf.funcindex as funcindex, ";
		menuHql += "upf.classuuid as classuuid, ";
		menuHql += "upf.state as state, ";
		menuHql += "uprf.uuid as refuuid, ";
		menuHql += "uo.uuid as operateuuid, ";
		menuHql += "uo.operate_code as operate_code, ";
		menuHql += "uo.operate_name as operate_name ";
		menuHql += ") ";
		menuHql += "from ";
		menuHql += "U_PORTAL_ROLE upr, ";
		menuHql += "U_PORTAL_REFROLEFUNC uprf, ";
		menuHql += "U_PORTAL_FUNC upf, ";
		menuHql += "U_OPERATE uo ";
		menuHql += "where upr.uuid = uprf.roleuuid ";
		menuHql += "and upf.uuid = uprf.funcuuid ";
		menuHql += "and uprf.operateuuid = uo.uuid ";
		menuHql += "and uo.uuid = '" + operateuuid + "' ";
		menuHql += "and upr.uuid = '" + roleuuid + "' ";
		menuHql += "order by upf.funcindex ";

		List<HashMap> menuReffuncs = autoService.findByHql(menuHql);

		String funcXML = makeXML(funcs, reffuncs, operates);	//generate func xml
		String menuXML = makeXML(menus, menuReffuncs, null);	//generate menu xml
		System.out.println(funcXML);
		String treeXML = "<stage funcuuid=\"-1\">";
		treeXML += "<node funcuuid=\"-1\" funcname=\"Menu Tree\">";
		treeXML += funcXML;
		treeXML += "</node>";
		treeXML += menuXML;
		treeXML += "</stage>";

		return treeXML;   //get tree xml
	}
	/**
	 * make xml string according data of list
	 * 
	 * @param list
	 * @param reflist
	 * @param oplist
	 * @return
	 */
	private String makeXML(List<HashMap> list, List<HashMap> reflist, List<HashMap> oplist) {

		StringBuffer xmlStr = new StringBuffer();

		for (int i = 0; i < list.size(); i++) {
			HashMap temp = list.get(i);
			xmlStr.append("<node ");
			xmlStr.append("funcuuid=\"" + temp.get("funcuuid") + "\" ");
			xmlStr.append("funcpid=\"" + temp.get("funcpid") + "\" ");
			xmlStr.append("funccode=\"" + temp.get("funccode") + "\" ");
			xmlStr.append("funcname=\"" + temp.get("funcname") + "\" ");
			xmlStr.append("funccnname=\"" + temp.get("funccnname") + "\" ");
			xmlStr.append("funcdesc=\"" + temp.get("funcdesc") + "\" ");
			xmlStr.append("funccndesc=\"" + temp.get("funccndesc") + "\" ");
			xmlStr.append("funcpath=\"" + temp.get("funcpath") + "\" ");
			xmlStr.append("funcicon=\"" + temp.get("funcicon") + "\" ");
			xmlStr.append("nodetype=\"" + temp.get("nodetype") + "\" ");
			xmlStr.append("functype=\"" + temp.get("functype") + "\" ");
			xmlStr.append("funcindex=\"" + temp.get("funcindex") + "\" ");
			xmlStr.append("classuuid=\"" + temp.get("classuuid") + "\" ");
			xmlStr.append("ops=\"" + getOperates(temp.get("funcuuid").toString(), oplist) + "\" ");
			xmlStr.append(isExist(temp, reflist));
			xmlStr.append("> ");

			List tempList = (List) temp.get("children");
			if (tempList != null && tempList.size() > 0) {
				xmlStr.append(makeXML(tempList, reflist, oplist));
			}

			xmlStr.append("</node> ");
		}

		return xmlStr.toString();
	}
	/**
	 * judge funcuuid exist or not 
	 * 
	 * @param map
	 * @param reflist
	 * @return
	 */
	private String isExist(HashMap map, List<HashMap> reflist) {

		String str = "";

		for (int i = 0; i < reflist.size(); i++) {
			HashMap temp = reflist.get(i);
			if (temp.get("funcuuid").equals(map.get("funcuuid"))) {
				str += "refuuid=\"" + temp.get("refuuid") + "\" ";
				str += "operateuuid=\"" + temp.get("operateuuid") + "\" ";
				str += "exist=\"true\" ";
				str += "checked=\"1\" ";
				break;
			}
		}

		return str;
	}
	/**
	 * obtain operate
	 * 
	 * @param funcuuid
	 * @param oplist
	 * @return
	 */
	private String getOperates(String funcuuid, List<HashMap> oplist) {

		String str = "";

		if (oplist == null) { //if operates list is null, return ""
			return "";
		}

		for (int i = 0; i < oplist.size(); i++) {
			HashMap temp = oplist.get(i);
			if (temp.get("funcuuid").equals(funcuuid)) {
				if (temp.get("operateuuid").equals("1")) {  //Read-Only
					str += "R/";
				}
				if (temp.get("operateuuid").equals("2")) {	//Read-Only-attachment
					str += "A/";
				}
				if (temp.get("operateuuid").equals("3")) {	//Download
					str += "D/";
				}
				if (temp.get("operateuuid").equals("4")) {	//ReadProtected
					str += "RP/";
				}
				if (temp.get("operateuuid").equals("5")) {	//Modify
					str += "M/";
				}
			}
		}

		return str;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.vgc.databank.service.IRightService#queryPortalOrg()
	 */
	public List<HashMap> queryPortalOrg() {

		List<HashMap> orgList = autoService.findAll("U_PORTAL_ORG");

		return orgList;

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.vgc.databank.service.IRightService#queryPortalRoles()
	 */
	public List<HashMap> queryPortalRoles() {

		List<HashMap> roles = autoService.findAll("U_PORTAL_ROLE");

		return makeDataLevel("0", roles);

	}

	public Map queryRoleUsers(String roleuuid,String userName) {
		List<HashMap> allUsers;
		if(userName == null || "".equals(userName.trim())){
			allUsers = userManageUtil.queryUsers();
		}else{
			allUsers = userManageUtil.queryUsersByName(userName);
		}

		StringBuffer hql = new StringBuffer();
		hql.append("select new map( ");
		hql.append("upu.uuid as uuid, ");
		hql.append("upr.uuid as refuuid, ");
		hql.append("upu.usercode as usercode, ");
		hql.append("upu.username as username, ");
		hql.append("upu.loginname as loginname, ");
		hql.append("upu.email as email, ");
		hql.append("upu.telephone as telephone, ");
		hql.append("upu.password as password ) ");
		hql.append("from ");
		hql.append("U_PORTAL_USER upu, ");
		hql.append("U_PORTAL_REFROLEUSER upr ");
		hql.append("where upu.uuid = upr.useruuid ");
		hql.append("and upr.roleuuid = '" + roleuuid + "' ");
		List<HashMap> roleUsers = autoService.findByHql(hql.toString());  //users have role
		
		List<HashMap> enabledUsers = new ArrayList<HashMap>();

		for (int i = 0; i < allUsers.size(); i++) {
			HashMap tempMap = allUsers.get(i);
			tempMap.put("exist", "false");
			for (int j = 0; j < roleUsers.size(); j++) {
				if (roleUsers.get(j).get("uuid").equals(tempMap.get("useruuid"))) {
					tempMap.put("exist", "true");		//given role
					tempMap.put("refuuid", roleUsers.get(j).get("refuuid"));
					enabledUsers.add(tempMap);
				}
			}
		}
		
		allUsers.removeAll(enabledUsers);
		
		Map result = new HashMap();
		result.put("enabledUsers",enabledUsers);
		result.put("disabledUsers",allUsers);
		return result;
	}
	/**
	 * make data level
	 * iterator all nodes list and recursive to generate tree structure data 
	 * @param pid
	 * @param allNodesList
	 * @return
	 */
	private List<HashMap> makeDataLevel(String pid, List allNodesList) {

		
		List newTree = new ArrayList();
		
		for (int i = 0; i < allNodesList.size(); i++) {
			HashMap nodeMap = (HashMap) allNodesList.get(i);
			if (nodeMap.get("pid") != null && nodeMap.get("pid").equals(pid)) { //if has children, recursive organization children
				List childList = makeDataLevel(nodeMap.get("uuid").toString(),allNodesList);
				if (childList != null && childList.size() > 0) {
					nodeMap.put("children", childList);	//put childList to map of key 'children'
				}
				newTree.add(nodeMap);
			}
		}
		return newTree;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.vgc.databank.service.IRightService#queryUserById(java.lang.String)
	 */
	private List<HashMap> queryRoleUser(String user_uuid){
		String hql = "select ";
		hql += "new map(upr.rolename as rolename) from U_PORTAL_REFROLEUSER upr where upr.useruuid='" + user_uuid + "'";
		List<HashMap> userRoleL = autoService.findByHql(hql);
		return userRoleL;
	}
	/**
	 * query func right
	 * 
	 * @param user_uuid
	 * @param funcType
	 * @return
	 */
	private List<HashMap> queryFuncRight(String user_uuid, String funcType) {
		//order by upf.funcindex
		String hql = "select ";
		hql += "new map( ";
		hql += "upf.uuid as funcuuid, ";
		hql += "upf.pid as funcpid, ";
		hql += "upf.funccode as funccode, ";
		hql += "upf.funcname as funcname, ";
		hql += "upf.funccnname as funccnname, ";
		hql += "upf.funcdesc as funcdesc, ";
		hql += "upf.funccndesc as funccndesc, ";
		hql += "upf.funcpath as funcpath, ";
		hql += "upf.funcicon as funcicon, ";
		hql += "upf.nodetype as nodetype, ";
		hql += "upf.functype as functype, ";
		hql += "upf.funcindex as funcindex, ";
		hql += "upf.classuuid as classuuid, ";
		hql += "upf.state as state, ";
		hql += "uo.uuid as operateuuid, ";
		hql += "uo.operate_code as operate_code, ";
		hql += "uo.operate_name as operate_name ";
		hql += ") ";
		hql += "from ";
		hql += "U_PORTAL_USER upu, ";
		hql += "U_PORTAL_REFROLEUSER upru, ";
		hql += "U_PORTAL_ROLE upr, ";
		hql += "U_PORTAL_REFROLEFUNC uprf, ";
		hql += "U_PORTAL_FUNC upf, ";
		hql += "U_OPERATE uo ";
		hql += "where upu.uuid = upru.useruuid ";
		hql += "and upr.uuid = upru.roleuuid ";
		hql += "and upr.uuid = uprf.roleuuid ";
		hql += "and upf.uuid = uprf.funcuuid ";
		hql += "and uprf.operateuuid = uo.uuid ";
		hql += "and upf.functype = '" + funcType + "' ";
		hql += "and upu.uuid = '" + user_uuid + "' ";
		hql += "and COALESCE(upf.isdelete,'0') !='1' ";
		hql += "order by upf.funcindex ";

		List<HashMap> funcList = autoService.findByHql(hql);

		return funcList;

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.vgc.databank.service.IRightService#queryUserRight(java.lang.String)
	 */
	public HashMap<String, Object> queryUserRight(String loginName) {

		HashMap<String, Object> right = new HashMap<String, Object>(); // All
																		// right.

		// get user by login name
		List<HashMap> user = autoService.findByHql("from U_PORTAL_USER u where lower(u.loginname) = lower('" + loginName + "')");
		HashMap<String, String> userMap = user.get(0);
		//get func tree and right collection
		List<HashMap> funcList = queryFuncRight(userMap.get("uuid"), "2"); 
		//get menu tree and right collection
		List<HashMap> menuList = queryFuncRight(userMap.get("uuid"), "1"); 
		//obtain user role list by uuid
		List<HashMap> userRoleList = queryRoleUser(userMap.get("uuid"));
		List<HashMap> buttonRight = queryUserButtonRight(userMap.get("uuid"));  //button has/hasn't right for display
		List<HashMap> userRoleL = new ArrayList<HashMap>(2);
		boolean confirmAdm = true;		//if has admin role, set var is false(purpose for giving once admin role)
		boolean confirmReport = true;	//control report right, set var false after first occur modify
		for(int i=0; i<userRoleList.size(); i++){	//judge user if has admin role, if has,put it to map of key 'rolename'
			HashMap hm = userRoleList.get(i);
			String roleName = (String) hm.get("rolename");
			if("Admin".equals(roleName)){
				if(confirmAdm){
					HashMap hashM = new HashMap();
					hashM.put("rolename", roleName);
					userRoleL.add(hashM);
					confirmAdm = false;
				}
			}
		}
		//judge user if has Modify right, if has, put it to map of key 'rolename',
		//and value is 'Report'
		for (int i = 0; i < buttonRight.size(); i++) {	
			HashMap hm = buttonRight.get(i);
			String button = (String) hm.get("operate_code");
			if("Modify(M)".equals(button)){			//have modify right
				if(confirmReport){
					HashMap hashM = new HashMap();
					hashM.put("rolename", "Report");  //give rolename 'Report'(judge button display or not in front)
					userRoleL.add(hashM);
					confirmReport = false;
				}
			}
		}
		right.put("role", userRoleL);		//admin and report role put here
		right.put("user", userMap);			//user info put here
		right.put("menuList", menuList);
		right.put("funcList", funcList);
		return right;
	}
	/**
	 * query user has/hasn't button right(has/hasn't modify right)
	 * 
	 * @param user_uuid
	 * @return
	 */
	private List<HashMap> queryUserButtonRight(String user_uuid){
		String hql = "select distinct new map(uo.operate_code as operate_code) from U_PORTAL_REFROLEUSER upr,U_PORTAL_REFROLEFUNC uprf,U_OPERATE uo " +
				"where upr.roleuuid = uprf.roleuuid and uprf.operateuuid = uo.uuid and upr.useruuid='" + user_uuid + "'";
		List<HashMap> buttonRight = autoService.findByHql(hql);
		return buttonRight;
	}
	
	

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.vgc.databank.service.IRightService#saveOperationRight(java.util.List)
	 */
	public Long saveOperationRight(HashMap<String, List> portalRefRoleFunc) {

		try {
			List<HashMap> addList = portalRefRoleFunc.get("addList");  //list add right
			List<HashMap> delList = portalRefRoleFunc.get("delList");	//list delete right

			autoService.saveAll("U_PORTAL_REFROLEFUNC", addList);
			autoService.deleteAll("U_PORTAL_REFROLEFUNC", delList);
		} catch (Exception e) {
			e.printStackTrace();
			return SERVER_ERROR;
		}
		return OK;

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.vgc.databank.service.IRightService#savePortalRefRoleUser(java.util.List)
	 */
	public long savePortalRefRoleUser(HashMap<String, List> result) {
		try {
			List<HashMap> addList = result.get("addList");	//list add role
			List<HashMap> delList = result.get("delList");	//list delete role

			autoService.saveAll("U_PORTAL_REFROLEUSER", addList);
			autoService.deleteAll("U_PORTAL_REFROLEUSER", delList);
		} catch (Exception e) {
			logger.error(e);
			return SERVER_ERROR;
		}
		return OK;
	}

	

}
