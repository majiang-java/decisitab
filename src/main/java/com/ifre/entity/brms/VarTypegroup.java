package com.ifre.entity.brms;

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
 * VarTypegroup entity.
 */
@Entity
@Table(name = "brms_var_typegroup")
public class VarTypegroup extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private RuleProdEntity ruleProdEntity;
	private String varTypegroupname;
	private String varTypegroupcode;
	private java.lang.Integer type;	
	private List<VarType> varTypes = new ArrayList<VarType>();
	@Column(name = "var_typegroupname", length = 50)
	public String getVarTypegroupname() {
		return this.varTypegroupname;
	}

	public void setVarTypegroupname(String varTypegroupname) {
		this.varTypegroupname = varTypegroupname;
	}

	@Column(name = "var_typegroupcode", length = 50)
	public String getVarTypegroupcode() {
		return this.varTypegroupcode;
	}

	public void setVarTypegroupcode(String varTypegroupcode) {
		this.varTypegroupcode = varTypegroupcode;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "varTypegroup")
	public List<VarType> getVarTypes() {
		return this.varTypes;
	}

	public void setVarTypes(List<VarType> varTypes) {
		this.varTypes = varTypes;
	}
	
	/**
	 * ruleProdEntity
	 * @return  the ruleProdEntity
	*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typecode")
	public RuleProdEntity getRuleProdEntity() {
		return ruleProdEntity;
	}

	/**
	 * @param ruleProdEntity the ruleProdEntity to set
	 */
	public void setRuleProdEntity(RuleProdEntity ruleProdEntity) {
		this.ruleProdEntity = ruleProdEntity;
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

}