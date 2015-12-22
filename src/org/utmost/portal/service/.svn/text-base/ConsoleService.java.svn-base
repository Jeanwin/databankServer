package org.utmost.portal.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.springframework.stereotype.Service;
/**
 * 
 * @description Console Service
 * @author  
 * @version 3.2.0
 * @date Feb 2, 2015 11:16:25 AM
 */
@Service("ConsoleService")
public class ConsoleService {

	public ConsoleService(InputStream[] inStreams) {
		for (int i = 0; i < inStreams.length; ++i)
			startConsoleReaderThread(inStreams[i]);
	} // ConsoleTextArea()

	public ConsoleService() {
		old = System.out;// source
		olderr = System.err;// source
	} // ConsoleTextArea()
	/**
	 * 
	 * @description start Console
	 * @author 
	 * @throws IOException
	 */
	public void startConsole() throws IOException {
		if (!istrue) {
			istrue = true;
			final LoopedStreams ls = new LoopedStreams();
			// redirect System.out and System.err
			PrintStream ps = new PrintStream(ls.getOutputStream());
			System.setOut(ps);
			System.setErr(ps);
			startConsoleReaderThread(ls.getInputStream());
		}
	}

	private StringBuffer rs = new StringBuffer();
	/**
	 * 
	 * @description stop Console
	 * @author 
	 */
	public void stopConsole() {
		if (istrue) {
			istrue = false;
			System.setOut(old);
			System.setErr(olderr);
		}
	}

	private Boolean istrue = false;
	private PrintStream old = null;
	private PrintStream olderr = null;
	/**
	 * 
	 * @description get Console Info
	 * @author 
	 * @return
	 */
	public String getConsoleInfo() {
		if (istrue)
			return rs.toString();
		else
			return "Console is closed!";
	}
	/**
	 * 
	 * @description clear
	 * @author 
	 */
	public void clear() {
		rs = new StringBuffer();
	}
	
	// startConsoleReaderThread()
	private void startConsoleReaderThread(InputStream inStream) {
		final BufferedReader br = new BufferedReader(new InputStreamReader(
				inStream));
		new Thread(new Runnable() {
			public void run() {
				
				try {
					String s = "";
					while ((s = br.readLine()) != null) {
						rs.append(s + "\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	} 
}
