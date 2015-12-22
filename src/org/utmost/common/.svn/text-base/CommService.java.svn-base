package org.utmost.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utmost.portal.service.TreeService;

/**
 * Python   console simulation class
 * 
 * @author wanglm
 * 
 */
@Service("CommService")
public abstract class CommService {
	@Autowired
	private DBSupport db;
	

	private static Logger logger = Logger.getLogger(CommService.class);

	/**
	 * obtain DBSupport object
	 * @return
	 */
	public DBSupport getDb() {
		if (db == null) {
			logger.error("reload DBSupport for CommService in SpringContext");
			try {
				Thread.sleep(2000);// synchronize thread time sleep
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			DBSupport dbsupport = (DBSupport) SpringContext.getBean("DBSupport");  //get dbsupport from spring context
			db = dbsupport;
		}
		return db;
	}
	/**
	 * set db
	 * @param db
	 */
	public void setDb(DBSupport db) {
		this.db = db;
	}
	
	public String formatDate(Object date){
		if(date instanceof Date){
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)date);
		}else{
			return date.toString();
		}
	}

}
