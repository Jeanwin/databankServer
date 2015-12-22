package com.vgc.databank.service;

import java.text.ParseException;
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
import org.utmost.util.DateUtil;
/**
 * 
 * @description Monitor Service
 * @author  
 * @version 3.2.0
 * @date Jan 29, 2015 10:53:40 AM
 */
@Service("MonitorService")
@SuppressWarnings({"unchecked","rawtypes"})
public class MonitorService {

	private static Logger logger = Logger.getLogger(MonitorService.class);

	@Autowired
	private AutoService autoService;
   
	/**
	 * @see get origial file name
	 * @param fileName
	 * @return
	 */
	public String getOriginalFileName(String fileName){
		List<HashMap> result = autoService.findByHql("from B_UPLOAD_FILES where file_name = '"+fileName+"' order by file_upload_time");
	    if(result==null||result.size()==0){
	    	return fileName;
	    }
	    HashMap file = result.get(0);
	    Object fileOrgName = file.get("file_original_name");
	    if(fileOrgName!=null){
	    	return fileOrgName.toString();
	    }
	    return fileName;
	}
	/**
	 * Record user info
	 * 
	 * @param user_UUID
	 * @param sessionID
	 * @param IP
	 */
	public void recordLogInfo(String user_UUID, String sessionID, String IP) {
		if(sessionID == null){
			sessionID="";
		}
		if(IP ==null){
			IP = "";
		}
		long loginTime = System.currentTimeMillis();	//obtain current system time
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //format date
		String loginTimeStr = sdf.format(new Date(loginTime));
		long count = getCount(user_UUID);
		HashMap hm = new HashMap();   //record user action
		hm.put("user_uuid", user_UUID);
		hm.put("sessionid", sessionID);
		hm.put("logincount", count);
		hm.put("logintime", loginTime);
		hm.put("logintimestring", loginTimeStr);
		hm.put("loginip", IP);
		autoService.save("U_USER_LOG", hm);
	}

	/**
	 * Record Logout Info
	 * 
	 * @param user_UUID
	 * @return
	 */
	public void recordLogoutInfo(String user_UUID) {
		long logoutTime = System.currentTimeMillis();  //get current system time
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String logoutTimeStr = sdf.format(new Date(logoutTime));
		String loguuid = getloguuid(user_UUID);
		if (user_UUID.equals(loguuid) || loguuid == null || loguuid.equals("null")) {
			logger.info("Currently logged on user does not log information, UUID for the user is " + user_UUID);
		} else {
			String loghql = "from U_USER_LOG u where u.uuid='" + loguuid + "'";
			List<HashMap> list = autoService.findByHql(loghql);
			if (list != null && list.size() == 1) {
				list.get(0).put("logouttime", logoutTime);		//set logout time
				list.get(0).put("logouttimestring", logoutTimeStr);	//update logout string time
				autoService.update("U_USER_LOG", list.get(0));		//update db
			}
		}
	}

	/**
	 * get user login count
	 * 
	 * @param user_UUID
	 * @return
	 */
	public long getCount(String user_UUID) {
		long count = 1;
		String hql = "select new map (max(u.logincount) as count) from U_USER_LOG u where u.user_uuid='" + user_UUID + "'";
		List<HashMap> list = autoService.findByHql(hql);
		if (list.size() == 1 && list.get(0).get("count") != null) {
			count = (Long) list.get(0).get("count");
		}
		return count + 1;
	}

	/**
	 * record user down load info
	 * 
	 * @param path
	 * @param file
	 * @param author
	 * @param user_UUID
	 * @param last_modify_date_str
	 * @return
	 */
	public void renewalDownload(String path, String file, String author,
			String user_UUID, String last_modify_date_str) {
		String loguuid = getloguuid(user_UUID);
		if (loguuid == null) {
			logger.info("Currently logged on user does not log information, UUID for the user is " + user_UUID);
			return;
		}
		long downloadTime = System.currentTimeMillis();  //obtain current system time
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //date format
		String downloadTimeStr = sdf.format(new Date(downloadTime));  //get string time from date
		//modify date format to "mm/dd/yyyy"
		last_modify_date_str = DateUtil.transferDateStrFormat(last_modify_date_str, new SimpleDateFormat("MM/dd/yyyy"));
		HashMap hm = new HashMap();
		hm.put("user_log_uuid", loguuid);	//log user uuid
		hm.put("downloadfilepath", path + file);	//file path
		hm.put("last_modify_author", author);	//last modify author
		hm.put("last_modify_date_str", last_modify_date_str); //last modify date string
		hm.put("downloadtimestr", downloadTimeStr);	//download time string
		autoService.save("U_USER_DOWNLOAD_LOG", hm);	//save to db
	}

