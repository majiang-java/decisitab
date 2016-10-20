package com.ifre.controller.brms;
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

import com.ifre.entity.brms.KnwldgLibEntity;
import com.ifre.service.brms.KnwldgLibServiceI;
import com.ifre.service.brms.RuleProdServiceI;

/**   
 * @Title: Controller
 * @Description: 知识库
 * @author zhangdaihao
 * @date 2016-05-19 11:47:26
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/knwldgLibController")
public class KnwldgLibController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(KnwldgLibController.class);

	@Autowired
	private KnwldgLibServiceI knwldgLibService;
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
		return new ModelAndView("com/ifre/brms/knwldgLibList");
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
	public void datagrid(KnwldgLibEntity knwldgLib,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(KnwldgLibEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, knwldgLib, request.getParameterMap());
		this.knwldgLibService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除知识库
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(KnwldgLibEntity knwldgLib, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		knwldgLib = systemService.getEntity(KnwldgLibEntity.class, knwldgLib.getId());
		String sql="select count(*) from BRMS_RULE_PROD where KKNWLDG_ID='"+knwldgLib.getId()+"'";
	    Long a=systemService.getCountForJdbc(sql);
	    if(a>0){
	    	message = "决策产品有用到此条知识库，拒绝删除";	
	    }else{
		message = "知识库删除成功";
		knwldgLibService.delete(knwldgLib);
	    systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
	    }
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
	public AjaxJson save(KnwldgLibEntity knwldgLib, HttpServletRequest request) {
		String orgId = request.getParameter("orgId");
		System.out.println(".....................................:"+orgId);	
		if (StringUtil.isNotEmpty(orgId)) {
			TSDepart depart = systemService.getEntity(TSDepart.class, orgId);
			System.out.println(depart.getOrgCode());
			knwldgLib.setOrgCode(depart.getOrgCode());
		}
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(knwldgLib.getId())) {
			message = "知识库更新成功";
			KnwldgLibEntity t = knwldgLibService.get(KnwldgLibEntity.class, knwldgLib.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(knwldgLib, t);
				knwldgLibService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "知识库更新失败";
			}
		} else {
			message = "知识库添加成功";
			knwldgLib.setCreateName("成功");
			knwldgLibService.save(knwldgLib);			
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
	public ModelAndView addorupdate(KnwldgLibEntity knwldgLib, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(knwldgLib.getId())) {
			knwldgLib = knwldgLibService.getEntity(KnwldgLibEntity.class, knwldgLib.getId());
			req.setAttribute("knwldgLibPage", knwldgLib);
		}
		return new ModelAndView("com/ifre/brms/knwldgLib");
	}
}
