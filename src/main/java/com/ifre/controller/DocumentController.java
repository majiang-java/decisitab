package com.ifre.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/documentController")
public class DocumentController {


		@RequestMapping(params="upload")
		public String upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
			// 这里我用到了jar包
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					MultipartFile file = multiRequest.getFile((String) iter.next());
					if (file != null) {
						String fileName = file.getOriginalFilename();

						String path1 = Thread.currentThread().getContextClassLoader().getResource("").getPath()
								+ "download" + File.separator;

						// 下面的加的日期是为了防止上传的名字一样
						String path = path1 + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + fileName;

						File localFile = new File(path);

						file.transferTo(localFile);
					}

				}

			}
			return "uploadSuccess";

		}

		@RequestMapping(params="showUpload")
		public ModelAndView showUpload() {
			
			return new ModelAndView("com/ifre/brms/upload");
		}

		@RequestMapping("/download")
		public String download(String fileName, HttpServletRequest request, HttpServletResponse response) {
			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
			try {
				String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "download";// 这个download目录为啥建立在classes下的
				InputStream inputStream = new FileInputStream(new File(path + File.separator + fileName));

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
	}
