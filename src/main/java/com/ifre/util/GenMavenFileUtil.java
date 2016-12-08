package com.ifre.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import org.apache.commons.io.FileUtils;
import org.jeecgframework.codegenerate.util.CodeResourceUtil;

import com.ifre.entity.brms.RuleProdEntity;

public class GenMavenFileUtil {

	private static String mavenProp = "pom.properties";
	private static String mavenXml ="pom.xml";
    public static String getProjectPath(){
        String path = (new StringBuilder(String.valueOf(System.getProperty("user.dir").replace("\\", "/")))).append("/").toString();
        return path;
    }
    
    public synchronized static String getMavenFilePath(RuleProdEntity entity){
    	String path = new StringBuilder(getProjectPath()).append(CodeResourceUtil.source_root_package).append("/META-INF/maven/")
    			.append(entity.getGroupId()).append("/").append(entity.getArtifactId()).append("/").toString();
        try{
        	File file = new File(path);
        	if (file.exists()== false || file.isDirectory()==false){
        		FileUtils.forceMkdir(file);
        	}
        }catch(Exception e){
			e.printStackTrace();
        }
    	return path;
    }
    public synchronized static String getMavenFilePath(String filepath, RuleProdEntity entity){
    	String path = new StringBuilder(filepath).append("/").append(entity.getName()).append("/META-INF/maven/")
    			.append(entity.getGroupId()).append("/").append(entity.getArtifactId()).append("/").toString();
    	System.out.println("...........................path:"+path);
        try{
        	File file = new File(path);
        	if (file.exists()== false || file.isDirectory()==false){
        		FileUtils.forceMkdir(file);
        	}
        }catch(Exception e){
			e.printStackTrace();
        }
    	return path;
    }

