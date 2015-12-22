/**
 * 
 */
package com.vgc.databank.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Service;
import org.utmost.util.DateUtil;

import com.vgc.databank.model.ExcelModel;
import com.vgc.databank.model.ExcelTitle;
import com.vgc.databank.service.GenerateExcelService;

/**
 * generate excel util
 * @author BULL
 *
 */
@Service("GenerateExcelUtil")
public class GenerateExcelUtil {
	/**
	 * data start row
	 */
	private final static int STARTDATAROW = 3;
	/**
	 * default line height
	 */
	public final static short DEFAULT_DATA_HEIGHT =600;
	/**
	 * one column font max value
	 */
	public final static int ONE_COLUMN_FONT_MAXNUM = 10;
	/**
	 * merge two column font max value
	 */
	public final static int TWO_COLUMN_FONT_MAXNUM = 35;
	/**
	 * sheet max row number
	 */
	public final static int SHEET_MAX_ROW_INDEX = 65535;
	/**
	 * 
	 */
	public final static int TITLE_ROW_NUM = 2;
	
	public final static int NAME_DATE_ROW_NUM =2;
	
	public final static int BLANK_ROW_NUM =2;
	
	/**
	 * Export excel according template
	 * 
	 * @param excelModel
	 * @param selectedItem
	 * @param dataMap
	 * @param filePath
	 * @throws IOException
	 */
	public void exportExcel(ExcelModel excelModel,List<String> selectedItem,
			List<HashMap> dataMap, String filePath,String userName)
			throws IOException {
    	OutputStream out = new FileOutputStream(filePath);
    	/**
    	 * Defined a work book with footer and header According template.
    	 */
        HSSFWorkbook wb =  this.readExcelTemplate(); 
        int sheetIndex = 0;
        int delRowIndex = -1;
        while(dataMap != null && !dataMap.isEmpty()){
        	delRowIndex = exportedToNewSheet(wb, excelModel, selectedItem, dataMap, sheetIndex, delRowIndex,userName);
        	sheetIndex ++;
        }
        wb.write(out);  
    }  
	
    /**
     * Exported data to a new sheet.
     * 
     * @param wb
     * @param excelModel
     * @param selectedItem
     * @param dataMap
     * @param sheetIndex
     * @param delRowIndex
     * @return
     * @throws IOException
     */
	private int exportedToNewSheet( HSSFWorkbook wb,ExcelModel excelModel,List<String> selectedItem,
			List<HashMap> dataMap, int sheetIndex,int delRowIndex,String userName) throws IOException{
	        int sumRow = STARTDATAROW;
	        HSSFSheet sheet = null;
	        if(sheetIndex == 0){
	        	sheet = setSheetNameAndTitle(wb, excelModel);
	            /**
		         * add selected item
		         */
		        sumRow = addSelectedItem(wb,sheet,selectedItem, excelModel, sumRow);
		        delRowIndex = sumRow;
	        }else{
	        	sheet = wb.cloneSheet(0);
	        	/**
	        	 * delete data row
	        	 */
	        	ExcelCellUtil.removeRow(sheet, delRowIndex,SHEET_MAX_ROW_INDEX -1);
	        	/**
	        	 * split merged cells of the summary data title
	        	 */
	        	ExcelCellUtil.unMergedCell(sheet, delRowIndex, 0);
	        	sumRow = delRowIndex;
	        }
	        
	        List<HashMap> newDataMap = getSheetDataMap(dataMap, sumRow+TITLE_ROW_NUM+NAME_DATE_ROW_NUM+BLANK_ROW_NUM);
	        
	        /**
	         * add data title
	         */
	        sumRow = setDataTitle(wb,sheet, excelModel, sumRow);

	        /**
	         * add data value
	         */
	        sumRow = setDataValue(wb,sheet, excelModel, newDataMap, sumRow);
	        /**
	         * add name and date
	         */
	        addNameAndDate(wb,sheet,excelModel, sumRow,userName);
	        
		    return delRowIndex;
	}
	
	/**
	 * Get dataMap of current sheet
	 *  
	 * @param dataMap
	 * @param sumFixRow
	 * @return
	 */
	private List<HashMap> getSheetDataMap(List<HashMap> dataMap, int sumFixRow){
        List<HashMap> newDataMap = new ArrayList<HashMap>();
        if(dataMap.size() > SHEET_MAX_ROW_INDEX -sumFixRow +1){
        	newDataMap = new ArrayList<HashMap>();
        	newDataMap.addAll(dataMap.subList(0, SHEET_MAX_ROW_INDEX -sumFixRow));
        	dataMap.removeAll(newDataMap);
        	
        }else{
        	newDataMap.addAll(dataMap);
        	dataMap.clear();
        }
        return newDataMap;
	}
	
