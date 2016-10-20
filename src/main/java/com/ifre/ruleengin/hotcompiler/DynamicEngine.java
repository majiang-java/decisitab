package com.ifre.ruleengin.hotcompiler;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.log4j.Logger;

import com.ifre.util.PathUtils;
  
/** 
 * 动态重新加载Class <br> 
 * Java内置的ClassLoader总会在加载一个Class之前检查这个Class是否已经被加载过 <br> 
 * 已经被加载过的Class不会加载第二次 <br> 
 * 因此要想重新加载Class，我们需要实现自己的ClassLoader <br> 
 * 另外一个问题是，每个被加载的Class都需要被链接(link)， <br> 
 * 这是通过执行ClassLoader.resolve()来实现的，这个方法是 final的，无法重写。 <br> 
 * ClassLoader.resolve()方法不允许一个ClassLoader实例link一个Class两次， <br> 
 * 因此，当需要重新加载一个 Class的时候，需要重新New一个自己的ClassLoader实例。 <br> 
 * 一个Class不能被一个ClassLoader实例加载两次，但是可以被不同的ClassLoader实例加载， <br> 
 * 这会带来新的问题 <br> 
 * 在一个Java应用中，Class是根据它的全名（包名+类名）和加载它的 ClassLoader来唯一标识的， <br> 
 * 不同的ClassLoader载入的相同的类是不能互相转换的。 <br> 
 * 解决的办法是使用接口或者父类，只重新加载实现类或者子类即可。 <br> 
 * 在自己实现的ClassLoader中，当需要加载接口或者父类的时候，要代理给父ClassLoader去加载 <br> 
 *  
 * @author majiang
 * @version  2016-06-29  
 * @since jdk1.6.0 
 */  
public class DynamicEngine {  
  
    private static Logger logger = Logger.getLogger(DynamicEngine.class);  
    private static DynamicEngine instance = new DynamicEngine();  
    private URLClassLoader parentClassLoader;  
    private String classpath;  
  
    public static DynamicEngine getInstance() {  
        return instance;  
    }  
  
    private DynamicEngine() {  
        this.parentClassLoader = (URLClassLoader) getClass().getClassLoader();  
        buildClassPath();  
    }  
  
    private void buildClassPath() {  
        StringBuilder sb = new StringBuilder();  
        for (URL url : this.parentClassLoader.getURLs()) {  
            String p = url.getFile();  
            sb.append(p);  
            sb.append(File.pathSeparator);  
        }  
        this.classpath = sb.toString();  
    }  
  
    /** 
     * 编译Java代码（用来检查代码正确性） 
     *  
     * @param className 
     * @param javaCode 
     * @return 编译通过则为null，不通过返回错误日志 
     * @throws Exception 
     */  
    @SuppressWarnings({ "rawtypes", "unchecked" })  
    public String javaCodeCompile(List<Map<String,Object>> list,String path) throws Exception {  
        long start = System.currentTimeMillis();  
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();  
        
        DiagnosticCollector diagListener = new DiagnosticCollector(); 
        StandardJavaFileManager filemanager = compiler.getStandardFileManager(diagListener, null, null);
       // ObjectFileManager fileManager = new ObjectFileManager(filemanager);  
        List<StringFileObject> compileUnits = new ArrayList<StringFileObject>();
        for (Map<String,Object> entry : list) {
            compileUnits.add(new StringFileObject(String.valueOf(entry.get("name")), String.valueOf(entry.get("content"))));  
		}
        List<String> options = new ArrayList<String>(6);  
        options.add("-encoding");  
        options.add("UTF-8");  
        options.add("-classpath");  
        options.add(this.classpath);  
        options.add("-d");  
        options.add(path);  
        JavaCompiler.CompilationTask task = compiler.getTask(null, filemanager, diagListener, options, null, compileUnits);  
        boolean success = task.call().booleanValue();  
        if (success) {  
            long end = System.currentTimeMillis();  
            logger.info("编译成功，用时:" + (end - start) + "ms");  
        } else {  
            StringBuilder error = new StringBuilder();  
            for (Object diagnostic : diagListener.getDiagnostics()) {  
                compilePrint(" ", error, (Diagnostic) diagnostic);  
            }  
            logger.error("编译失败:\n" + error);  
            return error.toString();  
        }  
        return null;  
    }  
  
