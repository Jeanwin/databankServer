package test.java.org.utmost.portal.service;

import static org.easymock.EasyMock.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.utmost.common.DBSupport;
import org.utmost.common.SpringContext;
import org.utmost.portal.service.AutoService;
import org.utmost.util.ClassUtil;


@RunWith(PowerMockRunner.class)     
@PrepareForTest({SpringContext.class,DBSupport.class,ClassUtil.class}) 
public class AutoServicePowerMockTest extends TestCase {
	
    AutoService autoService ;
    DBSupport dBSupport;

    @Before
    @PrepareForTest({SpringContext.class,DBSupport.class})  
    public void constructData(){
      
    }
    
    @Test
    public void testTransformType() throws Exception{
            HashMap arrMap = new HashMap();
            arrMap.put("key", "value");
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            SessionFactory sessionFactory = PowerMock.createMock(SessionFactory.class);
            ClassMetadata classMetadata  = PowerMock.createMock(ClassMetadata.class);
            Type type  = PowerMock.createMock(Type.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getSessionFactory()).andReturn(sessionFactory);
            EasyMock.expect(sessionFactory.getClassMetadata(EasyMock.anyObject(String.class))).andReturn(classMetadata);
            EasyMock.expect(classMetadata.getPropertyType(EasyMock.anyObject(String.class))).andReturn(type);
            EasyMock.expect(type.getName()).andReturn("test");
            
          PowerMock.replayAll();
          autoService = new AutoService();
         autoService.transformType("entityName",arrMap);
        verify();
    }
    
    @Test
    public void testSave() throws Exception{
            HashMap arrMap = new HashMap();
            arrMap.put("key", "value");
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            SessionFactory sessionFactory = PowerMock.createMock(SessionFactory.class);
            ClassMetadata classMetadata  = PowerMock.createMock(ClassMetadata.class);
            Type type  = PowerMock.createMock(Type.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getSessionFactory()).andReturn(sessionFactory);
            EasyMock.expect(dbsupport.save(EasyMock.anyObject(String.class), EasyMock.anyObject(HashMap.class))).andReturn("test");
            EasyMock.expect(sessionFactory.getClassMetadata(EasyMock.anyObject(String.class))).andReturn(classMetadata);
            EasyMock.expect(classMetadata.getPropertyType(EasyMock.anyObject(String.class))).andReturn(type);
            EasyMock.expect(type.getName()).andReturn("test");
            
          PowerMock.replayAll();
          autoService = new AutoService();
         autoService.save("entityName",arrMap);
        verify();
    }

    @Test
    public void testSaveAll() throws Exception{
            List reList = new ArrayList();
            HashMap arrMap = new HashMap();
            arrMap.put("key", "value");
            reList.add(arrMap);
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            SessionFactory sessionFactory = PowerMock.createMock(SessionFactory.class);
            ClassMetadata classMetadata  = PowerMock.createMock(ClassMetadata.class);
            Type type  = PowerMock.createMock(Type.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getSessionFactory()).andReturn(sessionFactory);
            EasyMock.expect(dbsupport.save(EasyMock.anyObject(String.class), EasyMock.anyObject(HashMap.class))).andReturn("test");
            EasyMock.expect(sessionFactory.getClassMetadata(EasyMock.anyObject(String.class))).andReturn(classMetadata);
            EasyMock.expect(classMetadata.getPropertyType(EasyMock.anyObject(String.class))).andReturn(type);
            EasyMock.expect(type.getName()).andReturn("test");
            
          PowerMock.replayAll();
          autoService = new AutoService();
         autoService.saveAll("entityName",reList);
        verify();
    }

    @Test
    public void testUpdateAll() throws Exception{
            List reList = new ArrayList();
            HashMap arrMap = new HashMap();
            arrMap.put("key", "value");
            reList.add(arrMap);
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            SessionFactory sessionFactory = PowerMock.createMock(SessionFactory.class);
            ClassMetadata classMetadata  = PowerMock.createMock(ClassMetadata.class);
            Type type  = PowerMock.createMock(Type.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getSessionFactory()).andReturn(sessionFactory);
            dbsupport.update(EasyMock.anyObject(String.class), EasyMock.anyObject(HashMap.class));
            EasyMock.expect(sessionFactory.getClassMetadata(EasyMock.anyObject(String.class))).andReturn(classMetadata);
            EasyMock.expect(classMetadata.getPropertyType(EasyMock.anyObject(String.class))).andReturn(type);
            EasyMock.expect(type.getName()).andReturn("test");
            
          PowerMock.replayAll();
          autoService = new AutoService();
         autoService.updateAll("entityName",reList);
        verify();
    }

