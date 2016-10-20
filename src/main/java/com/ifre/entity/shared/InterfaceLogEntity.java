package com.ifre.entity.shared;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 通讯日志
 * @author zhangdaihao
 * @date 2016-04-14 17:49:02
 * @version V1.0   
 *
 */
@Entity
@Table(name = "inf_interface_log", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class InterfaceLogEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
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
	private java.lang.String sendMsg;
	/**结果信息*/
	private java.lang.String result;
	/**处理状态*/
	private java.lang.String handleStatus;
	/**备注*/
	private java.lang.String remark;
	/**创建日期*/
	private java.util.Date createDate;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  渠道来源
	 */
	@Column(name ="SYS_SOURCE",nullable=false,length=32)
	public java.lang.String getSysSource(){
		return this.sysSource;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  渠道来源
	 */
	public void setSysSource(java.lang.String sysSource){
		this.sysSource = sysSource;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务类型
	 */
	@Column(name ="BUSINESS_TYPE",nullable=false,length=20)
	public java.lang.String getBusinessType(){
		return this.businessType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务类型
	 */
	public void setBusinessType(java.lang.String businessType){
		this.businessType = businessType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务子类型
	 */
	@Column(name ="SUB_BUSINESS_TYPE",nullable=true,length=20)
	public java.lang.String getSubBusinessType(){
		return this.subBusinessType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务子类型
	 */
	public void setSubBusinessType(java.lang.String subBusinessType){
		this.subBusinessType = subBusinessType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  接收信息
	 */
	@Column(name ="RECEIVE_MSG",nullable=true,length=1000)
	public java.lang.String getReceiveMsg(){
		return this.receiveMsg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接收信息
	 */
	public void setReceiveMsg(java.lang.String receiveMsg){
		this.receiveMsg = receiveMsg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发送信息
	 */
	@Column(name ="SEND_MSG",nullable=false,length=1000)
	public java.lang.String getSendMsg(){
		return this.sendMsg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发送信息
	 */
	public void setSendMsg(java.lang.String sendMsg){
		this.sendMsg = sendMsg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结果信息
	 */
	@Column(name ="RESULT",nullable=true,length=1000)
	public java.lang.String getResult(){
		return this.result;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结果信息
	 */
	public void setResult(java.lang.String result){
		this.result = result;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  处理状态
	 */
	@Column(name ="HANDLE_STATUS",nullable=false,precision=10,scale=0)
	public java.lang.String getHandleStatus(){
		return this.handleStatus;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  处理状态
	 */
	public void setHandleStatus(java.lang.String handleStatus){
		this.handleStatus = handleStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=100)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	@Column(name ="CREATE_DATE",nullable=false)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  渠道描述
	 */
	@Column(name ="SYS_DESC",nullable=true)
	public java.lang.String getSysDesc() {
		return sysDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@return: java.lang.String  渠道描述
	 */
	public void setSysDesc(java.lang.String sysDesc) {
		this.sysDesc = sysDesc;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务编码
	 */
	@Column(name ="BUSINESS_NUM",nullable=false)
	public java.lang.String getBusinessNum() {
		return businessNum;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务编码
	 */
	public void setBusinessNum(java.lang.String businessNum) {
		this.businessNum = businessNum;
	}
}
