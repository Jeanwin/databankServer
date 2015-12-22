package test.java.org.utmost.plugins.scripts;
/**
 * 
 */

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.python.util.PythonInterpreter;
import org.springframework.context.ApplicationContext;
import org.utmost.common.DBSupport;
import org.utmost.common.SpringContext;
import org.utmost.plugins.scripts.PythonPlugin;
/**
 * @author BULL
 *
 */
@RunWith(PowerMockRunner.class)
public class PythonPluginTest {

	PythonPlugin pythonPlugin ;
	PythonInterpreter pythonInterpreter;
	DBSupport DBSupport;
	@Before
	public void setUp() throws Exception {
		pythonPlugin = PowerMockito.spy(new PythonPlugin());
		pythonInterpreter = PowerMockito.mock(PythonInterpreter.class);
		DBSupport = PowerMockito.mock(DBSupport.class);
	}
	
	@Test
	public void testSetPythonPath(){
		pythonPlugin.setPythonPath();
	}
	
	@Test
	@PrepareForTest({SpringContext.class,PythonPlugin.class})
	public void testGetPython(){
		PowerMockito.mockStatic(SpringContext.class);
		PowerMockito.when(SpringContext.getContext()).thenReturn(PowerMockito.mock(ApplicationContext.class));
		PowerMockito.when(SpringContext.getBean("DBSupport")).thenReturn(DBSupport);
		PowerMockito.when(SpringContext.getBean("PythonInterpreter")).thenReturn(PowerMockito.mock(PythonInterpreter.class));
		PythonPlugin.getPython();
	}
	
	@Test
	public void testSetPython(){
		pythonPlugin.setPython(PowerMockito.mock(PythonInterpreter.class));
	}


}
