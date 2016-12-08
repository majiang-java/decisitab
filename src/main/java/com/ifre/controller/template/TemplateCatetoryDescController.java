package com.ifre.controller.template;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboBox;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ifre.entity.template.CategoryDepartEntity;
import com.ifre.entity.template.TemplateCategoryEntity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

@Scope("prototype")
@Controller
@RequestMapping("/templateDescController")
public class TemplateCatetoryDescController extends BaseController{

	@Autowired
	private SystemService systemService;
	
	private String message;
	
	
	/**
	 * 业务对象列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/ifre/template/templateDescList");
	}
	
	
	@RequestMapping(params = "showTemplateDesc")
	public ModelAndView showTemplateDesc(String templateDescId,HttpServletRequest request) {
		TSUser tSUser = ResourceUtil.getSessionUserName();
		List<TemplateCategoryEntity>  comList = new ArrayList<TemplateCategoryEntity>();
		if("A01".equals(tSUser.getCurrentDepart().getOrgCode())){
			String hql = "from TemplateCategoryEntity ";
			comList = systemService.findHql(hql);
		}else{
			List<CategoryDepartEntity> categorys = systemService.findByProperty(CategoryDepartEntity.class, "tsDepart.id", tSUser.getCurrentDepart().getId());
			for (CategoryDepartEntity categoryDepartEntity : categorys) {
				comList.add(categoryDepartEntity.getTempCategory());
			}
		}
		
		if(!comList.isEmpty()){
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"list"});
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			JSONArray ja = JSONArray.fromObject(comList,jsonConfig);
			for (int i = 0; i < ja.size(); i++) {
				JSONObject jb = ja.getJSONObject(i);
				if(jb.getString("id").equals(templateDescId)){
					jb.put("active", true);
				}else{
					jb.put("active", false);
				}
			}
			request.setAttribute("data", ja.toString());
		}
		
		return new ModelAndView("com/ifre/template/showTemplateDesc");
	}
	
	
	
	
	@RequestMapping(params = "combox")
	@ResponseBody
	public List<ComboBox> combox(HttpServletRequest request, DataGrid dataGrid) {
		List<ComboBox> list = new ArrayList<ComboBox>();
		TSUser tSUser = ResourceUtil.getSessionUserName();
		if("A01".equals(tSUser.getCurrentDepart().getOrgCode())){
			String hql = "from TemplateCategoryEntity ";
			List<TemplateCategoryEntity>  comList = systemService.findHql(hql);
			for (TemplateCategoryEntity templateCategory : comList) {
				ComboBox comBox = new ComboBox();
				comBox.setId(templateCategory.getId());
				comBox.setText(templateCategory.getTypename());
				list.add(comBox);
			}
		}else{
			//String hql = "from TemplateCategoryEntity t left join CategoryDepartEntity c with (t.id=c.tempCategory.id) where c.tsDepart.id = ?";
			List<CategoryDepartEntity> categorys = systemService.findByProperty(CategoryDepartEntity.class, "tsDepart.id", tSUser.getCurrentDepart().getId());
			
			for (CategoryDepartEntity categoryDepartEntity : categorys) {
				TemplateCategoryEntity templateCategory = categoryDepartEntity.getTempCategory();
				ComboBox comBox = new ComboBox();
				comBox.setId(templateCategory.getId());
				comBox.setText(templateCategory.getTypename());
				list.add(comBox);
			}
		}
		return list;
		
		
	}
	
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TemplateCategoryEntity templateDesc, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(templateDesc.getId())) {
			templateDesc = systemService.getEntity(TemplateCategoryEntity.class, templateDesc.getId());
			req.setAttribute("templateDesc", templateDesc);
			getOrgInfos(req,templateDesc);
		}
		return new ModelAndView("com/ifre/template/addTemplateDesc");
	}
	
	public void getOrgInfos(HttpServletRequest req, TemplateCategoryEntity templateDesc) {
		
		List<CategoryDepartEntity> categoryOrgs = systemService.findByProperty(CategoryDepartEntity.class, "tempCategory.id", templateDesc.getId());
		String orgIds = "";
		String departname = "";
		if (categoryOrgs.size() > 0) {
			for (CategoryDepartEntity categoryOrg : categoryOrgs) {
				orgIds += categoryOrg.getTsDepart().getId() + ",";
				departname += categoryOrg.getTsDepart().getDepartname() + ",";
			}
		}
		req.setAttribute("orgIds", orgIds);
		req.setAttribute("departname", departname);

	}
	@RequestMapping(params = "datagrid")
	public void datagrid(TemplateCategoryEntity templateDesc,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TemplateCategoryEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, templateDesc);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(TemplateCategoryEntity templateDesc,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(templateDesc.getId())) {
			message = "模版类型更新成功";
			TemplateCategoryEntity t = systemService.get(
					TemplateCategoryEntity.class, templateDesc.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(templateDesc, t);
				systemService.saveOrUpdate(t);
				systemService.executeSql("delete from brms_temp_depart where temp_category_id=?", t.getId());
				saveOrgList(request, t);
				systemService.addLog(message, Globals.Log_Type_UPDATE,
						Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "模版类型更新失败";
			}
		} else {
			message = "模版类型添加成功";
		
			systemService.save(templateDesc);
			saveOrgList(request, templateDesc);
			systemService.addLog(message, Globals.Log_Type_INSERT,
					Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}
	
	@RequestMapping(params = "del")
	@ResponseBody
	private AjaxJson del(TemplateCategoryEntity t,
			HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "模版类型删除成功";
		try{
			systemService.executeSql("delete from brms_temp_depart where temp_category_id=?", t.getId());
			TemplateCategoryEntity	templateCategory = systemService.getEntity(TemplateCategoryEntity.class, t.getId());
			systemService.delete(templateCategory);
			j.setMsg(message);
			j.setSuccess(true);
		}catch(Exception e){
			message = "模版类型删除失败";
			j.setMsg(message);
			j.setSuccess(false);
		}
		return j;
		
	}
	
    private void saveOrgList(HttpServletRequest request, TemplateCategoryEntity templateDesc) {
        String orgIds = oConvertUtils.getString(request.getParameter("orgIds"));

        List<CategoryDepartEntity> categoryDepartList = new ArrayList<CategoryDepartEntity>();
        List<String> orgIdList = extractIdListByComma(orgIds);
        for (String orgId : orgIdList) {
            TSDepart depart = new TSDepart();
            depart.setId(orgId);

            CategoryDepartEntity categoryDepart = new CategoryDepartEntity();
            categoryDepart.setTempCategory(templateDesc);
            categoryDepart.setTsDepart(depart);
            
            categoryDepartList.add(categoryDepart);
        }
        if (!categoryDepartList.isEmpty()) {
            systemService.batchSave(categoryDepartList);
        }
        
    }
	
    
    
	
}
