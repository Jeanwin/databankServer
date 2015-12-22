package test.java.org.utmost.start;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.utmost.start.JettyServer;

public class JettyServerTest {
	JettyServer jettyServer ;
	@Before
	public void setUp(){
		jettyServer = PowerMockito.spy(new JettyServer());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testMain() throws Exception{
		jettyServer.main(null);
	}
}
