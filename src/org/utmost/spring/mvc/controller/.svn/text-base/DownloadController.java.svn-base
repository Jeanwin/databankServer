package org.utmost.spring.mvc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.utmost.common.SpringContext;
import org.utmost.util.PathUtil;

import com.vgc.databank.service.MonitorService;
import com.vgc.databank.util.DownloadListUtil;
import com.vgc.databank.util.Main;
/**
 * 
 * @author Zhao Wei
 * This class is controller for download, most function is untouched
 *
 */

public class DownloadController extends MultiActionController {

	private static Logger logger = Logger.getLogger(DownloadController.class);

	/**
	 * utf-8 Transcoding tool
	 * 
	 * @param s
	 * @return
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {	//get every char from string s
			char c = s.charAt(i);	//get a char
			if (c >= 0 && c <= 255) {		//judging char of ascii
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");	//transfer char c to string c, then get byte array by encoding 'utf-8'
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		String s_utf8 = sb.toString();
		sb.delete(0, sb.length());
		sb.setLength(0);
		sb = null;
		return s_utf8;
	}

	/**
	 * Read image file
	 * 
	 * @param request
	 * @param response
	 */
	public void readImage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		request.setCharacterEncoding("UTF-8");  //set encoding 'utf-8', prevent messy code
		response.setCharacterEncoding("UTF-8"); //set encoding 'utf-8', prevent messy code

