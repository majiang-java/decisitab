package com.ifre.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONObject;

import jodd.util.Base64;


/**
 * 公共方法工具类
 * @author 王传圣
 */
public class CommonUtil {
    
	/**
     * json字符串转MAP
     * @author 王传圣
     * @param  String json字符串
     * @return Map<String,String> map对象
     * @throws Exception 
     */
    public static Map<String,Object> jsonStringToMap(String jsonString) throws Exception {
    	
    	Map<String,Object> map = new TreeMap<String, Object>();			
    	
    	JSONObject json = (JSONObject)JSONObject.parse(jsonString);
		Set<String> set = json.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {				
			String key = it.next();
			Object value = json.get(key);
			if(null != value && !"".equals(value)){
				map.put(key, value);
			}			  
		}
		
        return map;
    }
    
	/**
     * 对象转MAP
     * @author 王传圣
     * @param  obj JAVA对象
     * @return Map<String,String> map对象
     * @throws Exception 
     */
    public static Map<String,Object> objectToMap(Object obj) throws Exception {
    	
    	Map<String,Object> map = new TreeMap<String, Object>();			
    	
    	JSONObject json = (JSONObject)JSONObject.toJSON(obj);		
		Set<String> set = json.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {				
			String key = it.next();
			Object value = json.get(key);
			if(null != value && !"".equals(value)){
				map.put(key, value);
			}			  
		}
		
        return map;
    }
      
    /**
     * MAP转XML
     * @author 王传圣
     * @param  map 
     * @return String XML
     * @throws Exception 
     */
    public static String mapToXml(Map<String,String> map) throws Exception {
    			
    	String result = "";
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		StringBuffer buffer = new StringBuffer();
		while (it.hasNext()) {						
			String key = it.next();
			String value = map.get(key);
			if(null != value && !"".equals(value)){
				buffer.append("  ").append("<"+key+">"+value+"</"+key+">").append("\n");
			}			  
		}
		result = buffer.toString();
		if(!"".equals(result)){
			result = "<xml>\n" + result + "</xml>";
		}
		
        return result;
    }
    
