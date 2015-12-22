package test.java.org.utmost.portal.service;

import static org.easymock.EasyMock.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import org.utmost.common.SpringContext;
import org.utmost.portal.service.AutoService;
import org.utmost.portal.service.TreeService;
import org.utmost.util.CategoryUtil;


@RunWith(PowerMockRunner.class)     
@PrepareForTest({SpringContext.class}) 
public class TreeServicePowerMockTest extends TestCase {
	
    TreeService treeService ;
    AutoService autoService;
    CategoryUtil categoryUtil;

    @Before
    public void constructData(){
        treeService = new TreeService();
        autoService = PowerMock.createMock(AutoService.class);
    }
    
    @Test
    public void testTransformType() throws Exception{
        HashMap innerMap1 = new HashMap();
        innerMap1.put("uuid", "uuid");
        innerMap1.put("rootField", "rootField");
        innerMap1.put("rootValue", "rootField");
        List list = new ArrayList();
        list.add(innerMap1);
            Session session = PowerMock.createMock(Session.class);
            Query query = PowerMock.createMock(Query.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(autoService);
            EasyMock.expect(autoService.findByHql(EasyMock.anyObject(String.class))).andReturn(list);
            
          PowerMock.replayAll();
         treeService.getTree(innerMap1,new HashMap());
        verify();
    }

    @Test
    public void testDeleteTree() throws Exception{
        HashMap innerMap1 = new HashMap();
        List list = new ArrayList();
            Session session = PowerMock.createMock(Session.class);
            Query query = PowerMock.createMock(Query.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(autoService).anyTimes();
            EasyMock.expect(autoService.findByHql(EasyMock.anyObject(String.class))).andReturn(list).anyTimes();
            autoService.deleteAll(EasyMock.anyObject(String.class),EasyMock.anyObject(List.class));
            
          PowerMock.replayAll();
         treeService.deleteTree(innerMap1);
        verify();
    }

    @Test
    public void testDeleteTreeNode() throws Exception{
        HashMap innerMap1 = new HashMap();
        List list = new ArrayList();
            Session session = PowerMock.createMock(Session.class);
            Query query = PowerMock.createMock(Query.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(autoService).anyTimes();
            EasyMock.expect(autoService.findByHql(EasyMock.anyObject(String.class))).andReturn(list).anyTimes();
            autoService.deleteAll(EasyMock.anyObject(String.class),EasyMock.anyObject(List.class));
            
          PowerMock.replayAll();
         treeService.deleteTreeNode(innerMap1,"");
        verify();
    }
}
