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

import com.vgc.databank.model.ExcelModel;
import com.vgc.databank.model.ExcelTitle;
import com.vgc.databank.service.DataInfoService;
import com.vgc.databank.util.FuncUtil;
/**
 * @author BULL
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class DataInfoServiceTest {

    DataInfoService dataInfoService ;
    AutoService autoService ;
    FuncUtil  funcUtil;
	
	CategoryUtil categoryUtil;
	@Before
	public void setUp() throws Exception {
	    dataInfoService = PowerMockito.spy(new DataInfoService());
	    autoService = PowerMockito.mock(AutoService.class);
	    funcUtil  = PowerMockito.mock(FuncUtil.class);
		Whitebox.setInternalState(dataInfoService, "autoService", autoService);
		Whitebox.setInternalState(dataInfoService, "funcUtil", funcUtil);
	}

	
	@Test
	public void testAddField() {
    	  List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("func_uuid", "");
          testArr.add(hm);
	      List map = dataInfoService.addField(testArr);
	      Assert.assertEquals(4, map.size());
	}
	
	@Test
    public void testListDataParseNo() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("func_uuid", "");
          testArr.add(hm);
          List map = dataInfoService.listDataParseNo(testArr);
          Assert.assertEquals(1, map.size());
    }
	
	@Test
    public void testListTitleParseNo() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("field_name", "gorvernor_code_uuid");
          testArr.add(hm);
          List map = dataInfoService.listTitleParseNo(testArr,"02",2);
          Assert.assertEquals(3, map.size());
    }
	
	@Test
    public void testGetFieldTitle() {
    	  List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("uuid", java.util.UUID.randomUUID());
          hm.put("func_uuid", hm.get("func_uuid"));
          hm.put("field_name", "func_uuid");
          testArr.add(hm);
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
          List map = dataInfoService.getFieldTitle("main_func_UUID","func_UUID","user_UUID",true,1);
          Assert.assertEquals(4, map.size());
    }
	
	@Test
    public void testGetFieldTitleInfo() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("uuid", java.util.UUID.randomUUID());
          hm.put("func_uuid", hm.get("func_uuid"));
          hm.put("field_name", "func_uuid");
          testArr.add(hm);
          ExcelTitle et = new ExcelTitle();
          et.setHoldColumnNum(1);
          et.setTitleDesc("titleDesc");
          et.setTitleKey("titleKey");
          et.getHoldColumnNum();
          et.getTitleDesc();
          et.getTitleKey();
          Mockito.when(autoService.findByHqlCache(Mockito.isA(String.class))).thenReturn(testArr);
          List map = dataInfoService.getFieldTitleInfo("main_func_UUID","func_UUID","user_UUID",true,1);
          Assert.assertEquals(4, map.size());
    }
	
	@Test
    public void testGetFieldName() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("uuid", java.util.UUID.randomUUID());
          hm.put("func_uuid", hm.get("func_uuid"));
          hm.put("field_name", "func_uuid");
          testArr.add(hm);
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
          
          String[] map = dataInfoService.getFieldName("main_func_UUID","func_UUID","user_UUID",true,1);
          Assert.assertEquals(4, map.length);
    }

    @Test
    public void testGetFieldData() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("uuid", java.util.UUID.randomUUID());
          hm.put("func_uuid", hm.get("func_uuid"));
          hm.put("field_name", "func_uuid");
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("");
          
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr).thenReturn(testArr1);
          Mockito.when(funcUtil.queryChildFuncsByFuncId(Mockito.isA(String.class),Mockito.isA(String.class))).thenReturn(testArr);
          
          List map = dataInfoService.getFieldData("main_func_UUID","func_UUID","user_UUID",1,1,true,1);
          Assert.assertEquals(0, map.size());
    }
	
	@Test
    public void testGetFieldDataTotalRows() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("uuid", java.util.UUID.randomUUID());
          hm.put("func_uuid", hm.get("func_uuid"));
          hm.put("field_name", "func_uuid");
          hm.put("totalRows", 4L);
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("");
          
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr1).thenReturn(testArr);
          Mockito.when(funcUtil.queryChildFuncsByFuncId(Mockito.isA(String.class),Mockito.isA(String.class))).thenReturn(testArr);
          
          long map = dataInfoService.getFieldDataTotalRows("main_func_UUID","func_UUID","user_UUID",true,1);
          Assert.assertEquals(4, map);
    }
	
	@Test
    public void testGetSingleDataTitle() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("uuid", java.util.UUID.randomUUID());
          hm.put("func_uuid", hm.get("func_uuid"));
          hm.put("field_name", "func_uuid");
          hm.put("totalRows", 4L);
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("");
          
          Mockito.when(autoService.findByHqlCache(Mockito.isA(String.class))).thenReturn(testArr);
          
          List map = dataInfoService.getSingleDataTitle("02","func_UUID","user_UUID",true,1,"uuid");
          Assert.assertEquals(29, map.size());
    }
	
	 @Test
    public void testGetSingleData() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("uuid", java.util.UUID.randomUUID());
          hm.put("func_uuid", hm.get("func_uuid"));
          hm.put("field_name", "func_uuid");
          hm.put("funcuuid", "funcuuid");
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("");
          
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr).thenReturn(testArr1).thenReturn(testArr);
          Mockito.when(funcUtil.queryChildFuncsByFuncId(Mockito.isA(String.class),Mockito.isA(String.class))).thenReturn(testArr);
          
          List map = dataInfoService.getSingleData("main_func_UUID","func_UUID","user_UUID",true,1,"01");
          Assert.assertEquals(4, map.size());
    }
	 
	 @Test
	    public void testGetSubFunc_UUID() {
	          List testArr = new ArrayList<HashMap>();
	          HashMap hm = new HashMap();
	          hm.put("uuid", java.util.UUID.randomUUID());
	          hm.put("func_uuid", hm.get("func_uuid"));
	          hm.put("field_name", "func_uuid");
	          hm.put("funcuuid", "funcuuid");
	          testArr.add(hm);
	          List testArr1 = new ArrayList<HashMap>();
	          testArr1.add("");
	          ExcelModel em = new ExcelModel();
	          em.getColumnSize();
	          em.getDataSummaryTitle();
	          em.getDefaultFont();
	          em.getSelectedItemTitle();
	          em.getSheetName();
	          em.getSheetTitle();
	          em.getSumColumnNum();
	          em.getTitles();
	          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr1);
	          Mockito.when(funcUtil.queryChildFuncsByFuncId(Mockito.isA(String.class),Mockito.isA(String.class))).thenReturn(testArr);
	          
	          String[] map = dataInfoService.getSubFunc_UUID("main_func_UUID","user_UUID");
	          Assert.assertEquals(1, map.length);
	    }
	
}
