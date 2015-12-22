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
import org.utmost.util.CategoryUtil;

import com.vgc.databank.service.GanttService;
import com.vgc.databank.util.FuncUtil;
/**
 * @author BULL
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class GanttServiceTest {

    GanttService ganttService ;
    AutoService autoService ;
    FuncUtil  funcUtil;
	
	CategoryUtil categoryUtil;
	@Before
	public void setUp() throws Exception {
	    ganttService = PowerMockito.spy(new GanttService());
	    autoService = PowerMockito.mock(AutoService.class);
	    funcUtil  = PowerMockito.mock(FuncUtil.class);
		Whitebox.setInternalState(ganttService, "autoService", autoService);
		Whitebox.setInternalState(ganttService, "funcUtil", funcUtil);
	}


    @Test
    public void testQueryGanttsByFuncs() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("uuid", java.util.UUID.randomUUID());
          hm.put("start_date", 10000L);
          hm.put("end_date", 10000L);
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("");
          
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
          
          List map = ganttService.queryGanttsByFuncs(new String[]{"1"},true);
          Assert.assertEquals(1, map.size());
    }
	
	@Test
    public void testQueryGanttsByUuid() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("uuid", java.util.UUID.randomUUID());
          hm.put("start_date", 10000L);
          hm.put("end_date", 10000L);
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("");
          
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
          
          List map = ganttService.queryGanttsByUuid("1");
          Assert.assertEquals(1, map.size());
    }
	
	@Test
    public void testQueryGanttsByFunc() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("commondata_uuid", java.util.UUID.randomUUID());
          hm.put("funcuuid", "funcuuid");
          hm.put("start_date", 10000L);
          hm.put("end_date", 10000L);
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("");
          
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr).thenReturn(testArr);
          Mockito.when(funcUtil.queryChildFuncsByFuncId(Mockito.isA(String.class),Mockito.isA(String.class))).thenReturn(testArr);
          List map = ganttService.queryGanttsByFunc("user_uuid","func_uuid",false,true);
          Assert.assertEquals(1, map.size());
    }
	
	@Test
    public void testQueryGanttAppendixByDate() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("commondata_uuid", java.util.UUID.randomUUID());
          hm.put("funcuuid", "funcuuid");
          hm.put("start_date", 10000L);
          hm.put("end_date", 10000L);
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("");
          
          List map = ganttService.queryGanttAppendixByDate("commondata_uuid","2015","02");
          Assert.assertEquals(0, map.size());
    }
	
	@Test
    public void testSaveGantt() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("commondata_uuid", java.util.UUID.randomUUID());
          hm.put("funcuuid", "funcuuid");
          hm.put("start_date", 10000L);
          hm.put("end_date", 10000L);
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("");
          
          long map = ganttService.saveGantt(hm);
          Assert.assertEquals(0, map);
    }
	
	@Test
    public void testPublish() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("commondata_uuid", java.util.UUID.randomUUID());
          hm.put("funcuuid", "funcuuid");
          hm.put("start_date", 10000L);
          hm.put("end_date", 10000L);
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("");
          
          long map = ganttService.publish("commondatauuid");
          Assert.assertEquals(39321, map);
    }
	
	 @Test
    public void testRepublish() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("commondata_uuid", java.util.UUID.randomUUID());
          hm.put("funcuuid", "funcuuid");
          hm.put("start_date", 10000L);
          hm.put("end_date", 10000L);
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("");
          
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
          long map = ganttService.republish("user_uuid");
          Assert.assertEquals(39321, map);
	 }
	 
	 @Test
	    public void testDeleteFile() {
	          List testArr = new ArrayList<HashMap>();
	          HashMap hm = new HashMap();
	          hm.put("commondata_uuid", java.util.UUID.randomUUID());
	          hm.put("funcuuid", "funcuuid");
	          hm.put("start_date", 10000L);
	          hm.put("end_date", 10000L);
	          testArr.add(hm);
	          List testArr1 = new ArrayList<HashMap>();
	          testArr1.add("");
	          
	          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
	          long map = ganttService.deleteFile("user_uuid","user_uuid");
	          Assert.assertEquals(0, map);
	     }
	 
	 @Test
     public void testAddGanttAppendix() {
           List testArr = new ArrayList<HashMap>();
           HashMap hm = new HashMap();
           hm.put("commondata_uuid", java.util.UUID.randomUUID());
           hm.put("funcuuid", "funcuuid");
           hm.put("start_date", 10000L);
           hm.put("end_date", 10000L);
           testArr.add(hm);
           List testArr1 = new ArrayList<HashMap>();
           testArr1.add("");
           
           long map = ganttService.addGanttAppendix(hm);
           Assert.assertEquals(39321, map);
      }
  }
