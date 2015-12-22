package test.java.vgc.databank.service;
/**
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import org.utmost.portal.service.AutoService;

import com.vgc.databank.model.ExcelModel;
import com.vgc.databank.service.GenerateExcelService;
import com.vgc.databank.service.SearchService;
import com.vgc.databank.util.GenerateExcelUtil;
/**
 * @author BULL
 *
 */
public class GenerateExcelServiceTest {
	GenerateExcelService generateExcelService;

	GenerateExcelUtil generateExcelUtil;
	
	SearchService searchService;
	
	AutoService autoService;
	
	@Before
	public void setUp() throws Exception {
		generateExcelService = PowerMockito.spy(new GenerateExcelService());

		generateExcelUtil = PowerMockito.mock(GenerateExcelUtil.class);
		searchService = PowerMockito.mock(SearchService.class);
		autoService = PowerMockito.mock(AutoService.class);
		Whitebox.setInternalState(generateExcelService, "generateExcelUtil", generateExcelUtil);
		Whitebox.setInternalState(generateExcelService, "searchService", searchService);
		Whitebox.setInternalState(generateExcelService, "autoService", autoService);
	}
	

	@Test
	public void testCheckDirAndDelOtherFile() {
  	    List testArr = new ArrayList();
        HashMap hm = new HashMap();
        hm.put("logintime", 1234L);
        hm.put("loguuid", "d");
        testArr.add(hm);
        List testArr1 = new ArrayList<HashMap>();
        testArr1.add("standards");
        ExcelModel em = new ExcelModel();
        em.setColumnSize(2);
        em.setDataSummaryTitle("dataSummaryTitle");
        em.setDefaultFont("defaultFont");
        em.setSelectedItemTitle("selectedItemTitle");
        em.setSheetName("sheetName");
        em.setSheetTitle("sheetTitle");
        em.setSumColumnNum(2);
        em.setTitles(new ArrayList());
        Mockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(testArr);
		String result = generateExcelService.generatedExcel(testArr, testArr1, "en", "", "", "", "");
		Assert.assertNotNull(result);
	}
	
	
}
