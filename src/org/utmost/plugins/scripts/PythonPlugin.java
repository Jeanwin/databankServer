package org.utmost.plugins.scripts;

import org.python.util.PythonInterpreter;
import org.utmost.common.SpringContext;
import org.utmost.util.PathUtil;
/**
 * plugin class
 * 
 * @author bull
 *
 */
public class PythonPlugin {

	private static PythonInterpreter python = null;
	/**
	 * set python path
	 */
	public void setPythonPath() {
		String url = PathUtil.getRootPath();
		if (url != null) {
			System.setProperty("python.home", url);
			System.out.println("is web!!");
		} else {
			System.out.println("not web!!");
			System.setProperty("python.home", PathUtil.getClassFilePath());
		}
	}
	/**
	 * obtain python interpreter
	 * 
	 * @return
	 */
	public static PythonInterpreter getPython() {
		if (SpringContext.getContext() != null
				&& SpringContext.getBean("PythonInterpreter") != null) {
			return (PythonInterpreter) SpringContext
					.getBean("PythonInterpreter");
		}
		return new PythonInterpreter();
	}
	/**
	 * set python
	 * 
	 * @param python
	 */
	public void setPython(PythonInterpreter python) {
		serviceForSetPathon(python);
	}
	private static void serviceForSetPathon(PythonInterpreter ptn){
		python = ptn;
	}
	public static void main(String[] args) {}

}
