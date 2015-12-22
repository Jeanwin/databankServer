package test.java.org.utmost.portal.service;

import static org.easymock.EasyMock.verify;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.utmost.common.DBSupport;
import org.utmost.common.SpringContext;
import org.utmost.portal.service.ForumService;
import org.utmost.util.CategoryUtil;
import org.utmost.util.ClassUtil;


@RunWith(PowerMockRunner.class)     
@PrepareForTest({SpringContext.class,DBSupport.class,ClassUtil.class}) 
public class ForumServicePowerMockTest extends TestCase {
	
    ForumService forumService ;
    DBSupport dBSupport;
    CategoryUtil categoryUtil;

    @Before
    @PrepareForTest({SpringContext.class,DBSupport.class})  
    public void constructData(){
        forumService = new ForumService();
        categoryUtil = PowerMock.createMock(CategoryUtil.class);
        Whitebox.setInternalState(forumService, "categoryUtil", categoryUtil);
    }
    
    @Test
    public void testTransformType() throws Exception{
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        Map innerMap = new HashMap();
        List innerList = new ArrayList();
        Map innerMap1 = new HashMap();
        innerMap1.put("uuid", "uuid");
        innerMap1.put("create_date", new Date());
        innerMap1.put("children", innerList);
        innerList.add(innerMap1);
        innerMap.put("children", innerList);
        retList.add(innerMap);
        EasyMock.expect(categoryUtil.getForumCategoryList()).andReturn(retList);
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            Session session = PowerMock.createMock(Session.class);
            Query query = PowerMock.createMock(Query.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getDynamicSession()).andReturn(session).anyTimes();
            EasyMock.expect(session.createQuery(EasyMock.anyObject(String.class))).andReturn(query).anyTimes();
            EasyMock.expect(query.setString(EasyMock.anyObject(String.class), EasyMock.anyObject(String.class))).andReturn(null).anyTimes();
            EasyMock.expect(query.uniqueResult()).andReturn(1L).anyTimes();
            EasyMock.expect(query.setFirstResult(EasyMock.anyInt())).andReturn(query).anyTimes();
            EasyMock.expect(query.setMaxResults(EasyMock.anyInt())).andReturn(query).anyTimes();
            EasyMock.expect(query.list()).andReturn(innerList).anyTimes();
            
          PowerMock.replayAll();
         forumService.getForumCategoryListWithStatistic();
        verify();
    }

    @Test
    public void testGetTopicList() throws Exception{
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        Map innerMap = new HashMap();
        List innerList = new ArrayList();
        Map innerMap1 = new HashMap();
        innerMap1.put("uuid", "uuid");
        innerMap1.put("create_date", new Date());
        innerMap1.put("children", innerList);
        innerList.add(innerMap1);
        innerMap.put("children", innerList);
        retList.add(innerMap);
        EasyMock.expect(categoryUtil.getForumCategoryList()).andReturn(retList);
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            Session session = PowerMock.createMock(Session.class);
            Query query = PowerMock.createMock(Query.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getDynamicSession()).andReturn(session).anyTimes();
            EasyMock.expect(session.createQuery(EasyMock.anyObject(String.class))).andReturn(query).anyTimes();
            EasyMock.expect(query.setString(EasyMock.anyObject(String.class), EasyMock.anyObject(String.class))).andReturn(null).anyTimes();
            EasyMock.expect(query.uniqueResult()).andReturn(1L).anyTimes();
            EasyMock.expect(query.setFirstResult(EasyMock.anyInt())).andReturn(query).anyTimes();
            EasyMock.expect(query.setMaxResults(EasyMock.anyInt())).andReturn(query).anyTimes();
            EasyMock.expect(query.list()).andReturn(innerList).anyTimes();
            
          PowerMock.replayAll();
         forumService.getTopicList("categoryUuid",1,1);
        verify();
    }

