package com.ifre.entity.brms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 模型执行结果
 * 
 * @author CaiPeng
 *
 */
@Entity
@Table(name = "brms_model_result")
public class ModelResultEntity implements java.io.Serializable {

	/**
	 * serialVersionUID:TODO
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String sysSource;
	private String prodId;
	private Integer totalScore;
	private Integer type;
	private String parameters;
	private String result;
	private String orgCode;
	private java.util.Date cTime;
	

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "c_time")
	public java.util.Date getcTime() {
		return cTime;
	}

	public void setcTime(java.util.Date cTime) {
		//使用数据库自生成时间
		//this.cTime = cTime;
	}

	@Column(name = "sys_source", nullable = false, length = 32)
	public String getSysSource() {
		return sysSource;
	}

	public void setSysSource(String sysSource) {
		this.sysSource = sysSource;
	}

	@Column(name = "prod_id", nullable = false, length = 36)
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	@Column(name = "total_score", precision = 11)
	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	@Column(name = "type", precision = 4)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "parameters")
	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	@Column(name = "result")
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(name = "org_code", nullable = false, length = 36)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Override
	public String toString() {
		return "ModelResultEntity [id=" + id + ", sysSource=" + sysSource + ", prodId=" + prodId + ", totalScore="
				+ totalScore + ", type=" + type + ", parameters=" + parameters + ", result=" + result + ", orgCode="
				+ orgCode + ", cTime=" + cTime + "]";
	}

}