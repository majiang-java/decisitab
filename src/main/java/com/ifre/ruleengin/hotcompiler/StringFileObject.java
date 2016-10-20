package com.ifre.ruleengin.hotcompiler;
import java.net.URI;  
import javax.tools.JavaFileObject;  
import javax.tools.SimpleJavaFileObject;  
  
/** 
 * 缓存字符串格式代码 
 *  
 * @author majiang
 * @version 2016-06-29 
 * @since jdk1.6.0 
 */  
public class StringFileObject extends SimpleJavaFileObject {  
  
    private String content;  
  
    public StringFileObject(String className, String content) {  
        super(URI.create("string:///" + className.replace('.', '/') +  
                JavaFileObject.Kind.SOURCE.extension), JavaFileObject.Kind.SOURCE);  
        this.content = content;  
    }  
  
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {  
        return this.content;  
    }  
}  