	/**
	 * Set sheet name and title
	 * 
	 * @param wb
	 * @param excelModel
	 */
	private HSSFSheet setSheetNameAndTitle(HSSFWorkbook wb,ExcelModel excelModel){
		wb.setSheetName(0,excelModel.getSheetName());
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRichTextString val =  new HSSFRichTextString(excelModel.getSheetTitle()); 
		sheet.getRow(0).getCell(0).setCellValue(val);
		return sheet;
	}

	/**
	 * Add name and date
	 * @param wb
	 * @param excelModel
	 * @param startRowIndex
	 */
	private void addNameAndDate(HSSFWorkbook wb,HSSFSheet sheet,ExcelModel excelModel,int startRowIndex,String userName){
		int rowIndex= startRowIndex; 
		HSSFCellStyle style05 = ExcelStyle.getStyle(ExcelStyle.constant.NAME_DATE_STYLE_05, wb,excelModel.getDefaultFont());
		/**
		 * add name
		 */
		int sumColumn = excelModel.getSumColumnNum();
		this.setNameOrDate(sheet, rowIndex, sumColumn, style05, "Name",userName);
		/**
		 * add date
		 */
		 ++ rowIndex;
		String dateValue = DateUtil.getDateFormat("yyyy-MM-dd").format(new Date());
		this.setNameOrDate(sheet, rowIndex, sumColumn, style05, "Date",dateValue);
	}
	
	/**
	 * Set name Or date
	 * 
	 * @param sheet
	 * @param rowIndex
	 * @param sumColumn
	 * @param style
	 * @param value
	 */
	private void setNameOrDate(HSSFSheet sheet , int rowIndex, int sumColumn, HSSFCellStyle style,String value,String mergeValue){
		HSSFRow row = sheet.createRow(rowIndex);
		ExcelCellUtil.unMergedCell(sheet, rowIndex, sumColumn-3);
		ExcelCellUtil.createCell(row, sumColumn-3, style, value);
		if(mergeValue != null){
			ExcelCellUtil.createCell(row, sumColumn-2, style, mergeValue);
		}
		ExcelCellUtil.mergerCell(sheet, style, rowIndex, rowIndex, sumColumn-2, sumColumn-1,row.getHeight());
	}
	
