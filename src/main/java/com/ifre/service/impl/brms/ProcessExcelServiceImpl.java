package com.ifre.service.impl.brms;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifre.common.Constants;
import com.ifre.entity.brms.RulePckgEntity;
import com.ifre.entity.ruleengin.BrmsActionDetailEntity;
import com.ifre.entity.ruleengin.BrmsConditionDetailEntity;
import com.ifre.entity.ruleengin.BrmsDeciFileEntity;
import com.ifre.entity.ruleengin.BrmsRuleActionEntity;
import com.ifre.entity.ruleengin.BrmsRuleConditionEntity;
import com.ifre.entity.ruleengin.BrmsRuleTableEntity;
import com.ifre.exception.IfreException;
import com.ifre.ruleengin.RuleFactory;
import com.ifre.ruleengin.excel.ExcelReaderForNormalImpl;
import com.ifre.ruleengin.excel.ExcelWriter;
import com.ifre.ruleengin.excel.ProcessExcel;
import com.ifre.ruleengin.hotcompiler.DynamicEngine;
import com.ifre.service.brms.HotComplierServiceI;
import com.ifre.service.brms.ProcessExcelServicel;
import com.ifre.service.brms.RulePckgServiceI;
import com.ifre.service.ruleengin.BrmsActionDetailServiceI;
import com.ifre.service.ruleengin.BrmsConditionDetailServiceI;
import com.ifre.service.ruleengin.BrmsDeciFileServiceI;
import com.ifre.service.ruleengin.BrmsRuleActionServiceI;
import com.ifre.service.ruleengin.BrmsRuleConditionServiceI;
import com.ifre.service.ruleengin.BrmsRuleTableServiceI;
import com.ifre.util.PathUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service("processExcelService")
@Transactional
public class ProcessExcelServiceImpl extends CommonServiceImpl  implements ProcessExcelServicel {

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
	@Autowired
	private BrmsDeciFileServiceI brmsDeciFileService;
	@Autowired
	private HotComplierServiceI hotComilerService;
	
	public static final int templeteHeight = 4;
	
