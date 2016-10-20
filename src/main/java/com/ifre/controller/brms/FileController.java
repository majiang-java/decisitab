package com.ifre.controller.brms;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.web.demo.controller.test.FileUploadController;
import org.jeecgframework.web.demo.entity.test.FileMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ifre.common.Constants;
import com.ifre.entity.brms.RuleProdEntity;
import com.ifre.service.brms.ProcessExcelServicel;
import com.ifre.service.brms.TemplateMangerServiceI;

@Scope("prototype")
@Controller
@RequestMapping("/fileController")
public class FileController extends BaseController {

	@Autowired
	private ProcessExcelServicel processExcelService;
	
	@Autowired
	private TemplateMangerServiceI templateMangerService;

	private static final Logger logger = Logger.getLogger(FileUploadController.class);

	private String message;
	private static LinkedList<FileMeta> files = new LinkedList<FileMeta>();
	

	private FileMeta fileMeta = null;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@RequestMapping(params = "uploadview")
	public ModelAndView uploadview(HttpServletRequest request) {
		//processExcelService.makeExcel("8a8080b054e24ba50154e24c7e700001");
		//processExcelService.makeExcel("8a8080b054f1782f0154f179ddf90001");
		return new ModelAndView("com/ifre/brms/upload");
	}
	
	@RequestMapping(params = "uploadTemplateview")
	public ModelAndView uploadTemplateview(HttpServletRequest request) {
		System.out.println(123);
		return new ModelAndView("com/ifre/template/templateUpload");
	}

	@RequestMapping(params = "upload", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson upload(MultipartHttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try{
			String departId = request.getParameter("departId");
			String knowid = request.getParameter("knowId");
			String productId = request.getParameter("prodId");
			logger.info("upload-》1. build an iterator");
			Iterator<String> itr = request.getFileNames();
			MultipartFile mpf = null;
			while (itr.hasNext()) {
				logger.info("upload-》2.1 get next MultipartFile");
				mpf = request.getFile(itr.next());
				logger.info(mpf.getOriginalFilename() + " uploaded! " + files.size());
				System.out.println(mpf.getOriginalFilename() + " uploaded! " + files.size());
				logger.info("2.2 if files > 10 remove the first from the list");
				if (files.size() >= 10)
					files.pop();
				logger.info("2.3 create new fileMeta");
				fileMeta = new FileMeta();
				fileMeta.setFileName(mpf.getOriginalFilename());
				fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
				fileMeta.setFileType(mpf.getContentType());
				fileMeta.setBytes(mpf.getBytes());
				String path = "upload/";
				String realPath = request.getSession().getServletContext().getRealPath("/") + path;// 文件的硬盘真实路径
				logger.info("upload-》文件的硬盘真实路径" + realPath);
				String savePath = realPath + mpf.getOriginalFilename();// 文件保存全路径
				logger.info("upload-》文件保存全路径" + savePath);
				FileCopyUtils.copy(mpf.getBytes(), new File(savePath));
				logger.info("copy file to local disk (make sure the path e.g. D:/temp/files exists)");
				
				processExcelService.process(new File(savePath),departId, knowid,productId);
				RuleProdEntity prodEntry = processExcelService.getEntity(RuleProdEntity.class, productId);
				prodEntry.setStatus(Constants.START);
				processExcelService.updateEntitie(prodEntry);
				j.setMsg("上传决策表成功");
				j.setSuccess(true);
				logger.info("2.4 add to files");
				files.add(fileMeta);
				logger.info("success uploaded=" + files.size());
			}
	
		}catch(Exception e){
			e.printStackTrace();
			j.setMsg(e.getMessage());
			j.setSuccess(false);
			e.printStackTrace();
		}
		// result will be like this
		// [{"fileName":"app_engine-85x77.png","fileSize":"8
		// Kb","fileType":"image/png"},...]
		
		return j;
	}

	@RequestMapping(params = "view", method = { RequestMethod.GET, RequestMethod.POST })
	public void get(HttpServletResponse response, String index) {
		logger.info("get =》uploaded=" + files.size());
		FileMeta getFile = files.get(Integer.parseInt(index));
		try {
			response.setContentType(getFile.getFileType());
			String fileName = StringUtils.trim(getFile.getFileName());
			logger.info("fileUploadController->get—>下载文件名：" + fileName);
			String formatFileName = FileUtils.encodingFileName(fileName);
			logger.info("fileUploadController->get—>处理中文乱码及文件名有空格：" + fileName);
			response.setHeader("Content-disposition", "attachment; filename=\"" + formatFileName + "\"");
			FileCopyUtils.copy(getFile.getBytes(), response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(params = "uploadTemplate", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson uploadTemplate(MultipartHttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try{
			String typeCode = request.getParameter("typeid");
			String typeid = templateMangerService.findIdByCode(typeCode);
			logger.info("upload-》1. build an iterator");
			Iterator<String> itr = request.getFileNames();
			MultipartFile mpf = null;
			while (itr.hasNext()) {
				logger.info("upload-》2.1 get next MultipartFile");
				mpf = request.getFile(itr.next());
				logger.info(mpf.getOriginalFilename() + " uploaded! " + files.size());
				System.out.println(mpf.getOriginalFilename() + " uploaded! " + files.size());
				logger.info("2.2 if files > 10 remove the first from the list");
				if (files.size() >= 10)
					files.pop();
				logger.info("2.3 create new fileMeta");
				fileMeta = new FileMeta();
				fileMeta.setFileName(mpf.getOriginalFilename());
				fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
				fileMeta.setFileType(mpf.getContentType());
				fileMeta.setBytes(mpf.getBytes());
				String path = "upload/";
				String realPath = request.getSession().getServletContext().getRealPath("/") + path;// 文件的硬盘真实路径
				logger.info("upload-》文件的硬盘真实路径" + realPath);
				String savePath = realPath + mpf.getOriginalFilename();// 文件保存全路径
				logger.info("upload-》文件保存全路径" + savePath);
				FileCopyUtils.copy(mpf.getBytes(), new File(savePath));
				logger.info("copy file to local disk (make sure the path e.g. D:/temp/files exists)");
				
				templateMangerService.process(new File(savePath),typeid);
				j.setMsg("上传决策表成功");
				j.setSuccess(true);
				logger.info("2.4 add to files");
				files.add(fileMeta);
				logger.info("success uploaded=" + files.size());
			}
	
		}catch(Exception e){
			e.printStackTrace();
			j.setMsg(e.getMessage());
			j.setSuccess(false);
			e.printStackTrace();
		}
		return j;
	}

}
