package org.utmost.start;


import org.mortbay.jetty.Server;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
public class JettyServer {

	/**
	 * Jetty Server Test
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Server server = new Server();
		Connector conn = new SelectChannelConnector();   //new connection obj
		conn.setPort(8085);		//set port
		server.setConnectors(new Connector[] { conn });		//bind connection obj
		WebAppContext webRoot = new WebAppContext();
		webRoot.setLogUrlOnStart(false);       //set recording log false on start
		webRoot.setContextPath("/databankServer");
		//if need to appoint war directory, build a directory having same name in corresponding project,
		//if not do like this, start exception happen
		webRoot.setWar("WebRoot"); 
		server.setHandler(webRoot);
		server.start();    //start server
	}
}
