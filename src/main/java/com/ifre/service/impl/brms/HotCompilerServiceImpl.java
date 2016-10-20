package com.ifre.service.impl.brms;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifre.ruleengin.HotDeployment;
import com.ifre.ruleengin.hotcompiler.DynamicEngine;
import com.ifre.service.brms.BizObjServiceI;
import com.ifre.service.brms.HotComplierServiceI;
import com.ifre.service.brms.ProcessExcelServicel;

@Service("hotCompilerService")
public class HotCompilerServiceImpl extends CommonServiceImpl implements HotComplierServiceI{

	public static Logger logger = Logger.getLogger(HotCompilerServiceImpl.class);
	
	@Autowired
	private BizObjServiceI bizObjService;
	@Autowired
	private ProcessExcelServicel processExcelService;
	
	@Override
	public List<Map<String,Object>> hotcompiler(String prodId){
		
		String hql = "select obj.name,obj.SOURCE_CODE content,pckg.ALL_NAME pkgname from BRMS_BIZ_OBJ obj left join brms_rule_pckg  pckg on pckg.id = obj.PCKG_ID"
					+ " where obj.PROD_ID = ?";
		return bizObjService.findForJdbc(hql, new Object[]{prodId});
	}
	
	
	public String  makeFileForJar(String path,String prodId) throws Exception{
		List<Map<String,Object>> list = this.hotcompiler(prodId);
		DynamicEngine de = DynamicEngine.getInstance(); 
		de.javaCodeCompile(list,path); 
		return processExcelService.makeExcel(prodId,path);
	}
	
}
