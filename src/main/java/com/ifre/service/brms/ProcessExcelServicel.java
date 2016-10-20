package com.ifre.service.brms;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;

import com.ifre.exception.IfreException;

public interface ProcessExcelServicel  extends CommonService{

	
	public void process(File f,String orgId,String knowId,String prodId);
	
	public String makeExcel(String id);
	
	public String makeExcel(String id,String path);

	public Map<String, String[][]> grubDeciTableData(String id);
	
	public void proceeExcelData(String id,String data,String mergedata) throws IfreException;
	
	public boolean checkDecitable(String data,String prodId) throws Exception;
	
	public boolean checkDecitable(Map hm,String prodId) throws Exception;
	
	public List<File> makeExcelForCompile(String prodid);
	
}
