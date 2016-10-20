package com.ifre.page.brms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;

import com.ifre.entity.brms.RulePckgEntity;

/**   
 * @Title: Entity
 * @Description: 决策产品
 * @author zhangdaihao
 * @date 2016-05-19 13:42:39
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class RuleProdPage implements java.io.Serializable {
	/**保存-决策包信息*/
	private List<RulePckgEntity> rulePckgList = new ArrayList<RulePckgEntity>();
	public List<RulePckgEntity> getRulePckgList() {
		return rulePckgList;
	}
	public void setRulePckgList(List<RulePckgEntity> rulePckgList) {
		this.rulePckgList = rulePckgList;
	}


	/**ID*/
	private java.lang.String id;
	/**组织机构ID*/
	private java.lang.String orgId;
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
	private java.lang.Object versionDesc;
	/**状态*/
	private java.lang.Integer status;
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
	 *@return: java.lang.Object  描述
	 */
	public java.lang.Object getDescp(){
		return this.descp;
	}

	/**
	 *方法: 设置java.lang.Object
	 *@param: java.lang.Object  描述
	 */
	public void setDescp(java.lang.String descp){
		this.descp = descp;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  产品组ID
	 */
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
	 *方法: 取得java.lang.Object
	 *@return: java.lang.Object  版本描述
	 */
	public java.lang.Object getVersionDesc(){
		return this.versionDesc;
	}

	/**
	 *方法: 设置java.lang.Object
	 *@param: java.lang.Object  版本描述
	 */
	public void setVersionDesc(java.lang.Object versionDesc){
		this.versionDesc = versionDesc;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  状态
	 */
	public java.lang.Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  状态
	 */
	public void setStatus(java.lang.Integer status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人
	 */
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
