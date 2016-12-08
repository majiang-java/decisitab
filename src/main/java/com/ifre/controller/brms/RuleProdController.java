package com.ifre.controller.brms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ifre.common.Constants;
import com.ifre.entity.brms.KnwldgLibEntity;
import com.ifre.entity.brms.RulePckgEntity;
import com.ifre.entity.brms.RuleProdEntity;
import com.ifre.entity.ruleengin.BrmsRuleProdEntity;
import com.ifre.entity.ruleengin.BrmsRuleTableEntity;
import com.ifre.service.brms.HotComplierServiceI;
import com.ifre.service.brms.KnwldgLibServiceI;
import com.ifre.service.brms.ProcessExcelServicel;
import com.ifre.service.brms.RuleProdServiceI;
import com.ifre.util.ChineseToEnglish;
import com.ifre.util.GenMavenFileUtil;
import com.ifre.util.encrypte.EncryptClassDirectory;
import com.ifre.util.encrypte.IfreClassLoader;

/**
 * @Title: Controller
 * @Description: 决策方案
 * @author zhangdaihao
 * @date 2016-05-19 14:23:56
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/ruleProdController")
public class RuleProdController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RuleProdController.class);
	private static final String fileSeparator = "/";
	@Autowired
	private RuleProdServiceI ruleProdService;
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private HotComplierServiceI hotCompilerService;

	@Autowired
	private KnwldgLibServiceI knwldgLibService;
	private String message;
	
	@Autowired
	private ProcessExcelServicel processExcelService;	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 决策方案列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/ifre/brms/ruleProdList");
	}

	/**
	 * 决策方案列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "jarlist")
	public ModelAndView jarList(HttpServletRequest request) {
		String prodType = request.getParameter("prodType");
        request.setAttribute("prodType", prodType);
		if (prodType != null &&  prodType.equals("1")) {
			return new ModelAndView("com/ifre/brms/modelJarList");
		} else {
			return new ModelAndView("com/ifre/brms/ruleJarList");
		} 
		
	}
	
	@RequestMapping(params = "outerJarlist")
	public ModelAndView outerJarlist(HttpServletRequest request) {
		String prodType = request.getParameter("prodType");
        request.setAttribute("prodType", prodType);
		return new ModelAndView("com/ifre/outer/outerJarList");
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
	public void datagrid(RuleProdEntity ruleProd, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
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
	}

	public void createMavenFile() {
		try {
			String entityId = "4028aac454c7b3890154c7b84b7c0001";
			List<RuleProdEntity> bizList = ruleProdService.findHql("from RuleProdEntity where Id= '" + entityId + "'");
			RuleProdEntity entity = bizList.get(0);
			GenMavenFileUtil.GenMavenPropFile(entity);
			GenMavenFileUtil.GenMavenXmlFile(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除决策方案
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(RuleProdEntity ruleProd, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		ruleProd = systemService.getEntity(RuleProdEntity.class, ruleProd.getId());
		String sql = "select count(*) from brms_rule_pckg where PROD_ID='" + ruleProd.getId() + "'";
		Long a = systemService.getCountForJdbc(sql);
		if (a > 0) {
			message = "决策包有用到此条决策方案，拒绝删除";
		} else {
			message = "决策方案删除成功";
			ruleProdService.delete(ruleProd);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加决策方案
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(RuleProdEntity ruleProd, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(ruleProd.getId())) {
			message = "决策方案更新成功";
			RuleProdEntity t = ruleProdService.get(RuleProdEntity.class, ruleProd.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(ruleProd, t);
				ruleProdService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "决策方案更新失败";
			}
		} else {
			message = "决策方案添加成功";
			ruleProd.setStatus(Constants.START);
			ruleProd.setRightStatus(Constants.ACTIVATE);
			ruleProdService.save(ruleProd);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 决策方案列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(RuleProdEntity ruleProd, HttpServletRequest req) {
		String knowId = req.getParameter("knowId");
		String knowName = req.getParameter("knowName");
		String orgCode = req.getParameter("orgCode");
		String orgId = req.getParameter("orgId");
		TSDepart depart = new TSDepart();
		KnwldgLibEntity knwldgLib = new KnwldgLibEntity();
		if (StringUtil.isNotEmpty(ruleProd.getId())) {
			ruleProd = ruleProdService.getEntity(RuleProdEntity.class, ruleProd.getId());
			depart = systemService.getEntity(TSDepart.class, ruleProd.getOrgId());
			knwldgLib = knwldgLibService.getEntity(KnwldgLibEntity.class, ruleProd.getKknwldgId());
			ruleProd.setOrgId(ruleProd.getOrgId());
			ruleProd.setKknwldgId(ruleProd.getKknwldgId());
		} else {
			ruleProd.setKknwldgId(knowId);
			depart = systemService.getEntity(TSDepart.class, orgId);
			knwldgLib.setName(knowName);
			ruleProd.setOrgId(orgId);
			ruleProd.setOrgCode(orgCode);
		}
		req.setAttribute("ruleProd", ruleProd);
		req.setAttribute("depart", depart);
		req.setAttribute("knwldgLib", knwldgLib);
		return new ModelAndView("com/ifre/brms/ruleProd");
	}

	/**
	 * 产品打包页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "makePck")
	public ModelAndView makePckg(RuleProdEntity ruleProd, HttpServletRequest req) {
		String prodId = ruleProd.getId();
		ruleProd = ruleProdService.getEntity(RuleProdEntity.class, ruleProd.getId());
		// 从数据字典里取出定义的本地存储路径
	/*	List<TSType> list = ResourceUtil.allTypes.get("localpath");
		String localPath = ResourceUtil.allTypes.get("localpath").get(0).getTypename();*/
		String localPath = "";
		List<TSTypegroup> groupList = ruleProdService.findHql("from TSTypegroup where typegroupcode = ?", "localpath");
		if(groupList.isEmpty()){
			localPath = System.getProperty("saas_brms");
		}else{
			localPath = groupList.get(0).getTSTypes().get(0).getTypecode();
		}
		//System.out.println(".............................................:" + localPath);
		String pomCode = GenMavenFileUtil.GenMavenXmlContent(ruleProd);
		System.out.println(".............................................:" + pomCode);
		ruleProd.setDescp(pomCode);
		ruleProd.setName(localPath);
		req.setAttribute("RuleProdEntity", ruleProd);
		return new ModelAndView("com/ifre/brms/makePckg");
	}
	
	@RequestMapping(params = "makePckOuter")
	public ModelAndView makePckOuter(RuleProdEntity ruleProd, HttpServletRequest req) {
		String prodId = ruleProd.getId();
		ruleProd = ruleProdService.getEntity(RuleProdEntity.class, ruleProd.getId());
		// 从数据字典里取出定义的本地存储路径
	/*	List<TSType> list = ResourceUtil.allTypes.get("localpath");
		String localPath = ResourceUtil.allTypes.get("localpath").get(0).getTypename();*/
		String localPath = "";
		List<TSTypegroup> groupList = ruleProdService.findHql("from TSTypegroup where typegroupcode = ?", "localpath");
		if(groupList.isEmpty()){
			localPath = System.getProperty("saas_brms");
		}else{
			localPath = groupList.get(0).getTSTypes().get(0).getTypecode();
		}
		//System.out.println(".............................................:" + localPath);
		String pomCode = GenMavenFileUtil.GenMavenXmlContent(ruleProd);
		System.out.println(".............................................:" + pomCode);
		ruleProd.setDescp(pomCode);
		ruleProd.setName(localPath);
		req.setAttribute("RuleProdEntity", ruleProd);
		return new ModelAndView("com/ifre/outer/outerMakePckg");
	}
	/**
	 * 发布接口查询页面跳转
	 * 要提取调用URL，产品ID，产品对应的模型变量列表
	 * @return
	 */
	@RequestMapping(params = "queryUrlVarID")
	public ModelAndView queryUrlVarID(RuleProdEntity ruleProd, HttpServletRequest req) {
		ruleProd = ruleProdService.getEntity(RuleProdEntity.class, ruleProd.getId());
		
		List<Map<String,String>> varTypegroupList = getDecisionVarList(ruleProd.getId());
		
		String tempType = ruleProd.getTempType();
		StringBuilder Url = new StringBuilder(req.getRequestURL().substring(0, req.getRequestURL().indexOf("/saas_brms")));
		req.setAttribute("ruleProd", ruleProd);
		req.setAttribute("varTypegroupList", varTypegroupList);
		
		if(tempType != null && tempType.equals(Constants.RULE_TEMP_TYPE)){
			Url.append(Constants.RULE_INTF_URL);
			req.setAttribute("Url", Url.toString());
			return new ModelAndView("com/ifre/brms/queryRuleUrlVarID");
		}else{
			Url.append(Constants.MODEL_INTF_URL);
			req.setAttribute("Url", Url.toString());
			return new ModelAndView("com/ifre/brms/queryModelUrlVarID");
		}
		
		
		
	}	
	
	@RequestMapping(params = "getDecisionVarList")
	@ResponseBody
	public List<Map<String,String>> getDecisionVarList(String id){
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
		int type = Integer.valueOf(typeStr);
		if(type != 71){
			Map<String,Integer> setValue = new HashMap<String,Integer>();
			for (int i = 0; i < body.length; i++) {
				setValue.put(body[i][varValueP],i);
			}
	
			//变量描述可能随意变化，以变量名称为主键
			for (Map.Entry<String, Integer> value : setValue.entrySet()) {
				Map<String,String> temp = new HashMap<String,String>();
				 temp.put("varTypegroupcode",body[value.getValue()][varValueP]);
				 temp.put("varTypegroupname",body[value.getValue()][varDescP]);
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
				   temp.put("varTypegroupcode",sb.toString());
				   temp.put("varTypegroupname",body[i][flag]);
				   list.add(temp);
				}
		}
	
		

	  return list;
		
	}	

	/**
	 * 生成pom文件
	 * 
	 * @return
	 */
	@RequestMapping(params = "makePom")
	@ResponseBody
	public AjaxJson makePom(RuleProdEntity ruleProd, HttpServletRequest req) {
		System.out.println("......................................:" + ruleProd.getName());
		AjaxJson j = new AjaxJson();
		message = "POM文件生成成功";
		RuleProdEntity entity = ruleProdService.getEntity(RuleProdEntity.class, ruleProd.getId());
		String filename = null;
		GenMavenFileUtil.GenMavenXmlFile(entity, ruleProd.getName(), ruleProd.getDescp());
		j.setMsg(message);
		j.setMsg(filename + " " + message);
		return j;
	}

	
	@RequestMapping(params = "makeJarOuter")
	@ResponseBody
	public AjaxJson makeJarOuter(RuleProdEntity ruleProd, HttpServletRequest req) throws FileNotFoundException, IOException {
		AjaxJson j = new AjaxJson();
		try{
			String result = null;
			String path = ruleProd.getName();
			RuleProdEntity entity = ruleProdService.getEntity(RuleProdEntity.class, ruleProd.getId());
			path = path + File.separator + entity.getName();
			File file = new File(path);
			List<RulePckgEntity> entryList = ruleProdService.findHql("from RulePckgEntity where prodId = ?", ruleProd.getId());
			String packaStr = null;
			String rootPath = System.getProperty("saas_brms");
		
			if(!entryList.isEmpty())
			{
				packaStr = entryList.get(0).getAllName().replace(".", File.separator);
			}
			File targetFile = new File(rootPath +File.separator +"target"+File.separator+packaStr);
			String claaesPath =  rootPath+"WEB-INF"+File.separator+"classes";
			try {
				result = hotCompilerService.makeFileForJar(claaesPath+File.separator, ruleProd.getId());
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
			}
			if(!targetFile.exists()){
				targetFile.mkdirs();
			}
			File jarsFile = new File(rootPath +File.separator +"jars");
			if(!jarsFile.exists()){
				jarsFile.mkdirs();
			}
			String utilPackage = "com"+File.separator+"ifre"+File.separator+"util"+File.separator+"encrypte";
			String utilPath =claaesPath+File.separator + utilPackage;
			String packagePath = rootPath +"target"+File.separator + utilPackage;
			File packageFile = new File(packagePath);
			if(!packageFile.exists())
				packageFile.mkdirs();
			FileUtils.copyFileToDirectory(new File(utilPath+File.separator+"ClassGuarder.class"), packageFile);
			FileUtils.copyFileToDirectory(new File(utilPath+File.separator+"IfreClassLoader.class"), packageFile);
			String[] args = new String[2];
			//args[0] = rootPath +File.separator +"target"+File.separator+packaStr+File.separator+"ScoreModelObj.class";
			args[0] = claaesPath + File.separator + packaStr + File.separator + "ScoreModelObj.class";
			args[1] = fileSeparator+"com"+fileSeparator+"ifre"+fileSeparator+"util"+fileSeparator+"encrypte"+fileSeparator+"IfreClassLoader.class";
			EncryptClassDirectory.main(args);
			String versionId = "";
			if(StringUtil.isEmpty(entity.getVersionId())){
				versionId = "1.0";
			}else{
				versionId = entity.getVersionId();
			}
			
		    IfreClassLoader loader = new IfreClassLoader(Thread.currentThread().getContextClassLoader());
	/*		Class<?> clazzScoreModelObj;
			//检测
			try {
				clazzScoreModelObj = loader.loadClass("com.ifre.decisiton.svpioda.ScoreModelObj");
				Object instanceScoreModelObj = clazzScoreModelObj.newInstance();
				instanceScoreModelObj = null;
				loader = null;
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}*/
			FileUtils.copyDirectory(new File(result), targetFile);
			// 生成jar包
			// GenMavenFileUtil.GenJar(path, path+"-"+entity.getVersionId()+".jar");
			String commandStr = "jar -cvf " + rootPath +File.separator +"jars" +File.separator+entity.getName() + "-" + versionId + ".jar" + " -C " + rootPath +"target" + " .";
			System.out.println("...................:" + commandStr);
			GenMavenFileUtil.exeCmd(commandStr);
			FileUtils.cleanDirectory(new File(claaesPath+File.separator+packaStr));
			FileUtils.cleanDirectory(new File(rootPath +File.separator +"target"));
			entity.setPackStatus(Constants.PACKAGE);
			ruleProdService.updateEntitie(entity);
			
			message = "jar包生成成功";
			j.setMsg(message);
			j.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			message = "jar包生成失败";
			j.setMsg(message);
			j.setSuccess(false);
		}
	
		return j;
	}
	

	@RequestMapping(params = "downJarOuter")
	@ResponseBody
	public AjaxJson downJarOuter(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String fileName = request.getParameter("fileName");
		String status = request.getParameter("status");
		String version = request.getParameter("version");
		if("undefined".equals(version)){
			version = "1.0";
		}
		String rootPath = System.getProperty("saas_brms");
		
		//List<TSTypegroup> groupList = ruleProdService.findHql("from TSTypegroup where typegroupcode = ?", "localpath");
		String localPath = rootPath + File.separator + "jars";
		/*if(groupList.isEmpty()){
			localPath = System.getProperty("saas_brms");
		}else{
			localPath = groupList.get(0).getTSTypes().get(0).getTypecode();
		}*/
		File file = new File(localPath + File.separator + fileName + "-" + version + ".jar");
		System.out.println(fileName);
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ ChineseToEnglish.getPingYin(fileName) + "\"" + version + ".jar");
		response.addHeader("Content-Length", "" + file.length());
		response.setContentType("application/octet-stream;charset=UTF-8");
		try {

			System.out.println(file.getAbsolutePath());
			InputStream inputStream = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}

			// 这里主要关闭。
			os.close();

			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 返回值要注意，要不然就出现下面这句错误！
		// java+getOutputStream() has already been called for this response
		return null;

	}
	
	/**
	 * 生成pom文件
	 * 
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@RequestMapping(params = "makeJar")
	@ResponseBody
	public AjaxJson makeJar(RuleProdEntity ruleProd, HttpServletRequest req) throws FileNotFoundException, IOException {

		String result = null;
		String path = ruleProd.getName();
		RuleProdEntity entity = ruleProdService.getEntity(RuleProdEntity.class, ruleProd.getId());
		path = path + File.separator + entity.getName();
		File file = new File(path);
		// 更新数据库状态
		try {
			result = hotCompilerService.makeFileForJar(path.concat(File.separator), ruleProd.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		String versionId = "";
		if(StringUtil.isEmpty(entity.getVersionId())){
			versionId = "1.0";
		}else{
			versionId = entity.getVersionId();
		}
		// 生成jar包
		// GenMavenFileUtil.GenJar(path, path+"-"+entity.getVersionId()+".jar");
		String commandStr = "jar -cvf " + path + "-" + versionId + ".jar" + " -C " + path + " .";
		System.out.println("...................:" + commandStr);
		GenMavenFileUtil.exeCmd(commandStr);
		entity.setPackStatus(Constants.PACKAGE);
		ruleProdService.updateEntitie(entity);
		AjaxJson j = new AjaxJson();
		message = "jar包生成成功";
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "downJar")
	@ResponseBody
	public AjaxJson download(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String fileName = request.getParameter("fileName");
		String status = request.getParameter("status");
		String version = request.getParameter("version");
		if("undefined".equals(version)){
			version = "1.0";
		}
		List<TSTypegroup> groupList = ruleProdService.findHql("from TSTypegroup where typegroupcode = ?", "localpath");
		String localPath = null;;
		if(groupList.isEmpty()){
			localPath = System.getProperty("saas_brms");
		}else{
			localPath = groupList.get(0).getTSTypes().get(0).getTypecode();
		}
		File file = new File(localPath + File.separator + fileName + "-" + version + ".jar");
		System.out.println(fileName);
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ ChineseToEnglish.getPingYin(fileName) + "\"" + version + ".jar");
		response.addHeader("Content-Length", "" + file.length());
		response.setContentType("application/octet-stream;charset=UTF-8");
		try {

			System.out.println(file.getAbsolutePath());
			InputStream inputStream = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}

			// 这里主要关闭。
			os.close();

			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 返回值要注意，要不然就出现下面这句错误！
		// java+getOutputStream() has already been called for this response
		return null;

	}

	// 查出包全名列表
	@RequestMapping(params = "getGroupList")
	@ResponseBody
	public AjaxJson getGroupIdList(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		List<RuleProdEntity> list = ruleProdService.getList(RuleProdEntity.class);
		HashSet<String> set = new HashSet<String>();
		for (int i = 0; i < list.size(); i++) {
			String groupid = list.get(i).getGroupId();
			if (!StringUtil.isEmpty(groupid))
				set.add(groupid);
		}
		j.setObj(set);
		return j;
	}

	/**
	 * 激活方案
	 * 
	 * 
	 * @author caojianmiao
	 */
	@RequestMapping(params = "activate")
	@ResponseBody
	public AjaxJson activate(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		String message = null;
		BrmsRuleProdEntity brmsRuleProd = systemService.getEntity(BrmsRuleProdEntity.class, id);

		String activateValue = req.getParameter("activatevalue");

		brmsRuleProd.setRightStatus(new Integer(activateValue));
		try {
			ruleProdService.updateEntitie(brmsRuleProd);
			if ("0".equals(activateValue)) {
				message = "方案：" + brmsRuleProd.getName() + "关闭成功!";
			} else if ("1".equals(activateValue)) {
				message = "方案：" + brmsRuleProd.getName() + "激活成功!";
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			message = "操作失败!";
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 激活方案
	 * 
	 * 
	 * @author caojianmiao
	 */
	@RequestMapping(params = "allactivate")
	@ResponseBody
	public AjaxJson allactivate(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		String message = null;

		String activateValue = req.getParameter("activatevalue");
		String departid = req.getParameter("departid");
		
		try {
			ruleProdService.updateBySqlString("update brms_rule_prod set right_status = '" + new Integer(activateValue) + "' where org_id='" + departid +"'");
			if ("0".equals(activateValue)) {
				message = "方案全部关闭成功!";
			} else if ("1".equals(activateValue)) {
				message = "方案全部激活成功!";
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			message = "操作失败!";
		}
		j.setMsg(message);
		return j;
	}
	
}
