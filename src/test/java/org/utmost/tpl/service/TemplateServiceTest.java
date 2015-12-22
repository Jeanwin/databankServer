package test.java.org.utmost.tpl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.utmost.common.DBSupport;
import org.utmost.common.SpringContext;
import org.utmost.tpl.service.TemplateService;
@SuppressWarnings("rawtypes")
@RunWith(PowerMockRunner.class)
public class TemplateServiceTest {
	TemplateService templateService ;
	DBSupport DBSupport;
	@Before
	public void setUp(){
		templateService = PowerMockito.spy(new TemplateService());
		DBSupport = PowerMockito.mock(DBSupport.class);
	}
	
	@Test
	public void testFindAll() throws Exception{
		PowerMockito.doReturn(DBSupport).when(templateService, "getDb");
        PowerMockito.doReturn(null).when(DBSupport, "findByHql","",false);
        List result = templateService.findAll();
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testFindByUUID() throws Exception{
		PowerMockito.doReturn(DBSupport).when(templateService, "getDb");
        PowerMockito.doReturn(null).when(DBSupport, "findByHql","",false);
        List result = templateService.findByUUID("");
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testFindByPID() throws Exception{
		PowerMockito.doReturn(DBSupport).when(templateService, "getDb");
        PowerMockito.doReturn(null).when(DBSupport, "findByHql","",false);
        List result = templateService.findByPID("");
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testProcessTreeForData() throws Exception{
		PowerMockito.doReturn(DBSupport).when(templateService, "getDb");
        PowerMockito.doReturn(null).when(DBSupport, "findByHql","",false);
        String str = templateService.processTreeForData();
		Assert.assertNotNull(str);
	}
	
	@Test
	public void testProcessTreeForView() throws Exception{
		PowerMockito.doReturn(DBSupport).when(templateService, "getDb");
        PowerMockito.doReturn(null).when(DBSupport, "findByHql","",false);
        String str = templateService.processTreeForView();
		Assert.assertNotNull(str);
	}
	
	@Test
	public void testDeleteByUUID() throws Exception{
		PowerMockito.doReturn(DBSupport).when(templateService, "getDb");
        PowerMockito.doReturn(null).when(DBSupport, "findByHql","",false);
        templateService.deleteByUUID("");
	}
	
	@Test
	@PrepareForTest({SpringContext.class,TemplateService.class})
	public void testFindTplDataByUUID() throws Exception{
		PowerMockito.mockStatic(SpringContext.class);
        PowerMockito.doReturn(DBSupport).when(templateService, "getDb");
        PowerMockito.doReturn(null).when(DBSupport, "findByHql","",false);
        
        templateService.findTplDataByUUID("123");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@PrepareForTest({SpringContext.class,TemplateService.class})
	public void testProcessAllHbm() throws Exception{
		List<HashMap> list = new ArrayList<HashMap>();
		HashMap map = new HashMap();
		map.put("uuid","123");
		list.add(map);
		
		PowerMockito.mockStatic(SpringContext.class);
        PowerMockito.when(templateService.getDb()).thenReturn(DBSupport);
        PowerMockito.when(DBSupport.findByHql("from U_TPL_TEMPLATE v where v.nodetype='Collection'",false)).thenReturn(list);
        //PowerMockito.doReturn(list).when(DBSupport, "findByHql","from U_TPL_TEMPLATE v where v.nodetype='Collection'",false);
        
        templateService.processAllHbm();
	}
}
