package com.vgc.databank.junit;




import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.utmost.common.SpringContext;

import com.vgc.databank.service.DataInfoService;
import com.vgc.databank.service.SearchService;
/**
 * Test DataInfoService
 * 
 * @author bull
 *
 */
public class TestDataInfoService {

	
	
	@Before
	public  void setUpBeforeClass() throws Exception {}
	
	/**
	 * print result, check data from bean 'DataInfoService'
	 */
	@Test
	public void getgetFieldData(){
		DataInfoService dataInfoService = (DataInfoService)SpringContext.getBean("DataInfoService");
		List list = dataInfoService.getFieldData("02", "02010", "777", -1, -1, true, 1);
		System.out.println(list);
	}
}
