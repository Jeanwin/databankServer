package test.java.org.utmost.portal.service;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.utmost.portal.service.ForceNoCacheFilter;

public class ForceNoCacheFilterTest {
	ForceNoCacheFilter forceNoCacheFilter ;
	ServletRequest request;
	ServletResponse response;
	FilterChain filterChain;
	@Before
	public void setUp(){
		forceNoCacheFilter = PowerMockito.spy(new ForceNoCacheFilter());
		request = PowerMockito.mock(ServletRequest.class);
		response = PowerMockito.mock(HttpServletResponse.class);
		filterChain = PowerMockito.mock(FilterChain.class);
	}
	
	@Test
	public void testDoFilter() throws Exception{
		forceNoCacheFilter.doFilter(request, response, filterChain);
	}
	
	@Test
	public void testInit() throws Exception{
		forceNoCacheFilter.init(PowerMockito.mock(FilterConfig.class));
	}
	
	@Test
	public void testDestroy() throws Exception{
		forceNoCacheFilter.destroy();
	}
	@Test
    public void testGetForumCategoryListWithStatistic()throws Exception {
          forceNoCacheFilter.doFilter(PowerMockito.mock(ServletRequest.class),PowerMockito.mock(HttpServletResponse.class),PowerMockito.mock(FilterChain.class));
    }
}
