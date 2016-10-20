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

import com.ifre.entity.brms.ObjPropEntity;

/**   
 * @Title: Entity
 * @Description: 业务对象
 * @author zhangdaihao
 * @date 2016-05-19 13:51:14
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class BizObjPage implements java.io.Serializable {
	/**保存-对象属性*/
	private List<ObjPropEntity> objPropList = new ArrayList<ObjPropEntity>();
	public List<ObjPropEntity> getObjPropList() {
		return objPropList;
	}
	public void setObjPropList(List<ObjPropEntity> objPropList) {
		this.objPropList = objPropList;
	}


	/**ID*/
	private java.lang.String id;
	/**产品ID*/
	private java.lang.String prodId;
	/**包ID*/
	private java.lang.String pckgId;
	/**名称*/
	private java.lang.String name;
	/**描述*/
	private java.lang.String descp;
	/**源码*/
	private java.lang.Object sourceCode;
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
	 *@return: java.lang.String  产品ID
	 */
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
	public java.lang.String getPckgId(){
		return this.pckgId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  包ID
	 */
	public void setPckgId(java.lang.String pckgId){
		this.pckgId = pckgId;
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
	 *方法: 取得java.lang.Object
	 *@return: java.lang.Object  源码
	 */
	public java.lang.Object getSourceCode(){
		return this.sourceCode;
	}

	/**
	 *方法: 设置java.lang.Object
	 *@param: java.lang.Object  源码
	 */
	public void setSourceCode(java.lang.Object sourceCode){
		this.sourceCode = sourceCode;
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
