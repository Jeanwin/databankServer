package org.utmost.report.service;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.springframework.stereotype.Service;
import org.utmost.common.SpringContext;
import org.utmost.portal.service.AutoService;
import org.utmost.util.PathUtil;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

/**
 * Dynamic report service
 * 
 * @author wanglm
 * 
 */
@Service("ReportService")
public class ReportService {
	/**
	 *  Construct JasperPrint object
	 * 
	 * @param reportInfo
	 * @param reportData
	 * @return
	 * @throws ColumnBuilderException
	 * @throws ClassNotFoundException
	 * @throws JRException
	 */
	public JasperPrint buildJasperPrint(HashMap reportInfo,Collection<HashMap> reportData) throws ColumnBuilderException,
			ClassNotFoundException, JRException {
		String title = (String) reportInfo.get("title");//get title
		String subtitle = (String) reportInfo.get("subtitle");//get subtitle
		ArrayList<HashMap> columns = (ArrayList) reportInfo.get("columns");//get columns
		FastReportBuilder drb = new FastReportBuilder();
		drb.setTitle(title);
		drb.setSubtitle(subtitle + " " + new Date());
		// set style
		Style titleStyle = new Style();
		// titleStyle.setFont(new Font(18, Font._FONT_ARIAL, true));
		// titleStyle.setFont(new Font(18,
		// Font.PDF_ENCODING_UniGB_UCS2_H_Chinese_Simplified, true));
		titleStyle.setFont(new Font(18, Font._FONT_ARIAL, "STSong-Light","UniGB-UCS2-H", true));// wanglm
		drb.setTitleStyle(titleStyle);
		drb.setDetailHeight(10);
		drb.setMargins(30, 20, 30, 15);
		drb.setColumnsPerPage(1);
		drb.setUseFullPageWidth(true);
		drb.setPrintColumnNames(true);
		drb.setHeaderHeight(30);
		Integer margin = Integer.valueOf(5);
		drb.setSubtitleHeight(20);
		drb.setDetailHeight(10);
		drb.setLeftMargin(margin);
		drb.setRightMargin(margin);
		drb.setTopMargin(margin);
		drb.setBottomMargin(margin);
		drb.setPrintBackgroundOnOddRows(true);
		Style oddRowStyle = new Style();
		oddRowStyle.setBorder(Border.NO_BORDER);  //set no border
		Color veryLightGrey = new Color(230, 230, 230);
		oddRowStyle.setBackgroundColor(veryLightGrey);
		oddRowStyle.setTransparency(Transparency.OPAQUE);
		drb.setOddRowBackgroundStyle(oddRowStyle);
		drb.setColumnsPerPage(1);
		drb.setColumnSpace(5);

		drb.addAutoText(AutoText.AUTOTEXT_PAGE_X, AutoText.POSITION_FOOTER,AutoText.ALIGMENT_RIGHT);
		// drb.addAutoText(AutoText.AUTOTEXT_CREATED_ON,
		// AutoText.POSITION_FOOTER,
		// AutoText.ALIGMENT_LEFT,AutoText.PATTERN_DATE_DATE_TIME);
		int pageWidth = 0;// wanglm
		int columnWidth = 120;// wanglm
		for (HashMap map : columns) {
			String dataColumnProperty = (String) map.get("dataColumnProperty");//get data column property
			String dataClassTypeName = (String) map.get("dataClassTypeName");//get data class type name
			String dataTitle = (String) map.get("dataTitle");//get data title
			String dataWidth = (String) map.get("dataWidth");//get width
			String isgroup = (String) map.get("isgroup");//get isgroup

			// System.out.println("map:" + map);
			ColumnBuilder cb = ColumnBuilder.getInstance();
			// set style
			Style headerStyle = new Style();
			// headerStyle.setFont(Font.ARIAL_BIG_BOLD);
			// headerStyle.setFont(new Font(18,
			// Font.PDF_ENCODING_UniGB_UCS2_H_Chinese_Simplified, true));
			// titleStyle.setFont(new Font(18,
			// Font._FONT_ARIAL,
			// "STSong-Light", "UniGB-UCS2-H",
			// false));
			headerStyle.setFont(new Font(12, Font._FONT_ARIAL, "STSong-Light","UniGB-UCS2-H", true));
			headerStyle.setBorderBottom(Border.PEN_2_POINT);
			headerStyle.setBorder(Border.THIN);//set border is thin
			headerStyle.setHorizontalAlign(HorizontalAlign.CENTER); //align center
			headerStyle.setVerticalAlign(VerticalAlign.MIDDLE); //align middle
			headerStyle.setBackgroundColor(Color.LIGHT_GRAY);	//set color light gray
			headerStyle.setTextColor(Color.WHITE);		//set color white
			headerStyle.setTransparency(Transparency.OPAQUE);

			cb.setHeaderStyle(headerStyle);

			Style style = new Style();
			style.setFont(new Font(10, Font._FONT_ARIAL, "STSong-Light","UniGB-UCS2-H", true));
			style.setHorizontalAlign(HorizontalAlign.CENTER);  //horizontal align center
			style.setVerticalAlign(VerticalAlign.MIDDLE);	//vertical align middle
			style.setBorderColor(Color.black);		//set color black
			style.setBorder(Border.THIN);	//border is thin
			cb.setStyle(style);

			if (dataTitle != null)
				cb.setTitle(dataTitle);
			if (dataClassTypeName != null)
				cb.setColumnProperty(dataColumnProperty, dataClassTypeName);
			if (dataWidth != null)
				cb.setWidth(Integer.parseInt(dataWidth));
			// cb.setWidth(200);
			AbstractColumn column = cb.build();
			// column.setWidth(Integer.parseInt("200"));
			if (isgroup != null && isgroup.equalsIgnoreCase("true")) {
				GroupBuilder gb = new GroupBuilder();
				DJGroup g = gb.setCriteriaColumn((PropertyColumn) column).setGroupLayout(GroupLayout.DEFAULT).build();
				drb.addGroup(g);
			}
			pageWidth += columnWidth;
			column.setWidth(Integer.parseInt(String.valueOf(columnWidth)));
			column.setFixedWidth(true);
			drb.addColumn(column);
		}
		// set page size		
		Page page = new Page();
		page.setWidth(pageWidth + 11);
		// page.setHeight(800);
		page.setHeight(2000);
		page.setOrientationPortrait(true);
		drb.setPageSizeAndOrientation(page);

		DynamicReport dr = drb.build();
		JRDataSource ds = new JRBeanCollectionDataSource(reportData);
		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr,new ClassicLayoutManager(), ds);

