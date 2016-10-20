/**
 * 
 * @author majiang
 *
 */
package com.ifre.invoker.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.StatelessKieSession;

import com.ifre.invoker.DecitabInvokerI;
import com.ifre.ruleengin.RefacetHandler;
import com.ifre.ruleengin.hotcompiler.DynamicEngine;

import net.sf.json.JSONObject;
/**
 * 评分卡调用类
 * @author majiang
 *
 */
public class ScoreCardInvoker implements DecitabInvokerI {

	private Logger logger = Logger.getLogger(ScoreCardInvoker.class);
    //** The Constant BASIC. *//*
	
    private Map<String,Object> hmcreate = new HashMap<String,Object>() ;
    
    @SuppressWarnings("rawtypes")
	private List infactList = new ArrayList();
	@Override
	public void makeData(List<Map<String, Object>> list, Map<String, String> applicantMap,int type) throws Exception {
		// TODO Auto-generated method stub
	    if (applicantMap.isEmpty()) {
            throw new IllegalArgumentException("请至少设置一条规则！");
        }
		try{
	 		DynamicEngine de = DynamicEngine.getInstance(); 
    		Map<String,Class<?>> hm = de.javaCodeToMap(list);
	    	for (Map<String,Object> entry : list) {
	    		//Class<?> clazz = HotDeployment.hotComplierEngine(entry);
	    		String name = String.valueOf(entry.get("name"));
	    		if("LoanApplication".equalsIgnoreCase(name)){
	    			Class<?> clazz = hm.get(name);
	    			Object obj = clazz.newInstance();
	    			RefacetHandler.invokeFieldMethod(obj, "setType", new Object[]{type},"int");
	    			hmcreate.put("loanApp", obj);
	    		}else {
	    			Class<?> clazz = hm.get(name);
	    			for (Map.Entry<String, String> bizentry : applicantMap.entrySet()) {
	    				double varValue = -99;
	    				Object obj = clazz.newInstance();
	    				String fieldName = (String) bizentry.getKey();
	    				String value = (String) bizentry.getValue();
	                	
	    				if(StringUtils.isEmpty(value)){
	    					throw new IllegalArgumentException(fieldName+"变量输入值非法");
	    				}
	    				varValue = Double.parseDouble(value);
	    				
	    				RefacetHandler.invokeFieldMethod(obj, "setVarName", new Object[]{fieldName});
	    				RefacetHandler.invokeFieldMethod(obj, "setVarValueCode", new Object[]{value});
	    				RefacetHandler.invokeFieldMethod(obj, "setVarValue", new Object[]{varValue},"double");
	    				infactList.add(obj);
					}
	    			hmcreate.put("infacts", infactList);
	    		}
			}
    	}catch(Exception e){
    		logger.error("创建反射实例错误",e);
    		throw new Exception(e);
    	}
    	
	}
	@Override
	public String getPricingObj(StatelessKieSession ksession, String name) throws Exception {
	  	String result;
    	try{
    		//KieRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.( ksession, "C://drools.log" );
    	
	    	//LoanApplication loanApp = (LoanApplication)loanAppObj;
	    	Object loanAppObj = hmcreate.get("loanApp");
	        if (loanAppObj == null || loanAppObj == null) {
	            throw new NullPointerException("传入对象参数空指针错误！");
	        }
	        
	        if (name == null || "".equals(name)) {
	            throw new IllegalArgumentException("申请对象姓名不能为空，请设置！");
	        }
	        if ((infactList != null) && (infactList.size() > 0)) {
	            RefacetHandler.invokeFieldMethod(loanAppObj,"setTotalScore",new Object[]{0},"int");
	            ksession.execute(infactList);
	            infactList.add(loanAppObj);
	            ksession.execute(infactList);
	            Object totleScore = RefacetHandler.invokeFieldMethod(loanAppObj,"getTotalScore",new Object[]{});
	            RefacetHandler.invokeFieldMethod(loanAppObj,"setTrimScore",new Object[]{totleScore},"int");
	            infactList.clear();
	            infactList.add(loanAppObj);
	            ksession.execute(infactList);
	            JSONObject jb = JSONObject.fromObject(loanAppObj);
	            result = jb.toString();
	        }else{
	        	throw new Exception("传入规则的数据个数不能空");
	        }
	  
    	}catch(Exception e){
    		logger.error("执行规则错误",e);
    		throw new Exception("执行规则错误",e);
    	}
        
    	return result;
	}
	
    



   
}
