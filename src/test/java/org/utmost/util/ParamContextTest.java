package test.java.org.utmost.util;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.utmost.util.ParamContext;
public class ParamContextTest {
	ParamContext paramContext ;
	@Before
	public void setUp(){
		paramContext = PowerMockito.spy(new ParamContext());
	}
	
	@Test
	public void test(){
		PowerMockito.mockStatic(ParamContext.class);
	}
	

}
