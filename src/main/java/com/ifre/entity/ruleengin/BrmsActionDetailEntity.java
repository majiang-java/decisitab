package com.ifre.entity.ruleengin;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 动作明细
 * @author zhangdaihao
 * @date 2016-05-24 17:39:43
 * @version V1.0   
 *
 */
@Entity
@Table(name = "brms_action_detail", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class BrmsActionDetailEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**决策表ID*/
	private java.lang.String ruleTableId;
	/**动作ID*/
	private java.lang.String actionId;
	/**序号*/
	private java.lang.Integer seq;
	/**动作值*/
	private java.lang.String actionValue;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  动作ID
	 */
	@Column(name ="ACTION_ID",nullable=false,length=36)
	public java.lang.String getActionId(){
		return this.actionId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  动作ID
	 */
	public void setActionId(java.lang.String actionId){
		this.actionId = actionId;
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
	 *@return: java.lang.String  动作值
	 */
	@Column(name ="ACTION_VALUE",nullable=true,length=300)
	public java.lang.String getActionValue(){
		return this.actionValue;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  动作值
	 */
	public void setActionValue(java.lang.String actionValue){
		this.actionValue = actionValue;
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
