package test.java.com.exadel.flamingo.service.spring;
/**
 * 
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.powermock.api.mockito.PowerMockito;

import com.exadel.flamingo.service.spring.AMFToSpringServletForIP;
/**
 * @author BULL
 *
 */
public class AMFToSpringServletForIPTest {

	AMFToSpringServletForIP AMFToSpringServletForIP ;
	HttpServletRequest request;
	HttpServletResponse response;
	@Before
	public void setUp() throws Exception {
		AMFToSpringServletForIP = PowerMockito.spy(new AMFToSpringServletForIP());
		request = PowerMockito.mock(HttpServletRequest.class);
		response = PowerMockito.mock(HttpServletResponse.class);
	}


}
