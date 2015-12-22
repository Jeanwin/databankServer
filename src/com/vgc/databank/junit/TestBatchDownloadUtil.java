package com.vgc.databank.junit;

import org.junit.Before;
import org.junit.Test;
import org.utmost.common.SpringContext;

import com.vgc.databank.service.BatchDownloadUtil;
import com.vgc.databank.service.DataInfoService;
import com.vgc.databank.service.SearchService;
/**
 * Test BatchDownloadUtil
 * @author bull
 *
 */
public class TestBatchDownloadUtil {
	
	@Before
	public  void setUpBeforeClass() throws Exception {}
	
	/**
	 * obtain batchDownloadUtil from spring context bean 'BatchDownloadUtil',
	 * test getTotalPathName method,
	 * print path 
	 * @throws Exception
	 */
	@Test
	public void testRecursion() throws Exception {
		BatchDownloadUtil batchDownloadUtil = (BatchDownloadUtil)SpringContext.getBean("BatchDownloadUtil");
		System.out.println(batchDownloadUtil.getTotalPathName("030101"));
	}
}
