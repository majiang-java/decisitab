package com.ifre.ruleengin.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.ifre.entity.brms.BizObjEntity;
import com.ifre.entity.brms.ObjPropEntity;
import com.ifre.entity.brms.RulePckgEntity;
import com.ifre.util.GenEntityUtil;

public class TamplateModelObjectFactory {
	
	public static final String wy = "scorecards"; 
	public static final String fqz = "fqz"; 
	public static final String ZR = "access"; 
	private String packAllName;
	private  static TamplateModelObjectFactory instance = new TamplateModelObjectFactory();
	

	public String getPackAllName() {
		return packAllName;
	}

	public static TamplateModelObjectFactory getInstance(){
		return instance;
	}

	public List<Map<String,Object>> getSourceData(String type){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		 Map<String,String> packHm  = getRadormPackage();
		 if(fqz.equals(type)){
			 Map<String,Object> hm1 = new HashMap<String,Object>();
			 hm1.put("name", "LoanApplication");
			 hm1.put("content", genClassResouce("LoanApplication",packHm,type));
			 hm1.put("packAllName", packHm.get("allName"));
			 hm1.put("objects",  _initLoanAppalication(type));
			 Map<String,Object> hm2 = new HashMap<String,Object>();
			 hm2.put("name", "ScoreCard");
			 hm2.put("content", genClassResouce("ScoreCard",packHm,type));
			 hm2.put("packAllName", packHm.get("allName"));
			 hm2.put("objects", _initScoreCard(type));
			 Map<String,Object> hm3 = new HashMap<String,Object>();
			 hm3.put("name", "ScoreModelObj");
			 hm3.put("content", initScoreModelObject(packHm,type));
			 hm3.put("packAllName", packHm.get("allName"));
			 hm3.put("objects", new ArrayList<ObjPropEntity>());
			 list.add(hm1);
			 list.add(hm2);
			 list.add(hm3);
		 } else{
			 Map<String,Object> hm1 = new HashMap<String,Object>();
			 hm1.put("name", "LoanApplication");
			 hm1.put("content", genClassResouce("LoanApplication",packHm,type));
			 hm1.put("packAllName", packHm.get("allName"));
			 hm1.put("objects",  _initLoanAppalication(type));
			 Map<String,Object> hm2 = new HashMap<String,Object>();
			 hm2.put("name", "ScoreCard");
			 hm2.put("content", genClassResouce("ScoreCard",packHm,type));
			 hm2.put("packAllName", packHm.get("allName"));
			 hm2.put("objects", _initScoreCard(type));
			 
			 Map<String,Object> hm3 = new HashMap<String,Object>();
			 hm3.put("name", "ScoreModelObj");
			 hm3.put("content", initScoreModelObject(packHm,type));
			 hm3.put("packAllName", packHm.get("allName"));
			 hm3.put("objects", new ArrayList<ObjPropEntity>());
			 list.add(hm1);
			 list.add(hm2);
			 list.add(hm3);
		 }
		 return list;
		
	}
	public  List<Map<String,Object>> produce(String type){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,String> packHm  = getRadormPackage();
		if(fqz.equals(type)){
			 Map<String,Object> hm1 = new HashMap<String,Object>();
			 hm1.put("name", "LoanApplication");
			 hm1.put("content", genClassResouce("LoanApplication",packHm,type));
			 hm1.put("packAllName", packAllName);
			 Map<String,Object> hm2 = new HashMap<String,Object>();
			 hm2.put("name", "ScoreCard");
			 hm2.put("content", genClassResouce("ScoreCard",packHm,type));
			 hm2.put("packAllName", packAllName);
			 Map<String,Object> hm3 = new HashMap<String,Object>();
			 hm3.put("name", "ScoreModelObj");
			 hm3.put("content", initScoreModelObject(packHm,type));
			 hm3.put("packAllName", packAllName);
			 list.add(hm1);
			 list.add(hm2);
			 list.add(hm3);
		 } else if(ZR.equals(type)){
			 Map<String,Object> hm1 = new HashMap<String,Object>();
			 hm1.put("name", "LoanApplication");
			 hm1.put("content", genClassResouce("LoanApplication",packHm,type));
			 hm1.put("packAllName", packAllName);
			 Map<String,Object> hm2 = new HashMap<String,Object>();
			 hm2.put("name", "ScoreCard");
			 hm2.put("content", genClassResouce("ScoreCard",packHm,type));
			 hm2.put("packAllName", packAllName);
			 Map<String,Object> hm3 = new HashMap<String,Object>();
			 hm3.put("name", "ScoreModelObj");
			 hm3.put("content", initScoreModelObject(packHm,type));
			 hm3.put("packAllName", packAllName);
			 list.add(hm1);
			 list.add(hm2);
			 list.add(hm3);
		 }else{
			 Map<String,Object> hm1 = new HashMap<String,Object>();
			 hm1.put("name", "LoanApplication");
			 hm1.put("content", genClassResouce("LoanApplication",packHm,type));
			 hm1.put("packAllName", packAllName);
			 Map<String,Object> hm2 = new HashMap<String,Object>();
			 hm2.put("name", "ScoreCard");
			 hm2.put("content", genClassResouce("ScoreCard",packHm,type));
			 hm2.put("packAllName", packAllName);
			 Map<String,Object> hm3 = new HashMap<String,Object>();
			 hm3.put("name", "ScoreModelObj");
			 hm3.put("content", initScoreModelObject(packHm,type));
			 hm3.put("packAllName", packAllName);
			 list.add(hm1);
			 list.add(hm2);
			 list.add(hm3);
		 }
		 return list;
	}
	