    @Test
    public void testUpdate() throws Exception{
            HashMap arrMap = new HashMap();
            arrMap.put("key", "value");
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            SessionFactory sessionFactory = PowerMock.createMock(SessionFactory.class);
            ClassMetadata classMetadata  = PowerMock.createMock(ClassMetadata.class);
            Type type  = PowerMock.createMock(Type.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getSessionFactory()).andReturn(sessionFactory);
            dbsupport.update(EasyMock.anyObject(String.class), EasyMock.anyObject(HashMap.class));
            EasyMock.expect(sessionFactory.getClassMetadata(EasyMock.anyObject(String.class))).andReturn(classMetadata);
            EasyMock.expect(classMetadata.getPropertyType(EasyMock.anyObject(String.class))).andReturn(type);
            EasyMock.expect(type.getName()).andReturn("test");
            
          PowerMock.replayAll();
          autoService = new AutoService();
         autoService.update("entityName",arrMap);
        verify();
    }

    @Test
    public void testSaveOrUpdate() throws Exception{
            HashMap arrMap = new HashMap();
            arrMap.put("key", "value");
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            SessionFactory sessionFactory = PowerMock.createMock(SessionFactory.class);
            ClassMetadata classMetadata  = PowerMock.createMock(ClassMetadata.class);
            Type type  = PowerMock.createMock(Type.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getSessionFactory()).andReturn(sessionFactory).times(2);
            EasyMock.expect(sessionFactory.getClassMetadata(EasyMock.anyObject(String.class))).andReturn(classMetadata).times(2);
            EasyMock.expect(classMetadata.getPropertyType(EasyMock.anyObject(String.class))).andReturn(type).times(2);
            EasyMock.expect(type.getName()).andReturn("test").times(2);
            EasyMock.expect(dbsupport.saveOrUpdate(EasyMock.anyObject(String.class), EasyMock.anyObject(HashMap.class))).andReturn("test");
            
          PowerMock.replayAll();
          autoService = new AutoService();
         autoService.saveOrUpdate("entityName",arrMap);
        verify();
    }

    @Test
    public void testSaveOrUpdateAll() throws Exception{
            List reList = new ArrayList();
            HashMap arrMap = new HashMap();
            arrMap.put("key", "value");
            reList.add(arrMap);
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            SessionFactory sessionFactory = PowerMock.createMock(SessionFactory.class);
            ClassMetadata classMetadata  = PowerMock.createMock(ClassMetadata.class);
            Type type  = PowerMock.createMock(Type.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getSessionFactory()).andReturn(sessionFactory).times(2);
            EasyMock.expect(sessionFactory.getClassMetadata(EasyMock.anyObject(String.class))).andReturn(classMetadata).times(2);
            EasyMock.expect(classMetadata.getPropertyType(EasyMock.anyObject(String.class))).andReturn(type).times(2);
            EasyMock.expect(type.getName()).andReturn("test").times(2);
            EasyMock.expect(dbsupport.saveOrUpdate(EasyMock.anyObject(String.class), EasyMock.anyObject(HashMap.class))).andReturn("test");
            
          PowerMock.replayAll();
          autoService = new AutoService();
         autoService.saveOrUpdateAll("entityName",reList);
        verify();
    }

    @Test
    public void testDelete() throws Exception{
            HashMap arrMap = new HashMap();
            arrMap.put("key", "value");
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            SessionFactory sessionFactory = PowerMock.createMock(SessionFactory.class);
            ClassMetadata classMetadata  = PowerMock.createMock(ClassMetadata.class);
            Type type  = PowerMock.createMock(Type.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getSessionFactory()).andReturn(sessionFactory).times(2);
            EasyMock.expect(sessionFactory.getClassMetadata(EasyMock.anyObject(String.class))).andReturn(classMetadata).times(2);
            EasyMock.expect(classMetadata.getPropertyType(EasyMock.anyObject(String.class))).andReturn(type).times(2);
            EasyMock.expect(type.getName()).andReturn("test").times(2);
            dbsupport.delete(EasyMock.anyObject(String.class), EasyMock.anyObject(HashMap.class));
            
          PowerMock.replayAll();
          autoService = new AutoService();
         autoService.delete("entityName",arrMap);
        verify();
    }

