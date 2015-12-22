package test.java.vgc.databank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.utmost.common.SpringContext;
import org.utmost.portal.service.AutoService;

import com.vgc.databank.service.BatchDownloadService;
import com.vgc.databank.service.BatchDownloadUtil;
import com.vgc.databank.service.DataInfoService;
import com.vgc.databank.service.GanttService;
import com.vgc.databank.service.MonitorService;
import com.vgc.databank.util.DownloadListUtil;
import com.vgc.databank.util.FuncUtil;
@RunWith(PowerMockRunner.class)
@SuppressWarnings({"rawtypes","unchecked"})
public class BatchDownloadServiceTest {
	BatchDownloadService batchDownloadService ;
	AutoService autoService;
	BatchDownloadUtil batchDownloadUtil;
	MonitorService monitorService;
	DataInfoService dataInfoService;
	GanttService ganttService;
	FuncUtil funcUtil;
	DownloadListUtil downloadListUtil;
	@Before
	public void setUp(){
		batchDownloadService = PowerMockito.spy(new BatchDownloadService());
		autoService = PowerMockito.mock(AutoService.class);
		batchDownloadUtil = PowerMockito.mock(BatchDownloadUtil.class);
		monitorService = PowerMockito.mock(MonitorService.class);
		dataInfoService = PowerMockito.mock(DataInfoService.class);
		ganttService = PowerMockito.mock(GanttService.class);
		funcUtil = PowerMockito.mock(FuncUtil.class);
		downloadListUtil = PowerMockito.mock(DownloadListUtil.class);
		
		Whitebox.setInternalState(batchDownloadService, "autoService", autoService);
		Whitebox.setInternalState(batchDownloadService, "batchDownloadUtil", batchDownloadUtil);
		Whitebox.setInternalState(batchDownloadService, "monitorService", monitorService);
		Whitebox.setInternalState(batchDownloadService, "dataInfoService", dataInfoService);
		Whitebox.setInternalState(batchDownloadService, "ganttService", ganttService);
		Whitebox.setInternalState(batchDownloadService, "funcUtil", funcUtil);
		Whitebox.setInternalState(batchDownloadService, "downloadListUtil", downloadListUtil);
	}
	
	@Test
	@PrepareForTest(BatchDownloadService.class)
	public void testGetForumCategoryList() throws Exception{
		PowerMockito.mockStatic(BatchDownloadService.class);
		BatchDownloadService.print();
	}
	
	@Test
	@PrepareForTest(SpringContext.class)
	public void testGetPath() throws Exception{
		batchDownloadService.getPath(new String[]{"1","2"});
	}
	
	@Test
	@PrepareForTest(SpringContext.class)
	public void testGetCnPath() throws Exception{
		batchDownloadService.getCnPath(new String[]{"1","2"});
	}
	
	@Test
	public void testGetClassUUID() throws Exception{
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("classuuid", "1");
		list.add(map);
		
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);

		batchDownloadService.getClassUUID("1");
	}
	
	@Test
	public void testGetData() throws Exception{
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("classuuid", "1");
		list.add(map);
		
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);

		batchDownloadService.getData("1");
	}
	
	@Test
	public void testGetLeafUUID() throws Exception{
		List list = new ArrayList();
		list.add("3");
		
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);

		batchDownloadService.getLeafUUID("1","2");
	}
	
	
	@Test
	public void testQueryGanttsAndCommonAppendixByUuid() throws Exception{
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("classuuid", "1");
		list.add(map);
		
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);

		batchDownloadService.queryGanttsAndCommonAppendixByUuid("123");
	}
	

}