	//将excel装入数据库
	public void process(File f,String orgId,String knowId,String prodId) {
		TSUser user = ResourceUtil.getSessionUserName();
		String pack = null;
		List<String> filenames = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		try{
		//	File f = new File("E://access_rule.xls");
			ExcelReaderForNormalImpl reader = new ExcelReaderForNormalImpl();
		    List<Map<Integer,String>> list = reader.readExcelContent(new FileInputStream(f));
		    List<Map<String,Object>> tempList= hotComilerService.hotcompiler(prodId);
			DynamicEngine de = DynamicEngine.getInstance(); 
			de.javaCodeCompile(tempList,PathUtils.getClassesPath()); 
			TSDepart depart = hotComilerService.getEntity(TSDepart.class, orgId);
		    for (int counter = 0; counter < list.size(); counter++) {
			    Map<Integer,String> hm = list.get(counter);
			 
			    //插入table
			    String ruleSet = hm.get(0);
			    String prioritystr  = hm.get(1);
			    String notes = hm.get(2);
			    String name = hm.get(3);
			   
			    if(!checkDecitable(hm,prodId)){
			    	throw new Exception(name +"决策表错误");
			    }
			    BrmsRuleTableEntity table = new BrmsRuleTableEntity();
			    if(StringUtil.isNotEmpty(name)){
			    	String[] names = name.split("#");
			    	table.setName(names[names.length-1]);
			    }
			   
			    String[] rules =  ruleSet.split("#");
			    pack = rules[rules.length-1];
			    table.setPkgAllName(pack);
			    //解析优先级
			    String[] ps =  prioritystr.split("#");
			    String priority = ps[ps.length-1];
			    table.setSalience(Integer.valueOf(priority));
			    RulePckgEntity rulePckg = new RulePckgEntity();
			    rulePckg.setName(pack);
			    String findBypackNameSQL = "from RulePckgEntity where 1 = 1 AND allName = ?";
			    List<RulePckgEntity> packageList =  rulePckgServiceI.findHql(findBypackNameSQL, new Object[]{pack});
			    if(packageList == null  || packageList.isEmpty()){
			    	throw new Exception("决策表中的包名称在系统中无法找到");
			    }
			    String[] notestr = notes.split("#");
			    String note = notestr[notestr.length - 1];
			    table.setNote(note);
			    table.setPkgNo(packageList.get(0).getId());
			    table.setOrgId(orgId);
			    table.setProdId(prodId);
			    table.setKnowId(knowId);
			    table.setOrgCode(depart.getOrgCode());
			    table.setMergedRegion(hm.get(Constants.mergerData));
			    table.setCreateDate(new Date());
			    table.setUpdateBy(user.getUserName());
				if (counter == 0) {
					List<BrmsDeciFileEntity> logList = brmsDeciFileService.findList(f.getName(), pack);

					if (!logList.isEmpty()) {
						cleanTable(logList);
					}
				}
			    
			    brmsRuleTableService.save(table);
			    
			    sb.append(table.getId()).append(",");
			    String tableId = table.getId();
			    String row = hm.get(templeteHeight);
			    String[] rows = row.split("#");
			    int size = hm.size();
			    size = size - templeteHeight;
			    String[][] arrgs = new String[size][rows.length];
			    
			
			    for (int i = templeteHeight; i < hm.size(); i++) {
					String rowline = hm.get(i);
					if(StringUtil.isNotEmpty(rowline)){
						String[] rowlines = rowline.split("#");
						for (int j = 0; j < rowlines.length; j++) {
							arrgs[i-templeteHeight][j] = rowlines[j];
						}
					}
				}
			    
			     //截取一部分数组
			    String[][] newData = Arrays.copyOfRange(arrgs, 0, templeteHeight);
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
			    String[][] newDetailData = Arrays.copyOfRange(arrgs,templeteHeight, arrgs.length);
			    String[][] virticalNewDetailData = rowTocol(newDetailData);
			    for (int i = 0; i < virticalNewDetailData.length; i++) {
			    	if( i  < totelCondition){
			        	for (int j = 0; j < virticalNewDetailData[i].length; j++) {
				    		BrmsConditionDetailEntity conditionDetail = new BrmsConditionDetailEntity();
				    		conditionDetail.setCondId(templeteIdMap.get(i));
				    		conditionDetail.setSeq(j+1);
				    		conditionDetail.setRuleTableId(tableId);
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
					    	actionDetail.setRuleTableId(tableId);
					    	actionDetail.setActionValue(virticalNewDetailData[i][j]);
					    	actionDetail.setCreateName(user.getUserName());
					    	actionDetail.setCreateDate(new Date());
					    	actionDetalList.add(actionDetail);
						}
			    	}
				} 
			    brmsConditionDetailService.batchSave(conditionDetalList);
			    brmsActionDetailService.batchSave(actionDetalList);
			}
		    //插入记录日志
			BrmsDeciFileEntity log = new BrmsDeciFileEntity();
			log.setFileName(f.getName());
			log.setContentType(".xls");
			log.setPkgName(pack);
			log.setContainTable(sb.toString());
			log.setCreateTime(new Date());
			log.setUpdateUser(user.getUserName());
			brmsDeciFileService.save(log);
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("解析决策表错误:"+ e.getMessage());
		}
	}
	/**
	 * 
	 * @param logList
	 */
	private void cleanTable(List<BrmsDeciFileEntity> logList) {
		for (BrmsDeciFileEntity entry : logList) {
			String[] tables = entry.getContainTable().split(",");
			for (String tableid : tables) {
				String delTable = "delete from  brms_rule_table where id = ?";
				String delCondition = "delete from brms_rule_condition where rule_table_id = ?";
				String delAction = "delete from brms_rule_action where rule_table_id = ?";
				String delCondtionDetail = "delete from brms_condition_detail where rule_table_id = ?";
				String delActionDetail = "delete from brms_action_detail where rule_table_id = ?";
				brmsRuleTableService.executeSql(delTable, new Object[]{tableid});
				brmsRuleActionService.executeSql(delCondition, new Object[]{tableid});
				brmsRuleConditionService.executeSql(delAction, new Object[]{tableid});
				brmsConditionDetailService.executeSql(delCondtionDetail, new Object[]{tableid});
			    brmsActionDetailService.executeSql(delActionDetail, new Object[]{tableid});
			}
		}
	}
	/**
	 * 清除文件
	 * @param path
	 */
	private void cleanFiles(String path){
		FileFilter filefilter = new FileFilter() {
	        public boolean accept(File file) {
	            if (file.getName().endsWith(".xls")) {
	                return true;
	            }
	            return false;
	        }
		};
		File pathFile = new File(path);
		File[] files = pathFile.listFiles(filefilter);
		if(files == null){
			return;
		}
		for (File file : files) {
			if(file.exists()) file.delete();
		}
		//pathFile.delete();
	}
	
