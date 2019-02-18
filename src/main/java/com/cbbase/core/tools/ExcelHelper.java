package com.cbbase.core.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * base on jxl and poi
 * @author changbo
 *
 */
public class ExcelHelper {
	
	public static final int READ = 0;
	public static final int WRITE = 1;
	
	public static final int XLS = 0;
	public static final int XLSX = 1;
	
	private String fileName = null;
	private int operateModel;
	private int lineIndex;
	//
	private Workbook wb = null;
	private Sheet sheet = null;
	private WritableWorkbook wwb = null;
	private WritableSheet ws = null;
	//
	private XSSFWorkbook xssfWorkbook = null;
	private XSSFSheet xssfSheet = null;
	private int fileType = XLS;
	
	//default
	private WritableCellFormat format = new WritableCellFormat();

	private ExcelHelper(String fileName, int operateModel){
		this.fileName = fileName;
		this.operateModel = operateModel;
		if(fileName.toLowerCase().endsWith(".xlsx")){
			fileType = XLSX;
		}
	}

	public static ExcelHelper getExcelHelper(String fileName, int operateModel){
		return new ExcelHelper(fileName, operateModel);
	}
	public static ExcelHelper getExcelHelper(File file, int operateModel){
		return new ExcelHelper(file.getAbsolutePath(), operateModel);
	}

