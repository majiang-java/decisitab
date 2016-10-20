package com.ifre.controller.ruleengin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ifre.entity.brms.KnwldgLibEntity;
import com.ifre.entity.brms.RuleProdEntity;
import com.ifre.entity.ruleengin.BrmsRuleTableEntity;
import com.ifre.entity.template.TemplateRuleTableEntity;
import com.ifre.ruleengin.data.TamplateModelObjectFactory;
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
		return new ModelAndView("com/ifre/ruleengin/brmsRuleTableList");
	}

	@RequestMapping(params = "formerList")
	public ModelAndView formerList(HttpServletRequest request) {
		return new ModelAndView("com/ifre/ruleengin/brmsRuleListDiv");
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
			Boolean rightResult = rightsService.labelRights("decitableHead", request);
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
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

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
		
		List<TSTypegroup> list = brmsRuleTableService.findHql("from TSTypegroup where typegroupname = ?", "模版类型");
		
		List<TSType> types = list.get(0).getTSTypes();
		List<TreeGrid> grids = new ArrayList<TreeGrid>();
	    if(treegrid.getId()!= null){
	    	TSType tStype = brmsRuleTableService.getEntity(TSType.class, treegrid.getId());
	    	List<BrmsRuleTableEntity> tempList = brmsRuleTableService.findHql("from BrmsRuleTableEntity where type_id = ?", treegrid.getId());
	    	for (BrmsRuleTableEntity templateRuleTableEntity : tempList) {
	    		TreeGrid temptreegrid = new TreeGrid();
	    		temptreegrid.setId(templateRuleTableEntity.getId());
	    		temptreegrid.setText(templateRuleTableEntity.getName());
	    		temptreegrid.setParentId(tStype.getId());
	    		temptreegrid.setParentText(tStype.getTypename());
	    		grids.add(temptreegrid);
			}
		}
		if(treegrid.getId() ==null){
			for (TSType tsType : types) {
				List<BrmsRuleTableEntity> tempList = brmsRuleTableService.findHql("from BrmsRuleTableEntity where type_id = ?", tsType.getId());
				TreeGrid temptreegrid = new TreeGrid();
				temptreegrid.setId(tsType.getId());
				temptreegrid.setText(tsType.getTypename());
				temptreegrid.setParentText(tsType.getTypename());
				if(tempList != null && !tempList.isEmpty())
				temptreegrid.setState("closed");
				grids.add(temptreegrid);
			}
		}
	
		
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
	public AjaxJson del(BrmsRuleTableEntity brmsRuleTable, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		brmsRuleTable = systemService.getEntity(BrmsRuleTableEntity.class, brmsRuleTable.getId());
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
		
		j.setMsg(message);
		return j;
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
	 * 获取机构 知识库 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(params = "getIndustMsg")
	@ResponseBody
	public String getIndustMsg(){
		List list = new ArrayList();
		String hql = "from TSDepart where 1= 1"; 
		//第一层
		List<TSDepart> departList = systemService.findHql(hql, new Object[]{});
		for (TSDepart tsDepart : departList) {
			Map depart = new HashMap();
			String subLib = "from KnwldgLibEntity where orgId = ? and STATUS = 1 ";
			List subLibListContainer = new ArrayList();
			List<KnwldgLibEntity> subLibList = systemService.findHql(subLib, new Object[]{tsDepart.getId()});
			for (KnwldgLibEntity sublib : subLibList) {
				Map knowLib = new HashMap();
				knowLib.put("n", sublib.getName() );
				knowLib.put("v", sublib.getId());
				String subProp = "from RuleProdEntity where kknwldgId = ?";
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
	
	@RequestMapping(params = "doCreateModel")
	@ResponseBody
    public  AjaxJson doCreateModel(BrmsRuleTableEntity brmsRuleTable, HttpServletRequest req){
		AjaxJson j = new AjaxJson();
    	String departId = req.getParameter("departId");
    	String knowId = req.getParameter("knowId");
    	String prodId = req.getParameter("prodId");
    	
    	String typeCode= req.getParameter("typeid");
    	templateMangerService.cloneTemplateToBrms(departId,knowId,prodId,typeCode);
    	
    	return j;
    	//templateMangerService.
    }
}
