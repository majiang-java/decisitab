package com.ifre.util;

import java.io.File;

import com.ifre.ruleengin.HotDeployment;

public class PathUtils {
	
	public static String getTargetPath(){
		
		String root = System.getProperty("brms");
		return root + "/" + "target/";
		
	}
	
	/**
	 * 获取项目的根路径
	 * @return
	 */
    public static String getClassesPath(){
        String path = "";
        path = PathUtils.class.getResource("/").toString();
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
        return File.separator+path+File.separator;
    }
	

}
