/**
 * 
 */
package com.vgc.databank.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * @author BULL
 *
 */
public final class ExcelStyle {
	
	public static class constant {
		/**
		 * This style for first summary title style
		 */
		public final static String SURMARY_TITLE_STYLE_00 ="00"; 
		
		/**
		 * This style for second summary title style
		 */
		public final static String SURMARY_TITLE_STYLE_01 ="01"; 
		
		/**
		 * This style for selected item style
		 */
		public final static String ITEM_TITLE_STYLE_02 = "02";
		
		/**
		 * This style for the first row and not last column of selected item style
		 */
		public final static String ITEM_TITLE_STYLE_0201 = "02_01";
		
		/**
		 * This style for the first row and the last column of selected item style
		 */
		public final static String ITEM_TITLE_STYLE_0202 = "02_02";
		
		/**
		 * This style for not the first row but the last column of selected item style
		 */
		public final static String ITEM_TITLE_STYLE_0203 = "02_03";
		
		/**
		 * This style for data title but not included the last column.
		 */
		public final static String DATA_TITLE_STYLE_03 ="03";
		
		/**
		 * This style for the last column of the data title.
		 */
		public final static String DATA_TITLE_STYLE_0301 ="03_01";
		
		/**
		 * This style for the common style of data column.
		 */
		public final static String DATA_STYLE_04 ="04";
		
		/**
		 *  This style for the last row style of data column.
		 */
		public final static String DATA_STYLE_0401 ="04_01";
		
		/**
		 *  This style for the last column style of data column.
		 */
		public final static String DATA_STYLE_0402 ="04_02";
		
