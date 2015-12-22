package org.utmost.common;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

@Service("LogService")
public class LogService {
	private static Logger logger = Logger.getLogger(LogService.class);

	/**
	 * trigger around notice
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	public Object performance(ProceedingJoinPoint pjp) throws Throwable {

		long st = System.currentTimeMillis();
		Object retVal = pjp.proceed();	//call fact method
		long et = System.currentTimeMillis();
		//if time great than 1 millisecond, print detail
		if ((et - st) > 1) {
			// System.out.println("LogService.p*->"
			// + pjp.getSignature().getDeclaringTypeName() + "--"
			// + pjp.getSignature().getName() + " ms:" + (et - st));
			logger.info("*** ->" + pjp.getSignature().getDeclaringTypeName() + "--" + pjp.getSignature().getName() + " ms:" + (et - st));
			Object obj[] = pjp.getArgs();
			int count = 0;
			for (Object s : obj) {
				// System.out.println("		->arg" + (count++) + ":" + s);
				logger.info("		->arg" + (count++) + ":" + s);
			}
		}
		return retVal;
	}
	/**
	 * trigger before call fact method 
	 * @param point
	 */
	public void beforeAdvice(JoinPoint point) {
		System.out.println("前置通知被触发：" + point.getTarget().getClass().getName()
				+ "将要" + point.getSignature().getName());
	}
}
