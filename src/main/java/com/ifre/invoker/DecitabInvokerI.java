/**
 * 
 * @author majiang
 *
 */
package com.ifre.invoker;

import java.util.List;
import java.util.Map;

import org.kie.api.runtime.StatelessKieSession;

import com.ifre.entity.brms.BizObjEntity;

public interface DecitabInvokerI {

	public String getPricingObj(StatelessKieSession ks,String name) throws Exception;
	
	public void makeData(List<Map<String,Object>> list,Map<String,String> applicantMap,int type) throws Exception;
}
