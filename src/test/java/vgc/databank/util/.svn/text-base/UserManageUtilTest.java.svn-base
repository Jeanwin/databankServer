package test.java.vgc.databank.util;
/**
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import org.utmost.portal.service.AutoService;

import com.vgc.databank.service.RightService;
import com.vgc.databank.util.UserManageUtil;
/**
 * @author BULL
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class UserManageUtilTest {

	UserManageUtil userManageUtil ;
	AutoService autoService;
	RightService rightService;
	@Before
	public void setUp() throws Exception {
		userManageUtil = PowerMockito.spy(new UserManageUtil());
		autoService = PowerMockito.mock(AutoService.class);
		rightService = PowerMockito.mock(RightService.class);
		
		Whitebox.setInternalState(userManageUtil, "autoService", autoService);
		Whitebox.setInternalState(userManageUtil, "rightService", rightService);
	}
	
	@Test
	public void testAddPortalUser(){
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("loginname", "1");
		map.put("password", "21234333");
		
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);

		long result = userManageUtil.addPortalUser(map);
		Assert.assertEquals(0, result);
	}
	
	@Test
	public void testaddPortalUser(){
		List list = new ArrayList();
		List refOrg = new ArrayList();
		HashMap map = new HashMap();
		map.put("loginname", "1");
		map.put("password", "21234333");
		refOrg.add(map);
		
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);
		PowerMockito.when(autoService.save("U_PORTAL_USER", map)).thenReturn("123");

		long result = userManageUtil.addPortalUser(map,refOrg);
		Assert.assertEquals(0, result);
	}
	
	@Test
	public void testUpdatePortalUser(){
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("passwordChange", "true");
		map.put("password", "21234333");
		
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);

		long result = userManageUtil.updatePortalUser(map);
		Assert.assertEquals(0, result);
	}
	
	@Test
	public void testupdatePortalUser(){
		List list = new ArrayList();
		List orgList = new ArrayList();
		List orgNowList = new ArrayList();
		HashMap map = new HashMap();
		map.put("passwordChange", "true");
		map.put("password", "21234333");
		map.put("uuid", "21234333");
		orgList.add(map);
		orgNowList.add(map);
		
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);

		long result = userManageUtil.updatePortalUser(map,orgList,orgNowList);
		Assert.assertEquals(0, result);
	}
	
	@Test
	public void testDeletePortalUser(){
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("passwordChange", "true");
		map.put("password", "21234333");
		map.put("uuid", "21234333");
		list.add(map);
		
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);

		long result = userManageUtil.deletePortalUser("123");
		Assert.assertEquals(0, result);
	}
	
	@Test
	public void testUpdateUserPassword(){
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("passwordChange", "true");
		map.put("password", DigestUtils.md5Hex("321").toString());
		map.put("uuid", "21234333");
		list.add(map);
		
		PowerMockito.when(autoService.findByUUID("U_PORTAL_USER",
				"123")).thenReturn(map);

		long result = userManageUtil.updateUserPassword("123","321","1234567");
		Assert.assertEquals(0, result);
	}
	
	@Test
	public void testQueryUsers(){
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("passwordChange", "true");
		map.put("password", DigestUtils.md5Hex("321").toString());
		map.put("uuid", "21234333");
		map.put("useruuid", "21234333");
		map.put("orguuid", "21234333");
		map.put("state", "0");
		map.put("orgname", "test");
		list.add(map);
		
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);
		PowerMockito.when(autoService.findAll("U_PORTAL_USER")).thenReturn(list);

		List result = userManageUtil.queryUsers();
		Assert.assertEquals("21234333", ((HashMap)result.get(0)).get("orguuid"));
	}
	
	@Test
	public void testQueryUserRoles(){
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("useruuid", "1234");
		map.put("password", DigestUtils.md5Hex("321").toString());
		map.put("uuid", "21234333");
		list.add(map);
		
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);

		List result = userManageUtil.queryUserRoles();
		Assert.assertEquals("21234333", ((HashMap)result.get(0)).get("uuid"));
	}
	
	@Test
	public void testqueryUserRoles(){
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("useruuid", "1234");
		map.put("password", DigestUtils.md5Hex("321").toString());
		map.put("rolename", "test");
		map.put("uuid", "21234333");
		list.add(map);
		
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);

		List result = userManageUtil.queryUserRoles("123");
		Assert.assertEquals("21234333", ((HashMap)result.get(0)).get("uuid"));
	}
	
	@Test
	public void testQueryUsersByName(){
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("useruuid", "1234");
		map.put("password", DigestUtils.md5Hex("321").toString());
		map.put("uuid", "21234333");
		list.add(map);
		
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);

		List result = userManageUtil.queryUsersByName("123");
		Assert.assertEquals("21234333", ((HashMap)result.get(0)).get("uuid"));
	}
	
}
