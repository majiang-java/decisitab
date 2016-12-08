package com.ifre.form.api;

import java.util.List;

/**
 * 模型变量
 * 
 * @author CaiPeng
 *
 */
public class ModelElement {

	/** 变量名称 **/
	private String name;
	/** 变量描述 **/
	private String value;
	private List<ModelElementItem> modelElementItem;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<ModelElementItem> getModelElementItem() {
		return modelElementItem;
	}

	public void setModelElementItem(List<ModelElementItem> modelElementItem) {
		this.modelElementItem = modelElementItem;
	}

	@Override
	public String toString() {
		return "ModelElement [name=" + name + ", value=" + value + ", modelElementItem=" + modelElementItem + "]";
	}

}