package test.java.vgc.databank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import org.utmost.portal.service.AutoService;

import com.vgc.databank.service.BatchDownloadUtil;
@SuppressWarnings({"rawtypes","unchecked"})
public class BatchDownloadUtilTest {
	BatchDownloadUtil batchDownloadUtil ;
	AutoService autoService;
	@Before
	public void setUp(){
		batchDownloadUtil = PowerMockito.spy(new BatchDownloadUtil());
		autoService = PowerMockito.mock(AutoService.class);
		
		Whitebox.setInternalState(batchDownloadUtil, "autoService", autoService);

	}
	
	@Test
	public void testGetTotalPathName() throws Exception{
		List<HashMap> list = new ArrayList<HashMap>();
		HashMap map = new HashMap();
		map.put("funcname","test");
		map.put("funcpid","0");
		list.add(map);
		
		PowerMockito.when(autoService.findByHql("select new map (u.uuid as funcuuid,u.pid as funcpid,u.funcname as funcname) "
				+ "from U_PORTAL_FUNC u where u.uuid='1'")).thenReturn(list);
		String result = batchDownloadUtil.getTotalPathName("1");
		Assert.assertEquals("test/",result);
	}
	
	@Test
	public void testGetTotalCnPathName() throws Exception{
		List<HashMap> list = new ArrayList<HashMap>();
		HashMap map = new HashMap();
		map.put("funccnname","test");
		map.put("funcpid","0");
		list.add(map);
		
		PowerMockito.when(autoService.findByHql("select new map (u.uuid as funcuuid,u.pid as funcpid,u.funccnname as funccnname) "
				+ "from U_PORTAL_FUNC u where u.uuid='1'")).thenReturn(list);
		String result = batchDownloadUtil.getTotalCnPathName("1");
		Assert.assertEquals("test/",result);
	}
	

}
