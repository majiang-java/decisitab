package com.ifre.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.ifre.exception.IfreException;

/**
 * 实体Entity属性约束判断：以抛出异常的形式通知
 * 实体Entity被检测的属性必须有对应的get、set方法
 * 
 * @author 胡正
 *
 */
public class StringCheckUtil {
	/** ********************* check类型 *********************************** */
	public static final String CHECK_TYPE_EMPTY                = "0";                              //检查字符串空
	public static final String CHECK_TYPE_VALUEENUM            = "1";                              //检查字符串是否为枚举值
	public static final String CHECK_TYPE_MATCHES              = "2";                              //检查字符串表达式匹配
	public static final String CHECK_TYPE_INDEXOF              = "3";                              //检查字符串子串
	public static final String CHECK_TYPE_EQUALS			   = "11";							   //关联属性等值
	public static final String CHECK_TYPE_RELEVANT_EMPTY	   = "12";							   //关联属性非空
	
	/**
	 * 0检测字符串是否为空
	 * @param s：被检测的字符串
	 * @param code：检测失败异常代码
	 * @param message：检测失败异常内容
	 * @throws IfreException
	 */
	private static void checkEmpty(String s , String  code, String message) throws IfreException {
		if (code==null) code="";
		if (s== null || s.trim().isEmpty())
			throw new IfreException(message);
	}
	
	/**
	 * 1检测是否匹配数据字典
	 * @param s：被检测的字符串
	 * @param code：检测失败异常代码
	 * @param message：检测失败异常内容
	 * @param en：默认值列表，“,"分隔
	 * @throws IfreException
	 */
	private static void checkEnum(String s , String  code, String message, String en) throws IfreException {
		if (code==null) code="";
		if (s== null || s.trim().isEmpty() || en == null)
			throw new IfreException(message);
		String[] values = en.split(",", -1);
		boolean inEnum = false;
		for(int i=0;i<values.length;i++){
			if(s.equals(values[i])){
				inEnum =true;
				break;
			}
		}
		if(inEnum==false) throw new IfreException(message);
	}
	
	/**
	 * 3检测字符串子串
	 * @param s：被检测的字符串
	 * @param code：检测失败异常代码
	 * @param message：检测失败异常内容
	 * @param allstr：母串
	 * @throws IfreException
	 */
	private static void checkIndexOf(String s , String  code, String message, String allstr) throws IfreException {
		if (code==null) code="";
		if (s== null || s.trim().isEmpty() || allstr == null|| (allstr.indexOf(s)==-1))
			throw new IfreException(message);
	}
	
	/**
	 * 2检测字符串表达式匹配
	 * @param s：被检测的字符串
	 * @param code：检测失败异常代码
	 * @param message：检测失败异常内容
	 * @param expression：表达式
	 * @throws IfreException
	 */
	private static void checkMatches(String s , String  code, String message, String expression) throws IfreException {
		if (code==null) code="";
		if (s== null || s.trim().isEmpty()|| expression== null|| (s.matches(expression)==false))
			throw new IfreException(message);
	}
	
	//{contractMoney|11||"合同金额contractMoney应与实付金额actualMoney一致"|actualMoney}
	/**
	 * 11检测关联属性等值
	 * @param entity
	 * @param s：被检测的字符串
	 * @param code：检测失败异常代码
	 * @param message：检测失败异常内容
	 * @param attrs：关联属性名称列表，“,"分隔
	 * @throws IfreException
	 */
	private static void checkAttrEquals(Object entity ,String s , String  code, String message, String attrs) throws IfreException {
		if (code==null) code="";
		if (s== null || s.trim().isEmpty()|| attrs ==null ||attrs.trim().isEmpty())
			throw new IfreException(message);
		String[] fields = attrs.split(",", -1);
		boolean isEqual = true;
		for(int i=0;i<fields.length;i++){
			if(! s.equals(getFieldValue(entity, fields[i]))){
				isEqual = false;
				break;
			}
		}
		if(isEqual==false) throw new IfreException(message);
	}

	//{lendAccType|12||"放款账户类型lendAccType不为空则放款银行代码lendBankCode、放款账户名称lendAccName、放款账户号码lendAccNo、支付渠道lendChannel、放款账户开户支行lendOpenBranch、放款账户开户所在省lendOpenProvince、放款账户开户所在市lendOpenCity都不能为空"|"lendBankCode,lendAccName,lendAccNo,lendChannel,lendOpenBranch,lendOpenProvince,lendOpenCity"}
	/**
	 * 12检测关联属性，全非空、全空
	 * @param entity
	 * @param s：被检测的字符串
	 * @param code：检测失败异常代码
	 * @param message：检测失败异常内容
	 * @param attrs：关联属性名称列表，“,"分隔
	 * @throws IfreException
	 */
	private static void checkRelevantAttrEmpty(Object entity ,String s , String  code, String message, String attrs) throws IfreException {
		if (code==null) code="";
		if (s== null || s.trim().isEmpty()){
			if (attrs ==null ||attrs.trim().isEmpty())
				return;
			else{
				String[] fields = attrs.split(",", -1);
				for(int i=0;i<fields.length;i++){
					String attValue = getFieldValue(entity, fields[i]);
					if(attValue== null || attValue.trim().isEmpty()){
						continue;
					}else throw new IfreException(message);
				}
			}
		}else{
			String[] fields = attrs.split(",", -1);
			boolean isEmpty = false;
			for(int i=0;i<fields.length;i++){
				String attValue = getFieldValue(entity, fields[i]);
				if (attValue== null || attValue.trim().isEmpty()){
					isEmpty = true;
					break;
				}
			}
			if(isEmpty) throw new IfreException(message);
		}
	}
	
