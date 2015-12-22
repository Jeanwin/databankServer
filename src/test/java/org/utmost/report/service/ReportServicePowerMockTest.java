package test.java.org.utmost.report.service;

import static org.easymock.EasyMock.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.utmost.common.DBSupport;
import org.utmost.common.SpringContext;
import org.utmost.portal.service.AutoService;
import org.utmost.report.service.ReportService;
import org.utmost.util.CategoryUtil;
import org.utmost.util.PathUtil;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;


@RunWith(PowerMockRunner.class)     
@PrepareForTest({SpringContext.class,DBSupport.class,ColumnBuilder.class,DynamicJasperHelper.class,JasperExportManager.class,ReportService.class,PathUtil.class}) 
public class ReportServicePowerMockTest extends TestCase {
	
    ReportService reportService ;
    DBSupport dBSupport;
    CategoryUtil categoryUtil;

    @Before
    @PrepareForTest({SpringContext.class,DBSupport.class,PathUtil.class})  
    public void constructData(){
        reportService = new ReportService();
    }
    
    @Test
    public void testBuildJasperPrint() throws Exception{
            HashMap reportInfo = new HashMap();
            reportInfo.put("title", "reporttitle");
            reportInfo.put("subtitle", "reportsubtitle");
            ArrayList<HashMap> columns = new ArrayList<HashMap>();
            HashMap<String,String> column = new HashMap<String,String>();
            column.put("dataTitle", "columntitle");
            column.put("reportsubtitle", "reportsubtitle");
            column.put("columnproperty", "columnproperty");
            column.put("columntitle", "columntitle");
            column.put("uuid", "uuid");
            column.put("columntype", "columntype");
            column.put("reporttitle", "reporttitle");
            columns.add(column);
            reportInfo.put("columns", columns);
            Collection<HashMap> reportData = new ArrayList();
            ColumnBuilder cb = PowerMock.createMock(ColumnBuilder.class);
            PowerMock.mockStatic(ColumnBuilder.class);
            EasyMock.expect(ColumnBuilder.getInstance()).andReturn(cb);
            
            EasyMock.expect(cb.setHeaderStyle(EasyMock.anyObject(Style.class))).andReturn(cb);
            EasyMock.expect(cb.setStyle(EasyMock.anyObject(Style.class))).andReturn(cb);
            EasyMock.expect(cb.setTitle(EasyMock.anyObject(String.class))).andReturn(cb);
            AbstractColumn abstractColumn = PowerMock.createMock(AbstractColumn.class);
            EasyMock.expect(cb.build()).andReturn(abstractColumn);
            abstractColumn.setWidth(EasyMock.anyInt());
            abstractColumn.setFixedWidth(EasyMock.anyBoolean());
            
            JasperPrint jasperPrint = PowerMock.createMock(JasperPrint.class);
            PowerMock.mockStatic(DynamicJasperHelper.class);
            EasyMock.expect(DynamicJasperHelper.generateJasperPrint(EasyMock.anyObject(DynamicReport.class),EasyMock.anyObject(LayoutManager.class),EasyMock.anyObject(JRDataSource.class))).andReturn(jasperPrint);
            
            PowerMock.replayAll();
            reportService.buildJasperPrint(reportInfo,reportData);
            verify();
    }
    

    @Test
    public void testExportReportToHtmlFile() throws Exception{
            JasperPrint jasperPrint = PowerMock.createMock(JasperPrint.class);
            
            PowerMock.mockStatic(JasperExportManager.class);
            JasperExportManager.exportReportToHtmlFile(EasyMock.anyObject(JasperPrint.class),EasyMock.anyObject(String.class));
            
            PowerMock.replayAll();
            reportService.exportReportToHtmlFile(jasperPrint,"name");
            verify();
    }

    @Test
    public void testExportReportToPdfFile() throws Exception{
            JasperPrint jasperPrint = PowerMock.createMock(JasperPrint.class);
            
            PowerMock.mockStatic(JasperExportManager.class);
            JasperExportManager.exportReportToPdfFile(EasyMock.anyObject(JasperPrint.class),EasyMock.anyObject(String.class));
            
            PowerMock.replayAll();
            reportService.exportReportToPdfFile(jasperPrint,"name");
            verify();
    }

