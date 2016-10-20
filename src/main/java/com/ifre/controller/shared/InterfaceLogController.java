package com.ifre.controller.shared;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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

import com.ifre.entity.shared.InterfaceLogEntity;
import com.ifre.service.shared.InterfaceLogServiceI;
//import com.alibaba.fastjson.JSONObject;

/**   
 * @Title: Controller
 * @Description: 通讯日志
 * @author zhangdaihao
 * @date 2016-04-14 17:49:02
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/interfaceLogController")
public class InterfaceLogController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(InterfaceLogController.class);

	@Autowired
	private InterfaceLogServiceI interfaceLogService;
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
	 * 通讯日志列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/ifre/shared/interfaceLogList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagrid")
	public void datagrid(InterfaceLogEntity interfaceLog,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(InterfaceLogEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, interfaceLog, request.getParameterMap());
		this.interfaceLogService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除通讯日志
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(InterfaceLogEntity interfaceLog, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		interfaceLog = systemService.getEntity(InterfaceLogEntity.class, interfaceLog.getId());
		message = "通讯日志删除成功";
		interfaceLogService.delete(interfaceLog);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		logger.info(message);
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加通讯日志
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(InterfaceLogEntity interfaceLog, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(interfaceLog.getId())) {
			message = "通讯日志更新成功";
			InterfaceLogEntity t = interfaceLogService.get(InterfaceLogEntity.class, interfaceLog.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(interfaceLog, t);
				interfaceLogService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "通讯日志更新失败";
			}
		} else {
			message = "通讯日志添加成功";
			interfaceLogService.save(interfaceLog);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		logger.info(message);
		j.setMsg(message);
		return j;
	}

	/**
	 * 通讯日志列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(InterfaceLogEntity interfaceLog, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(interfaceLog.getId())) {
			interfaceLog = interfaceLogService.getEntity(InterfaceLogEntity.class, interfaceLog.getId());
			interfaceLog.setReceiveMsg(interfaceLog.getReceiveMsg().replaceAll("(\r\n|\r|\n|\n\r)", ""));
			req.setAttribute("interfaceLogPage", interfaceLog);
		}
		return new ModelAndView("com/ifre/shared/interfaceLog");
	}
	
	
	/**
	 * easyui AJAX请求数据（测试失败）
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "interfaceLogShow")
	public void interfaceLogShow(InterfaceLogEntity interfaceLog,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(InterfaceLogEntity.class, dataGrid);
//		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, interfaceLog, request.getParameterMap());
//		this.interfaceLogService.getDataGridReturn(cq, true);
//		TagUtil.datagrid(response, dataGrid);

		if (StringUtil.isNotEmpty(interfaceLog.getId())) {
			interfaceLog = interfaceLogService.getEntity(InterfaceLogEntity.class, interfaceLog.getId());
			request.setAttribute("interfaceLogPage", interfaceLog);
		}
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		JSONObject object=JSONObject.fromObject(interfaceLog);  
		try {
			PrintWriter pw=response.getWriter();
			pw.write(object.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
