package com.ifre.entity.template;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "brms_template_desc", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TemplateCategoryEntity {
	
	private String id;
	
	private String image;
	
	private String attchment;
	
	private String remark;
	
	private String typename;
	
	private String typecode;
	
	private List<CategoryDepartEntity> list = new ArrayList<CategoryDepartEntity>();
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "IMAGE", nullable = true, length = 255)
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Column(name = "ATTACHMENT", nullable = true, length = 255)
	public String getAttchment() {
		return attchment;
	}

	public void setAttchment(String attchment) {
		this.attchment = attchment;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "REMARK", nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "TYPENAME", length = 255)
	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}
	@Column(name = "TYPECODE", length = 255)
	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	
	@JsonIgnore
    @OneToMany(mappedBy = "tempCategory")
	public List<CategoryDepartEntity> getList() {
		return list;
	}

	public void setList(List<CategoryDepartEntity> list) {
		this.list = list;
	}

	
}
