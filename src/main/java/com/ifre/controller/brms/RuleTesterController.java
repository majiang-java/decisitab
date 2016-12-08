package com.ifre.controller.brms;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ifre.common.Constants;
import com.ifre.entity.brms.RulePckgEntity;
import com.ifre.entity.brms.RuleProdEntity;
import com.ifre.entity.ruleengin.BrmsRuleTableEntity;
import com.ifre.invoker.DecitabInvokerI;
import com.ifre.invoker.DecitableInvokerFactroy;
import com.ifre.ruleengin.RuleFactory;
import com.ifre.ruleengin.hotcompiler.DynamicEngine;
import com.ifre.service.brms.HotComplierServiceI;
import com.ifre.service.brms.ProcessExcelServicel;
import com.ifre.service.brms.RuleProdServiceI;
import com.ifre.util.ChineseToEnglish;
import com.ifre.util.PathUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Scope("prototype")
@Controller
@RequestMapping("/ruleTesterController")
public class RuleTesterController extends BaseController{

	Logger log = Logger.getLogger(RuleTesterController.class);
	@Autowired
	private RuleProdServiceI ruleProdService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ProcessExcelServicel processExcelService;
	@Autowired 
	private HotComplierServiceI hotComplierService;
	
	@RequestMapping(params = "datagrid")
	public void datagrid(RuleProdEntity ruleProd,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String prodType = request.getParameter("prodType");
		String sql = " ";
		if (StringUtil.isNotEmpty(ruleProd.getOrgId())) {
			sql += "and pr.orgId='" + ruleProd.getOrgId() + "'";
		}
		if (StringUtil.isNotEmpty(ruleProd.getKknwldgId())) {
			sql += "and pr.kknwldgId='" + ruleProd.getKknwldgId() + "'";
		}
		if (StringUtil.isNotEmpty(ruleProd.getName())) {
			sql += "and pr.name like '%" + ruleProd.getName() + "%'";
		}
		if (StringUtil.isNotEmpty(prodType)) {
			sql += "and pr.type =" + Integer.parseInt(prodType);
		}		
		sql += " and pr.status >= '"+Constants.START+"'" + " and pr.rightStatus = 1" ;
		
		CriteriaQuery cq = new CriteriaQuery(RuleProdEntity.class, dataGrid);
		//机构过滤-后台实现
		TSUser tSUser = ResourceUtil.getSessionUserName();
		if(!"A01".equals(tSUser.getCurrentDepart().getOrgCode())){
			//cq无效果，原因：马江修改了jeecg源码，使用的是sql的形式获取数据
			//cq.eq("orgCode", tSUser.getSysCompanyCode());
			sql += " and  pr.orgCode = '" + tSUser.getCurrentDepart().getOrgCode() + "'";
		} 
		cq.add();
		
//		cq.eq("kknwldgId", ruleProd.getKknwldgId());
//		cq.like("name", ruleProd.getName());
//		cq.eq("type", prodType);
//		cq.ge("status", Constants.START);
//		cq.eq("rightStatus", "1");
//		cq.add();
		
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, ruleProd, request.getParameterMap());
		this.ruleProdService.getDataGridAReturn(cq, true, sql);
//		this.ruleProdService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
//		createMavenFile();
	}
	
	@RequestMapping(params = "datagridJarList")
	public void datagridJarList(RuleProdEntity ruleProd,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String prodType = request.getParameter("prodType");
		String sql = " ";
		if (StringUtil.isNotEmpty(ruleProd.getOrgId())) {
			sql += "and pr.orgId='" + ruleProd.getOrgId() + "'";
		}
		if (StringUtil.isNotEmpty(ruleProd.getKknwldgId())) {
			sql += "and pr.kknwldgId='" + ruleProd.getKknwldgId() + "'";
		}
		if (StringUtil.isNotEmpty(ruleProd.getName())) {
			sql += "and pr.name like '%" + ruleProd.getName() + "%'";
		}
		if (StringUtil.isNotEmpty(prodType)) {
			sql += "and pr.type =" + Integer.parseInt(prodType);
		}	
		sql += "and pr.status >= '"+Constants.PASS_TEST+"'" + " and pr.rightStatus = 1" ;
		CriteriaQuery cq = new CriteriaQuery(RuleProdEntity.class, dataGrid);
		//机构过滤-后台实现
		TSUser tSUser = ResourceUtil.getSessionUserName();
		if(!"A01".equals(tSUser.getCurrentDepart().getOrgCode())){
			//cq无效果，原因：马江修改了jeecg源码，使用的是sql的形式获取数据
			//cq.eq("orgCode", tSUser.getSysCompanyCode());
			sql += " and  pr.orgCode = '" + tSUser.getCurrentDepart().getOrgCode() + "'";
		} 
		cq.add();
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, ruleProd, request.getParameterMap());
		this.ruleProdService.getDataGridAReturn(cq, true, sql);
		// this.ruleProdService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
