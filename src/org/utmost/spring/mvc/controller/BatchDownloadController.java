package org.utmost.spring.mvc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class BatchDownloadController extends MultiActionController {
	
	/**
	 * batch download
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void dload(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");   //set request encoding 'utf-8', prevent messy code
		response.setCharacterEncoding("UTF-8");	 //set response encoding 'utf-8', prevent messy code
		InputStream is = null;
		OutputStream out = null;
		String path = "test.txt";
		File file = null;
		try {
			file = new File(path);
			is = new FileInputStream(file);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ new String("vgc.zip".getBytes("GBK"), "ISO-8859-1") + "\"");  //get byte in 'gbk', package to string in 'iso-8859-1'
			out = response.getOutputStream();		//get outputstream by  response
			byte[] b = new byte[2048];		//byte array
			int len;
			while ((len = is.read(b)) != -1)
				out.write(b, 0, len);		//write byte array to output stream
		} catch (FileNotFoundException e) {
				response.setContentType("text/html;charset=UFT-8");		//if exception happen, set context type text/html normal type
				response.setHeader("Cache-Control", "no-cache");		//set header no cache
				out = response.getOutputStream();
				out.write("File not find!".getBytes());
				logger.error(e);
		}finally{
			try{
				if(is != null){
					is.close();
				}
				if(out != null){
					out.close();
				}
			}catch(Exception e){
				logger.error(e);
			}
		}
	}
}