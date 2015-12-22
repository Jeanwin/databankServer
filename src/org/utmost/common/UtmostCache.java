package org.utmost.common;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * non implement
 * 
 * @author wanglm
 * 
 */
@Component("UtmostCache")
public class UtmostCache extends CommService {
	private static Logger logger = Logger.getLogger(UtmostCache.class);

	private HashMap<String, List> cacheMap = new HashMap<String, List>();

	public UtmostCache() {
		// logger.info("Initialized UtmostCache");
		// cacheTPL();
		// logger.info("UtmostCache initialized");
	}

	/**
	 * load tpl data mapping
	 */
	public void cacheTPL() {
		//find all data table 'U_TPL_TEMPLATEDATA' and put map key is 'U_TPL_TEMPLATEDATA'
		cacheMap.put("U_TPL_TEMPLATEDATA", getDb().findAll("U_TPL_TEMPLATEDATA")); 
		//find all data table 'U_TPL_TEMPLATEVIEW' and put map key is 'U_TPL_TEMPLATEVIEW'
		cacheMap.put("U_TPL_TEMPLATEVIEW", getDb().findAll("U_TPL_TEMPLATEVIEW"));
	}
	/**
	 * obtain map by key
	 * @param key
	 * @return
	 */
	public List getList(String key) {
		return cacheMap.get(key);
	}
}
