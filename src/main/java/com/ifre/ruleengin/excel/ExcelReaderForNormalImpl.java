package com.ifre.ruleengin.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * @author majiang
 *
 */
public class ExcelReaderForNormalImpl implements ExcelReaderI{
	private Logger log = Logger.getLogger(ExcelReaderForNormalImpl.class);
	private POIFSFileSystem fs;
	private HSSFWorkbook wb;
	

	/**
	 * 读取Excel数据内容
	 * 
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 */
	public List<Map<Integer, String>> readExcelContent(InputStream is) {
		
		String str = "";
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Map<Integer, String>> relist = new ArrayList<Map<Integer, String>>();
		int sheetNum = wb.getNumberOfSheets();
		for (int x = 0; x < sheetNum; x++) {
			HSSFSheet sheet = wb.getSheetAt(x);
			// 得到总行数
			String json = getAllMergedRegion(sheet);
			int rowNum = sheet.getLastRowNum();
			HSSFRow row = sheet.getRow(0);
			int colNum = row.getPhysicalNumberOfCells(); // 获取有数据的列的个数
			// int colNum = 15;
			// 正文内容应该从第二行开始,第一行为表头的标题
			Map<Integer, String> content = new HashMap<Integer, String>();
			for (int i = 0; i <= rowNum; i++) {
				row = sheet.getRow(i);
				int j = 0;
			/*	if(row == null){
					continue;
				}*/
				StringBuilder sbder = new StringBuilder();
				if(log.isDebugEnabled()){
					log.debug("colNum :" +colNum);
				}
			
				while (j < colNum) {
					Cell c = row.getCell(j);
					if(c == null) continue;
					
					if(log.isDebugEnabled()){
						log.debug("i:" +i +" j:"+j);
					}
					boolean isMerge = isMergedRegion(sheet, i, c.getColumnIndex());
					// 判断是否具有合并单元格
					String cellStr = "";
					if (isMerge) {
						cellStr = getMergedRegionValue(sheet, row.getRowNum(), c.getColumnIndex());
					}else{
						cellStr = getCellFormatValue(row.getCell(j)).trim();
					/*	if (cell == "") {
							j++;
							continue;
						}*/
					}
					//str += cellStr + "#";
					sbder.append(cellStr).append("#");
					j++;
				}
				sbder.toString().trim();
				if(log.isDebugEnabled()){
					log.debug("解析第"+i+"行："+sbder);
				}
				// System.out.println(str);
				content.put(i, sbder.toString());
				str = "";
			}
			content.put(9999, json);
			relist.add(content);
			
		}
		log.info(relist);
		System.out.println(relist);
		return relist;
	}
	//是否包含合并单元格
	private boolean isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}
	public String getMergedRegionValue(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();

		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();

			if (row >= firstRow && row <= lastRow) {

				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return getCellValue(fCell);
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 * @return
	 */
	public String getCellValue(Cell cell) {
		if (cell == null)
			return "";
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			return cell.getStringCellValue();
		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			return cell.getCellFormula();
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return String.valueOf((int)cell.getNumericCellValue());
		}
		return "";
	}
	
	private String getCellFormatValue(HSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
				case HSSFCell.CELL_TYPE_NUMERIC: {
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cellvalue = cell.getStringCellValue().trim();
					break;
				}
				case HSSFCell.CELL_TYPE_FORMULA: {
					// 判断当前的cell是否为Date
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
	
						// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
						Date date = cell.getDateCellValue();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						cellvalue = sdf.format(date);
	
					}
					// 如果是纯数字
					else {
						// 取得当前Cell的数值
						cellvalue = String.valueOf(cell.getRichStringCellValue()).trim();
					}
					break;
				}
				// 如果当前Cell的Type为STRIN
				case HSSFCell.CELL_TYPE_STRING:
					// 取得当前的Cell字符串
					cellvalue = cell.getRichStringCellValue().getString();
					break;
				// 默认的Cell值
				default:
					cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;

	}
	
	private String getAllMergedRegion(Sheet sheet) {
		JSONArray ja = new JSONArray();
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			JSONObject jo = new JSONObject();
			CellRangeAddress range = sheet.getMergedRegion(i);
			jo.put("firstColumn", range.getFirstColumn());
			jo.put("lastColumn", range.getLastColumn());
			jo.put("firstRow", range.getFirstRow());
			jo.put("lastRow", range.getLastRow());
			ja.add(jo);
		}
		return ja.toString();
	}


	public static void main(String[] args) throws FileNotFoundException {
		File f = new File("E://access_rule.xls");
		ExcelReaderForNormalImpl reader = new ExcelReaderForNormalImpl();
		List<Map<Integer,String>> hm = reader.readExcelContent(new FileInputStream(f));
		System.out.println(hm);
		
	}

}
