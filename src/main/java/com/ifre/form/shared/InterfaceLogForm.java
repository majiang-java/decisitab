package com.ifre.form.shared;


public class InterfaceLogForm {
	
	/**渠道来源*/
	private java.lang.String sysSource;
	/**渠道描述*/
	private java.lang.String sysDesc;
	/**业务编码*/
	private java.lang.String businessNum;
	/**业务类型*/
	private java.lang.String businessType;
	/**业务子类型*/
	private java.lang.String subBusinessType;
	/**接收信息*/
	private java.lang.String receiveMsg;
	/**发送信息*/
	private java.lang.Object sendObj;
	/**结果信息*/
	private java.lang.String result;
	/**处理状态*/
	private java.lang.Integer handleStatus;
	/**备注*/
	private java.lang.String remark;

	public InterfaceLogForm(String businessType) {
		super();
		this.businessType = businessType;
	}
	
	public java.lang.String getSysSource() {
		return sysSource;
	}
	public void setSysSource(java.lang.String sysSource) {
		this.sysSource = sysSource;
	}
	public java.lang.String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(java.lang.String businessType) {
		this.businessType = businessType;
	}
	public java.lang.String getSubBusinessType() {
		return subBusinessType;
	}
	public void setSubBusinessType(java.lang.String subBusinessType) {
		this.subBusinessType = subBusinessType;
	}
	public java.lang.String getReceiveMsg() {
		return receiveMsg;
	}
	public void setReceiveMsg(java.lang.String receiveMsg) {
		this.receiveMsg = receiveMsg;
	}
	public java.lang.Object getSendObj() {
		return sendObj;
	}
	public void setSendObj(java.lang.Object sendObj) {
		this.sendObj = sendObj;
	}
	public java.lang.String getResult() {
		return result;
	}
	public void setResult(java.lang.String result) {
		this.result = result;
	}
	public java.lang.Integer getHandleStatus() {
		return handleStatus;
	}
	public void setHandleStatus(java.lang.Integer handleStatus) {
		this.handleStatus = handleStatus;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	public java.lang.String getSysDesc() {
		return sysDesc;
	}
	public void setSysDesc(java.lang.String sysDesc) {
		this.sysDesc = sysDesc;
	}

	public java.lang.String getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(java.lang.String businessNum) {
		this.businessNum = businessNum;
	}
}