    @Test
    public void testDeleteAll() throws Exception{
            List reList = new ArrayList();
            HashMap arrMap = new HashMap();
            arrMap.put("key", "value");
            reList.add(arrMap);
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            SessionFactory sessionFactory = PowerMock.createMock(SessionFactory.class);
            ClassMetadata classMetadata  = PowerMock.createMock(ClassMetadata.class);
            Type type  = PowerMock.createMock(Type.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getSessionFactory()).andReturn(sessionFactory).times(2);
            EasyMock.expect(sessionFactory.getClassMetadata(EasyMock.anyObject(String.class))).andReturn(classMetadata).times(2);
            EasyMock.expect(classMetadata.getPropertyType(EasyMock.anyObject(String.class))).andReturn(type).times(2);
            EasyMock.expect(type.getName()).andReturn("test").times(2);
            dbsupport.delete(EasyMock.anyObject(String.class), EasyMock.anyObject(HashMap.class));
            
          PowerMock.replayAll();
          autoService = new AutoService();
         autoService.deleteAll("entityName",reList);
        verify();
    }

    @Test
    public void testDeleteByUUID() throws Exception{
            List reList = new ArrayList();
            HashMap arrMap = new HashMap();
            arrMap.put("key", "value");
            reList.add(arrMap);
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            SessionFactory sessionFactory = PowerMock.createMock(SessionFactory.class);
            ClassMetadata classMetadata  = PowerMock.createMock(ClassMetadata.class);
            Type type  = PowerMock.createMock(Type.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getSessionFactory()).andReturn(sessionFactory).times(2);
            EasyMock.expect(sessionFactory.getClassMetadata(EasyMock.anyObject(String.class))).andReturn(classMetadata).times(2);
            EasyMock.expect(classMetadata.getPropertyType(EasyMock.anyObject(String.class))).andReturn(type).times(2);
            EasyMock.expect(type.getName()).andReturn("test").times(2);
            EasyMock.expect(dbsupport.findByHql(EasyMock.anyObject(String.class), EasyMock.anyBoolean())).andReturn(reList);
            dbsupport.deleteAll(EasyMock.anyObject(Collection.class));
          PowerMock.replayAll();
          autoService = new AutoService();
         autoService.deleteByUUID("entityName","value");
        verify();
    }

    @Test
    public void testDeleteByHql() throws Exception{
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            SessionFactory sessionFactory = PowerMock.createMock(SessionFactory.class);
            ClassMetadata classMetadata  = PowerMock.createMock(ClassMetadata.class);
            Type type  = PowerMock.createMock(Type.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getSessionFactory()).andReturn(sessionFactory);
            EasyMock.expect(sessionFactory.getClassMetadata(EasyMock.anyObject(String.class))).andReturn(classMetadata).times(2);
            EasyMock.expect(classMetadata.getPropertyType(EasyMock.anyObject(String.class))).andReturn(type);
            EasyMock.expect(type.getName()).andReturn("test");
            dbsupport.deleteByHql(EasyMock.anyObject(String.class));
          PowerMock.replayAll();
          autoService = new AutoService();
         autoService.deleteByHql("entityName");
        verify();
    }

    @Test
    public void testFindByUUID() throws Exception{
            List reList = new ArrayList();
            HashMap arrMap = new HashMap();
            arrMap.put("key", "value");
            reList.add(arrMap);
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            SessionFactory sessionFactory = PowerMock.createMock(SessionFactory.class);
            ClassMetadata classMetadata  = PowerMock.createMock(ClassMetadata.class);
            Type type  = PowerMock.createMock(Type.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getSessionFactory()).andReturn(sessionFactory);
            EasyMock.expect(sessionFactory.getClassMetadata(EasyMock.anyObject(String.class))).andReturn(classMetadata).times(2);
            EasyMock.expect(classMetadata.getPropertyType(EasyMock.anyObject(String.class))).andReturn(type);
            EasyMock.expect(type.getName()).andReturn("test");
            EasyMock.expect(dbsupport.findByHql(EasyMock.anyObject(String.class), EasyMock.anyBoolean())).andReturn(reList);
            PowerMock.replayAll();
            autoService = new AutoService();
            autoService.findByUUID("entityName","uuid");
            verify();
    }

