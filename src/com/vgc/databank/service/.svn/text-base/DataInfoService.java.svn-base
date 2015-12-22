package com.vgc.databank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utmost.portal.service.AutoService;
import org.utmost.util.DateUtil;

import com.vgc.databank.util.FuncUtil;

/**
 * 
 * @description  Data Info Service
 * @author  
 * @version 3.2.0
 * @date Jan 29, 2015 10:45:30 AM
 */

@Service("DataInfoService")
@SuppressWarnings({ "unchecked", "rawtypes","unused" })
public class DataInfoService {
	private static Logger logger = Logger.getLogger(DataInfoService.class);


	@Autowired
	private AutoService autoService;
	
	@Autowired
	private FuncUtil funcUtil;

	/**
	 * 
	 * @description add Field 
	 * @param list
	 * @return
	 */
	
	public List<HashMap> addField(List<HashMap> list) {
		if (list != null) {
			HashMap hm = list.get(0);
			HashMap funcuuidHm = new HashMap();
			HashMap authorHm = new HashMap();
			HashMap timeHm = new HashMap();
			funcuuidHm.put("uuid", java.util.UUID.randomUUID());
			funcuuidHm.put("func_uuid", hm.get("func_uuid"));
			funcuuidHm.put("field_name", "func_uuid");
			
			authorHm.put("uuid", java.util.UUID.randomUUID());
			authorHm.put("func_uuid", hm.get("func_uuid"));
			authorHm.put("field_name", "last_modify_author");
			
			timeHm.put("uuid", java.util.UUID.randomUUID());
			timeHm.put("func_uuid", hm.get("func_uuid"));
			timeHm.put("field_name", "last_modify_date_str");
			list.add(0,authorHm);
			list.add(0,funcuuidHm);
			list.add(0,timeHm);
		}
		return list;
	}

