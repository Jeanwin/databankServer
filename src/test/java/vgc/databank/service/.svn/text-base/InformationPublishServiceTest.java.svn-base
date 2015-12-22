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

import com.vgc.databank.service.InformationPublishService;
import com.vgc.databank.util.FuncUtil;

import flex.messaging.io.ArrayCollection;
/**
 * @author BULL
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class InformationPublishServiceTest {

    InformationPublishService informationPublishService ;
    AutoService autoService ;
    FuncUtil  funcUtil;
	
	CategoryUtil categoryUtil;
	@Before
	public void setUp() throws Exception {
	    informationPublishService = PowerMockito.spy(new InformationPublishService());
	    autoService = PowerMockito.mock(AutoService.class);
	    funcUtil  = PowerMockito.mock(FuncUtil.class);
	    PowerMockito.mockStatic(InformationPublishService.class);
		Whitebox.setInternalState(informationPublishService, "auto", autoService);
//		Whitebox.setInternalState(informationPublishService, "funcUtil", funcUtil);
	}

    @Test
    public void testAddCommonDataWithUUID() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("start_date", "2015-01-01");
          hm.put("end_date", "2015-01-01");
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add(1);
          ArrayCollection ac = new ArrayCollection();
          ac.add(hm);
          Mockito.when(autoService.saveOrUpdate(Mockito.isA(String.class), Mockito.isA(HashMap.class))).thenReturn("");
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr1).thenReturn(testArr1);
          
          informationPublishService.addCommonDataWithUUID(ac);
    }
    
    @Test
    public void testGetRecordid() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("start_date", "2015-01-01");
          hm.put("end_date", "2015-01-01");
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add(1);
          ArrayCollection ac = new ArrayCollection();
          ac.add(hm);
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr1).thenReturn(testArr1);
          
          int map = informationPublishService.getRecordid("table");
          Assert.assertEquals(1, map);
    }
    
    @Test
    public void testAddGantt() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("start_date", "2015-01-01");
          hm.put("end_date", "2015-01-01");
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add(1);
          ArrayCollection ac = new ArrayCollection();
          ac.add(hm);
          Mockito.when(autoService.save(Mockito.isA(String.class),Mockito.isA(HashMap.class))).thenReturn("");
          
          boolean map = informationPublishService.addGantt(hm);
          Assert.assertEquals(true, map);
    }
    
    @Test
    public void testJudgeFuncCode() {
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("standards");
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr1);
          
          boolean map = informationPublishService.judgeFuncCode("table");
          Assert.assertEquals(true, map);
    }
    
    @Test
    public void testQueryClassField() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("start_date", "2015-01-01");
          hm.put("end_date", "2015-01-01");
          testArr.add(hm);
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
          
          ArrayCollection ac = informationPublishService.queryClassField("table");
          Assert.assertNotNull(ac);
    }
    
    @Test
    public void testQueryCommonData() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("start_date", "2015-01-01");
          hm.put("end_date", "2015-01-01");
          testArr.add(hm);
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
          
          HashMap ac = informationPublishService.queryCommonData("table","commondatauuid");
          Assert.assertNotNull(ac);
    }

    @Test
    public void testQueryGovenorCode() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("start_date", "2015-01-01");
          hm.put("end_date", "2015-01-01");
          testArr.add(hm);
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
          
          ArrayCollection ac = informationPublishService.queryGovenorCode();
          Assert.assertNotNull(ac);
    }

    @Test
    public void testQueryGovenorCode1() {
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("standards");
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr1);
          
          String ac = informationPublishService.queryGovenorCode("uuid");
          Assert.assertNotNull(ac);
    }
    

    @Test
    public void testQueryTechnicalCommitteesCode() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("start_date", "2015-01-01");
          hm.put("end_date", "2015-01-01");
          testArr.add(hm);
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
          
          ArrayCollection ac = informationPublishService.queryTechnicalCommitteesCode();
          Assert.assertNotNull(ac);
    }
    

    @Test
    public void testQueryTechnicalCommitteesCode1() {
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("standards");
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr1);
          
          String ac = informationPublishService.queryTechnicalCommitteesCode("uuid");
          Assert.assertNotNull(ac);
    }
    
    @Test
    public void testUpdateCommonData() {
          ArrayCollection ac = new ArrayCollection();
          HashMap hm = new HashMap();
          hm.put("start_date", "2015-01-01");
          ac.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("standards");
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr1);
          
          boolean map = informationPublishService.updateCommonData(ac);
          Assert.assertEquals(true, map);
    }

    @Test
    public void testUpdateGantt() {
          ArrayCollection ac = new ArrayCollection();
          HashMap hm = new HashMap();
          hm.put("start_date", "2015-01-01");
          ac.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("standards");
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr1);
          
          boolean map = informationPublishService.updateGantt(ac);
          Assert.assertEquals(false, map);
    }
    

    @Test
    public void testDeleteCommondata() {
          List testArr1 = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("uuid", "123");
          testArr1.add(hm);
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr1);
          
          long map = informationPublishService.deleteCommondata("commondatauuid","true");
          Assert.assertEquals(0, map);
    }
}
