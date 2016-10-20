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

import com.ifre.entity.ruleengin.BrmsObjPropEntity;
import com.ifre.service.ruleengin.BrmsObjPropServiceI;

/**   
 * @Title: Controller
 * @Description: 数据属性
 * @author zhangdaihao
 * @date 2016-05-19 11:17:56
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/brmsObjPropController")
public class BrmsObjPropController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BrmsObjPropController.class);

	@Autowired
	private BrmsObjPropServiceI brmsObjPropService;
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
	 * 数据属性列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/ifre/com.ifre/brmsObjPropList");
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
	public void datagrid(BrmsObjPropEntity brmsObjProp,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BrmsObjPropEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, brmsObjProp, request.getParameterMap());
		this.brmsObjPropService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除数据属性
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BrmsObjPropEntity brmsObjProp, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		brmsObjProp = systemService.getEntity(BrmsObjPropEntity.class, brmsObjProp.getId());
		message = "数据属性删除成功";
		brmsObjPropService.delete(brmsObjProp);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加数据属性
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BrmsObjPropEntity brmsObjProp, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(brmsObjProp.getId())) {
			message = "数据属性更新成功";
			BrmsObjPropEntity t = brmsObjPropService.get(BrmsObjPropEntity.class, brmsObjProp.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(brmsObjProp, t);
				brmsObjPropService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "数据属性更新失败";
			}
		} else {
			message = "数据属性添加成功";
			brmsObjPropService.save(brmsObjProp);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 数据属性列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BrmsObjPropEntity brmsObjProp, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(brmsObjProp.getId())) {
			brmsObjProp = brmsObjPropService.getEntity(BrmsObjPropEntity.class, brmsObjProp.getId());
			req.setAttribute("brmsObjPropPage", brmsObjProp);
		}
		return new ModelAndView("com/ifre/com.ifre/brmsObjProp");
	}
}
