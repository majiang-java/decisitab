package com.ifre.form.shared;


/**
 * 共享服务返回信息
 * @author 王传圣
 *
 */
public class SharedReturn {

	private	String	status = "FAIL";	//状态(SUCCESS/FAIL)
	private	Object 	msg;   				//返回信息（明文）
	private Object  encryptMsg;			//返回信息（密文）
	private String  sysSign;			//签名字段
	
	public String getStatus(){
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Object getMsg() {
		return msg;
	}
	public void setMsg(Object msg) {
		this.msg = msg;
	}
	public Object getEncryptMsg() {
		return encryptMsg;
	}
	public void setEncryptMsg(Object encryptMsg) {
		this.encryptMsg = encryptMsg;
	}
	public String getSysSign() {
		return sysSign;
	}
	public void setSysSign(String sysSign) {
		this.sysSign = sysSign;
	}
}