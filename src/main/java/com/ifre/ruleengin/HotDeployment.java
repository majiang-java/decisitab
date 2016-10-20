/**
 * @author majiang
 */
package com.ifre.ruleengin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager.Location;
import javax.tools.JavaFileObject.Kind;



import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ifre.util.PathUtils;

@SuppressWarnings("unused")
public class HotDeployment {
	
	public static Logger log = Logger.getLogger(HotDeployment.class);
	   /**

	    
		/**
		 * 热编译，热加载方法 
		 * @param className  方法名称
		 * @param sourse  数据源
		 * @return  map  返回热加载类的对象
		 * @throws Exception
		 */
		public static final Class<?> hotComplierEngine(Map<String,Object> entry) throws Exception{
			return hotComplierEngine(entry,null);
		}
		
		
		public static final Class<?> hotComplierEngine(Map<String,Object> entry,String srcPath) throws Exception{
			Class<?> reclazz = null;
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager fileManager = compiler.getStandardFileManager(
					null, null, null);
			Location location = StandardLocation.CLASS_OUTPUT;
			try{
				if(StringUtils.isEmpty(srcPath)){
					//srcPath = getClassesPath();
					srcPath = PathUtils.getTargetPath();
				}
				File path = new File(srcPath);
				if(!path.exists()){
					path.mkdir();
				}
				File[] outputs = new File[] { path };
				try {
					fileManager.setLocation(location, Arrays.asList(outputs));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				StringSourceJavaObject sourceObject = new StringSourceJavaObject(
						String.valueOf(entry.get("name")), String.valueOf(entry.get("content")));
				Iterable<? extends SimpleJavaFileObject> fileObjects = Arrays
						.asList(sourceObject);
				 ;
				String root = System.getProperty("brms");
				String classpaths = root+"WEB-INF\\lib\\";
				StringBuffer sbb = new StringBuffer();
				File files = new File(classpaths);
				String[] filess = files.list();
				//遍历要导入引入的包
				for (int i = 0; i < filess.length; i++) {
					sbb.append(classpaths+filess[i]+";");
				}
				System.out.println(sbb);
				String sourcepath = getClassesPath();
				Iterable<String> options = Arrays.asList("-sourcepath", sourcepath,"-classpath",sbb.toString());
				CompilationTask task = compiler.getTask(null, fileManager, null, options,
						null, fileObjects);
				boolean result = task.call();
			
				if (result) {
					log.info("编译成功！");
					URLClassLoader classLoader = null;
					try {
					//	Class clazz = HotClassComplierRuleCreator.class.getClassLoader().loadClass("com.ruleEngine.dao.helper.GenRule");
						URL url = new URL("file:"+srcPath);
						classLoader = new URLClassLoader(new URL[]{url},Thread.currentThread().getContextClassLoader());
						Class<?> clazz = classLoader.loadClass(entry.get("pkgname")+"."+entry.get("name"));
						reclazz = clazz;
					} catch (ClassNotFoundException e) {
						throw new Exception(e);
					} catch (SecurityException e) {
						throw new Exception(e);
					} catch (IllegalArgumentException e) {
						throw new Exception(e);
					}finally{
						//classLoader.close();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				fileManager.close();
			}
			return reclazz;
		}
		
		  
	    private String buildClassPath() {  
	    	URLClassLoader parentClassLoader = (URLClassLoader) getClass().getClassLoader();  
	        StringBuilder sb = new StringBuilder();  
	        for (URL url : parentClassLoader.getURLs()) {  
	            String p = url.getFile();  
	            sb.append(p);  
	            sb.append(File.pathSeparator);  
	        }  
	        return sb.toString();  
	    }  
		
		/**
		 * 删除临时文件夹
		 */
		public static void deletePath(){
			File f = new File(getClassesPath()+"/com/ruleEngine/dao/dynamic/");
			if(f.isDirectory()){
				File[] files = f.listFiles();
			    for (File file : files) {
			    	file.delete();
				}
			}
			f.delete();
		}
		static class StringSourceJavaObject extends SimpleJavaFileObject {
			private String code;
			protected StringSourceJavaObject(String name, String content) {
				
				super(URI.create("String:///" + name.replace(".", "/")
						+ Kind.SOURCE.extension), Kind.SOURCE);
				this.code = content;
			}
			@Override
			public CharSequence getCharContent(boolean paramBoolean)
			throws IOException {
				return code;
			}
		}
		
		
		/**
		 * 获取项目的根路径
		 * @return
		 */
	    public static String getClassesPath(){
	        String path = "";
	        path = HotDeployment.class.getResource("/").toString();
	        if(path.startsWith("file")){
	            // 当class文件在class文件中时，返回"file:/F:/ ..."样的路径
	            path = path.substring(6);
	        }else if(path.startsWith("jar")){
	            // 当class文件在jar文件中时，返回"jar:file:/F:/ ..."样的路径
	            path = path.substring(10);
	        }
	        if(path.endsWith("/") || path.endsWith("\\")){
	            //使返回的路径不包含最后的"/"
	            path = path.substring(0, path.length()-1);
	        }
	        return path;
	    }
	    
}
