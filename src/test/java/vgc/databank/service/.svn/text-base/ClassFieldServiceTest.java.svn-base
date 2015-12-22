package test.java.vgc.databank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.utmost.common.DBSupport;
import org.utmost.common.SpringContext;

import com.vgc.databank.service.ClassFieldService;
@SuppressWarnings({"rawtypes","unchecked"})
@RunWith(PowerMockRunner.class)
public class ClassFieldServiceTest {
	ClassFieldService classFieldService ;
	DBSupport DBSupport;
	@Before
	public void setUp(){
		classFieldService = PowerMockito.spy(new ClassFieldService());
		DBSupport = PowerMockito.mock(DBSupport.class);
	}
	
	@Test
	@PrepareForTest(SpringContext.class)
	public void testGetTotalPathName() throws Exception{
		List<HashMap> list = new ArrayList<HashMap>();
		HashMap map = new HashMap();
		map.put("funcname","test");
		map.put("funcpid","0");
		list.add(map);
		
		PowerMockito.mockStatic(SpringContext.class);
		PowerMockito.when(classFieldService, "getDb").thenReturn(DBSupport);
		PowerMockito.when(DBSupport, "findByHql","",false).thenReturn(list);
		
		classFieldService.getClassFieldByUUID("1");
	}
	
	@Test
	@PrepareForTest(SpringContext.class)
	public void testGetTotalClassField() throws Exception{
		List<HashMap> list = new ArrayList<HashMap>();
		HashMap map = new HashMap();
		map.put("funcname","test");
		map.put("funcpid","0");
		list.add(map);
		
		PowerMockito.mockStatic(SpringContext.class);
		PowerMockito.when(classFieldService, "getDb").thenReturn(DBSupport);
		PowerMockito.when(DBSupport, "findByHql","",false).thenReturn(list);
		
		classFieldService.getTotalClassField();
	}
	
	@Test
	@PrepareForTest(SpringContext.class)
	public void testGetTotalClass() throws Exception{
		List<HashMap> list = new ArrayList<HashMap>();
		HashMap map = new HashMap();
		map.put("funcname","test");
		map.put("funcpid","0");
		list.add(map);
		
		PowerMockito.mockStatic(SpringContext.class);
		PowerMockito.when(classFieldService, "getDb").thenReturn(DBSupport);
		PowerMockito.when(DBSupport, "findByHql","",false).thenReturn(list);
		
		classFieldService.getTotalClass();
	}
	
}
