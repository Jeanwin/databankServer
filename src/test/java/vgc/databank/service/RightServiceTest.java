package test.java.vgc.databank.service;
/**
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import org.utmost.portal.service.AutoService;

import com.vgc.databank.service.PortalFuncService;
import com.vgc.databank.service.RightService;
import com.vgc.databank.util.FileUtil;
import com.vgc.databank.util.UserManageUtil;
/**
 * @author BULL
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class RightServiceTest {
	
	private static Logger logger = Logger.getLogger(RightServiceTest.class);


	RightService rightService ;
	
	UserManageUtil userManageUtil;
	
	PortalFuncService funcService;
	
	AutoService autoService;
	@Before
	public void setUp() throws Exception {
		rightService = PowerMockito.spy(new RightService());
		userManageUtil = PowerMockito.mock(UserManageUtil.class);
		funcService = PowerMockito.mock(PortalFuncService.class);
		autoService = PowerMockito.mock(AutoService.class);
		
		Whitebox.setInternalState(rightService, "userManageUtil", userManageUtil);
		Whitebox.setInternalState(rightService, "funcService", funcService);
		Whitebox.setInternalState(rightService, "autoService", autoService);

	}

	@Test
	public void testSavePortalRole() {
		Mockito.when(autoService.saveOrUpdate(Mockito.isA(String.class),Mockito.isA(HashMap.class))).thenReturn("");
		long result = rightService.savePortalRole(new HashMap());
		if(logger.isDebugEnabled()){
		   logger.debug("sers are returned");
		}
		Assert.assertEquals(0x0000L, result);
	}
	
	@Test
	public void testDeletePortalRoleByIde() {
		Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(new ArrayList<HashMap>());

		long result = rightService.deletePortalRoleById("");
		
		Assert.assertEquals(0x0000L, result);
	}
	
	@Test
	public void testLogin() {
		ArrayList<HashMap> map = new ArrayList<HashMap>();
		map.add(new HashMap());
		Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(map);

		rightService.login("11","");
		
	}

	@Test
	public void testQueryRoleOperFuncs() {
		Query query = PowerMockito.mock(Query.class);
		Mockito.when(query.uniqueResult()).thenReturn(Long.parseLong("20"));
		
		String result = rightService.queryRoleOperFuncs(Mockito.isA(String.class),Mockito.isA(String.class));
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testQueryPortalRoles() {
		ArrayList<HashMap> map = new ArrayList<HashMap>();
		map.add(new HashMap());
		Mockito.when(autoService.findAll(Mockito.isA(String.class))).thenReturn(map);

		rightService.queryPortalRoles();
		
	}
	
	@Test
	public void testQueryUserRight() {
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		HashMap map = new HashMap();
		map.put("uuid", "1005");
		list.add(map);
		Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);

		Map result = rightService.queryUserRight("");
		Assert.assertNotNull(result);
		
	}
	
	@Test
	public void testSavePortalRefRoleUser() {
		HashMap<String, List> map = new HashMap();

		long result = rightService.savePortalRefRoleUser(map);
		Assert.assertEquals(0x0000L, result);
		
	}
	

}