	//创建excel
	@Override
	public String makeExcel(String prodid){
		return makeExcel(prodid,null);
	}
	//创建excel
	@Override
	public String makeExcel(String prodid,String srcPath){
		String returnPath = "";
		try{
			List<String> fileNameList = new ArrayList<String>();
			String hql = "from BrmsRuleTableEntity where prodId = ?";
			List<BrmsRuleTableEntity> propList = brmsRuleTableService.findHql(hql, new Object[]{prodid});
			int ccc = 0;
			for (BrmsRuleTableEntity table : propList) {
				String hqlcondition = "from BrmsRuleConditionEntity where ruleTableId = ?";
				List<BrmsRuleConditionEntity> conditionList = brmsRuleConditionService.findHql(hqlcondition, new Object[]{table.getId()});
				
				
				String hqlAction = "from BrmsRuleActionEntity where ruleTableId = ?";
				
				List<BrmsRuleActionEntity> actionList = brmsRuleConditionService.findHql(hqlAction, new Object[]{table.getId()});
				String[][] templete = new String[conditionList.size()+actionList.size()][4];
				
				
				int conditionTotel = 0;
				
				List<List<BrmsConditionDetailEntity>> conditionTotalList = new ArrayList<List<BrmsConditionDetailEntity>>();
				List<List<BrmsActionDetailEntity>> actionTotalList = new ArrayList<List<BrmsActionDetailEntity>>();
				
				for (int i = 0; i < conditionList.size(); i++) {
					BrmsRuleConditionEntity condition = conditionList.get(i);
					templete[i][0] = condition.getCondId();
					templete[i][1] = condition.getBizObjName();
					templete[i][2] = condition.getPropCondScript();
					templete[i][3] = condition.getPropCondDes();
					conditionTotel++;
					List<BrmsConditionDetailEntity> conditionDetails = queryForConditionDetail(condition.getId(),condition.getRuleTableId());
					conditionTotalList.add(conditionDetails);
				}
				for (int i = 0; i < actionList.size(); i++) {
					BrmsRuleActionEntity action = actionList.get(i);
					templete[i+conditionTotel][0] = action.getActionId();
					templete[i+conditionTotel][1] = action.getBizObjName();
					templete[i+conditionTotel][2] = action.getPropActionScript();
					templete[i+conditionTotel][3] = action.getPropActionDes();
					List<BrmsActionDetailEntity> actionDetails = queryForActionDetail(action.getId(),action.getRuleTableId());
					actionTotalList.add(actionDetails);
				}
				
				String[][] newTemplete = rowTocol(templete);
				
				String[][] total = new String[conditionList.size()+actionList.size()][actionTotalList.get(0).size()];
				for (int i = 0; i < conditionTotalList.size(); i++) {
					for (int j = 0; j < conditionTotalList.get(i).size(); j++) {
						total[i][j] = conditionTotalList.get(i).get(j).getCondValue();
					}
				}
				
				for (int i = 0; i < actionTotalList.size(); i++) {
					for (int j = 0; j < actionTotalList.get(i).size(); j++) {
						total[i+conditionTotel][j] = actionTotalList.get(i).get(j).getActionValue();
					}
				}
				
				//String[][] newTotal = meger()
				String[][] newTotal = rowTocol(total);
				int flag = 0;
				for (int j = 0; j < newTemplete[0].length; j++) {
					if("CONDITION".equalsIgnoreCase(newTemplete[0][j])){
						flag = j;
						break;
					}
				}
				
				/*File f = new File("E://access_rule11.xls");
		    	if(!f.exists()){
	  	        	f.createNewFile();
	  	        }*/
				String randomName = UUID.randomUUID().toString().replace("-", "");
				//String path = getClassesPath();
				String path = null;
				if(StringUtils.isEmpty(srcPath)){
				    path = PathUtils.getClassesPath();
				}else{
					path = srcPath;
				}
				
				
				String pkg = table.getPkgAllName().replace(".", File.separator);
				path = path + pkg;
				if(ccc==0){
					cleanFiles(path);
				}
				log.info("生成的文件为："+path);
				File pf = new File(path);
				
				if(!pf.exists()){
					pf.mkdirs();
				}
				
				returnPath = path;
				String fileName = "temp"+randomName;
				//File f = File.createTempFile(fileName, ".xls",pf);
				File f = new File(pf, fileName+".xls");
				fileNameList.add(fileName);
	  	        ExcelWriter e = new ExcelWriter();  
	  	        try {  
	  	            e = new ExcelWriter(new FileOutputStream(f));  
	  	        } catch (FileNotFoundException e1) {  
	  	            e1.printStackTrace();  
	  	        } 
	  	        //第一行
	  	        e.createRow(0);
	  	        for (int i = 0; i < flag; i++) {
	  	        	 e.setCell(i, "");
				}
	  	        e.setCell(flag, "RuleSet");
	  	        e.setCell(flag+1, table.getPkgAllName());
	  	        e.createRow(1);
		        for (int i = 0; i < flag; i++) {
		        	 e.setCell(i, "");
				}
		        e.setCell(flag, "PRIORITY");
		        e.setCell(flag+1, table.getSalience());
	  	        //第二行
	  	        e.createRow(2);
	  	        for (int i = 0; i < flag; i++) {
		        	 e.setCell(i, "");
				}
	  	        e.setCell(flag, "Notes");
		        e.setCell(flag+1, table.getNote());
		        
		        //第三行
		        e.createRow(3);
		        for (int i = 0; i < flag; i++) {
		        	 e.setCell(i, "");
				}
	 	        e.setCell(flag, table.getName());
		       
	  	        for (int i = 0; i < newTemplete.length; i++) {
	  	            e.createRow(i+templeteHeight);
					for (int j = 0; j < newTemplete[i].length; j++) {
						e.setCell(j,newTemplete[i][j]);
					}
				}
	  	        
	  	        for (int i = 0; i < newTotal.length; i++) {
					e.createRow(i+newTemplete.length+templeteHeight);
					for (int j = 0; j < newTotal[i].length; j++) {
						e.setCell(j,newTotal[i][j]);
					}
				}
	  	        
	  		    JSONArray array = JSONArray.fromObject(table.getMergedRegion());
			    for (int i = 0; i < array.size(); i++) {
			    	JSONObject jb = array.getJSONObject(i);
			    	int firstColumn = Integer.valueOf(jb.getString("firstColumn"));
					int lastColumn = Integer.valueOf(jb.getString("lastColumn"));
					int firstRow = Integer.valueOf(jb.getString("firstRow"));
					int lastRow = Integer.valueOf(jb.getString("lastRow"));
					e.mergeCell(firstRow, firstColumn, lastRow, lastColumn);
				}
	  	        
	  	       try {  
	  	            e.export();  
	  	            System.out.println(" 导出Excel文件[成功] ");  
	  	        } catch (IOException ex) {  
	  	            System.out.println(" 导出Excel文件[失败] ");  
	  	            ex.printStackTrace();  
	  	        }  
	  	       ccc++;
			}
			
			makeConfigFile(returnPath,fileNameList);
			//BrmsRuleTableEntity table = tableList.get(0);

		}catch(Exception e){
			log.error("生成决策表出错",e);
		}
		return returnPath;
	}
	
