package org.utmost.util.cmd;

/**
 * rar function class
 * @author bull
 *
 */
public class RarToFile {
	/**
	 * obtain cmd path
	 * @return
	 */
	public static String getCmdPath(){
		return new ReadProperties().getValue("org/utmost/util/cmd/cmd.properties", "rarpath");
	}
	
	/**
	 * call command to zip file
	 * @param rarName rar file name
	 * @param srcDir   
	 * @param destDir
	 */
	public static void RARFile(String rarName, String srcDir, String destDir) {
		String rarCmd = getCmdPath() + " a -df -ibck -r -ep1 "+destDir + rarName + ".zip " + srcDir;
		Process p = null ;
		try {
			Runtime rt = Runtime.getRuntime();
			p = rt.exec(rarCmd);   //execute cmd order
			p.waitFor();
		} catch (Exception e) {
			if(p!=null){
				p.destroy();	
			}
		}
	}
	
	/**
	 * 
	 * @param rarFileName
	 * @param destDir
	 * @deprecated
	 */
	public static void unRARFile(String rarFileName, String destDir) {
		/*String unrarCmd = rarFileName + " " + destDir;
		try {
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec(unrarCmd);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}*/
	}
	
	
	public static void main(String[] args) {
		RARFile("testrar", "D:/temp", "D:/");
	}
}
