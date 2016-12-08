package com.ifre.controller.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ifre.entity.template.TemplateCategoryEntity;
import com.ifre.entity.template.TemplateRuleTableEntity;
import com.ifre.service.brms.TemplateMangerServiceI;
import com.ifre.service.rights.BrmsRightsServiceI;

@Controller
@RequestMapping("/templateMangerController")
public class TemplateMangerController {

	private Logger logger = Logger.getLogger(TemplateMangerController.class);
	@Autowired
	private TemplateMangerServiceI templateMangerService;
	
	@Autowired
	private BrmsRightsServiceI brmsRightsService;
	
	@Autowired
	private SystemService systemService;
	
	String message = "";
	@RequestMapping(params = "formerList")
	public ModelAndView formerList(HttpServletRequest request) {
		return new ModelAndView("com/ifre/template/templateList");
	}
	
	@RequestMapping(params = "editDecitable")
	public ModelAndView editDecitable(TemplateRuleTableEntity brmsRuleTable,HttpServletRequest request) {
		request.setAttribute("id", brmsRuleTable.getId());
		return new ModelAndView("com/ifre/template/showTemplate");
	}
	
	/**
	 * 决策表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "getDecitableData")
	@ResponseBody
	public String getDecitableData(String id,HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
//			Boolean rightResult = rightsService.labelRights("decitableHead", request);
			//屏蔽表头
			Boolean rightResult = false;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("decitableHead", rightResult);
			j.setAttributes(map);
			
			Map<String,String[][]> hm= templateMangerService.grubDeciTableData(id);
			String jb = JSONObject.toJSONString(hm);
			j.setSuccess(true);
			j.setObj(jb);
			j.setMsg(id);
		}catch(Exception e){
			logger.error("获取决策表数据错误",e);
		}
		return j.getJsonStr();
	}

	
	@RequestMapping(params = "ruleTreeGrid")
	@ResponseBody
	public Object ruleTreeGrid(HttpServletRequest request, HttpServletResponse response, TreeGrid treegrid) {
		//List<TSTypegroup> list = templateMangerService.findHql("from TSTypegroup where typegroupname = ?", "模版类型");
		List<TemplateCategoryEntity> types = templateMangerService.findHql("from TemplateCategoryEntity");
		
		List<TreeGrid> grids = new ArrayList<TreeGrid>();
		//机构过滤-后台实现-待验证
		TSUser tSUser = ResourceUtil.getSessionUserName();
		String hql;
		if("A01".equals(tSUser.getCurrentDepart().getOrgCode())){
			hql = "from TemplateRuleTableEntity where type_id = ?";
    	}else{
    		hql = "from TemplateRuleTableEntity where type_id = ? and orgCode=?";
    	} 
		if(treegrid.getId()!= null){
			TemplateCategoryEntity tStype = templateMangerService.getEntity(TemplateCategoryEntity.class, treegrid.getId());
	    	List<TemplateRuleTableEntity> tempList;
	    	//机构过滤-后台实现-待验证
	    	if("A01".equals(tSUser.getCurrentDepart().getOrgCode())){
				tempList = templateMangerService.findHql(hql, treegrid.getId());
	    	}else{
	    		tempList = templateMangerService.findHql(hql, treegrid.getId(),tSUser.getSysCompanyCode());
	    	}
	    	for (TemplateRuleTableEntity templateRuleTableEntity : tempList) {
	    		TreeGrid temptreegrid = new TreeGrid();
	    		temptreegrid.setId(templateRuleTableEntity.getId());
	    		temptreegrid.setText(templateRuleTableEntity.getName());
	    		temptreegrid.setParentId(tStype.getId());
	    		temptreegrid.setParentText(tStype.getTypename());
	    		grids.add(temptreegrid);
			}
		}
		if(treegrid.getId() ==null){
			for (TemplateCategoryEntity tsType : types) {
				List<TemplateRuleTableEntity> tempList;
				//机构过滤-后台实现-待验证
				if("A01".equals(tSUser.getCurrentDepart().getOrgCode())){
					tempList = templateMangerService.findHql(hql, tsType.getId());
		    	}else{
		    		tempList = templateMangerService.findHql(hql, tsType.getId(),tSUser.getSysCompanyCode());
		    	}
				if(!tempList.isEmpty()){
					TreeGrid temptreegrid = new TreeGrid();
					temptreegrid.setId(tsType.getId());
					temptreegrid.setText(tsType.getTypename());
					temptreegrid.setParentText(tsType.getTypename());
					if(tempList != null && !tempList.isEmpty())
					temptreegrid.setState("closed");
					grids.add(temptreegrid);
				}
			}
		}
		return grids;
	}
	
	
	
	/**
	 * 删除决策表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	@Transactional
	public AjaxJson del(TreeGrid treeGrid, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = null;
		TemplateCategoryEntity type = templateMangerService.getEntity(TemplateCategoryEntity.class, treeGrid.getId());
		if(type!= null){
			List<TemplateRuleTableEntity> list = templateMangerService.findHql("from TemplateRuleTableEntity where typeId=?",treeGrid.getId());
			for (TemplateRuleTableEntity table : list) {
				deleteSingleTemplateTable(table.getId());
			}
			 message = "模版删除成功";
		}else{
			
			deleteSingleTemplateTable(treeGrid.getId());
			 message = "模版删除成功";
		}
		j.setMsg(message);
		return j;
	}

	private void deleteSingleTemplateTable(String tableId) {
		TemplateRuleTableEntity	brmsRuleTable = templateMangerService.getEntity(TemplateRuleTableEntity.class, tableId);
	   
		templateMangerService.delete(brmsRuleTable);
		String delCondition = "delete from brms_rule_condition_template where rule_table_id = ?";
		String delAction = "delete from brms_rule_action_template where rule_table_id = ?";
		String delCondtionDetail = "delete from brms_condition_detail_template where rule_table_id = ?";
		String delActionDetail = "delete from brms_action_detail_template where rule_table_id = ?";
	//	brmsRuleTableService.executeSql(delTable, new Object[]{tableid});
		templateMangerService.executeSql(delCondition, new Object[]{brmsRuleTable.getId()});
		templateMangerService.executeSql(delAction, new Object[]{brmsRuleTable.getId()});
		templateMangerService.executeSql(delCondtionDetail, new Object[]{brmsRuleTable.getId()});
		templateMangerService.executeSql(delActionDetail, new Object[]{brmsRuleTable.getId()});
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
	}
	/**
	 * 添加决策表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveDecitable")
	@ResponseBody
	public AjaxJson saveDecitable(String id, String data, String mergedata, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			String message = "决策表更新成功";
			logger.info(data);
			logger.info(mergedata);
			templateMangerService.proceeExcelData(id, data, mergedata);
			j.setMsg(message);
			j.setSuccess(true);
		} catch (Exception e) {
			logger.error("决策表更新成功", e);
			j.setMsg("决策表更新失败");
			j.setSuccess(false);
		}
		return j;
	}

	
	
}