    /** 
     * 编译Java代码（用来生成可用java对象） 
     *  
     * @param className 
     * @param javaCode 
     * @return 编译通过返回相应对象，不通过则为null 
     */  
    @SuppressWarnings({ "rawtypes", "unchecked" })  
    public Class javaCodeToObject(Map<String,Object> entry) throws Exception {  
        Class result = null;  
        long start = System.currentTimeMillis();  
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();  
        DiagnosticCollector diagListener = new DiagnosticCollector();  
        StandardJavaFileManager filemanager = compiler.getStandardFileManager(diagListener, null, null);
        
        List<StringFileObject> compileUnits = new ArrayList<StringFileObject>();  
        compileUnits.add(new StringFileObject(String.valueOf(entry.get("name")), String.valueOf(entry.get("content"))));  
        List<String> options = new ArrayList<String>(6);  
        options.add("-encoding");  
        options.add("UTF-8");  
        options.add("-classpath");  
        options.add(this.classpath);  
        options.add("-d");  
        //options.add(PathUtils.getTargetPath());  
        options.add(PathUtils.getClassesPath());
        JavaCompiler.CompilationTask task = compiler.getTask(null, filemanager, diagListener, options, null, compileUnits);  
        boolean success = task.call().booleanValue();  
        if (success) {  
        	URLClassLoader classLoader = null;
        	try{
	        	URL url = new URL("file:"+PathUtils.getTargetPath());
			    classLoader = new URLClassLoader(new URL[]{url},Thread.currentThread().getContextClassLoader());
				Class<?> clazz = classLoader.loadClass(entry.get("pkgname")+"."+entry.get("name"));
				result = clazz;
	            long end = System.currentTimeMillis();  
	            logger.info("编译成功，用时:" + (end - start) + "ms"); 
        	}catch(Exception e){
        		
        	}finally{
        		  classLoader.close();
        	}
          
        } else {  
            StringBuilder error = new StringBuilder();  
            for (Object diagnostic : diagListener.getDiagnostics()) {  
                compilePrint(String.valueOf(entry.get("content")), error, (Diagnostic) diagnostic);  
            }  
            logger.error("编译失败:\n" + error);  
            
        }  
        
        return result;  
    }  
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })  
    public Map<String,Class<?>> javaCodeToMap(List<Map<String,Object>> list) throws Exception {  
        Map<String,Class<?>> result = new HashMap<String,Class<?>>();  
        long start = System.currentTimeMillis();  
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();  
        DiagnosticCollector diagListener = new DiagnosticCollector();  
        StandardJavaFileManager filemanager = compiler.getStandardFileManager(diagListener, null, null);
        
        List<StringFileObject> compileUnits = new ArrayList<StringFileObject>();  
        //compileUnits.add(new StringFileObject(String.valueOf(entry.get("name")), String.valueOf(entry.get("content"))));  
        for (Map<String,Object> entry : list) {
            compileUnits.add(new StringFileObject(String.valueOf(entry.get("name")), String.valueOf(entry.get("content"))));  
		}
        List<String> options = new ArrayList<String>(6);  
        options.add("-encoding");  
        options.add("UTF-8");  
        options.add("-classpath");  
        options.add(this.classpath);  
        options.add("-d");  
        //options.add(PathUtils.getTargetPath());  
        options.add(PathUtils.getClassesPath());
        JavaCompiler.CompilationTask task = compiler.getTask(null, filemanager, diagListener, options, null, compileUnits);  
        boolean success = task.call().booleanValue();  
        if (success) {  
        	URLClassLoader classLoader = null;
        	try{
	        	URL url = new URL("file:"+PathUtils.getTargetPath());
			    classLoader = new URLClassLoader(new URL[]{url},Thread.currentThread().getContextClassLoader());
				/*Class<?> clazz = classLoader.loadClass(entry.get("pkgname")+"."+entry.get("name"));
				result = clazz;*/
			    for (Map<String,Object> entry : list) {
			    	String name = String.valueOf(entry.get("name"));
			    	Class<?> clazz = classLoader.loadClass(entry.get("pkgname")+"."+name);
			    	result.put(name, clazz);
				}
	            long end = System.currentTimeMillis();  
	            logger.info("编译成功，用时:" + (end - start) + "ms"); 
        	}catch(Exception e){
        		throw new Exception("编译失败");
        	}finally{
        		  classLoader.close();
        	}
          
        } else {  
            StringBuilder error = new StringBuilder();  
            for (Object diagnostic : diagListener.getDiagnostics()) {  
                compilePrint(" ", error, (Diagnostic) diagnostic);  
            }  
           
            logger.error("编译失败:\n" + error);  
            throw new Exception("编译失败");
        }  
        
        return result;  
    }  
  
    /** 
     * 构造编译错误日志 
     *  
     * @param javaCode 
     * @param error 
     * @param diagnostic 
     */  
    @SuppressWarnings("rawtypes")  
    private void compilePrint(String javaCode, StringBuilder error, Diagnostic diagnostic) {  
        error.append(diagnostic.getMessage(null));  
        error.append('\n');  
        error.append(getLine(javaCode, (int) diagnostic.getLineNumber()));  
        error.append('\n');  
        error.append(rjust("^", (int) diagnostic.getColumnNumber()));  
        error.append('\n');  
    }  
  
    /** 
     * 取源数据的指定行 
     *  
     * @param source 源数据 
     * @param line 行号 
     * @return 确定的行 
     */  
    public String getLine(String source, int line) {  
        char[] chars = source.toCharArray();  
        int count = 1;  
        int n = chars.length;  
        int j = 0;  
        for (int i = 0; i < n;) {  
            // Find a line and append it  
            while (i < n && chars[i] != '\n' && chars[i] != '\r'  
                    && Character.getType(chars[i]) != Character.LINE_SEPARATOR) {  
                i++;  
            }  
            // Skip the line break reading CRLF as one line break  
            int eol = i;  
            if (i < n) {  
                if (chars[i] == '\r' && i + 1 < n && chars[i + 1] == '\n') {  
                    i += 2;  
                } else {  
                    i++;  
                }  
            }  
            if (count == line) {  
                return source.substring(j, eol);  
            } else {  
                count++;  
            }  
            j = i;  
        }  
        if (j < n) {  
            return source.substring(j, n);  
        }  
        return source;  
    }  
  
    /** 
     * 左对齐（右方用空格填充） 
     *  
     * @param src 
     * @param width 
     * @return 
     */  
    public String ljust(String src, int width) {  
        return expand(src, width, ' ', true);  
    }  
  
    /** 
     * 右对齐（左方用空格填充） 
     *  
     * @param src 
     * @param width 
     * @return 
     */  
    public String rjust(String src, int width) {  
        return expand(src, width, ' ', false);  
    }  
  
    private String expand(String src, int width, char fillchar, boolean postfix) {  
        String result = src;  
        if (result.length() < width) {  
            char[] temp = new char[width - result.length()];  
            for (int i = 0; i < temp.length; i++) {  
                temp[i] = fillchar;  
            }  
            if (postfix) {  
                result = result + new String(temp);  
            } else {  
                result = new String(temp) + result;  
            }  
        }  
        return result;  
    }  
}  
