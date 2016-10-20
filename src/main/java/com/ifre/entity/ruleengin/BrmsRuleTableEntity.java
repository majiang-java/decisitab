package com.ifre.entity.ruleengin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 决策表
 * @author zhangdaihao
 * @date 2016-05-31 10:09:51
 * @version V1.0   
 *
 */
@Entity
@Table(name = "brms_rule_table", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class BrmsRuleTableEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**组织机构ID*/
	private java.lang.String orgId;
	/**知识库ID*/
	private java.lang.String knowId;
	/**产品ID*/
	private java.lang.String prodId;
	/**包ID*/
	private java.lang.String pkgNo;
	/**包全名*/
	private java.lang.String pkgAllName;
	/**名称*/
	private java.lang.String name;
	/**注释*/
	private java.lang.String note;
	/**优先级*/
	private java.lang.Integer salience;
	/**条件总数*/
	private java.lang.Integer condCount;
	/**动作总数*/
	private java.lang.Integer actionCount;
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
	
	private java.lang.String mergedRegion;
	
	private String orgName;
	
	private String prodName;
	
	private String knowName;
	
	private String orgCode;
	
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
	 *@return: java.lang.String  组织机构ID
	 */
	@Column(name ="ORG_ID",nullable=false,length=36)
	public java.lang.String getOrgId(){
		return this.orgId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  组织机构Code
	 */
	public void setOrgId(java.lang.String orgId){
		this.orgId = orgId;
	}
	
	@Column(name ="ORG_CODE",nullable=false,length=36)
	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  知识库ID
	 */
	@Column(name ="KNOW_ID",nullable=false,length=36)
	public java.lang.String getKnowId(){
		return this.knowId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  知识库ID
	 */
	public void setKnowId(java.lang.String knowId){
		this.knowId = knowId;
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
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  产品ID
	 */
	public void setProdId(java.lang.String prodId){
		this.prodId = prodId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  包ID
	 */
	@Column(name ="PKG_NO")
	public java.lang.String getPkgNo(){
		return this.pkgNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  包ID
	 */
	public void setPkgNo(java.lang.String pkgNo){
		this.pkgNo = pkgNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  包全名
	 */
	@Column(name ="PKG_ALL_NAME",nullable=false,length=260)
	public java.lang.String getPkgAllName(){
		return this.pkgAllName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  包全名
	 */
	public void setPkgAllName(java.lang.String pkgAllName){
		this.pkgAllName = pkgAllName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="NAME",nullable=false,length=200)
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
	 *方法: 取得java.lang.Object
	 *@return: java.lang.Object  注释
	 */
	@Column(name ="NOTE",nullable=false,length=65535)
	public java.lang.String getNote(){
		return this.note;
	}

	/**
	 *方法: 设置java.lang.Object
	 *@param: java.lang.Object  注释
	 */
	public void setNote(java.lang.String note){
		this.note = note;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  优先级
	 */
	@Column(name ="SALIENCE",nullable=true,precision=10,scale=0)
	public java.lang.Integer getSalience(){
		return this.salience;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  优先级
	 */
	public void setSalience(java.lang.Integer salience){
		this.salience = salience;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  条件总数
	 */
	@Column(name ="COND_COUNT",nullable=true,precision=10,scale=0)
	public java.lang.Integer getCondCount(){
		return this.condCount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  条件总数
	 */
	public void setCondCount(java.lang.Integer condCount){
		this.condCount = condCount;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  动作总数
	 */
	@Column(name ="ACTION_COUNT",nullable=true,precision=10,scale=0)
	public java.lang.Integer getActionCount(){
		return this.actionCount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  动作总数
	 */
	public void setActionCount(java.lang.Integer actionCount){
		this.actionCount = actionCount;
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
	
	@Column(name ="MERGED_REGION",nullable=false,length=65535)
	public java.lang.String getMergedRegion() {
		return mergedRegion;
	}

	public void setMergedRegion(java.lang.String mergedRegion) {
		this.mergedRegion = mergedRegion;
	}
	@Transient
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Transient
	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	@Transient
	public String getKnowName() {
		return knowName;
	}

	public void setKnowName(String knowName) {
		this.knowName = knowName;
	}


	
}
