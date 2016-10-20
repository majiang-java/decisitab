package com.ifre.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <li>系统总异常类
 * <li>继承的子类必须捕获异常处理
 */
public class IfreException extends Exception {

//	@Autowired
//	private SystemService systemService;
		
	private static final long serialVersionUID = 1L;

	//异常分类编码
	private int code;
	
	//占位符值列表
	private Object[] placeHolderValues;
	
	//堆栈信息第一行
	private String stackTraceFirst;
	
	//全部信息：realCause + stackTraceFirst
	private String totalMessage;
	
	private Throwable realCause;
	
	public IfreException(){
		super();
		this.code = 000000;
		this.placeHolderValues = new String[] {"-"};
	}
	
	public IfreException(int code){
		super();
		this.code = code;
		this.placeHolderValues = new String[] {"-"};
	}
	
	public IfreException(Throwable cause){
		super(cause);
		this.code = 000000;
		this.placeHolderValues = new String[]{"-"};
	}
	
	public IfreException(String message){
		super(message);
		this.code = 000000;
		this.placeHolderValues = new String[]{message};
	}

	public IfreException(int code,Throwable cause){
		super(cause);
		this.code = code;
		this.placeHolderValues = new String[]{"-"};
	}
	
	public IfreException(int code,Object[] placeHolderValuse){
		super();
		this.code = code;
		this.placeHolderValues = placeHolderValuse;
	}
	
	public IfreException(String message,Throwable cause){
		super(message,cause);
		this.code = 000000;
		this.placeHolderValues = new String[]{message};
	}
	
	public IfreException(String message,Throwable cause,String stackTraceFirst){
		super(message,cause);
		this.code = 000000;
		if(null != cause){
			this.realCause = cause;
			if(null != cause.getCause()){
				this.realCause = cause.getCause();
			}
		}		
		this.placeHolderValues = new String[]{message};
		this.stackTraceFirst = stackTraceFirst;
		this.totalMessage = realCause + "\n at " + stackTraceFirst;
	}
	
	public IfreException(Throwable cause,String message){
		super(message,cause);
		this.realCause = deepestException(cause);	
		this.placeHolderValues = new String[]{message};
		this.totalMessage = getThrowableMessage(cause);
	}
	
	public String getThrowableMessage(Throwable ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
	}
	
	public IfreException(int code,Object[] placeHolderValuse,Throwable cause){
		super(cause);
		this.code = code;
		this.placeHolderValues = placeHolderValuse;
	}
	
	public IfreException(int code,String message,Throwable cause){
		super(message,cause);
		this.code = code;
		this.realCause = deepestException(cause);	
		this.placeHolderValues = new String[]{message};
		this.stackTraceFirst = cause.getStackTrace()[0].toString();
		this.totalMessage = getThrowableMessage(cause);
	}
	
	public IfreException(int code,String message,Throwable cause,String stackTraceFirst){
		super(message,cause);
		this.code = code;
		this.realCause = deepestException(cause);	
		this.placeHolderValues = new String[]{message};
		this.stackTraceFirst = stackTraceFirst;
		this.totalMessage = getThrowableMessage(cause);
	}
	
	/**
	 * 获取最原始的异常出处，即最初抛出异常的地方
	 */
    private Throwable deepestException(Throwable e){
        Throwable tmp = e;
        int breakPoint = 0;
        while(tmp.getCause()!=null){
            if(tmp.equals(tmp.getCause())){
                break;
            }
            tmp=tmp.getCause();
            breakPoint++;
            if(breakPoint>1000){
                break;
            }
        } 
        return tmp;
    }
	
	public int getCode() {
		return code;
	}

	public Object[] getPlaceHolderValues() {
		return placeHolderValues;
	}

	public String getStackTraceFirst() {
		return stackTraceFirst;
	}
	
	public String getTotalMessage() {		
		return totalMessage;
	}
	
	public Throwable getRealCause() {		
		return realCause;
	}
	
	public static Throwable getRealCause(Exception ex){
		
		Throwable realCause = null; 
		if(null != ex){
			if(null != ex.getCause()){
				realCause = ex.getCause();
				if(null != realCause.getCause()){
					realCause = realCause.getCause();
				}
			}			
		}
		return realCause;
	}
	
	/**
	 * 将一个异常包装成IfreException抛出
	 * @param originException
	 * @return
	 */
	public static IfreException transferIfreException(Exception originException){
		if(originException == null){
			return null;
		} else if(originException instanceof IfreException){
			return (IfreException)originException;
		}else{
			return new IfreException(originException);
		}
	}
}