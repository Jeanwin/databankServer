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

import com.vgc.databank.service.DataInfoService;
import com.vgc.databank.service.SearchService;
import com.vgc.databank.util.FuncUtil;
/**
 * @author BULL
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class SearchServiceTest {

    SearchService searchService ;
    AutoService autoService ;
    DataInfoService dataInfoService;
    FuncUtil  funcUtil;
	
	CategoryUtil categoryUtil;
	@Before
	public void setUp() throws Exception {
	    searchService = PowerMockito.spy(new SearchService());
	    autoService = PowerMockito.mock(AutoService.class);
	    dataInfoService = PowerMockito.mock(DataInfoService.class);
	    funcUtil  = PowerMockito.mock(FuncUtil.class);
		Whitebox.setInternalState(searchService, "autoService", autoService);
		Whitebox.setInternalState(searchService, "dataInfoService", dataInfoService);
	}

	
    @Test
    public void testSearchByKeyword() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("uuid", java.util.UUID.randomUUID());
          hm.put("func_uuid", hm.get("func_uuid"));
          hm.put("field_name", "func_uuid");
          testArr.add(hm);
          List testArr1 = new ArrayList<HashMap>();
          testArr1.add("");
//          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr).thenReturn(testArr1);
//          Mockito.when(funcUtil.queryChildFuncsByFuncId(Mockito.isA(String.class),Mockito.isA(String.class))).thenReturn(testArr);
          List map = searchService.searchByKeyword(1, 1,"keyword","Or", true,"validNodes");
          Assert.assertEquals(0, map.size());
    }
	
    
	@Test
    public void testSearchByKeywordTotalRows() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("uuid", java.util.UUID.randomUUID());
          hm.put("func_uuid", hm.get("func_uuid"));
          hm.put("totalRows", 100L);
          testArr.add(hm);
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
          long map = searchService.searchByKeywordTotalRows("keyword","Or", true,"validNodes");
          Assert.assertEquals(100, map);
    }
	
	@Test
    public void testAdvancedSearch() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("uuid", java.util.UUID.randomUUID());
          hm.put("func_uuid", hm.get("func_uuid"));
          hm.put("totalRows", 100L);
          testArr.add(hm);
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
          Mockito.when(dataInfoService.getFieldName(Mockito.isA(String.class),Mockito.isA(String.class),Mockito.isA(String.class),Mockito.anyBoolean(),Mockito.anyLong())).thenReturn(new String[]{});
          Mockito.when(dataInfoService.listDataParseNo(Mockito.isA(List.class))).thenReturn(testArr);
          List map = searchService.advancedSearch("node_UUID","main_func_UUID", "hqlParam","user_UUID", 1,1, true,1);
          Assert.assertEquals(0, map.size());
    }
	

    @Test
    public void testAdvancedSearch1() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("uuid", java.util.UUID.randomUUID());
          hm.put("func_uuid", hm.get("func_uuid"));
          hm.put("totalRows", 100L);
          testArr.add(hm);
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
          Mockito.when(dataInfoService.getFieldName(Mockito.isA(String.class),Mockito.isA(String.class),Mockito.isA(String.class),Mockito.anyBoolean(),Mockito.anyLong())).thenReturn(new String[]{});
          Mockito.when(dataInfoService.getSubFunc_UUID(Mockito.isA(String.class),Mockito.isA(String.class))).thenReturn(new String[]{"1"});
          List map = searchService.advancedSearch("node_UUID","02", "hqlParam","user_UUID", true,1);
          Assert.assertEquals(0, map.size());
    }
	

    @Test
    public void testAdvancedSearchTotalRows() {
          List testArr = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("uuid", java.util.UUID.randomUUID());
          hm.put("func_uuid", hm.get("func_uuid"));
          hm.put("totalRows", 100L);
          testArr.add(hm);
          Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
          Mockito.when(dataInfoService.getSubFunc_UUID(Mockito.isA(String.class),Mockito.isA(String.class))).thenReturn(new String[]{"1"});
          long map = searchService.advancedSearchTotalRows("02", "hqlParam","user_UUID", true,1);
          Assert.assertEquals(100, map);
    }
	
}
