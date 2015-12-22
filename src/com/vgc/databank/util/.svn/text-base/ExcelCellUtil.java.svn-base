/**
 * 
 */
package com.vgc.databank.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.CellRangeAddress;

/**
 * @author BULL
 *
 */
public class ExcelCellUtil {
	
	public final static short DEFAULT_DATA_HEIGHT =600;
	
	 /** 
	 * Remove a row by its index 
	 * @param sheet a Excel sheet 
	 * @param rowIndex a 0 based index of removing row 
	 */  
	public static void removeRow(HSSFSheet sheet, int rowIndex,int lastRowNum) {  
	   while(rowIndex<=lastRowNum){  
	       HSSFRow removingRow=sheet.getRow(rowIndex);  
	        if(removingRow!=null){  
	        	sheet.removeRow(removingRow); 
	        }
	        rowIndex ++;
	   }
	} 

	/**
	 * unMerged cell
	 * @param sheet
	 * @param rowIndex
	 * @param colIndex
	 */
	public static void unMergedCell(HSSFSheet sheet, int rowIndex, int colIndex){
	     int num = sheet.getNumMergedRegions();
	     for(int i = 0; i < num; i++) {
	         CellRangeAddress range = sheet.getMergedRegion(i);
	         if(range !=null && range.getFirstColumn() == colIndex && range.getFirstRow() == rowIndex){
	            sheet.removeMergedRegion(i) ;
	            break;
	         }
	     }
	 } 
	
	/**
	 * Create a new cell
	 * 
	 * @param row
	 * @param columnIndex
	 * @param style
	 * @param vlaue
	 * @return
	 */
	public static HSSFCell createCell(HSSFRow row,int columnIndex,HSSFCellStyle style,String value){
		HSSFCell cell = row.createCell(columnIndex);
		if(value != null){
			HSSFRichTextString val =  new HSSFRichTextString(value); 
			cell.setCellValue(val);
		}
		if(style != null){
			cell.setCellStyle(style);
		}
		return cell;
	}
	
	/**
	 * merge cells 
	 * @param sheet
	 * @param style
	 * @param rowFrom
	 * @param rowTo
	 * @param columnFrom
	 * @param columnTo
	 * @return
	 */
	public static HSSFSheet mergerCell(HSSFSheet sheet, HSSFCellStyle style,
			int rowFrom, int rowTo, int columnFrom, int columnTo) {
		return mergerCell(sheet, style, rowFrom, rowTo, columnFrom, columnTo, DEFAULT_DATA_HEIGHT);
	}
	
	/**
	 * Merge cells and when create row with height value
	 * @param sheet
	 * @param style
	 * @param rowFrom
	 * @param rowTo
	 * @param columnFrom
	 * @param columnTo
	 * @param rowHeight
	 * @return
	 */
	public static HSSFSheet mergerCell(HSSFSheet sheet, HSSFCellStyle style,
			int rowFrom, int rowTo, int columnFrom, int columnTo , short rowHeight) {
		CellRangeAddress region = new CellRangeAddress((short) rowFrom,
				(short) rowTo, (short) (columnFrom), (short) (columnTo));
		sheet.addMergedRegion(region);
		ExcelStyle.setRegionStyle(sheet, region, style,rowHeight);
		return sheet;
	}
}
