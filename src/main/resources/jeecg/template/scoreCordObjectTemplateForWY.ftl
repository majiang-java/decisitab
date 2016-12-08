package ${bussiPackage}.${entityPackage};

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.kie.api.KieServices;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

import com.ifre.util.StringUtils;


/**
 * 违约模型通用类.
 */
public class ScoreModelObj {
    
    /** The logger. */
    private static Logger logger = Logger.getLogger(ScoreModelObj.class);
    

    /** The kc. */
    private KieContainer kc = getKieClasspathContainer();
    
    /**
     * Gets the kie classpath container.
     * 
     * @return the kie classpath container
     */
    private KieContainer getKieClasspathContainer() {
        if (kc == null) {
            kc = KieServices.Factory.get().getKieClasspathContainer();
        }
        return kc;
    }

    /**
     * 校验传入的申请人对象，转化为决策表需要的对象.
     * 
     * @param object the object
     * @param objs the objs
     * @return the score objs
     * @throws Exception the exception
     */
    public List<Serializable> getScoreObjs(Object object, List<Serializable> objs) throws Exception {
        for (Class<?> classType = object.getClass(); classType != Object.class; classType = classType.getSuperclass()) {
            Field[] fdAttr = classType.getDeclaredFields();
            for (Field fd : fdAttr) {
                String fieldName = fd.getName();
                if (!((fieldName.equals("serialVersionUID") || (fieldName.equals("name"))))) {
                    String firstLetter = fieldName.substring(0, 1).toUpperCase();
                    String getMethodName = "get" + firstLetter + fieldName.substring(1);
                    Method getMethod = classType.getMethod(getMethodName, new Class[0]);
                    Object value = getMethod.invoke(object, new Object[0]);
                    setObjList(objs, fieldName, value);
                }
            }

        }

        return objs;
    }

    /**
     * Gets the score map objs.
     * 
     * @param object the object
     * @param objs the objs
     * @return the score map objs
     */
    private List<Serializable> getScoreMapObjs(Object object, List<Serializable> objs) {
        Map<?, ?> objMap = (Map<?, ?>) object;
        if (objMap.isEmpty()) {
            throw new IllegalArgumentException("请至少设置一条规则！");
        }
        for (Object key : objMap.keySet()) {
            String fieldName = (String) key;
            Object value = objMap.get(key);
            if (!fieldName.equals("serialVersionUID")) {
                setObjList(objs, fieldName, value);
            }
        }
        return objs;
    }

    /**
     * Sets the obj list.
     * 
     * @param objs the objs
     * @param fieldName the field name
     * @param value the value
     */
    private void setObjList(List<Serializable> objs, String fieldName, Object value) {
    	String valueCode = null;
    	double varValue = -99;
        try {
        	if(value == null) {
        		valueCode = "-99";
        	}else{
        		valueCode = (String)value;
        		if (valueCode.equals("")){
        			valueCode = "-99";
        		}
        	}
        	if(isRealNumber(valueCode)){
        		varValue = Double.parseDouble(valueCode);
        	}
        	
            ScoreCard scoreCard = new ScoreCard();
            scoreCard.setVarName(fieldName);
            scoreCard.setVarValueCode(valueCode);
            scoreCard.setVarValue(varValue);
            objs.add(scoreCard);
        }catch (NumberFormatException e) {
        	throw new NumberFormatException(valueCode + "变量输入格式错误！");
        }
    }
    
	public StatelessKieSession createKsession(String path) throws IOException {
		DecisionTableConfiguration dtableconfiguration = null;
		if(dtableconfiguration == null){
			dtableconfiguration = KnowledgeBuilderFactory.newDecisionTableConfiguration();
			dtableconfiguration.setInputType(DecisionTableInputType.XLS);
		}
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		//File pathf= new File(path);
         InputStream is =this.getClass().getResourceAsStream("config.properties");
         Properties prop=new Properties(); 
         prop.load(is);  
         is.close();  
         String names = prop.getProperty("names");
         String[] namef = names.split("#");
         
		for (String name : namef) {
			logger.info("logger:file" + name);
			InputStream xis=this.getClass().getResourceAsStream(name+".xls");
			Resource xlsRes = ResourceFactory.newInputStreamResource(xis);
			kbuilder.add(xlsRes, ResourceType.DTABLE, dtableconfiguration);
		}
	   if ( kbuilder.hasErrors() ) {
		   logger.info( kbuilder.getErrors().toString() );
	            throw new RuntimeException( "Unable to compile \"."+kbuilder.getErrors().toString() ); 
        }
	   
	   KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();;
       kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
       return kbase.newStatelessKieSession();
       
	}

    /**
     * Gets the pricing obj.
     *
     * @param object the object
     * @param loanApp the loan app
     * @return the pricing obj
     * @throws IOException 
     */
    public LoanApplication getPricingObj(Object object, LoanApplication loanApp) throws IOException {
    	loanApp.setRejectReason(new ArrayList<String>());
    	loanApp.setType(25);
    	StatelessKieSession ksession = createKsession(null);
        List<Serializable> objs = new ArrayList<Serializable>();
        if (object == null || loanApp == null) {
            throw new NullPointerException("传入对象参数空指针错误！");
        }
        
        try {
            if (object != null && object instanceof Map) {
                objs = getScoreMapObjs(object, objs);
            } else {
                objs = getScoreObjs(object, objs);
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return null;
        }
        if ((objs != null) && (objs.size() > 0)) {
            loanApp.setTotalScore(0);
            ksession.execute(objs);
            objs.add(loanApp);
            ksession.execute(objs);
            loanApp.setTrimScore(loanApp.getTotalScore());
            objs.clear();
            objs.add(loanApp);
            ksession.execute(objs);
            return loanApp;
        } else {
            return null;
        }
        
    }
    

	public boolean isRealNumber(String orginal) {
		return isWholeNumber(orginal) || isDecimal(orginal);
	}
	
	public boolean isWholeNumber(String orginal) {
		return isMatch("[+-]{0,1}0", orginal) || isMatch("^\\+{0,1}[1-9]\\d*", orginal) || isMatch("^-[1-9]\\d*", orginal);
	}	
	
	public boolean isDecimal(String orginal) {
		return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
	}	
    
	private boolean isMatch(String regex, String orginal) {
		if (orginal == null || orginal.trim().equals("")) {
			return false;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher isNum = pattern.matcher(orginal);
		return isNum.matches();
	}    
}
