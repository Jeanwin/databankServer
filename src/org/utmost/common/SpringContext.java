package org.utmost.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.utmost.portal.service.ConsoleService;
import org.utmost.spring.mvc.controller.DownloadController;
import org.utmost.tpl.service.TemplateService;

/**
 *Spring context service
 * 
 * @author wanglm
 * 
 */
@Component("SpringContext")
public class SpringContext implements ApplicationContextAware {
	private static Logger logger = Logger.getLogger(SpringContext.class);

	private static ApplicationContext applicationContext;

	/**
	 * auto register spring context
	 * 
	 * @param context
	 * @return
	 * @throws BeansException
	 */
	@Autowired
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		serviceForApplicationContext(context);
	}
	
	private static void serviceForApplicationContext(ApplicationContext context){
		applicationContext = context;
	}
	/**
	 * obtain context
	 * @return
	 */
	public static ApplicationContext getContext() {
		if (applicationContext == null) {
			System.out.println("init applicationContext...");
			ApplicationContext ctx = new ClassPathXmlApplicationContext("/conf/applicationContext.xml");
			applicationContext = ctx;
		}
		return applicationContext;
	}

	/**
	 * obtain spring of bean
	 * 
	 * @param name
	 * @return
	 */
	public static Object getBean(String name) {
		return SpringContext.getContext().getBean(name);
	}

	/**
	 * reload spring server and renew born hbm corresponding file
	 * 
	 * @param 
	 * @return
	 */
	public static void reloadSpring() {
		ConsoleService cs = (ConsoleService) SpringContext.getBean("ConsoleService");
		cs.stopConsole();
		// generate hibernate config file
		TemplateService ts = (TemplateService) SpringContext.getBean("TemplateService");
		ts.processAllHbm();
		// renew load and initial spring
		AbstractApplicationContext aac = (AbstractApplicationContext) SpringContext.getContext();
		aac.refresh();

	}
}
