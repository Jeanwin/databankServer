package test.java.vgc.databank.util;
/**
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.utmost.portal.service.AutoService;

import com.vgc.databank.service.PortalFuncService;
import com.vgc.databank.util.FuncUtil;
/**
 * @author BULL
 *
 */
@RunWith(PowerMockRunner.class)
@SuppressWarnings({"rawtypes","unchecked"})
public class FuncUtilTest {

	FuncUtil funcUtil ;
	AutoService autoService;
	PortalFuncService funcService;
	@Before
	public void setUp() throws Exception {
		funcUtil = PowerMockito.spy(new FuncUtil());
		autoService = PowerMockito.mock(AutoService.class);
		funcService = PowerMockito.mock(PortalFuncService.class);
		
		Whitebox.setInternalState(funcUtil, "autoService", autoService);
		Whitebox.setInternalState(funcUtil, "funcService", funcService);
	}
	
	
	@Test
	public void testQueryFuncByUser(){
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("funcuuid", "1");
		list.add(map);
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);
		
		List result = funcUtil.queryFuncByUser("01","test");
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testqueryFuncByUser(){
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("funcuuid", "1");
		list.add(map);
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);
		
		List result = funcUtil.queryFuncByUser("01","test","1");
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testQueryAllFuncs(){
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("funcuuid", "1");
		list.add(map);
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);
		
		List result = funcUtil.queryAllFuncs("test");
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testqueryAllFuncs(){
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("funcuuid", "1");
		list.add(map);
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);
		
		List result = funcUtil.queryAllFuncs();
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testQueryFuncsByFuncId(){
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("funcuuid", "1");
		list.add(map);
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(null);
		List result = funcUtil.queryFuncsByFuncId("01","test");
		Assert.assertNull(result);
	}
	
	@Test
	public void testQueryChildFuncsByFuncId(){
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("funcuuid", "1");
		map.put("nodetype", "3");
		list.add(map);
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);
		
		List result = funcUtil.queryChildFuncsByFuncId("01","test");
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testQueryFuncByUserOperation(){
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("funcuuid", "1");
		map.put("nodetype", "3");
		list.add(map);
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);
		
		List result = funcUtil.queryFuncByUserOperation("01","test","tt");
		Assert.assertNotNull(result);
	}
	
}
