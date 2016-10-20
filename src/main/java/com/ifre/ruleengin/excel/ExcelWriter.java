package com.ifre.ruleengin.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import com.ifre.ruleengin.RuleFactory;  
  
  
/** 
 * 生成导出Excel文件对象 
 *  
 * @author majiang 
 *  
 */  
public class ExcelWriter {  
  
    // 定制浮点数格式  
    private static String NUMBER_FORMAT = "#,##0.00";  
  
    // 定制日期格式  
    private static String DATE_FORMAT = "m/d/yy"; // "m/d/yy h:mm"  
  
    private OutputStream out = null;  
  
    private HSSFWorkbook workbook = null;  
  
    private HSSFSheet sheet = null;  
  
    private HSSFRow row = null;  
  
    public ExcelWriter() {  
    }  
  
    /** 
     * 初始化Excel 
     *  
     */  
    public ExcelWriter(OutputStream out) {  
        this.out = out;  
        this.workbook = new HSSFWorkbook();  
        this.sheet = workbook.createSheet();  
    }  
  
    /** 
     * 导出Excel文件 
     *  
     * @throws IOException 
     */  
    public void export() throws FileNotFoundException, IOException {  
        try {  
            workbook.write(out);  
            out.flush();  
            out.close();  
        } catch (FileNotFoundException e) {  
            throw new IOException(" 生成导出Excel文件出错! ");  
        } catch (IOException e) {  
            throw new IOException(" 写入Excel文件出错! ");  
        }  
  
    }  
  
    /** 
     * 增加一行 
     *  
     * @param index 
     *            行号 
     */  
    public void createRow(int index) {  
        this.row = this.sheet.createRow(index);  
    }  
      
    /** 
     * 获取单元格的值 
     *  
     * @param index 
     *            列号 
     */  
    public String getCell(int index) {  
        HSSFCell cell = this.row.getCell(index);  
        String strExcelCell = "";  
        if (cell != null) { // add this condition  
            // judge  
            switch (cell.getCellType()) {  
            case HSSFCell.CELL_TYPE_FORMULA:  
                strExcelCell = "FORMULA ";  
                break;  
            case HSSFCell.CELL_TYPE_NUMERIC: {  
                strExcelCell = String.valueOf(cell.getNumericCellValue());  
            }  
                break;  
            case HSSFCell.CELL_TYPE_STRING:  
                strExcelCell = cell.getStringCellValue();  
                break;  
            case HSSFCell.CELL_TYPE_BLANK:  
                strExcelCell = "";  
                break;  
            default:  
                strExcelCell = "";  
                break;  
            }  
        }  
        return strExcelCell;  
    }  
  
    /** 
     * 设置单元格 
     *  
     * @param index 
     *            列号 
     * @param value 
     *            单元格填充值 
     */  
    public void setCell(int index, int value) {  
        HSSFCell cell = this.row.createCell(index);  
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
        cell.setCellValue(value);  
    }  
  
    /** 
     * 设置单元格 
     *  
     * @param index 
     *            列号 
     * @param value 
     *            单元格填充值 
     */  
    public void setCell(int index, double value) {  
        HSSFCell cell = this.row.createCell(index);  
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
        cell.setCellValue(value);  
        HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式  
        HSSFDataFormat format = workbook.createDataFormat();  
        cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // 设置cell样式为定制的浮点数格式  
        cell.setCellStyle(cellStyle); // 设置该cell浮点数的显示格式  
    }  
  
    /** 
     * 设置单元格 
     *  
     * @param index 
     *            列号 
     * @param value 
     *            单元格填充值 
     */  
    public void setCell(int index, String value) {  
        HSSFCell cell = this.row.createCell((int) index);  
        HSSFCellStyle style = workbook.createCellStyle();   
        style.setBorderBottom((short)1);// 下边框   
        style.setBorderLeft((short)1);// 左边框   
        style.setBorderRight((short)1);// 右边框   
        style.setBorderTop((short)1);// 上边框   
        cell.setCellStyle(style);
        
        cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
        cell.setCellValue(value);  
    }  
      
    /** 
     * 设置单元格 
     *  
     * @author zgm 
     * @param index 
     *            列号 
     * @param value 
     *            单元格填充值 
     */  
    public void setCellWithAlign(int index, String value) {  
        this.row.setHeightInPoints(30);  
        HSSFCell cell = this.row.createCell(index);  
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
        cell.setCellValue(value);  
        HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式  
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
          
        cellStyle.setLocked(true);  
        // Create a new font and alter it.  
        HSSFFont font = workbook.createFont();  
        font.setFontHeightInPoints((short)14);  
        font.setFontName("rial Black");  
        font.setItalic(false);  
        font.setStrikeout(false);  
          
        cellStyle.setFont(font);  
        cell.setCellStyle(cellStyle);  
        
    }  
      