    public static void GenMavenPropFile(RuleProdEntity entity){
		String filename = getMavenFilePath(entity) + mavenProp;
		Properties props = new Properties();
		props.setProperty("groupId", entity.getGroupId());
		props.setProperty("artifactId", entity.getArtifactId());
		props.setProperty("version", entity.getVersionId());
		FileOutputStream fos = null;
		try{
			fos = new FileOutputStream(filename);
			props.store(fos, "pom.xml");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(fos!=null) fos.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
    
	public static String GenMavenXmlContent(RuleProdEntity entity){
		StringBuffer sbuffer = new StringBuffer();
		try{
			sbuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
			sbuffer.append("<project xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://maven.apache.org/POM/4.0.0\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\r\n");
			sbuffer.append("<modelVersion>4.0.0</modelVersion>\r\n");
			sbuffer.append("<groupId>");
			sbuffer.append(entity.getGroupId());
			sbuffer.append("</groupId>\r\n");
			sbuffer.append("<artifactId>");
			sbuffer.append(entity.getArtifactId());
			sbuffer.append("</artifactId>\r\n");
			sbuffer.append("<version>");
			sbuffer.append(entity.getVersionId());
			sbuffer.append("</version>\r\n");
			sbuffer.append("<name>");
			sbuffer.append(entity.getName());
			sbuffer.append("</name>\r\n");
			sbuffer.append("<build>\r\n");
			sbuffer.append("<plugins>\r\n");
			sbuffer.append("<plugin>\r\n");
			sbuffer.append("<groupId>org.kie</groupId>\r\n");
			sbuffer.append("<artifactId>kie-maven-plugin</artifactId>\r\n");
			sbuffer.append("<version>6.2.0.Final</version>\r\n");
			sbuffer.append("<extensions>true</extensions>\r\n");
			sbuffer.append("</plugin>\r\n");
			sbuffer.append("</plugins>\r\n");
			sbuffer.append("</build>\r\n");
			sbuffer.append("</project>\r\n");
		}catch(Exception e){
			e.printStackTrace();
		}
		return sbuffer.toString();
	} 
	
	public static void GenMavenXmlFile(RuleProdEntity entity){
		String filename = getMavenFilePath(entity) + mavenXml;
		FileOutputStream fos = null;
        Writer out = null;
        
		try{
			fos = new FileOutputStream(filename);
			out = new OutputStreamWriter(fos, CodeResourceUtil.SYSTEM_ENCODING);
			String content = GenMavenXmlContent(entity);
			out.write(content);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(out!=null) out.close();
				if(fos!=null) fos.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static String GenMavenXmlFile(RuleProdEntity entity, String path, String content){
		
		String path1=path+File.separator+entity.getName();
		File file=new File(path1);
		if(file.isDirectory())
	        GenMavenFileUtil.deleteDir(file);
		String filename = getMavenFilePath(path ,entity) + mavenXml;
		FileOutputStream fos = null;
        Writer out = null;
        
		try{
			fos = new FileOutputStream(filename);
			out = new OutputStreamWriter(fos, "utf-8");
			out.write(content);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(out!=null) out.close();
				if(fos!=null) fos.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return filename;
	}
	
	
	public static String GenJar(String javaClassPath,String targetPath) throws FileNotFoundException, IOException{
		
//		String currentDir = "e:/myProject";    
//	    String javaClassPath = currentDir + "/classes";  
//	    String targetPath = currentDir + "/target/MyProject.jar";  	    
		 System.out.println("*** --> 开始生成jar包..."+targetPath); 
		 	int index =targetPath.lastIndexOf(File.separator);
		 	if(index <=0) index =targetPath.length();
		    String targetDirPath = targetPath.substring(0, index); 
		    System.out.println(targetDirPath);
		    File targetDir = new File(targetDirPath);  
		    if (!targetDir.exists()) {
		    	
		        targetDir.mkdirs();  
		    }  
//		  
//		    Manifest manifest = new Manifest();  
//		    manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");  
		    JarOutputStream target = new JarOutputStream(new FileOutputStream(targetPath));  
		    writeClassFile(new File(javaClassPath), target,javaClassPath);  
		    target.close();  
		    System.out.println("*** --> jar包生成完毕。");
			return targetPath;  
	}
	
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		GenJar("E:\\path\\Pro1","E:\\path\\Pro1-01.jar");
		
		String commandStr = "jar -cvf loan.jar -C E:/Servers/modeljars .";
		//String commandStr = "ipconfig";
		GenMavenFileUtil.exeCmd(commandStr);
		
		
	}

	
	private static void writeClassFile(File source, JarOutputStream target,String javaClassPath) throws IOException {  
	    BufferedInputStream in = null;  
	    try {  
	        if (source.isDirectory()) {  
	            String name = source.getPath().replace("\\", File.separator);
	            if (!name.isEmpty()) {  
	                if (!name.endsWith(File.separator)) {  
	                    name += File.separator;  
	                }
	                name = name.substring(javaClassPath.length());
	                if(name.startsWith(File.separator)){
	                    name=name.substring(1);
	                }
	                if(name.length()!=0){
	                JarEntry entry = new JarEntry(name);  
	                entry.setTime(source.lastModified());                
	                target.putNextEntry(entry);  
	                target.closeEntry();  
	               }
	            }  
	            for (File nestedFile : source.listFiles())  
	                writeClassFile(nestedFile, target,javaClassPath);  
	            return;  
	        }  
	  
	        String middleName = source.getPath().replace("\\", File.separator).substring(javaClassPath.length());       
	        if(middleName.startsWith(File.separator)){
	        	middleName=middleName.substring(1);
            }
	        JarEntry entry = new JarEntry(middleName); 
	        entry.setTime(source.lastModified());  
	        target.putNextEntry(entry);  
	        in = new BufferedInputStream(new FileInputStream(source));  
	  
	        byte[] buffer = new byte[1024];  
	        while (true) {  
	            int count = in.read(buffer);  
	            if (count == -1)  
	                break;  
	            target.write(buffer, 0, count);  
	        }  
	        target.closeEntry();  
	    } finally {  
	        if (in != null)  
	            in.close();  
	    }  
	}  
	
	/*删除目录*/
	public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
	
	
	
	
	
	
	public static void exeCmd(String commandStr) {
		BufferedReader br = null;
		try {
			Process p = Runtime.getRuntime().exec(commandStr);
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			System.out.println(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		finally
		{
			if (br != null)
			{
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	

	
	
	
	
	
}