    @Test
    public void testExportReportToRtfFile() throws Exception{
            JasperPrint jasperPrint = PowerMock.createMock(JasperPrint.class);
            
            JRRtfExporter jRRtfExporter = PowerMock.createMock(JRRtfExporter.class);
            PowerMock.expectNew(JRRtfExporter.class).andReturn(jRRtfExporter);
            jRRtfExporter.exportReport();
            jRRtfExporter.setParameter(EasyMock.anyObject(JRExporterParameter.class),EasyMock.anyObject(Object.class));
            EasyMock.expectLastCall().times(2);
            
            PowerMock.replayAll();
            reportService.exportReportToRtfFile(jasperPrint,"name");
            verify();
    }

    @Test
    public void testExportReportToXlsFile() throws Exception{
            JasperPrint jasperPrint = PowerMock.createMock(JasperPrint.class);
            
            JRXlsExporter jRRtfExporter = PowerMock.createMock(JRXlsExporter.class);
            PowerMock.expectNew(JRXlsExporter.class).andReturn(jRRtfExporter);
            jRRtfExporter.exportReport();
            jRRtfExporter.setParameter(EasyMock.anyObject(JRExporterParameter.class),EasyMock.anyObject(Object.class));
            EasyMock.expectLastCall().times(3);
            
            PowerMock.replayAll();
            reportService.exportReportToXlsFile(jasperPrint,"name");
            verify();
    }

    @Test
    public void testBuildReportInfo() throws Exception{
          HashMap main = new HashMap();
          main.put("uuid", "100");
          List tempList = new ArrayList();
          tempList.add(main);
         AutoService autoService = PowerMock.createMock(AutoService.class);
         PowerMock.mockStatic(SpringContext.class);
         EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(autoService);
         EasyMock.expect(autoService.findByHql(EasyMock.anyObject(String.class))).andReturn(tempList).times(2);
         
         PowerMock.replayAll();
         reportService.buildReportInfo("name");
            verify();
    }

    @Test
    public void testBuildReportDataByHql() throws Exception{
          HashMap main = new HashMap();
          main.put("uuid", "100");
          List tempList = new ArrayList();
          tempList.add(main);
         AutoService autoService = PowerMock.createMock(AutoService.class);
         PowerMock.mockStatic(SpringContext.class);
         EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(autoService);
         EasyMock.expect(autoService.findByHql(EasyMock.anyObject(String.class))).andReturn(tempList);
         
         PowerMock.replayAll();
         reportService.buildReportDataByHql("name");
            verify();
    }

    @Test
    public void testBuildHtmlByHql() throws Exception{
          HashMap main = new HashMap();
          main.put("uuid", "100");
          List tempList = new ArrayList();
          tempList.add(main);
         AutoService autoService = PowerMock.createMock(AutoService.class);
         PowerMock.mockStatic(SpringContext.class);
         EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(autoService).times(2);
         EasyMock.expect(autoService.findByHql(EasyMock.anyObject(String.class))).andReturn(tempList).times(3);


         ColumnBuilder cb = PowerMock.createMock(ColumnBuilder.class);
         PowerMock.mockStatic(ColumnBuilder.class);
         EasyMock.expect(ColumnBuilder.getInstance()).andReturn(cb);
         
         EasyMock.expect(cb.setHeaderStyle(EasyMock.anyObject(Style.class))).andReturn(cb);
         EasyMock.expect(cb.setStyle(EasyMock.anyObject(Style.class))).andReturn(cb);
         EasyMock.expect(cb.setTitle(EasyMock.anyObject(String.class))).andReturn(cb);
         AbstractColumn abstractColumn = PowerMock.createMock(AbstractColumn.class);
         EasyMock.expect(cb.build()).andReturn(abstractColumn);
         abstractColumn.setWidth(EasyMock.anyInt());
         abstractColumn.setFixedWidth(EasyMock.anyBoolean());
         
         JasperPrint jasperPrint = PowerMock.createMock(JasperPrint.class);
         PowerMock.mockStatic(DynamicJasperHelper.class);
         EasyMock.expect(DynamicJasperHelper.generateJasperPrint(EasyMock.anyObject(DynamicReport.class),EasyMock.anyObject(LayoutManager.class),EasyMock.anyObject(JRDataSource.class))).andReturn(jasperPrint);
         
         PowerMock.mockStatic(PathUtil.class);
         EasyMock.expect(PathUtil.getWebPath()).andReturn("t");
         
         PowerMock.mockStatic(JasperExportManager.class);
         JasperExportManager.exportReportToHtmlFile(EasyMock.anyObject(JasperPrint.class),EasyMock.anyObject(String.class));
         
         JRXlsExporter jRRtfExporter = PowerMock.createMock(JRXlsExporter.class);
         PowerMock.expectNew(JRXlsExporter.class).andReturn(jRRtfExporter);
         jRRtfExporter.exportReport();
         jRRtfExporter.setParameter(EasyMock.anyObject(JRExporterParameter.class),EasyMock.anyObject(Object.class));
         EasyMock.expectLastCall().times(3);
         
         JRRtfExporter jRRtfExporter1 = PowerMock.createMock(JRRtfExporter.class);
         PowerMock.expectNew(JRRtfExporter.class).andReturn(jRRtfExporter1);
         jRRtfExporter1.exportReport();
         jRRtfExporter1.setParameter(EasyMock.anyObject(JRExporterParameter.class),EasyMock.anyObject(Object.class));
         EasyMock.expectLastCall().times(2);
         
         JasperExportManager.exportReportToPdfFile(EasyMock.anyObject(JasperPrint.class),EasyMock.anyObject(String.class));
   
         
         PowerMock.replayAll();
         reportService.buildHtmlByHql("name","hql");
    }

