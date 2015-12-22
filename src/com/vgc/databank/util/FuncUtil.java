/**
 * 2015-1-27 14:27:37
 * This class is used for func's manipulation
 */
package com.vgc.databank.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utmost.portal.service.AutoService;

import com.vgc.databank.service.PortalFuncService;

/**
 * @author Zhao Wei
 *
 */
@Service("FuncUtil")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class FuncUtil implements Constant{
	/**
	 * queryFuncByUser: Return corresponding func list according to user authorization and func type
	 * 
	 * @param user_uuid
	 * @param funcType Func type 1:content tree, 2:function tree
	 * @return List<HashMap> Func list
	 * @throws 
	*/
	@Autowired
	private AutoService autoService;
	
	@Autowired
	private PortalFuncService funcService;
	
	/**
	 * query func by user uuid and func type
	 * @param user_uuid
	 * @param funcType
	 * @return
	 */
	public List<HashMap> queryFuncByUser(String user_uuid, String funcType) {
		//if the user is super user, return all the func list
		if (SUPERMAN_UUID.equals(user_uuid)) { 
			return queryAllFuncs(funcType);
		}
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

		return takeHql(hql);

	}
	/**
	 * queryFuncByUser: Return corresponding func list according to user privileges, func type and nodes use scope
	 * 
	 * @param user_uuid
	 * @param funcType Func type 1:content tree, 2:function tree
	 * @param funcscope Func scope 1:front, 2:back stage
	 * @return List<HashMap> Func list
	 * @throws 
	*/
	public List<HashMap> queryFuncByUser(String user_uuid, String funcType,
			String funcscope) {
		//if the current user is super user, return all the func list direclty
		if (SUPERMAN_UUID.equals(user_uuid)) {
			return queryAllFuncs(funcType);
		}

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
		hql += "upf.funcscope as funcscope, ";
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
		hql += "and upf.funcscope = '" + funcscope + "'";
		hql += "and COALESCE(upf.isdelete,'0') !='1' ";
		hql += "order by upf.funcindex ";

		return takeHql(hql);

	}
	/**
	 * queryAllFuncs: Query all nodes of tree based on func type
	 *
	 * @param funcType tree type 1:content tree  2:function tree
	 * @return List<HashMap> FUNC list
	 * @throws 
	*/
	public List<HashMap> queryAllFuncs(String funcType) {

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
		hql += "upf.isdelete as isdelete, ";
		hql += "upf.state as state )";
		hql += "from U_PORTAL_FUNC upf where upf.functype='" + funcType+"' ";
		hql += "and COALESCE(upf.isdelete,'0') !='1' ";
		hql += "order by upf.funcindex ";
		//find all the sub nodes for func_uuid
		List<HashMap> funcList = autoService.findByHql(hql); 

		return funcList;

	}
	/**
	 * @see queryAllFuncs: find all the func nodes according to the diffrent function types(func tree or menu tree)
	 * @return List<HashMap>
	 * @throws
	 */
	public List<HashMap> queryAllFuncs() {

		List<HashMap> menuList = funcService.reBuildTree("1");
		List<HashMap> funcList = funcService.reBuildTree("2");

		HashMap menu = new HashMap();
		HashMap func = new HashMap();

		menu.put("children", menuList);
		menu.put("funcname", "Menu Tree");
		func.put("children", funcList);
		func.put("funcname", "Function Tree");

		List<HashMap> treeObj = new ArrayList<HashMap>();
		treeObj.add(menu);
		treeObj.add(func);

		return treeObj;
	}
	/**
	 * queryFuncByUser: Return all nodes user has privileges to download under this func node according to user privileges and func uuid
	 * 
	 * @param user_uuid
	 * @param func_uuid
	 * @return List<HashMap> Func list
	 * @throws 
	*/
	public List<HashMap> queryFuncsByFuncId(String user_uuid, String func_uuid) {

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
		hql += "and upu.uuid = '" + user_uuid + "' ";
		hql += "and upf.pid='" + func_uuid + "' ";
		hql += "and COALESCE(upf.isdelete,'0') !='1' ";
		//find all the sub nodes for func_uuid
		List<HashMap> funcList = autoService.findByHql(hql);

		if (funcList != null) {
			// find recurthe sub nodes for every node
			for (int i = 0; i < funcList.size(); i++) { 
				HashMap<String, Object> tempMap = funcList.get(i);
				List<HashMap> tempList = queryFuncsByFuncId(user_uuid, tempMap
						.get("funcuuid").toString());
				if (tempList != null && tempList.size() > 0) {
					tempMap.put("children", tempList);
				}
			}
		}

		return funcList;

	}
	/**
	 * queryFuncByUser: Return all leaf nodes user has privileges to download under this func node according to user privileges and func uuid
	 * 
	 * @param user_uuid
	 * @param func_uuid
	 * @return List<HashMap> Func list
	 * @throws 
	*/
	public List<HashMap> queryChildFuncsByFuncId(String user_uuid,String func_uuid) {
		String hql = "select ";
		if (SUPERMAN_UUID.equals(user_uuid)) {
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
			hql += "upf.state as state ";
			hql += ") ";
			hql += "from ";
			hql += "U_PORTAL_FUNC upf ";
			hql += "where upf.pid='" + func_uuid + "' ";
			hql += "and COALESCE(upf.isdelete,'0') !='1' ";
		} else {
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
			hql += "and upu.uuid = '" + user_uuid + "' ";
			hql += "and upf.pid='" + func_uuid + "'";
			hql += "and COALESCE(upf.isdelete,'0') !='1' ";
		}
		 // find all the sub nodes for func_uuid
		List<HashMap> funcList = autoService.findByHql(hql);

		List<HashMap> childFuncList = new ArrayList<HashMap>();

		if (funcList != null) {
			// find all the sub nodes for every node
			for (int i = 0; i < funcList.size(); i++) { 
				HashMap<String, Object> tempMap = funcList.get(i);
				if (tempMap.get("nodetype").equals("3")) {
					childFuncList.add(tempMap);
				} else {
					List tempList = queryChildFuncsByFuncId(user_uuid, tempMap.get("funcuuid").toString());
					childFuncList.addAll(tempList);
				}
			}
		}

		HashMap<String, String> tempMap = new HashMap<String, String>();

		List<HashMap> tempList = new ArrayList<HashMap>();

		for (int i = 0; i < childFuncList.size(); i++) {
			HashMap temp = childFuncList.get(i);
			if (tempMap.get(temp.get("funcuuid").toString()) == null) {
				tempMap.put(temp.get("funcuuid").toString(), "");
				tempList.add(temp);
			}
		}

		return tempList;
	}
	/**
	 * queryFuncByUserOperation: Return corresponding func list according to user operation privileges and func type
	 *
	 * @param user_uuid
	 * @param funcType 	Func type 1:content tree, 2:function tree
	 * @param operation	operation table uuid
	 * @return List<HashMap> Func list
	 * @throws 
	*/
	public List<HashMap> queryFuncByUserOperation(String user_uuid,
			String funcType, String operation) {
		//if the current user is super user, return all the func list direclty
		if (SUPERMAN_UUID.equals(user_uuid)) { 
			return queryAllFuncs(funcType);
		}

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
		hql += "upf.isdelete as isdelete, ";
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
		hql += "and uo.uuid = '" + operation + "' ";
		hql += "and COALESCE(upf.isdelete,'0') !='1' ";
		hql += "order by upf.funcindex ";

		return takeHql(hql);

	}
	
	/**
	 * take hql
	 * @param hql
	 * @return 
	 */
	private List<HashMap> takeHql(String hql){
    	  List<HashMap> funcList = autoService.findByHql(hql);
    
          HashMap<String, String> tempMap = new HashMap<String, String>();
    
          List<HashMap> tempList = new ArrayList<HashMap>();
    
          for (int i = 0; i < funcList.size(); i++) {
              HashMap temp = funcList.get(i);
              if (tempMap.get(temp.get("funcuuid").toString()) == null) {
                  tempMap.put(temp.get("funcuuid").toString(), "");
                  tempList.add(temp);
              }
          }
          return tempList;
	}

}