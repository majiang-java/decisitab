package com.ifre.controller.ruleengin;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ifre.entity.ruleengin.BrmsRuleConditionEntity;
import com.ifre.service.ruleengin.BrmsRuleConditionServiceI;

/**   
 * @Title: Controller
 * @Description: 规则条件
 * @author zhangdaihao
 * @date 2016-05-19 11:20:45
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/brmsRuleConditionController")
public class BrmsRuleConditionController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BrmsRuleConditionController.class);

	@Autowired
	private BrmsRuleConditionServiceI brmsRuleConditionService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 规则条件列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/ifre/ruleEngin/brmsRuleConditionList");
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
	public void datagrid(BrmsRuleConditionEntity brmsRuleCondition,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BrmsRuleConditionEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, brmsRuleCondition, request.getParameterMap());
		this.brmsRuleConditionService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除规则条件
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BrmsRuleConditionEntity brmsRuleCondition, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		brmsRuleCondition = systemService.getEntity(BrmsRuleConditionEntity.class, brmsRuleCondition.getId());
		message = "规则条件删除成功";
		brmsRuleConditionService.delete(brmsRuleCondition);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加规则条件
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BrmsRuleConditionEntity brmsRuleCondition, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(brmsRuleCondition.getId())) {
			message = "规则条件更新成功";
			BrmsRuleConditionEntity t = brmsRuleConditionService.get(BrmsRuleConditionEntity.class, brmsRuleCondition.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(brmsRuleCondition, t);
				brmsRuleConditionService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "规则条件更新失败";
			}
		} else {
			message = "规则条件添加成功";
			brmsRuleConditionService.save(brmsRuleCondition);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 规则条件列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BrmsRuleConditionEntity brmsRuleCondition, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(brmsRuleCondition.getId())) {
			brmsRuleCondition = brmsRuleConditionService.getEntity(BrmsRuleConditionEntity.class, brmsRuleCondition.getId());
			req.setAttribute("brmsRuleConditionPage", brmsRuleCondition);
		}
		return new ModelAndView("com/ifre/ruleEngin/brmsRuleCondition");
	}
}