	//创建config文件
	private void makeConfigFile(String path, List<String> fileNames) throws Exception{
		FileWriter fw = null;
		try {
			StringBuilder sb = new StringBuilder();
			for (String str : fileNames) {
				sb.append(str).append("#");
			}
			//File f = File.createTempFile("config", ".properties", new File(path));
			File f = new File(new File(path), "config.properties");
			if(!f.exists()){
				f.createNewFile();
			}
			fw = new FileWriter(f);
			fw.write("names=" + sb.toString());
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			fw.flush();
			fw.close();
		}
		
	}
	
	//创建excel
	@Override
	public List<File> makeExcelForCompile(String prodid){
		List<File> files = new ArrayList<File>();
		try{
			
			String hql = "from BrmsRuleTableEntity where prodId = ?";
			List<BrmsRuleTableEntity> propList = brmsRuleTableService.findHql(hql, new Object[]{prodid});
			int ccc = 0;
			for (BrmsRuleTableEntity table : propList) {
				String hqlcondition = "from BrmsRuleConditionEntity where ruleTableId = ?";
				List<BrmsRuleConditionEntity> conditionList = brmsRuleConditionService.findHql(hqlcondition, new Object[]{table.getId()});
				
				
				String hqlAction = "from BrmsRuleActionEntity where ruleTableId = ?";
				
				List<BrmsRuleActionEntity> actionList = brmsRuleConditionService.findHql(hqlAction, new Object[]{table.getId()});
				String[][] templete = new String[conditionList.size()+actionList.size()][4];
				
				
				int conditionTotel = 0;
				
				List<List<BrmsConditionDetailEntity>> conditionTotalList = new ArrayList<List<BrmsConditionDetailEntity>>();
				List<List<BrmsActionDetailEntity>> actionTotalList = new ArrayList<List<BrmsActionDetailEntity>>();
				
				for (int i = 0; i < conditionList.size(); i++) {
					BrmsRuleConditionEntity condition = conditionList.get(i);
					templete[i][0] = condition.getCondId();
					templete[i][1] = condition.getBizObjName();
					templete[i][2] = condition.getPropCondScript();
					templete[i][3] = condition.getPropCondDes();
					conditionTotel++;
					List<BrmsConditionDetailEntity> conditionDetails = queryForConditionDetail(condition.getId(),condition.getRuleTableId());
					conditionTotalList.add(conditionDetails);
				}
				for (int i = 0; i < actionList.size(); i++) {
					BrmsRuleActionEntity action = actionList.get(i);
					templete[i+conditionTotel][0] = action.getActionId();
					templete[i+conditionTotel][1] = action.getBizObjName();
					templete[i+conditionTotel][2] = action.getPropActionScript();
					templete[i+conditionTotel][3] = action.getPropActionDes();
					List<BrmsActionDetailEntity> actionDetails = queryForActionDetail(action.getId(),action.getRuleTableId());
					actionTotalList.add(actionDetails);
				}
				
				String[][] newTemplete = rowTocol(templete);
				
				String[][] total = new String[conditionList.size()+actionList.size()][actionTotalList.get(0).size()];
				for (int i = 0; i < conditionTotalList.size(); i++) {
					for (int j = 0; j < conditionTotalList.get(i).size(); j++) {
						total[i][j] = conditionTotalList.get(i).get(j).getCondValue();
					}
				}
				
				for (int i = 0; i < actionTotalList.size(); i++) {
					for (int j = 0; j < actionTotalList.get(i).size(); j++) {
						total[i+conditionTotel][j] = actionTotalList.get(i).get(j).getActionValue();
					}
				}
				
				//String[][] newTotal = meger()
				String[][] newTotal = rowTocol(total);
				int flag = 0;
				for (int j = 0; j < newTemplete[0].length; j++) {
					if("CONDITION".equalsIgnoreCase(newTemplete[0][j])){
						flag = j;
						break;
					}
				}
				
				/*File f = new File("E://access_rule11.xls");
		    	if(!f.exists()){
	  	        	f.createNewFile();
	  	        }*/
				String randomName = UUID.randomUUID().toString().replace("-", "");
				//String path = getClassesPath();
				String path = PathUtils.getClassesPath();
				String pkg = table.getPkgAllName().replace(".", File.separator);
				path = path + pkg;
			/*	if(ccc==0){
					cleanFiles(path);
				}*/
				log.info("生成的文件为："+path);
				File pf = new File(path);
				
				if(!pf.exists()){
					pf.mkdirs();
				}
				
				File f = File.createTempFile("temp"+randomName, ".xls",pf);
	  	        ExcelWriter e = new ExcelWriter();  
	  	        try {  
	  	            e = new ExcelWriter(new FileOutputStream(f));  
	  	        } catch (FileNotFoundException e1) {  
	  	            e1.printStackTrace();  
	  	        } 
	  	        //第一行
	  	        e.createRow(0);
	  	        for (int i = 0; i < flag; i++) {
	  	        	 e.setCell(i, "");
				}
	  	        e.setCell(flag, "RuleSet");
	  	        e.setCell(flag+1, table.getPkgAllName());
	  	        e.createRow(1);
		        for (int i = 0; i < flag; i++) {
		        	 e.setCell(i, "");
				}
		        e.setCell(flag, "PRIORITY");
		        e.setCell(flag+1, table.getSalience());
	  	        //第二行
	  	        e.createRow(2);
	  	        for (int i = 0; i < flag; i++) {
		        	 e.setCell(i, "");
				}
	  	        e.setCell(flag, "Notes");
		        e.setCell(flag+1, table.getNote());
		        
		        //第三行
		        e.createRow(3);
		        for (int i = 0; i < flag; i++) {
		        	 e.setCell(i, "");
				}
	 	        e.setCell(flag, table.getName());
		       
	  	        for (int i = 0; i < newTemplete.length; i++) {
	  	            e.createRow(i+templeteHeight);
					for (int j = 0; j < newTemplete[i].length; j++) {
						e.setCell(j,newTemplete[i][j]);
					}
				}
	  	        
	  	        for (int i = 0; i < newTotal.length; i++) {
					e.createRow(i+newTemplete.length+templeteHeight);
					for (int j = 0; j < newTotal[i].length; j++) {
						e.setCell(j,newTotal[i][j]);
					}
				}
	  	        
	  		    JSONArray array = JSONArray.fromObject(table.getMergedRegion());
			    for (int i = 0; i < array.size(); i++) {
			    	JSONObject jb = array.getJSONObject(i);
			    	int firstColumn = Integer.valueOf(jb.getString("firstColumn"));
					int lastColumn = Integer.valueOf(jb.getString("lastColumn"));
					int firstRow = Integer.valueOf(jb.getString("firstRow"));
					int lastRow = Integer.valueOf(jb.getString("lastRow"));
					e.mergeCell(firstRow, firstColumn, lastRow, lastColumn);
				}
	  	        
	  	       try {  
	  	            e.export();  
	  	            files.add(f);
	  	            System.out.println(" 导出Excel文件[成功] ");  
	  	        } catch (IOException ex) {  
	  	            System.out.println(" 导出Excel文件[失败] ");  
	  	            ex.printStackTrace();  
	  	        }  
	  	       ccc++;
			}
			
			//BrmsRuleTableEntity table = tableList.get(0);

		}catch(Exception e){
			log.error("生成决策表出错",e);
		}
		return files;
	}
	private List<BrmsConditionDetailEntity> queryForConditionDetail(String condId, String ruleTableId){
		String hql = "from BrmsConditionDetailEntity where condId = ? and ruleTableId = ?";
		return brmsConditionDetailService.findHql(hql, new Object[]{condId,ruleTableId});
	}
	

	
	private List<BrmsActionDetailEntity> queryForActionDetail(String actionId,String ruleTableId){
		String hql = "from BrmsActionDetailEntity where actionId = ? and ruleTableId = ?";
		return brmsActionDetailService.findHql(hql, new Object[]{actionId,ruleTableId});
	}
	
