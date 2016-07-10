package com.ebao.ap99.accounting.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.ebao.ap99.parent.context.AppContext;

public class test {

	private static CellStyle cellStyles = null;
	private static CellStyle cellStyle = null;
	private static CellStyle cellStylesbrokers = null;
	private static CellStyle cellStylebrokers = null;

	public static void main(String[] args) throws Exception {
		

		 String currecy = NumberFormat.getNumberInstance().format(0);
		 
		 String str ="17.5";   
		 BigDecimal b = new BigDecimal(str);    
		 
		 String currecy1 =  NumberFormat.getInstance().format(b);
		 
		 	
//		 //,代表分隔符     								
//		 //0.后面的##代表位数 如果换成0 效果就是位数不足0补齐     
//		 DecimalFormat d1 =new DecimalFormat("#,##0.####;(#)");  	 
//		 System.out.println(d1.format(b));

	        System.out.println("转换成Currency格式："+currecy1);
	        
	        String amount1 = "13,000.00";   
	        double d1 = new DecimalFormat().parse(amount1).doubleValue();
	       System.out.println(String.valueOf(d1));



//		Calendar cal = Calendar.getInstance();
//        cal.setTime(AppContext.getSysDate());
//        cal.add(Calendar.DATE, 2);
//        
//       
//        System.out.println( AppContext.getSysDate().compareTo(cal.getTime()));
		
//		
//		String path = "D:/Exception";
//	    String fileName = "EXCEPTION_RECORD" + ".xls";
//
//	    File files = new File(path);
//	    path = files.getAbsolutePath();
//
//	    File fil = new File(path + "/" + fileName);//"Parcelas Pendentes - AIG.pdf");
//	    File _parentDir = new File(fil.getParent());
//
//	    if (!_parentDir.isDirectory()) {
//	      _parentDir.mkdirs();
//	    }
//
//	    boolean newFileFlag = fil.createNewFile();
//	    if (!newFileFlag) {
//	      // exist repeat file
//	      //throw new Exception("exist repeat file");
//	      fil.delete();
//	      fil.createNewFile();
//	    }
//	    
//	    ByteArrayOutputStream outByteStream = excelGenerate();
//			
//	    byte[] outArray = outByteStream.toByteArray();
//
//	    OutputStream file = new FileOutputStream(fil);
//	    file.write(outArray);
//	    file.close();

	}
	
	
	
	private static ByteArrayOutputStream excelGenerate() throws Exception {
		 //(1) init
		HSSFWorkbook workbook = new HSSFWorkbook();

		cellStyles = getExcelHeaderStyle(workbook);
		cellStyle = getExcelNormalStyle(workbook);
		cellStylesbrokers = getExcelHeaderStyleBrokersName(workbook);
		cellStylebrokers = getExcelNormalStyleBrokersName(workbook);
		//(2)
		HSSFSheet worksheet = generateExcelBaseFormat(workbook);
		
		//(3)
		generateExcelDetailHeader(worksheet);
		generateAutoFit(worksheet, 16);
		generateExcelDetail(worksheet);
		generateAutoFit(worksheet, 16);
		
		 ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		    workbook.write(outByteStream);
		return outByteStream;
	}
	
	private static CellStyle getExcelHeaderStyle(HSSFWorkbook wb) {
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
	
	private static CellStyle getExcelNormalStyle(HSSFWorkbook wb) {
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
	
	private static CellStyle getExcelHeaderStyleBrokersName(HSSFWorkbook wb) {
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
	
	private static CellStyle getExcelNormalStyleBrokersName(HSSFWorkbook wb) {
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
	
  
	private static HSSFSheet generateExcelBaseFormat(HSSFWorkbook wb)
			throws Exception {
		//sheet name : "Relat髍io atrasado Brokers pagamento"
		HSSFSheet worksheet = wb
				.createSheet("Exception Record");

		return worksheet;
	}
	
	private static void generateExcelDetailHeader(HSSFSheet worksheet) throws Exception {

		HSSFRow rowHeading = worksheet.createRow(0);
		int colStart=0;
		HSSFCell cell0 = rowHeading.createCell(colStart++);
		
		cell0.setCellValue("ContractID");
		cell0.setCellStyle(cellStyles);

		HSSFCell cell1 = rowHeading.createCell(colStart++);
		cell1.setCellValue("Reviewed");
		cell1.setCellStyle(cellStyles);

		
}
	
	private static void generateAutoFit(HSSFSheet worksheet, int colCounts) {
		int i=0;
		//Produto
		worksheet.autoSizeColumn(i++);
		//Nome Cliente
		worksheet.autoSizeColumn(i++);
		//N鷐ero Ap髄ice/Endosso

	}
	
	
	private static void generateExcelDetail(HSSFSheet worksheet) throws Exception {

		int iRow = 1;
		int colStart=0;
		Double totalValue = 0.0;
		int totalrecords = 0;
	//	for (ProposalPendencyAnslysisVO vo : queryRef) {
			HSSFRow Row_Value = worksheet.createRow(iRow++);
			totalrecords++;
			colStart=0;
			// pendingTime
			HSSFCell cells0 = Row_Value.createCell(colStart++);
			cells0.setCellValue("123456");
			cells0.setCellStyle(cellStyle);

			// quotationNumber
			HSSFCell cells1 = Row_Value.createCell(colStart++);
			cells1.setCellValue("true");
			cells1.setCellStyle(cellStyle);
			
	//	}
	}

}