    @Test
    public void testBuildFileByHql() throws Exception{
          HashMap main = new HashMap();
          main.put("uuid", "100");
          List tempList = new ArrayList();
          tempList.add(main);
         AutoService autoService = PowerMock.createMock(AutoService.class);
         PowerMock.mockStatic(SpringContext.class);
         EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(autoService).times(2);
         EasyMock.expect(autoService.findByHql(EasyMock.anyObject(String.class))).andReturn(tempList).times(3);


         ColumnBuilder cb = PowerMock.createMock(ColumnBuilder.class);
         PowerMock.mockStatic(ColumnBuilder.class);
         EasyMock.expect(ColumnBuilder.getInstance()).andReturn(cb);
         
         EasyMock.expect(cb.setHeaderStyle(EasyMock.anyObject(Style.class))).andReturn(cb);
         EasyMock.expect(cb.setStyle(EasyMock.anyObject(Style.class))).andReturn(cb);
         EasyMock.expect(cb.setTitle(EasyMock.anyObject(String.class))).andReturn(cb);
         AbstractColumn abstractColumn = PowerMock.createMock(AbstractColumn.class);
         EasyMock.expect(cb.build()).andReturn(abstractColumn);
         abstractColumn.setWidth(EasyMock.anyInt());
         abstractColumn.setFixedWidth(EasyMock.anyBoolean());
         
         JasperPrint jasperPrint = PowerMock.createMock(JasperPrint.class);
         PowerMock.mockStatic(DynamicJasperHelper.class);
         EasyMock.expect(DynamicJasperHelper.generateJasperPrint(EasyMock.anyObject(DynamicReport.class),EasyMock.anyObject(LayoutManager.class),EasyMock.anyObject(JRDataSource.class))).andReturn(jasperPrint);
         
         PowerMock.mockStatic(PathUtil.class);
         EasyMock.expect(PathUtil.getWebPath()).andReturn("t");
         
         PowerMock.mockStatic(JasperExportManager.class);
         JasperExportManager.exportReportToHtmlFile(EasyMock.anyObject(JasperPrint.class),EasyMock.anyObject(String.class));
         PowerMock.replayAll();
         reportService.buildFileByHql("name","hql","html");
    }
    
    @Test
    public void testBuildReportByHql() throws Exception{
          HashMap main = new HashMap();
          main.put("uuid", "100");
          List tempList = new ArrayList();
          tempList.add(main);
          
        HashMap reportInfo = new HashMap();
        reportInfo.put("title", "reporttitle");
        reportInfo.put("subtitle", "reportsubtitle");
        ArrayList<HashMap> columns = new ArrayList<HashMap>();
        HashMap<String,String> column = new HashMap<String,String>();
        column.put("dataTitle", "columntitle");
        column.put("reportsubtitle", "reportsubtitle");
        column.put("columnproperty", "columnproperty");
        column.put("columntitle", "columntitle");
        column.put("uuid", "uuid");
        column.put("columntype", "columntype");
        column.put("reporttitle", "reporttitle");
        columns.add(column);
        reportInfo.put("columns", columns);
         AutoService autoService = PowerMock.createMock(AutoService.class);
         PowerMock.mockStatic(SpringContext.class);
         EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(autoService).times(2);
         EasyMock.expect(autoService.findByHql(EasyMock.anyObject(String.class))).andReturn(tempList).times(3);


         ColumnBuilder cb = PowerMock.createMock(ColumnBuilder.class);
         PowerMock.mockStatic(ColumnBuilder.class);
         EasyMock.expect(ColumnBuilder.getInstance()).andReturn(cb);
         
         EasyMock.expect(cb.setHeaderStyle(EasyMock.anyObject(Style.class))).andReturn(cb);
         EasyMock.expect(cb.setStyle(EasyMock.anyObject(Style.class))).andReturn(cb);
         EasyMock.expect(cb.setTitle(EasyMock.anyObject(String.class))).andReturn(cb);
         AbstractColumn abstractColumn = PowerMock.createMock(AbstractColumn.class);
         EasyMock.expect(cb.build()).andReturn(abstractColumn);
         abstractColumn.setWidth(EasyMock.anyInt());
         abstractColumn.setFixedWidth(EasyMock.anyBoolean());
         
         JasperPrint jasperPrint = PowerMock.createMock(JasperPrint.class);
         PowerMock.mockStatic(DynamicJasperHelper.class);
         EasyMock.expect(DynamicJasperHelper.generateJasperPrint(EasyMock.anyObject(DynamicReport.class),EasyMock.anyObject(LayoutManager.class),EasyMock.anyObject(JRDataSource.class))).andReturn(jasperPrint);
         
         PowerMock.mockStatic(PathUtil.class);
         EasyMock.expect(PathUtil.getWebPath()).andReturn("t");
         
         PowerMock.mockStatic(JasperExportManager.class);
         JasperExportManager.exportReportToHtmlFile(EasyMock.anyObject(JasperPrint.class),EasyMock.anyObject(String.class));
         
         PowerMock.replayAll();
         reportService.buildReportByHql(reportInfo,"hql","html");
    }
    

