package com.ifre.service.brms;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;

public interface HotComplierServiceI extends CommonService{

	
	public List<Map<String,Object>> hotcompiler(String prodId);
	
	public String  makeFileForJar(String path,String prodId) throws Exception;
	
}
