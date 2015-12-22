package test.java.vgc.databank.util;
/**
 * 
 */

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.vgc.databank.util.ExcelStyle;
/**
 * @author BULL
 *
 */
@RunWith(PowerMockRunner.class)
public class ExcelStyleTest {

	ExcelStyle excelStyle ;
	@Before
	public void setUp() throws Exception {
		excelStyle = PowerMockito.spy(new ExcelStyle());
	}
	
	@Test
	@PrepareForTest(ExcelStyle.class)
	public void test(){
		HSSFWorkbook wb = PowerMockito.mock(HSSFWorkbook.class);
		HSSFCellStyle style = PowerMockito.mock(HSSFCellStyle.class);
		HSSFPalette palette = PowerMockito.mock(HSSFPalette.class);
		HSSFFont font = PowerMockito.mock(HSSFFont.class);
		
		PowerMockito.when(wb.createCellStyle()).thenReturn(style);
		PowerMockito.when(wb.createFont()).thenReturn(font);
		PowerMockito.when(wb.getCustomPalette()).thenReturn(palette);
		
		ExcelStyle.getStyle("01", wb, "test");
	}
	
}