	private String genClassResouce(String className,Map<String,String> packHm,String type){
		String returnc =null;
		packAllName = packHm.get("allName");
		if("LoanApplication".equals(className)){
		
			RulePckgEntity p = new RulePckgEntity();
			p.setName(packHm.get("name"));
			p.setAllName(packHm.get("allName"));
			
			BizObjEntity bizObj = new BizObjEntity();
			bizObj.setName(className);
			bizObj.setDescp(className);
			List<ObjPropEntity> objProperties = _initLoanAppalication(type);
			returnc = (new GenEntityUtil(bizObj,p, objProperties)).generateToFile();
			
		}else if("ScoreCard".equals(className)){
			RulePckgEntity p = new RulePckgEntity();
			p.setName(packHm.get("name"));
			p.setAllName(packHm.get("allName"));
			BizObjEntity bizObj = new BizObjEntity();
			bizObj.setName(className);
			bizObj.setDescp(className);
			
			List<ObjPropEntity> objProperties = _initScoreCard(type);
			returnc = (new GenEntityUtil(bizObj,p, objProperties)).generateToFile();
		}
		return returnc;
	}

	private List<ObjPropEntity> _initScoreCard(String type) {
		List<ObjPropEntity> objProperties = new ArrayList<ObjPropEntity>();
		if(fqz.equals(type)){
			ObjPropEntity obj1 = new ObjPropEntity();
			obj1.setType("String");
			obj1.setPropCode("varName");
			obj1.setName("变量名称");
			obj1.setDescp("变量名称");
			obj1.setIsList("N");
			ObjPropEntity obj2 = new ObjPropEntity();
			obj2.setType("String");
			obj2.setPropCode("varValueCode");
			obj2.setName("变量值代码");
			obj2.setDescp("变量值代码");
			obj2.setIsList("N");
			ObjPropEntity obj3 = new ObjPropEntity();
			obj3.setType("double");
			obj3.setPropCode("varCodeFreq");
			obj3.setName("变量值");
			obj3.setDescp("变量值");
			obj3.setIsList("N");
			
			objProperties.add(obj1);
			objProperties.add(obj2);
			objProperties.add(obj3);
		}else if(ZR.equals(type)){
			ObjPropEntity obj1 = new ObjPropEntity();
			obj1.setType("String");
			obj1.setPropCode("varName");
			obj1.setName("变量名称");
			obj1.setDescp("变量名称");
			obj1.setIsList("N");
			ObjPropEntity obj2 = new ObjPropEntity();
			obj2.setType("String");
			obj2.setPropCode("varValueCode");
			obj2.setName("变量值代码");
			obj2.setDescp("变量值代码");
			obj2.setIsList("N");
			ObjPropEntity obj3 = new ObjPropEntity();
			obj3.setType("double");
			obj3.setPropCode("varValue");
			obj3.setName("变量值");
			obj3.setDescp("变量值");
			obj3.setIsList("N");
			
			objProperties.add(obj1);
			objProperties.add(obj2);
			objProperties.add(obj3);
		}else{
			ObjPropEntity obj1 = new ObjPropEntity();
			obj1.setType("String");
			obj1.setPropCode("varName");
			obj1.setName("变量名称");
			obj1.setDescp("变量名称");
			obj1.setIsList("N");
			ObjPropEntity obj2 = new ObjPropEntity();
			obj2.setType("String");
			obj2.setPropCode("varValueCode");
			obj2.setName("变量值代码");
			obj2.setDescp("变量值代码");
			obj2.setIsList("N");
			ObjPropEntity obj3 = new ObjPropEntity();
			obj3.setType("double");
			obj3.setPropCode("varValue");
			obj3.setName("变量值");
			obj3.setDescp("变量值");
			obj3.setIsList("N");
			
			objProperties.add(obj1);
			objProperties.add(obj2);
			objProperties.add(obj3);
		}
		return objProperties;
	}

