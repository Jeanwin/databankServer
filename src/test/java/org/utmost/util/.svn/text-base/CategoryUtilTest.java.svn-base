package test.java.org.utmost.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.utmost.common.DBSupport;
import org.utmost.common.SpringContext;
import org.utmost.util.CategoryUtil;
@RunWith(PowerMockRunner.class)
@SuppressWarnings({"rawtypes","unchecked"})
public class CategoryUtilTest {
	CategoryUtil categoryUtil ;
	DBSupport DBSupport;
	Session session;
	Query query;
	@Before
	public void setUp(){
		categoryUtil = PowerMockito.spy(new CategoryUtil());
		DBSupport = PowerMockito.mock(DBSupport.class);
		session = PowerMockito.mock(Session.class);
		query = PowerMockito.mock(Query.class);
	}
	
	@Test
	@PrepareForTest(SpringContext.class)
	public void testGetForumCategoryList() throws Exception{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uuid","1111");
		map.put("pid","2222");
		list.add(map);
        PowerMockito.doReturn(null).when(DBSupport, "findByHql","",false);
        PowerMockito.doReturn(DBSupport).when(categoryUtil, "getDb");
        List<Map<String, Object>> result = categoryUtil.getForumCategoryList();

		Assert.assertNotNull(result);
	}
	
	
	@Test
	@PrepareForTest(SpringContext.class)
	public void testSaveOrder() throws Exception{
		List<Map> orderList = new ArrayList<Map>();
		Map map = new HashMap();
		map.put("uuid", "123456");
		map.put("children", null);
		orderList.add(map);
		
		PowerMockito.mockStatic(SpringContext.class);
		PowerMockito.when(SpringContext.class, "getBean","CategoryUtil").thenReturn(categoryUtil);
		PowerMockito.when(categoryUtil.getDb()).thenReturn(DBSupport);
		PowerMockito.when(DBSupport.getDynamicSession()).thenReturn(session);
		PowerMockito.when(session.createQuery(Mockito.isA(String.class))).thenReturn(query);
		categoryUtil.saveOrder(orderList);
	}
	
	@Test
	@PrepareForTest(SpringContext.class)
	public void testSaveOrUpdateCategory() throws Exception{
		Map map = new HashMap();
		map.put("uuid", "123456");
		map.put("children", null);
		
		PowerMockito.mockStatic(SpringContext.class);
		PowerMockito.when(SpringContext.class, "getBean","CategoryUtil").thenReturn(categoryUtil);
		PowerMockito.when(categoryUtil.getDb()).thenReturn(DBSupport);
		PowerMockito.when(DBSupport.getDynamicSession()).thenReturn(session);
		PowerMockito.when(session.createQuery(Mockito.isA(String.class))).thenReturn(query);
		String str = categoryUtil.saveOrUpdateCategory(map);
		Assert.assertEquals("123456", str);
	}
	
	@Test
	@PrepareForTest(SpringContext.class)
	public void testGetSelfAndParentName() throws Exception{
		Map map = new HashMap();
		map.put("pid", "456");
		PowerMockito.mockStatic(SpringContext.class);
		PowerMockito.when(SpringContext.class, "getBean","CategoryUtil").thenReturn(categoryUtil);
		PowerMockito.when(categoryUtil.getDb()).thenReturn(DBSupport);
		PowerMockito.when(DBSupport.getDynamicSession()).thenReturn(session);
		PowerMockito.when(session.createQuery(Mockito.isA(String.class))).thenReturn(query);
		PowerMockito.when(query.uniqueResult()).thenReturn(map);

		Map result = categoryUtil.getSelfAndParentName("123");
		Assert.assertEquals("456", ((Map)result.get("parent")).get("pid").toString());
	}
	
	@Test
	@PrepareForTest(SpringContext.class)
	public void testDeleteCategory() throws Exception{
		PowerMockito.mockStatic(SpringContext.class);
		PowerMockito.when(SpringContext.class, "getBean","CategoryUtil").thenReturn(categoryUtil);
		PowerMockito.when(categoryUtil.getDb()).thenReturn(DBSupport);
		PowerMockito.when(DBSupport.getDynamicSession()).thenReturn(session);
		PowerMockito.when(session.createQuery(Mockito.isA(String.class))).thenReturn(query);
		categoryUtil.deleteCategory("123","456");
	}
	

}
