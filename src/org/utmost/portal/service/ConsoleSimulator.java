package org.utmost.portal.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

@Service("ConsoleSimulator")
public class ConsoleSimulator implements Runnable {
	public ConsoleSimulator() {}

	private volatile boolean isStop = false;

	private static final int INFO = 0;

	private static final int ERROR = 1;

	private InputStream is;

	private int type;

	public ConsoleSimulator(InputStream is, int type) {
		this.is = is;
		this.type = type;
	}

	public static String consoleContext = "";

	public static void clearContext() {
		consoleContext = "";
	}

	public void run() {
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(isr);
		String s;
		try {
			while ((!isStop) && (s = reader.readLine()) != null) {
				if (s.length() != 0) {
					if (type == INFO) {
						setConsoleContext("INFO> " + s + "\n");
					} else {
						setConsoleContext("ERROR> " + s + "\n");
					}

					try {
						Thread.sleep(10);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void setConsoleContext(String val){
		consoleContext += val;
	}
	
	public void stop() {
		isStop = true;
	}

	public static String runPython(String name, String scripts)
			throws InterruptedException, IOException {
		String url = ConsoleSimulator.class.getResource("").getPath();
		url = url.replaceAll("%20", " ");
		String classesurl = url.substring(1, url.indexOf("/classes/"))
				+ "/classes/";
		
		String liburls = "";
		String filespath = url.substring(1, url.indexOf("/classes/")) + "/lib/";
		File f = new File(filespath);
		System.out.println(filespath);
		File[] files = f.listFiles();
		for (File fo : files) {
			liburls += filespath + fo.getName() + ";";
			// System.out.println(fo.getName());
		}

		// Properties pro = System.getProperties();
		// System.out.println("Properties:\n"+pro);
		String scripsurl = url.substring(1, url.indexOf("/classes/"))
				+ "/classes/org/utmost/portal/service/";
		// System.out.println("url:" + url);
		// System.out.println("classesurl:" + classesurl);
		// System.out.println("liburl:" + liburl);

		// scripts=scripts.replaceAll("\n", ";");
		// scripts=scripts.replaceAll(";+", ";");

		String filename = scripsurl + name;
		FileWriter fw = null;
		try{
			fw = new FileWriter(new File(filename));
			fw.write(scripts);
		}catch(Exception e){
			throw new IOException();
		}finally{
			try{
			  fw.close();
			}catch(Exception e){
				throw new IOException();
			}
		}

		String command = "java -classpath \"" + classesurl + "\";\"" + liburls
				+ "\" org.utmost.portal.service.RuleEngine \"" + filename
				+ "\"";
		System.out.println("command:\n" + command);

		Process child = Runtime.getRuntime().exec(command);

		
		InputStream stdin = child.getInputStream();
		InputStream stderr = child.getErrorStream();
		//start new thread to intercept data the command line printed
		Thread tIn = new Thread(new ConsoleSimulator(stdin, INFO));
		Thread tErr = new Thread(new ConsoleSimulator(stderr, ERROR));
		tIn.start();
		tErr.start();
		int result = child.waitFor();
		tIn.join();
		tErr.join();
		if (result == 0) {
			// System.out.println("SUCCESS! ");
		} else {
			// System.out.println("FAILED! ");
		}
		return consoleContext;
	}
}
