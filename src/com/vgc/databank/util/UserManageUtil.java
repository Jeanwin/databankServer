/**
 * 2015-1-27 10:49:34
 * This class is used for user related function
 */
package com.vgc.databank.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utmost.portal.service.AutoService;

import com.vgc.databank.service.RightService;

/**
 * @author Zhao Wei
 *
 */
@Service("UserManageUtil")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserManageUtil implements Constant{
	
	@Autowired
	private AutoService autoService;
	
	@Autowired
	private RightService rightService;
	
	/**
	 * addPortalUser: Add user
	 * 
	 * @param user user info
	 * @return long Error code 0:success 1:user name duplicate 4:system error
	 * @throws 
	*/
	public long addPortalUser(HashMap user) {

		String hql = "from U_PORTAL_USER u where u.loginname = '"
				+ user.get("loginname") + "'";
		List<HashMap> list = autoService.findByHql(hql);
		if (list.size() > 0) {
			return 1L;
		}
		
		if(null != user.get("password") && !"".equals(user.get("password"))){
			user.put("password", DigestUtils.md5Hex(user.get("password").toString()));
		}else{
			user.put("password", "123456");
		}


		autoService.save("U_PORTAL_USER", user);
		

		return 0L;
	}
	/**
	 * @see addPortalUser: add user and add the reference between organizations
	 * 
	 * @param user
	 *            relative users
	 * @param org
	 *            relative organization references
	 * @return long
	 * @throws
	 */
	public long addPortalUser(HashMap user, List<HashMap> refOrg) {

		try {
			String hql = "from U_PORTAL_USER u where u.loginname = '"
					+ user.get("loginname") + "'";
			List<HashMap> list = autoService.findByHql(hql);
			if (list.size() > 0) {
				return 1L;
			}
			
			if(null != user.get("password") && !"".equals(user.get("password"))){
				user.put("password", DigestUtils.md5Hex(user.get("password").toString()));
			}else{
				user.put("password", "123456");
			}

			String useruuid = autoService.save("U_PORTAL_USER", user);

			// add user id to the organization references
			for (int i = 0; i < refOrg.size(); i++) {
				refOrg.get(i).put("useruuid", useruuid);
			}

			autoService.saveAll("U_PORTAL_REFORGUSER", refOrg);


		} catch (Exception e) {
			e.printStackTrace();
			return 4L;
		}

		return 0L;
	}
	/**
	 * updatePortalUser: Update user information
	 *
	 * @param user  user info package to map
	 * @return Long error code
	 * @throws 
	*/
	public long updatePortalUser(HashMap user) {

		try {
			if ("true".equals(user.get("passwordChange"))) {
				user.put("password",
						DigestUtils.md5Hex(user.get("password").toString()));
			}
			autoService.update("U_PORTAL_USER", user);
		} catch (Exception e) {
			e.printStackTrace();
			return SERVER_ERROR;
		}
		return OK;
	}
	/**
	 * updatePortalUser: Update user information
	 * @param user
	 * @param orgList
	 * @param orgNowList
	 * @return
	 */
	public long updatePortalUser(HashMap user, List<HashMap> orgList,
			List<HashMap> orgNowList) {

		try {
			// modify user
			if ("true".equals(user.get("passwordChange"))) {
				user.put("password",
						DigestUtils.md5Hex(user.get("password").toString()));
			}
			autoService.update("U_PORTAL_USER", user);

			//delete the old relationship between users and orginzations
			for (int i = 0; i < orgList.size(); i++) {

				HashMap org = orgList.get(i);

				if (org != null) {
					String hql = "from U_PORTAL_REFORGUSER ref "
							+ "where ref.useruuid = '" + user.get("uuid")
							+ "' " + "and ref.orguuid = '" + org.get("uuid")
							+ "'";

					List<HashMap> list = autoService.findByHql(hql);

					String refuuid = "";
					if (list != null && list.size() > 0) {
						refuuid = list.get(0).get("uuid").toString();
					}

					autoService.deleteByUUID("U_PORTAL_REFORGUSER", refuuid);
				}
			}

			//modify the relationship for users and orginazations
			for (int i = 0; i < orgNowList.size(); i++) {

				HashMap org = orgNowList.get(i);
				if (org != null) {
					HashMap ref = new HashMap();
					ref.put("orguuid", org.get("uuid"));
					ref.put("orgcode", org.get("orgcode"));
					ref.put("orgname", org.get("orgname"));
					ref.put("useruuid", user.get("uuid"));
					ref.put("usercode", user.get("usercode"));
					ref.put("username", user.get("username"));

					autoService.save("U_PORTAL_REFORGUSER", ref);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return SERVER_ERROR;
		}
		return OK;
	}
	/**
	 * deletePortalUser: Delete user according to user table uuid
	 *
	 * @param uuid 
	 * @return Long error code
	 * @throws 
	*/
	public Long deletePortalUser(String uuid) {

		try {
			//delete the record in table U_PORTAL_ROLE according to uuid
			autoService.deleteByUUID("U_PORTAL_USER", uuid); 

			List<HashMap> roleUserList = autoService
					.findByHql("from U_PORTAL_REFROLEUSER where useruuid='"
							+ uuid + "'");

			ArrayList<String> refuuidList = new ArrayList<String>();

			for (int i = 0; i < roleUserList.size(); i++) {
				refuuidList.add(roleUserList.get(i).get("uuid").toString());
			}
			//delete the records from U_PORTAL_REFROLEUSER
			autoService.deleteByAllUUID("U_PORTAL_REFROLEUSER", refuuidList); 

			List<HashMap> orgUserList = autoService.findByHql("from U_PORTAL_REFORGUSER where useruuid='"
							+ uuid + "'");

			ArrayList<String> refuuidList2 = new ArrayList<String>();

			for (int i = 0; i < orgUserList.size(); i++) {
				refuuidList2.add(orgUserList.get(i).get("uuid").toString());
			}
			//delete the records from U_PORTAL_REFORGUSER
			autoService.deleteByAllUUID("U_PORTAL_REFORGUSER", refuuidList2); 
		} catch (Exception e) {
			e.printStackTrace();
			return SERVER_ERROR;
		}
		return OK;
	}
	/**
	 * updateUserPassword: Update user password
	 *
	 * @param user_uuid 
	 * @param oldPassword 
	 * @param newPassword 
	 * @return Long error code
	 * @throws 
	*/
	public long updateUserPassword(String user_uuid, String oldPassword,
			String newPassword) {

		HashMap user = (HashMap) autoService.findByUUID("U_PORTAL_USER",
				user_uuid);
		// user doesn't exist
		if (user == null) {
			return LOGIN_USER_LOGINNAME_ERROR; 
		}
        // if old password is ok, modify the new password
		if (user.get("password").equals(DigestUtils.md5Hex(oldPassword))) {
			user.put("password", DigestUtils.md5Hex(newPassword));
			try {
				//update the database
				autoService.update("U_PORTAL_USER", user); 
				return OK; // return success
			} catch (Exception e) {
				e.printStackTrace();
				return SERVER_ERROR; // server exception
			}
		} else {
			// return old password error
			return USER_OLDPASSWORD_ERROR; 
		}
	}
	/**
	 * queryUsers: Query user list
	 *
	 * @return List<HashMap> 
	 * @throws 
	*/
	public List<HashMap> queryUsers() {
       return queryUsers(null);

	}
	
	private List<HashMap> queryUsers(String nameKeyStr){
		String hql = "select ";
		hql += "new map( ";
		hql += "upu.uuid as useruuid, ";
		hql += "upu.usercode as usercode, ";
		hql += "upu.username as username, ";
		hql += "upu.loginname as loginname, ";
		hql += "upu.password as password, ";
		hql += "upu.email as email, ";
		hql += "upu.telephone as telephone, ";
		hql += "upu.state as state ";
		hql += ") ";
		hql += "from ";
		hql += "U_PORTAL_USER as upu ";
		if(nameKeyStr!=null&&nameKeyStr.length()!=0){
			hql += "where Upper(upu.username) like '%" + nameKeyStr.toUpperCase()
			+ "%' ";
		}
		hql += "order by  upper(username)";

		List<HashMap> usersTemp = autoService.findByHql(hql);

		List<HashMap> refOrgList = autoService.findAll("U_PORTAL_REFORGUSER");

		List<HashMap> orgList = rightService.queryPortalOrg();

		for (int i = 0; i < usersTemp.size(); i++) {

			String useruuid = usersTemp.get(i).get("useruuid").toString();
			String companyName = null;
			String depName = null;
			for (int j = 0; j < refOrgList.size(); j++) {

				HashMap refOrg = refOrgList.get(j);

				if (useruuid.equals(refOrg.get("useruuid"))) {
					HashMap org = null;
					for (int k = 0; k < orgList.size(); k++) {
						if (refOrg.get("orguuid").equals(
								orgList.get(k).get("uuid").toString())) {
							org = orgList.get(k);
						}
					}
					if (org != null) {
						if (org.get("state").equals("0")) {
							companyName = String.valueOf(org.get("orgname"));
						} else if (org.get("state").equals("1")) {
							depName = String.valueOf(org.get("orgname"));
						}
					}
				}
			}

			usersTemp.get(i).put("company", companyName);
			usersTemp.get(i).put("department", depName);
		}

		return usersTemp;
	}
	/**
	 * queryUserRoles: Query user and its user group
	 *
	 * @return List<HashMap>
	 * @throws 
	*/
	public List<HashMap> queryUserRoles() {

		StringBuffer hql = new StringBuffer();

		hql.append("select new map( ");
		hql.append("upu.uuid as useruuid, ");
		hql.append("upu.usercode as usercode, ");
		hql.append("upu.username as username, ");
		hql.append("upu.loginname as loginname, ");
		hql.append("upu.password as password, ");
		hql.append("upu.email as email, ");
		hql.append("upu.telephone as telephone, ");
		hql.append("upr.rolename as rolename )");
		hql.append("from ");
		hql.append("U_PORTAL_USER upu, ");
		hql.append("U_PORTAL_REFROLEUSER ref, ");
		hql.append("U_PORTAL_ROLE upr ");
		hql.append("where upu.uuid = ref.useruuid ");
		hql.append("and upr.uuid = ref.roleuuid ");

		return takeHqlByProperty(hql.toString(),"rolename");
	}
	/**
	 * queryUserRoles: Query user and its user group according to user name
	 *
	 * @return List<HashMap>
	 * @throws 
	*/
	public List<HashMap> queryUserRoles(String username) {

		StringBuffer hql = new StringBuffer();

		hql.append("select new map( ");
		hql.append("upu.uuid as useruuid, ");
		hql.append("upu.usercode as usercode, ");
		hql.append("upu.username as username, ");
		hql.append("upu.loginname as loginname, ");
		hql.append("upu.password as password, ");
		hql.append("upu.email as email, ");
		hql.append("upu.telephone as telephone, ");
		hql.append("upr.rolename as rolename )");
		hql.append("from ");
		hql.append("U_PORTAL_USER upu, ");
		hql.append("U_PORTAL_REFROLEUSER ref, ");
		hql.append("U_PORTAL_ROLE upr ");
		hql.append("where upu.uuid = ref.useruuid ");
		hql.append("and upr.uuid = ref.roleuuid ");
		hql.append("and Upper(upu.username) like '%" + username.toUpperCase() + "%' ");

		List<HashMap> usersTemp = autoService.findByHql(hql.toString());

		List<HashMap> users = new ArrayList<HashMap>();

		for (int i = 0; i < usersTemp.size(); i++) {
			boolean existFlag = false;

			for (int j = 0; j < users.size(); j++) {
				if (usersTemp.get(i).get("useruuid").equals(users.get(j).get("useruuid"))) {
					existFlag = true;
					users.get(j).put("rolename",users.get(j).get("rolename") + ", "
									+ usersTemp.get(i).get("rolename"));
					break;
				}
			}

			if (!existFlag) {
				users.add(usersTemp.get(i));
			}
		}

		return users;
	}
	/**
	 * queryUsersByName: find the users according to the key words
	 * 
	 * @param nameKeyStr
	 *            user name key words
	 * @return List<HashMap> user list
	 * @throws
	 */
	public List<HashMap> queryUsersByName(String nameKeyStr) {
     /*
		String hql = "select ";
		hql += "new map( ";
		hql += "upu.uuid as useruuid, ";
		hql += "upu.usercode as usercode, ";
		hql += "upu.username as username, ";
		hql += "upu.loginname as loginname, ";
		hql += "upu.password as password, ";
		hql += "upu.email as email, ";
		hql += "upu.state as state, ";
		hql += "upu.telephone as telephone ";
		hql += ") ";
		hql += "from ";
		hql += "U_PORTAL_USER as upu ";
		hql += "where Upper(upu.username) like '%" + nameKeyStr.toUpperCase()
				+ "%' "; //compare after transferring the name key words to upper case
		hql += "order by  upper(username)";

		return takeHqlByProperty(hql.toString(),"orgname");*/
		return queryUsers(nameKeyStr);

	}
	/**
	 * make hql by properties
	 * @param hql
	 * @param propertyName
	 * @return
	 */
	public List<HashMap> takeHqlByProperty(String hql,String propertyName){
  	    List<HashMap> usersTemp = autoService.findByHql(hql.toString());
  
        List<HashMap> users = new ArrayList<HashMap>();
  
        for (int i = 0; i < usersTemp.size(); i++) {
            boolean existFlag = false;
  
            for (int j = 0; j < users.size(); j++) {
                if (usersTemp.get(i).get("useruuid")
                        .equals(users.get(j).get("useruuid"))) {
                    existFlag = true;
                    users.get(j).put(propertyName,users.get(j).get(propertyName) + ", "
                                    + usersTemp.get(i).get(propertyName));
                    break;
                }
            }
  
            if (!existFlag) {
                users.add(usersTemp.get(i));
            }
        }
        return users;
	}

}
