package test.java.org.utmost.common;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.utmost.common.DBSupport;
import org.utmost.common.SpringContext;
import org.utmost.common.UtmostCache;
@RunWith(PowerMockRunner.class)
@SuppressWarnings("rawtypes")
public class UtmostCacheTest {
	UtmostCache utmostCache ;
	DBSupport DBSupport;
	@Before
	public void setUp() throws Exception {
		utmostCache = PowerMockito.spy(new UtmostCache());
		DBSupport = PowerMockito.mock(DBSupport.class);
	}
	
	@Test
	@PrepareForTest(SpringContext.class)
	public void testInvokeMethod() throws Exception{
		List list = new ArrayList();
		PowerMockito.mockStatic(SpringContext.class);
		PowerMockito.when(utmostCache.getDb()).thenReturn(DBSupport);
		PowerMockito.when(DBSupport.findAll("U_TPL_TEMPLATEDATA")).thenReturn(list);
		PowerMockito.when(DBSupport.findAll("U_TPL_TEMPLATEVIEW")).thenReturn(list);

		utmostCache.cacheTPL();
	}
	
	@Test
	@PrepareForTest(SpringContext.class)
	public void testGetList() throws Exception{

		utmostCache.getList("U_TPL_TEMPLATEDATA");
	}
}