	private List<ObjPropEntity> _initLoanAppalication(String type) {
		List<ObjPropEntity> objProperties = new ArrayList<ObjPropEntity>();
	    if(fqz.equals(type)){
			ObjPropEntity obj1 = new ObjPropEntity();
			obj1.setType("double");
			obj1.setPropCode("totalScore");
			obj1.setName("总分值");
			obj1.setDescp("总分值");
			obj1.setIsList("N");
			objProperties.add(obj1);
			ObjPropEntity obj2 = new ObjPropEntity();
			obj2.setType("int");
			obj2.setPropCode("type");
			obj2.setName("类型");
			obj2.setDescp("类型");
			obj2.setIsList("N");
			objProperties.add(obj2);
			ObjPropEntity obj3 = new ObjPropEntity();
			obj3.setType("double");
			obj3.setPropCode("trimScore");
			obj3.setName("截断评分");
			obj3.setDescp("截断评分");
			obj3.setIsList("N");
			objProperties.add(obj3);
			ObjPropEntity obj4 = new ObjPropEntity();
			obj4.setType("double");
			obj4.setPropCode("firstValue");
			obj4.setName("第一分值");
			obj4.setDescp("第一分值");
			obj4.setIsList("N");
			objProperties.add(obj4);
			
			ObjPropEntity obj5 = new ObjPropEntity();
			obj5.setType("String");
			obj5.setPropCode("firstProposal");
			obj5.setName("第一建议");
			obj5.setDescp("第一建议");
			obj5.setIsList("N");
			objProperties.add(obj5);
			ObjPropEntity obj6 = new ObjPropEntity();
			obj6.setType("double");
			obj6.setPropCode("secondValue");
			obj6.setName("第二分值");
			obj6.setDescp("第二分值");
			obj6.setIsList("N");
			objProperties.add(obj6);
			ObjPropEntity obj7 = new ObjPropEntity();
			obj7.setType("String");
			obj7.setPropCode("secondProposal");
			obj7.setName("第二建议");
			obj7.setDescp("第二建议");
			obj7.setIsList("N");
			objProperties.add(obj7);
			ObjPropEntity obj8 = new ObjPropEntity();
			obj8.setType("double");
			obj8.setPropCode("thirdValue");
			obj8.setName("第三分值");
			obj8.setDescp("第三分值");
			obj8.setIsList("N");
			objProperties.add(obj8);
			ObjPropEntity obj9 = new ObjPropEntity();
			obj9.setType("String");
			obj9.setPropCode("thirdProposal");
			obj9.setName("第三建议");
			obj9.setDescp("第三建议");
			obj9.setIsList("N");
			objProperties.add(obj9);
			ObjPropEntity obj10 = new ObjPropEntity();
			obj10.setType("String");
			obj10.setPropCode("riskRanking");
			obj10.setName("风险等级");
			obj10.setDescp("风险等级");
			obj10.setIsList("N");
			objProperties.add(obj10);
			ObjPropEntity obj11 = new ObjPropEntity();
			obj11.setType("String");
			obj11.setPropCode("finalProposal");
			obj11.setName("审核建议");
			obj11.setDescp("审核建议");
			obj11.setIsList("N");
			objProperties.add(obj11);
			ObjPropEntity obj12 = new ObjPropEntity();
			obj12.setType("String");
			obj12.setPropCode("name");
			obj12.setName("姓名");
			obj12.setDescp("姓名");
			obj12.setIsList("N");
			objProperties.add(obj12);
			ObjPropEntity obj13 = new ObjPropEntity();
			obj13.setType("String");
			obj13.setPropCode("firstCode");
			obj13.setName("第一代码");
			obj13.setDescp("第一代码");
			obj13.setIsList("N");
			objProperties.add(obj13);
			ObjPropEntity obj14 = new ObjPropEntity();
			obj14.setType("String");
			obj14.setPropCode("secondCode");
			obj14.setName("第二代码");
			obj14.setDescp("第二代码");
			obj14.setIsList("N");
			objProperties.add(obj14);
			ObjPropEntity obj15 = new ObjPropEntity();
			obj15.setType("String");
			obj15.setPropCode("thirdCode");
			obj15.setName("第三代码");
			obj15.setDescp("第三代码");
			obj15.setIsList("N");
			objProperties.add(obj15);
			
			ObjPropEntity obj16 = new ObjPropEntity();
			obj16.setType("double");
			obj16.setPropCode("firstSingleValue");
			obj16.setName("第一单个分值");
			obj16.setDescp("第一单个分值");
			obj16.setIsList("N");
			objProperties.add(obj16);
			ObjPropEntity obj17 = new ObjPropEntity();
			obj17.setType("double");
			obj17.setPropCode("secondSingleValue");
			obj17.setName("第二单个分值");
			obj17.setDescp("第二单个分值");
			obj17.setIsList("N");
			objProperties.add(obj17);
			ObjPropEntity obj18 = new ObjPropEntity();
			obj18.setType("double");
			obj18.setPropCode("thirdSingleValue");
			obj18.setName("第三单个分值");
			obj18.setDescp("第三单个分值");
			obj18.setIsList("N");
			objProperties.add(obj18);
		}else if(ZR.equals(type)){
			ObjPropEntity obj1 = new ObjPropEntity();
			obj1.setType("double");
			obj1.setPropCode("totalScore");
			obj1.setName("总分值");
			obj1.setDescp("总分值");
			obj1.setIsList("N");
			objProperties.add(obj1);
			
			ObjPropEntity obj2 = new ObjPropEntity();
			obj2.setType("int");
			obj2.setPropCode("type");
			obj2.setName("类型");
			obj2.setDescp("类型");
			obj2.setIsList("N");
			objProperties.add(obj2);
			
			ObjPropEntity obj3 = new ObjPropEntity();
			obj3.setType("int");
			obj3.setPropCode("scoreType");
			obj3.setName("分值类型");
			obj3.setDescp("分值类型");
			obj3.setIsList("N");
			objProperties.add(obj3);
			
			ObjPropEntity obj4 = new ObjPropEntity();
			obj4.setType("String");
			obj4.setPropCode("finalProposal");
			obj4.setName("审批建议");
			obj4.setDescp("审批建议");
			obj4.setIsList("N");
			objProperties.add(obj4);
			
			ObjPropEntity obj5 = new ObjPropEntity();
			obj5.setType("String");
			obj5.setPropCode("rejectReason");
			obj5.setName("拒绝原因");
			obj5.setDescp("拒绝原因");
			obj5.setIsList("Y");
			objProperties.add(obj5);
			
		}else{
			
			ObjPropEntity obj1 = new ObjPropEntity();
			obj1.setType("double");
			obj1.setPropCode("totalScore");
			obj1.setName("总分值");
			obj1.setDescp("总分值");
			obj1.setIsList("N");
			objProperties.add(obj1);
			ObjPropEntity obj2 = new ObjPropEntity();
			obj2.setType("String");
			obj2.setPropCode("ranking");
			obj2.setName("评级");
			obj2.setDescp("评级");
			obj2.setIsList("N");
			objProperties.add(obj2);
			ObjPropEntity obj3 = new ObjPropEntity();
			obj3.setType("int");
			obj3.setPropCode("type");
			obj3.setName("类型");
			obj3.setDescp("类型");
			obj3.setIsList("N");
			objProperties.add(obj3);
			
			ObjPropEntity obj4 = new ObjPropEntity();
			obj4.setType("double");
			obj4.setPropCode("trimScore");
			obj4.setName("截断评分");
			obj4.setDescp("截断评分");
			obj4.setIsList("N");
			objProperties.add(obj4);
			ObjPropEntity obj5 = new ObjPropEntity();
			obj5.setType("double");
			obj5.setPropCode("defaultRatio");
			obj5.setName("违约概率");
			obj5.setDescp("违约概率");
			obj5.setIsList("N");
			objProperties.add(obj5);
			
			ObjPropEntity obj6 = new ObjPropEntity();
			obj6.setType("String");
			obj6.setPropCode("finalProposal");
			obj6.setName("审批建议");
			obj6.setDescp("审批建议");
			obj6.setIsList("N");
			objProperties.add(obj6);
			
			ObjPropEntity obj7 = new ObjPropEntity();
			obj7.setType("String");
			obj7.setPropCode("rejectReason");
			obj7.setName("拒绝原因");
			obj7.setDescp("拒绝原因");
			obj7.setIsList("Y");
			objProperties.add(obj7);
		}
		return objProperties;
	}