	/**
	 * 
	 * @description list Data Parse No
	 * @param list
	 * 
	 * @return List<HashMap>
	 */
	public List<HashMap> listDataParseNo(List<HashMap> list) {
		List<HashMap> listData = new ArrayList<HashMap>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				HashMap hm = list.get(i);
				StringBuilder sb = new StringBuilder();
				/**
				 * get standard_no from standard_attribute1,standard_attribute2,
				 *  standard_attribute3 and no_
				 */
				String standard_attribute1 = "";
				String standard_attribute2 = "";
				String standard_attribute3 = "";
				//piece together swf and pdf eg:xxx.pdf/xxx.swf
				String en_swf = "";
				String no_ = "";
				String version_ = "";
				
				standard_attribute1 = (String) hm.get("standard_attribute1");
				standard_attribute2 = (String) hm.get("standard_attribute2");
				standard_attribute3 = (String) hm.get("standard_attribute3");
				
				no_ = (String) hm.get("no_");
				version_ = (String) hm.get("version_");
				en_swf = (String) hm.get("download_gb_en_swf");
				
				sb.append(standard_attribute1 != null ? standard_attribute1: "");
				sb.append(standard_attribute2 != null ? standard_attribute2: "");
				sb.append(standard_attribute3 != null ? standard_attribute3: "").append(" ");
				sb.append(no_ != null ? no_ : "").append(version_ != null ?"-":"");
				sb.append(version_ != null ? version_ : "");
				hm.put("standard_no", sb.toString());
				String others = hm.get("doc_")== null ?"":(String) hm.get("doc_");
				if(!"".equals(others) && !"null".equals(others.toLowerCase())){
					hm.put("doc_", others.substring(0, others.length()-1));
				}
				
				//replace mandatory value 1/0 in yes/no
				String mandatory = (String) hm.get("ismandatory");
				if("1".equals(mandatory)){
					hm.put("ismandatory", "Yes");
				}else if("0".equals(mandatory)){
					hm.put("ismandatory", "No");
				}
				/**
				 * mandatory add a field 'mycheckbox' for advanced search result screen
				 * function:remember items selected of all pages 
				 */
				hm.put("mycheckbox", false);
				listData.add(hm);
			}
		}
		return listData;
	}
	/**
	 * 
	 * @description list Title Parse No
	 * @param list
	 * @param main_func_UUID
	 * @param show_mode
	 * 
	 * @return List<HashMap>
	 */
	public List<HashMap> listTitleParseNo(List<HashMap> list,String main_func_UUID, long show_mode) {
		HashMap map = new HashMap();
		HashMap gorvernorMap = new HashMap();
		HashMap committeeMap = new HashMap();
		boolean arg = true;
		String[] no = new String[] { "standard_attribute1", "standard_attribute2", "standard_attribute3", "no_", "version_" };
		for (int i = 0; i < list.size(); i++) {
			HashMap hm = list.get(i);
			if (contains(((String) hm.get("field_name")).toLowerCase(), no) && arg) { //judge hm if has value in no array
					map.put("uuid", java.util.UUID.randomUUID());
					map.put("func_uuid", hm.get("func_uuid"));
					map.put("field_name", "standard_no");	//set field_name's value is standard_no
					map.put("show_name_en", "No.");	//set show_name_en's value is No.
					map.put("show_name_ch", "标准号");	//set show_name_ch's value is No.
					map.put("order_no", list.get(0).get("order_no"));
					map.put("show_mode", hm.get("show_mode"));
					map.put("is_protected", hm.get("is_protected"));
					map.put("field_type", hm.get("field_type"));
					map.put("field_value", hm.get("field_value"));
					map.put("is_config", hm.get("is_config"));
					map.put("tooltip", hm.get("tooltip"));
					map.put("regex", hm.get("regex"));
					map.put("show_width", 120);
					arg = false;
			}
			if ("gorvernor_code_uuid".equals(((String) hm.get("field_name")).toLowerCase())
					&& StaticFunUUID.standardUUID.equals(main_func_UUID) && show_mode == 2) {
				//give field_name, show_name_en and show_name_ch value hard code
				//every is govenor_code, Govenor and Code and sector
				gorvernorMap.put("field_name", "govenor_code");
				gorvernorMap.put("show_name_en", "Govenor and Code");
				gorvernorMap.put("show_name_ch", "主管部门");
			}
			if ("technical_committees_code_uuid".equals(((String) hm.get("field_name")).toLowerCase())
					&& StaticFunUUID.standardUUID.equals(main_func_UUID)&& show_mode == 2) {
				//set hard code
				//field_name is technical_committees_code
				//show_name_en is Technical Committees and Code
				//show_name_ch is
				committeeMap.put("field_name", "technical_committees_code");
				committeeMap.put("show_name_en", "Technical Committees and Code");
				committeeMap.put("show_name_ch", "归口单位");
			}
		}
		if (StaticFunUUID.standardUUID.equals(main_func_UUID) && show_mode == 2) {
			list.add(0, committeeMap);
			list.add(0, gorvernorMap);
		}
		if (!arg) {
			list.add(0, map);
		}
		return list;
	}

	/**
	 * 
	 * @description return all field description(user can see)
	 * @param main_func_UUID
	 * @param func_UUID
	 * @param user_UUID
	 * @param hasProtected
	 * @param show_mode
	 * 
	 * @return List<HashMap>
	 */
	public List<HashMap> getFieldTitle(String main_func_UUID, String func_UUID,
			String user_UUID, boolean hasProtected, long show_mode) {
		logger.debug("get fieldtitle method param: ( main_func_UUID:"
				+ main_func_UUID + " )," + "( func_UUID:" + func_UUID + " ),"
				+ "(user_UUID:" + user_UUID + " )," + "( hasProtected:"
				+ hasProtected + " )," + "( show_mode:" + show_mode + " )");
		String hql = constructSqlbyProtectedAndShowmode( hasProtected, show_mode, main_func_UUID); //construct hql
		logger.info(hql.toString());
		List<HashMap> listTitle = autoService.findByHql(hql);  //obtain titles
		return addField(listTitle);
	}

	/**
	 * 
	 * @description get Field Title Info
	 * @param main_func_UUID
	 * @param func_UUID
	 * @param user_UUID
	 * @param hasProtected
	 * @param show_mode
	 * 
	 * @return List<HashMap>
	 */
	public List<HashMap> getFieldTitleInfo(String main_func_UUID,
			String func_UUID, String user_UUID, boolean hasProtected,
			long show_mode) {
		logger.debug("get fieldtitleinfo method param: ( main_func_UUID:"
				+ main_func_UUID + " )," + "( func_UUID:" + func_UUID + " ),"
				+ "(user_UUID:" + user_UUID + " )," + "( hasProtected:"
				+ hasProtected + " )," + "( show_mode:" + show_mode + " )");
		//construct hql
		String hql = constructSqlbyProtectedAndShowmode( hasProtected, show_mode, main_func_UUID) + " order by b.order_no";
		List<HashMap> listTitle = autoService.findByHqlCache(hql);
		if(listTitle!=null){
			//parse no
			//No make up of standard_attribute1, standard_attribute2, standard_attribute3, no_ 
			//and version_
			listTitle = listTitleParseNo(listTitle, main_func_UUID, show_mode);
			listTitle = addField(listTitle);  //add field
				logger.debug("listTitle size of parseNo: " + listTitle.size() + "hql :" + hql);
		}
		return listTitle;
	}
	/**
	 * construct Sql by Protected And Show mode
	 * 
	 * @param hasProtected
	 * @param show_mode
	 * @param main_func_UUID
	 * @return
	 */
	private String constructSqlbyProtectedAndShowmode(boolean hasProtected,long show_mode,String main_func_UUID){
	    return "from B_CLASS_FIELD b where b.func_uuid='"+ main_func_UUID + "' "
	                +(!hasProtected?" and b.is_protected='0' " : "")  
	                      +(show_mode == 1?" and b.show_mode=" + show_mode : "");
	}
	
	/**
	 * 
	 * @description  Construct all field name array(user can see)
	 * @param main_func_UUID
	 * @param func_UUID
	 * @param user_UUID
	 * @param hasProtected
	 * @param show_mode
	 * 
	 * @return String[]
	 */
	public String[] getFieldName(String main_func_UUID, String func_UUID,
			String user_UUID, boolean hasProtected, long show_mode) {
		List<HashMap> list = this.getFieldTitle(main_func_UUID, func_UUID,user_UUID, hasProtected, show_mode);
		String[] string = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			string[i] = ((String) list.get(i).get("field_name")).toLowerCase();
		}
		return string;
	}


	/**
	 * 
	 * @description get Field Data
	 * @param main_func_UUID
	 * @param func_UUID
	 * @param user_UUID
	 * @param pageNo
	 * @param pageSize
	 * @param hasProtected
	 * @param show_mode
	 * 
	 * @return List<HashMap>
	 */
	public List<HashMap> getFieldData(String main_func_UUID, String func_UUID,
			String user_UUID, int pageNo, int pageSize, boolean hasProtected, long show_mode) {
		logger.debug("get fieldtitleinfo method param: ( main_func_UUID:"
				+ main_func_UUID + " )," + "( func_UUID:" + func_UUID + " ),"
				+ "( pageNo:" + pageNo + " )," + "( pageSize:" + pageSize
				+ " )," + "(user_UUID:" + user_UUID + " )," + "( hasProtected:"
				+ hasProtected + " )," + "( show_mode:" + show_mode + " )");
		List<HashMap> listData = null;
		//field name
		String[] fieldName = this.getFieldName(main_func_UUID, func_UUID,user_UUID, hasProtected, show_mode);
		//node array
		String[] leafArr = this.getSubFunc_UUID(func_UUID, user_UUID);
		StringBuffer hql = null;
		if (leafArr != null) {
			logger.debug("leafArr size: " + leafArr.length);
			hql = new StringBuffer();
			if (StaticFunUUID.standardUUID.equals(main_func_UUID)) {
				hql.append("select new map( ");
				hql.append("g.govenor_code as govenor_code , t.technical_committees_code as technical_committees_code,");
				hql.append("b.uuid as uuid").append(" ,");
				hql.append("b.last_modify_author as last_modify_author , ");
				hql.append("b.func_uuid as func_uuid , ");
				for (int i = 0; i < fieldName.length; i++) {
					hql.append("b.").append(fieldName[i].toLowerCase()).append(
							"  as  ").append(fieldName[i].toLowerCase()).append(" ,");
				}
				hql.deleteCharAt(hql.length() - 1).append(
								" ) from B_COMMONDATA as b , B_GOVENOR_CODE as g, B_TECHNICAL_COMMITTEES_CODE as t")
						.append(" where b.func_uuid in ( ");
				for (int i = 0; i < leafArr.length; i++) {
					hql.append("'").append(leafArr[i]).append("',");
				}
				hql.deleteCharAt(hql.length() - 1).append(")");
				hql.append("and b.gorvernor_code_uuid=g.uuid and b.technical_committees_code_uuid=t.uuid");
				hql.append(" order by COALESCE(b.standard_attribute1, '') ,COALESCE(b.standard_attribute2, '') ,COALESCE(b.standard_attribute3, '')  ,b.no_ ,b.version_  ");
				logger.info("getData:  " + hql.toString() + ", mainFunc: "+ main_func_UUID);
			} else {
				hql.append("select new map( ");
				for (int i = 0; i < fieldName.length; i++) {
					hql.append("b.uuid as uuid").append(" ,");
					hql.append("b.").append(fieldName[i].toLowerCase()).append("  as  ").append(fieldName[i].toLowerCase()).append(" ,");
				}
				hql.deleteCharAt(hql.length() - 1).append(" ) from B_COMMONDATA as b ").append(" where b.func_uuid in ( ");
				for (int i = 0; i < leafArr.length; i++) {
					hql.append("'").append(leafArr[i]).append("',");
				}
				hql.deleteCharAt(hql.length() - 1).append(")");
				hql.append(" order by b.title_en ");
				logger.info("getData:  " + hql.toString() + ", mainFunc: "+ main_func_UUID);
			}
			listData = autoService.pagination(pageNo, pageSize, hql.toString());
		}
		
		listData = listDataParseNo(listData);
		List<String> keyStr = new ArrayList<String>();   //Date format need to modify
		keyStr.add("last_modify_date_str");
		keyStr.add("issuance_date_str");
		keyStr.add("execute_date_str");
		keyStr.add("pre_execute_date_str");
		keyStr.add("online_auto_execute_date_str");
		keyStr.add("publication_date_str");
		DateUtil.transferStrDate(listData, keyStr);
		return listData; 
	}

	/**
	 * @see return the document order number
	 * @param main_func_UUID
	 * @param func_UUID
	 * @param user_UUID
	 * @param hasProtected
	 * @param show_mode
	 * @param uuid
	 * @return
	 */
	public long getOrderByDocumentUUID(String main_func_UUID,
			String func_UUID, String user_UUID, boolean hasProtected,long show_mode,String uuid,int pageSize){
		if(uuid==null){
			return -1;
		}
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select new map( ");
		sBuilder.append("b.standard_attribute1 as standard_attribute1,b.standard_attribute2 as standard_attribute2,b.standard_attribute3 as standard_attribute3");
		sBuilder.append(",b.no_ as no_,b.version_ as version_,b.title_en as title_en) from B_COMMONDATA b where b.uuid = '");
		sBuilder.append(uuid);
		sBuilder.append("'");
		List<HashMap> listToDate = autoService.findByHql(sBuilder.toString());
		if(listToDate!=null&&listToDate.size()>0){
			HashMap hMap = listToDate.get(0);
			String standard_attribute1 = (String) hMap.get("standard_attribute1");
			String standard_attribute2 = (String) hMap.get("standard_attribute2");
			String standard_attribute3 = (String) hMap.get("standard_attribute3");
			String no = (String) hMap.get("no_");
			String version = (String) hMap.get("version_");
			String title_en = (String) hMap.get("title_en");
		    if(logger.isDebugEnabled()){
		    	logger.debug("values:"+ standard_attribute1+","+standard_attribute2+","+standard_attribute3+","+no+","+version+","+title_en);
		    }
//		    String[] fieldName = this.getFieldName(main_func_UUID, func_UUID,user_UUID, hasProtected, show_mode);
			String[] leafArr = this.getSubFunc_UUID(func_UUID, user_UUID);
			if (leafArr != null) {
				StringBuffer hql = new StringBuffer();
				if (StaticFunUUID.standardUUID.equals(main_func_UUID)) {
					hql.append("select new map( b.uuid as uuid");
					hql.append(" ) from B_COMMONDATA as b , B_GOVENOR_CODE as g, B_TECHNICAL_COMMITTEES_CODE as t")
							.append(" where b.func_uuid in ( ");
					for (int i = 0; i < leafArr.length; i++) {
						hql.append("'").append(leafArr[i]).append("',");
					}
					hql.deleteCharAt(hql.length() - 1).append(")");
					hql.append("and b.gorvernor_code_uuid=g.uuid and b.technical_committees_code_uuid=t.uuid ");
					hql.append(" order by COALESCE(b.standard_attribute1, '') ,COALESCE(b.standard_attribute2, '') ,COALESCE(b.standard_attribute3, '')  ,b.no_ ,b.version_  ");
				}else{
					hql.append("select new map( b.uuid as uuid");
					hql.append(" ) from B_COMMONDATA as b ").append(" where b.func_uuid in ( ");
					for (int i = 0; i < leafArr.length; i++) {
						hql.append("'").append(leafArr[i]).append("',");
					}
					hql.deleteCharAt(hql.length() - 1).append(")");
					hql.append("order by b.title_en ");
				}
				List<HashMap> result = autoService.findByHql(hql.toString());
				if(result!=null&&result.size()>0){
					for(int i=0;i<result.size();i++){
						HashMap hp = result.get(i);
						if(hp!=null&&hp.get("uuid").equals(uuid)){
							return i/pageSize+1;
						}
					}
				}
			}
		}
		return -1;
		
	}
	
	/**
	 * 
	 * @description get Field Data Total Rows
	 * @param main_func_UUID
	 * @param func_UUID
	 * @param user_UUID
	 * @param hasProtected
	 * @param show_mode
	 * 
	 * @return long
	 */
	public long getFieldDataTotalRows(String main_func_UUID,
			String func_UUID, String user_UUID, boolean hasProtected,long show_mode) {
		logger.debug("get fieldtitleinfo method param: ( main_func_UUID:"
				+ main_func_UUID + " )," + "( func_UUID:" + func_UUID + " ),"
				+ " )," + "(user_UUID:" + user_UUID + " )," + "( hasProtected:"
				+ hasProtected + " )," + "( show_mode:" + show_mode + " )");
		List<HashMap> listData = null;
		long totalRows = 0;
		String[] leafArr = this.getSubFunc_UUID(func_UUID, user_UUID);
		StringBuffer hql = null;
		if (leafArr != null) {
			logger.debug("leafArr size: " + leafArr.length);
			hql = new StringBuffer();
			if (StaticFunUUID.standardUUID.equals(main_func_UUID)) {
				hql.append("select new map( count(*) as totalRows ");
				hql.append(" ) from B_COMMONDATA as b , B_GOVENOR_CODE as g, B_TECHNICAL_COMMITTEES_CODE as t").append(" where b.func_uuid in ( ");
				for (int i = 0; i < leafArr.length; i++) {
					hql.append("'").append(leafArr[i]).append("',");
				}
				hql.deleteCharAt(hql.length() - 1).append(")");
				hql.append("and b.gorvernor_code_uuid=g.uuid and b.technical_committees_code_uuid=t.uuid");
				logger.info("getData:  " + hql.toString() + ", mainFunc: "+ main_func_UUID);
			} else {
				hql.append("select new map( count(*) as totalRows ");
				hql.append(" ) from B_COMMONDATA as b ").append(" where b.func_uuid in ( ");
				for (int i = 0; i < leafArr.length; i++) {
					hql.append("'").append(leafArr[i]).append("',");
				}
				hql.deleteCharAt(hql.length() - 1).append(")");
				logger.info("getData:  " + hql.toString() + ", mainFunc: "+ main_func_UUID);
			}
			listData = autoService.findByHql(hql.toString());
			if (listData != null && listData.size() == 1) {
				totalRows = (Long) listData.get(0).get("totalRows");
			}
		}
		return totalRows;
	}

	/**
	 * 
	 * @description Display one recorder detail title information
	 * @param main_func_UUID
	 * @param func_UUID
	 * @param user_UUID
	 * @param hasProtected
	 * @param show_mode
	 * @param uuid
	 * 
	 * @return List<HashMap>
	 */
	public List<HashMap> getSingleDataTitle(String main_func_UUID,
			String func_UUID, String user_UUID, boolean hasProtected,
			long show_mode, String uuid) {
		List<HashMap> listTitle = this.getFieldTitleInfo(main_func_UUID, func_UUID, user_UUID,
				hasProtected, show_mode);
		if("02".equals(main_func_UUID) || "0202".equals(main_func_UUID)){	//issued node and gantt node detail
			return obtainTitleList(listTitle,main_func_UUID);  //obtain titles defined title name and order
		}else{
			return listTitle;
		}
	}
	/**
	 * for issued and draft
	 * 
	 * @param listTitle
	 * @param main_func_UUID
	 * @return
	 */
	private List<HashMap> obtainTitleList(List<HashMap> listTitle,String main_func_UUID){
	    List<String> titleList = new ArrayList<String>();
	    //define title name and order for detail display
		String titleStr = "standard_no,"+ 
		("02".equals(main_func_UUID)?"effect_status":"status") +
		",title_en,title_ch,issuance_date_str,pre_execute_date_str," +
		"execute_date_str,online_auto_execute_date_str,replaced_standard,ismandatory,applicability,power_type," +
		"download_ece_r,download_eec,download_fmvss,doc_,revision_est,isparts,isccc,isbulletin,is_national_emi,is_local_emi,technical_committees_code_uuid," +
		"application_degree,gorvernor_code_uuid,drafting_committee,plan_no,contact_person,remark_";
		String[] titleArr = titleStr.split(",");
		for (int i = 0; i < titleArr.length; i++) {
			titleList.add(titleArr[i]);
		}
		return parseTitle(listTitle,titleList);  //replace title name for us need to display
	}
	/**
	 * adjust title order
	 * replace title info
	 * @param listTitle
	 * @param titleList
	 * @return
	 */
	private List<HashMap> parseTitle(List<HashMap> listTitle,List<String> titleList){
		List<HashMap> newTitle = new ArrayList<HashMap>();
		HashMap[] tempTitle = new HashMap[29];
		for (HashMap hm : listTitle) {
			String fieldName = (String) hm.get("field_name");
			if(fieldName != null && -1 != titleList.indexOf(fieldName.toLowerCase())){
				if("effect_status".equals(fieldName.toLowerCase())){
					hm.put("show_name_en", "Effective/ Abolished");
					tempTitle[titleList.indexOf(fieldName.toLowerCase())] = hm;
				}else if("title_en".equals(fieldName.toLowerCase())){
					hm.put("show_name_en", "Standard Title _ EN");
					tempTitle[titleList.indexOf(fieldName.toLowerCase())] = hm;
				}else if("online_auto_execute_date_str".equals(fieldName.toLowerCase())){
					hm.put("show_name_en", "Implementation Date for In-Production");
					tempTitle[titleList.indexOf(fieldName.toLowerCase())] = hm;
				}else if("execute_date_str".equals(fieldName.toLowerCase())){
					hm.put("show_name_en", "Implementation Date for New Type");
					tempTitle[titleList.indexOf(fieldName.toLowerCase())] = hm;
				}else{
					tempTitle[titleList.indexOf(fieldName.toLowerCase())] = hm;
				}
			}
		} 
		for (int i = 0; i < tempTitle.length; i++) {
			newTitle.add(tempTitle[i]);
		}
		return newTitle;
	}

	/**
	 * 
	 * @description Display one recorder detail info
	 * @param main_func_UUID
	 * @param func_UUID
	 * @param user_UUID
	 * @param hasProtected
	 * @param show_mode
	 * @param uuid
	 * 
	 * @return List<HashMap>
	 */
	public List<HashMap> getSingleData(String main_func_UUID, String func_UUID,
			String user_UUID, boolean hasProtected, long show_mode, String uuid) {
		List<HashMap> listData = null;
		String[] fieldName = this.getFieldName(main_func_UUID, func_UUID, user_UUID, hasProtected, show_mode);	//title name
		String[] leafArr = this.getSubFunc_UUID(func_UUID, user_UUID);
		StringBuffer hql = null;
		if (leafArr != null) {
			hql = new StringBuffer();
			if (StaticFunUUID.standardUUID.equals(main_func_UUID)) {
				hql.append("select new map( ");
				hql.append("g.govenor_code as govenor_code , t.technical_committees_code as technical_committees_code,");
				hql.append("b.uuid as uuid").append(" ,");
				for (int i = 0; i < fieldName.length; i++) {
					hql.append("b.").append(fieldName[i].toLowerCase()).append("  as  ").append(fieldName[i].toLowerCase()).append(" ,");
				}
				hql.deleteCharAt(hql.length() - 1)
						.append(" ,b.gorvernor_code_uuid_others as gorvernor_code_uuid_others) from B_COMMONDATA as b , B_GOVENOR_CODE as g, B_TECHNICAL_COMMITTEES_CODE as t where b.func_uuid in ( ");
				for (int i = 0; i < leafArr.length; i++) {
					hql.append("'").append(leafArr[i]).append("',");
				}
				hql.deleteCharAt(hql.length() - 1).append(")").append(" and b.uuid='").append(uuid).append("' ");
				hql.append("and b.gorvernor_code_uuid=g.uuid and b.technical_committees_code_uuid=t.uuid");
				logger.info("getSingleData:  " + hql.toString()+ ", mainFunc: " + main_func_UUID);
			} else {
				hql.append("select new map( ");
				for (int i = 0; i < fieldName.length; i++) {
					hql.append("b.uuid as uuid").append(" ,");
					hql.append("b.").append(fieldName[i].toLowerCase()).append("  as  ").append(fieldName[i].toLowerCase()).append(" ,");
				}
				hql.deleteCharAt(hql.length() - 1).append(" ,b.gorvernor_code_uuid_others as gorvernor_code_uuid_others) from B_COMMONDATA as b  where b.func_uuid in ( ");
				for (int i = 0; i < leafArr.length; i++) {
					hql.append("'").append(leafArr[i]).append("',");
				}
				hql.deleteCharAt(hql.length() - 1).append(")").append(" and b.uuid='").append(uuid).append("' ");
			}
			listData = autoService.findByHql(hql.toString());
		}
		listData = listDataParseNo(listData);  //parse No_
		List<String> keyStr = new ArrayList<String>();		//all is need to modify string date format
		keyStr.add("issuance_date_str");
		keyStr.add("execute_date_str");
		keyStr.add("last_modify_date_str");
		keyStr.add("pre_execute_date_str");
		keyStr.add("online_auto_execute_date_str");
		keyStr.add("publication_date_str");
		DateUtil.transferStrDate(listData,keyStr);
		return listData;
	}

	/**
	 * Judge if des contained in src array
	 * 
	 * @param des
	 * @param src
	 * 
	 * @return boolean
	 */
	public boolean contains(String des, String[] src) {
		for (int i = 0; i < src.length; i++) {
			if (des.equals(src[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Obtain all leaf node under this node
	 * 
	 * @param func_UUID
	 * @param user_UUID
	 * 
	 * @return String[]
	 */
	public String[] getSubFunc_UUID(String func_UUID, String user_UUID) {
		String[] str = null;
		String node_TYPE = this.getNodeTYPE(func_UUID);
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
	 * 
	 * @description Obtain node type
	 * @param func_UUID
	 * 
	 * @return String
	 */
	public String getNodeTYPE(String func_UUID) {
		String hql = "select u.nodetype from U_PORTAL_FUNC as u where u.uuid='"+ func_UUID + "'";
		Object o = autoService.findByHql(hql).get(0);
		String str = (String) o;
		return str;
	}
}