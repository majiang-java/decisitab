package com.ifre.entity.brms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;

/**
 * 模型变量表
 *  @author  caojianmiao
 */
@Entity
@Table(name = "brms_var_type")
public class VarType extends IdEntity implements java.io.Serializable {

	/**
	 * serialVersionUID:TODO
	 */
	
	private static final long serialVersionUID = 1L;
	private VarTypegroup varTypegroup;//变量分组
	private VarType varType;//父类型
	private String typename;//变量取值
	private BigDecimal codevalue1; //第一码值
	private BigDecimal codevalue2; //第二码值
	private BigDecimal score; //分值
	
	
//	private List<TPProcess> TSProcesses = new ArrayList();
	private List<VarType> varTypes =new ArrayList<VarType>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typegroupid")
	public VarTypegroup getVarTypegroup() {
		return this.varTypegroup;
	}

	public void setVarTypegroup(VarTypegroup varTypegroup) {
		this.varTypegroup = varTypegroup;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typepid")
	public VarType getVarType() {
		return this.varType;
	}

	public void setVarType(VarType varType) {
		this.varType = varType;
	}

	@Column(name = "typename", length = 50)
	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "varType")
	public List<VarType> getVarTypes() {
		return this.varTypes;
	}

	public void setVarTypes(List<VarType> varTypes) {
		this.varTypes = varTypes;
	}

	/**
	 * codevalue1
	 * @return  the codevalue1
	*/
	@Column(name ="codevalue1",nullable=true,precision=19,scale=2)
	public BigDecimal getCodevalue1() {
		return codevalue1;
	}

	/**
	 * @param codevalue1 the codevalue1 to set
	 */
	public void setCodevalue1(BigDecimal codevalue1) {
		this.codevalue1 = codevalue1;
	}

	/**
	 * codevalue2
	 * @return  the codevalue2
	*/
	@Column(name ="codevalue2",nullable=true,precision=19,scale=2)
	public BigDecimal getCodevalue2() {
		return codevalue2;
	}

	/**
	 * @param codevalue2 the codevalue2 to set
	 */
	public void setCodevalue2(BigDecimal codevalue2) {
		this.codevalue2 = codevalue2;
	}

	/**
	 * score
	 * @return  the score
	*/
	@Column(name ="score",nullable=true,precision=19,scale=2)
	public BigDecimal getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}

}