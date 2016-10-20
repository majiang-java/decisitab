package com.ifre.ruleengin.excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;

import com.ifre.entity.brms.RulePckgEntity;
import com.ifre.entity.ruleengin.BrmsActionDetailEntity;
import com.ifre.entity.ruleengin.BrmsConditionDetailEntity;
import com.ifre.entity.ruleengin.BrmsRuleActionEntity;
import com.ifre.entity.ruleengin.BrmsRuleConditionEntity;
import com.ifre.entity.ruleengin.BrmsRuleTableEntity;
import com.ifre.service.brms.RulePckgServiceI;
import com.ifre.service.ruleengin.BrmsActionDetailServiceI;
import com.ifre.service.ruleengin.BrmsConditionDetailServiceI;
import com.ifre.service.ruleengin.BrmsRuleActionServiceI;
import com.ifre.service.ruleengin.BrmsRuleConditionServiceI;
import com.ifre.service.ruleengin.BrmsRuleTableServiceI;

import jodd.util.StringUtil;

public class ProcessExcel {
	Logger log = Logger.getLogger(ProcessExcel.class);
	@Autowired
	private BrmsRuleTableServiceI brmsRuleTableService;
	@Autowired
	private BrmsConditionDetailServiceI brmsConditionDetailService;
	@Autowired
	private BrmsActionDetailServiceI brmsActionDetailService;
	@Autowired
	private BrmsRuleActionServiceI brmsRuleActionService;
	@Autowired
	private BrmsRuleConditionServiceI brmsRuleConditionService;
	@Autowired
	private RulePckgServiceI rulePckgServiceI; 
	
	
	//将excel装入数据库
	public void process(String orgId,String prodId) {
		TSUser user = ResourceUtil.getSessionUserName();
		try{
			File f = new File("E://access_rule.xls");
			ExcelReaderForNormalImpl reader = new ExcelReaderForNormalImpl();
		    List<Map<Integer,String>> list = reader.readExcelContent(new FileInputStream(f));
		    Map<Integer,String> hm = list.get(0);
		    //插入table
		    String ruleSet = hm.get(0);
		    String notes = hm.get(1);
		    String name = hm.get(3);
		    BrmsRuleTableEntity table = new BrmsRuleTableEntity();
		    if(StringUtil.isNotEmpty(name)){
		    	name = name.split(" ")[1];
		    }
		    table.setName(name);
		    String[] rules =  ruleSet.split("#");
		    String pack = rules[rules.length-1];
		    table.setPkgAllName(pack);
		    RulePckgEntity rulePckg = new RulePckgEntity();
		    rulePckg.setName(pack);
		    List<RulePckgEntity> packageList =  rulePckgServiceI.findByExample("RulePckgEntity", rulePckg);
		    String[] notestr = notes.split("#");
		    String note = notestr[notestr.length - 1];
		    table.setNote(note);
		    table.setPkgNo(packageList.get(0).getId());
		    table.setOrgId(orgId);
		    table.setProdId(prodId);
		    
		    table.setCreateDate(new Date());
		    table.setUpdateBy(user.getUserName());
		    brmsRuleTableService.save(table);
		    String tableId = table.getId();
		    String row = hm.get(3);
		    String[] rows = row.split("#");
		    int size = hm.size();
		    size = size - 3;
		    String[][] arrgs = new String[size][rows.length];
		    
		
		    for (int i = 3; i < hm.size(); i++) {
				String rowline = hm.get(i);
				String[] rowlines = rowline.split("#");
				for (int j = 0; j < rowlines.length; j++) {
					arrgs[i][j] = rowlines[j];
				}
			}
		    
		     //截取一部分数组
		    String[][] newData = Arrays.copyOfRange(arrgs, 3, 7);
		    int totelCondition = 0;
		    int totelAction = 0;
		    //行列转换
		    String[][] virticalNewData = rowTocol(newData);
		    Map<Integer,String> templeteIdMap = new HashMap<Integer,String>();
		    for(int i = 0 ; i < virticalNewData.length; i++ ){
		    	//for (int j = 0; j < virticalNewData[i].length; j++) {
				if(StringUtils.isEmpty(virticalNewData[i][0])||
						"CONDITION".equalsIgnoreCase(virticalNewData[i][0]) ){
		    		BrmsRuleConditionEntity condition = new BrmsRuleConditionEntity(); 
		    		condition.setRuleTableId(tableId);
		    		condition.setCondId(virticalNewData[i][0]);
		    		condition.setBizObjName(virticalNewData[i][1]);
		    		condition.setSeq(i+1);
		    	//	condition.setBizObjProp(bizObjProp);
		    		condition.setPropCondScript(virticalNewData[i][2]);
		    		condition.setPropCondDes(virticalNewData[i][3]);
		    		condition.setCreateName(user.getUserName());
		    		condition.setCreateDate(new Date());
		    		brmsRuleConditionService.save(condition);
		    		templeteIdMap.put(i, condition.getId());
		    		totelCondition++;
		    	 }else{
		    		BrmsRuleActionEntity action = new BrmsRuleActionEntity();
		    		action.setRuleTableId(tableId);
		    		action.setActionId(virticalNewData[i][0]);
		    		action.setSeq(i+1);
		    		action.setBizObjName(virticalNewData[i][1]);
		    	//	condition.setBizObjProp(bizObjProp);
		    		action.setPropActionScript(virticalNewData[i][2]);
		    		action.setPropActionDes(virticalNewData[i][3]);
		    		action.setCreateName(user.getUserName());
		    		action.setCreateDate(new Date());
		    		
		    		brmsRuleActionService.save(action);
		    		templeteIdMap.put(i, action.getId());
		    		totelAction++;
		    	 }
		    		
			//	}
		    }
		    List<BrmsConditionDetailEntity> conditionDetalList = new ArrayList<BrmsConditionDetailEntity>();
		    
		    List<BrmsActionDetailEntity> actionDetalList = new ArrayList<BrmsActionDetailEntity>();
		    String[][] virticalNewDetailData = Arrays.copyOfRange(arrgs, 7, arrgs.length);
		    for (int i = 0; i < virticalNewDetailData.length; i++) {
		    	if( i  <= totelCondition){
		        	for (int j = 0; j < virticalNewDetailData[i].length; j++) {
			    		BrmsConditionDetailEntity conditionDetail = new BrmsConditionDetailEntity();
			    		conditionDetail.setCondId(templeteIdMap.get(i));
			    		conditionDetail.setSeq(j+1);
			    		conditionDetail.setCondValue(virticalNewDetailData[i][j]);
				    	conditionDetail.setCreateName(user.getUserName());
				    	conditionDetail.setCreateDate(new Date());
				    	conditionDetalList.add(conditionDetail);
					}
		    		
		    	}else{
		    		for (int j = 0; j < virticalNewDetailData[i].length; j++) {
			    		BrmsActionDetailEntity actionDetail = new BrmsActionDetailEntity();
				    	actionDetail.setActionId(templeteIdMap.get(i));
				    	actionDetail.setSeq(j+1);
				    	actionDetail.setActionValue(virticalNewDetailData[i][j]);
				    	actionDetail.setCreateName(user.getUserName());
				    	actionDetail.setCreateDate(new Date());
				    	actionDetalList.add(actionDetail);
					}
		    	}
		
		    
			} 
		    brmsConditionDetailService.batchSave(conditionDetalList);
		    brmsActionDetailService.batchSave(actionDetalList);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	public static void main(String[] args) {
		String[] a = {"1","2","3","4","5"};
		String[] a1 = Arrays.copyOfRange(a,0,2);
		System.out.println("a1:");
		print(a1);
		String[] a2 = Arrays.copyOfRange(a,2,a.length);
		System.out.println("a2:");
		print(a2);
	}
	public static void print(String[] args){
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}
	}
	   public static String[][] rowTocol ( String[][] array )
	    {
		   	System.out.println("0:"+array[0].length);
		   	System.out.println("1:"+array.length);
	        String[][] result = new String[array[0].length][array.length];
	        for (int i = 0; i < array.length; i++) {// 调整数组行列数据  
	            for (int j = 0; j < array[i].length; j++) {  
	                result[j][i] = array[i][j];
	            }  
	        }  
	        return result;
	    }


}
