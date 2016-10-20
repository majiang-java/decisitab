package com.ifre.ruleengin.hotcompiler;

import java.net.URL;
import java.net.URLClassLoader;  
  
/** 
 * 自定义ClassLoader<br> 
 * 定义一个ClassLoader，载入一个动态Class 
 *  
 * @author majiang
 * @version  2016-06-29  
 * @since jdk1.6.0 
 */  
public class DynamicClassLoader extends URLClassLoader {  
    public DynamicClassLoader(URL[] urls,ClassLoader parent) {  
        super(urls, parent);  
    }  
  
    public Class<?> findClassByClassName(String className) throws ClassNotFoundException {  
        return findClass(className);  
    }  
  
    public Class<?> loadClass(String className, ByteFileObject byteFileObject) {  
        byte[] classData = byteFileObject.getBytes();  
        return defineClass(className, classData, 0, classData.length);  
    }  
    
    
}  