	//生成随机package
	public Map<String,String> getRadormPackage(){
		Map<String,String> hm = new HashMap<String,String>();
		String name = getRandomString(7);
		StringBuffer sb = new StringBuffer();
		sb.append("com.ifre.decisiton");
		sb.append(".").append(name);
		hm.put("name",name);
		hm.put("allName", sb.toString());
		return hm;
	}
	//生成随机字符串
	private String getRandomString(int length) { //length表示生成字符串的长度  
	    String base = "abcdefghijklmnopqrstuvwxyz";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer(); 
	    sb.append(base.charAt( random.nextInt(base.length())));
	    for (int i = 0; i < length-1; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 }  
	

	private String initScoreModelObject(Map<String,String> packHm,String type){
		String returnc = null;
		RulePckgEntity p = new RulePckgEntity();
		p.setName(packHm.get("name"));
		p.setAllName(packHm.get("allName"));
		BizObjEntity bizObj = new BizObjEntity();
		bizObj.setName("ScoreModelObj");
		bizObj.setDescp("ScoreModelObj");
		if(type.equals(fqz)){
			returnc = (new GenEntityUtil(bizObj,p, new ArrayList<ObjPropEntity>())).generateToFile("scoreCordObjectTemplateForFQZ");
		}else if(type.equals(ZR)){
			returnc = (new GenEntityUtil(bizObj,p, new ArrayList<ObjPropEntity>())).generateToFile("scoreCordObjectTemplateForZR");
		}else{
			returnc = (new GenEntityUtil(bizObj,p, new ArrayList<ObjPropEntity>())).generateToFile("scoreCordObjectTemplateForWY");
		}
		return returnc;
	}
}