		// JasperViewer.viewReport(jp);
		return jp;
	}

	/**
	 * export Report To Html File
	 * 
	 * @param jp
	 * @param filename
	 * @return
	 * @throws JRException
	 */
	public String exportReportToHtmlFile(JasperPrint jp, String filename) throws JRException {
		JasperExportManager.exportReportToHtmlFile(jp, filename);
		return filename;
	}

	/**
	 * export Report To Pdf File
	 * 
	 * @param jp
	 * @param filename
	 * @return
	 * @throws JRException
	 */
	public String exportReportToPdfFile(JasperPrint jp, String filename) throws JRException {
		JasperExportManager.exportReportToPdfFile(jp, filename);
		return filename;
	}

	/**
	 * export Report To Rtf File
	 * 
	 * @param jp
	 * @param filename
	 * @return
	 * @throws JRException
	 */
	public String exportReportToRtfFile(JasperPrint jp, String filename) throws JRException {
		JRRtfExporter exporter = new JRRtfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filename);
		exporter.exportReport();
		return filename;
	}

	/**
	 * export Report To Xls File
	 * 
	 * @param jp
	 * @param filename
	 * @return
	 * @throws JRException
	 */
	public String exportReportToXlsFile(JasperPrint jp, String filename) throws JRException {
		JRXlsExporter exporterXls = new JRXlsExporter();
		exporterXls.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filename);
		exporterXls.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.TRUE);
		exporterXls.exportReport();
		return filename;
	}
	/**
	 * build report info
	 * @param reportcode
	 * @return
	 */
	public HashMap buildReportInfo(String reportcode) {
		AutoService as = (AutoService) SpringContext.getBean("AutoService");
		String hql = "from U_TPL_REPORT v where v.reportcode='" + reportcode + "'";
		List reportlist = as.findByHql(hql);
		// System.out.println(reportlist);
		HashMap reportmap = (HashMap) reportlist.get(0);
		String uuid = (String) reportmap.get("uuid");
		hql = "from U_TPL_REPORT_ITEM v where v.cid='" + uuid + "' order by v.fieldorder asc";
		List itemlist = as.findByHql(hql);
		// building
		HashMap reportInfo = new HashMap();
		reportInfo.put("title", (String) reportmap.get("reporttitle"));
		reportInfo.put("subtitle", (String) reportmap.get("reportsubtitle"));

		ArrayList columns = new ArrayList();
		for (int x = 0; x < itemlist.size(); x++) {
			HashMap map = (HashMap) itemlist.get(x);
			map.put("dataTitle", (String) map.get("columntitle"));
			map.put("dataColumnProperty", (String) map.get("columnproperty"));
			map.put("dataClassTypeName", (String) map.get("columntype"));
			columns.add(map);
		}
		reportInfo.put("columns", columns);
		return reportInfo;
	}
	/**
	 * build report data by hql
	 * @param hql
	 * @return
	 */
	public List buildReportDataByHql(String hql) {
		AutoService as = (AutoService) SpringContext.getBean("AutoService");
		List list = as.findByHql(hql);
		return list;
	}

	/**
	 * build Html By Hql
	 * 
	 * @param reportcode
	 * @param hql
	 * @param filename
	 * @throws ColumnBuilderException
	 * @throws ClassNotFoundException
	 * @throws JRException
	 */
	public String buildHtmlByHql(String reportcode, String hql)
			throws ColumnBuilderException, ClassNotFoundException, JRException {
		List reportData = this.buildReportDataByHql(hql);
		HashMap reportInfo = this.buildReportInfo(reportcode);
		JasperPrint jp = this.buildJasperPrint(reportInfo, reportData);
		//obtain web path
		String path = PathUtil.getWebPath();
		path += "REPORT/";
		String uuid = java.util.UUID.randomUUID().toString();
		String filename = path + uuid;
		this.exportReportToHtmlFile(jp, filename + ".html");
		this.exportReportToXlsFile(jp, filename + ".xls");
		this.exportReportToRtfFile(jp, filename + ".rtf");
		this.exportReportToPdfFile(jp, filename + ".pdf");
		return uuid + ".html";
	}

	/**
	 * entry type
	 * build File By Hql
	 * 
	 * @param reportcode
	 * @param hql
	 * @param filename
	 * @throws ColumnBuilderException
	 * @throws ClassNotFoundException
	 * @throws JRException
	 */
	public String buildFileByHql(String reportcode, String hql, String type)
			throws ColumnBuilderException, ClassNotFoundException, JRException {
		List reportData = this.buildReportDataByHql(hql);
		HashMap reportInfo = this.buildReportInfo(reportcode);
		JasperPrint jp = this.buildJasperPrint(reportInfo, reportData);

		String path = PathUtil.getWebPath();
		path += "REPORT/";
		String uuid = java.util.UUID.randomUUID().toString();
		String filename = path + uuid;
		System.out.println("export-->filename:" + filename);
		if (type.equals("html")) {
			this.exportReportToHtmlFile(jp, filename + ".html");
			System.out.println("exportReportToHtmlFile-->end");
		}
		if (type.equals("xls")) {
			this.exportReportToXlsFile(jp, filename + ".xls");
			System.out.println("exportReportToXlsFile-->end");
		}
		if (type.equals("rtf")) {
			this.exportReportToRtfFile(jp, filename + ".rtf");
			System.out.println("exportReportToRtfFile-->end");
		}
		if (type.equals("pdf")) {
			this.exportReportToPdfFile(jp, filename + ".pdf");
			System.out.println("exportReportToPdfFile-->end");
		}
		return uuid + "." + type;
	}
	/**
	 * build report for flex 
	 * @param map
	 * @return
	 */
	public String buildReportForFlex(HashMap map) {
		HashMap info = (HashMap) map.get("info");
		String hql = (String) map.get("hql");
		String type = (String) map.get("type");
		try {
			return buildReportByHql(info, hql, type);
		} catch (ColumnBuilderException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * buildReportByHql
	 *  build report by Hql
	 * 
	 * @param reportcode
	 * @param hql
	 * @param filename
	 * @throws ColumnBuilderException
	 * @throws ClassNotFoundException
	 * @throws JRException
	 */
	public String buildReportByHql(HashMap reportInfo, String hql, String type)
			throws ColumnBuilderException, ClassNotFoundException, JRException {
		List reportData = this.buildReportDataByHql(hql);
		// HashMap reportInfo = this.buildReportInfo(reportcode);
		JasperPrint jp = this.buildJasperPrint(reportInfo, reportData);
		//obtain web path
		String path = PathUtil.getWebPath();
		path += "REPORT/";
		String uuid = java.util.UUID.randomUUID().toString();
		String filename = path + uuid;
		System.out.println("export-->filename:" + filename);
		if (type.equals("html")) {
			this.exportReportToHtmlFile(jp, filename + ".html");
			System.out.println("exportReportToHtmlFile-->end");
		}
		if (type.equals("xls")) {
			this.exportReportToXlsFile(jp, filename + ".xls");
			System.out.println("exportReportToXlsFile-->end");
		}
		if (type.equals("rtf")) {
			this.exportReportToRtfFile(jp, filename + ".rtf");
			System.out.println("exportReportToRtfFile-->end");
		}
		if (type.equals("pdf")) {
			this.exportReportToPdfFile(jp, filename + ".pdf");
			System.out.println("exportReportToPdfFile-->end");
		}
		return uuid + "." + type;
	}
}