    @Test
    public void testFindTopicListByKewords() throws Exception{
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        Map innerMap = new HashMap();
        List innerList = new ArrayList();
        Map innerMap1 = new HashMap();
        innerMap1.put("uuid", "uuid");
        innerMap1.put("create_date", new Date());
        innerMap1.put("children", innerList);
        innerList.add(innerMap1);
        innerMap.put("children", innerList);
        retList.add(innerMap);
        EasyMock.expect(categoryUtil.getForumCategoryList()).andReturn(retList);
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            Session session = PowerMock.createMock(Session.class);
            Query query = PowerMock.createMock(Query.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getDynamicSession()).andReturn(session).anyTimes();
            EasyMock.expect(session.createQuery(EasyMock.anyObject(String.class))).andReturn(query).anyTimes();
            EasyMock.expect(query.setString(EasyMock.anyObject(String.class), EasyMock.anyObject(String.class))).andReturn(null).anyTimes();
            EasyMock.expect(query.uniqueResult()).andReturn(1L).anyTimes();
            EasyMock.expect(query.setFirstResult(EasyMock.anyInt())).andReturn(query).anyTimes();
            EasyMock.expect(query.setMaxResults(EasyMock.anyInt())).andReturn(query).anyTimes();
            EasyMock.expect(query.list()).andReturn(innerList).anyTimes();
            
          PowerMock.replayAll();
         forumService.findTopicListByKewords("categoryUuid",1,1);
        verify();
    }

    @Test
    public void testGetTopicDetailByTopicUuid() throws Exception{
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        Map innerMap = new HashMap();
        List innerList = new ArrayList();
        Map innerMap1 = new HashMap();
        innerMap1.put("uuid", "uuid");
        innerMap1.put("create_date", new Date());
        innerMap1.put("children", innerList);
        innerList.add(innerMap1);
        innerMap.put("children", innerList);
        retList.add(innerMap);
        EasyMock.expect(categoryUtil.getForumCategoryList()).andReturn(retList);
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            Session session = PowerMock.createMock(Session.class);
            Query query = PowerMock.createMock(Query.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getDynamicSession()).andReturn(session).anyTimes();
            EasyMock.expect(session.createQuery(EasyMock.anyObject(String.class))).andReturn(query).anyTimes();
            EasyMock.expect(query.setString(EasyMock.anyObject(String.class), EasyMock.anyObject(String.class))).andReturn(null).anyTimes();
            EasyMock.expect(query.uniqueResult()).andReturn(innerMap1);
            EasyMock.expect(query.uniqueResult()).andReturn(1L).times(3);
            EasyMock.expect(query.setFirstResult(EasyMock.anyInt())).andReturn(query).anyTimes();
            EasyMock.expect(query.setMaxResults(EasyMock.anyInt())).andReturn(query).anyTimes();
            EasyMock.expect(query.list()).andReturn(innerList).anyTimes();
            EasyMock.expect(categoryUtil.getSelfAndParentName(EasyMock.anyObject(String.class))).andReturn(innerMap1);
            
          PowerMock.replayAll();
         forumService.getTopicDetailByTopicUuid("categoryUuid");
        verify();
    }

    @Test
    public void testGetReplyListByTopicUuid() throws Exception{
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        Map innerMap = new HashMap();
        List innerList = new ArrayList();
        Map innerMap1 = new HashMap();
        innerMap1.put("uuid", "uuid");
        innerMap1.put("create_date", new Date());
        innerMap1.put("children", innerList);
        innerList.add(innerMap1);
        innerMap.put("children", innerList);
        retList.add(innerMap);
        EasyMock.expect(categoryUtil.getForumCategoryList()).andReturn(retList);
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            Session session = PowerMock.createMock(Session.class);
            Query query = PowerMock.createMock(Query.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getDynamicSession()).andReturn(session).anyTimes();
            EasyMock.expect(session.createQuery(EasyMock.anyObject(String.class))).andReturn(query).anyTimes();
            EasyMock.expect(query.setString(EasyMock.anyObject(String.class), EasyMock.anyObject(String.class))).andReturn(null).anyTimes();
            EasyMock.expect(query.uniqueResult()).andReturn(1L);
            EasyMock.expect(query.setFirstResult(EasyMock.anyInt())).andReturn(query).anyTimes();
            EasyMock.expect(query.setMaxResults(EasyMock.anyInt())).andReturn(query).anyTimes();
            EasyMock.expect(query.list()).andReturn(innerList).anyTimes();
            EasyMock.expect(categoryUtil.getSelfAndParentName(EasyMock.anyObject(String.class))).andReturn(innerMap1);
            
          PowerMock.replayAll();
         forumService.getReplyListByTopicUuid("categoryUuid");
        verify();
    }
}
