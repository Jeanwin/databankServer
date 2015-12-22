package test.java.vgc.databank.util;
/**
 * 
 */

import org.junit.Before;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.utmost.portal.service.AutoService;

import com.vgc.databank.util.ExcelCellUtil;
/**
 * @author BULL
 *
 */
@RunWith(PowerMockRunner.class)
public class ExcelCellUtilTest {

	ExcelCellUtil excelCellUtil ;
	AutoService autoService;
	@Before
	public void setUp() throws Exception {
		excelCellUtil = PowerMockito.spy(new ExcelCellUtil());
	}
	
}
