package test.java.org.utmost.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.utmost.common.LogService;
public class LogServiceTest {
	LogService logService ;
	JoinPoint point;
	Object target;
	String name;
	Signature signature;
	ProceedingJoinPoint pjp;
	@Before
	public void setUp() throws Exception {
		logService = PowerMockito.spy(new LogService());
		point = PowerMockito.mock(JoinPoint.class);
		target = PowerMockito.mock(Object.class);
		name = PowerMockito.mock(String.class);
		signature = PowerMockito.mock(Signature.class);
		pjp = PowerMockito.mock(ProceedingJoinPoint.class);
	}
	
	@Test
	public void testBeforeAdvice() throws Exception{
		PowerMockito.when(point.getTarget()).thenReturn(target);
		PowerMockito.when(point.getSignature()).thenReturn(signature);
		PowerMockito.when(signature.getName()).thenReturn(name);

		logService.beforeAdvice(point);
	}
	
	@Test
	public void testPerformance() throws Throwable{
		PowerMockito.when(point.getTarget()).thenReturn(target);
		PowerMockito.when(point.getSignature()).thenReturn(signature);
		PowerMockito.when(signature.getName()).thenReturn(name);

		logService.performance(pjp);
	}
}