	/**
	 * get user log UUID
	 * 
	 * @param user_UUID
	 * @return loguuid
	 */
	public String getloguuid(String user_UUID) {
		String loguuid = null;
		long logintime = 0;
		String hql = "select new map( max(u.logintime) as logintime) from U_USER_LOG u where u.user_uuid='" + user_UUID + "'";
		List<HashMap> list = autoService.findByHql(hql);
		if (list.get(0).get("logintime") == null) {  //hasn't login time return null
			return null;
		}
		if (list.size() == 1 && (Long) list.get(0).get("logintime") != 0) {
			logintime = (Long) list.get(0).get("logintime");
		}
		hql = "select new map( u.uuid as loguuid) from U_USER_LOG u where u.logintime='" + logintime + "'";
		list = autoService.findByHql(hql);
		if (list.size() == 1 && list.get(0).get("loguuid") != null) {
			loguuid = (String) list.get(0).get("loguuid");
		}
		return loguuid;
	}

	/**
	 * get user login info list
	 * 
	 * @return
	 */
	public List<HashMap> getLoglist() {
		Date d = new Date();  //obtain current system date
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String ds = sdf.format(d);
		try {
			d = sdf.parse(ds);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long l = d.getTime();
		String hql = "select new map(l.uuid as uuid,l.user_uuid as user_uuid,l.sessionid as sessionid,l.logincount as logincount, l.logintime as logintime, l.logintimestring as logintimestring, l.logouttimestring as logouttimestring, l.loginip as loginip, u.username as username, u.loginname as loginname ) from U_USER_LOG l,U_PORTAL_USER u where l.user_uuid=u.uuid and l.logintime>"+l+" order by u.loginname desc, l.logincount desc";
		List<HashMap> list = autoService.findByHql(hql);
		return list;
	}
	/**
	 * get user login detail list
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<HashMap> getPageLoglist(int currentPage,int pageSize) {
		String hql = "select new map(l.logintime as logintime) from U_USER_LOG l,U_PORTAL_USER u where l.user_uuid=u.uuid order by l.logintime desc";
        List<HashMap> list = autoService.pagination(currentPage, pageSize, hql);
        if(list.size()==0){
            return null;
        }
        long lastLogInTime = (Long)list.get(0).get("logintime");  //last login time
        long firstLogInTime = (Long)list.get(list.size()-1).get("logintime");
        String hqlDownLog = "select new map(l.uuid as uuid,l.user_uuid as user_uuid,l.sessionid as sessionid,l.logincount as logincount, l.logintime as logintime, l.logintimestring as logintimestring, l.logouttimestring as logouttimestring, l.loginip as loginip, u.username as username, u.loginname as loginname, (select count(uuid) from U_USER_DOWNLOAD_LOG dl where dl.user_log_uuid = l.uuid) as downloadtimes) from U_USER_LOG l,U_PORTAL_USER u where l.user_uuid=u.uuid and l.logintime>="+firstLogInTime+" and l.logintime<="+lastLogInTime+" order by l.logintime desc";
        return autoService.findByHql(hqlDownLog);
	}
	/**
	 * get count of user login
	 * @return
	 */
	public long getTotalRows() {
		long totalRows = 0;
		String hql = "select new map ( count(*) as total ) from U_USER_LOG l,U_PORTAL_USER u where l.user_uuid=u.uuid";
		List<HashMap> list = autoService.findByHql(hql);
		if (list.size() == 1 && list.get(0).get("total") != null) {
			totalRows = (Long) list.get(0).get("total");
		}
		return totalRows;
	}
	/**
	 * user down load info detail
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @param user_log_uuid
	 * @return
	 */
	public List<HashMap> getDownloadInfo(int currentPage, int pageSize, String user_log_uuid) {
		String hql = "from U_USER_DOWNLOAD_LOG u where u.user_log_uuid='" + user_log_uuid + "' order by u.downloadfilepath desc";
		List<HashMap> list = autoService.pagination(currentPage, pageSize, hql);
		List<String> keyStr = new ArrayList<String>();	//date format need to modify
		keyStr.add("last_modify_date_str");
		DateUtil.transferStrDate(list, keyStr);  //format date
		return list;
	}
	/**
	 * get user's login count
	 * 
	 * @param user_log_uuid
	 * @return
	 */
	public long getDownloadTotalRows(String user_log_uuid) {
		long totalRows = 0;
		String hql = "select new map ( count(*) as total ) from U_USER_DOWNLOAD_LOG u where u.user_log_uuid='" + user_log_uuid + "'";
		List<HashMap> list = autoService.findByHql(hql);
		if (list.size() == 1 && list.get(0).get("total") != null) {
			totalRows = (Long) list.get(0).get("total");
		}
		return totalRows;
	}
	/**
	 * search account info by key word
	 * 
	 * @param keywords
	 * @return
	 */
	public List<HashMap> searchAcountInfo(String keywords) {
		Date d = new Date(); //obtain current system date
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String ds = sdf.format(d);
		try {
			d = sdf.parse(ds);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long l = d.getTime();
		
		keywords = keywords.trim();  //trim spacer
		if ("".equals(keywords)) {
			return getLoglist();
		} else {
			String hql = "select new map(l.uuid as uuid,l.user_uuid as user_uuid,l.sessionid as sessionid,l.logincount as logincount,l.logintimestring as logintimestring, l.logouttimestring as logouttimestring, l.logintime as logintime, l.loginip as loginip, r.username as username, r.loginname as loginname ) "
					+ "from U_USER_LOG l,U_PORTAL_USER r where l.user_uuid=r.uuid "
					+ "and ( " + "r.username like '%" + keywords + "%'"
					+ " or r.loginname like '%" + keywords + "%'" + " ) and "+"l.logintime>"+l;
			List<HashMap> list = autoService.findByHql(hql);
			return list;
		}
	}
	/**
	 * query account info by parameter
	 * 
	 * @param currentPage
	 * @param pageSize: a page display total line
	 * @param keywords
	 * @param from: from date string
	 * @param to: to date string
	 * @return
	 */
	public List<HashMap> searchAcountInfo(int currentPage, int pageSize, String keywords,String from,String to) {
		keywords = keywords.trim();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		StringBuilder hql = new StringBuilder("select new map(l.logintime as logintime) from U_USER_LOG l,U_PORTAL_USER r where l.user_uuid=r.uuid "); 
		if(from!=null&&!"".equals(from)){
			try {
				//translate string 'from date' to long date for compare
			    Date fromDate = sdf.parse(DateUtil.transferDateStrFormat(from, sdf));
				hql.append(" and l.logintime>").append(fromDate.getTime());
			} catch (ParseException e) {
				logger.info("from Date("+from+") ParseException: "+e.getMessage());
			}
		}
		if(to!=null&&!"".equals(to)){
			try {
				//translate string 'to date' to long date for compare 
			    Date toDate = sdf.parse(DateUtil.transferDateStrFormat(to, sdf));
				hql.append(" and l.logintime<").append(toDate.getTime()+86400000);
			} catch (ParseException e) {
				logger.info("to Date("+to+") ParseException: "+e.getMessage());
			}
		}
		/**
		 * keywords like query username or loginname columns
		 */
		if(keywords!=null&&!"".equals(keywords)){
			hql.append("and ( ");
			hql.append("r.username like '%");
			hql.append(keywords);
			hql.append("%'");
			hql.append(" or r.loginname like '%" + keywords + "%'" + " )");
		}
		//order by login time desc
		hql.append(" order by l.logintime desc");
		List<HashMap> list = autoService.pagination(currentPage, pageSize, hql.toString());
		if(list.size()==0){
		    return null;
		}
		//last login time
		long lastLogInTime = (Long)list.get(0).get("logintime");
		//first login time
	    long firstLogInTime = (Long)list.get(list.size()-1).get("logintime");
	    StringBuffer hqlDownLog = new StringBuffer("select new map(l.uuid as uuid,l.user_uuid as user_uuid,l.sessionid as sessionid,l.logincount as logincount, l.logintime as logintime, l.logintimestring as logintimestring, l.logouttimestring as logouttimestring, l.loginip as loginip, r.username as username, r.loginname as loginname, (select count(uuid) from U_USER_DOWNLOAD_LOG dl where dl.user_log_uuid = l.uuid) as downloadtimes) from  U_USER_LOG l,U_PORTAL_USER r where l.user_uuid=r.uuid ");
	    if(from!=null&&!"".equals(from)){
          try {
        	  //translate string 'from date' to long time for compare condition
              Date fromDate = sdf.parse(DateUtil.transferDateStrFormat(from, sdf));
              hqlDownLog.append(" and l.logintime>").append(fromDate.getTime());
          } catch (ParseException e) {
              logger.info("from Date("+from+") ParseException: "+e.getMessage());
          }
      }
      if(to!=null&&!"".equals(to)){
          try {
        	 //translate string 'to date' to long time for compare condition
              Date toDate = sdf.parse(DateUtil.transferDateStrFormat(to, sdf));
              hqlDownLog.append(" and l.logintime<").append(toDate.getTime()+86400000);
          } catch (ParseException e) {
              logger.info("to Date("+to+") ParseException: "+e.getMessage());
          }
      }
      /**
		 * keywords like query username or loginname columns
		 */
      if(!"".equals(keywords)){
          hqlDownLog.append("and ( ");
          hqlDownLog.append("r.username like '%");
          hqlDownLog.append(keywords);
          hqlDownLog.append("%'");
          hqlDownLog.append(" or r.loginname like '%" + keywords + "%'" + " )");
      }
      hqlDownLog.append("and l.logintime>="+firstLogInTime+" and l.logintime<="+lastLogInTime+" order by l.logintime desc");
	    return autoService.findByHql(hqlDownLog.toString());
	}
	/**
	 * query account total rows by parameter
	 * 
	 * @param keywords
	 * @param from:from string date
	 * @param to:to string date
	 * @return totalRows: all data
	 */
	public long searchAcountTotalRows(String keywords,String from,String to) {
		long totalRows = 0;
		keywords = keywords.trim();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		StringBuilder hql = new StringBuilder("select new map( count(*) as totalRows ) "
			+ "from U_USER_LOG l,U_PORTAL_USER r where l.user_uuid=r.uuid "); 
		if(from!=null&&!"".equals(from)){
			try {
				//translate string 'from date' to long time for compare condition
			    Date fromDate = sdf.parse(DateUtil.transferDateStrFormat(from, sdf));
				hql.append(" and l.logintime>").append(fromDate.getTime());
			} catch (ParseException e) {
				logger.info("from Date("+from+") ParseException: "+e.getMessage());
			}
		}
		if(to!=null&&!"".equals(to)){
			try {
				//translate string 'to date' to long time for compare condition
			    Date toDate = sdf.parse(DateUtil.transferDateStrFormat(to, sdf));
				hql.append(" and l.logintime<").append(toDate.getTime()+86400000);
			} catch (ParseException e) {
				logger.info("to Date("+to+") ParseException: "+e.getMessage());
			}
		}
		/**
		 * keywords like query username or loginname columns
		 */
		if(keywords!=null&&!"".equals(keywords)){
			hql.append("and ( ");
			hql.append("r.username like '%");
			hql.append(keywords);
			hql.append("%'");
			hql.append(" or r.loginname like '%" + keywords + "%'" + " )");
		}
		List<HashMap> list = autoService.findByHql(hql.toString());
		if (list.size() == 1 && list.get(0).get("totalRows") != null) {
			totalRows = (Long) list.get(0).get("totalRows");  //obtain total rows
		}
		return totalRows;
	}

	/**
	 * delete account info
	 * 
	 * @param uuid
	 * @param user_uuid
	 * @return
	 */
	public boolean delAcountInfo(String uuid, String user_UUID) {
		String loguuid = getloguuid(user_UUID);
		if (uuid.equals(loguuid) || loguuid == null || loguuid.equals("null")) {
			return false;
		} else {
			String downloadhql = "from U_USER_DOWNLOAD_LOG u where u.user_log_uuid='" + uuid + "'";
			String loghql = "from U_USER_LOG u where u.uuid='" + uuid + "'";
			autoService.deleteAll("U_USER_DOWNLOAD_LOG", autoService.findByHql(downloadhql));
			autoService.deleteAll("U_USER_LOG", autoService.findByHql(loghql));
			return true;
		}
	}
	
	
	/**
	 * delete account info by UUID
	 * @param user_UUID
	 */
	public void delAcountAllInfo(String user_UUID){
		if(user_UUID == null){
			return ;
		}
		String loghql = "from U_USER_LOG u where u.user_uuid='" + user_UUID + "'";
		List<HashMap> list = autoService.findByHql(loghql);
		for(int i=0;i<list.size();i++){
			String downloadhql = "from U_USER_DOWNLOAD_LOG u where u.user_log_uuid='" + list.get(i).get("uuid") + "'";
			autoService.deleteAll("U_USER_DOWNLOAD_LOG", autoService.findByHql(downloadhql));
		}
		autoService.deleteAll("U_USER_LOG", list);
	}

}