    @Test
    public void testDeleteByAllUUID() throws Exception{
            ArrayList reList = new ArrayList();
            reList.add("key");
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            SessionFactory sessionFactory = PowerMock.createMock(SessionFactory.class);
            ClassMetadata classMetadata  = PowerMock.createMock(ClassMetadata.class);
            Type type  = PowerMock.createMock(Type.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getSessionFactory()).andReturn(sessionFactory);
            EasyMock.expect(sessionFactory.getClassMetadata(EasyMock.anyObject(String.class))).andReturn(classMetadata).times(2);
            EasyMock.expect(classMetadata.getPropertyType(EasyMock.anyObject(String.class))).andReturn(type);
            EasyMock.expect(type.getName()).andReturn("test");
            dbsupport.deleteAll(EasyMock.anyObject(Collection.class));
            EasyMock.expect(dbsupport.findByHql(EasyMock.anyObject(String.class), EasyMock.anyBoolean())).andReturn(reList);
            PowerMock.replayAll();
            autoService = new AutoService();
            autoService.deleteByAllUUID("entityName",reList);
            verify();
    }

    @Test
    public void testFindByHql() throws Exception{
            ArrayList reList = new ArrayList();
            reList.add("key");
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            SessionFactory sessionFactory = PowerMock.createMock(SessionFactory.class);
            ClassMetadata classMetadata  = PowerMock.createMock(ClassMetadata.class);
            Type type  = PowerMock.createMock(Type.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getSessionFactory()).andReturn(sessionFactory);
            EasyMock.expect(sessionFactory.getClassMetadata(EasyMock.anyObject(String.class))).andReturn(classMetadata).times(2);
            EasyMock.expect(classMetadata.getPropertyType(EasyMock.anyObject(String.class))).andReturn(type);
            EasyMock.expect(type.getName()).andReturn("test");
            dbsupport.deleteAll(EasyMock.anyObject(Collection.class));
            EasyMock.expect(dbsupport.findByHql(EasyMock.anyObject(String.class), EasyMock.anyBoolean())).andReturn(reList);
            PowerMock.replayAll();
            autoService = new AutoService();
            autoService.findByHql("entityName");
            verify();
    }

    @Test
    public void testFindByHqlCache() throws Exception{
            ArrayList reList = new ArrayList();
            reList.add("key");
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            SessionFactory sessionFactory = PowerMock.createMock(SessionFactory.class);
            ClassMetadata classMetadata  = PowerMock.createMock(ClassMetadata.class);
            Type type  = PowerMock.createMock(Type.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getSessionFactory()).andReturn(sessionFactory);
            EasyMock.expect(sessionFactory.getClassMetadata(EasyMock.anyObject(String.class))).andReturn(classMetadata).times(2);
            EasyMock.expect(classMetadata.getPropertyType(EasyMock.anyObject(String.class))).andReturn(type);
            EasyMock.expect(type.getName()).andReturn("test");
            dbsupport.deleteAll(EasyMock.anyObject(Collection.class));
            EasyMock.expect(dbsupport.findByHql(EasyMock.anyObject(String.class), EasyMock.anyBoolean())).andReturn(reList);
            PowerMock.replayAll();
            autoService = new AutoService();
            autoService.findByHqlCache("entityName");
            verify();
    }

    @Test
    public void testFindAll() throws Exception{
            ArrayList reList = new ArrayList();
            reList.add("key");
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            SessionFactory sessionFactory = PowerMock.createMock(SessionFactory.class);
            ClassMetadata classMetadata  = PowerMock.createMock(ClassMetadata.class);
            Type type  = PowerMock.createMock(Type.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getSessionFactory()).andReturn(sessionFactory);
            EasyMock.expect(sessionFactory.getClassMetadata(EasyMock.anyObject(String.class))).andReturn(classMetadata).times(2);
            EasyMock.expect(classMetadata.getPropertyType(EasyMock.anyObject(String.class))).andReturn(type);
            EasyMock.expect(type.getName()).andReturn("test");
            dbsupport.deleteAll(EasyMock.anyObject(Collection.class));
            EasyMock.expect(dbsupport.findAll(EasyMock.anyObject(String.class))).andReturn(reList);
            PowerMock.replayAll();
            autoService = new AutoService();
            autoService.findAll("entityName");
            verify();
    }


