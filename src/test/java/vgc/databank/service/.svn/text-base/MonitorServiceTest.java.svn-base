package test.java.vgc.databank.service;
/**
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import org.utmost.portal.service.AutoService;

import com.vgc.databank.service.MonitorService;

import flex.messaging.io.ArrayCollection;
/**
 * @author BULL
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class MonitorServiceTest {

	MonitorService monitorService ;
	
	AutoService autoService;
	
	@Before
	public void setUp() throws Exception {
		monitorService = PowerMockito.spy(new MonitorService());
		autoService = PowerMockito.mock(AutoService.class);
		Whitebox.setInternalState(monitorService, "autoService", autoService);
	}
	

	@Test
	public void testRecordLogInfo() {
		monitorService.recordLogInfo("1005","","");
	}
	
	@Test
	public void testGetCount() {
		List<HashMap> list = new ArrayList<HashMap>();
		HashMap map = new HashMap();
		map.put("count", Long.parseLong("11"));
		list.add(map);
		Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);
		Long result = monitorService.getCount("1005");
		Assert.assertEquals(Long.valueOf("12"), result);
	}
	
	@Test
	public void testRenewalDownload() {
		List testArr = new ArrayList();
        HashMap hm = new HashMap();
        hm.put("logintime", 1234L);
        hm.put("loguuid", "d");
        testArr.add(hm);
        List testArr1 = new ArrayList<HashMap>();
        testArr1.add("standards");
        Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
        
        monitorService.renewalDownload("1005","","","","");
	}
	
	@Test
	public void testGetloguuid() {
		List<HashMap> list = new ArrayList<HashMap>();
		HashMap map = new HashMap();
		map.put("logintime", Long.parseLong("11"));
		map.put("loguuid", "1");
		list.add(map);
		Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);
		String result = monitorService.getloguuid("1005");
		Assert.assertEquals("1", result);
	}
	
	@Test
	public void testGetLoglist() {
		List<HashMap> list = new ArrayList<HashMap>();
		HashMap map = new HashMap();
		map.put("logintime", Long.parseLong("11"));
		map.put("loguuid", "1");
		list.add(map);
		Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);
		monitorService.getLoglist();
	}
	
	@Test
	public void testGetTotalRows() {
		List<HashMap> list = new ArrayList<HashMap>();
		HashMap map = new HashMap();
		map.put("total", Long.parseLong("11"));
		list.add(map);
		Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);
		Long result = monitorService.getTotalRows();
		Assert.assertEquals(Long.valueOf("11"), result);
	}
	
	@Test
    public void testRecordLogoutInfo() {
          List testArr = new ArrayList();
          HashMap hm = new HashMap();
          hm.put("logintime", 1234L);
          hm.put("loguuid", "d");
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("standards");
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
          
          monitorService.recordLogoutInfo("user_id");
    }
	   @Test
      public void testGetPageLoglist() {
            List testArr = new ArrayList();
            HashMap hm = new HashMap();
            hm.put("logintime", 1234L);
            hm.put("loguuid", "d");
            testArr.add(hm);
            List testArr1 = new ArrayList<HashMap>();
            testArr1.add("standards");
            Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
            
            Mockito.when(autoService.pagination(Mockito.anyInt(),Mockito.anyInt(),Mockito.isA(String.class))).thenReturn(testArr);
            monitorService.getPageLoglist(1,1);
      }
	@Test
    public void testGetDownloadInfo() {
          List testArr = new ArrayList();
          HashMap hm = new HashMap();
          hm.put("logintime", 1234L);
          hm.put("loguuid", "d");
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("standards");
          Mockito.when(autoService.pagination(Mockito.anyInt(),Mockito.anyInt(),Mockito.isA(String.class))).thenReturn(testArr);
          monitorService.getDownloadInfo(1,1,"userId");
    }
	
	@Test
    public void testGetDownloadTotalRows() {
          List testArr = new ArrayList();
          HashMap hm = new HashMap();
          hm.put("logintime", 1234L);
          hm.put("loguuid", "d");
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("standards");
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
          monitorService.getDownloadTotalRows("userId");
    }
	
	@Test
    public void testSearchAcountInfo() {
          List testArr = new ArrayList();
          HashMap hm = new HashMap();
          hm.put("logintime", 1234L);
          hm.put("loguuid", "d");
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("standards");
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
          monitorService.searchAcountInfo("userId");
    }
	
	@Test
    public void testSearchAcountInfo1() {
          List testArr = new ArrayList();
          HashMap hm = new HashMap();
          hm.put("logintime", 1234L);
          hm.put("loguuid", "d");
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("standards");
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
          Mockito.when(autoService.pagination(Mockito.anyInt(),Mockito.anyInt(),Mockito.isA(String.class))).thenReturn(testArr);
          monitorService.searchAcountInfo(1,1,"userId","2015-01-01","2015-01-01");
    }
	
	@Test
    public void testSearchAcountTotalRows() {
          List testArr = new ArrayList();
          HashMap hm = new HashMap();
          hm.put("logintime", 1234L);
          hm.put("loguuid", "d");
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("standards");
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
          Mockito.when(autoService.pagination(Mockito.anyInt(),Mockito.anyInt(),Mockito.isA(String.class))).thenReturn(testArr);
          monitorService.searchAcountTotalRows("userId","2015-01-01","2015-01-01");
    }
	
	@Test
    public void testDelAcountAllInfo() {
          List testArr = new ArrayList();
          HashMap hm = new HashMap();
          hm.put("logintime", 1234L);
          hm.put("loguuid", "d");
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("standards");
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
          Mockito.when(autoService.pagination(Mockito.anyInt(),Mockito.anyInt(),Mockito.isA(String.class))).thenReturn(testArr);
          monitorService.delAcountAllInfo("uuid");
    }
	
	@Test
    public void testDelAcountInfo() {
          List testArr = new ArrayList();
          HashMap hm = new HashMap();
          hm.put("logintime", 1234L);
          hm.put("loguuid", "d");
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("standards");
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
          Mockito.when(autoService.pagination(Mockito.anyInt(),Mockito.anyInt(),Mockito.isA(String.class))).thenReturn(testArr);
          monitorService.delAcountInfo("uuid","userid");
    }
}
