/**
 * 
 */
package com.vgc.databank.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.vgc.databank.model.ExcelModel;
import com.vgc.databank.model.ExcelTitle;

/**
 * @author BULL
 * 
 */

public final class ReadExcelConfUtil {

	/**
	 * Read configuration file get excel display items.
	 * 
	 * @param nodeId
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static ExcelModel readConfig(String nodeId, String lan)
			throws JDOMException, IOException {
		Element sheet = ReadExcelConfUtil.getValidSheet(nodeId, lan);
		ExcelModel excelModel = null;
		if (sheet != null) {
			excelModel = new ExcelModel();
			excelModel.setSumColumnNum(Integer.valueOf(sheet.getChildText("sumColumn")));
			excelModel.setColumnSize(Integer.valueOf(sheet.getChildText("columnSize")));
			excelModel.setSheetName(sheet.getChildText("sheetName"));
			excelModel.setSheetTitle(sheet.getChildText("sheetTitle"));
			excelModel.setSelectedItemTitle(sheet.getChildText("selectedItemTitle"));
			excelModel.setDataSummaryTitle(sheet.getChildText("dataSummaryTitle"));
			excelModel.setDefaultFont(sheet.getChildText("defaultFont"));
			List<Element> titleList = sheet.getChild("titles").getChildren("title");
			List<ExcelTitle> titles = new ArrayList<ExcelTitle>();
			ExcelTitle excelTitle = null;
			for (Element title : titleList) {
				excelTitle = new ExcelTitle();
				excelTitle.setTitleKey(title.getChildText("key"));
				excelTitle.setTitleDesc(title.getChildText("desc"));
				excelTitle.setHoldColumnNum(Integer.valueOf(title.getChildText("column")));
				titles.add(excelTitle);
			}
			excelModel.setTitles(titles);
		}
		return excelModel;
	}

	/**
	 * Get valid sheet defined
	 * 
	 * @param nodeId
	 * @param lan
	 * @param doc
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 */
	private static Element getValidSheet(String nodeId, String lan)
			throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(ReadExcelConfUtil.class.getResourceAsStream("/conf/genExcelConf.xml"));
		Element rootEle = doc.getRootElement();
		List<Element> eleList = rootEle.getChildren("sheet");
		/*
		 * String nodeIds = null; String[] ids = null; for(Element ele:eleList)
		 * { nodeIds = ele.getChildText("nodeId"); ids = nodeIds.split(",");
		 * if(isContain(nodeIds, ids)){ map =
		 * generateMap(ele.getChildText("key-desc")); } }
		 */
		Element sheet = null;
		for (Element ele : eleList) {
			if (lan.equals(ele.getAttributeValue("lan"))) {
				sheet = ele;
				break;
			}
		}
		return sheet;
	}

	/*
	 * private static boolean isContain(String nodeId,String[] ids){ for(String
	 * id : ids ){ if(nodeId.equals(id)){ return true; } } return false;
	 * 
	 * }
	 */
	/*
	 * private static Map<String,String> generateMap(String keyAndDesc){
	 * Map<String,String> map = null; if(keyAndDesc != null &&
	 * keyAndDesc.length()>0){ map = new LinkedHashMap<String,String>();
	 * String[] arrays = keyAndDesc.split(","); String[] str = null; for(String
	 * array :arrays){ str = array.split(":"); map.put(str[0].trim(),
	 * str[1].trim()); } } return map; }
	 */

}
