package com.ifre.entity.brms;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.ifre.entity.ruleengin.BrmsRuleTableEntity;

/**   
 * @Title: Entity
 * @Description: 决策产品
 * @author zhangdaihao
 * @date 2016-05-19 14:23:57
 * @version V1.0   
 *
 */
@Entity
@Table(name = "BRMS_RULE_PROD", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class RuleProdEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**组织机构ID*/
	private java.lang.String orgId;
	/**组织机构*/
	private java.lang.String orgCode;
	/**知识库ID*/
	private java.lang.String kknwldgId;
	/**名称*/
	private java.lang.String name;
	/**描述*/
	private java.lang.String descp;
	/**产品组ID*/
	private java.lang.String groupId;
	/**组件ID*/
	private java.lang.String artifactId;
	/**版本号*/
	private java.lang.String versionId;
	/**版本描述*/
	private java.lang.String versionDesc;
	/**状态*/
	private java.lang.String status;
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
	
	/**方案类别*/
	private java.lang.Integer type;	
	
	/**方案权限*/
	private java.lang.Integer rightStatus;	
	
	/**模板类别*/
	private java.lang.String tempType;	
	
	private java.lang.String packStatus;
	
	private java.lang.String publishStatus;
	
	private List<BrmsRuleTableEntity> ruleTables = new ArrayList<BrmsRuleTableEntity>();
	
	private List<VarTypegroup> varTypegroup = new ArrayList<VarTypegroup>();
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
	 *@param: java.lang.String  组织机构ID
	 */
	public void setOrgId(java.lang.String orgId){
		this.orgId = orgId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  知识库ID
	 */
	@Column(name ="KKNWLDG_ID",nullable=false,length=36)
	public java.lang.String getKknwldgId(){
		return this.kknwldgId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  知识库ID
	 */
	public void setKknwldgId(java.lang.String kknwldgId){
		this.kknwldgId = kknwldgId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="NAME",nullable=true,length=100)
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
	 *@return: java.lang.String  产品组ID
	 */
	@Column(name ="GROUP_ID",nullable=true,length=200)
	public java.lang.String getGroupId(){
		return this.groupId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  产品组ID
	 */
	public void setGroupId(java.lang.String groupId){
		this.groupId = groupId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  组件ID
	 */
	@Column(name ="ARTIFACT_ID",nullable=true,length=100)
	public java.lang.String getArtifactId(){
		return this.artifactId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  组件ID
	 */
	public void setArtifactId(java.lang.String artifactId){
		this.artifactId = artifactId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  版本号
	 */
	@Column(name ="VERSION_ID",nullable=true,length=100)
	public java.lang.String getVersionId(){
		return this.versionId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  版本号
	 */
	public void setVersionId(java.lang.String versionId){
		this.versionId = versionId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  版本描述
	 */
	@Column(name ="VERSION_DESC",nullable=true,length=65535)
	public java.lang.String getVersionDesc(){
		return this.versionDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  版本描述
	 */
	public void setVersionDesc(java.lang.String versionDesc){
		this.versionDesc = versionDesc;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  状态
	 */
	@Column(name ="STATUS",nullable=true,length=11)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
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

	@Transient
	public List<BrmsRuleTableEntity> getRuleTables() {
		return ruleTables;
	}

	public void setRuleTables(List<BrmsRuleTableEntity> ruleTables) {
		this.ruleTables = ruleTables;
	}

	/**
	 * type
	 * @return  the type
	*/
	@Column(name ="TYPE",nullable=true,precision=10,scale=0)
	public java.lang.Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	/**
	 * varTypegroup
	 * @return  the varTypegroup
	*/
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ruleProdEntity")
	public List<VarTypegroup> getVarTypegroup() {
		return varTypegroup;
	}

	/**
	 * @param varTypegroup the varTypegroup to set
	 */
	public void setVarTypegroup(List<VarTypegroup> varTypegroup) {
		this.varTypegroup = varTypegroup;
	}

	/**
	 * rightStatus
	 * @return  the rightStatus
	*/
	@Column(name ="RIGHT_STATUS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getRightStatus() {
		return rightStatus;
	}

	/**
	 * @param rightStatus the rightStatus to set
	 */
	
	public void setRightStatus(java.lang.Integer rightStatus) {
		this.rightStatus = rightStatus;
	}

	/**
	 * tempType
	 * @return  the tempType
	*/
	@Column(name ="TEMP_TYPE",nullable=true,length=20)
	public java.lang.String getTempType() {
		return tempType;
	}

	/**
	 * @param tempType the tempType to set
	 */
	public void setTempType(java.lang.String tempType) {
		this.tempType = tempType;
	}

	@Column(name ="PACK_STATUS",length=10)
	public java.lang.String getPackStatus() {
		return packStatus;
	}
	
	public void setPackStatus(java.lang.String packStatus) {
		this.packStatus = packStatus;
	}


	@Column(name ="PUBLISH_STATUS",length=20)
	public java.lang.String getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(java.lang.String publishStatus) {
		this.publishStatus = publishStatus;
	}
	
	
	
}
