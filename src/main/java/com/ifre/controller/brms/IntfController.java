package com.ifre.controller.brms;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ifre.invoker.DecitabInvokerI;
import com.ifre.invoker.DecitableInvokerFactroy;
import com.ifre.ruleengin.RuleFactory;
import com.ifre.service.brms.HotComplierServiceI;
import com.ifre.service.brms.ProcessExcelServicel;

@Scope("prototype")
@Controller
@RequestMapping("/intfController")
public class IntfController  extends BaseController{

	@Autowired
	private ProcessExcelServicel processExcelService;
	
	@Autowired 
	private HotComplierServiceI hotComplierService;
	
    /** The Constant ANTIFRAUD. */
	
    @RequestMapping(params = "callModelFire")
    @ResponseBody
	public String callModelFire(){
    	String returnStr = "";
    	try{
		  Map<String,String> applicantMap = new HashMap<String,String>();
		  DecitabInvokerI  antiFraudModelClient = DecitableInvokerFactroy.create(DecitableInvokerFactroy.FQZ);
	      applicantMap = new HashMap<String, String>();
	      applicantMap.put("C0101", "C0103");
	      applicantMap.put("D0104", "D0104");
	      applicantMap.put("D0201", "D0201");
	      applicantMap.put("D0402", "D0402");
//	      applicantMap.put("D0505", "D05051");
	      int type = 71;
	      List<Map<String,Object>> list = hotComplierService.hotcompiler("8a8080a8555369010155537269110003");
	      antiFraudModelClient.makeData(list, applicantMap,type);
	      RuleFactory factory = new RuleFactory();
	      List<File> path = processExcelService.makeExcelForCompile("8a8080a8555369010155537269110003");
	      StatelessKieSession ks = factory.createKsession(path);
	      returnStr =antiFraudModelClient.getPricingObj(ks," ");
	      
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return returnStr;
	}
    
    @RequestMapping(params = "makejar")
    @ResponseBody
    public AjaxJson makejar() throws Exception{
    	hotComplierService.makeFileForJar("E://target//", "8a8080bf55a51e1d0155a520eef50003");
    	return new AjaxJson();
    }
    
    @RequestMapping(params = "showLogin")
    @ResponseBody
    public ModelAndView showLogin() throws Exception{
    	return new ModelAndView("");
    }
}