	/**
	 * 根据属性名：获取对象中的对应属性值
	 * @param entity
	 * @param name
	 * @return
	 * @throws IfreException
	 */
	private static String getFieldValue(Object entity ,String name) throws IfreException {
		if(entity ==null ||name ==null) return null;
		String fieldValue = null;
		//添加一种从Map取属性值
		if (entity instanceof Map){
			try {
				Object value = ((Map<String,Object>) entity).get(name);
				if (value != null) fieldValue = value.toString();
			}catch (Exception e) {
				throw new IfreException("获取Map中属性值异常", e);
			}
		}
		else{
		//默认从Entity中通过get方法获取属性值
			String methodName = getMethodName(name);
			try {
				//返回方法名为methodName 的一个 Method 对象，后面跟的是该方法参数
				Method method = entity.getClass().getMethod(methodName, new Class[0]);
				//执行该方法
				Object ob =  method.invoke(entity, new Object[0]);
				if (ob !=null) fieldValue = ob.toString();
			} catch (Exception e) {
				throw new IfreException("获取实体属性值异常", e);
			}
		}
		return fieldValue;		
	}

	/**
	 * 核心：根据不同规则执行不同的校验
	 * @param entity
	 * @param item
	 * @throws IfreException
	 */
	private static void checkEntityItem(Object entity , String[] item) throws IfreException {
		if (item!=null &&item.length >=4){
			String fieldValue = getFieldValue(entity ,item[0]);
			if (CHECK_TYPE_EMPTY.equals(item[1])){
				checkEmpty(fieldValue , item[2], item[3]);
			}else if (item.length>4){
				if (CHECK_TYPE_VALUEENUM.equals(item[1])){
					checkEnum(fieldValue, item[2], item[3],item[4]);
				}
				else if (CHECK_TYPE_MATCHES.equals(item[1])){
					checkMatches(fieldValue, item[2], item[3],item[4]);
				}
				else if (CHECK_TYPE_INDEXOF.equals(item[1])){
					checkIndexOf(fieldValue, item[2], item[3],item[4]);
				}
				else if (CHECK_TYPE_EQUALS.equals(item[1])){
					checkAttrEquals(entity ,fieldValue, item[2], item[3],item[4]);
				}
				else if (CHECK_TYPE_RELEVANT_EMPTY.equals(item[1])){
					checkRelevantAttrEmpty(entity ,fieldValue, item[2], item[3],item[4]);
				}

			}else throw new IfreException( "属性："+ item[0]+"校验方式异常");
		}
	}
	
	/**
	 * 根据属性名称拼接对象get方法名
	 * @param fieldName
	 * @return
	 */
	private static String getMethodName(String fieldName){
		String methodName = null;
		if (fieldName != null) {
			methodName = "get"+  fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1).trim();
		}
		return methodName;
	}
	
	/**
	 * 根据List检测对象所有属性的约束性-提供对外方法
	 * @param entity
	 * @param list
	 * @throws IfreException
	 */
	public static void checkEntity(Object entity , List<String[]> list) throws IfreException {
		if (list== null|| list.size()==0) return;
		for(String[] temp: list){
			checkEntityItem(entity, temp);
		}
	}

	/**
	 * 根据Map检测对象所有属性的约束性-提供对外方法
	 * @param entity
	 * @param map
	 * @throws IfreException
	 */
	public static void checkEntity(Object entity ,Map<String, String[]> map) throws IfreException {
		if (map== null|| map.size() ==0) return;
		
	    for (Map.Entry<String, String[]> entry : map.entrySet()) {
	    	String[] value =  entry.getValue();
	    	String[] temp = new String[value.length+1];
	    	temp[0] = entry.getKey();
	    	for (int i=1;i<= value.length; i++){
	    		temp[i]= value[i];
	    	}
	    	checkEntityItem(entity, temp);
	    }
	}
	
	/**
	 * 根据配置文件检测对象所有属性的约束性-提供对外方法
	 * @param entity
	 * @param filename
	 * @throws IfreException
	 */
	public static void checkEntity(Object entity ,String filename) throws IfreException {
		InputStreamReader read = null;
		BufferedReader reader= null;
		try{
			File file = new File(StringCheckUtil.class.getResource("/").getPath() + File.separator +filename);
			read = new InputStreamReader (new FileInputStream(file),"UTF-8");
			reader=new BufferedReader(read);
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("{") && line.endsWith("}")){
					String[] temp = line.substring(1, line.length() -1).split("\\|", 5);
					for(int i =0;i<temp.length; i++){
						if(temp[i] ==null) continue;
						temp[i] = temp[i].trim();
						if(temp[i].length()>2 && temp[i].startsWith("\"") && temp[i].endsWith("\"")){
							temp[i]= temp[i].substring(1, temp[i].length()-1);
						}
					}
					checkEntityItem(entity, temp);
				}			 
			}
			 
		}catch(IfreException e){
			throw e;
		}catch(Exception e){
			throw new IfreException("读取校验规则文件内容异常", e);
		}finally{
			try{
				if(reader !=null) reader.close();
				if(read !=null) read.close();
			}catch(Exception e){
				throw new IfreException("关闭校验规则文件异常", e);
			}
		}
	}

}
