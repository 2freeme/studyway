package com.base.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.fonts.FontUtil;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.type.PdfVersionEnum;
import net.sf.jasperreports.export.type.PdfaConformanceEnum;
import net.sourceforge.barbecue.env.Environment;
import net.sourceforge.barbecue.env.EnvironmentFactory;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * <pre>
 * JasperReport工具类
 * </pre>
 * 
 * @author ex_dengjy2
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public abstract class JasperReportUtils {
	
	/**
	 * 打印PDF(支持多个模板打印至一个文件)
	 * @param fileNameList 文件名称List
	 * @param beanList 数据List
	 * @param response
	 * @throws Exception
	 */
	public static void printPdf(List<String> fileNameList, List<?> beanList, HttpServletResponse response) throws Exception {
		response.setContentType("application/pdf");
		response.setCharacterEncoding("UTF-8");
		printPdf(fileNameList, beanList, response.getOutputStream());
	}
	
	/**
	 * 打印PDF(支持多个模板打印至一个文件)
	 * @param fileNameList 文件名称List
	 * @param beanList 数据List
	 * @param out 输出流
	 * @throws JRException
	 */
	public static void printPdf(List<String> fileNameList, List<?> beanList, OutputStream out) throws JRException {
		//报表路径
		String reportPath = JasperReportUtils.class.getClassLoader().getResource("").getPath() + "print/";

		List<File> jasperFileList = new ArrayList<>(fileNameList.size());
		for (String fileName : fileNameList) {
			File jasperFile = new File(reportPath, fileName);
			if (!jasperFile.exists()) {
				throw new IllegalArgumentException(fileName + "文件不存在");
			}
			jasperFileList.add(jasperFile);
		}
		
		JasperReportsContext jasperReportsContext = new SimpleJasperReportsContext();
		// 获取字体
		final Font awtFont = FontUtil.getInstance(jasperReportsContext).getAwtFontFromBundles("CCSSimsun", Font.PLAIN, 20.0f, null,false);
		// 解决barcode下面的数字打印不出来的问题，保证barbecue字体和jarsperreport字体一致
		Environment env = new Environment() {

			@Override
			public int getResolution() {
				return 300;
			}

			@Override
			public Font getDefaultFont() {
				return awtFont;
			}
			
		};
		EnvironmentFactory.setDefaultEnvironment(env);

		List<JasperPrint> jasperPrintList = new ArrayList<>();
		Map<String, Object> paramMap = new HashMap<>();// jasperreport -> parameters
		paramMap.put("SUBREPORT_DIR", reportPath);
		for (int i = 0, len = beanList.size(); i < len; i++) {
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(CollectionUtil.getList(beanList.get(i)));
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperReportsContext, jasperFileList.get(i));
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, jrDataSource);
			jasperPrintList.add(jasperPrint);
		}
		
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
		
		SimplePdfExporterConfiguration config = new SimplePdfExporterConfiguration();
		config.setCompressed(false);
		config.setPdfVersion(PdfVersionEnum.VERSION_1_4);
		config.setPdfaConformance(PdfaConformanceEnum.NONE);
		config.setIccProfilePath(reportPath + "sRGB2014.icc");
		exporter.setConfiguration(config);
		
		exporter.exportReport();
	}

}