		/**
		 * This style for the name and date.
		 */
		public final static String NAME_DATE_STYLE_05 ="05";
	}
	/**
	 * Get excel style
	 * 
	 * @param styleCode
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle getStyle(String styleCode,HSSFWorkbook wb,String fontName){
		HSSFCellStyle style = null;
		 HSSFFont font = null;
		 HSSFPalette palette = wb.getCustomPalette(); 
		if ("00".equals(styleCode)) {
			palette.setColorAtIndex(HSSFColor.YELLOW.index, (byte) 228,
					(byte) 237, (byte) 211);
			style = ExcelStyle.getSpecialStyle(wb,
					HSSFColor.YELLOW.index, HSSFCellStyle.SOLID_FOREGROUND,
					HSSFCellStyle.BORDER_DOUBLE, HSSFCellStyle.BORDER_THIN,
					HSSFCellStyle.BORDER_MEDIUM, HSSFCellStyle.BORDER_MEDIUM,
					HSSFColor.GREEN.index);
			font = ExcelStyle.getFont(wb, fontName, (short) 11,
					HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font);
		}else if("01".equals(styleCode)){
			style = ExcelStyle.getSpecialStyle(wb,
					HSSFColor.YELLOW.index, HSSFCellStyle.SOLID_FOREGROUND,
					HSSFCellStyle.BORDER_MEDIUM, HSSFCellStyle.BORDER_THIN,
					HSSFCellStyle.BORDER_MEDIUM, HSSFCellStyle.BORDER_DOUBLE,
					HSSFColor.GREEN.index);
			font = ExcelStyle.getFont(wb, fontName, (short) 11,
					HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font);
		}else if("02".equals(styleCode)){
			style = ExcelStyle.getSpecialStyle(wb,
					HSSFColor.WHITE.index, HSSFCellStyle.SOLID_FOREGROUND,
					HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
					HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
					HSSFColor.BLACK.index);
			font = ExcelStyle.getFont(wb, fontName, (short) 10,
					HSSFFont.BOLDWEIGHT_NORMAL);
			style.setFont(font);
		}else if("02_01".equals(styleCode)){
			style = ExcelStyle.getSpecialStyle(wb,
					HSSFColor.WHITE.index, HSSFCellStyle.SOLID_FOREGROUND,
					HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
					HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM,
					HSSFColor.BLACK.index);
			style.setTopBorderColor(HSSFColor.GREEN.index);
			font = ExcelStyle.getFont(wb, fontName, (short) 10,
					HSSFFont.BOLDWEIGHT_NORMAL);
			style.setFont(font);
		}else if("02_02".equals(styleCode)){
			style = ExcelStyle.getSpecialStyle(wb,
					HSSFColor.WHITE.index, HSSFCellStyle.SOLID_FOREGROUND,
					HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
					HSSFCellStyle.BORDER_MEDIUM, HSSFCellStyle.BORDER_MEDIUM,
					HSSFColor.BLACK.index);
			style.setTopBorderColor(HSSFColor.GREEN.index);
			style.setRightBorderColor(HSSFColor.GREEN.index);
			font = ExcelStyle.getFont(wb, fontName, (short) 10,
					HSSFFont.BOLDWEIGHT_NORMAL);
			style.setFont(font);
		}else if("02_03".equals(styleCode)){
			style = ExcelStyle.getSpecialStyle(wb,
					HSSFColor.WHITE.index, HSSFCellStyle.SOLID_FOREGROUND,
					HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
					HSSFCellStyle.BORDER_MEDIUM, HSSFCellStyle.BORDER_THIN,
					HSSFColor.BLACK.index);
			style.setRightBorderColor(HSSFColor.GREEN.index);
			font = ExcelStyle.getFont(wb, fontName, (short) 10,
					HSSFFont.BOLDWEIGHT_NORMAL);
			style.setFont(font);
		}else if("03".equals(styleCode)){
			style = ExcelStyle.getSpecialStyle(wb,
					HSSFColor.YELLOW.index, HSSFCellStyle.SOLID_FOREGROUND,
					HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
					HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_DOUBLE,
					HSSFColor.GREEN.index);
			style.setLeftBorderColor(HSSFColor.BLACK.index);
			style.setBottomBorderColor(HSSFColor.BLACK.index);
			font = ExcelStyle.getFont(wb, fontName, (short) 8,
					HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font);
		}else if("03_01".equals(styleCode)){
			style = ExcelStyle.getSpecialStyle(wb,
					HSSFColor.YELLOW.index, HSSFCellStyle.SOLID_FOREGROUND,
					HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
					HSSFCellStyle.BORDER_MEDIUM, HSSFCellStyle.BORDER_DOUBLE,
					HSSFColor.GREEN.index);
			style.setLeftBorderColor(HSSFColor.BLACK.index);
			style.setBottomBorderColor(HSSFColor.BLACK.index);
			font = ExcelStyle.getFont(wb, fontName, (short) 8,
					HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font);
		}else if("04".equals(styleCode)){
			style = ExcelStyle.getSpecialStyle(wb,
					HSSFColor.WHITE.index, HSSFCellStyle.SOLID_FOREGROUND,
					HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
					HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
					HSSFColor.BLACK.index);
			font = ExcelStyle.getFont(wb, fontName, (short) 9,
					HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font);
		}else if("04_01".equals(styleCode)){
			style = ExcelStyle.getSpecialStyle(wb,
					HSSFColor.WHITE.index, HSSFCellStyle.SOLID_FOREGROUND,
					HSSFCellStyle.BORDER_MEDIUM, HSSFCellStyle.BORDER_THIN,
					HSSFCellStyle.BORDER_MEDIUM, HSSFCellStyle.BORDER_THIN,
					HSSFColor.BLACK.index);
			style.setRightBorderColor(HSSFColor.GREEN.index);
			style.setBottomBorderColor(HSSFColor.GREEN.index);
		}else if("04_02".equals(styleCode)){
			style = ExcelStyle.getSpecialStyle(wb,
					HSSFColor.WHITE.index, HSSFCellStyle.SOLID_FOREGROUND,
					HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
					HSSFCellStyle.BORDER_MEDIUM, HSSFCellStyle.BORDER_THIN,
					HSSFColor.BLACK.index);
			style.setRightBorderColor(HSSFColor.GREEN.index);
			font = ExcelStyle.getFont(wb, fontName, (short) 9,
					HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font);
		}else if("05".equals(styleCode)){
			style = ExcelStyle.getSpecialStyle(wb,
					HSSFColor.WHITE.index, HSSFCellStyle.SOLID_FOREGROUND,
					HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
					HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
					HSSFColor.GREEN.index);
			font = ExcelStyle.getFont(wb, fontName, (short) 10,
					HSSFFont.BOLDWEIGHT_NORMAL);
			font.setColor(HSSFColor.BLACK.index);
			style.setFont(font);
		}
		return style;
	}
	/**
	 * obtain special style
	 * @param wb
	 * @param bg
	 * @param fp
	 * @param bb
	 * @param bl
	 * @param br
	 * @param bt
	 * @param bc
	 * @return
	 */
	private static HSSFCellStyle getSpecialStyle(HSSFWorkbook wb,short bg, short fp, short bb,short bl,short br,short bt,short bc){
		HSSFCellStyle style = wb.createCellStyle();  
        style.setFillForegroundColor(bg);  
        style.setFillPattern(fp);  
        style.setBorderBottom(bb);  
        style.setBorderLeft(bl);  
        style.setBorderRight(br);  
        style.setBorderTop(bt);  
        style.setBottomBorderColor(bc);
        style.setRightBorderColor(bc);
        style.setLeftBorderColor(bc);
        style.setTopBorderColor(bc);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        style.setWrapText(true);
        return style;
	}
	/**
	 * obtain font
	 * @param wb
	 * @param fontName
	 * @param fontHeight
	 * @param boldWeight
	 * @return
	 */
	private static HSSFFont getFont(HSSFWorkbook wb,String fontName,short fontHeight, short boldWeight){
		HSSFFont font = wb.createFont();  
		HSSFPalette palette = wb.getCustomPalette();
		font.setFontName(fontName);
        palette.setColorAtIndex(HSSFColor.DARK_BLUE.index, (byte) 0, (byte)50, (byte) 100);
        font.setColor(HSSFColor.DARK_BLUE.index);  
        font.setFontHeightInPoints(fontHeight);  
        font.setBoldweight(boldWeight); 
        return font;
	}
	
	
	/**
	  * set merge cell styles 
	  * @param sheet
	  * @param region
	  * @param style
	  */
	 public static void setRegionStyle(HSSFSheet sheet, CellRangeAddress region,HSSFCellStyle style, short rowHeight) {
	  for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {
	   HSSFRow row = sheet.getRow(i);
	   if(row == null){
		   row = sheet.createRow(i);
		   row.setHeight(rowHeight);
	   }
	   for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
	    HSSFCell cell = row.getCell(j);
	    if(cell == null){
	    	cell = row.createCell(j);
	    }
	    cell.setCellStyle(style);
	   }
	  }
	 }
}
