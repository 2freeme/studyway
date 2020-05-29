//package com.base.util;
//
//import com.midea.ccs.core.dto.request.ExcelMetaDataDTO;
//import com.midea.ccs.core.dto.request.ExcelMetaDataRangeDTO;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.xssf.streaming.SXSSFSheet;
//import org.apache.poi.xssf.streaming.SXSSFWorkbook;
//import org.apache.poi.xssf.usermodel.XSSFCellStyle;
//import org.apache.poi.xssf.usermodel.XSSFRichTextString;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.File;
//import java.util.List;
//import java.util.Map;
//
//
//public class PoiExportUtils {
//
//	private static final Logger logger = LoggerFactory.getLogger(PoiExportUtils.class.getName());
//
//	private final static Integer FLUSH_ROWNUM = 100; // 每次刷入Sheet的数据条数
//
//	/**
//	 * @see 向工作簿中新增一个Sheet页，并填充相应的内容
//	 * @param headerList sheet标题行
//	 * @param content sheet内容
//	 * @param workbook POI Excel工作簿对象
//	 * @author wenbin.xia@midea.com.cn 2016-07-16
//	 * @throws Exception
//	 */
//	public static void addSheet2Excel(List<ExcelMetaDataDTO> headerList, List<Map<String, Object>> content, SXSSFWorkbook workbook, List<ExcelMetaDataRangeDTO> rangeHeaderList) throws Exception {
//			RichTextString text = null;
//
//			// 表头Cell样式
//			XSSFCellStyle styleMust=(XSSFCellStyle) workbook.createCellStyle();
//			XSSFCellStyle styleNotMust=(XSSFCellStyle) workbook.createCellStyle();
//			// 红色表头字体
//			Font fontRed = workbook.createFont();
//		    fontRed.setFontHeightInPoints((short)12); //字体大小
//		    fontRed.setFontName("楷体");
//		    fontRed.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
//		    fontRed.setColor(HSSFColor.RED.index);    //红字
//		    // 黑色表头字体
//		    Font fontBlack = workbook.createFont();
//		    fontBlack.setFontHeightInPoints((short)12); //字体大小
//		    fontBlack.setFontName("楷体");
//		    fontBlack.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
//		    fontBlack.setColor(HSSFColor.BLACK.index);    //黑字
//		    // 设置表头字体
//		    styleMust.setFont(fontBlack);
//		    styleNotMust.setFont(fontBlack);
//			// 设置表头边框
//		    styleMust.setBorderTop(XSSFCellStyle.BORDER_THIN);
//		    styleMust.setBorderRight(XSSFCellStyle.BORDER_THIN);
//		    styleMust.setBorderBottom(XSSFCellStyle.BORDER_THIN);
//		    styleMust.setBorderLeft(XSSFCellStyle.BORDER_THIN);
//		    styleNotMust.setBorderTop(XSSFCellStyle.BORDER_THIN);
//		    styleNotMust.setBorderRight(XSSFCellStyle.BORDER_THIN);
//		    styleNotMust.setBorderBottom(XSSFCellStyle.BORDER_THIN);
//		    styleNotMust.setBorderLeft(XSSFCellStyle.BORDER_THIN);
//		    // 设置表头左右居中
//		    styleMust.setAlignment(CellStyle.ALIGN_CENTER);
//		    styleNotMust.setAlignment(CellStyle.ALIGN_CENTER);
//		    // 设置表头上下居中
//		    styleMust.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//		    styleNotMust.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//
//		    // 设置数据格式
//		    DataFormat df = workbook.createDataFormat();
//
//		    // 黑色表头字体
//		    Font fontCellBlack = workbook.createFont();
//		    fontCellBlack.setFontHeightInPoints((short)11); //字体大小
//		    fontCellBlack.setFontName("宋体");
//		    fontCellBlack.setColor(HSSFColor.BLACK.index);    //黑字
//
//		    // 数据Cell样式
//		    XSSFCellStyle doubleCellStyle=(XSSFCellStyle) workbook.createCellStyle();
//		    doubleCellStyle.setFont(fontCellBlack);
//		    // 小数
//		    doubleCellStyle.setAlignment(CellStyle.ALIGN_RIGHT); // 靠右
//		    doubleCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 上下居中
//		    doubleCellStyle.setDataFormat(df.getFormat("#,##0.00")); // 保留两位小数点
//		    doubleCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
//		    doubleCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
//		    doubleCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
//		    doubleCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
//
//		    // 整数
//		    XSSFCellStyle intCellStyle=(XSSFCellStyle) workbook.createCellStyle();
//		    intCellStyle.setFont(fontCellBlack);
//		    intCellStyle.setAlignment(CellStyle.ALIGN_RIGHT); // 靠右
//		    intCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 上下居中
//		    intCellStyle.setDataFormat(df.getFormat("#,##0")); // 数据格式只显示整数
//		    intCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
//		    intCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
//		    intCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
//		    intCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
//
//		    // 字符串
//		    XSSFCellStyle stringCellStyle=(XSSFCellStyle) workbook.createCellStyle();
//		    stringCellStyle.setFont(fontCellBlack);
//		    stringCellStyle.setAlignment(CellStyle.ALIGN_LEFT); // 靠左
//		    stringCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 上下居中
//		    stringCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
//		    stringCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
//		    stringCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
//		    stringCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
//
//			// 总列数
//			int colCount = headerList.size();
//			// 生成一个表格
//			Sheet sheet = null;
//			Row row = null;
//			// 生成Sheet
//			sheet = workbook.createSheet();
//
//			if(rangeHeaderList!=null&&rangeHeaderList.size()>0) {
//				// 生成表格标题
//				Row row1 = sheet.createRow(0);
//				Row row2 = sheet.createRow(1);
//				for (short i = 0; i < colCount; i++) {
//					Cell cell = row1.createCell(i);
//					Cell cell2 = row2.createCell(i);
//
//					text = new XSSFRichTextString(headerList.get(i).getFieldCaption());
//					if (headerList.get(i).getFieldIsNull() != null && headerList.get(i).getFieldIsNull().intValue() == 1) {
//						cell.setCellStyle(styleMust);
//						cell2.setCellStyle(styleMust);
//					} else {
//						cell.setCellStyle(styleNotMust);
//						cell2.setCellStyle(styleNotMust);
//					}
//					cell.setCellValue(text);
//					cell2.setCellValue(text);
//				}
//
//				// 根据配置合并第一行表头
//				for (short i = 0; i < rangeHeaderList.size(); i++) {
//					ExcelMetaDataRangeDTO rangeDto = rangeHeaderList.get(i);
//					text = new XSSFRichTextString(rangeDto.getFieldCaption());
//					row1.getCell(rangeDto.getStartColumn()).setCellValue(text);
//					CellRangeAddress region = new CellRangeAddress(rangeDto.getStartRow(), rangeDto.getEndRow(), rangeDto.getStartColumn(), rangeDto.getEndColumn());
//					sheet.addMergedRegion(region);
//				}
//			}else {
//				// 生成表格标题
//				row = sheet.createRow(0);
//				for (short i = 0; i < colCount; i++) {
//					Cell cell = row.createCell(i);
//					text = new XSSFRichTextString(headerList.get(i).getFieldCaption());
//					if(headerList.get(i).getFieldIsNull()!=null&&headerList.get(i).getFieldIsNull().intValue()==1){
//						cell.setCellStyle(styleMust);
//					}else{
//						cell.setCellStyle(styleNotMust);
//					}
//					cell.setCellValue(text);
//				}
//			}
//
//
//			// 内容行行数
//			Integer size = content.size();
//
//			// 遍历内容
//			if (content != null && size > 0) {
//				// 除去表头，行下标从1开始
//				int rowNum = 1;
//				if (rangeHeaderList != null && rangeHeaderList.size() > 0) {//需要合并表头从第二行开始写数据
//					rowNum = 2;
//				}
//				for (Map<String, Object> map : content) {
//					row = sheet.createRow(rowNum++);
//					for (int i = 0; i < colCount; i++) {
//						Cell cell = row.createCell(i);
//
//						if(headerList.get(i).getFieldType() !=null && headerList.get(i).getFieldType().intValue() == 1) { // 小数
//							cell.setCellStyle(doubleCellStyle);
//
//							double doubleValue = map.get(headerList.get(i).getFieldName()) == null ? 0.00
//									: Double.parseDouble(map.get(headerList.get(i).getFieldName()).toString());
//							cell.setCellValue(doubleValue);
//						} else if(headerList.get(i).getFieldType() !=null && headerList.get(i).getFieldType().intValue() == 2){ // 整数
//							cell.setCellStyle(intCellStyle);
//
//							int intValue = map.get(headerList.get(i).getFieldName()) == null ? 0
//									: Integer.parseInt(map.get(headerList.get(i).getFieldName()).toString());
//							cell.setCellValue(intValue);
//						} else { // 其他
//							cell.setCellStyle(stringCellStyle);
//
//							text = new XSSFRichTextString(map.get(headerList.get(i).getFieldName()) == null ? "" : map.get(
//									headerList.get(i).getFieldName()).toString());
//							cell.setCellValue(text);
//						}
//					}
//					if (rowNum % FLUSH_ROWNUM == 0) {
//						((SXSSFSheet) sheet).flushRows();
//					}
//				}
//			}
//	}
//
//	/**
//	 * @see 向工作簿中新增一个Sheet页，并填充相应的内容
//	 * @param headerList sheet标题行
//	 * @param content sheet内容
//	 * @param sheet POI Excel Sheet对象
//	 * @param startRow 添加开始行
//	 * @author wenbin.xia@midea.com.cn 2016-07-16
//	 * @throws Exception
//	 */
//	public static void addContent2Sheet(List<ExcelMetaDataDTO> headerList, List<Map<String, Object>> content, Sheet sheet, int startRowIndex) throws Exception {
//			RichTextString text = null;
//			// 总列数
//			int colCount = headerList.size();
//			Row row = null;
//			// 内容行行数
//			Integer size = content.size();
//			// 遍历内容
//			if (content != null && size > 0) {
//				int rowNum = startRowIndex;
//				for (Map<String, Object> map : content) {
//					row = sheet.createRow(rowNum);
//					for (int i = 0; i < colCount; i++) {
//						Cell cell = row.createCell(i);
//						text = new XSSFRichTextString(map.get(headerList.get(i).getFieldName()) == null ? "" : map.get(
//								headerList.get(i).getFieldName()).toString());
//						cell.setCellValue(text);
//					}
//					if (rowNum % FLUSH_ROWNUM == 0) {
//						((SXSSFSheet) sheet).flushRows();
//					}
//					rowNum++;
//				}
//			}
//	}
//
//	public static String getExportPath(){
//		String osName = System.getProperty("os.name");
//		if(osName != null && osName.startsWith("win")) { // Windows系统
//			return getProjectRootPath() + File.separator + "files" + File.separator + "export";
//		} else {
//			return "/was/app/upload_file/ccsfile/export";
//		}
//	}
//
//	public static String getProjectRootPath(){
//		String systemPath = PoiExportUtils.class.getClassLoader().getResource("").getPath();
//		systemPath = systemPath.substring(0, systemPath.lastIndexOf(File.separator) == -1 ? systemPath.length() : systemPath.lastIndexOf(File.separator));
//		systemPath = systemPath.replace("/WEB-INF/classes", "");
//		return systemPath;
//	}
//
//}
