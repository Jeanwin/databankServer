package org.utmost.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.vgc.databank.service.SearchService;
/**
 * Format date
 * all methods are decorated static, so no new DateUtil()
 * @author bull
 *
 */
public class DateUtil {
	private static Logger logger = Logger.getLogger(SearchService.class);
	public static SimpleDateFormat getDateFormat(String str) {
		return new java.text.SimpleDateFormat(str);
	}
	/**
	 * get current system date
	 * @return
	 */
	public static String getNowDate() {
		return DateUtil.getDateFormat("yyyyMMdd HH:ss:mm").format(new Date());
	}
	public static String getNowDateForFN() {
		return DateUtil.getDateFormat("yyyyMMddHHssmm").format(new Date());
	}
	/**
	 * transfer date to define string format date
	 * @param date
	 * @param formateStr
	 * @return
	 */
	public static String formatDate(Date date,String formateStr){
		SimpleDateFormat sdf = new SimpleDateFormat(formateStr);
		return sdf.format(date);
	}
	/**
	 * modify string date format:  mm/dd/yyyy to  yyyy-mm-dd
	 * @param list
	 * @param keyStrs
	 */
	public static void transferStrDate(List list,List<String> keyStrs){
		for(int i=0;i < list.size();i++){
			Object obj = list.get(i);
			if(obj instanceof HashMap){
				HashMap hm = (HashMap) obj;
				String nowDate = "";
				for(String key:keyStrs){
					String originalDateStr = (String) hm.get(key);
					if(null != originalDateStr && !"".equals(originalDateStr.trim())){
						DateFormat dbFormat = new SimpleDateFormat("MM/dd/yyyy");
						DateFormat nowFormat = new SimpleDateFormat("yyyy-MM-dd");
						try {
							if(originalDateStr.indexOf("-")<0){
								Date dateDb = dbFormat.parse(originalDateStr);
								nowDate = nowFormat.format(dateDb);
								hm.put(key, nowDate);
							}else{
								hm.put(key, originalDateStr);
							}
						} catch (ParseException e) {
							logger.error("Parse date error", e);
						}
					}
				}
			}
		}
	}
	/**
	 * transfer yyyy-mm-dd to format
	 * @param oriStr
	 * @param format
	 * return 
	 */
	public static String transferDateStrFormat(String oriStr,DateFormat format){
	    DateFormat formatOri = new SimpleDateFormat("yyyy-MM-dd"); 
	    String returnStr = "";
	    if(oriStr!=null && !"".equals(oriStr.trim())){
            try {
              Date dateOri = formatOri.parse(oriStr);
              returnStr = format.format(dateOri);
            } catch (ParseException e) {
              e.printStackTrace();
            }
	    }
	    return returnStr;
	}
}