	/*public static void main(String[] args) {
		String[][] a = {{"1","2","3"},{"4","5","6"}};
		String[][] b = {{"7","8","9"},{"a","b","c"}};
		
		print(meger(a,b));
		String[] a1 = Arrays.copyOfRange(a,0,2);
		System.out.println("a1:");
		print(a1);
		String[] a2 = Arrays.copyOfRange(a,2,a.length);
		System.out.println("a2:");
		print(a2);
		print(a);
		String[][] newA1 = rowTocol(a);
		print(newA1);
		String[][] newA2 = rowTocol(newA1);
		print(newA2);
	}*/
	public static void print(String[][] args){
		for (int i = 0; i < args.length; i++) {
			for (int j = 0; j < args[i].length; j++) {
				System.out.print(args[i][j]);
			}
			System.out.println();
		}
	}
	
	/**
	 * 数组反转
	 * @param array
	 * @return
	 */
	   public static String[][] rowTocol ( String[][] array )
	    {
	        String[][] result = new String[array[0].length][array.length];
	        for (int i = 0; i < array.length; i++) {// 调整数组行列数据  
	            for (int j = 0; j < array[i].length; j++) {  
	                result[j][i] = array[i][j];
	            }  
	        }  
	        return result;
	    }
	   
	   /**
	    * 数组合并
	    * @param byte_1
	    * @param byte_2
	    * @return
	    */
	   public static String[][] meger(String[][] byte_1, String[][] byte_2){  
		   List<String[]> list = new ArrayList<String[]>(Arrays.<String[]>asList(byte_1));
		   list.addAll(Arrays.<String[]>asList(byte_2));
		   return list.toArray(byte_1);
	    }

	   
	   /**
	    * 生成excel数据
	    */
	@Override
	public Map<String,String[][]> grubDeciTableData(String id) {
		Map<String,String[][]> returnMap = new HashMap<String,String[][]>();
		try{
		// TODO Auto-generated method stub
		String hql = "from BrmsRuleTableEntity where id = ?";
		List<BrmsRuleTableEntity> tableList = brmsRuleTableService.findHql(hql, new Object[]{id});
		BrmsRuleTableEntity table = tableList.get(0);
		String hqlcondition = "from BrmsRuleConditionEntity where ruleTableId = ?";
		List<BrmsRuleConditionEntity> conditionList = brmsRuleConditionService.findHql(hqlcondition, new Object[]{table.getId()});
		
		
		String hqlAction = "from BrmsRuleActionEntity where ruleTableId = ?";
		
		List<BrmsRuleActionEntity> actionList = brmsRuleConditionService.findHql(hqlAction, new Object[]{table.getId()});
		String[][] templete = new String[conditionList.size()+actionList.size()][4];
		
		
		int conditionTotel = 0;
		
		List<List<BrmsConditionDetailEntity>> conditionTotalList = new ArrayList<List<BrmsConditionDetailEntity>>();
		List<List<BrmsActionDetailEntity>> actionTotalList = new ArrayList<List<BrmsActionDetailEntity>>();
		
		for (int i = 0; i < conditionList.size(); i++) {
			BrmsRuleConditionEntity condition = conditionList.get(i);
			templete[i][0] = condition.getCondId();
			templete[i][1] = condition.getBizObjName();
			templete[i][2] = condition.getPropCondScript();
			templete[i][3] = condition.getPropCondDes();
			conditionTotel++;
			List<BrmsConditionDetailEntity> conditionDetails = queryForConditionDetail(condition.getId(),condition.getRuleTableId());
			conditionTotalList.add(conditionDetails);
		}
		for (int i = 0; i < actionList.size(); i++) {
			BrmsRuleActionEntity action = actionList.get(i);
			templete[i+conditionTotel][0] = action.getActionId();
			templete[i+conditionTotel][1] = action.getBizObjName();
			templete[i+conditionTotel][2] = action.getPropActionScript();
			templete[i+conditionTotel][3] = action.getPropActionDes();
			List<BrmsActionDetailEntity> actionDetails = queryForActionDetail(action.getId(),action.getRuleTableId());
			actionTotalList.add(actionDetails);
		}
		
		String[][] newTemplete = rowTocol(templete);
		
		String[][] total = new String[conditionList.size()+actionList.size()][actionTotalList.get(0).size()];
		for (int i = 0; i < conditionTotalList.size(); i++) {
			for (int j = 0; j < conditionTotalList.get(i).size(); j++) {
				total[i][j] = conditionTotalList.get(i).get(j).getCondValue();
			}
		}
		
		for (int i = 0; i < actionTotalList.size(); i++) {
			for (int j = 0; j < actionTotalList.get(i).size(); j++) {
				total[i+conditionTotel][j] = actionTotalList.get(i).get(j).getActionValue();
			}
		}
		
		//String[][] newTotal = meger()
		String[][] newTotal = rowTocol(total);
		int flag = 0;
		for (int j = 0; j < newTemplete[0].length; j++) {
			if("CONDITION".equalsIgnoreCase(newTemplete[0][j])){
				flag = j;
				break;
			}
		}
		
		
	/*	File f = new File("E://access_rule11.xls");
    	if(!f.exists()){
        	f.createNewFile();
        }*/
		String[][] headData = new String[8][conditionList.size()+actionList.size()];
		/*	ExcelWriter e = new ExcelWriter();
			try {
				e = new ExcelWriter(new FileOutputStream(f));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}*/
			// 第一行
			//e.createRow(0);
		    headData = init(headData);
		    headData[0][flag] = "RuleSet";
		    headData[0][flag + 1] =table.getPkgAllName();
		
		    headData[1][flag] = "PRIORITY";
		    headData[1][flag + 1] = String.valueOf(table.getSalience());
			// 第二行
		    headData[2][flag] = "Notes";
		    headData[2][flag + 1] = table.getNote();

			// 第三行
	        headData[3][flag] = table.getName();
       
			for (int i = 0; i < newTemplete.length; i++) {
				for (int j = 0; j < newTemplete[i].length; j++) {
					headData[i + templeteHeight][j] = newTemplete[i][j];
				}
			}
			String[][] bodyData = new String[actionTotalList.get(0).size()][conditionList.size()+actionList.size()];
			for (int i = 0; i < newTotal.length; i++) {
				for (int j = 0; j < newTotal[i].length; j++) {
					bodyData[i][j] =  newTotal[i][j];
				}
			}
			String mergedData = table.getMergedRegion();
		
			JSONArray ja = JSONArray.fromObject(mergedData);
			String[] regin = new String[ja.size()];
			for (int i = 0; i < ja.size(); i++) {
				regin[i] = ja.getString(i);
			}
			String[][] remeg = {regin};
			returnMap.put("merged", remeg);
	        returnMap.put("head", headData);
	        returnMap.put("body", bodyData);
        } catch (Exception ex) {  
            log.error(" 导出Excel文件[失败] ");  
            ex.printStackTrace();  
        }
		return returnMap;  
	       
	}  
	
