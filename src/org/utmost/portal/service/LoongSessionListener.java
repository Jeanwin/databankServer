package org.utmost.portal.service;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
/**
 * Session listener
 * 
 * @author bull
 *
 */
public class LoongSessionListener implements HttpSessionListener {
	private static int activeSessions = 0;
	/**
	 * create a new session(in fact, var activeSessions plus 1)
	 * 
	 * @param se
	 */
	public void sessionCreated(HttpSessionEvent se) {
		serviceForSessionCreated();
	}
	/**
	 * destroy a session(in fact, var activeSessions subtract 1)
	 * 
	 * @param se
	 */
	public void sessionDestroyed(HttpSessionEvent se) {
		serviceForSessionDestroyed();
	}
	
	public static void serviceForSessionCreated(){
		activeSessions++;
	}
	public static  void serviceForSessionDestroyed(){
		if (activeSessions > 0)
			activeSessions--;
	}
	/**
	 * obtain var activeSessions
	 * 
	 * @return
	 */
	public static int getActiveSessions() {
		return activeSessions;
	}

}
