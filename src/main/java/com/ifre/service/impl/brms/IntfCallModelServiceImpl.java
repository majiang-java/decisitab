package com.ifre.service.impl.brms;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifre.invoker.DecitabInvokerI;
import com.ifre.invoker.DecitableInvokerFactroy;
import com.ifre.ruleengin.RuleFactory;
import com.ifre.service.brms.HotComplierServiceI;
import com.ifre.service.brms.IntfCallModelServiceI;
import com.ifre.service.brms.ProcessExcelServicel;

@Service("intfCallModelService")
@Transactional
public class IntfCallModelServiceImpl implements IntfCallModelServiceI{

	private Logger log = Logger.getLogger(IntfCallModelServiceImpl.class);
	@Autowired
	private ProcessExcelServicel processExcelService;
	
	@Autowired
	private  HotComplierServiceI hotComplierService;
    /** The Constant ANTIFRAUD. */
    private static final int ANTIFRAUD = 71;
	
    @Override
	public String callModelFire(Map<String,String> applicantMap){
       	String returnStr = "";
    	try{
    	  String prodId = applicantMap.get("prodId");
    	  applicantMap.remove("prodId");
    	   int type = Integer.valueOf(applicantMap.get("type"));
			DecitabInvokerI antiFraudModelClient = DecitableInvokerFactroy.create(DecitableInvokerFactroy.FQZ);
			applicantMap = new HashMap<String, String>();
			List<Map<String, Object>> list = hotComplierService.hotcompiler(prodId);
			RuleFactory factory = new RuleFactory();
			antiFraudModelClient.makeData(list, applicantMap,type);
			List<File> files = processExcelService.makeExcelForCompile(prodId);
			StatelessKieSession ks = factory.createKsession(files);
			returnStr = antiFraudModelClient.getPricingObj(ks, " ");

			for (File file : files) {
				if (file.exists())
					file.delete();
			}
	      
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return returnStr;
	}
}
