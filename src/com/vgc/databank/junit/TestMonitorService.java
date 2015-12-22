package com.vgc.databank.junit;

import org.junit.Before;
import org.junit.Test;
import org.utmost.common.SpringContext;

import com.vgc.databank.service.DataInfoService;
import com.vgc.databank.service.MonitorService;
/**
 * Test MonitorService 
 * @author bull
 *
 */
public class TestMonitorService {


	@Before
	public  void setUpBeforeClass() throws Exception {}
	/**
	 * print count, this data from bean 'MonitorService'
	 */
	@Test
	public void getCount(){
		MonitorService monitorService = (MonitorService)SpringContext.getBean("MonitorService");
		System.out.println(monitorService.getCount("777"));
	}
	/**
	 * obtain monitorService from bean 'MonitorService',
	 * test recordLogInfo method
	 */
	@Test
	public void record(){
		MonitorService monitorService = (MonitorService)SpringContext.getBean("MonitorService");
			monitorService.recordLogInfo("777", "3h34n34kk23", "127.0.0.1");
	}
	/**
	 * obtain monitorService from bean 'MonitorService',
	 * test getloguuid method,
	 * print uuid
	 */
	@Test
	public void getloguuid(){
		MonitorService monitorService = (MonitorService)SpringContext.getBean("MonitorService");
		System.out.println(monitorService.getloguuid("3"));
	}
	/**
	 * obtain monitorService from bean 'MonitorService',
	 * test delAcountInfo method,
	 * print result(true or false,means delete acount info success or fail)
	 */
	@Test
	public void delAcountInfo(){
		MonitorService monitorService = (MonitorService)SpringContext.getBean("MonitorService");
		System.out.println(monitorService.delAcountInfo("1900","777"));
	}
	/**
	 * obtain monitorService from bean 'MonitorService',
	 * test delAcountAllInfo method,
	 * delete user all info
	 */
	@Test
	public void delAcountAllInfo(){
		MonitorService monitorService = (MonitorService)SpringContext.getBean("MonitorService");
		monitorService.delAcountAllInfo("777");
	}
}
