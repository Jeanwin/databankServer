package test.java.org.utmost.portal.service;

import javax.servlet.FilterChain;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.utmost.portal.service.LoongSessionListener;

public class LoongSessionListenerTest {
	LoongSessionListener loongSessionListener ;
	HttpSessionEvent se;
	ServletResponse response;
	FilterChain filterChain;
	@Before
	public void setUp(){
		loongSessionListener = PowerMockito.spy(new LoongSessionListener());
		se = PowerMockito.mock(HttpSessionEvent.class);
		response = PowerMockito.mock(HttpServletResponse.class);
		filterChain = PowerMockito.mock(FilterChain.class);
	}
	
	@Test
	public void testSessionCreated() throws Exception{
		loongSessionListener.sessionCreated(se);
	}
	
	@Test
	public void testSessionDestroyed() throws Exception{
		loongSessionListener.sessionDestroyed(se);
	}
	
	@Test
	public void testGetActiveSessions() throws Exception{
		PowerMockito.mockStatic(LoongSessionListener.class);
		int i = LoongSessionListener.getActiveSessions();
		Assert.assertEquals(0,i);
	}
}
