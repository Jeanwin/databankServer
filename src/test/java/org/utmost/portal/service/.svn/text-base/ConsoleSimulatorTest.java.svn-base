package test.java.org.utmost.portal.service;
/**
 * 
 */

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import org.utmost.portal.service.ConsoleSimulator;
import org.utmost.util.CategoryUtil;

import com.vgc.databank.util.FuncUtil;
/**
 * @author BULL
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class ConsoleSimulatorTest {

    ConsoleSimulator consoleSimulator ;
    InputStream is ;
    FuncUtil  funcUtil;
	
	CategoryUtil categoryUtil;
	@Before
	public void setUp() throws Exception {
	    consoleSimulator = PowerMockito.spy(new ConsoleSimulator());
	    is = PowerMockito.mock(InputStream.class);
		Whitebox.setInternalState(consoleSimulator, "is", is);
	}

    @Test
    public void testGetForumCategoryListWithStatistic() {
          List<Map<String, Object>> testArr = new ArrayList<Map<String, Object>>();
          List testArr1 = new ArrayList<HashMap>();
          HashMap hm = new HashMap();
          hm.put("children", testArr1);
          HashMap hm1 = new HashMap();
          hm1.put("children", null);
          testArr1.add(hm1);
          testArr.add(hm);
          
          consoleSimulator.run();
    }

    @Test
    public void testStop() {
          consoleSimulator.stop();
    }
    
//    @Test
//    public void testRunPython() throws Exception{
//      List<Map<String, Object>> testArr = new ArrayList<Map<String, Object>>();
//      List testArr1 = new ArrayList<HashMap>();
//      HashMap hm = new HashMap();
//      hm.put("children", testArr1);
//      HashMap hm1 = new HashMap();
//      hm1.put("children", null);
//      testArr1.add(hm1);
//      testArr.add(hm);
//      
//      consoleSimulator.runPython("newReport.jrxml","scripts");
//}
  }