	private String[][] init(String[][] args){
		for (int i = 0; i < args.length; i++) {
			for (int j = 0; j < args[i].length; j++) {
				args[i][j] = "";
			}
		}
		return args;
	}

	public void delete (String tableid){
		String delTable = "delete from  brms_rule_table where id = ?";
		String delCondition = "delete from brms_rule_condition where rule_table_id = ?";
		String delAction = "delete from brms_rule_action where rule_table_id = ?";
		String delCondtionDetail = "delete from brms_condition_detail where rule_table_id = ?";
		String delActionDetail = "delete from brms_action_detail where rule_table_id = ?";
	//	brmsRuleTableService.executeSql(delTable, new Object[]{tableid});
		brmsRuleActionService.executeSql(delCondition, new Object[]{tableid});
		brmsRuleConditionService.executeSql(delAction, new Object[]{tableid});
		brmsConditionDetailService.executeSql(delCondtionDetail, new Object[]{tableid});
	    brmsActionDetailService.executeSql(delActionDetail, new Object[]{tableid});
		
	}
	@Override
	public void proceeExcelData(String id,String data,String mergedata) throws IfreException{
		try{
			JSONArray array = JSONArray.fromObject(data);
			BrmsRuleTableEntity table = brmsRuleTableService.getEntity(BrmsRuleTableEntity.class, id);
			delete(id);
			TSUser user = ResourceUtil.getSessionUserName();
			JSONArray ruleSet = array.getJSONArray(0);
			int flag = 0;
			JSONArray subarray = array.getJSONArray(0);
			for (int i = 0; i < subarray.size(); i++) {
				if(!"".equals(String.valueOf(subarray.getString(i))) ){
					flag = i;
					break;
				}
				
			}
			JSONArray prioritystr  = array.getJSONArray(1);
			JSONArray notes = array.getJSONArray(2);
			JSONArray names = array.getJSONArray(3);
			
		    table.setName(names.getString(flag ));
		    table.setPkgAllName(ruleSet.getString(flag +1));
		    table.setMergedRegion(mergedata);
		    //解析优先级
		    table.setSalience(Integer.valueOf(prioritystr.getString(flag + 1)));
		    RulePckgEntity rulePckg = new RulePckgEntity();
		    rulePckg.setName(ruleSet.getString(flag + 1));
		    table.setNote(notes.getString(flag + 1));
		    brmsRuleTableService.updateEntitie(table);
		    String tableId = table.getId();
		  
		    int size = array.size();
		    size = size - templeteHeight;
		    String[][] arrgs = new String[size][array.getJSONArray(templeteHeight).size()];
		    
		
		    for (int i = templeteHeight; i < array.size(); i++) {
				JSONArray rowlines = array.getJSONArray(i);
				for (int j = 0; j < rowlines.size(); j++) {
					arrgs[i-templeteHeight][j] = rowlines.getString(j);
				}
			}
		    
		     //截取一部分数组
		    String[][] newData = Arrays.copyOfRange(arrgs, 0, templeteHeight);
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
			    String[][] newDetailData = Arrays.copyOfRange(arrgs,templeteHeight, arrgs.length);
			    String[][] virticalNewDetailData = rowTocol(newDetailData);
			    for (int i = 0; i < virticalNewDetailData.length; i++) {
			    	if( i  < totelCondition){
			        	for (int j = 0; j < virticalNewDetailData[i].length; j++) {
				    		BrmsConditionDetailEntity conditionDetail = new BrmsConditionDetailEntity();
				    		conditionDetail.setCondId(templeteIdMap.get(i));
				    		conditionDetail.setSeq(j+1);
				    		conditionDetail.setRuleTableId(tableId);
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
					    	actionDetail.setRuleTableId(tableId);
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
			log.error("页面插入excel数据错误",e);
			throw new IfreException();
		}
		
	}
	
	//检测上传的或者保存的规则是否有效
	public boolean checkDecitable(String data,String prodId) throws Exception{
		boolean r = false;
		try{
			JSONArray array = JSONArray.fromObject(data);
			String[][] args = new String[array.size()][array.getJSONArray(0).size()];
			for (int i = 0; i < array.size(); i++) {
				JSONArray jr = array.getJSONArray(i);
				for (int j = 0; j < jr.size(); j++) {
					args[i][j] = jr.getString(j);
				}
				
			}
			r = check(args,null,prodId);
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("检查保存数据时候错误",e);
		}
		return r;
	}
	
	//检测上传的或者保存的规则是否有效
	public boolean checkDecitable(Map hm,String prodId) throws Exception{
		
	    boolean r = false;
		try{
			String createListstr = hm.get(8).toString();
			String[] createList = createListstr.split("#");
			String[][] args = new String[hm.size()][createList.length];
			//初始化
			args = init(args);
			int flag = 0;
			String firstrow = String.valueOf(hm.get(0));
			String[] core = firstrow.split("#");
		    for (int i = 0; i < core.length; i++) {
				if(!"".equals(core[i])){
					flag = i;
					break;
				}
			}
		    
		    args[0][flag] = "RuleSet";
		    args[0][flag + 1] = core[core.length - 1];
		  
		    String secondrowStr = String.valueOf(hm.get(1));
		    String[] secondRow = secondrowStr.split("#");
		    args[1][flag] = "PRIORITY";
		    args[1][flag + 1] = secondRow[secondRow.length - 1];
			// 第二行
		    String thirdrowStr = String.valueOf(hm.get(2));
		    String[] thirdRow = thirdrowStr.split("#");
		    args[2][flag] = "Notes";
		    args[2][flag + 1] = thirdRow[thirdRow.length - 1];
		    
		    
		    String fourthrowStr = String.valueOf(hm.get(3));
		    String[] fourthRow = fourthrowStr.split("#");
		    
			// 第三行
		    args[3][flag] = fourthRow[fourthRow.length - 1];
	        
	        for (int i = templeteHeight; i < hm.size(); i++) {
				String rowStr = String.valueOf(hm.get(i));
				String[] row = rowStr.split("#");
				for (int j = 0; j < row.length; j++) {
					args[i][j] = row[j];
				}
			}
	        
			r = check(args,String.valueOf(hm.get(Constants.mergerData)),prodId);
		}catch(Exception e){
			throw new Exception("检查决策表数据错误");
		}
		return r;
		
	}
	
	public boolean check(String[][] args,String merger,String prodId) throws Exception{
		
		boolean flag = false;
		try{
			String randomName = UUID.randomUUID().toString().replace("-", "");
			//获取classespath的绝对路径
			
			String path = PathUtils.getClassesPath();
			int g = findEmpty(args[0]);
			String pkg = args[0][g +1].replace(".", "/");
			path = path + pkg;
			log.info("生成的文件为："+path);
			File pf = new File(path);
			if(!pf.exists()){
				pf.mkdirs();
			}
			File f = File.createTempFile("temp"+randomName, ".xls",pf);
			ExcelWriter writer  = new ExcelWriter(new FileOutputStream(f));
			
			//writer.
			for (int i = 0; i < args.length; i++) {
				writer.createRow(i);
				for (int j = 0; j < args[i].length; j++) {
					writer.setCell(j, args[i][j]);
				}
			}
		    JSONArray array = JSONArray.fromObject(merger);
		    for (int i = 0; i < array.size(); i++) {
		    	JSONObject jb = array.getJSONObject(i);
		    	int firstColumn = Integer.valueOf(jb.getString("firstColumn"));
				int lastColumn = Integer.valueOf(jb.getString("lastColumn"));
				int firstRow = Integer.valueOf(jb.getString("firstRow"));
				int lastRow = Integer.valueOf(jb.getString("lastRow"));
				writer.mergeCell(firstRow, firstColumn, lastRow, lastColumn);
			}
		
			writer.export();
			
			RuleFactory factory = new RuleFactory();
			
			factory.checkKsession(f);
			
			f.delete();
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("验证决策表错误");
		}
		return flag;
		
		
	}
	
	
	private int findEmpty(String[] args){
		int flag = 0;
		for (int i = 0; i < args.length; i++) {
			if(!"".equals(args[i])){
				flag = i;
				break;
			}
		}
		return flag;
	}
	public static void main(String[] args) {
		UUID uuid = UUID.randomUUID();
		System.out.println(uuid.toString().replace("-", ""));
	}
}
