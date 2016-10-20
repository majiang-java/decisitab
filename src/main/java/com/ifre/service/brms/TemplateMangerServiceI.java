package com.ifre.service.brms;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;

import com.ifre.exception.IfreException;

public interface TemplateMangerServiceI extends CommonService{

	String makeExcel(String prodid);

	String makeExcel(String prodid, String srcPath);

	List<File> makeExcelForCompile(String prodid);

	Map<String, String[][]> grubDeciTableData(String id);

	void proceeExcelData(String id, String data, String mergedata) throws IfreException;


	void process(File f, String typeId) throws Exception;

	String findIdByCode(String typeCode);

	void cloneTemplateToBrms(String departId, String knowId, String prodId, String typeCode);
}