	/**
	 * Set data value
	 * 
	 * @param wb
	 * @param excelModel
	 * @param dataMap
	 * @param startRowIndex
	 * @return
	 */
	private int setDataValue(HSSFWorkbook wb,HSSFSheet sheet,ExcelModel excelModel,List<HashMap> dataMap,int startRowIndex){
		int rowIndex= startRowIndex; 
		HSSFCellStyle style01 = ExcelStyle.getStyle(ExcelStyle.constant.SURMARY_TITLE_STYLE_01, wb,excelModel.getDefaultFont());
		HSSFCellStyle dataStyle = ExcelStyle.getStyle(ExcelStyle.constant.DATA_STYLE_04, wb,excelModel.getDefaultFont());
		HSSFCellStyle dataStyle0401 = ExcelStyle.getStyle(ExcelStyle.constant.DATA_STYLE_0401, wb,excelModel.getDefaultFont());
		HSSFCellStyle dataStyle0402 = ExcelStyle.getStyle(ExcelStyle.constant.DATA_STYLE_0402, wb,excelModel.getDefaultFont());
		
		int occupyColumn = 1;
		HSSFRow row = null;
		ExcelTitle title = null;
		List<ExcelTitle> titles = excelModel.getTitles();
		if(dataMap!=null){
	        for(HashMap<String,String> map:dataMap){
	        	row = sheet.createRow(rowIndex);  
	        	row.setHeight(DEFAULT_DATA_HEIGHT);
	        	occupyColumn = 1;
				 for(int i =0; i< titles.size();i++){
					title = titles.get(i);
					ExcelCellUtil.createCell(row, occupyColumn, dataStyle, (String) map.get(title.getTitleKey()));
					if(title.getHoldColumnNum() > 1){
						ExcelCellUtil.mergerCell(sheet, dataStyle, rowIndex, rowIndex, occupyColumn, occupyColumn+(title.getHoldColumnNum()-1));
						occupyColumn = occupyColumn + title.getHoldColumnNum();
					}else{
						occupyColumn = occupyColumn + 1;
					}
					if(occupyColumn > excelModel.getSumColumnNum()){
						break;
					}
				}
				/**
				 * set last column style.
				 */
				row.getCell(excelModel.getSumColumnNum() - 1).setCellStyle(dataStyle0402);
				 rowIndex ++;
			}
		}
	
        /**
         * merge data summary title 
         */
        int defaultExtendsSize = startRowIndex + (dataMap==null?0:dataMap.size()) +2;
        
        ExcelCellUtil.mergerCell(sheet, style01, startRowIndex-1, defaultExtendsSize -1 , 0, 0);
		/**
		 * merge blank data area,set last row style of data
		 */
        ExcelCellUtil.mergerCell(sheet, dataStyle0401, rowIndex, defaultExtendsSize-1, 1, excelModel.getSumColumnNum()-1);
		 
		return defaultExtendsSize;
	}
	/**
	 * Set data title.
	 * @param wb
	 * @param excelModel
	 * @param sumRow
	 * @return
	 */
	private int setDataTitle(HSSFWorkbook wb,HSSFSheet sheet,ExcelModel excelModel,int startRowIndex){
		HSSFCellStyle style01 = ExcelStyle.getStyle(ExcelStyle.constant.SURMARY_TITLE_STYLE_01,wb,excelModel.getDefaultFont());
		HSSFCellStyle titleStyle = ExcelStyle.getStyle(ExcelStyle.constant.DATA_TITLE_STYLE_03, wb,excelModel.getDefaultFont());
		HSSFCellStyle titleStyle0301 = ExcelStyle.getStyle(ExcelStyle.constant.DATA_TITLE_STYLE_0301, wb,excelModel.getDefaultFont());
	    /**
	     * Add Data item title
	     */
		HSSFRow row = sheet.createRow(startRowIndex);
		row.setHeight(DEFAULT_DATA_HEIGHT);
		ExcelCellUtil.createCell(row, 0, style01, excelModel.getDataSummaryTitle());
		
		List<ExcelTitle> titles = excelModel.getTitles();
		int occupyColumn = 1;
		for(ExcelTitle t : titles){
			ExcelCellUtil.createCell(row, occupyColumn, titleStyle, t.getTitleDesc());
			if(t.getHoldColumnNum()>1){
				ExcelCellUtil.mergerCell(sheet, titleStyle, startRowIndex, startRowIndex, occupyColumn, occupyColumn+(t.getHoldColumnNum()-1));
				occupyColumn = occupyColumn + t.getHoldColumnNum();
			}else{
				occupyColumn = occupyColumn + 1;
			}
			
			if(occupyColumn >= excelModel.getSumColumnNum()){
				break;
			}
		}
		/**
		 * set last column style.
		 */
		row.getCell(excelModel.getSumColumnNum()-1).setCellStyle(titleStyle0301);
		return startRowIndex + 1;
	}
	
	
	/**
	 * Add selected item
	 * @param wb
	 * @param selectedItem
	 * @param excelModel
	 * @param sumRow
	 * @return
	 * @throws IOException
	 */
	private int addSelectedItem(HSSFWorkbook wb,HSSFSheet sheet,List<String> selectedItem,ExcelModel excelModel, int startRowIndex) throws IOException{
		int rowIndex= startRowIndex;
		HSSFCellStyle sumTitleStyle = ExcelStyle.getStyle(ExcelStyle.constant.SURMARY_TITLE_STYLE_00, wb,excelModel.getDefaultFont());
		HSSFCellStyle itemStyle02  = ExcelStyle.getStyle(ExcelStyle.constant.ITEM_TITLE_STYLE_02, wb,excelModel.getDefaultFont());
		HSSFCellStyle itemStyle0201 = ExcelStyle.getStyle(ExcelStyle.constant.ITEM_TITLE_STYLE_0201, wb,excelModel.getDefaultFont());
		HSSFCellStyle itemStyle = null;
	    /**
	     * add selected item title
	     */
		HSSFRow row = sheet.createRow(rowIndex);
		row.setHeight(DEFAULT_DATA_HEIGHT);
		ExcelCellUtil.createCell(row, 0, sumTitleStyle,excelModel.getSelectedItemTitle());
		
		/**
		 * add selected item.
		 */
		if(selectedItem != null && selectedItem.size()>0){
			int occupyColumn = 1;
			String item = null;
			for(int i=0;i<selectedItem.size();){
				item = selectedItem.get(i);
				if(item==null){
					i++;
					continue;
				}
				/**
				 * if a row columns have been filled, create a new row.
				 */
				if(occupyColumn >= excelModel.getSumColumnNum()){
					++rowIndex;
					row = sheet.createRow(rowIndex);
					row.setHeight(DEFAULT_DATA_HEIGHT);
					occupyColumn = 1;
				}
				/**
				 * set style for first row or other row.
				 */
				if(rowIndex == startRowIndex){
					itemStyle = itemStyle0201;
				}else{
					itemStyle = itemStyle02;
				}
				/**
				 * set value
				 */
				ExcelCellUtil.createCell(row, occupyColumn, itemStyle, item);
				
				if(item!=null&&item.length()<=ONE_COLUMN_FONT_MAXNUM && occupyColumn+1 <= excelModel.getSumColumnNum()){
					/**
					 * No need to merge cells for display. occupy one column. 
					 */
					occupyColumn = occupyColumn + 1;
				}else if(item.length()<TWO_COLUMN_FONT_MAXNUM && occupyColumn+2 <= excelModel.getSumColumnNum()){
					/**
					 * Merge two cells,occupy two columns. 
					 */
					ExcelCellUtil.mergerCell(sheet, itemStyle, rowIndex, rowIndex, occupyColumn, occupyColumn + 1);
					occupyColumn = occupyColumn +2;
				}else if (item.length()>TWO_COLUMN_FONT_MAXNUM && occupyColumn+3 <= excelModel.getSumColumnNum()){
				   /**
				    * Merge three cells,occupy three columns. 
				    */
					ExcelCellUtil.mergerCell(sheet, itemStyle, rowIndex, rowIndex, occupyColumn, occupyColumn + 2);
					occupyColumn = occupyColumn +3;
				}else{
					/**
					 * if the left columns not fit the current item,then merge the left columns with the previous columns 
					 */
					ExcelCellUtil.mergerCell(sheet, itemStyle, rowIndex, rowIndex, occupyColumn-1, excelModel.getSumColumnNum()-1);
					occupyColumn = excelModel.getSumColumnNum();
					setLastCellForSelectedItem(wb, sheet, occupyColumn, excelModel, rowIndex, startRowIndex);
					continue;
				}
                setLastCellForSelectedItem(wb, sheet, occupyColumn, excelModel, rowIndex, startRowIndex);
				i++;
			}
			/**
			 * if the selected item has been filled, merge the left columns.
			 */
			if(occupyColumn<excelModel.getSumColumnNum()){
				ExcelCellUtil.mergerCell(sheet, itemStyle, rowIndex, rowIndex, occupyColumn, excelModel.getSumColumnNum()-1);
					occupyColumn = excelModel.getSumColumnNum();
					setLastCellForSelectedItem(wb, sheet, occupyColumn, excelModel, rowIndex, startRowIndex);
			}
		}
		
		// Merge cells for Row title;
		ExcelCellUtil.mergerCell(sheet, sumTitleStyle, startRowIndex, rowIndex, 0, 0);
		return rowIndex + 1;
	}
	
