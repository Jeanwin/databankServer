package com.vgc.databank.junit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.utmost.common.SpringContext;

import com.vgc.databank.service.BatchDownloadService;
/**
 * Test BatchDownloadService
 * 
 * @author bull
 *
 */
public class TestBatchDownloadService {

	@Before
	public void setUpBeforeClass() throws Exception {}
	/**
	 * obtain batchDownloadService from spring context bean 'BatchDownloadService',
	 * test getPath method,
	 * Iterator map and print info
	 * @throws Exception
	 */
	@Test
	public void testBatchDownload1() throws Exception {
		BatchDownloadService batchDownloadService = (BatchDownloadService) SpringContext
				.getBean("BatchDownloadService");
		HashMap hm = (HashMap) batchDownloadService.getPath(batchDownloadService.getLeafUUID("02", "777"));
		Set set = hm.keySet();
		Iterator iter = set.iterator();
		while(iter.hasNext()){
			String key = (String)iter.next();
			String value = (String)hm.get(key);
			System.out.println("Path:" + value);
		}
	}

	
	@Test
	public void testBatchDownload2() throws Exception {
		BatchDownloadService batchDownloadService = (BatchDownloadService) SpringContext
				.getBean("BatchDownloadService");
//		batchDownloadService.zipAllFiles("02", "777");
		
	}
	/**
	 * obtain batchDownloadService from spring context bean 'BatchDownloadService',
	 * test delTemp method
	 * @throws Exception
	 */
	@Test
	public void testBatchDownload3() throws Exception {
		BatchDownloadService batchDownloadService = (BatchDownloadService) SpringContext
				.getBean("BatchDownloadService");
		batchDownloadService.delTemp();
		
	}

}
