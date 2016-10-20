/**
 * 
 * @author majiang
 *
 */
package com.ifre.ruleengin;

import java.lang.reflect.Method;

public class RefacetHandler {
	
	/**
	 * 反射执行对象中的方法（传入参数）
	 * 
	 * @param owner
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	public static Object invokeMethod(Object owner, String methodName, Object[] args) throws Exception {   
	     Class<?> ownerClass = owner.getClass();   
		 Class[] argsClass = new Class[args.length];   
	     for (int i = 0, j = args.length; i < j; i++) {   
	    	 argsClass[i] = args[i].getClass().getSuperclass();
	     }   
	     Method method = ownerClass.getMethod(methodName,argsClass);   
	     return method.invoke(owner, args);   
	} 
	/**
	 * 反射执行对象中的方法 （给对象赋值）
	 * @param owner
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	public static Object invokeFieldMethod(Object owner, String methodName, Object[] args) throws Exception {   
	     Class<? extends Object> ownerClass = owner.getClass();   
	     Class[] argsClass = new Class[args.length];   
	    
	     for (int i = 0, j = args.length; i < j; i++) {  
	    	 argsClass[i] = args[i].getClass();
	     }   
	     
	     Method method = ownerClass.getMethod(methodName,argsClass);   
	     return method.invoke(owner, args);   
	}
	 
	 @SuppressWarnings("rawtypes")
	public static Object invokeFieldMethod(Object owner, String methodName, Object[] args,String type) throws Exception {   
	     Class<? extends Object> ownerClass = owner.getClass();   
	    
	     Method method = null;
	   
	     if("int".equals(type)){
	    	 method = ownerClass.getMethod(methodName, int.class);
	     }else if("double".equals(type)){
	    	 method = ownerClass.getMethod(methodName, double.class);
	     }else{
			Class[] argsClass = new Class[args.length];
			for (int i = 0, j = args.length; i < j; i++) {
				argsClass[i] = args[i].getClass();
			}
			method = ownerClass.getMethod(methodName, argsClass);
	     }
	      
	     return method.invoke(owner, args);   
	}


}