    @Test
    public void testPagination() throws Exception{
            ArrayList reList = new ArrayList();
            reList.add("key");
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            SessionFactory sessionFactory = PowerMock.createMock(SessionFactory.class);
            ClassMetadata classMetadata  = PowerMock.createMock(ClassMetadata.class);
            Type type  = PowerMock.createMock(Type.class);
            PowerMock.mockStatic(SpringContext.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getSessionFactory()).andReturn(sessionFactory);
            EasyMock.expect(sessionFactory.getClassMetadata(EasyMock.anyObject(String.class))).andReturn(classMetadata).times(2);
            EasyMock.expect(classMetadata.getPropertyType(EasyMock.anyObject(String.class))).andReturn(type);
            EasyMock.expect(type.getName()).andReturn("test");
            dbsupport.deleteAll(EasyMock.anyObject(Collection.class));
            EasyMock.expect(dbsupport.pagination(EasyMock.anyInt(),EasyMock.anyInt(),EasyMock.anyObject(String.class))).andReturn(reList);
            PowerMock.replayAll();
            autoService = new AutoService();
            autoService.pagination(1,1,"entityName");
            verify();
    }

    @Test
    public void testCallfunc() throws Exception{
            ArrayList reList = new ArrayList();
            reList.add("key");
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            SessionFactory sessionFactory = PowerMock.createMock(SessionFactory.class);
            ClassMetadata classMetadata  = PowerMock.createMock(ClassMetadata.class);
            Type type  = PowerMock.createMock(Type.class);
            PowerMock.mockStatic(SpringContext.class);
            PowerMock.mockStatic(ClassUtil.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getSessionFactory()).andReturn(sessionFactory);
            EasyMock.expect(sessionFactory.getClassMetadata(EasyMock.anyObject(String.class))).andReturn(classMetadata).times(2);
            EasyMock.expect(classMetadata.getPropertyType(EasyMock.anyObject(String.class))).andReturn(type);
            EasyMock.expect(type.getName()).andReturn("test");
            dbsupport.deleteAll(EasyMock.anyObject(Collection.class));
            EasyMock.expect(ClassUtil.invokeMethod(EasyMock.anyObject(Object.class),EasyMock.anyObject(String.class),EasyMock.anyObject(Object.class))).andReturn(type);
            PowerMock.replayAll();
            autoService = new AutoService();
            autoService.callfunc("1","1","entityName");
            verify();
    }

    @Test
    public void testSaveFuncRightByFunc() throws Exception{
            ArrayList reList = new ArrayList();
            HashMap arrMap = new HashMap();
            arrMap.put("pid", "srcFuncUUID");
            arrMap.put("uuid", "uuid");
            arrMap.put("funcname", "funcname");
            reList.add(arrMap);
            DBSupport dbsupport = PowerMock.createMock(DBSupport.class); 
            SessionFactory sessionFactory = PowerMock.createMock(SessionFactory.class);
            ClassMetadata classMetadata  = PowerMock.createMock(ClassMetadata.class);
            Type type  = PowerMock.createMock(Type.class);
            PowerMock.mockStatic(SpringContext.class);
            PowerMock.mockStatic(ClassUtil.class);
            EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(dbsupport);
            EasyMock.expect(dbsupport.getSessionFactory()).andReturn(sessionFactory).anyTimes();
            EasyMock.expect(sessionFactory.getClassMetadata(EasyMock.anyObject(String.class))).andReturn(classMetadata).times(2);
            EasyMock.expect(classMetadata.getPropertyType(EasyMock.anyObject(String.class))).andReturn(type).anyTimes();
            EasyMock.expect(type.getName()).andReturn("test").anyTimes();
            EasyMock.expect(dbsupport.save(EasyMock.anyObject(String.class), EasyMock.anyObject(HashMap.class))).andReturn("test").anyTimes();
            EasyMock.expect(dbsupport.findByHql(EasyMock.anyObject(String.class), EasyMock.anyBoolean())).andReturn(reList);
            EasyMock.expect(ClassUtil.invokeMethod(EasyMock.anyObject(Object.class),EasyMock.anyObject(String.class),EasyMock.anyObject(Object.class))).andReturn(type);
            PowerMock.replayAll();
            autoService = new AutoService();
            autoService.saveFuncRightByFunc("entityName",arrMap);
            verify();
    }
}
