package test.java.vgc.databank.service;
/**
 * 
 */

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.utmost.portal.service.ConsoleService;
import org.utmost.portal.service.LoopedStreams;

/**
 * @author BULL
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class ConsoleServiceTest {

	ConsoleService consoleService ;
	@Before
	public void setUp() throws Exception {
		consoleService = PowerMockito.spy(new ConsoleService());
	}

	@Test
	public void testStartConsole() {
		try {
			consoleService.startConsole();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testStopConsole() {
		consoleService.stopConsole();
	}
	
	@Test
	public void testGetConsoleInfo() {
		String str = consoleService.getConsoleInfo();
		Assert.assertEquals("Console is closed!", str);
	}
	
	@Test
	public void testClear() {
		consoleService.clear();
	}
	
	@Test
	public void testStartConsoleReaderThread() {
		Class clazz = ConsoleService.class;  
		Method addMethod;
		try {
			addMethod = clazz.getDeclaredMethod("startConsoleReaderThread",InputStream.class);
			addMethod.setAccessible(true);
			final LoopedStreams ls = new LoopedStreams();
			addMethod.invoke(consoleService,ls.getInputStream());
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Test method for {@link com.vgc.databank.service.GanttService#publish(java.lang.String)}.
	 
	@Test
	public void testPublish() {
		HashMap commondata = new HashMap();
		commondata.put("uuid", "02");
		commondata.put("func_uuid", "02010303");
		HashMap func = new HashMap();
		func.put("funcname", "standards");
		
		Mockito.when(
				autoService.findByUUID(Mockito.isA(String.class),
						Mockito.isA(String.class))).thenReturn(commondata)
				.thenReturn(func);
		
		List<HashMap> node = new ArrayList<HashMap>();
		HashMap map = new HashMap();
		map.put("uuid", "01");		
		node.add(map);
		Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(node);
		Mockito.when(autoService.save(Mockito.isA(String.class), Mockito.isA(HashMap.class))).thenReturn("01");
		long ok = ganttService.publish("02");
		Assert.assertEquals(Constant.OK, ok);
	}*/

}
