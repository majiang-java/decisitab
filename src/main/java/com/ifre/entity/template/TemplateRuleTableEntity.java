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
@Table(name = "brms_rule_table_template", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TemplateRuleTableEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
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
	
	
	private String orgCode;
	
	
	private String typeId;//父类型
	
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
	
	@Column(name ="ORG_CODE",length=36)
	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  包ID
	 */
	@Column(name ="PKG_NO",length=36)
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
	
	@Column(name ="TYPE_ID",nullable=true)
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	

	
	
	
}