package com.ifre.form.api;

import java.util.List;

/**
 * 模型所有属性集合
 * 
 * @author CaiPeng
 *
 */
public class Model {
	
	/** 模型名称，暂时不使用 **/
	private String modelName;
	private String prodId;
	/** 模型类型，暂时不使用 **/
	private String modelType;
	// 模型属性及模型变量集合
	private List<ModelElement> modelElement;
	private ModelResultWy modelResultWy;
	// 模型约束暂时不考虑，后台进行约束，前台直接展示
	private ModelResultWyConstraintTruncation constraintTruncation;
	// 模型结果枚举，相互约束校验暂时不考虑，优先使用min为准
	private List<ModelResultWyEnumerationItem> enumerationItem;

	
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public List<ModelElement> getModelElement() {
		return modelElement;
	}

	public void setModelElement(List<ModelElement> modelElement) {
		this.modelElement = modelElement;
	}

	public ModelResultWy getModelResultWy() {
		return modelResultWy;
	}

	public void setModelResultWy(ModelResultWy modelResultWy) {
		this.modelResultWy = modelResultWy;
	}

	public ModelResultWyConstraintTruncation getConstraintTruncation() {
		return constraintTruncation;
	}

	public void setConstraintTruncation(ModelResultWyConstraintTruncation constraintTruncation) {
		this.constraintTruncation = constraintTruncation;
	}

	public List<ModelResultWyEnumerationItem> getEnumerationItem() {
		return enumerationItem;
	}

	public void setEnumerationItem(List<ModelResultWyEnumerationItem> enumerationItem) {
		// 添加升序排序功能，展示更方便，边界值以排序值为准
		int num = enumerationItem.size();
		double tempMinScore;
		ModelResultWyEnumerationItem temp;
		for (int i = 0; i < num; i++) {
			tempMinScore = enumerationItem.get(i).getMinScore();
			for (int j = i + 1; j < num; j++) {
				if (enumerationItem.get(j).getMinScore() < tempMinScore) {
					tempMinScore = enumerationItem.get(j).getMinScore();
					temp = enumerationItem.get(j);
					enumerationItem.set(j, enumerationItem.get(i));
					enumerationItem.set(i, temp);
				}
			}
		}
		this.enumerationItem = enumerationItem;
	}

	@Override
	public String toString() {
		return "Model [modelName=" + modelName + ", prodId=" + prodId + ", modelType=" + modelType + ", modelElement="
				+ modelElement + ", modelResultWy=" + modelResultWy + ", constraintTruncation=" + constraintTruncation
				+ ", enumerationItem=" + enumerationItem + "]";
	}
	
}