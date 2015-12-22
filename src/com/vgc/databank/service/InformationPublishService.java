package com.vgc.databank.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utmost.common.DBSupport;
import org.utmost.portal.service.AutoService;
import org.utmost.util.DateUtil;

import com.vgc.databank.util.Constant;

import flex.messaging.io.ArrayCollection;

/**
 * 
 * @description Information Publish Service
 * @author  
 * @version 3.2.0
 * @date Jan 29, 2015 10:49:40 AM
 */
@SuppressWarnings("unchecked")
@Service("InformationPublishService")
public class InformationPublishService implements InformationPublishInterface,Constant {
	@Autowired
	private AutoService auto;

	@Autowired
	private DBSupport dbs;

	private static Logger logger = Logger.getLogger(InformationPublishService.class);

	/**
	 * 
	 * @description add Common Data With UUID
	 * @author 
	 * @param commondata
	 * 
	 * @return String
	 */
	public String addCommonDataWithUUID (ArrayCollection commondata){
		String uuid = null;
		HashMap hm = new HashMap();
		logger.debug("common data =" + commondata);
		try {
			for (int i = 0; i < commondata.size(); i++) {
				HashMap hm1 = (HashMap) commondata.get(i);
				if (hm1.get("text") != null && !"".equals(hm1.get("text").toString())) {
					hm.put(hm1.get("id"), hm1.get("text"));
					// Confirm string date if is null or "" 
					if ("issuance_date_str".equals(hm1.get("id"))) {
					  
						parseDate(hm,hm1,"issuance_date");
					}
					if ("execute_date_str".equals(hm1.get("id"))) {
					  
						parseDate(hm,hm1,"execute_date");
					}
					if ("pre_execute_date_str".equals(hm1.get("id"))) {
					  
						parseDate(hm,hm1,"pre_execute_date");
					}
					if ("online_auto_execute_date_str".equals(hm1.get("id"))) {
					  
						parseDate(hm,hm1,"online_auto_execute_date");
					}
					if ("last_modify_date_str".equals(hm1.get("id"))) {
						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
						try {
							//transfer date format: yyyy-mm-dd to mm/dd/yyyy
							String dateTmp = DateUtil.transferDateStrFormat(hm1.get("text").toString(),sdf);
							hm.put(hm1.get("id"), dateTmp);
							Long date = new java.util.Date().getTime();
							hm.put("last_modify_date", date);
						} catch (Exception e) {
							logger.error(e.getMessage());
						}
					}

				} else {
					// when get static page, then save
					if (hm1.get("issuance_date_str") != null
							&& !"".equals(hm1.get("issuance_date_str").toString().trim())) {
						
						parseDate2( hm, hm1,"issuance_date");
					}
					if (hm1.get("execute_date_str") != null
							&& !"".equals(hm1.get("execute_date_str").toString().trim())) {
					  
						parseDate2( hm, hm1,"execute_date");
					}
					if (hm1.get("pre_execute_date_str") != null
							&& !"".equals(hm1.get("pre_execute_date_str").toString().trim())) {
					  
						parseDate2( hm, hm1,"pre_execute_date");
					}
					if (hm1.get("online_auto_execute_date_str") != null
							&& !"".equals(hm1.get("online_auto_execute_date_str").toString().trim())) {
						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
						//transfer date format: yyyy-mm-dd to mm/dd/yyyy
						String dateTmp = DateUtil.transferDateStrFormat(hm1.get("online_auto_execute_date_str").toString(),sdf);
						hm1.put("online_auto_execute_date_str", dateTmp);
						Long date = sdf.parse(dateTmp).getTime();
						hm1.put("online_auto_execute_date", date);
					}
					if (hm1.get("last_modify_date_str") != null
							&& !"".equals(hm1.get("last_modify_date_str").toString().trim())) {
						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
						try {
							//transfer date format: yyyy-mm-dd to mm/dd/yyyy
							//String dateTmp = DateUtil.transferDateStrFormat(hm1.get("last_modify_date_str").toString(),sdf);
							hm1.put("last_modify_date_str", hm1.get("last_modify_date_str").toString());
							Long date = new java.util.Date().getTime();
							hm1.put("last_modify_date", date);
						} catch (Exception e) {
							logger.error(e.getMessage());
						}
					}
					int recordid = this.getRecordid("B_COMMONDATA") + 1;
					hm1.put("recordid", recordid);
					String commondatauuid = auto.saveOrUpdate("B_COMMONDATA",hm1);
					
					//flag = true;
					uuid = commondatauuid;
					if (hm1.get("start_date") != null
							&& hm1.get("end_date").toString() != null
							&& !"".equals(hm1.get("start_date").toString().trim())
							&& !"".equals(hm1.get("end_date").toString().trim())) {
						HashMap h2 = new HashMap();
						h2.put("commondata_uuid", commondatauuid);
						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
						//transfer date format: yyyy-mm-dd to mm/dd/yyyy
						String dateTmp = DateUtil.transferDateStrFormat(hm1.get("start_date").toString(),sdf);
						Long start_date = sdf.parse(dateTmp).getTime();
						//transfer date format: yyyy-mm-dd to mm/dd/yyyy
						String dateEndTmp = DateUtil.transferDateStrFormat(hm1.get("end_date").toString(),sdf);
						Long end_date = sdf.parse(dateEndTmp).getTime();
						h2.put("start_date", start_date);
						h2.put("end_date", end_date);
						h2.put("phase", "0");
						h2.put("state", "0");
						int recordidgantt = this.getRecordid("B_GANTT") + 1;
						h2.put("recordid", recordidgantt);
						boolean boolgantt = this.addGantt(h2);
						if (uuid!=null && boolgantt) {
							//flag = true;
						} else {
							uuid = null;
						}
					}
				}
			}
			if (((HashMap) commondata.get(0)).get("text") != null) {
				//Obtain max recordid, then plus 1
				int recordid = this.getRecordid("B_COMMONDATA") + 1;
				hm.put("recordid", recordid);
				uuid = auto.saveOrUpdate("B_COMMONDATA", hm);
				//flag = true;
			}
		} catch (Exception e) {
			uuid = null;
			//e.printStackTrace();
		}
		
		return uuid;
	}
	/**
	 * parse Date
	 * 
	 * @param hm
	 * @param hm1
	 * @param propertyName
	 * @return
	 */
	private void parseDate(HashMap hm,HashMap hm1,String propertyName){
          SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
          try {
            //transfer date format: yyyy-mm-dd to mm/dd/yyyy
            String dateTmp = DateUtil.transferDateStrFormat(hm1.get("text").toString(),sdf);
            hm.put(hm1.get("id"), dateTmp);
            //Transfer string date to long date
            Long date = sdf.parse(dateTmp).getTime();
            hm.put(propertyName, date);
          } catch (ParseException e) {
            logger.error(e.getMessage());
          }
	}
	/**
	 * parse Date2
	 * 
	 * @param hm
	 * @param hm1
	 * @param propertyName
	 * @return
	 */
	private void parseDate2(HashMap hm,HashMap hm1,String propertyName){
          SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
          try {
          //transfer date format: yyyy-mm-dd to mm/dd/yyyy
          String dateTmp = DateUtil.transferDateStrFormat(hm1.get(propertyName+"_str").toString(),sdf);
          hm1.put(propertyName+"_str", dateTmp);
          Long date = sdf.parse(dateTmp).getTime();
          hm1.put(propertyName, date);
          } catch (ParseException e) {
              logger.error(e.getMessage());
          }
	}
	/**
	 * 
	 * @description add Common Data
	 * 
	 * @param commondata
	 * 
	 * @return boolean
	 */
	public boolean addCommonData(ArrayCollection commondata) {
		// Add info(publish)
		//boolean flag = false;
		String uuid = addCommonDataWithUUID(commondata);
		if(uuid!=null){
			return true;
		}
		return false;
		
	}
	/**
	 * 
	 * @description testHashMap
	 * @return 
	 */
	public void testHashMap() {
		HashMap<String, Object> log = new HashMap<String, Object>();
		log.put("user_log_uuid", "336699");
		log.put("downloadfilepath", "aabbcc");
		log.put("last_modify_author", "qq");

		dbs.saveOrUpdate("U_USER_DOWNLOAD_LOG", log);

		System.out.println(log.get("uuid"));
	}
	/**
	 * 
	 * @description get Record id
	 * 
	 * @param table
	 * @return int
	 */
	public int getRecordid(String table) {
		int recordid = 0;
		try {
			if (auto != null) {
				Object o = auto.findByHql("select max(b.recordid) from " + table + " b").get(0);
				if (o != null)
					recordid = Integer.parseInt(auto.findByHql("select max(b.recordid) from " + table
											+ " b").get(0).toString());
				else
					recordid = 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return recordid;
	}
	/**
	 * 
	 * @description add Gantt
	 * @author 
	 * @param gantt
	 * @return Boolean
	 */
	public Boolean addGantt(HashMap gantt) {
		// TODO add plan start date and end date
		boolean flag = false;
		try {
			if (auto != null) {
				auto.save("B_GANTT", gantt);
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}

		return flag;
	}
	/**
	 * 
	 * @description judge Func Code
	 * @author
	 * @param uuid
	 * @return Boolean
	 */
	public Boolean judgeFuncCode(String uuid) {
		// TODO Confirm FuncCode value if is standard
		boolean flag = false;
		try {
			if (auto != null) {
				List list = auto.findByHql("select u.funccode from U_PORTAL_FUNC u where uuid='"
								+ uuid + "'");
				if ("standards".equals((String) list.get(0))) {
					flag = true;
				}
			} else {
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}

		return flag;
	}
	/**
	 * 
	 * @description query Class Field
	 * @author 
	 * @param funcuuid
	 * @return ArrayCollection
	 */
	public ArrayCollection queryClassField(String funcuuid) {
		ArrayCollection ac = new ArrayCollection();
		if (auto != null) {
			String hql = "select new map(c.field_name as field_name,c.show_name_en as show_name_en,c.field_type as field_type,c.field_value as field_value,c.tooltip as tooltip,c.regex as regex) from B_CLASS_FIELD c where func_uuid='"
					+ funcuuid + "' and is_config='1' order by order_no";
			List list = auto.findByHql(hql);
			for (int i = 0; i < list.size(); i++) {
				HashMap hm = (HashMap) list.get(i);
				HashMap hm1 = new HashMap();
				for (Object o : hm.entrySet()) {
					Map.Entry<String, String> m = (Map.Entry<String, String>) o;
					if (hm.get(m.getKey()) == null) {
						hm1.put(m.getKey(), "");
					} else {
						hm1.put(m.getKey(), m.getValue());
					}
				}
				ac.add(hm1);
			}
		}
		return ac;
	}

	/**
	 * 
	 * @description query Common Data
	 * @author 
	 * @param classfieldfuncuuid
	 * @param commondatauuid
	 * @return HashMap
	 */
	public HashMap queryCommonData(String classfieldfuncuuid,
			String commondatauuid) {
		// TODO query publish information
		List list = auto.findByHql("select field_name from B_CLASS_FIELD where func_uuid='"
						+ classfieldfuncuuid + "'");

		List al = null;

		if (list != null && list.size() > 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("select new map(c."
					+ String.valueOf(list.get(0)).toLowerCase() + " as "
					+ list.get(0));
			for (int i = 1; i < list.size(); i++) {
				sb.append(",c." + String.valueOf(list.get(i)).toLowerCase()
						+ " as " + list.get(i));
			}
			sb.append(", c.func_uuid as func_uuid, c.importance as importance,c.gorvernor_code_uuid_others as GORVERNOR_CODE_UUID_OTHERS) from B_COMMONDATA c where c.uuid='"
					+ commondatauuid + "'");
			al = auto.findByHql(sb.toString());
		}
		HashMap hm1 = new HashMap();
		if (al != null && al.size() > 0) {
			HashMap hm = (HashMap) al.get(0);
			for (Object o : hm.entrySet()) {
				Map.Entry<String, String> m = (Map.Entry<String, String>) o;
				if (hm.get(m.getKey()) == null) {
					hm.put(m.getKey(), "");
				} else {
					hm1.put(m.getKey(), m.getValue());
				}
			}
		}
		List<String> keyStr = new ArrayList<String>();  //date format need to modify
		keyStr.add("LAST_MODIFY_DATE_STR");
		keyStr.add("ISSUANCE_DATE_STR");
		keyStr.add("EXECUTE_DATE_STR");
		keyStr.add("PRE_EXECUTE_DATE_STR");
		keyStr.add("ONLINE_AUTO_EXECUTE_DATE_STR");
		keyStr.add("PUBLICATION_DATE_STR");
		List<HashMap> data = new ArrayList<HashMap>();
		data.add(hm1);
		DateUtil.transferStrDate(data, keyStr);
		return data.get(0);
	}
	/**
	 * 
	 * @description query Govenor Code
	 * @author 
	 * @return ArrayCollection
	 */
	public ArrayCollection queryGovenorCode() {
		// TODO Query GOVENOR_CODE result list
		ArrayCollection ac = new ArrayCollection();
		List al = auto.findByHql("from B_GOVENOR_CODE");
		for (int i = 0; i < al.size(); i++) {
			ac.add(al.get(i));
		}
		return ac;
	}
	/**
	 * 
	 * @description query Govenor Code
	 * @author 
	 * @param uuid
	 * @return String
	 */
	public String queryGovenorCode(String uuid) {
		// TODO  Query GOVENOR_CODE value based on uuid
		String str = "";
		str = (String) auto.findByHql("from B_GOVENOR_CODE where UUID='" + uuid + "'").get(0);
		return str;
	}
	/**
	 * 
	 * @description query Technical Committees Code
	 * @author 
	 * @return ArrayCollection
	 */
	public ArrayCollection queryTechnicalCommitteesCode() {
		// TODO Query TECHNICAL_COMMITTEES_CODE result list
		ArrayCollection ac = new ArrayCollection();
		List al = auto.findByHql("from B_TECHNICAL_COMMITTEES_CODE");
		for (int i = 0; i < al.size(); i++) {
			ac.add(al.get(i));
		}
		return ac;
	}
	/**
	 * 
	 * @description query Technical Committees Code
	 * @author
	 * @param uuid
	 * @return String
	 */
	public String queryTechnicalCommitteesCode(String uuid) {
		// TODO query TECHNICAL_COMMITTEES_CODE value based on uuid
		String str = "";
		str = (String) auto.findByHql("from B_TECHNICAL_COMMITTEES_CODE where UUID='" + uuid + "'").get(0);
		return str;
	}
	/**
	 * 
	 * @description update Common Data
	 * @author 
	 * @param commondata
	 * @return Boolean
	 */
	public Boolean updateCommonData(ArrayCollection commondata) {
		// TODO modify publish information
		boolean flag = false;
		HashMap hm = (HashMap) commondata.get(0);
		if (auto != null) {
			try {
				auto.update("B_COMMONDATA", hm);
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
			}
		}
		return flag;
	}
	/**
	 * 
	 * @description update Gantt
	 * @author 
	 * @param gantt
	 * @return Boolean
	 */
	public Boolean updateGantt(ArrayCollection gantt) {
		// TODO modify plan start date and end date
		boolean flag = false;
		HashMap hm = (HashMap) gantt.get(0);
		if (auto != null) {
			auto.update("B_GANTT", hm);
		}
		return flag;
	}

	/**
	 * deleteCommondata: Delete recorder table data based on commondatauuid
	 * 
	 * @param commondatauuid
	 * @param isGantt
	 *            If is gantt
	 * @return long
	 * @throws
	 */
	public long deleteCommondata(String commondatauuid, String isGantt) {

		try {
			auto.deleteByUUID("B_COMMONDATA", commondatauuid); 

			if (isGantt.equals("true")) {

				String hql = "from B_GANTT where commondata_uuid = '"
						+ commondatauuid + "'";

				List<HashMap> ganttList = auto.findByHql(hql);

				ArrayList<String> ganttUUIDList = new ArrayList<String>();

				for (int i = 0; i < ganttList.size(); i++) {
					ganttUUIDList.add(ganttList.get(i).get("uuid").toString());
				}

				auto.deleteByAllUUID("B_GANTT", ganttUUIDList); // Cascade delete gantt table

				String hql2 = "from B_GANTT_APPENDIX where commondata_uuid = '"
						+ commondatauuid + "'";

				List<HashMap> ganttAppendixList = auto.findByHql(hql2);

				ArrayList<String> ganttAppendixUUIDList = new ArrayList<String>();

				for (int i = 0; i < ganttAppendixList.size(); i++) {
					ganttAppendixUUIDList.add(ganttAppendixList.get(i).get("uuid").toString());
				}

				auto.deleteByAllUUID("B_GANTT_APPENDIX", ganttAppendixUUIDList); // Cascade delete gantt attachment table
			}
		} catch (Exception e) {
			e.printStackTrace();
			return SERVER_ERROR;
		}
		return OK;
	}
}