		String fileName = request.getParameter("fileName");
		String filepath = "/" + PathUtil.getUploadPath() + "/" + fileName;   //obtain file path
		InputStream is = null;
		OutputStream out = null;
		try {
			is = new FileInputStream(filepath);
			out = response.getOutputStream();
			byte[] b = new byte[2048];			//define a byte array for putting byte reading from inputstream
			int len;
			while ((len = is.read(b)) != -1)   
				out.write(b, 0, len);		//write byte array to outputstream
		} catch (FileNotFoundException e) {
			logger.error("image not found:" + fileName,e);
		}finally{		//close all stream
			if(is!=null){
				is.close();
			}
			if(out!=null){
				out.close();
			}
		}
	}

	
	/**
	 * Single file download and view function 
	 * new parameter action is used to control whether download or view
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void download(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");   //set encoding 'utf-8', prevent messy code
		response.setCharacterEncoding("UTF-8");  //set encoding 'utf-8', prevent messy code
		String path = request.getParameter("path");
		String name = request.getParameter("name");
		if (name != null) {
			name = new String(name.getBytes("iso-8859-1"), "utf-8");  //read in 'iso-8859-1', package in 'utf-8'
		}
		String uuid = request.getParameter("uuid");// func_uuid
		String author = request.getParameter("author");
		String useruuid = request.getParameter("useruuid");
		String last_modify_date_str = request.getParameter("last_modify_date_str"); 
		String action = request.getParameter("action") != null?request.getParameter("action"):"download";
		logger.debug("path:" + path + ",name:" + name + ",uuid:" + uuid
				+ ",author:" + author + ",useruuid:" + useruuid
				+ ",lastModify:" + last_modify_date_str + ";");
		MonitorService monitorService = (MonitorService) SpringContext.getBean("MonitorService");   //get monitorService from spring context bean 'MonitorService'
		DownloadListUtil downloadListUtil = (DownloadListUtil) SpringContext.getBean("DownloadListUtil");
		monitorService.renewalDownload(downloadListUtil.getPath(uuid,"","/"), name, author,useruuid, last_modify_date_str);
		String filepath = "/" + PathUtil.getFileSaveRoot() + path + "/" + name;
		InputStream is = null;
		OutputStream out = null;
		try {
			is = new FileInputStream(filepath);
			if("view".equals(action)){		//view action
				//file can be either pdf or swf/flv
				String extension = name.substring(name.length()-3);
				if("pdf".equals(extension)){		//set content type 'pdf'
					response.setContentType("application/pdf");
				}else{		//set content type 'flash'
					response.setContentType("application/x-shockwave-flash");
				}
			}else if("download".equals(action)){	// download action
				response.setContentType("application/x-msdownload");   //set contect type 'download'
				String fileName = URLEncoder.encode(monitorService.getOriginalFileName(name), "UTF-8").replace("+"," ");
				response.setHeader("Content-Disposition", "attachment; filename=\""
						+ fileName + "\"");   //encoding 'utf-8'
			}
			
			logger.info("download path :" + path + "/" + name);
			out = response.getOutputStream();	//get outputstream by response
			byte[] b = new byte[2048];		//define a byte array for putting byte reading from inputstream
			int len;
			while ((len = is.read(b)) != -1)
				out.write(b, 0, len);		//write byte array to outputstream
			
		}catch (FileNotFoundException e) {	//when exception happen
			logger.error("download path:" + path + "/" + name);
			try {
				//If target file is not found, download WebRoot/ERRINFO/readme.txt instead
				if("view".equals(action)){		//view action
					response.setContentType("text/plain");
				}else if("download".equals(action)){	//download action
					response.setContentType("application/x-msdownload");
					response.setHeader("Content-Disposition",
							"attachment; filename=\"" + URLEncoder.encode("readme.txt", "UTF-8")+ "\"");
				}
				
				out = response.getOutputStream();
				is = new FileInputStream(PathUtil.getWebPath() + "ERRINFO" + "/" + "readme.txt");  //get read me info
				byte[] b = new byte[2048];		//define a byte array for putting byte reading from inputstream
				int len;
				while ((len = is.read(b)) != -1)
					out.write(b, 0, len);
			} catch (RuntimeException e1) {
				logger.info("IO Exception:" + path + "/" + name);
				logger.error("IO Exception:" + path + "/" + name);
			}finally{
				try{
					if(is != null){
						is.close();
					}
					if(out != null){
						out.close();
					}
				}catch(Exception e1){
					logger.error(e1);
				}
			}
		}finally{	//close all stream
			try{
				if(is != null){
					is.close();
				}
				if(out != null){
					out.close();
				}
			}catch(Exception e){
				logger.error("IO Exception:" + path + "/" + name);
			}
		}
	}

	
	/**
	 * Single file downLoad excel file, this function service for generate excel report.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void downloadExcel(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		InputStream is = null;
		OutputStream out = null;
		String filePath = request.getParameter("url");
		try {
			request.setCharacterEncoding("UTF-8");	//set encoding 'utf-8', prevent messy code
			response.setCharacterEncoding("UTF-8"); //set encoding 'utf-8', prevent messy code
			int beginIndex = filePath.lastIndexOf("\\");
			String name = filePath.substring(beginIndex+1);
			is = new FileInputStream(filePath);
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ URLEncoder.encode(name, "UTF-8") + "\"");
			out = response.getOutputStream();
			byte[] b = new byte[2048];
			int len;
			while ((len = is.read(b)) != -1)
				out.write(b, 0, len);
		
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
		}finally{	//close all stream
			try{
				is.close();
				out.close();
			}catch(Exception e){
				logger.error(e.getMessage(), e);
			}
		}
	}

	
	
	/**
	 * Batch download
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void zipDownload(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");		//set encoding 'utf-8', prevent messy code
		response.setCharacterEncoding("UTF-8");		//set encoding 'utf-8', prevent messy code
		String url = request.getParameter("url");	//obtain url
		String webPath = PathUtil.getWebPath();
		InputStream is = null;
		OutputStream out = null;
		try {
			if (url.equals("readme.txt")) {
				is = new FileInputStream(PathUtil.getWebPath()
						+ "ERRINFO/readme.txt");
			} else {
				is = new FileInputStream(PathUtil.getWebPath() + "TEMP/" + url);
			}
			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ new String(url.getBytes("UTF-8"), "ISO-8859-1") + "\"");
			out = response.getOutputStream();
			byte[] b = new byte[2048];
			int len;
			while ((len = is.read(b)) != -1)
				out.write(b, 0, len);
			out.flush();
		} catch (FileNotFoundException e) {
			try {
			    takeTryCatch( response, is , out,"x-download");  //set head info
			    if(out!=null){
				  out.flush();
			    }
			} catch (RuntimeException re) {
				logger.info("IO Exception:" + PathUtil.getWebPath() + "TEMP/"
						+ url);
			}
			logger.error("download path:" + PathUtil.getWebPath() + "TEMP/"
					+ url , e);
		} finally {		//close all stream and others handle
			try {
				if(is != null){
					is.close();
				}
				if(out != null){
					out.close();
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
			}

			Main main = new Main();
			File files = new File(webPath + "TEMP/" + url);
			if (files.exists()) {
				main.delete(files);
			}
			url = url.substring(0, url.lastIndexOf("."));
			File zipfile = new File(webPath + "TEMP/" + url);
			if (zipfile.exists()) {
				System.out.println("delete *[]..");
				main.delete(zipfile);
			}
		}
	}

	/**
	 * Batch download
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void dlzip(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		/**
		 * set encoding 'utf-8', prevent messy code
		 */
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String url = request.getParameter("url");
		response.setContentType("application/x-download"); // set type to application/x-download
		String filenamedisplay = url; // file name
		String filenamedownload = PathUtil.getWebPath() + "TEMP/" + filenamedisplay; // absolute dictionary path
		filenamedisplay = URLEncoder.encode(filenamedisplay, "UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + filenamedisplay);
		java.io.OutputStream os = null;
		java.io.FileInputStream fis = null;
		try {
			os = response.getOutputStream();
			fis = new java.io.FileInputStream(filenamedownload);
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = fis.read(b)) > 0) {
				os.write(b, 0, i);		//write file out
			}
			
		} catch (Exception e) {
		}finally{	//close all stream
			try{
				fis.close();
				os.flush();
				os.close();
			}catch(Exception e){
			    logger.error(e.getMessage(), e);
			}
		}

	}

	/**
	 * Zip files
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void zip(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		/**
		 * set encoding 'utf-8', prevent messy code
		 */
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		InputStream is = null;
		OutputStream out = null;
		String path = "";
		
		try {
			File file = new File(path);
			is = new FileInputStream(file);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ new String("vgc.zip".getBytes("UTF-8"), "ISO-8859-1") + "\"");
			out = response.getOutputStream();
			byte[] b = new byte[2048];
			int len;
			while ((len = is.read(b)) != -1)
				out.write(b, 0, len);
			out.close();
			is.close();
		} catch (FileNotFoundException e) {
			try {
			    takeTryCatch( response, is , out,"octet-stream"); //set head info
			} catch (RuntimeException e1) {
			    takeCatch(e, response, out);  //set head info
			}
			logger.error(e);
		}finally{	//close all stream
			try{
			  is.close();
			  out.close();
			}catch(Exception e){
				logger.error("error",e);
			}
		}
	}
	/**
	 * obtain image source
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void findImgSource(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");		//set encoding 'utf-8', prevent messy code
		response.setCharacterEncoding("UTF-8");		//set encoding 'utf-8', prevent messy code
		String path = request.getParameter("path");
		String name = request.getParameter("name");
		String filepath = PathUtil.getWebPath() + path + "/" + name;
		InputStream is = null;
		OutputStream out = null;
		try {
			is = new FileInputStream(filepath);
			response.setContentType("application/octet-stream");   //set content type support octet stream
			response.setHeader("Content-Disposition", "attachment; filename=\""+ new String(name.getBytes("UTF-8"), "ISO-8859-1") + "\"");
			out = response.getOutputStream();		//get outputstream
			byte[] b = new byte[2048];
			int len;
			while ((len = is.read(b)) != -1)
				out.write(b, 0, len);
			
		} catch (FileNotFoundException e) {
		    takeCatch(e, response, out);
		}finally{		//close all stream
			try{
				if(is != null){
				      is.close();
				  }
				  if(out != null ){
				      out.close();
				  }
			}catch(IOException e){
				throw e;
			}
		}
	}

	/**
	 * take try catch
	 * set head info
	 * @param response
	 * @throws IOException
	 */
	private void takeTryCatch(HttpServletResponse response,InputStream is ,OutputStream out,String contentType)throws UnsupportedEncodingException, IOException{
    	 try{
    		 response.setContentType("application/"+contentType);
             response.setHeader("Content-Disposition","attachment; filename=\""
                             + new String("readme.txt".getBytes("UTF-8"),"ISO-8859-1") + "\"");   //read in 'utf-8', package in 'iso-8859-1'
             out = response.getOutputStream();
             is = new FileInputStream(PathUtil.getWebPath() + "ERRINFO" + "/" + "readme.txt");
             byte[] b = new byte[2048];
             int len;
             while ((len = is.read(b)) != -1)
                 out.write(b, 0, len);
    	 }finally{
    		 if(is != null){
    			 is.close();
    		 }
    		 if(out != null){
    			 out.close();
    		 }
    	 }
	}
	
	/**
	 * take catch
	 * set response head info
	 * @param response
	 * @throws IOException
	 */
    private void takeCatch(FileNotFoundException e,HttpServletResponse response,OutputStream out)throws IOException{
        response.setContentType("text/html;charset=UFT-8");
        response.setHeader("Cache-Control", "no-cache");
        out = response.getOutputStream();
        out.write("File not find!".getBytes());   //write string 'File not find!' to client
        e.printStackTrace();
    }
}
