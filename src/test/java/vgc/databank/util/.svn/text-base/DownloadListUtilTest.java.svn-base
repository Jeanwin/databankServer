package test.java.vgc.databank.util;
/**
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.utmost.common.SpringContext;
import org.utmost.portal.service.AutoService;

import com.vgc.databank.util.DownloadListUtil;
/**
 * @author BULL
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
@RunWith(PowerMockRunner.class)
public class DownloadListUtilTest {

	DownloadListUtil downloadListUtil ;
	AutoService autoService;
	@Before
	public void setUp() throws Exception {
		downloadListUtil = PowerMockito.spy(new DownloadListUtil());
		autoService = PowerMockito.mock(AutoService.class);
		Whitebox.setInternalState(downloadListUtil, "autoService", autoService);
	}
	
	@Test
	@PrepareForTest(SpringContext.class)
	public void testHasDuplicate(){
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("user_uuid", "user_uuid");
		map.put("document_title", "document_title");
		list.add(map);
		PowerMockito.when(autoService.findByHql("from U_USER_DOWNLOAD_LIST where user_uuid = 'user_uuid' "
				+ "and document_title = 'document_title' and is_history = '0'")).thenReturn(list);
		HashMap result = downloadListUtil.hasDuplicate(map);
		Assert.assertNotNull(result);
	}
	
	@Test
	@PrepareForTest(SpringContext.class)
	public void testAddPathToMap(){
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("user_uuid", "user_uuid");
		map.put("document_title", "document_title");
		map.put("func_uuid", "123");
		map.put("pid", "0");
		map.put("funcname", "test");
		list.add(map);
		
		PowerMockito.when(autoService.findByHql("select new map (uuid as uuid ,pid as pid ,funcname as funcname)"
				+ "from U_PORTAL_FUNC where uuid='123' ")).thenReturn(list);
		list = downloadListUtil.addPathToMap(list);
		Assert.assertNotNull(list.get(0));
	}

}
