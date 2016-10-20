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

import com.ifre.entity.ruleengin.BrmsKnwldgLibEntity;
import com.ifre.service.ruleengin.BrmsKnwldgLibServiceI;

/**   
 * @Title: Controller
 * @Description: 知识库
 * @author zhangdaihao
 * @date 2016-05-19 11:17:09
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/brmsKnwldgLibController")
public class BrmsKnwldgLibController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BrmsKnwldgLibController.class);

	@Autowired
	private BrmsKnwldgLibServiceI brmsKnwldgLibService;
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
	 * 知识库列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/ifre/com.ifre/brmsKnwldgLibList");
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
	public void datagrid(BrmsKnwldgLibEntity brmsKnwldgLib,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BrmsKnwldgLibEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, brmsKnwldgLib, request.getParameterMap());
		this.brmsKnwldgLibService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除知识库
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BrmsKnwldgLibEntity brmsKnwldgLib, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		brmsKnwldgLib = systemService.getEntity(BrmsKnwldgLibEntity.class, brmsKnwldgLib.getId());
		message = "知识库删除成功";
		brmsKnwldgLibService.delete(brmsKnwldgLib);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加知识库
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BrmsKnwldgLibEntity brmsKnwldgLib, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(brmsKnwldgLib.getId())) {
			message = "知识库更新成功";
			BrmsKnwldgLibEntity t = brmsKnwldgLibService.get(BrmsKnwldgLibEntity.class, brmsKnwldgLib.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(brmsKnwldgLib, t);
				brmsKnwldgLibService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "知识库更新失败";
			}
		} else {
			message = "知识库添加成功";
			brmsKnwldgLibService.save(brmsKnwldgLib);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 知识库列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BrmsKnwldgLibEntity brmsKnwldgLib, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(brmsKnwldgLib.getId())) {
			brmsKnwldgLib = brmsKnwldgLibService.getEntity(BrmsKnwldgLibEntity.class, brmsKnwldgLib.getId());
			req.setAttribute("brmsKnwldgLibPage", brmsKnwldgLib);
		}
		return new ModelAndView("com/ifre/com.ifre/brmsKnwldgLib");
	}
}
