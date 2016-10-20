package com.ifre.controller.brms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.common.model.json.ValidForm;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.extend.hqlsearch.parse.ObjectParseUtil;
import org.jeecgframework.core.extend.hqlsearch.parse.PageValueConvertRuleEnum;
import org.jeecgframework.core.extend.hqlsearch.parse.vo.HqlRuleEnum;
import org.jeecgframework.core.util.ListUtils;
import org.jeecgframework.core.util.MutiLangSqlCriteriaUtil;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.MutiLangServiceI;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ifre.entity.brms.VarType;
import com.ifre.entity.brms.VarTypegroup;
import com.ifre.service.brms.ModelVarServiceI;
import com.ifre.util.ResoureceUtilBrms;

/**
 * 类型字段处理类
 *
 * @author caojianmiao
 * @param <ModelVarService>
 *
 */
//@Scope("prototype")
@Controller
@RequestMapping("/modelVarController")
public class ModelVarController extends BaseController {
	private static final Logger logger = Logger.getLogger(ModelVarController.class);
	private UserService userService;
	private ModelVarServiceI modelVarService;
	private MutiLangServiceI mutiLangService;


	@Autowired
	public void setModelVarService(ModelVarServiceI modelVarService) {
		this.modelVarService = modelVarService;
	}

	@Autowired
	public void setMutiLangService(MutiLangServiceI mutiLangService) {
		this.mutiLangService = mutiLangService;
	}

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@RequestMapping(params = "druid")
	public ModelAndView druid() {
		return new ModelAndView(new RedirectView("druid/index.html"));
	}
	/**
	 * 类型字典列表页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "typeGroupTabs")
	public ModelAndView typeGroupTabs(HttpServletRequest request) {
		List<VarTypegroup> typegroupList = modelVarService.loadAll(VarTypegroup.class);
		request.setAttribute("typegroupList", typegroupList);
		return new ModelAndView("com/ifre/brms/typeGroupTabs");
	}

	/**
	 * 类型分组列表页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "typeGroupList")
	public ModelAndView typeGroupList(HttpServletRequest request) {
		return new ModelAndView("com/ifre/brms/typeGroupList");
	}

	/**
	 * 类型列表页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "typeList")
	public ModelAndView typeList(HttpServletRequest request) {
		String typegroupid = request.getParameter("typegroupid");
		VarTypegroup typegroup = modelVarService.getEntity(VarTypegroup.class, typegroupid);
		request.setAttribute("typegroup", typegroup);
		return new ModelAndView("com/ifre/brms/typeList");
	}

	/**
	 * easyuiAJAX请求数据
	 */

