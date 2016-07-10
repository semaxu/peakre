package com.ebao.ap99.accounting.util;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.ebao.ap99.accounting.model.ExceptionContractRecord;
import com.ebao.ap99.accounting.model.RevaluationDetailModel;


public class GenerateExcel {
	
	private  CellStyle cellStyles = null;
	private  CellStyle cellStyle = null;
	private  CellStyle cellStylesbrokers = null;
	private  CellStyle cellStylebrokers = null;
	
	public  ByteArrayOutputStream excelGenerate(List<ExceptionContractRecord> excepContList) throws Exception {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		cellStyles = getExcelHeaderStyle(workbook);
		cellStyle = getExcelNormalStyle(workbook);
		cellStylesbrokers = getExcelHeaderStyleBrokersName(workbook);
		cellStylebrokers = getExcelNormalStyleBrokersName(workbook);
		
		HSSFSheet worksheet = generateExcelBaseFormat(workbook);	
		generateExcelDetailHeader(worksheet);
		generateAutoFit(worksheet, 16);
		generateExcelDetail(worksheet,excepContList);
		generateAutoFit(worksheet, 16);
		
		 ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		    workbook.write(outByteStream);
		return outByteStream;
	}
	
	
	public  ByteArrayOutputStream revaluateExcelGenerate(List<RevaluationDetailModel> revalResultList) throws Exception {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		cellStyles = getExcelHeaderStyle(workbook);
		cellStyle = getExcelNormalStyle(workbook);
		cellStylesbrokers = getExcelHeaderStyleBrokersName(workbook);
		cellStylebrokers = getExcelNormalStyleBrokersName(workbook);
		
		HSSFSheet worksheet = generateRevaluateExcelBaseFormat(workbook);	
		generateRevaluateExcelDetailHeader(worksheet);
		generateAutoFit(worksheet, 16);
		generateRevaluateExcelDetail(worksheet,revalResultList);
		generateAutoFit(worksheet, 16);
		
		 ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		    workbook.write(outByteStream);
		return outByteStream;
	}
	
	private  CellStyle getExcelHeaderStyle(HSSFWorkbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setWrapText(false);
		style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setWrapText(true);
		Font fnt = wb.createFont();
		fnt.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fnt.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(fnt);
		return style;
	}
	
	private  CellStyle getExcelNormalStyle(HSSFWorkbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setWrapText(false);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setAlignment(CellStyle.ALIGN_CENTER);
		return style;
	}
	
	private  CellStyle getExcelHeaderStyleBrokersName(HSSFWorkbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setWrapText(false);
		style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setAlignment(CellStyle.ALIGN_LEFT);
		Font fnt = wb.createFont();
		fnt.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fnt.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(fnt);
		return style;
	}
	
	private  CellStyle getExcelNormalStyleBrokersName(HSSFWorkbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setWrapText(false);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setAlignment(CellStyle.ALIGN_LEFT);
		return style;
	}
	
 
	private  HSSFSheet generateExcelBaseFormat(HSSFWorkbook wb)
			throws Exception {
		//sheet name : "Exception Record"
		HSSFSheet worksheet = wb.createSheet("Exception Record");
		return worksheet;
	}
	
	private  HSSFSheet generateRevaluateExcelBaseFormat(HSSFWorkbook wb)
			throws Exception {
		//sheet name : "Revaluation Record"
		HSSFSheet worksheet = wb.createSheet("Revaluation Record");
		return worksheet;
	}
	
	private  void generateExcelDetailHeader(HSSFSheet worksheet) throws Exception {
		HSSFRow rowHeading = worksheet.createRow(0);
		int colStart=0;
		HSSFCell cell0 = rowHeading.createCell(colStart++);
		cell0.setCellValue("ContractID");
		cell0.setCellStyle(cellStyles);
		HSSFCell cell1 = rowHeading.createCell(colStart++);
		cell1.setCellValue("Reviewed");
		cell1.setCellStyle(cellStyles);
}
	
	
	private  void generateRevaluateExcelDetailHeader(HSSFSheet worksheet) throws Exception {
		HSSFRow rowHeading = worksheet.createRow(0);
		int colStart=0;
		HSSFCell cell0 = rowHeading.createCell(colStart++);
		cell0.setCellValue("Contract ID");
		cell0.setCellStyle(cellStyles);
		
		HSSFCell cell1 = rowHeading.createCell(colStart++);
		cell1.setCellValue("Entry code");
		cell1.setCellStyle(cellStyles);
		
		HSSFCell cell2 = rowHeading.createCell(colStart++);
		cell2.setCellValue("Entry item");
		cell2.setCellStyle(cellStyles);
		
		
		HSSFCell cell3 = rowHeading.createCell(colStart++);
		cell3.setCellValue("OC");
		cell3.setCellStyle(cellStyles);
		
		HSSFCell cell4 = rowHeading.createCell(colStart++);
		cell4.setCellValue("Amount(OC)");
		cell4.setCellStyle(cellStyles);
		
		HSSFCell cell5 = rowHeading.createCell(colStart++);
		cell5.setCellValue("Monthly exchange rate");
		cell5.setCellStyle(cellStyles);
		
		HSSFCell cell6 = rowHeading.createCell(colStart++);
		cell6.setCellValue("Amount(USD)");
		cell6.setCellStyle(cellStyles);
		
		HSSFCell cell7 = rowHeading.createCell(colStart++);
		cell7.setCellValue("Month end exchange rate");
		cell7.setCellStyle(cellStyles);
		
		HSSFCell cell8 = rowHeading.createCell(colStart++);
		cell8.setCellValue("Amount(USD)");
		cell8.setCellStyle(cellStyles);
		
		HSSFCell cell9 = rowHeading.createCell(colStart++);
		cell9.setCellValue("Revaluation amount (USD)");
		cell9.setCellStyle(cellStyles);
}
	
