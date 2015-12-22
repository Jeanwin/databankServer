package org.utmost.portal.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.utmost.common.SpringContext;
import org.utmost.plugins.scripts.PythonPlugin;

public class RuleEngine {
	public static void main(String[] args) throws IOException {
		RuleEngine.execFile(args[0]);
	}

	public static String execFile(String arg) throws IOException {
		// setSysArg();
		BufferedReader fr = null;
		try{
			fr = new BufferedReader(new FileReader(arg));
			String str = null;
			String script = "";
			while ((str = fr.readLine()) != null) {
				script += str + "\n";
			}
			script = new String(script.getBytes(), "8859_1");
			PythonInterpreter interp = PythonPlugin.getPython();
			interp.exec(script);
			PyObject _rv = interp.get("_ro");
			fr.close();
			if (_rv != null)
				return _rv.toString();
			return null;
		}catch(IOException e){
			throw e;
		}finally{
			if(fr!=null){
				fr.close();
			}
		}
	}

	public static String exec(String script)
			throws UnsupportedEncodingException {
		// setSysArg();
		// PythonInterpreter interp = new PythonInterpreter();

		PythonInterpreter interp = PythonPlugin.getPython();

		script = new String(script.getBytes(), "8859_1");
		interp.exec(script);
		PyObject _rv = interp.get("_ro");
		if (_rv != null)
			return _rv.toString();
		return null;
	}
	/*
	 * public static void setSysArg() { String url =
	 * RuleEngine.class.getResource("").getPath(); url = url.replaceAll("%20",
	 * " "); url = url.substring(1, url.indexOf("/UtmostTPL/"));
	 * System.setProperty("python.home", url); String pythonpath = url +
	 * "/UtmostTPL/WEB-INF/classes/org/utmost/portal/scripts/template/;"; //
	 * System.out.println("pythonpath:" + pythonpath);
	 * System.setProperty("python.path", pythonpath); }
	 */
}
