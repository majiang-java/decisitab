package com.ifre.entity.brms;

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
 * @Description: 决策包
 * @author zhangdaihao
 * @date 2016-05-19 14:25:10
 * @version V1.0   
 *
 */
@Entity
@Table(name = "BRMS_RULE_PCKG", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class RulePckgEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**产品ID*/
	private java.lang.String prodId;
	
	/**组织机构*/
	private java.lang.String orgCode;
	
	
	/**组织机构ID*/
	private java.lang.String orgId;
	
	/**名称*/
	private java.lang.String name;
	/**全名*/
	private java.lang.String allName;
	/**描述*/
	private java.lang.String descp;
	/**父包ID*/
	private java.lang.String parentPckgId;
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
	 *@return: java.lang.String  产品ID
	 */
	@Column(name ="PROD_ID",nullable=false,length=36)
	public java.lang.String getProdId(){
		return this.prodId;
	}

	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  组织机构ID
	 */
	@Column(name ="ORG_ID",nullable=false,length=36)
	public java.lang.String getOrgId(){
		return this.orgId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  组织机构ID
	 */
	public void setOrgId(java.lang.String orgId){
		this.orgId = orgId;
	}
	
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  产品ID
	 */
	public void setProdId(java.lang.String prodId){
		this.prodId = prodId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="NAME",nullable=true,length=80)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  全名
	 */
	@Column(name ="ALL_NAME",nullable=true,length=300)
	public java.lang.String getAllName(){
		return this.allName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  全名
	 */
	public void setAllName(java.lang.String allName){
		this.allName = allName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  描述
	 */
	@Column(name ="DESCP",nullable=true,length=65535)
	public java.lang.String getDescp(){
		return this.descp;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  描述
	 */
	public void setDescp(java.lang.String descp){
		this.descp = descp;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  父包ID
	 */
	@Column(name ="PARENT_PCKG_ID",nullable=true,length=36)
	public java.lang.String getParentPckgId(){
		return this.parentPckgId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  父包ID
	 */
	public void setParentPckgId(java.lang.String parentPckgId){
		this.parentPckgId = parentPckgId;
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
	
	@Column(name ="ORG_Code",nullable=true)
	public java.lang.String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(java.lang.String orgCode) {
		this.orgCode = orgCode;
	}
	
	

}