	private  void generateAutoFit(HSSFSheet worksheet, int colCounts) {
		int i=0;
		//contractID
		worksheet.autoSizeColumn(i++);
		//reviewedFlag
		worksheet.autoSizeColumn(i++);

	}
	
	
	private  void generateExcelDetail(HSSFSheet worksheet,List<ExceptionContractRecord> excepContList) throws Exception {

		int iRow = 1;
		int colStart=0;
		for (ExceptionContractRecord exceptContRecord : excepContList) {
			HSSFRow Row_Value = worksheet.createRow(iRow++);
			colStart=0;
			//contractID
			HSSFCell cells0 = Row_Value.createCell(colStart++);
			cells0.setCellValue(exceptContRecord.getContractID());
			cells0.setCellStyle(cellStyle);

			//reviewedFlag
			HSSFCell cells1 = Row_Value.createCell(colStart++);
			cells1.setCellValue(exceptContRecord.getReviewedFlag());
			cells1.setCellStyle(cellStyle);
			
		}
	}
	
	
	private  void generateRevaluateExcelDetail(HSSFSheet worksheet,List<RevaluationDetailModel> revalResultList) throws Exception {

		int iRow = 1;
		int colStart=0;
		for (RevaluationDetailModel revaluationDetail : revalResultList) {
			HSSFRow Row_Value = worksheet.createRow(iRow++);
			colStart=0;
			//contractID
			HSSFCell cells0 = Row_Value.createCell(colStart++);
			cells0.setCellValue(revaluationDetail.getContractID());
			cells0.setCellStyle(cellStyle);

			//EntryCode
			HSSFCell cells1 = Row_Value.createCell(colStart++);
			cells1.setCellValue(revaluationDetail.getEntryCode());
			cells1.setCellStyle(cellStyle);
			
			//EntryItem
			HSSFCell cells2 = Row_Value.createCell(colStart++);
			cells2.setCellValue(revaluationDetail.getEntryItem());
			cells2.setCellStyle(cellStyle);
			
			//CurrencyOC
			HSSFCell cells3 = Row_Value.createCell(colStart++);
			cells3.setCellValue(revaluationDetail.getCurrencyOC());
			cells3.setCellStyle(cellStyle);
			
			//AmountOC
			HSSFCell cells4 = Row_Value.createCell(colStart++);
			cells4.setCellValue(revaluationDetail.getAmountOC().toString());
			cells4.setCellStyle(cellStyle);
			
			//OriginalRate
			HSSFCell cells5 = Row_Value.createCell(colStart++);
			cells5.setCellValue(revaluationDetail.getOriginalRate().toString());
			cells5.setCellStyle(cellStyle);
			
			
			//AmountUSD
			HSSFCell cells6 = Row_Value.createCell(colStart++);
			cells6.setCellValue(revaluationDetail.getAmountUSD().toString());
			cells6.setCellStyle(cellStyle);
			
			
			//AmountOC
			HSSFCell cells7 = Row_Value.createCell(colStart++);
			cells7.setCellValue(revaluationDetail.getCurrentRate().toString());
			cells7.setCellStyle(cellStyle);
			
			//CurrentAmountUSD
			HSSFCell cells8 = Row_Value.createCell(colStart++);
			cells8.setCellValue(revaluationDetail.getCurrentAmountUSD().toString());
			cells8.setCellStyle(cellStyle);
					
			//RevaluationAmountUSD
			HSSFCell cells9 = Row_Value.createCell(colStart++);
			cells9.setCellValue(revaluationDetail.getRevaluationAmountUSD().toString());
			cells9.setCellStyle(cellStyle);
			
		
			
		}
	}

}
