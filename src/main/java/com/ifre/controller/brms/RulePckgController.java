package com.ifre.controller.brms;
import java.util.HashSet;
import java.util.List;

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

import com.ifre.entity.brms.RulePckgEntity;
import com.ifre.entity.brms.RuleProdEntity;
import com.ifre.service.brms.RulePckgServiceI;

/**   
 * @Title: Controller
 * @Description: 决策包
 * @author zhangdaihao
 * @date 2016-05-19 14:25:10
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/rulePckgController")
public class RulePckgController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RulePckgController.class);

	@Autowired
	private RulePckgServiceI rulePckgService;
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
	 * 决策包列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/ifre/brms/rulePckgList");
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
	public void datagrid(RulePckgEntity rulePckg,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String sql=" ";
		if(StringUtil.isNotEmpty(rulePckg.getProdId())){
			sql += "and pc.prodId='"+rulePckg.getProdId()+"'";
		}
		if(StringUtil.isNotEmpty(rulePckg.getAllName())){
			sql += "and pc.allName='"+rulePckg.getAllName()+"'";
		}
		if(StringUtil.isNotEmpty(rulePckg.getName())){
			sql += "and pc.name like '%"+rulePckg.getName()+"%'";
		}
		if(StringUtil.isNotEmpty(rulePckg.getParentPckgId())){
			sql += "and pc.parentPckgId = '"+ rulePckg.getParentPckgId() + "'";
		}
		CriteriaQuery cq = new CriteriaQuery(RulePckgEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, rulePckg, request.getParameterMap());
		this.rulePckgService.getDataGridAReturn(cq, true,sql);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除决策包
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(RulePckgEntity rulePckg, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		rulePckg = systemService.getEntity(RulePckgEntity.class, rulePckg.getId());
		String sql="select count(*) from brms_biz_obj where PCKG_ID='"+rulePckg.getId()+"'";
	    Long a=systemService.getCountForJdbc(sql);
	    if(a>0){
	    	message = "业务对象有用到此条决策包，拒绝删除";	
	    }else{
		message = "决策包删除成功";
		rulePckgService.delete(rulePckg);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
	    }
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加决策包
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(RulePckgEntity rulePckg, HttpServletRequest request) {
		String parentPckgId=rulePckg.getParentPckgId();
		String pid=rulePckg.getAllName();		
		rulePckg.setParentPckgId(pid.split("\\.")[pid.split("\\.").length-2]);
		AjaxJson j = new AjaxJson();
		RuleProdEntity	ruleProd = systemService.getEntity(RuleProdEntity.class, rulePckg.getProdId());
		rulePckg.setOrgCode(ruleProd.getOrgCode());
		rulePckg.setOrgId(ruleProd.getOrgId());
		if (StringUtil.isNotEmpty(rulePckg.getId())) {
			message = "决策包更新成功";
			RulePckgEntity t = rulePckgService.get(RulePckgEntity.class, rulePckg.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(rulePckg, t);
				rulePckgService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "决策包更新失败";
			}
		} else {
			message = "决策包添加成功";
			rulePckgService.save(rulePckg);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 决策包列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(RulePckgEntity rulePckg, HttpServletRequest req) {
		String prodId=req.getParameter("prodId");
		String prodName=req.getParameter("prodName");
		logger.info("..........................:"+prodId+"  "+prodName);
		if (StringUtil.isNotEmpty(rulePckg.getId())) {
			rulePckg = rulePckgService.getEntity(RulePckgEntity.class, rulePckg.getId());
			RuleProdEntity	ruleProd = systemService.getEntity(RuleProdEntity.class, rulePckg.getProdId());
			rulePckg.setProdId(rulePckg.getProdId()+","+ruleProd.getName());
		}else{
			logger.info("..........................1:");
			rulePckg.setProdId(prodId+","+prodName);
		}		
		req.setAttribute("rulePckgPage", rulePckg);
		return new ModelAndView("com/ifre/brms/rulePckg");
	}
	
	
	//查出包全名列表
		@RequestMapping(params = "fullName")
		@ResponseBody
		public AjaxJson fullName(RulePckgEntity rulePckg, HttpServletRequest request) {
			AjaxJson j = new AjaxJson();
				message = "决策包更新成功";
				List<RulePckgEntity> list=rulePckgService.getList(RulePckgEntity.class);
				HashSet<String> set=new HashSet<String>();
				for(int i=0;i<list.size();i++){
					String allName=list.get(i).getAllName();
					if(!StringUtil.isEmpty(allName))					
						allName=allName.substring(0,allName.lastIndexOf("."));
						set.add(allName);					
				}
	            j.setObj(set);
			    return j;
		}
}
