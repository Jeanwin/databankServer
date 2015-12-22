package test.java.com.exadel.flamingo.service.spring;
/**
 * 
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.powermock.api.mockito.PowerMockito;

import com.exadel.flamingo.service.spring.AMFToSpringServlet;
/**
 * @author BULL
 *
 */
public class AMFToSpringServletTest {

	AMFToSpringServlet AMFToSpringServlet ;
	HttpServletRequest request;
	HttpServletResponse response;
	@Before
	public void setUp() throws Exception {
		AMFToSpringServlet = PowerMockito.spy(new AMFToSpringServlet());
		request = PowerMockito.mock(HttpServletRequest.class);
		response = PowerMockito.mock(HttpServletResponse.class);
	}


}
