package com.ifre.controller.brms;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDataRule;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ifre.common.Constants;
import com.ifre.entity.brms.KnwldgLibEntity;
import com.ifre.entity.brms.RuleProdEntity;
import com.ifre.service.brms.HotComplierServiceI;
import com.ifre.service.brms.KnwldgLibServiceI;
import com.ifre.service.brms.RuleProdServiceI;
import com.ifre.util.GenMavenFileUtil;

/**   
 * @Title: Controller
 * @Description: 决策产品
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

	@Autowired
	private RuleProdServiceI ruleProdService;
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private HotComplierServiceI hotCompilerService;
	
	@Autowired
	private KnwldgLibServiceI knwldgLibService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 决策产品列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/ifre/brms/ruleProdList");
	}
	
	/**
	 * 决策产品列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "jarlist")
	public ModelAndView jarList(HttpServletRequest request) {
		return new ModelAndView("com/ifre/brms/ruleJarList");
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
	public void datagrid(RuleProdEntity ruleProd,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String sql=" ";
		if(StringUtil.isNotEmpty(ruleProd.getOrgId())){
			sql += "and pr.orgId='"+ruleProd.getOrgId()+"'";
		}
		if(StringUtil.isNotEmpty(ruleProd.getKknwldgId())){
			sql += "and pr.kknwldgId='"+ruleProd.getKknwldgId()+"'";
		}
		if(StringUtil.isNotEmpty(ruleProd.getName())){
			sql += "and pr.name like '%"+ruleProd.getName()+"%'";
		}
		CriteriaQuery cq = new CriteriaQuery(RuleProdEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, ruleProd, request.getParameterMap());
		this.ruleProdService.getDataGridAReturn(cq, true,sql);
//		this.ruleProdService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	public void createMavenFile(){
		try{
			String entityId ="4028aac454c7b3890154c7b84b7c0001";
			List<RuleProdEntity> bizList = ruleProdService.findHql("from RuleProdEntity where Id= '"+ entityId +"'");
			RuleProdEntity entity = bizList.get(0);
			GenMavenFileUtil.GenMavenPropFile(entity);
			GenMavenFileUtil.GenMavenXmlFile(entity);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 删除决策产品
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(RuleProdEntity ruleProd, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		ruleProd = systemService.getEntity(RuleProdEntity.class, ruleProd.getId());
		String sql="select count(*) from brms_rule_pckg where PROD_ID='"+ruleProd.getId()+"'";
	    Long a=systemService.getCountForJdbc(sql);
	    if(a>0){
	    	message = "决策包有用到此条决策产品，拒绝删除";	
	    }else{
		message = "决策产品删除成功";
		ruleProdService.delete(ruleProd);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
	    }
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加决策产品
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(RuleProdEntity ruleProd, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(ruleProd.getId())) {
			message = "决策产品更新成功";
			RuleProdEntity t = ruleProdService.get(RuleProdEntity.class, ruleProd.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(ruleProd, t);
				ruleProdService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "决策产品更新失败";
			}
		} else {
			message = "决策产品添加成功";
			ruleProd.setStatus(Constants.START);
			ruleProdService.save(ruleProd);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 决策产品列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(RuleProdEntity ruleProd, HttpServletRequest req) {
		String knowId=req.getParameter("knowId");
		String knowName=req.getParameter("knowName");
		String orgCode=req.getParameter("orgCode");
		String orgId=req.getParameter("orgId");
		if (StringUtil.isNotEmpty(ruleProd.getId())) {
			ruleProd = ruleProdService.getEntity(RuleProdEntity.class, ruleProd.getId());
			TSDepart depart = systemService.getEntity(TSDepart.class, ruleProd.getOrgId());
			KnwldgLibEntity t = knwldgLibService.getEntity(KnwldgLibEntity.class, ruleProd.getKknwldgId());
			String orgName=depart.getDepartname();
			ruleProd.setOrgId(ruleProd.getOrgId()+","+orgName);	
			String kName=t.getName();
			ruleProd.setKknwldgId(ruleProd.getKknwldgId()+","+kName);			
		}else{
			ruleProd.setKknwldgId(knowId+","+knowName);
			TSDepart depart = systemService.getEntity(TSDepart.class, orgId);
			String orgName=depart.getDepartname();
			ruleProd.setOrgId(orgId+","+orgName);
			ruleProd.setOrgCode(orgCode);
//			ruleProd.setOrgId(knowOrgCode);
			
		}
		req.setAttribute("ruleProdPage", ruleProd);
		return new ModelAndView("com/ifre/brms/ruleProd");
	}
	
	
	/**
	 * 产品打包页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "makePck")
	public ModelAndView makePckg(RuleProdEntity ruleProd, HttpServletRequest req) {
		String prodId=ruleProd.getId(); 
		ruleProd = ruleProdService.getEntity(RuleProdEntity.class, ruleProd.getId());
		//从数据字典里取出定义的本地存储路径
		List<TSType> list=ResourceUtil.allTypes.get("localpath");
		String localPath=ResourceUtil.allTypes.get("localpath").get(0).getTypename();
		System.out.println(".............................................:"+localPath);
		String pomCode=GenMavenFileUtil.GenMavenXmlContent(ruleProd);
		System.out.println(".............................................:"+pomCode);
		ruleProd.setDescp(pomCode);
		ruleProd.setName(localPath);
		req.setAttribute("RuleProdEntity",ruleProd );
		return new ModelAndView("com/ifre/brms/makePckg");
	}
	
	/**
	 * 生成pom文件
	 * 
	 * @return
	 */
	@RequestMapping(params = "makePom")
	@ResponseBody
	public AjaxJson makePom(RuleProdEntity ruleProd, HttpServletRequest req) {
		System.out.println("......................................:"+ruleProd.getName());
		AjaxJson j = new AjaxJson();
        message = "POM文件生成成功";
        RuleProdEntity entity=ruleProdService.getEntity(RuleProdEntity.class, ruleProd.getId());
        String filename=null;
        		GenMavenFileUtil.GenMavenXmlFile(entity, ruleProd.getName(), ruleProd.getDescp());
		j.setMsg(message);
		j.setMsg(filename+" "+message);
		return j;
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
		
		String result;
		String path=ruleProd.getName();
		RuleProdEntity entity=ruleProdService.getEntity(RuleProdEntity.class, ruleProd.getId());
		path=path+File.separator+entity.getName();
		File file=new File(path);
		//更新数据库状态
		try {
			result=hotCompilerService.makeFileForJar(path.concat(File.separator), ruleProd.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		//生成jar包
		//GenMavenFileUtil.GenJar(path, path+"-"+entity.getVersionId()+".jar");	
		String commandStr = "jar -cvf "+path+"-"+entity.getVersionId()+".jar"+" -C "+path+" .";
		System.out.println("...................:"+commandStr);
		GenMavenFileUtil.exeCmd(commandStr);
		entity.setStatus("1");
		ruleProdService.updateEntitie(entity);
		AjaxJson j = new AjaxJson();
        message = "jar包生成成功";
		j.setMsg(message);		
		return j;
	}
	
	
	
	@RequestMapping(params ="downJar")
	@ResponseBody
    public AjaxJson download(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		 request.setCharacterEncoding("UTF-8");  
		String fileName=request.getParameter("fileName");
		String status=request.getParameter("status");
		String version=request.getParameter("version");
        List<TSType> list=ResourceUtil.allTypes.get("localpath");
		String localPath=ResourceUtil.allTypes.get("localpath").get(0).getTypename();
		 File file=new File(localPath+ File.separator + fileName+"-"+version+".jar");
			System.out.println(fileName);
			 response.reset();   
		     response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("gbk"),"iso-8859-1") + "\""+version+".jar");    
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
	            //  返回值要注意，要不然就出现下面这句错误！
	            //java+getOutputStream() has already been called for this response
	        return null;
		
		
    }
	
	//查出包全名列表
			@RequestMapping(params = "getGroupList")
			@ResponseBody
			public AjaxJson getGroupIdList(HttpServletRequest request) {
				AjaxJson j = new AjaxJson();
					List<RuleProdEntity> list=ruleProdService.getList(RuleProdEntity.class);
					HashSet<String> set=new HashSet<String>();
					for(int i=0;i<list.size();i++){
						String groupid=list.get(i).getGroupId();
						if(!StringUtil.isEmpty(groupid))					
							set.add(groupid);					
					}
		            j.setObj(set);
				    return j;
			}			
}


	