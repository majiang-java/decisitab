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

import com.ifre.entity.ruleengin.BrmsRuleActionEntity;
import com.ifre.service.ruleengin.BrmsRuleActionServiceI;

/**   
 * @Title: Controller
 * @Description: 规则动作
 * @author zhangdaihao
 * @date 2016-05-19 11:19:29
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/brmsRuleActionController")
public class BrmsRuleActionController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BrmsRuleActionController.class);

	@Autowired
	private BrmsRuleActionServiceI brmsRuleActionService;
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
	 * 规则动作列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/ifre/com.ifre/brmsRuleActionList");
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
	public void datagrid(BrmsRuleActionEntity brmsRuleAction,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BrmsRuleActionEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, brmsRuleAction, request.getParameterMap());
		this.brmsRuleActionService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除规则动作
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BrmsRuleActionEntity brmsRuleAction, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		brmsRuleAction = systemService.getEntity(BrmsRuleActionEntity.class, brmsRuleAction.getId());
		message = "规则动作删除成功";
		brmsRuleActionService.delete(brmsRuleAction);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加规则动作
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BrmsRuleActionEntity brmsRuleAction, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(brmsRuleAction.getId())) {
			message = "规则动作更新成功";
			BrmsRuleActionEntity t = brmsRuleActionService.get(BrmsRuleActionEntity.class, brmsRuleAction.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(brmsRuleAction, t);
				brmsRuleActionService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "规则动作更新失败";
			}
		} else {
			message = "规则动作添加成功";
			brmsRuleActionService.save(brmsRuleAction);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 规则动作列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BrmsRuleActionEntity brmsRuleAction, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(brmsRuleAction.getId())) {
			brmsRuleAction = brmsRuleActionService.getEntity(BrmsRuleActionEntity.class, brmsRuleAction.getId());
			req.setAttribute("brmsRuleActionPage", brmsRuleAction);
		}
		return new ModelAndView("com/ifre/com.ifre/brmsRuleAction");
	}
}
