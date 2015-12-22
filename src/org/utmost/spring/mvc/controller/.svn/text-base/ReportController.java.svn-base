package org.utmost.spring.mvc.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.utmost.util.PathUtil;
/**
 * Report controller function class
 * 
 * @author bull
 *
 */
public class ReportController extends MultiActionController {
	public void reportDownload(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");	//set request encode 'utf-8'
		response.setCharacterEncoding("UTF-8");	//set response encoe 'utf-8'
		String path = request.getParameter("path");
		String name = request.getParameter("name");
		
		response.setContentType("application/octet-stream");  //set conten+t type
		response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
		OutputStream out = null;
		InputStream is = null;
		try{	
			out = response.getOutputStream();	//get outputstream
			String filepath = PathUtil.getWebPath() + path + name;
			is = new FileInputStream(filepath);
			byte[] b = new byte[2048];
			int len;
			while ((len = is.read(b)) != -1)
				out.write(b, 0, len);
			
		}catch(Exception e){
			throw e;
		}finally{	//close all stream
			if(is != null){
				is.close();
			}
			if(out != null){
				out.close();
			}
		}
		
	}
}
