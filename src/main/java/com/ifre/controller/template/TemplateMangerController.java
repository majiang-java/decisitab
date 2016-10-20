package com.ifre.controller.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ifre.entity.ruleengin.BrmsRuleTableEntity;
import com.ifre.entity.template.TemplateRuleTableEntity;
import com.ifre.service.brms.TemplateMangerServiceI;
import com.ifre.service.rights.BrmsRightsServiceI;

@Controller
@RequestMapping("/templateMangerController")
public class TemplateMangerController {

	private Logger logger = Logger.getLogger(TemplateMangerController.class);
	@Autowired
	private TemplateMangerServiceI templateMangerService;
	
	@Autowired
	private BrmsRightsServiceI brmsRightsService;
	
	@RequestMapping(params = "formerList")
	public ModelAndView formerList(HttpServletRequest request) {
		return new ModelAndView("com/ifre/template/templateList");
	}
	
	@RequestMapping(params = "editDecitable")
	public ModelAndView editDecitable(TemplateRuleTableEntity brmsRuleTable,HttpServletRequest request) {
		request.setAttribute("id", brmsRuleTable.getId());
		return new ModelAndView("com/ifre/template/showTemplate");
	}
	
	/**
	 * 决策表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "getDecitableData")
	@ResponseBody
	public String getDecitableData(String id,HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			Boolean rightResult = brmsRightsService.labelRights("decitableHead", request);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("decitableHead", rightResult);
			j.setAttributes(map);
			
			Map<String,String[][]> hm= templateMangerService.grubDeciTableData(id);
			String jb = JSONObject.toJSONString(hm);
			j.setSuccess(true);
			j.setObj(jb);
			j.setMsg(id);
		}catch(Exception e){
			logger.error("获取决策表数据错误",e);
		}
		return j.getJsonStr();
	}

	
	@RequestMapping(params = "ruleTreeGrid")
	@ResponseBody
	public Object ruleTreeGrid(HttpServletRequest request, HttpServletResponse response, TreeGrid treegrid) {
		List<TSTypegroup> list = templateMangerService.findHql("from TSTypegroup where typegroupname = ?", "模版类型");
		
		List<TSType> types = list.get(0).getTSTypes();
		List<TreeGrid> grids = new ArrayList<TreeGrid>();
	    if(treegrid.getId()!= null){
	    	TSType tStype = templateMangerService.getEntity(TSType.class, treegrid.getId());
	    	List<TemplateRuleTableEntity> tempList = templateMangerService.findHql("from TemplateRuleTableEntity where type_id = ?", treegrid.getId());
	    	for (TemplateRuleTableEntity templateRuleTableEntity : tempList) {
	    		TreeGrid temptreegrid = new TreeGrid();
	    		temptreegrid.setId(templateRuleTableEntity.getId());
	    		temptreegrid.setText(templateRuleTableEntity.getName());
	    		temptreegrid.setParentId(tStype.getId());
	    		temptreegrid.setParentText(tStype.getTypename());
	    		grids.add(temptreegrid);
			}
		}
		if(treegrid.getId() ==null){
			for (TSType tsType : types) {
				List<TemplateRuleTableEntity> tempList = templateMangerService.findHql("from TemplateRuleTableEntity where type_id = ?", tsType.getId());
				TreeGrid temptreegrid = new TreeGrid();
				temptreegrid.setId(tsType.getId());
				temptreegrid.setText(tsType.getTypename());
				temptreegrid.setParentText(tsType.getTypename());
				if(tempList != null && !tempList.isEmpty())
				temptreegrid.setState("closed");
				grids.add(temptreegrid);
			}
		}
	
		
		return grids;
	}
	
	
}
