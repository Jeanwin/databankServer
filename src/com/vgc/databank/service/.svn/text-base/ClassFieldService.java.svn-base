/**
 * ClassFieldService.java
 * com.vgc.databank.service
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2009-11-10 		Yangxb
 *
 * Copyright (c) 2009, TNT All Rights Reserved.
 */

package com.vgc.databank.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.utmost.common.CommService;
import org.utmost.spring.mvc.controller.DownloadController;

/**
 * 
 * @description Class Field Service
 * @author  
 * @version 3.2.0
 * @date Jan 29, 2015 10:24:42 AM
 */
@Service("classFieldService")
public class ClassFieldService extends CommService {
	private static Logger logger = Logger.getLogger(ClassFieldService.class);

	/**
	 * get all properties of this class by main uuid
	 * 
	 * @param UUID：main_UUID
	 * @return List：all properties of this class，
	 *         this list：include HashMaps，key:ClassField column name，value:property value
	 */
	public List getClassFieldByUUID(String FUNC_UUID) {
		// order by order_no
		return getDb().findByHql("from B_CLASS_FIELD b where b.func_uuid='" + FUNC_UUID
						+ "' order by b.order_no", false);
	}

	/**
	 * get all property in dictionary
	 * 
	 * @return totalClassFieldList：all columns collection,、
	 *         list：include HashMaps，key:ClassField column name,value:property value
	 */
	public List getTotalClassField() {
		return getDb().findByHql("select new map(s.uuid as data,s.field_name as label) from B_COMMONDATA_FIELD s order by s.field_name",false);
	}

	/**
	 * get all main list
	 * 
	 * @return totalClassList：all main list、
	 *         list：include HashMaps，key:ClassField column name,value:property value
	 */
	public List getTotalClass() {
		return getDb().findByHql("select new map(s.uuid as data,s.funcname as label) from U_PORTAL_FUNC s where s.nodetype='1' and s.functype='1' and s.funccode!='whatishot' and s.uuid!='02' order by s.uuid",false);
	}

}
