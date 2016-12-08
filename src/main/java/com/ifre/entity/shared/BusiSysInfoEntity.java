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
 * @Description: 渠道来源信息
 * @author zhangdaihao
 * @date 2016-04-14 17:49:50
 * @version V1.0   
 *
 */
@Entity
@Table(name = "inf_busi_sys_info", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class BusiSysInfoEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**渠道来源*/
	private java.lang.String sysSource;
	/**渠道描述*/
	private java.lang.String sysDesc;
	/**密码*/
	private java.lang.String password;
	/**签名秘钥*/
	private java.lang.String signKey;
	/**加密秘钥*/
	private java.lang.String encryptKey;
	/**回调地址*/
	private java.lang.String notifyUrl;
	/**标识*/
	private java.lang.String marking;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	/**记录删除标志*/
	private java.lang.Integer status;
	/**排序*/
	private java.lang.Integer sorts;
	/**组织机构*/
	private java.lang.String orgCode;
	
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
	
	@Column(name ="ORG_Code")
	public java.lang.String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(java.lang.String orgCode) {
		this.orgCode = orgCode;
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
	 *@return: java.lang.String  渠道描述
	 */
	@Column(name ="SYS_DESC",nullable=false,length=32)
	public java.lang.String getSysDesc(){
		return this.sysDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  渠道描述
	 */
	public void setSysDesc(java.lang.String sysDesc){
		this.sysDesc = sysDesc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  密码
	 */
	@Column(name ="PASSWORD",nullable=true,length=32)
	public java.lang.String getPassword(){
		return this.password;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  密码
	 */
	public void setPassword(java.lang.String password){
		this.password = password;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  签名秘钥
	 */
	@Column(name ="SIGN_KEY",nullable=true,length=43)
	public java.lang.String getSignKey(){
		return this.signKey;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  签名秘钥
	 */
	public void setSignKey(java.lang.String signKey){
		this.signKey = signKey;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  加密秘钥
	 */
	@Column(name ="ENCRYPT_KEY",nullable=true,length=43)
	public java.lang.String getEncryptKey(){
		return this.encryptKey;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  加密秘钥
	 */
	public void setEncryptKey(java.lang.String encryptKey){
		this.encryptKey = encryptKey;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  回调地址
	 */
	@Column(name ="NOTIFY_URL",nullable=true,length=100)
	public java.lang.String getNotifyUrl(){
		return this.notifyUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  回调地址
	 */
	public void setNotifyUrl(java.lang.String notifyUrl){
		this.notifyUrl = notifyUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标识
	 */
	@Column(name ="MARKING",nullable=true,length=10)
	public java.lang.String getMarking(){
		return this.marking;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标识
	 */
	public void setMarking(java.lang.String marking){
		this.marking = marking;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */
	@Column(name ="CREATE_BY",nullable=false,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
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
	 *@return: java.lang.String  更新人登录名称
	 */
	@Column(name ="UPDATE_BY",nullable=false,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */
	@Column(name ="UPDATE_DATE",nullable=false)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  记录删除标志
	 */
	@Column(name ="STATUS",nullable=true,length=2)
	public java.lang.Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  记录删除标志
	 */
	public void setStatus(java.lang.Integer status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排序
	 */
	@Column(name ="SORTS",nullable=true,length=2)
	public java.lang.Integer getSorts(){
		return this.sorts;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排序
	 */
	public void setSorts(java.lang.Integer sorts){
		this.sorts = sorts;
	}
}
