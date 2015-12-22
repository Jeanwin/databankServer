package test.java.vgc.databank.util;
/**
 * 
 */

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.commontemplate.util.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.utmost.common.DBSupport;
import org.utmost.common.SpringContext;
import org.utmost.portal.service.AutoService;

import com.vgc.databank.util.CommonTools;
/**
 * @author BULL
 *
 */
@SuppressWarnings({"rawtypes","deprecation","unchecked"})
@RunWith(PowerMockRunner.class)
public class CommonToolsTest {

	CommonTools commonTools ;
	AutoService autoService;
	DBSupport DBSupport;
	@Before
	public void setUp() throws Exception {
		commonTools = PowerMockito.spy(new CommonTools());
		autoService = PowerMockito.mock(AutoService.class);
		DBSupport = PowerMockito.mock(DBSupport.class);
	}
	
	@Test
	public void testParseFileName(){
		String str = commonTools.parseFileName("test", "main");
		Assert.assertNotNull(str);
	}
	
	
	@Test
	@PrepareForTest(SpringContext.class)
	public void testGetTotalPathName(){
		List list = new ArrayList();
		HashMap map = new HashMap();
		map.put("funcname", "test");
		map.put("funcpid", "0");
		list.add(map);
		
		PowerMockito.mockStatic(SpringContext.class);
		PowerMockito.when(commonTools.getDb()).thenReturn(DBSupport);
		PowerMockito.when(DBSupport.findByHql("select new map (u.uuid as funcuuid,u.pid as funcpid,u.funcname as funcname) "
				+ "from U_PORTAL_FUNC u where u.uuid='123'",false)).thenReturn(list);
		
		String str = commonTools.getTotalPathName("123");
		Assert.assertNotNull(str);
	}
	

}