	@RequestMapping(params = "typeGroupGrid")
	public void typeGroupGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, VarTypegroup typegroup) {
		CriteriaQuery cq = new CriteriaQuery(VarTypegroup.class, dataGrid);
//        add-start--Author:zhangguoming  Date:20140929 for：多语言条件添加
        String vartypegroupname = request.getParameter("vartypegroupname");
        if(vartypegroupname != null && vartypegroupname.trim().length() > 0) {
            vartypegroupname = vartypegroupname.trim();
            List<String> vartypegroupnameKeyList = modelVarService.findByQueryString("select vartypegroupname from brms_var_typegroup");
            if(vartypegroupname.lastIndexOf("*")==-1){
            	vartypegroupname = vartypegroupname + "*";
            }
            MutiLangSqlCriteriaUtil.assembleCondition(vartypegroupnameKeyList, cq, "vartypegroupname", vartypegroupname);
        }
		this.modelVarService.getDataGridReturn(cq, true);
        MutiLangUtil.setMutiLangValueForList(dataGrid.getResults(), "vartypegroupname");
//        add-end--Author:zhangguoming  Date:20140929 for：多语言条件添加

		TagUtil.datagrid(response, dataGrid);
	}


	//add-start--Author:luobaoli  Date:20150607 for：增加表单分类树
	/**
	 *
	 * @param request
	 * @param comboTree
	 * @param code
	 * @return
	 */
	@RequestMapping(params = "formTree")
	@ResponseBody
	public List<ComboTree> formTree(HttpServletRequest request,final ComboTree rootCombotree) {
		String varTypegroupcode = request.getParameter("varTypegroupcode");
		VarTypegroup group = ResoureceUtilBrms.allVarTypegroups.get(varTypegroupcode.toLowerCase());
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();

		for(VarType tsType : ResoureceUtilBrms.allVarTypes.get(varTypegroupcode.toLowerCase())){
			ComboTree combotree = new ComboTree();
			combotree.setText(tsType.getTypename());
			comboTrees.add(combotree);
		}
		rootCombotree.setId(group.getVarTypegroupcode());
		rootCombotree.setText(group.getVarTypegroupname());
		rootCombotree.setChecked(false);
		rootCombotree.setChildren(comboTrees);

		return new ArrayList<ComboTree>(){{add(rootCombotree);}};
	}
	//add-end--Author:luobaoli  Date:20150607 for：增加表单分类树

	/**
	 * easyuiAJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "typeGrid")
	public void typeGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String typegroupid = request.getParameter("typegroupid");
		String typename = request.getParameter("typename");
		CriteriaQuery cq = new CriteriaQuery(VarType.class, dataGrid);
		cq.eq("varTypegroup.id", typegroupid);
		cq.like("typename", typename);
		cq.add();
		this.modelVarService.getDataGridReturn(cq, true);
        // add-start--Author:zhangguoming  Date:20140928 for：处理多语言
        MutiLangUtil.setMutiLangValueForList(dataGrid.getResults(), "typename");
        // add-end--Author:zhangguoming  Date:20140928 for：处理多语言

		TagUtil.datagrid(response, dataGrid);
	}

    // add-start--Author:zhangguoming  Date:20140928 for：数据字典修改
    /**
     * 跳转到类型页面
     * @param request request
     * @return
     */
	@RequestMapping(params = "goTypeGrid")
	public ModelAndView goTypeGrid(HttpServletRequest request) {
		String typegroupid = request.getParameter("typegroupid");
        request.setAttribute("typegroupid", typegroupid);
		return new ModelAndView("com/ifre/brms/typeListForTypegroup");
	}


	@RequestMapping(params = "typeGridTree")
	@ResponseBody
    @Deprecated // add-begin-end--Author:zhangguoming  Date:20140928 for：数据字典修改，该方法启用，数据字典不在已树结构展示了
	public List<TreeGrid> typeGridTree(HttpServletRequest request, TreeGrid treegrid) {
		CriteriaQuery cq;
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		if (treegrid.getId() != null) {
			cq = new CriteriaQuery(VarType.class);
			cq.eq("varTypegroup.id", treegrid.getId().substring(1));
			cq.add();
			List<VarType> typeList = modelVarService.getListByCriteriaQuery(cq, false);
			for (VarType obj : typeList) {
				TreeGrid treeNode = new TreeGrid();
				treeNode.setId("T"+obj.getId());
				treeNode.setText(obj.getTypename());
				treeGrids.add(treeNode);
			}
		} else {
			cq = new CriteriaQuery(VarTypegroup.class);
//            add-begin--Author:zhangguoming  Date:20140807 for：添加字典查询条件
            String varTypegroupcode = request.getParameter("varTypegroupcode");
            if(varTypegroupcode != null ) {
            	//begin--Author:JueYue  Date:2014-8-23 for：修改查询拼装
                HqlRuleEnum rule = PageValueConvertRuleEnum
						.convert(varTypegroupcode);
                Object value = PageValueConvertRuleEnum.replaceValue(rule,
                		varTypegroupcode);
				ObjectParseUtil.addCriteria(cq, "varTypegroupcode", rule, value);
				//end--Author:JueYue  Date:2014-8-23 for：修改查询拼装
                cq.add();
            }
            String vartypegroupname = request.getParameter("vartypegroupname");
            if(vartypegroupname != null && vartypegroupname.trim().length() > 0) {
                vartypegroupname = vartypegroupname.trim();
                List<String> vartypegroupnameKeyList = modelVarService.findByQueryString("select varTypegroupname from VarTypegroup");
                MutiLangSqlCriteriaUtil.assembleCondition(vartypegroupnameKeyList, cq, "varTypegroupname", vartypegroupname);
            }
//            add-end--Author:zhangguoming  Date:20140807 for：添加字典查询条件
            List<VarTypegroup> typeGroupList = modelVarService.getListByCriteriaQuery(cq, false);
			for (VarTypegroup obj : typeGroupList) {
				TreeGrid treeNode = new TreeGrid();
				treeNode.setId("G"+obj.getId());
				treeNode.setText(obj.getVarTypegroupname());
				treeNode.setCode(obj.getVarTypegroupcode());
				treeNode.setState("closed");
				treeGrids.add(treeNode);
			}
		}
		MutiLangUtil.setMutiTree(treeGrids);
		return treeGrids;
	}


    /**
	 * 删除类型分组或者类型（ID以G开头的是分组）
	 *
	 * @return
	 */
	@RequestMapping(params = "delTypeGridTree")
	@ResponseBody
	public AjaxJson delTypeGridTree(String id, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (id.startsWith("G")) {//分组
			VarTypegroup typegroup = modelVarService.getEntity(VarTypegroup.class, id.substring(1));
			message = "数据字典分组: " + mutiLangService.getLang(typegroup.getVarTypegroupname()) + "被删除 成功";
			modelVarService.delete(typegroup);
		} else {
			VarType type = modelVarService.getEntity(VarType.class, id.substring(1));
			message = "数据字典类型: " + mutiLangService.getLang(type.getTypename()) + "被删除 成功";
			modelVarService.delete(type);
		}
		modelVarService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		//刷新缓存
		modelVarService.refleshVarTypegroupCach();
		j.setMsg(message);
		return j;
	}

	/**
	 * 删除类型分组
	 *
	 * @return
	 */
	@RequestMapping(params = "delTypeGroup")
	@ResponseBody
	public AjaxJson delTypeGroup(VarTypegroup typegroup, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		typegroup = modelVarService.getEntity(VarTypegroup.class, typegroup.getId());
//        add-begin--Author:zhangguoming  Date:20140929 for：数据字典修改
		message = "类型分组: " + mutiLangService.getLang(typegroup.getVarTypegroupname()) + " 被删除 成功";
        if (ListUtils.isNullOrEmpty(typegroup.getVarTypes())) {
            modelVarService.delete(typegroup);
            modelVarService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            //刷新缓存
            modelVarService.refleshVarTypegroupCach();
        } else {
            message = "类型分组: " + mutiLangService.getLang(typegroup.getVarTypegroupname()) + " 下有类型信息，不能删除！";
        }
//        add-end--Author:zhangguoming  Date:20140929 for：数据字典修改
		j.setMsg(message);
		return j;
	}

	/**
	 * 删除类型
	 *
	 * @return
	 */
	@RequestMapping(params = "delType")
	@ResponseBody
	public AjaxJson delType(VarType type, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		type = modelVarService.getEntity(VarType.class, type.getId());
		message = "类型: " + mutiLangService.getLang(type.getTypename()) + "被删除 成功";
		modelVarService.delete(type);
		//刷新缓存
		modelVarService.refleshTypesCach(type);
		modelVarService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}

	/**
	 * 检查分组代码
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkTypeGroup")
	@ResponseBody
	public ValidForm checkTypeGroup(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String varTypegroupcode=oConvertUtils.getString(request.getParameter("param"));
		String code=oConvertUtils.getString(request.getParameter("code"));
		List<VarTypegroup> typegroups=modelVarService.findByProperty(VarTypegroup.class,"varTypegroupcode",varTypegroupcode);
		if(typegroups.size()>0&&!code.equals(varTypegroupcode))
		{
			v.setInfo("分组已存在");
			v.setStatus("n");
		}
		return v;
	}
	/**
	 * 添加类型分组
	 *
	 * @param typegroup
	 * @return
	 */
	@RequestMapping(params = "saveTypeGroup")
	@ResponseBody
	public AjaxJson saveTypeGroup(VarTypegroup varTypegroup, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(varTypegroup.getId())) {
			message = "类型分组: " + mutiLangService.getLang(varTypegroup.getVarTypegroupname()) + "被更新成功";
			userService.saveOrUpdate(varTypegroup);
			modelVarService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "类型分组: " + mutiLangService.getLang(varTypegroup.getVarTypegroupname()) + "被添加成功";
			userService.save(varTypegroup);
			modelVarService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		//刷新缓存
		modelVarService.refleshVarTypegroupCach();
		j.setMsg(message);
		return j;
	}
	/**
	 * 检查类型代码
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkType")
	@ResponseBody
	public ValidForm checkType(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String typecode=oConvertUtils.getString(request.getParameter("param"));
		String code=oConvertUtils.getString(request.getParameter("code"));
		String varTypeGroupCode=oConvertUtils.getString(request.getParameter("varTypeGroupCode"));
		StringBuilder hql = new StringBuilder("FROM ").append(VarType.class.getName()).append(" AS entity WHERE 1=1 ");
		hql.append(" AND entity.varTypegroup.varTypegroupcode =  '").append(varTypeGroupCode).append("'");
		hql.append(" AND entity.varTypegroup.typecode =  '").append(typecode).append("'");
		List<Object> types = this.modelVarService.findByQueryString(hql.toString());
		if(types.size()>0&&!code.equals(typecode))
		{
			v.setInfo("类型已存在");
			v.setStatus("n");
		}
		return v;
	}
	/**
	 * 添加类型
	 *
	 * @param type
	 * @return
	 */
	@RequestMapping(params = "saveType")
	@ResponseBody
	public AjaxJson saveType(VarType type, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(type.getId())) {
			message = "类型: " + mutiLangService.getLang(type.getTypename()) + "被更新成功";
			userService.saveOrUpdate(type);
			modelVarService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "类型: " + mutiLangService.getLang(type.getTypename()) + "被添加成功";
			userService.save(type);
			modelVarService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		//刷新缓存
		modelVarService.refleshTypesCach(type);
		j.setMsg(message);
		return j;
	}



	/**
	 * 类型分组列表页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "aouTypeGroup")
	public ModelAndView aouTypeGroup(VarTypegroup varTypegroup, HttpServletRequest req) {
		Map<String,Object> data = new HashMap<String,Object>(); 
		if (varTypegroup.getId() != null) {
			varTypegroup = modelVarService.getEntity(VarTypegroup.class, varTypegroup.getId());
//			req.setAttribute("varTypegroup1", varTypegroup);
		    data.put("varTypegroup",varTypegroup); 
		}
		return new ModelAndView("com/ifre/brms/varTypegroup",data);
	}

	/**
	 * 类型列表页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "addorupdateType")
	public ModelAndView addorupdateType(VarType type, HttpServletRequest req) {
		String typegroupid = req.getParameter("typegroupid");
		req.setAttribute("typegroupid", typegroupid);
        VarTypegroup typegroup = modelVarService.findUniqueByProperty(VarTypegroup.class, "id", typegroupid);
        String varTypegroupname = typegroup.getVarTypegroupname();
        req.setAttribute("varTypegroupname", mutiLangService.getLang(varTypegroupname));
		if (StringUtil.isNotEmpty(type.getId())) {
			type = modelVarService.getEntity(VarType.class, type.getId());
			req.setAttribute("type", type);
		}
		return new ModelAndView("com/ifre/brms/type");
	}

}
