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

import org.apache.commons.lang.StringUtils;
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
 * 反欺诈模型代理类.
 */
public class AntifraudInvoker implements DecitabInvokerI{


    private static Logger logger = Logger.getLogger(AntifraudInvoker.class);
    
    private static final String NULLVALUE = "无";
    
    private Map<String,Object> hmcreate = new HashMap<String,Object>() ;
    
    @SuppressWarnings("rawtypes")
	private List infactList = new ArrayList();
    
    @SuppressWarnings({ "unchecked" })
	@Override
    public void makeData(List<Map<String,Object>> list,Map<String,String> applicantMap,int type) throws Exception{
    	try{
    		DynamicEngine de = DynamicEngine.getInstance(); 
    		if(applicantMap.isEmpty()){
    			throw new Exception("至少提供一条规则");
    		}
    		Map<String,Class<?>> hm = de.javaCodeToMap(list);
	    	for (Map<String,Object> entry : list) {
	    		//Class<?> clazz = HotDeployment.hotComplierEngine(entry);
	    		String name = String.valueOf(entry.get("name"));
	    		if("LoanApplication".equalsIgnoreCase(name)){
	    			//Class<?> clazz = hm.get("name");
	    			//Class<?> clazz = HotDeployment.hotComplierEngine(entry);
	    			Class<?> clazz =  hm.get(name);
	    			Object obj = clazz.newInstance();
	    			RefacetHandler.invokeFieldMethod(obj, "setType", new Object[]{type},"int");
    				RefacetHandler.invokeFieldMethod(obj, "setName", new Object[]{" "});
	    			hmcreate.put("loanApp", obj);
	    		}else if("ScoreCard".equalsIgnoreCase(String.valueOf(entry.get("name")))){
	    			//Class<?> clazz = de.javaCodeToObject(entry);
	    			//Object obj =  hm.get(name);
	    			//Class<?> clazz = HotDeployment.hotComplierEngine(entry);
	    			Class<?> clazz =  hm.get(name);
	    			for (Map.Entry<String, String> bizentry : applicantMap.entrySet()) {
	    				//Object obj = clazz.newInstance();
	    			
		    			  Object obj = clazz.newInstance();
	    				  String tempValue = (String)bizentry.getValue();
	    			      String tempVarCode = tempValue.substring(0, 5);
	    			      String tempVarCodeFreq = tempValue.substring(5);
	    			      if ((! com.ifre.util.StringUtils.isNumbersOrLetters(tempVarCode)) || ((! com.ifre.util.StringUtils.isNumbers(tempVarCodeFreq)) && 
	    			        (!tempVarCodeFreq
	    			        .equals("")))) {
	    			        throw new IllegalArgumentException(bizentry.getKey() + "变量输入值非法！");
	    			      }
	    			      int varCodeFreq = tempVarCodeFreq.equals("") ? 1 : Integer.valueOf(tempVarCodeFreq).intValue();

	    				RefacetHandler.invokeFieldMethod(obj, "setVarName", new Object[]{tempValue});
	    				RefacetHandler.invokeFieldMethod(obj, "setVarValueCode", new Object[]{tempVarCode});
	    				RefacetHandler.invokeFieldMethod(obj, "setVarCodeFreq", new Object[]{varCodeFreq},"int");
	    				infactList.add(obj);
					}
	    			hmcreate.put("infacts", infactList);
	    		}
			}
    	}catch(Exception e){
    		logger.error("创建反射实例错误",e);
    		throw new Exception(e.getMessage());
    	}
    	
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public String getPricingObj(StatelessKieSession ksession,String name) throws Exception{
    	String result;
    	try{
    		//KieRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.( ksession, "C://drools.log" );
    	
	    	Object loanAppObj = hmcreate.get("loanApp");
	        if (loanAppObj == null || loanAppObj == null) {
	            throw new NullPointerException("传入对象参数空指针错误！");
	        }
	        
	        if (name == null || "".equals(name)) {
	            throw new IllegalArgumentException("申请对象姓名不能为空，请设置！");
	        }
	        
	       
	        if ((infactList != null) && (infactList.size() > 0)) {
	            RefacetHandler.invokeFieldMethod(loanAppObj,"setTotalScore",new Object[]{0},"int");
	            infactList.add(loanAppObj);
	            ksession.execute(infactList);
	            infactList.clear();
	            infactList.add(loanAppObj);
	            ksession.execute(infactList);
	            String firstProposal = (String) RefacetHandler.invokeFieldMethod(loanAppObj,"getFirstProposal",new Object[]{});
	            if(StringUtils.isEmpty(firstProposal)){
	            	RefacetHandler.invokeFieldMethod(loanAppObj,"setFirstProposal",new Object[]{NULLVALUE});
	            }
	            
	            String secondProposal = (String) RefacetHandler.invokeFieldMethod(loanAppObj,"getSecondProposal",new Object[]{});
	            if(StringUtils.isEmpty(secondProposal)){
	            	RefacetHandler.invokeFieldMethod(loanAppObj,"setSecondProposal",new Object[]{NULLVALUE});
	            }
	            
	            String thirdProposal = (String) RefacetHandler.invokeFieldMethod(loanAppObj,"getThirdProposal",new Object[]{});
	            if(StringUtils.isEmpty(thirdProposal)){
	            	RefacetHandler.invokeFieldMethod(loanAppObj,"setThirdProposal",new Object[]{NULLVALUE});
	            }
	            
	            JSONObject jb = JSONObject.fromObject(loanAppObj);
	            
	            result = jb.toString();
	        } else {
	            result =  null;
	        }
    	}catch(Exception e){
    		logger.error("执行规则错误");
    		throw new Exception("");
    	}
    	return result;
    }
	
}