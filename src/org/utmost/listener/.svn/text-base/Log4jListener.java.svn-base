package org.utmost.listener;

import java.io.FileNotFoundException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.util.Log4jConfigurer;
import org.springframework.util.ResourceUtils;
import org.springframework.web.util.ServletContextPropertyUtils;
import org.springframework.web.util.WebUtils;

public class Log4jListener implements ServletContextListener{
	
	public void contextInitialized(ServletContextEvent event)
    {
		ServletContext servletContext = event.getServletContext();
        if(exposeWebAppRoot(servletContext))
            WebUtils.setWebAppRootSystemProperty(servletContext);
        String location = servletContext.getInitParameter("log4jConfigLocation");
        
        if(location != null)
            try
            {
                location = ServletContextPropertyUtils.resolvePlaceholders(location, servletContext);
                if(!ResourceUtils.isUrl(location))
                    location = WebUtils.getRealPath(servletContext, location);
                String active = System.getProperty("spring.profiles.active");
                if(active == null){
                	throw new NullPointerException((new StringBuilder()).append("Parameter spring.profiles.active not set!").toString());
                }else if(!"dev".equals(active) && !"intg".equals(active) && !"qa".equals(active) && !"prod".equals(active)){
                	throw new IllegalArgumentException((new StringBuilder()).append("Please set spring.profiles.active to dev, intg, qa or prod").toString());
                }
                location = location.replace("log4j.xml", active+"/log4j.xml");
                servletContext.log((new StringBuilder()).append("Initializing log4j from [").append(location).append("]").toString());
                String intervalString = servletContext.getInitParameter("log4jRefreshInterval");
                if(intervalString != null)
                    try
                    {
                        long refreshInterval = Long.parseLong(intervalString);
                        Log4jConfigurer.initLogging(location, refreshInterval);
                    }
                    catch(NumberFormatException ex)
                    {
                        throw new IllegalArgumentException((new StringBuilder()).append("Invalid 'log4jRefreshInterval' parameter: ").append(ex.getMessage()).toString());
                    }
                else
                    Log4jConfigurer.initLogging(location);
            }
            catch(FileNotFoundException ex)
            {
                throw new IllegalArgumentException((new StringBuilder()).append("Invalid 'log4jConfigLocation' parameter: ").append(ex.getMessage()).toString());
            }
    }

    public void contextDestroyed(ServletContextEvent event)
    {
    	ServletContext servletContext = event.getServletContext();
    	servletContext.log("Shutting down log4j");
        Log4jConfigurer.shutdownLogging();
        if(exposeWebAppRoot(servletContext))
            WebUtils.removeWebAppRootSystemProperty(servletContext);
    }
    
    private static boolean exposeWebAppRoot(ServletContext servletContext)
    {
        String exposeWebAppRootParam = servletContext.getInitParameter("log4jExposeWebAppRoot");
        return exposeWebAppRootParam == null || Boolean.valueOf(exposeWebAppRootParam).booleanValue();
    }

}
