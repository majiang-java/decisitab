package com.ifre.controller.ruleengin;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import com.ifre.entity.ruleengin.BrmsConditionDetailEntity;
import com.ifre.service.ruleengin.BrmsConditionDetailServiceI;

/**   
 * @Title: Controller
 * @Description: 条件描述
 * @author zhangdaihao
 * @date 2016-05-19 11:38:35
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/brmsConditionDetailController")
public class BrmsConditionDetailController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BrmsConditionDetailController.class);

	@Autowired
	private BrmsConditionDetailServiceI brmsConditionDetailService;
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
	 * 条件描述列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/ifre/ruleengin/brmsConditionDetailList");
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
	public void datagrid(BrmsConditionDetailEntity brmsConditionDetail,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BrmsConditionDetailEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, brmsConditionDetail, request.getParameterMap());
		this.brmsConditionDetailService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除条件描述
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BrmsConditionDetailEntity brmsConditionDetail, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		brmsConditionDetail = systemService.getEntity(BrmsConditionDetailEntity.class, brmsConditionDetail.getId());
		message = "条件描述删除成功";
		brmsConditionDetailService.delete(brmsConditionDetail);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加条件描述
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BrmsConditionDetailEntity brmsConditionDetail, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(brmsConditionDetail.getId())) {
			message = "条件描述更新成功";
			BrmsConditionDetailEntity t = brmsConditionDetailService.get(BrmsConditionDetailEntity.class, brmsConditionDetail.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(brmsConditionDetail, t);
				brmsConditionDetailService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "条件描述更新失败";
			}
		} else {
			message = "条件描述添加成功";
			brmsConditionDetailService.save(brmsConditionDetail);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 条件描述列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BrmsConditionDetailEntity brmsConditionDetail, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(brmsConditionDetail.getId())) {
			brmsConditionDetail = brmsConditionDetailService.getEntity(BrmsConditionDetailEntity.class, brmsConditionDetail.getId());
			req.setAttribute("brmsConditionDetailPage", brmsConditionDetail);
		}
		return new ModelAndView("com/ifre/ruleengin/brmsConditionDetail");
	}
}
