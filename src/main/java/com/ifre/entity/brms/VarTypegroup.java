package com.ifre.entity.brms;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;

/**
 * VarTypegroup entity.
 */
@Entity
@Table(name = "brms_var_typegroup")
public class VarTypegroup extends IdEntity implements java.io.Serializable {
	private String varTypegroupname;
	private String varTypegroupcode;
	private String typecode;//模型类型
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
	
	@Column(name = "typecode", length = 50)
	public String getTypecode() {
		return this.typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

}