package ${bussiPackage}.${entityPackage};

import java.io.FileInputStream;
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
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;


/**
 * 反欺诈模型通用类.
 */
public class ScoreModelObj {

    /** The logger. */
    private static Logger logger = Logger.getLogger(ScoreModelObj.class);
    
    /** The Constant NULLVALUE. */
    private static final String NULLVALUE = "无";


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
            if (!((fieldName.equals("serialVersionUID") || (fieldName.equals("name"))))) {
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
        if ((value != null) && (!(value.equals("")))) {
            String tempValue = (String) value;
            String tempVarCode = tempValue.substring(0, 5);
            String tempVarCodeFreq = tempValue.substring(5);
            if (!(isNumbersOrLetters(tempVarCode) && (isNumbers(tempVarCodeFreq) || tempVarCodeFreq
                    .equals("")))) {
                throw new IllegalArgumentException(fieldName + "变量输入值非法！");
            }
            int varCodeFreq = tempVarCodeFreq.equals("") ? 1 : Integer.valueOf(tempVarCodeFreq);

            ScoreCard scoreCard = new ScoreCard();
            scoreCard.setVarName(fieldName);
            scoreCard.setVarValueCode(tempVarCode);
            scoreCard.setVarCodeFreq(varCodeFreq);
            objs.add(scoreCard);
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
     //   StatelessKieSession ksession = kc.newStatelessKieSession("defaultStatelessKieSession");
    	//String path = this.getClass().getResource("").getPath();
    	loanApp.setType(71);
    	StatelessKieSession ksession = createKsession(null);
        List<Serializable> objs = new ArrayList<Serializable>();
        if (object == null || loanApp == null) {
            throw new NullPointerException("传入对象参数空指针错误！");
        }
        
        if (loanApp.getName() == null || loanApp.getName().equals("")) {
            throw new IllegalArgumentException("申请对象姓名不能为空，请设置！");
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
            objs.add(loanApp);
            ksession.execute(objs);
            objs.clear();
            objs.add(loanApp);
            ksession.execute(objs);
            if(loanApp.getFirstProposal() == null) loanApp.setFirstProposal(NULLVALUE);
            if(loanApp.getSecondProposal() == null) loanApp.setSecondProposal(NULLVALUE);
            if(loanApp.getThirdProposal() == null) loanApp.setThirdProposal(NULLVALUE);
            return loanApp;
        } else {
            return null;
        }
        
    }
    
    /**
     * Checks if is numbers or letters.
     *
     * @param charSequence the char sequence
     * @return true, if is numbers or letters
     */
    private static boolean isNumbersOrLetters(String charSequence)
    {
      if (charSequence == null) {
        return false;
      }
      Matcher matcher = Pattern.compile("^[0-9a-zA-Z]+$").matcher(charSequence);
      return matcher.find();
    }

    /**
     * Checks if is numbers.
     *
     * @param charSequence the char sequence
     * @return true, if is numbers
     */
    private static boolean isNumbers(String charSequence)
    {
      if (charSequence == null) {
        return false;
      }
      Matcher matcher = Pattern.compile("^[0-9]+$").matcher(charSequence);
      return matcher.find();
    }
}
