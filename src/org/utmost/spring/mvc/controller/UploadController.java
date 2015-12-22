package org.utmost.spring.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.utmost.util.DateUtil;
import org.utmost.util.PathUtil;

public class UploadController extends MultiActionController {
	private static Logger logger = Logger.getLogger(DownloadController.class);

	// limit file uploaded size
	private int maxPostSize = 20* 1024 * 1024;

	public void singleFileUpload(HttpServletRequest request, HttpServletResponse response)
			throws  FileUploadException {
		// save file to server
		response.setContentType("text/html; charset=UTF-8");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		String fileName = request.getParameter("fileName");
		factory.setSizeThreshold(4096);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxPostSize);		//limit file uploaded size
		try {
			List fileItems = upload.parseRequest(request);	//get list from client form data
			Iterator iter = fileItems.iterator();
			String path = PathUtil.getUploadPath() + "/";
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {		// if isn't form field, upload file
					try {
						//if deliver original file, delete old file
//						String fieldName = item.getFieldName();
//						if (fieldName != null && !fieldName.equals("")) {
//							File oldFile = new File(path+fieldName);
//							if (oldFile.exists()) {
//								oldFile.delete();
//							}
//						}
						//upload new file
						String newName =path+ fileName;
						item.write(new File(newName));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

	/**
	 * publish data work flow uploaded to work flow
	 * 
	 * @param rq
	 * @param rp
	 * @throws FileUploadException
	 */
	// public void deployJBPMProc(HttpServletRequest rq, HttpServletResponse rp)
	// throws FileUploadException {
	//
	// JBPMService jbpmS = (JBPMService) SpringContext.getBean("JBPMService");
	// List<FileItem> items = getFileUpload().parseRequest(rq);
	// for (FileItem item : items) {
	// if (!item.isFormField()) {
	// try {
	// jbpmS.deployProcDef(item.getInputStream());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }
	/**
	 * 
	 * @param rq
	 * @param rp
	 * @throws FileUploadException
	 */
	public void upload(HttpServletRequest rq, HttpServletResponse rp)
			throws FileUploadException {
		String path = PathUtil.getUploadPath();
		try {
			logger.debug("+++++++"
					+ java.net.URLDecoder.decode(rq.getParameter("fileName"),
							"UTF8"));
		} catch (UnsupportedEncodingException e1) {

			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		List<FileItem> items = getFileUpload().parseRequest(rq);
		for (FileItem item : items) {
			if (!item.isFormField()) {
				File fi = null;
				try {
					fi = new File(path
							+ File.separator
							+ java.net.URLDecoder.decode(rq
									.getParameter("fileName"), "UTF8"));
				} catch (UnsupportedEncodingException e1) {

					// TODO Auto-generated catch block
					e1.printStackTrace();

				}
				try {
					item.write(fi);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * upload And Return Collection
	 * 
	 * @param rq
	 * @param rp
	 * @throws FileUploadException
	 * @throws IOException
	 * @throws Exception
	 */
	public void uploadARC(HttpServletRequest rq, HttpServletResponse rp)
			throws Exception {
		String path = PathUtil.getUploadPath() + "/upload";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		List<FileItem> items = getFileUpload().parseRequest(rq);

		for (FileItem item : items) {
			if (!item.isFormField()) {
				File fi = new File(path + File.separator + item.getName());
				try {
					item.write(fi);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// HashMap map = new HashMap();
		// map.put("1", "22");
		// map.put("33", "42");
		// rlist.add(map);
		// map = new HashMap();
		// map.put("15", "22");
		// map.put("33", "42");
		// rlist.add(map);
		PrintWriter pw = rp.getWriter();
		// JSONArray json = JSONArray.fromObject(rlist);
		// System.out.println("--" + json.toString());
		pw.write("hello test");
		pw.flush();
	}

	/**
	 * 
	 * @return
	 */
	public ServletFileUpload getFileUpload() {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8"); // set encoding 'utf-8', prevent Chinese messy code
		return upload;
	}

	/**
	 * Construct file name unified
	 * 
	 * @param rq
	 * @param rp
	 * @throws FileUploadException
	 */
	public void uploadVGC(HttpServletRequest rq, HttpServletResponse rp)
			throws FileUploadException {
		String path = PathUtil.getRootPath() + "/upload";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		// String first_dir = rq.getParameter("main_func_name");
		String first_dir = "main_func";
		List<FileItem> items = getFileUpload().parseRequest(rq);
		for (FileItem item : items) {
			if (!item.isFormField()) {
				// File fi = new File(path + this.buildFileName(item, rq,
				// "一级目录"));
				File fi = new File(path
						+ this.buildFileName(item, rq, first_dir));
				try {
					item.write(fi);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * build file name
	 * @param item
	 * @param rq
	 * @param first_dir
	 * @return
	 */
	private String buildFileName(FileItem item, HttpServletRequest rq,
			String first_dir) {
		String itemStr = null;
		String fileName = null;
		StringBuilder sb = new StringBuilder();
		//
		sb.append("\\VGC_");
		// obtain project name
		sb.append(rq.getContextPath().substring(1)).append("_");
		String fileType = null;
		if (first_dir != null) {
			sb.append(first_dir).append("_");
		}
		if (item != null) {
			itemStr = item.getName();
		}
		// obtain file name
		if(itemStr!=null){
			fileName = itemStr.substring(itemStr.lastIndexOf(File.separator) + 1,
					itemStr.lastIndexOf("."));
			// obtain type of file
			fileType = itemStr.substring(itemStr.lastIndexOf(".") + 1);
	
			if (fileName.length() >= 50) {
				sb.append(fileName.substring(0, 50));
			} else {
				sb.append(fileName).append("_");
			}
	          
			String dateStr2 = DateUtil.getNowDateForFN();
			sb.append(dateStr2);
			sb.append(".");
			sb.append(fileType);
		}
		return sb.toString();
	}

}
