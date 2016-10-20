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
 * @Description: 存储决策表文件上传记录
 * @author zhangdaihao
 * @date 2016-06-24 10:31:13
 * @version V1.0   
 *
 */
@Entity
@Table(name = "brms_deci_file", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class BrmsDeciFileEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**决策表文件名*/
	private java.lang.String fileName;
	/**决策表包名*/
	private java.lang.String pkgName;
	/**决策表类型*/
	private java.lang.String contentType;
	/**创建用户*/
	private java.lang.String createUser;
	/**创建时间*/
	private java.util.Date createTime;
	/**更新者*/
	private java.lang.String updateUser;
	/**包含的决策表主键*/
	private java.lang.String containTable;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  决策表文件名
	 */
	@Column(name ="FILE_NAME",nullable=true,length=100)
	public java.lang.String getFileName(){
		return this.fileName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  决策表文件名
	 */
	public void setFileName(java.lang.String fileName){
		this.fileName = fileName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  决策表包名
	 */
	@Column(name ="PKG_NAME",nullable=true,length=100)
	public java.lang.String getPkgName(){
		return this.pkgName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  决策表包名
	 */
	public void setPkgName(java.lang.String pkgName){
		this.pkgName = pkgName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  决策表类型
	 */
	@Column(name ="CONTENT_TYPE",nullable=true,length=100)
	public java.lang.String getContentType(){
		return this.contentType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  决策表类型
	 */
	public void setContentType(java.lang.String contentType){
		this.contentType = contentType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建用户
	 */
	@Column(name ="CREATE_USER",nullable=true,length=255)
	public java.lang.String getCreateUser(){
		return this.createUser;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建用户
	 */
	public void setCreateUser(java.lang.String createUser){
		this.createUser = createUser;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_TIME",nullable=true)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新者
	 */
	@Column(name ="UPDATE_USER",nullable=true,length=100)
	public java.lang.String getUpdateUser(){
		return this.updateUser;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新者
	 */
	public void setUpdateUser(java.lang.String updateUser){
		this.updateUser = updateUser;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  包含的决策表主键
	 */
	@Column(name ="CONTAIN_TABLE",nullable=true,length=255)
	public java.lang.String getContainTable(){
		return this.containTable;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  包含的决策表主键
	 */
	public void setContainTable(java.lang.String containTable){
		this.containTable = containTable;
	}
}
