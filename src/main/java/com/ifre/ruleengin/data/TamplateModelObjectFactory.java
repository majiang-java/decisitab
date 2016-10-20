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
		 if(wy.equals(type)){
			 Map<String,Object> hm1 = new HashMap<String,Object>();
			 hm1.put("name", "LoanApplication");
			 hm1.put("content", genClassResouce("LoanApplication",packHm));
			 hm1.put("packAllName", packHm.get("allName"));
			 hm1.put("objects",  _initLoanAppalication());
			 Map<String,Object> hm2 = new HashMap<String,Object>();
			 hm2.put("name", "ScoreCard");
			 hm2.put("content", genClassResouce("ScoreCard",packHm));
			 hm2.put("packAllName", packHm.get("allName"));
			 hm2.put("objects", _initScoreCard());
			 list.add(hm1);
			 list.add(hm2);
		 }
		 return list;
		
	}
	public  List<Map<String,Object>> produce(String type){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,String> packHm  = getRadormPackage();
		 if(wy.equals(type)){
			 Map<String,Object> hm1 = new HashMap<String,Object>();
			 hm1.put("name", "LoanApplication");
			 hm1.put("content", genClassResouce("LoanApplication",packHm));
			 hm1.put("packAllName", packAllName);
			 Map<String,Object> hm2 = new HashMap<String,Object>();
			 hm2.put("name", "ScoreCard");
			 hm2.put("content", genClassResouce("ScoreCard",packHm));
			 hm2.put("packAllName", packAllName);
			 list.add(hm1);
			 list.add(hm2);
		 }
		 return list;
	}
	
	private String genClassResouce(String className,Map<String,String> packHm){
		String returnc =null;
		packAllName = packHm.get("allName");
		if("LoanApplication".equals(className)){
		
			RulePckgEntity p = new RulePckgEntity();
			p.setName(packHm.get("name"));
			p.setAllName(packHm.get("allName"));
			
			BizObjEntity bizObj = new BizObjEntity();
			bizObj.setName(className);
			bizObj.setDescp(className);
			List<ObjPropEntity> objProperties = _initLoanAppalication();
			returnc = (new GenEntityUtil(bizObj,p, objProperties)).generateToFile();
			
		}else if("ScoreCard".equals(className)){
			RulePckgEntity p = new RulePckgEntity();
			p.setName(packHm.get("name"));
			p.setAllName(packHm.get("allName"));
			BizObjEntity bizObj = new BizObjEntity();
			bizObj.setName(className);
			bizObj.setDescp(className);
			
			List<ObjPropEntity> objProperties = _initScoreCard();
			returnc = (new GenEntityUtil(bizObj,p, objProperties)).generateToFile();
		}
		return returnc;
	}

	private List<ObjPropEntity> _initScoreCard() {
		List<ObjPropEntity> objProperties = new ArrayList<ObjPropEntity>();
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
		obj3.setType("Double");
		obj3.setPropCode("varValue");
		obj3.setName("变量值");
		obj3.setDescp("变量值");
		obj3.setIsList("N");
		
		objProperties.add(obj1);
		objProperties.add(obj2);
		objProperties.add(obj3);
		return objProperties;
	}

	private List<ObjPropEntity> _initLoanAppalication() {
		List<ObjPropEntity> objProperties = new ArrayList<ObjPropEntity>();
		ObjPropEntity obj1 = new ObjPropEntity();
		obj1.setType("int");
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
		obj3.setType("String");
		obj3.setPropCode("loanRatio");
		obj3.setName("获贷概率");
		obj3.setDescp("获贷概率");
		obj3.setIsList("N");
		objProperties.add(obj3);
		ObjPropEntity obj4 = new ObjPropEntity();
		obj4.setType("int");
		obj4.setPropCode("type");
		obj4.setName("类型");
		obj4.setDescp("类型");
		obj4.setIsList("N");
		objProperties.add(obj4);
		
		ObjPropEntity obj5 = new ObjPropEntity();
		obj5.setType("int");
		obj5.setPropCode("trimScore");
		obj5.setName("截断评分");
		obj5.setDescp("截断评分");
		obj5.setIsList("N");
		objProperties.add(obj5);
		ObjPropEntity obj6 = new ObjPropEntity();
		obj6.setType("int");
		obj6.setPropCode("scoreType");
		obj6.setName("分值类型");
		obj6.setDescp("分值类型");
		obj6.setIsList("N");
		objProperties.add(obj6);
		ObjPropEntity obj7 = new ObjPropEntity();
		obj7.setType("int");
		obj7.setPropCode("basicScore");
		obj7.setName("基本分值");
		obj7.setDescp("基本分值");
		obj7.setIsList("N");
		objProperties.add(obj7);
		ObjPropEntity obj8 = new ObjPropEntity();
		obj8.setType("int");
		obj8.setPropCode("workScore");
		obj8.setName("工作分值");
		obj8.setDescp("工作分值");
		obj8.setIsList("N");
		objProperties.add(obj8);
		ObjPropEntity obj9 = new ObjPropEntity();
		obj9.setType("int");
		obj9.setPropCode("creditScore");
		obj9.setName("信用分值");
		obj9.setDescp("信用分值");
		obj9.setIsList("N");
		objProperties.add(obj9);
		ObjPropEntity obj10 = new ObjPropEntity();
		obj10.setType("int");
		obj10.setPropCode("assetScore");
		obj10.setName("资产分值");
		obj10.setDescp("资产分值");
		obj10.setIsList("N");
		objProperties.add(obj10);
		ObjPropEntity obj11 = new ObjPropEntity();
		obj11.setType("int");
		obj11.setPropCode("debtScore");
		obj11.setName("负债分值");
		obj11.setDescp("负债分值");
		obj11.setIsList("N");
		objProperties.add(obj11);
		ObjPropEntity obj12 = new ObjPropEntity();
		obj12.setType("double");
		obj12.setPropCode("defaultRatio");
		obj12.setName("违约概率");
		obj12.setDescp("违约概率");
		obj12.setIsList("N");
		objProperties.add(obj12);
		ObjPropEntity obj13 = new ObjPropEntity();
		obj13.setType("boolean");
		obj13.setPropCode("isTrim");
		obj13.setName("是否截断");
		obj13.setDescp("是否截断");
		obj13.setIsList("N");
		objProperties.add(obj13);
		
		ObjPropEntity obj14 = new ObjPropEntity();
		obj14.setType("String");
		obj14.setPropCode("rejectResponse");
		obj14.setName("拒绝原因列表");
		obj14.setDescp("拒绝原因列表");
		obj14.setIsList("Y");
		objProperties.add(obj14);
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
}