//		createMavenFile();
	}
	
	@RequestMapping(params = "showTester")
	public ModelAndView showTester(HttpServletRequest request){
		String prodType = request.getParameter("prodType");
        request.setAttribute("prodType", prodType);
		if (prodType != null &&  prodType.equals("1")) {
			return new ModelAndView("com/ifre/brms/modelTestList");
		} else {
			return new ModelAndView("com/ifre/brms/ruleTestList");
		} 
		
	}
	
	@RequestMapping(params = "ruleTesterProd")
	public ModelAndView ruleTesterProd(String id,String prodName,HttpServletRequest request) throws UnsupportedEncodingException{
		request.setAttribute("id", id);
		request.setAttribute("prodName", prodName);
		return new ModelAndView("com/ifre/brms/ruleTesterProd");
	}
	
	@RequestMapping(params = "callJar")
	public ModelAndView callJar(HttpServletRequest request) throws UnsupportedEncodingException{
		return new ModelAndView("com/ifre/show/callJar");
	}
	
	@RequestMapping(params = "outerCallJar")
	public ModelAndView outerCallJar(String id,HttpServletRequest request) throws UnsupportedEncodingException{
		List<RulePckgEntity> packList = systemService.findHql("from RulePckgEntity where prodId = ?", id);
		String packc = null;
		if(!packList.isEmpty()){
			packc = packList.get(0).getAllName();
		}
		request.setAttribute("packc", packc);
		return new ModelAndView("com/ifre/show/callJarOuter");
	}
	
	
	@RequestMapping(params = "getTesterSelectData")
	@ResponseBody
	public List<Map<String,String>> getTesterSelectData(String id,HttpServletRequest request){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String hqlForFirst = "from BrmsRuleTableEntity where prodId = ? order by salience desc";
		List<BrmsRuleTableEntity> ruleList = systemService.findHql(hqlForFirst, new Object[]{id});
		String ruleId = null;
		if(!ruleList.isEmpty()){
			ruleId = ruleList.get(0).getId();
		}
		Map<String,String[][]> hm = processExcelService.grubDeciTableData(ruleId);
		String[][] head = hm.get("head");
		String[][] body = hm.get("body"); 
		int flag = 0;
		for (int i = 0; i < head[0].length; i++) {
			if(StringUtils.isNotEmpty(head[0][i])){
				flag = i;
				break;
			}
		}
		int headRows = head.length;
		int varDescP = 0;
		int varValueP = 0;
		for(int i = 0; i < head[headRows-1].length;i++ ){
			if("变量描述".equals(head[headRows-1][i])){
				varDescP =i;
				break;
			}
		}
		for(int i = 0; i < head[headRows-1].length;i++ ){
			if("变量名称".equals(head[headRows-1][i])){
				varValueP = i;
				break;
			}
		}
		String typeStr = null;
		for(int i = 0; i < head[headRows-1].length;i++ ){
			if("模型类型".equals(head[headRows-1][i])||"贷款类型".equals(head[headRows-1][i])){
				typeStr = body[0][i];
				break;
			}
		}
		log.info("模型类型:"+typeStr);
		int type = Integer.valueOf(typeStr);
		List<Map<String,String>> descList = new ArrayList<Map<String,String>>(); 
        int row = body[0].length;
	    for (int i = 0; i < body.length; i++) {
		   Map<String,String> temp = new HashMap<String,String>();
		   StringBuilder sb = new StringBuilder();
		   if(StringUtils.isEmpty(body[i][0])){
			   continue;
		   }
		   for (int j = 1; j < row; j++) {
			   sb.append(body[i][j]);
		   }
		   temp.put("text", sb.toString());
		   temp.put("value", body[i][varValueP]);
		   descList.add(temp);		 
		}
		if(type != 71){
			Map<String,Integer> setDesc = new HashMap<String,Integer>();
			Map<String,Integer> setValue = new HashMap<String,Integer>();
			for (int i = 0; i < body.length; i++) {
//				setDesc.put(body[i][varDescP],i);
				setValue.put(body[i][varValueP],i);
			}
	
		/*	Set<String> setValueP = new HashSet<String>();
			for (int i = 0; i < body.length; i++) {
				setValueP.add(body[i][varDescP]);
			}*/
//			for (Map.Entry<String, Integer> desc : setDesc.entrySet()) {
//				for (Map.Entry<String, Integer> value : setValue.entrySet()) {
//					if(desc.getValue() == value.getValue()){
//						Map<String,String> temp = new HashMap<String,String>();
//						 temp.put("text",desc.getKey());
//						 temp.put("value",StringUtils.trim(value.getKey()));
//						 list.add(temp);
//					}
//				}
//			}
			
			//变量描述可能随意变化，以变量名称为主键
			for (Map.Entry<String, Integer> value : setValue.entrySet()) {
				Map<String,String> temp = new HashMap<String,String>();
				 temp.put("text",body[value.getValue()][varDescP]);
				 temp.put("value",body[value.getValue()][varValueP]);
				 list.add(temp);
			}				
			
		}else{
			  for (int i = 0; i < body.length; i++) {
				   Map<String,String> temp = new HashMap<String,String>();
				   StringBuilder sb = new StringBuilder();
				   if(StringUtils.isEmpty(body[i][0])){
					   continue;
				   }
				   for (int j = 1; j < flag; j++) {
					   sb.append(body[i][j]);
				   }
				   temp.put("text", sb.toString());
				   temp.put("value", body[i][flag]);
				   list.add(temp);
				}
		}
	
		

	  return list;
		
	}
	/**
	 * 获取预测填写数据
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "getPredData")
	@ResponseBody
	public Map<String,Object> getPredData(String id,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> hm = new HashMap<String, Object>();
		String hqlForPreTest = "select prop.PROP_CODE code, prop.name, prop.descp from brms_obj_prop prop "
				+ "left join brms_biz_obj bizobj on prop.BIZ_OBJ_ID = bizobj.id "
				+ "where bizobj.name = 'LoanApplication' and bizobj.PROD_ID = ? ";
		List<Map<String, Object>> preList = systemService.findForJdbc(hqlForPreTest, new Object[] { id });
		hm.put("total", preList.size());
		hm.put("rows", preList);
		return hm;
	}
	
	@RequestMapping(params = "mainTest")
	@ResponseBody
	public AjaxJson mainTest(String id,String data,HttpServletRequest request){
		AjaxJson aj = new AjaxJson();
		JSONObject dataObj = JSONObject.fromObject(data);
		JSONArray array = dataObj.getJSONArray("rows");
		String returnStr = "";
		List<File> files = null;
		try {
			Map<String, String> applicantMap = new HashMap<String, String>();

			applicantMap = new HashMap<String, String>();
			for (int i = 0; i < array.size(); i++) {
				JSONObject jb = array.getJSONObject(i);
				applicantMap.put(jb.getString("testKey"), jb.getString("value"));
			}
			List<Map<String, Object>> list = hotComplierService.hotcompiler(id);
			RuleFactory factory = new RuleFactory();
			String hqlForFirst = "from BrmsRuleTableEntity where prodId = ? order by salience desc";
			List<BrmsRuleTableEntity> ruleList = systemService.findHql(hqlForFirst, new Object[]{id});
			String ruleId = null;
			if(!ruleList.isEmpty()){
				ruleId = ruleList.get(0).getId();
			}
			Map<String,String[][]> hm = processExcelService.grubDeciTableData(ruleId);
			String[][] heads = hm.get("head");
			String[][] bodys = hm.get("body");
			String typeStr = "";
			int headRows = heads.length;
			for(int i = 0; i < heads[headRows-1].length;i++ ){
				if("模型类型".equals(heads[headRows-1][i])||"贷款类型".equals(heads[headRows-1][i])){
					typeStr = bodys[0][i];
					break;
				}
			}
			log.info("模型类型:"+typeStr);
			int type = Integer.valueOf(typeStr);
			DecitabInvokerI antiFraudModelClient = DecitableInvokerFactroy.create(type);
			antiFraudModelClient.makeData(list, applicantMap,type);
			files = processExcelService.makeExcelForCompile(id);
			StatelessKieSession ks = factory.createKsession(files);
			returnStr = antiFraudModelClient.getPricingObj(ks, " ");
			RuleProdEntity prod = ruleProdService.getEntity(RuleProdEntity.class, id);
			prod.setStatus(Constants.PASS_TEST);
			ruleProdService.updateEntitie(prod);
			aj.setSuccess(true);
			aj.setObj(JSONObject.fromObject(returnStr));
			for (File file : files) {
				if (file.exists())
					file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			aj.setSuccess(false);
			aj.setMsg(e.getMessage());
		}finally{
		
		}
		return aj;

	}
	
	@RequestMapping(params = "prodPublish")
	@ResponseBody
	public AjaxJson prodPublish(String id,String pathstr,HttpServletRequest request){
		AjaxJson aj = new AjaxJson();
		try {
			RuleProdEntity prod = hotComplierService.getEntity(RuleProdEntity.class, id);
			if(Integer.valueOf(prod.getStatus())<Integer.valueOf(Constants.PASS_TEST)){
				//e.printStackTrace();
				aj.setSuccess(false);
				aj.setMsg("没有测试成功");
				return aj;
			}
			List<Map<String,Object>> list = hotComplierService.hotcompiler(id);
			DynamicEngine de = DynamicEngine.getInstance(); 
			StringBuilder sb = new StringBuilder(200);
			String mainPath = PathUtils.getClassesPath();
			log.info("mainPath path："+ mainPath);
			sb.append(mainPath.substring(0, mainPath.lastIndexOf("brms"))).append("deis")
			.append(File.separator).append("WEB-INF").append(File.separator).append("classes").append(File.separator);
			log.info("publish path："+ sb.toString());
			
			String tempPath = sb.toString();
			//tempPath.
		
			if(StringUtils.isEmpty(pathstr)){
				String[] paths = {sb.toString()};
				makePublis(id, paths, list, de,mainPath,prod);
			}else{
				String[] paths = pathstr.split("#");
				makePublis(id, paths, list, de,mainPath,prod);
			}
			//ruleProdService.updateProductStatus(id, Constants.PUBLISH);
			prod.setStatus(Constants.PUBLISH);
			prod.setPublishStatus("1");
			hotComplierService.updateEntitie(prod);
			aj.setSuccess(true);
			aj.setMsg("发布成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			aj.setSuccess(false);
			aj.setMsg("发布错误");
		} 
		return aj;
	}

	private String makeBackupPath(RuleProdEntity prod){
		List<TSTypegroup> groupList = ruleProdService.findHql("from TSTypegroup where typegroupcode = ?", "backupPath");
		String path = Constants.BACKUP_PATH;
		StringBuilder sb = new StringBuilder();
		if(groupList ==null || groupList.isEmpty()){
		}else{
			path = groupList.get(0).getTSTypes().get(0).getTypecode();
		}
		sb.append(path).append(File.separator).append(ChineseToEnglish.getPingYin(prod.getName())).append(File.separator)
		.append(new SimpleDateFormat("yyyyMMdd_hhMMss").format(new Date()));
		return sb.toString();
	}
	
	private void makePublis(String id, String[] paths, List<Map<String, Object>> list, DynamicEngine de,String mainPath,RuleProdEntity prod)
			throws Exception, IOException {
		
		//de.javaCodeCompile(list,mainPath);
		de.javaCodeToMap(list);
		String math = processExcelService.makeExcel(id,mainPath);
		String targetMath = math.replace("brms", "deis");
		File file = new File(targetMath);
		if(!file.exists()){
			file.mkdirs();
		}else{
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if(files[i].exists()) files[i].delete();
			}
		}
		FileUtils.copyDirectory(new File(math), new File(targetMath));
		String backupPath = makeBackupPath(prod);
		log.info("backup path:" +backupPath);
		File backup = new File(backupPath);
		if(!backup.exists()){
			backup.mkdirs();
		}else{
			File[] files = backup.listFiles();
			for (int i = 0; i < files.length; i++) {
				if(files[i].exists()) files[i].delete();
			}
		}
		FileUtils.copyDirectory(new File(math), new File(backupPath));
	}
	
	
	@RequestMapping(params = "showPublishPage")
	public ModelAndView showPublishPage(String id,String prodName,HttpServletRequest request) throws UnsupportedEncodingException{
		request.setAttribute("id", id);
		request.setAttribute("prodName", prodName);
		return new ModelAndView("com/ifre/brms/showPublishPage");
	}
	
	
}
