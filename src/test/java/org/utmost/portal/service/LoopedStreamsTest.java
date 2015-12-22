package test.java.org.utmost.portal.service;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.utmost.portal.service.LoopedStreams;

public class LoopedStreamsTest {
	LoopedStreams loopedStreams ;
	HttpSessionEvent se;
	ServletResponse response;
	FilterChain filterChain;
	@Before
	public void setUp() throws IOException{
		loopedStreams = PowerMockito.spy(new LoopedStreams());
		se = PowerMockito.mock(HttpSessionEvent.class);
		response = PowerMockito.mock(HttpServletResponse.class);
		filterChain = PowerMockito.mock(FilterChain.class);
	}
	
	@Test
	public void testGetInputStreamd() throws Exception{
		loopedStreams.getInputStream();
	}
	
	@Test
	public void testGetOutputStream() throws Exception{
		loopedStreams.getOutputStream();
	}
	
}
