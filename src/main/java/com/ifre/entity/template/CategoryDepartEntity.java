package com.ifre.entity.template;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.web.system.pojo.base.TSDepart;

@Entity
@Table(name = "brms_temp_depart", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class CategoryDepartEntity implements java.io.Serializable {

	private String id;
	private TemplateCategoryEntity tempCategory;
	private TSDepart tsDepart;

	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public void setTsDepart(TSDepart tsDepart) {
		this.tsDepart = tsDepart;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id")
	public TSDepart getTsDepart() {
		return tsDepart;
	}


	public void setTempCategory(TemplateCategoryEntity tempCategory) {
		this.tempCategory = tempCategory;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "temp_category_id")
	public TemplateCategoryEntity getTempCategory() {
		return tempCategory;
	}


}
