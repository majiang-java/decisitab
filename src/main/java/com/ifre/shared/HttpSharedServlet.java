package com.ifre.shared;


import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ifre.shared.brmsShared.BrmsSharedI;
import com.ifre.util.Tools;

/**
 * 共享服务http方式
 */
public class HttpSharedServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(HttpSharedServlet.class);
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		
		//Class<?> clazz = null;
		
		try {
			String classpath = "";	
			String result = "";
			req.setCharacterEncoding("utf-8");
			String requestURI = req.getRequestURI();
			ServletInputStream in = req.getInputStream();
			String jsonData = Tools.inputStream2String(in);
			if(null == jsonData || "".equals(jsonData)){
				jsonData = req.getParameter("jsonData");
			}
			log.info("接收到的参数:"+jsonData);
			String method = "";
			if(requestURI.contains("brmsShared/api")){
				classpath = "com.ifre.shared.brmsShared.impl.BrmsSharedImpl";
			}else if(requestURI.contains("absShared/api")){
//				classpath = "com.ifre.shared.absShared.impl.AbsSharedImpl";		
			}
			method = classpath+"."+requestURI.substring(requestURI.lastIndexOf("/")+1,requestURI.length());
			
			ClassReflect reflect = new ClassReflect();
			try {
				result = (String) reflect.invokAction(method,jsonData);
			}catch (Exception e){
				e.printStackTrace();
			}
			//返回
			writeString(resp, result);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 直接写字符串
	 * @param response
	 * @param msg
	 */
	private void writeString(HttpServletResponse response, String msg) {
		try {
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class ClassReflect{
		@SuppressWarnings({ "rawtypes", "unchecked"})
		public Object runMethod(String param,String jsonData) throws Exception{
			String[] str = param.split("\\.");
			Class cla =  Class.forName( param.substring(0, param.indexOf(str[str.length-1])-1));
			Method method = cla.getMethod(str[str.length-1],String.class);
			BrmsSharedI brmsSharedI = (BrmsSharedI)cla.newInstance();
			Object result = method.invoke(brmsSharedI,jsonData);
			return result;
		}
		
		public Object invokAction(String param,String jsonData) throws Exception{
			return  new ClassReflect().runMethod(param,jsonData);
		}

	}
}