    /**
     * 字节数组转十六进制字符串
     * @author 王传圣
     * @param  hash 字节数组
     * @return String 十六进制字符串
     * @throws Exception 
     */
    public static String byteToHex(byte[] hash) throws Exception{
    	
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    
    /**
     * 创建随机字符串
     * @author 王传圣
     * @return String 随机字符串
     */    
    public static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    public static String create_nonce_str32(){
		String result="";
		UUID uuid = java.util.UUID.randomUUID();
        String temp=uuid.toString();
        StringTokenizer token=new StringTokenizer(temp,"-"); 
        while(token.hasMoreTokens()){
        	result+=token.nextToken(); 
        } 
		return result;
	}
    
    /**
     * 获取当前时间戳
     * @author 王传圣
     * @return String 当前时间戳
     */  
    public static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
   public static Map<String, String> Dom2Map(String xml) throws DocumentException{  
	  Document doc = DocumentHelper.parseText(xml);  
 	  Element rootElement = doc.getRootElement();  
 	  Map map = Dom2Map(rootElement);
      return map;  
   }  
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map<String, Object> Dom2MapObj(String xml) throws DocumentException{  
  	  Document doc = DocumentHelper.parseText(xml);  
   	  Element rootElement = doc.getRootElement();  
   	  Map map = Dom2Map(rootElement);
        return map;  
     } 
    
   @SuppressWarnings({ "unchecked", "rawtypes" })
   public static Map Dom2Map(Element e){  
	   
       Map map = new HashMap();  
       List list = e.elements();  
       if(list.size() > 0){  
           for (int i = 0;i < list.size(); i++) {  
               Element iter = (Element) list.get(i);  
               List mapList = new ArrayList();  
                 
               if(iter.elements().size() > 0){  
                   Map m = Dom2Map(iter);  
                   if(map.get(iter.getName()) != null){  
                       Object obj = map.get(iter.getName());  
                       if(!obj.getClass().getName().equals("java.util.ArrayList")){  
                           mapList = new ArrayList();  
                           mapList.add(obj);  
                           mapList.add(m);  
                       }  
                       if(obj.getClass().getName().equals("java.util.ArrayList")){  
                           mapList = (List) obj;  
                           mapList.add(m);  
                       }  
                       map.put(iter.getName(), mapList);  
                   }else  
                       map.put(iter.getName(), m);  
               }  
               else{  
                   if(map.get(iter.getName()) != null){  
                       Object obj = map.get(iter.getName());  
                       if(!obj.getClass().getName().equals("java.util.ArrayList")){  
                           mapList = new ArrayList();  
                           mapList.add(obj);  
                           mapList.add(iter.getText());  
                       }  
                       if(obj.getClass().getName().equals("java.util.ArrayList")){  
                           mapList = (List) obj;  
                           mapList.add(iter.getText());  
                       }  
                       map.put(iter.getName(), mapList);  
                   }else  
                       map.put(iter.getName(), iter.getText());  
               }  
           }  
       }else  
           map.put(e.getName(), e.getText());  
       return map;  
   }     
   
   /**
	 * 构造HTTP POST交易表单的方法示例
	 * 
	 * @param action
	 *            表单提交地址
	 * @param hiddens
	 *            以MAP形式存储的表单键值
	 * @return 构造好的HTTP POST交易表单
	 */
	public static String createHtml(String rquestUrl, Map<String, String> mapData) {
		StringBuffer sf = new StringBuffer();
		sf.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body>");
		sf.append("<form id = \"pay_form\" action=\"" + rquestUrl
				+ "\" method=\"post\">");
		if (null != mapData && 0 != mapData.size()) {
			Set<Entry<String, String>> set = mapData.entrySet();
			Iterator<Entry<String, String>> it = set.iterator();
			while (it.hasNext()) {
				Entry<String, String> ey = it.next();
				String key = ey.getKey();
				String value = ey.getValue();
				sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\""
						+ key + "\" value=\"" + value + "\"/>");
			}
		}
		sf.append("</form>");
		sf.append("</body>");
		sf.append("<script type=\"text/javascript\">");
		sf.append("document.all.pay_form.submit();");
		sf.append("</script>");
		sf.append("</html>");
		return sf.toString();
	}
	
	/**

	* 使用gzip进行压缩
	*/
	public static String gzip(String primStr) {
		if (primStr == null || primStr.length() == 0) {
			return primStr;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip=null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(primStr.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(gzip!=null){
				try {
					gzip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new Base64().encodeToString(out.toByteArray());
	}
	
	
	/**
	*
	* <p>Description:使用gzip进行解压缩</p>
	* @param compressedStr
	* @return
	*/
	public static String gunzip(String compressedStr){
		if(compressedStr==null){
			return null;
		}
		ByteArrayOutputStream out= new ByteArrayOutputStream();
		ByteArrayInputStream in=null;
		GZIPInputStream ginzip=null;
		byte[] compressed=null;
		String decompressed = null;
		try {
			compressed = Base64.decode(compressedStr);
			in=new ByteArrayInputStream(compressed);
			ginzip=new GZIPInputStream(in);
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed=out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ginzip != null) {
				try {
					ginzip.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	return decompressed;
	}

	/**
     * map转对象
     * @author 王传圣
     * @param  map
     * @return obj 对象
     * @throws Exception 
     */
	public static void transMap2Bean(Map<String, Object> map, Object obj) throws Exception{  
		  
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  

        for (PropertyDescriptor property : propertyDescriptors) {  
            String key = property.getName();  

            if (map.containsKey(key)) {  
                Object value = map.get(key);  
                // 得到property对应的setter方法  
                Method setter = property.getWriteMethod();  
                setter.invoke(obj, value);  
            }  
        }   
    }  
}