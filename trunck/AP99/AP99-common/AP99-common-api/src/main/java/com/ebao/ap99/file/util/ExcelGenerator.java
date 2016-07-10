package com.ebao.ap99.file.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ebao.ap99.file.entity.DocumentField;
import com.ebao.ap99.file.entity.DocumentRule;
import com.ebao.ap99.file.service.FileService;

public class ExcelGenerator {

	public static final String[] tableHeader = { "Currency", "BusiType", "EntryType", "Amount" };

	public static HSSFWorkbook dataWorkBook = new HSSFWorkbook();

	public static HSSFSheet dataSheet = dataWorkBook.createSheet("Data List");

	public static final short cellNumber = (short) tableHeader.length;

	public static final int columNumber = 1;

	/**
	 *
	 * @return
	 */
	public static void createTableHeader() {
		HSSFHeader header = dataSheet.getHeader();
		header.setCenter("Claim Summary");
		HSSFRow headerRow = dataSheet.createRow((short) 0);
		for (int i = 0; i < cellNumber; i++) {
			HSSFCell headerCell = headerRow.createCell((short) i);
			headerCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			headerCell.setCellValue(tableHeader[i]);
		}
	}

	/**
	 *
	 * @param cells
	 * @param rowIndex
	 */
	public static void createTableRow(List cells, short rowIndex) {

		HSSFRow row = dataSheet.createRow((short) rowIndex);
		for (int i = 0; i < cells.size(); i++) {

			HSSFCell cell = row.createCell(i);
			if (i != 3) {
				HSSFCellStyle cellStyle2 = dataWorkBook.createCellStyle();
				HSSFDataFormat format = dataWorkBook.createDataFormat();
				cellStyle2.setDataFormat(format.getFormat("@"));
				cell.setCellStyle(cellStyle2);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue((String) cells.get(i));
			}
			if (i == 5) {
				CellStyle cellStyle = dataWorkBook.createCellStyle();
				CreationHelper createHelper = dataWorkBook.getCreationHelper();
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("mm/dd/yyyy hh:mm"));
				cell.setCellStyle(cellStyle);

				cell.setCellValue((Date) cells.get(i));
			} else {
				CellStyle cellStyle = dataWorkBook.createCellStyle();
				CreationHelper createHelper = dataWorkBook.getCreationHelper();
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("$ .00"));
				cell.setCellStyle(cellStyle);
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue((double) cells.get(i));
			}

		}
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public List getDate() {
		List cacheList = new ArrayList();
		for (int j = 0; j < 10; j++) {

		}
		return cacheList;
	}

	/**
	 * 
	 * @throws SQLException
	 * 
	 */
	public void createExcelSheet() throws SQLException {
		createTableHeader();
		int rowIndex = 1;

		List list = getDate();

		for (int j = 0; j < list.size(); j++) {
			List listRead = new ArrayList();
			for (int i = 1; i <= columNumber; i++) {
				listRead.add(list.get(i));
				listRead.add(list.get(i));
				listRead.add(list.get(i));
				listRead.add(list.get(i));

			}
			createTableRow(listRead, (short) rowIndex);
			rowIndex++;
		}
	}

	/**
	 *
	 * @param sheet
	 * @param os
	 * @throws IOException
	 */
	public void exportExcel(HSSFSheet sheet, OutputStream os) throws IOException {
		sheet.setGridsPrinted(true);
		HSSFFooter footer = sheet.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
		dataWorkBook.write(os);
	}

	public static String insertRow(List itemVOs, List<DocumentField> fields, String template) throws Exception {
		InputStream inp = new FileInputStream(FileService.FILE_ROOT_PATH + File.separator + FileService.FILE_PATH_TEMPLATE +File.separator + template);
		Workbook wb = WorkbookFactory.create(inp);
		//XSSFWorkbook wb = new XSSFWorkbook(inp);
		Sheet sheet = wb.getSheet("Data List");
		int starRow = 1;
		
		int rows = itemVOs.size();
        if(rows > 0){
        	
        
		sheet.shiftRows(starRow + 1, sheet.getLastRowNum() + 1, rows, true, false);

		starRow = starRow - 2;

		for (int i = 0; i < rows; i++) {
			Object obj = itemVOs.get(i);

			Row sourceRow = null;
			Row targetRow = null;
			Cell sourceCell = null;
			Cell targetCell = null;
			short m;
			starRow = starRow + 1;
			sourceRow = sheet.getRow(starRow);
			targetRow = sheet.createRow(starRow + 1);
			targetRow.setHeight(sourceRow.getHeight());

			for (m = sourceRow.getFirstCellNum(); m < sourceRow.getLastCellNum(); m++) {
				DocumentField field = fields.get(m);
				String fieldName = field.getFieldName();
				String name = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				// try {
				sourceCell = sourceRow.getCell(m);
				targetCell = targetRow.createCell(m);

				// targetCell.setEncoding(sourceCell.getEncoding());
				targetCell.setCellStyle(sourceCell.getCellStyle());
				targetCell.setCellType(sourceCell.getCellType());

				String type = obj.getClass().getDeclaredField(fieldName).getGenericType().toString();
				Method mt = obj.getClass().getMethod("get" + name);
				String value = null;
				if (type.equals("class java.lang.String")) {
					value = (String) mt.invoke(obj);
					targetCell.setCellValue(value);
				}
				if (type.equals("class java.lang.Integer")) {
					value = String.valueOf((Integer) mt.invoke(obj));
					targetCell.setCellValue(value);
				}
				if (type.equals("int")) {
					value = String.valueOf((int) mt.invoke(obj));
					targetCell.setCellValue(value);
				}
				if (type.equals("class java.lang.Long")) {
					value = String.valueOf((Long) mt.invoke(obj));
					targetCell.setCellValue(value);
				}
				if (type.equals("class java.math.BigDecimal")) {
					value = String.valueOf((BigDecimal) mt.invoke(obj));
					targetCell.setCellValue(Double.valueOf(value));
				}
				if (type.equals("long")) {
					value = String.valueOf((long) mt.invoke(obj));
					targetCell.setCellValue(value);

				}
				if (type.equals("class java.util.Date")) {
					value = String.valueOf((Date) mt.invoke(obj));
					targetCell.setCellValue(value);
				}
			}
		}
        }
		String filepath = FileService.FILE_ROOT_PATH + File.separator 
				+ FileService.FILE_PATH_DOCUMENT_OUTPUT + File.separator 
				+ "temp_" + (new Date().getTime()) + ".xlsx";
		makeDir( FileService.FILE_ROOT_PATH + File.separator 
                + FileService.FILE_PATH_DOCUMENT_OUTPUT);
		FileOutputStream fileOut = new FileOutputStream(filepath);
		try {
			wb.write(fileOut);
		} finally {
			fileOut.close();
		}
		
		return filepath;
	}

	public static String createModelList(List itemVOs, DocumentRule documentRule, List<DocumentField> fields) throws Exception {
		return insertRow(itemVOs, fields, documentRule.getTemplate());

	}
	
	private static void makeDir(String absolutePath) {
	        // create folder
	        File file = new File(absolutePath);
	        if (!file.exists()) {
	            file.mkdirs();
	        }
	    }

}