package com.ifre.controller.shared;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.ifre.entity.shared.BusiSysInfoEntity;
import com.ifre.service.shared.BusiSysInfoServiceI;

/**   
 * @Title: Controller
 * @Description: 渠道来源信息
 * @author zhangdaihao
 * @date 2016-04-14 17:49:50
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/busiSysInfoController")
public class BusiSysInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
//	private static final Logger logger = Logger.getLogger(BusiSysInfoController.class);

	@Autowired
	private BusiSysInfoServiceI busiSysInfoService;
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
	 * 渠道来源信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/ifre/shared/busiSysInfoList");
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
	public void datagrid(BusiSysInfoEntity busiSysInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BusiSysInfoEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, busiSysInfo);
		this.busiSysInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除渠道来源信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BusiSysInfoEntity busiSysInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		busiSysInfo = systemService.getEntity(BusiSysInfoEntity.class, busiSysInfo.getId());
		message = "渠道来源信息删除成功";
		busiSysInfoService.delete(busiSysInfo);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加渠道来源信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BusiSysInfoEntity busiSysInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(busiSysInfo.getId())) {
			message = "渠道来源信息更新成功";
			BusiSysInfoEntity t = busiSysInfoService.get(BusiSysInfoEntity.class, busiSysInfo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(busiSysInfo, t);
				t.setUpdateBy("system");
				t.setUpdateDate(new Date());
				busiSysInfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "渠道来源信息更新失败";
			}
		} else {
			message = "渠道来源信息添加成功";
			busiSysInfo.setStatus(0);
			busiSysInfo.setSorts(0);
			busiSysInfo.setCreateBy("system");
			busiSysInfo.setCreateDate(new Date());
			busiSysInfo.setUpdateBy("system");
			busiSysInfo.setUpdateDate(new Date());
			busiSysInfoService.save(busiSysInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 渠道来源信息列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BusiSysInfoEntity busiSysInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(busiSysInfo.getId())) {
			busiSysInfo = busiSysInfoService.getEntity(BusiSysInfoEntity.class, busiSysInfo.getId());
			req.setAttribute("busiSysInfoPage", busiSysInfo);
		}
		return new ModelAndView("com/ifre/shared/busiSysInfo");
	}
}
