/**
 * 
 */
package com.vgc.databank.service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.utmost.portal.service.AutoService;
import org.utmost.util.PathUtil;

import com.vgc.databank.model.ExcelModel;
import com.vgc.databank.util.GenerateExcelUtil;
import com.vgc.databank.util.ReadExcelConfUtil;

/**
 * 
 * @description Generate Excel Service
 * @author  
 * @version 3.2.0
 * @date Jan 29, 2015 10:48:06 AM
 */
@Service("GenerateExcelService")
public class GenerateExcelService {


	private static Logger logger = Logger.getLogger(GenerateExcelService.class);

	@Autowired
	private GenerateExcelUtil generateExcelUtil;
	
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private AutoService autoService;
	/**
	 * handle error return this var
	 */
	private final static String SERVER_ERROR="Error";
	/**
	 * return when search result screen has not result items
	 */
	private final static String NO_DATA = "NoData";
	/**
	 * prefix name of the excel
	 */
	private final static String EXCEL_PREFIX = "CATOnline_Standard_Report";
	/**
	 * excel save directory
	 */
	private final static String EXCEL_DIRECTORY ="ExportExcel";
	/**
	 * excel extension
	 */
	private static final String XLS_SUFFIX = ".xls";
	
	/**
	 * Generate excel report 
	 * 
	 * @param listData
	 * @param selectedItems
	 * @param lan
	 * @param hql
	 * @param issuedId
	 * @param standardId
	 * @param userUUId
	 * 
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String generatedExcel(Object listData,Object selectedItems,String lan,String hql,String issuedId,String standardId,String userUUId){
		String filePath = null;
		try {
			String path = PathUtil.getFileSaveRoot()+EXCEL_DIRECTORY;
			checkDirAndDelOtherFile(path);
			List<HashMap> dataMap = (List<HashMap>) listData;
			if(dataMap == null || dataMap.isEmpty()){
				/**
				 * When listData is null, means exported all.
				 */
				dataMap = searchService.advancedSearch(issuedId, standardId, hql, userUUId, true, 1);
				if(dataMap == null || dataMap.isEmpty()){
					return NO_DATA;   //no data for generating excel
				}
			}
			List<String> selectedItemList = (List<String>) selectedItems;
			//get user name
			List<HashMap> users = autoService.findByHql("from U_PORTAL_USER u where lower(u.uuid) = '" + userUUId + "'");
			String userName = "";
			if(users!=null){
				HashMap<String, String> userMap = users.get(0);
				userName = (String) userMap.get("username");
			}
			//change excel path according to the client requirement
			//filePath = path +"\\" + issuedId+"_"+ new Date().getTime()+XLS_SUFFIX;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
			filePath = path +"\\" + EXCEL_PREFIX+"_"+ df.format(new Date())+XLS_SUFFIX;
			ExcelModel excelModel = ReadExcelConfUtil.readConfig(issuedId,lan);
			generateExcelUtil.exportExcel(excelModel, selectedItemList,dataMap, filePath,userName);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return SERVER_ERROR;
		}
		return filePath;
	}
	

    /**
     * check the directory is existed or not , if existed then delete the other files before generated the new file.
     * if not, create a new directory. 
     * 
     * @param path
     */
	private void checkDirAndDelOtherFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			boolean mkDirsFlag = file.mkdirs();
			if(!mkDirsFlag){	//file if create success
				logger.error("Create temp directory fail!");
			}
		} else {
			File[] list = file.listFiles();
			boolean delFlag = true;
			for (File f : list) {
				if (f != null) {
					delFlag = f.delete();
					logger.error("Delete file fail : " + f.getName());
					delFlag = true;
					continue;
				}
			}
		}
	}
}