	public String getSheetName(int sheetNum){
		if(fileType == XLSX){
			return getSheetNameXLSX(sheetNum);
		}
		File file = new File(fileName);
		if(!file.exists()){
			return null;
		}
		try {
			wb = Workbook.getWorkbook(new FileInputStream(fileName));
			return wb.getSheet(sheetNum).getName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private String getSheetNameXLSX(int sheetNum){
		File file = new File(fileName);
		if(!file.exists()){
			return null;
		}
		try {
			xssfWorkbook = new XSSFWorkbook(new FileInputStream(fileName));
			return xssfWorkbook.getSheetName(sheetNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getSheetCount(){
		if(fileType == XLSX){
			return getSheetCountXLSX();
		}
		File file = new File(fileName);
		if(!file.exists()){
			return 0;
		}
		try {
			wb = Workbook.getWorkbook(new FileInputStream(fileName));
			return wb.getSheets().length;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private int getSheetCountXLSX(){
		File file = new File(fileName);
		if(!file.exists()){
			return 0;
		}
		try {
			xssfWorkbook = new XSSFWorkbook(new FileInputStream(fileName));
			return xssfWorkbook.getNumberOfSheets();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * start with 0
	 * @param sheetNum
	 * @return
	 */
	public ExcelHelper open(int sheetNum){
		if(fileType == XLSX){
			return openXLSX(sheetNum);
		}
		lineIndex = 0;
		try {
			File file = new File(fileName);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			if(operateModel == READ){
				wb = Workbook.getWorkbook(new FileInputStream(fileName));
				sheet = wb.getSheet(sheetNum);
			}else if(operateModel == WRITE){
				wwb = Workbook.createWorkbook(new FileOutputStream(fileName));
				if(wwb.getSheets().length == 0){
					ws = wwb.createSheet(""+sheetNum, sheetNum);
				}else{
					ws = wwb.getSheet(sheetNum);
				}
				//
				format.setVerticalAlignment(VerticalAlignment.CENTRE);
				format.setAlignment(Alignment.LEFT);
				format.setWrap(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this;
		}
		return this;
	}
	
	private ExcelHelper openXLSX(int sheetNum){
		lineIndex = 0;
		try {
			File file = new File(fileName);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			if(!file.exists()){
				file.createNewFile();
			}
			if(operateModel == READ){
				xssfWorkbook = new XSSFWorkbook(new FileInputStream(fileName));
				xssfSheet = xssfWorkbook.getSheetAt(sheetNum);
			}else if(operateModel == WRITE){
				xssfWorkbook = new XSSFWorkbook();
	            xssfSheet = xssfWorkbook.createSheet(""+sheetNum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this;
		}
		return this;
	}
	
	public ExcelHelper setSkipLine(int skipLine){
		lineIndex = skipLine;
		return this;
	}
	
	/**
	 * 默认宽度为10
	 * @param columWide
	 * @return
	 */
	public boolean setColumnView(int[] columWide){
		if(fileType == XLSX){
			return setColumnViewXLSX(columWide);
		}
		if(columWide == null || columWide.length == 0){
			return false;
		}
		for(int i=0; i<columWide.length; i++){
			ws.setColumnView(i, columWide[i] <= 10 ? 10 : columWide[i]);
		}
		return true;
	}

	private boolean setColumnViewXLSX(int[] columWide){
		if(columWide == null || columWide.length == 0){
			return false;
		}
		for(int i=0; i<columWide.length; i++){
			xssfSheet.setColumnWidth(i, columWide[i] <= 0 ? 10 : columWide[i]);
		}
		return true;
	}
	
	public Map<Integer, String> readLine(){
		return readLine(true);
	}
	
	public Map<Integer, String> readLine(boolean ignoreBlankRow){
		if(fileType == XLSX){
			return readLineXLSX(ignoreBlankRow);
		}
		
		if(lineIndex >= sheet.getRows()){
			return null;
		}
		Map<Integer, String> map = getRows(lineIndex);
		lineIndex++;
		
		//if blank, get next row
		if(ignoreBlankRow && isEmptyData(map)){
			return readLine(ignoreBlankRow);
		}
		
		return map;
	}
	
	private Map<Integer, String> readLineXLSX(boolean ignoreBlankRow){
		if(lineIndex > xssfSheet.getLastRowNum()){
			return null;
		}
		Map<Integer, String> map = getRowsXLSX(lineIndex);
		lineIndex++;
		
		//if blank, get next row
		if(ignoreBlankRow && isEmptyData(map)){
			return readLine(ignoreBlankRow);
		}
		
		return map;
	}
	
	public List<Map<Integer, String>> readAllLines(){
		return readAllLines(true);
	}
	
	public void skip(int skip) {
		lineIndex = lineIndex+skip;
	}
	
	public List<Map<Integer, String>> readAllLines(boolean ignoreBlankRow){
		List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
		Map<Integer, String> line = null;
		while((line=readLine()) != null){
			list.add(line);
		}
		close();
		return list;
	}
	
	public boolean writeLine(String[] strs){
		if(fileType == XLSX){
			return writeLineXLSX(strs);
		}
		
		if(lineIndex > 65535){
			System.out.println("[ExcelHelper]lineIndex="+lineIndex);
			return false;
		}
		
		if(!isEmptyData(strs)){
			for(int i=0;i<strs.length; i++){
				if(StringUtil.isEmpty(strs[i])){
					continue;
				}
				try {
					Label label = new Label(i, lineIndex, StringUtil.getValue(strs[i]), format);
					ws.addCell(label);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		lineIndex++;
		return true;
	}

	private boolean writeLineXLSX(String[] strs){
		if(lineIndex > 65535){
			System.out.println("[ExcelHelper]lineIndex="+lineIndex);
			return false;
		}
		
		if(!isEmptyData(strs)){
			Row row = xssfSheet.createRow(lineIndex);
			for(int i=0;i<strs.length; i++){
				if(StringUtil.hasValue(strs[i])){
					org.apache.poi.ss.usermodel.Cell cell = row.createCell(i);
					cell.setCellType(CellType.STRING);
					cell.setCellValue(strs[i]);
				}
			}
		}
		lineIndex++;
		return true;
	}
	
	public boolean writeLine(Map<Integer, String> map){
		if(fileType == XLSX){
			return writeLineXLSX(map);
		}
		
		if(!isEmptyData(map)){
			for(Integer i : map.keySet()){
				if(StringUtil.hasValue(map.get(i))){
					try {
						Label label = new Label(i, lineIndex, StringUtil.getValue(map.get(i)), format);
						ws.addCell(label);
					} catch (Exception e) {
						e.printStackTrace();
						return false;
					}
				}
			}
		}
		lineIndex++;
		return true;
	}

	
	private boolean writeLineXLSX(Map<Integer, String> map){
		if(!isEmptyData(map)){
			Row row = xssfSheet.createRow(lineIndex);
			for(Integer i : map.keySet()){
				if(StringUtil.hasValue(map.get(i))){
					org.apache.poi.ss.usermodel.Cell cell = row.createCell(i);
					cell.setCellValue(map.get(i));
					cell.setCellType(CellType.STRING);
				}
			}
		}
		lineIndex++;
		return true;
	}
	
	public Map<Integer, String> getRows(int index){
		if(fileType == XLSX){
			return getRowsXLSX(index);
		}
		if(index >= 65535){
			System.out.println("[ExcelHelper]index="+index);
			return null;
		}
		Map<Integer, String> map = new HashMap<Integer, String>();
		int wide = sheet.getColumns();
		for(int j=0; j<wide; j++){
			Cell cell = sheet.getCell(j,index);
			map.put(j, StringUtil.getValue(cell.getContents()));
		}
		return map;
	}
	
	private Map<Integer, String> getRowsXLSX(int index){
		if(index >= 65535){
			System.out.println("[ExcelHelper]index="+index);
			return null;
		}
		Map<Integer, String> map = new HashMap<Integer, String>();
		int wide = xssfSheet.getRow(lineIndex).getLastCellNum();
		for(int j=0; j<wide; j++){
			 XSSFCell cell = xssfSheet.getRow(lineIndex).getCell(j);
			 if(cell != null){
				 map.put(j, getCellStringXLSX(cell));
			 }
		}
		return map;
	}
	
	private String getCellStringXLSX(XSSFCell cell){
		if(cell == null){
			return "";
		}
		CellType type = cell.getCellTypeEnum();
		if(type.equals(CellType.BLANK)){
			return "";
		}
		if(type.equals(CellType.BOOLEAN)){
			return ""+cell.getBooleanCellValue();
		}
		if(type.equals(CellType.FORMULA)){
			return ""+cell.getCellFormula();
		}
		if(type.equals(CellType.NUMERIC)){
			return ""+StringUtil.toString(cell.getNumericCellValue());
		}
		if(type.equals(CellType.STRING)){
			return ""+cell.getStringCellValue();
		}
		return cell.toString();
	}

	private boolean isEmptyData(String[] strs){
		boolean isEmpty = true;
		if(strs != null && strs.length > 0){
			for(String i : strs){
				if(StringUtil.hasValue(i)){
					isEmpty = false;
				}
			}
		}
		return isEmpty;
	}
	private boolean isEmptyData(Map<Integer, String> map){
		boolean isEmpty = true;
		if(map != null && map.size() > 0){
			for(Integer i : map.keySet()){
				if(StringUtil.hasValue(map.get(i))){
					isEmpty = false;
				}
			}
		}
		return isEmpty;
	}
	
	public boolean close(){
		if(fileType == XLSX){
			return closeXLSX();
		}
		
		if(wb != null){
			wb.close();
		}
		if(wwb != null){
			try {
				wwb.write();
				wwb.close();
			} catch (Exception e) {
				
			}
		}
		return true;
	}

	private boolean closeXLSX(){
		try {
			if(operateModel == WRITE){
		        //创建文件流   
		        OutputStream outputStream = new FileOutputStream(fileName);  
		        //写入数据   
		        xssfWorkbook.write(outputStream);
		        //关闭文件流   
	            outputStream.flush();
	            outputStream.close();  
			}
			if(xssfWorkbook != null){
				xssfWorkbook.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
}

