package com.ifre.controller.ruleengin;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ifre.entity.brms.BizObjEntity;
import com.ifre.entity.brms.KnwldgLibEntity;
import com.ifre.entity.brms.RulePckgEntity;
import com.ifre.entity.brms.RuleProdEntity;
import com.ifre.entity.ruleengin.BrmsRuleTableEntity;
import com.ifre.service.brms.ProcessExcelServicel;
import com.ifre.service.brms.TemplateMangerServiceI;
import com.ifre.service.rights.BrmsRightsServiceI;
import com.ifre.service.ruleengin.BrmsRuleTableServiceI;

import net.sf.json.JSONArray;

/**   
 * @Title: Controller
 * @Description: 决策表
 * @author zhangdaihao
 * @date 2016-05-31 10:09:51
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/brmsRuleTableController")
public class BrmsRuleTableController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BrmsRuleTableController.class);

	@Autowired
	private BrmsRuleTableServiceI brmsRuleTableService;
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private BrmsRightsServiceI rightsService;
	
	@Autowired
	private ProcessExcelServicel processExcelService;
	private String message;
	
	@Autowired
	private TemplateMangerServiceI templateMangerService;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 决策表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		String prodType = request.getParameter("prodType");
        request.setAttribute("prodType", prodType);
		/*if (prodType != null &&  prodType.equals("1")) {
			return new ModelAndView("com/ifre/ruleengin/brmsModelTableList");
		} else {
			return new ModelAndView("com/ifre/ruleengin/brmsRuleTableList");
		} */
		return new ModelAndView("com/ifre/ruleengin/ruleTableList");
		
	}

	@RequestMapping(params = "formerList")
	public ModelAndView formerList(HttpServletRequest request) {
		return new ModelAndView("com/ifre/ruleengin/ruleTableList");
	}
	
	/**
	 * 决策表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "editDecitable")
	public ModelAndView editDecitable(BrmsRuleTableEntity brmsRuleTable,HttpServletRequest request) {
		request.setAttribute("id", brmsRuleTable.getId());
		return new ModelAndView("com/ifre/ruleengin/showRuleEngin");
	}

	/**
	 * 决策表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "createDecitable")
	public ModelAndView createDecitable(BrmsRuleTableEntity brmsRuleTable,HttpServletRequest request) {
		request.setAttribute("id", brmsRuleTable.getId());
		request.setAttribute("prodType", request.getParameter("prodType"));
		return new ModelAndView("com/ifre/ruleengin/createModel");
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
			
			Map<String,String[][]> hm= processExcelService.grubDeciTableData(id);
			String jb = JSONObject.toJSONString(hm);
			j.setSuccess(true);
			j.setObj(jb);
			j.setMsg(id);
		}catch(Exception e){
			logger.error("获取决策表数据错误",e);
		}
		return j.getJsonStr();
	}

	
	/**
	 * easyui AJAX请求数据-20161028检验，没有地方使用该方法
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@Deprecated
	@RequestMapping(params = "datagrid")
	public void datagrid(BrmsRuleTableEntity brmsRuleTable,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String sql=" ";
		if(StringUtil.isNotEmpty(brmsRuleTable.getOrgId())){
			sql += "and tb.orgId='"+brmsRuleTable.getOrgId()+"'";
		}
		if(StringUtil.isNotEmpty(brmsRuleTable.getKnowId())){
			sql += "and tb.knowId='"+brmsRuleTable.getKnowId()+"'";
		}
		if(StringUtil.isNotEmpty(brmsRuleTable.getProdId())){
			sql += "and tb.prodId = '"+brmsRuleTable.getProdId()+"'";
		}
		CriteriaQuery cq = new CriteriaQuery(BrmsRuleTableEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, brmsRuleTable, request.getParameterMap());
		this.brmsRuleTableService.getDataGridAReturn(cq, true,sql);
		
		
//		this.ruleProdService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
   
	@RequestMapping(params = "selfRuleTreeGrid")
	@ResponseBody
	public Object selfRuleTreeGrid(RuleProdEntity ruleProdEntry,HttpServletRequest request, HttpServletResponse response){
		TSUser tSUser = ResourceUtil.getSessionUserName();
		String hql;
		String orgId = request.getParameter("orgId");
		String prodId = request.getParameter("prodId");
		String prodType = request.getParameter("prodType");
		logger.info("orgId:" +orgId);
		logger.info("prodId:" +prodId);
		List<TSDepart> departList = brmsRuleTableService.findHql("from TSDepart");
		StringBuilder sb = new StringBuilder();
		if("A01".equals(tSUser.getCurrentDepart().getOrgCode())){
			hql = "from RuleProdEntity where 1 = 1 and type = "+prodType;
    	}else{
    		hql = "from RuleProdEntity where 1= 1 and  type = "+prodType +" and orgId= '"+tSUser.getCurrentDepart().getId()+"' ";
    	} 
		sb.append(hql);
		if(StringUtils.isNotEmpty(orgId)){
			sb.append(" and orgId = '"+ orgId+"'");
		}
		
		if(StringUtils.isNotEmpty(prodId)){
			sb.append(" and name like '%"+ prodId+"%'");
		}
    	List<RuleProdEntity> prodList = brmsRuleTableService.findHql(sb.toString());
		
		JSONArray jsonArray = new JSONArray();
		for (RuleProdEntity ruleProdEntity : prodList) {
			List<BrmsRuleTableEntity> ruleList = brmsRuleTableService.findHql("from BrmsRuleTableEntity where prodId =?", ruleProdEntity.getId());
			ruleProdEntity.setRuleTables(ruleList);
			JSONObject jb = new JSONObject();
			jb.put("text", ruleProdEntity.getName());
			for (TSDepart depart : departList) {
				if(ruleProdEntity.getOrgId().equals(depart.getId())){
					jb.put("note", depart.getDepartname());
				}
			}
			jb.put("id", ruleProdEntity.getId());
			if(ruleList.size() >0){
				
				jb.put("state", "closed");
				jsonArray.add(jb);
			}else{
				jb.put("state", "open");
			}
			
			
			for (BrmsRuleTableEntity brmsRuleTableEntity : ruleList) {
				JSONObject subJb = new JSONObject();
				subJb.put("text", brmsRuleTableEntity.getName());
				subJb.put("id", brmsRuleTableEntity.getId());
				subJb.put("src", brmsRuleTableEntity.getSalience());
				subJb.put("_parentId", ruleProdEntity.getId());
				jsonArray.add(subJb);
			}
			
		}
		
		JSONObject returnJB = new JSONObject();
		returnJB.put("total", jsonArray.size());
		returnJB.put("rows", jsonArray);
		
		return returnJB;
	}
	@Deprecated
	@RequestMapping(params = "ruleTreeGrid")
	@ResponseBody
	public Object ruleTreeGrid(RuleProdEntity ruleProdEntry,HttpServletRequest request, HttpServletResponse response, TreeGrid treegrid) {
	
/*		List<RuleProdEntity> list = brmsRuleTableService.getList(RuleProdEntity.class);
		JSONArray jsonArray = new JSONArray();
		for (RuleProdEntity ruleProdEntity : list) {
			List<BrmsRuleTableEntity> ruleList = brmsRuleTableService.findHql("from BrmsRuleTableEntity where prodId =?", ruleProdEntity.getId());
			ruleProdEntity.setRuleTables(ruleList);
			JSONObject jb = new JSONObject();
			jb.put("text", ruleProdEntity.getName());
			jb.put("id", ruleProdEntity.getId());
			jsonArray.add(jb);
			for (BrmsRuleTableEntity brmsRuleTableEntity : ruleList) {
				JSONObject subJb = new JSONObject();
				subJb.put("text", brmsRuleTableEntity.getName());
				subJb.put("id", brmsRuleTableEntity.getId());
				subJb.put("_parentId", ruleProdEntity.getId());
				jsonArray.add(subJb);
			}
			
		}
		
		JSONObject returnJB = new JSONObject();
		returnJB.put("total", jsonArray.size());
		returnJB.put("rows", jsonArray);
		
		return returnJB;*/
		
		//机构过滤-后台实现-待验证
		TSUser tSUser = ResourceUtil.getSessionUserName();
		String hql;
		String orgId = request.getParameter("orgId");
		String prodId = request.getParameter("prodId");
		String search = request.getParameter("search");
		if ("1".equals(request.getParameter("search"))) {
			treegrid.setId(null);
		}
		if("A01".equals(tSUser.getCurrentDepart().getOrgCode())){
			hql = "from BrmsRuleTableEntity where prodId = ?";
    	}else{
    		hql = "from BrmsRuleTableEntity where prodId = ? and orgId=?";
    	} 
		
		String prodType = request.getParameter("prodType");
		List<TreeGrid> grids = new ArrayList<TreeGrid>();
	    if(treegrid.getId()!= null){
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("from RuleProdEntity where id = '"+treegrid.getId()+"'");
	    	

	    
			if(StringUtils.isNotEmpty(orgId)){
				sb.append(" and orgId = '"+ orgId+"'");
			}
			
			if(StringUtils.isNotEmpty(prodId)){
				sb.append(" and prodId like %"+ prodId+"%");
			}
	    	List<RuleProdEntity> prodList = brmsRuleTableService.findHql(sb.toString());
	    	RuleProdEntity prod = null;
		    if(!prodList.isEmpty()){
		    	prod = prodList.get(0);
		    	
				List<BrmsRuleTableEntity> tempList;
		    	if("A01".equals(tSUser.getCurrentDepart().getOrgCode())){
		    		tempList = brmsRuleTableService.findHql(hql, prod.getId());
		    	}else{
		    		tempList = brmsRuleTableService.findHql(hql, prod.getId(),tSUser.getCurrentDepart().getId());
		    	}
		    	for (BrmsRuleTableEntity templateRuleTableEntity : tempList) {
		    		TreeGrid temptreegrid = new TreeGrid();
		    		temptreegrid.setId(templateRuleTableEntity.getId());
		    		temptreegrid.setText(templateRuleTableEntity.getName());
		    		temptreegrid.setSrc(String.valueOf(templateRuleTableEntity.getSalience()));
		    		temptreegrid.setParentId(prod.getId());
		    		temptreegrid.setParentText(prod.getName());
		    		grids.add(temptreegrid);
				}
	    	}
		}else {
			StringBuilder sb = new StringBuilder();
			sb.append("from RuleProdEntity where type = " + Integer.parseInt(prodType));
			if(StringUtils.isNotEmpty(orgId)){
				sb.append(" and orgId = '"+ orgId+"'");
			}
			if(StringUtils.isNotEmpty(prodId)){
				sb.append(" and prodId like %"+ prodId+"%");
			}
			List<RuleProdEntity> list = brmsRuleTableService.findHql(sb.toString());
			
			for (RuleProdEntity prod : list) {
				List<BrmsRuleTableEntity> tempList;
				if("A01".equals(tSUser.getCurrentDepart().getOrgCode())){
		    		tempList = brmsRuleTableService.findHql(hql, prod.getId());
		    	}else{
		    		tempList = brmsRuleTableService.findHql(hql, prod.getId(),tSUser.getCurrentDepart().getId());
		    	}
				if(tempList != null && !tempList.isEmpty()){
					TreeGrid temptreegrid = new TreeGrid();
					temptreegrid.setId(prod.getId());
					temptreegrid.setText(prod.getName());
					temptreegrid.setNote(prod.getOrgId());
					temptreegrid.setParentText(prod.getName());
					temptreegrid.setState("closed");
					grids.add(temptreegrid);
				}
				
				
			}
		}
	
	    request.removeAttribute("search");
		return grids;
		
	}
	
	
	
	@Deprecated
	private void queryForName(BrmsRuleTableEntity table){
		String departhql = "from TSDepart where id = ?";
		List<TSDepart> departlist = systemService.findHql(departhql, new Object[]{table.getOrgId()});
		if(!departlist.isEmpty()){
			table.setOrgName(departlist.get(0).getDepartname());
		}
		String knowhql = "from KnwldgLibEntity where id = ?";
		List<KnwldgLibEntity> knowlist = systemService.findHql(knowhql, new Object[]{table.getKnowId()});
		if(!knowlist.isEmpty()){
			table.setKnowName(knowlist.get(0).getName());
		}
		String prodhql = "from RuleProdEntity where id = ?";
		List<RuleProdEntity> prodlist = systemService.findHql(prodhql, new Object[]{table.getProdId()});
		if(!prodlist.isEmpty()){
			table.setProdName(prodlist.get(0).getName());
		}
		
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
		try{
			RuleProdEntity prodEntry = systemService.getEntity(RuleProdEntity.class, treeGrid.getId());
			if(prodEntry!= null){
				List<BrmsRuleTableEntity> tableList =  systemService.findHql("from BrmsRuleTableEntity where prodId=?", treeGrid.getId());
				for (BrmsRuleTableEntity brmsRuleTableEntity : tableList) {
					deleteSingleTable(brmsRuleTableEntity.getId());
				}
				List<BizObjEntity> bizObjectList = systemService.findHql("from BizObjEntity where prodId = ?", treeGrid.getId());
				systemService.deleteAllEntitie(bizObjectList);
				
				List<RulePckgEntity> rulePckgList = systemService.findHql("from RulePckgEntity where prodId = ?", treeGrid.getId());
				systemService.deleteAllEntitie(rulePckgList);
				//初始化状态
				prodEntry.setStatus("0");
				systemService.updateEntitie(prodEntry);
				j.setMsg("删除成功");
				j.setSuccess(true);
			}else{
				deleteSingleTable(treeGrid.getId());
				j.setMsg("删除成功");
				j.setSuccess(true);
			}
		}catch(Exception e){
			j.setMsg("删除失败");
			j.setSuccess(false);
		}
		return j;
	}

	private void deleteSingleTable(String tableId) {
		BrmsRuleTableEntity	brmsRuleTable = systemService.getEntity(BrmsRuleTableEntity.class, tableId);
		message = "决策表删除成功";
		brmsRuleTableService.delete(brmsRuleTable);
		String delCondition = "delete from brms_rule_condition where rule_table_id = ?";
		String delAction = "delete from brms_rule_action where rule_table_id = ?";
		String delCondtionDetail = "delete from brms_condition_detail where rule_table_id = ?";
		String delActionDetail = "delete from brms_action_detail where rule_table_id = ?";
//	brmsRuleTableService.executeSql(delTable, new Object[]{tableid});
		systemService.executeSql(delCondition, new Object[]{brmsRuleTable.getId()});
		systemService.executeSql(delAction, new Object[]{brmsRuleTable.getId()});
		systemService.executeSql(delCondtionDetail, new Object[]{brmsRuleTable.getId()});
		systemService.executeSql(delActionDetail, new Object[]{brmsRuleTable.getId()});
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
	public AjaxJson saveDecitable(String id,String data,String mergedata, HttpServletRequest request) {
		    AjaxJson j = new AjaxJson();
			try{
				message = "决策表更新成功";
				logger.info(data);
				logger.info(mergedata);
				processExcelService.proceeExcelData(id, data,mergedata);
				j.setMsg(message);
				j.setSuccess(true);
			}catch(Exception e){
				logger.error("决策表更新成功",e);
				j.setMsg("决策表更新失败");
				j.setSuccess(false);
			}
			try {
				templateMangerService.syncDecisitabToData(id);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return j;
		}

	/**
	 * 添加决策表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "update")
	@ResponseBody
	public AjaxJson update(BrmsRuleTableEntity brmsRuleTable, HttpServletRequest request) {
			AjaxJson j = new AjaxJson();
			message = "决策表更新成功";
			BrmsRuleTableEntity t = brmsRuleTableService.get(BrmsRuleTableEntity.class, brmsRuleTable.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(brmsRuleTable, t);
				brmsRuleTableService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "决策表更新失败";
			}
			j.setMsg(message);
			return j;
		}

	
	
	/**
	 * 添加决策表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BrmsRuleTableEntity brmsRuleTable, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(brmsRuleTable.getId())) {
			message = "决策表更新成功";
			BrmsRuleTableEntity t = brmsRuleTableService.get(BrmsRuleTableEntity.class, brmsRuleTable.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(brmsRuleTable, t);
				brmsRuleTableService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "决策表更新失败";
			}
		} else {
			message = "决策表添加成功";
			brmsRuleTableService.save(brmsRuleTable);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 决策表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BrmsRuleTableEntity brmsRuleTable, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(brmsRuleTable.getId())) {
			brmsRuleTable = brmsRuleTableService.getEntity(BrmsRuleTableEntity.class, brmsRuleTable.getId());
			req.setAttribute("brmsRuleTablePage", brmsRuleTable);
		}
		return new ModelAndView("com/ifre/ruleengin/brmsRuleTable");
	}
	
	/**
	 * 获取机构 产品 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "getIndustMsg")
	@ResponseBody
	public String getIndustMsg(HttpServletRequest req){
		String prodType = req.getParameter("prodType");
		List list = new ArrayList();
		//String hql = "from TSDepart where 1= 1 and status = 1 and startDate <= ? and endDate >= ?"; 
		//第一层
		TSUser tSUser = ResourceUtil.getSessionUserName();
		
		Date curr = new Date();
	//	List<TSDepart> departList = systemService.findHql(hql, new Object[]{curr,curr});
		CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
		if (!"A01".equals(tSUser.getCurrentDepart().getOrgCode())) {
			cq.eq("orgCode", tSUser.getSysCompanyCode());
		}
	
		tSUser = ResourceUtil.getSessionUserName();
		if (!"A01".equals(tSUser.getCurrentDepart().getOrgCode())) {
			cq.eq("id", tSUser.getCurrentDepart().getId());
		} else {
			cq.isNull("TSPDepart");
		}
		
		cq.eq("status", 1);
		cq.add();
		List<TSDepart> departsList = new ArrayList<TSDepart>();
		List<TSDepart> dList = systemService.getListByCriteriaQuery(cq, false);
		for (TSDepart tsDepart : dList) {
			departsList.add(tsDepart);
			if(!tsDepart.getTSDeparts().isEmpty()){
				departsList.addAll(tsDepart.getTSDeparts());
			}
		}
	
		for (TSDepart tsDepart : departsList) {
			Map depart = new HashMap();
			String subLib = "from KnwldgLibEntity where orgId = ? and STATUS = 1 ";
			List subLibListContainer = new ArrayList();
			List<KnwldgLibEntity> subLibList = systemService.findHql(subLib, new Object[]{tsDepart.getId()});
			for (KnwldgLibEntity sublib : subLibList) {
				Map knowLib = new HashMap();
				knowLib.put("n", sublib.getName() );
				knowLib.put("v", sublib.getId());
				String subProp = "from RuleProdEntity where kknwldgId = ? and rightStatus = 1 and type = " + Integer.parseInt(prodType);
				List subprodContainer = new ArrayList();
				List<RuleProdEntity> subPropList = systemService.findHql(subProp, new Object[]{sublib.getId()});
				for (RuleProdEntity ruleProdEntity : subPropList) {
					Map subProd = new HashMap();
					subProd.put("s", ruleProdEntity.getName());
					subProd.put("v",ruleProdEntity.getId());
					subprodContainer.add(subProd);
				}
				knowLib.put("a", subprodContainer);
				subLibListContainer.add(knowLib);
			}
			depart.put("c", subLibListContainer);
			depart.put("p",tsDepart.getDepartname());
			depart.put("v",tsDepart.getId());
			list.add(depart);
		}
		
		JSONArray array = JSONArray.fromObject(list);
		JSONObject jb = new JSONObject();
		jb.put("citylist", array);
		return jb.toString();
	}
	
	//同步数据
	@RequestMapping(params = "doSyncDataModel")
	@ResponseBody
	public AjaxJson doSyncDataModel(RuleProdEntity ruleProd, HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		try{
			templateMangerService.syncDataToDecisitab(ruleProd);
			j.setMsg("数据同步成功");
			j.setSuccess(true);
		}catch(Exception e){
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		return j;
	}
	
/*	@RequestMapping(params = "doSyncDecitab")
	@ResponseBody
	public AjaxJson doSyncDecitab(RuleProdEntity ruleProd, HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		try{
			templateMangerService.syncDecisitabToData(ruleProd);
			j.setMsg("数据同步成功");
			j.setSuccess(true);
		}catch(Exception e){
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		return j;
		
	}*/
	
	@RequestMapping(params = "doCreateModel")
	@ResponseBody
    public  AjaxJson doCreateModel(BrmsRuleTableEntity brmsRuleTable, HttpServletRequest req){
		AjaxJson j = new AjaxJson();
    	String departId = req.getParameter("departId");
    	String knowId = req.getParameter("knowId");
    	String prodId = req.getParameter("prodId");
    	String isUse = req.getParameter("isUse");
    	String typeId= req.getParameter("typeid");
    	
    	if(!brmsRuleTableService.isExist(departId,knowId,prodId)){
    		j.setSuccess(false);
    		j.setMsg("此决策方案中已经包含规则，无法创建");
    	}else{
    		try{
    			templateMangerService.cloneTemplateToBrms(departId,knowId,prodId,typeId,isUse);
    		}catch(Exception e){
    			j.setSuccess(false);
        		j.setMsg(e.getMessage());
    		}
    	}
    	
    	return j;
    	//templateMangerService.
    }
}