    /** 
     * 设置单元格 
     *  
     * @author zgm 
     * @param index 
     *            列号 
     * @param value 
     *            单元格填充值 
     */  
    public void setCellWithAlign(int index, String value, int color) {  
//      this.sheet.autoSizeColumn((short) index);  
        this.row.setHeightInPoints(30);  
        HSSFCell cell = this.row.createCell((int) index);  
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
        cell.setCellValue(value);  
        HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式  
        cellStyle.setLocked(true);  
        // Create a new font and alter it.  
          
        HSSFFont font = workbook.createFont();  
        if(color == -1){  
             font.setColor(HSSFColor.WHITE.index);  
        }else if(color > -1){  
            font.setColor((short)color);  
        }  
         
        cellStyle.setFont(font);  
        cell.setCellStyle(cellStyle);  
  
    }  
      
      
      
      
    /** 
     * 设置单元格 
     *  
     * @param index 
     *            列号 
     * @param value 
     *            单元格填充值 
     */  
    public void setCell(int index, Calendar value) {  
        HSSFCell cell = this.row.createCell((int) index);  
        cell.setCellValue(value.getTime());  
        HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式  
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(DATE_FORMAT)); // 设置cell样式为定制的日期格式  
        cell.setCellStyle(cellStyle); // 设置该cell日期的显示格式  
    }  
      
      
    public HSSFRow getRow(int index){  
        return this.sheet.getRow(index);  
    }  
      
    public void setRow(int index){  
        this.row = this.sheet.getRow(index);  
    }  
      
    public void mergeCell(int firstRowNum, int firstColumn, int secondRowNum, int secondColumnNum ){  
//        sheet.addMergedRegion(new Region(firstRowNum, (short)firstColumn, secondRowNum, (short)secondColumnNum)); 
        sheet.addMergedRegion(new CellRangeAddress(firstRowNum, secondRowNum, firstColumn, secondColumnNum));
//      sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 2));  
          
    }  
      
    public byte[] writeToBurrer() throws Exception {  
        workbook.write(out);  
       
        byte[] buffer = ((ByteArrayOutputStream) out).toByteArray();  
        out.flush();  
        out.close();  
          
        return buffer;  
  
    }  
      
    public static void main(String[] args) throws IOException {  
        //System.out.println(" 开始导出Excel文件 ");  
    	File in = new File("E://access_rule.xls");
    	ExcelReaderForNormalImpl reader = new ExcelReaderForNormalImpl();
    	List<Map<Integer,String>> list = reader.readExcelContent(new FileInputStream(in));
    	System.out.println(list);
    	for (int x = 0; x < list.size(); x++) {
    	  	 Map<Integer, String> map = list.get(x);
    	        File f = new File("E://create//qt"+x+".xls");  
    	        if(!f.exists()){
    	        	f.createNewFile();
    	        }
    	        ExcelWriter e = new ExcelWriter();  
    	        try {  
    	            e = new ExcelWriter(new FileOutputStream(f));  
    	        } catch (FileNotFoundException e1) {  
    	            e1.printStackTrace();  
    	        } 
    	       
    	      
    	       for (int i = 0; i < map.size(); i++) {
    	    	   String content =  map.get(i);
    	    	   String[] arr = content.split("#");
    	    	   e.createRow(i);
    	    	   for (int j = 0; j < arr.length; j++) {
    	    		 
    	    	    	e.setCell(j,arr[j]);
    	    	   }
    	       }
    	       try {  
    	            e.export();  
    	            System.out.println(" 导出Excel文件[成功] ");  
    	        } catch (IOException ex) {  
    	            System.out.println(" 导出Excel文件[失败] ");  
    	            ex.printStackTrace();  
    	        }  
		}
   /* 	RuleFactory factory = new RuleFactory();
    	StatefulKnowledgeSession ksession = factory.complile("E://create//");
    	ksession.insert(object);
    	ksession.insert(object);
    	ksession.insert(object);*/
    	
  
/*        e.createRow(0);  
        e.mergeCell(0, 1, 0, 2);  
        e.setCell(0, "试题编码 ");  
        e.setCell(1, "题型");  
        e.setCell(2, "分值");  
        e.setCell(3, "难度");  
        e.setCell(4, "级别");  
        e.setCell(5, "知识点");  
  
        e.createRow(1);  
        e.setCell(0, "t1");  
        e.setCell(1, 1);  
        e.setCell(2, 3.0);  
        e.setCell(3, 1);  
        e.setCell(4, "重要");  
        e.setCell(5, "专业");  
          
        //合并单元格  
        e.createRow(2);  
        e.setCell(0, "此为合并后的单元格");  
        e.mergeCell(2, 0, 2, 1);  */
  
    
    }  
  
}  