    @Test
    public void testBuildReportForFlex() throws Exception{
          HashMap main = new HashMap();
          main.put("uuid", "100");
          List tempList = new ArrayList();
          tempList.add(main);
          
          HashMap reportInfo = new HashMap();
          reportInfo.put("title", "reporttitle");
          reportInfo.put("subtitle", "reportsubtitle");
          ArrayList<HashMap> columns = new ArrayList<HashMap>();
          HashMap<String,String> column = new HashMap<String,String>();
          column.put("dataTitle", "columntitle");
          column.put("reportsubtitle", "reportsubtitle");
          column.put("columnproperty", "columnproperty");
          column.put("columntitle", "columntitle");
          column.put("uuid", "uuid");
          column.put("columntype", "columntype");
          column.put("reporttitle", "reporttitle");
          columns.add(column);
          reportInfo.put("columns", columns);
          HashMap reportInfo1 = new HashMap();
          reportInfo1.put("info", reportInfo);
          reportInfo1.put("type", "html");
           AutoService autoService = PowerMock.createMock(AutoService.class);
           PowerMock.mockStatic(SpringContext.class);
           EasyMock.expect(SpringContext.getBean(EasyMock.anyObject(String.class))).andReturn(autoService).times(2);
           EasyMock.expect(autoService.findByHql(EasyMock.anyObject(String.class))).andReturn(tempList).times(3);
  
           PowerMock.mockStatic(PathUtil.class);
           EasyMock.expect(PathUtil.getWebPath()).andReturn("t");
  
           ColumnBuilder cb = PowerMock.createMock(ColumnBuilder.class);
           PowerMock.mockStatic(ColumnBuilder.class);
           EasyMock.expect(ColumnBuilder.getInstance()).andReturn(cb);
           
           EasyMock.expect(cb.setHeaderStyle(EasyMock.anyObject(Style.class))).andReturn(cb);
           EasyMock.expect(cb.setStyle(EasyMock.anyObject(Style.class))).andReturn(cb);
           EasyMock.expect(cb.setTitle(EasyMock.anyObject(String.class))).andReturn(cb);
           AbstractColumn abstractColumn = PowerMock.createMock(AbstractColumn.class);
           EasyMock.expect(cb.build()).andReturn(abstractColumn);
           abstractColumn.setWidth(EasyMock.anyInt());
           abstractColumn.setFixedWidth(EasyMock.anyBoolean());
           
           JasperPrint jasperPrint = PowerMock.createMock(JasperPrint.class);
           PowerMock.mockStatic(DynamicJasperHelper.class);
           EasyMock.expect(DynamicJasperHelper.generateJasperPrint(EasyMock.anyObject(DynamicReport.class),EasyMock.anyObject(LayoutManager.class),EasyMock.anyObject(JRDataSource.class))).andReturn(jasperPrint);
           
           
           PowerMock.mockStatic(JasperExportManager.class);
           JasperExportManager.exportReportToHtmlFile(EasyMock.anyObject(JasperPrint.class),EasyMock.anyObject(String.class));
           
           PowerMock.replayAll();
           reportService.buildReportForFlex(reportInfo1);
    }
}