	/**
	 * Set Last Cell For Selected Item
	 * @param wb
	 * @param sheet
	 * @param occupyColumn
	 * @param excelModel
	 * @param rowIndex
	 * @param startRowIndex
	 */
	private void setLastCellForSelectedItem(HSSFWorkbook wb, HSSFSheet sheet,
			int occupyColumn, ExcelModel excelModel, int rowIndex,int startRowIndex) {
		HSSFCellStyle itemStyle0202 = ExcelStyle.getStyle(
				ExcelStyle.constant.ITEM_TITLE_STYLE_0202, wb,excelModel.getDefaultFont());
		HSSFCellStyle itemStyle0203 = ExcelStyle.getStyle(
				ExcelStyle.constant.ITEM_TITLE_STYLE_0203, wb,excelModel.getDefaultFont());
		/**
		 * set style for last column
		 */
		if (occupyColumn >= excelModel.getSumColumnNum()) {
			HSSFCellStyle style = null;
			if (rowIndex == startRowIndex) {
				style = itemStyle0202;
			} else {
				style = itemStyle0203;
			}
			sheet.getRow(rowIndex).getCell(excelModel.getSumColumnNum() - 1)
					.setCellStyle(style);
		}
	}
	
	 /**
	  * Read excel template
	  * @return
	  * @throws IOException
	  */
	private HSSFWorkbook readExcelTemplate() throws IOException{
		InputStream fos =  GenerateExcelService.class.getResourceAsStream("/conf/temlate.xls");        
		POIFSFileSystem fs = new POIFSFileSystem(fos);   
		HSSFWorkbook wb = new HSSFWorkbook(fs);  
		return wb;
	}
}
