package com.ifre.entity.template;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "brms_rule_condition_template", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TemplateRuleConditonEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**决策表ID*/
	private java.lang.String ruleTableId;
	/**序号*/
	private java.lang.Integer seq;
	/**条件标识符*/
	private java.lang.String condId;
	/**业务对象名*/
	private java.lang.String bizObjName;
	/**对象属性*/
	private java.lang.String bizObjProp;
	/**属性条件脚本*/
	private java.lang.String propCondScript;
	/**属性条件描述*/
	private java.lang.String propCondDes;
	/**创建人*/
	private java.lang.String createBy;
	/**创建人名字*/
	private java.lang.String createName;
	/**创建时间*/
	private java.util.Date createDate;
	/**修改人*/
	private java.lang.String updateBy;
	/**修改时间*/
	private java.util.Date updateDate;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ID
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
	 *@param: java.lang.String  ID
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  决策表ID
	 */
	@Column(name ="RULE_TABLE_ID",nullable=false,length=36)
	public java.lang.String getRuleTableId(){
		return this.ruleTableId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  决策表ID
	 */
	public void setRuleTableId(java.lang.String ruleTableId){
		this.ruleTableId = ruleTableId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  序号
	 */
	@Column(name ="SEQ",nullable=false,precision=10,scale=0)
	public java.lang.Integer getSeq(){
		return this.seq;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  序号
	 */
	public void setSeq(java.lang.Integer seq){
		this.seq = seq;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  条件标识符
	 */
	@Column(name ="COND_ID",nullable=true,length=36)
	public java.lang.String getCondId(){
		return this.condId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  条件标识符
	 */
	public void setCondId(java.lang.String condId){
		this.condId = condId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务对象名
	 */
	@Column(name ="BIZ_OBJ_NAME",nullable=true,length=60)
	public java.lang.String getBizObjName(){
		return this.bizObjName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务对象名
	 */
	public void setBizObjName(java.lang.String bizObjName){
		this.bizObjName = bizObjName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  对象属性
	 */
	@Column(name ="BIZ_OBJ_PROP",nullable=true,length=60)
	public java.lang.String getBizObjProp(){
		return this.bizObjProp;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  对象属性
	 */
	public void setBizObjProp(java.lang.String bizObjProp){
		this.bizObjProp = bizObjProp;
	}
	/**
	 *方法: 取得java.lang.Object
	 *@return: java.lang.Object  属性条件脚本
	 */
	@Column(name ="PROP_COND_SCRIPT",nullable=true,length=65535)
	public java.lang.String getPropCondScript(){
		return this.propCondScript;
	}

	/**
	 *方法: 设置java.lang.Object
	 *@param: java.lang.Object  属性条件脚本
	 */
	public void setPropCondScript(java.lang.String propCondScript){
		this.propCondScript = propCondScript;
	}
	/**
	 *方法: 取得java.lang.Object
	 *@return: java.lang.Object  属性条件描述
	 */
	@Column(name ="PROP_COND_DES",nullable=true,length=65535)
	public java.lang.String getPropCondDes(){
		return this.propCondDes;
	}

	/**
	 *方法: 设置java.lang.Object
	 *@param: java.lang.Object  属性条件描述
	 */
	public void setPropCondDes(java.lang.String propCondDes){
		this.propCondDes = propCondDes;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人
	 */
	@Column(name ="CREATE_BY",nullable=true,length=36)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名字
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=32)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名字
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=36)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改时间
	 */
	@Column(name ="UPDATE_DATE",nullable=true)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改时间
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
}
