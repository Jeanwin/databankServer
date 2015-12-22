package test.java.vgc.databank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import org.utmost.common.SpringContext;
import org.utmost.portal.service.AutoService;

import com.vgc.databank.service.DownloadListService;
import com.vgc.databank.util.DownloadListUtil;
@SuppressWarnings({"rawtypes","unchecked"})
public class DownloadListServiceTest {
	DownloadListService downloadListService ;
	AutoService autoService;
	DownloadListUtil downloadListUtil;
	@Before
	public void setUp(){
		downloadListService = PowerMockito.spy(new DownloadListService());
		autoService = PowerMockito.mock(AutoService.class);
		downloadListUtil = PowerMockito.mock(DownloadListUtil.class);
		
		Whitebox.setInternalState(downloadListService, "autoService", autoService);
		Whitebox.setInternalState(downloadListService, "downloadListUtil", downloadListUtil);
	}

	
	@Test
	public void testGetTotalPathName() throws Exception{
		List<HashMap> list = new ArrayList<HashMap>();
		HashMap map = new HashMap();
		map.put("funcname","test");
		map.put("funcpid","0");
		list.add(map);
		
		PowerMockito.mockStatic(SpringContext.class);
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);
		
		downloadListService.addToList(list);
	}
	
	@Test
	public void testGetCount() throws Exception{
		List<HashMap> list = new ArrayList<HashMap>();
		HashMap map = new HashMap();
		map.put("count","10");
		map.put("funcpid","0");
		list.add(map);
		
		PowerMockito.mockStatic(SpringContext.class);
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);
		
		String str = downloadListService.getCount("123");
		Assert.assertEquals("10", str);
	}
	
	@Test
	public void testGetPath() throws Exception{
		String str = "test";
		PowerMockito.when(downloadListUtil.getPath("123","","|||")).thenReturn(str);
		
		String result = downloadListService.getPath("123");
		Assert.assertEquals("test", result);
	}
	
	@Test
	public void testGetDownloadList() throws Exception{
		List<HashMap> list = new ArrayList<HashMap>();
		HashMap map = new HashMap();
		map.put("funcname","test");
		map.put("funcpid","0");
		list.add(map);
		
		PowerMockito.mockStatic(SpringContext.class);
		PowerMockito.when(autoService.findByHql(Mockito.isA(String.class))).thenReturn(list);
		
		downloadListService.getDownloadList("123");
	}
	
	@Test
	public void testChangeStatusToDownloaded() throws Exception{
		List<HashMap> list = new ArrayList<HashMap>();
		HashMap map = new HashMap();
		map.put("funcname","test");
		map.put("funcpid","0");
		list.add(map);
		
		downloadListService.changeStatusToDownloaded(list);
	}
}
