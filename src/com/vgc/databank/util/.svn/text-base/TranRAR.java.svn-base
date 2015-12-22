package com.vgc.databank.util;

import java.io.File;
import java.io.IOException;
/**
 * rar test
 * 
 * @author bull
 *
 */
public class TranRAR {
	/**
	 * winrar file test
	 * @param src
	 * @param des
	 * @return
	 */
	public String toRAR(String src,String des){
		long start = System.currentTimeMillis();
		File f = new File("E:/e");
		if(!f.exists()){
			f.mkdirs();
		}
		Process p;
		try {
			p = Runtime.getRuntime().exec("C:/Program Files/WinRAR/WinRAR.exe a -df -ibck -r -ep1 "+des+" "+src);
			try {
				if(p.waitFor()==0){
					System.out.println(System.currentTimeMillis()-start);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}
}
