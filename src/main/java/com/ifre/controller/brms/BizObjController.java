package com.ifre.controller.brms;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ifre.entity.brms.BizObjEntity;
import com.ifre.entity.brms.ObjPropEntity;
import com.ifre.entity.brms.RulePckgEntity;
import com.ifre.entity.brms.RuleProdEntity;
import com.ifre.page.brms.BizObjPage;
import com.ifre.service.brms.BizObjServiceI;
import com.ifre.service.brms.RulePckgServiceI;
import com.ifre.service.ruleengin.BrmsObjPropServiceI;
import com.ifre.util.GenEntityUtil;
/**   
 * @Title: Controller
 * @Description: 业务对象
 * @author zhangdaihao
 * @date 2016-05-19 13:51:14
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/bizObjController")
public class BizObjController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BizObjController.class);

	@Autowired
	private BizObjServiceI bizObjService;
	@Autowired
	private RulePckgServiceI rulePckgService;
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
	 * 业务对象列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/ifre/brms/bizObjList");
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
	public void datagrid(BizObjEntity bizObj,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String sql=" ";
		if(StringUtil.isNotEmpty(bizObj.getProdId())){
			sql += "and ob.prodId='"+bizObj.getProdId()+"'";
		}
		if(StringUtil.isNotEmpty(bizObj.getPckgId())){
			sql += "and ob.pckgId='"+bizObj.getPckgId()+"'";
		}
		if(StringUtil.isNotEmpty(bizObj.getName())){
			sql += "and ob.name like '%"+bizObj.getName()+"%'";
		}
		CriteriaQuery cq = new CriteriaQuery(BizObjEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, bizObj);
		this.bizObjService.getDataGridAReturn(cq, true,sql);
		TagUtil.datagrid(response, dataGrid);
	}
	
//	public void createEntityCode(){
//		try{
//			String entityId ="4028aac454c887000154c8943b0f0007";
//			List<BizObjEntity> bizList = bizObjService.findHql("from BizObjEntity where Id= '"+ entityId +"'");
//			BizObjEntity entity = bizList.get(0);
//			String hql = "from RulePckgEntity where Id=? ";
//			RulePckgEntity pckg = null;
//			List<ObjPropEntity> objProperties= null;
//			List<RulePckgEntity> rpeList = rulePckgService.findHql(hql, entity.getPckgId());
//			if (rpeList != null && rpeList.size() != 0) {
//				pckg = rpeList.get(0);
//			}
//			hql = "from ObjPropEntity where bizObjId= ? ";
//			objProperties = brmsObjPropService.findHql(hql, entity.getId());
//			(new GenEntityUtil(entity,pckg, objProperties)).generateToFile();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	/**
	 * 删除业务对象
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BizObjEntity bizObj, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		bizObj = systemService.getEntity(BizObjEntity.class, bizObj.getId());
		message = "删除成功";
		bizObjService.delete(bizObj);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加业务对象
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BizObjEntity bizObj,BizObjPage bizObjPage, HttpServletRequest request) {
		List<ObjPropEntity> objPropList = null;
		RulePckgEntity pckg = null;
		String resultCode="";
		objPropList =  bizObjPage.getObjPropList();
		pckg=rulePckgService.get(RulePckgEntity.class, bizObj.getPckgId());
		bizObj.setOrgCode(pckg.getOrgCode());
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(bizObj.getId())) {
			message = "更新成功";
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			if(StringUtil.isEmpty(bizObj.getSourceCode())){
				resultCode=(new GenEntityUtil(bizObj,pckg, objPropList)).generateToFile();
				bizObj.setSourceCode(resultCode);	
			}
            logger.info(bizObj.getSourceCode());
			bizObjService.updateMain(bizObj, objPropList);		
		} else {
			message = "添加成功";					
			if(StringUtil.isEmpty(bizObj.getSourceCode())){
				resultCode=(new GenEntityUtil(bizObj,pckg, objPropList)).generateToFile();
				bizObj.setSourceCode(resultCode);	
			}
			bizObjService.addMain(bizObj, objPropList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		logger.info("............................................:"+resultCode);
		j.setMsg(message);
		return j;
	}

	
	/**
	 * 添加业务对象
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "savePage")
	@ResponseBody
	public String savePage(BizObjEntity bizObj,BizObjPage bizObjPage, HttpServletRequest request) {
//		String codeValue=request.getParameter("codeValue");
//		System.out.println("......:"+codeValue);
		RulePckgEntity pckg = null;
		String codeValue=bizObj.getSourceCode();
		System.out.println("codeValue:"+codeValue);
		pckg=rulePckgService.get(RulePckgEntity.class, bizObj.getPckgId());
		List<ObjPropEntity> objPropList =  bizObjPage.getObjPropList();	
		logger.info("字段个数为:"+objPropList.size());
		String resultCode=(new GenEntityUtil(bizObj,pckg, objPropList)).generateToFile();
		logger.info("resultCode is :"+resultCode);
		AjaxJson j = new AjaxJson();
		j.setAttributes(null);
		j.setObj(resultCode);
		j.setSuccess(true);
		j.setMsg("代码生成成功");
		resultCode=resultCode.replaceAll("\r\n", "_#@").replaceAll("\r\n\t","_@#");
		System.out.println(resultCode);
		return resultCode;
	}
	
	/**
	 * 业务对象列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BizObjEntity bizObj, HttpServletRequest req) {
		BizObjPage bizObjPage=new BizObjPage();
		RuleProdEntity ruleProd=null;
		RulePckgEntity rulePckg=null;
		String ruleName=null;
		String pckgName=null;
		String pckgId=null;
		String prodId=null;
		if (StringUtil.isNotEmpty(bizObj.getId())) {
			System.out.println("...............................11:"+bizObj.getId());
			bizObj = bizObjService.getEntity(BizObjEntity.class, bizObj.getId());
			ruleProd = systemService.getEntity(RuleProdEntity.class, bizObj.getProdId());
			rulePckg = rulePckgService.get(RulePckgEntity.class, bizObj.getPckgId());
			List<ObjPropEntity> objPropList = bizObjService.findListbySql("select * from brms_obj_prop where BIZ_OBJ_ID='"+bizObj.getId()+"'");
			ruleName=ruleProd.getName();
			pckgName=rulePckg.getAllName();
			pckgId=bizObj.getPckgId();
			prodId=bizObj.getProdId();
			bizObjPage.setId(bizObj.getId());
			bizObjPage.setSourceCode(bizObj.getSourceCode());
			bizObjPage.setName(bizObj.getName());
			bizObjPage.setDescp(bizObj.getDescp());
			req.setAttribute("objPropList", objPropList);
			System.out.println("...........................:"+objPropList.size());
			//req.setAttribute("objPropList",objPropList );
		}else{
			prodId=req.getParameter("prodId");
			System.out.println("...............................22:"+prodId);
			pckgId=req.getParameter("pckgId");
			pckgName=req.getParameter("allName");
			ruleProd = systemService.getEntity(RuleProdEntity.class, prodId);
			ruleName=ruleProd.getName();
		}
		bizObjPage.setPckgId(pckgId+","+pckgName);
		bizObjPage.setProdId(prodId+","+ruleName);
		req.setAttribute("bizObjPage", bizObjPage);

		
		return new ModelAndView("com/ifre/brms/bizObj");
	}
	
	
	
	/**
	 * 加载明细列表[对象属性]
	 * 
	 * @return
	 */
	@RequestMapping(params = "objPropList")
	public ModelAndView objPropList(BizObjEntity bizObj, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object id0 = bizObj.getId();
		//===================================================================================
		//查询-对象属性
	    String hql0 = "from ObjPropEntity where 1 = 1 AND bizObjId = ? ";
		try{
		    List<ObjPropEntity> objPropEntityList = systemService.findHql(hql0,id0);
			req.setAttribute("objPropList", objPropEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/ifre/brms/objPropList");
	}
	
	
}
