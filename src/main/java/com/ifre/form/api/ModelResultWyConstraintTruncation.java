package com.ifre.form.api;

/**
 * 模型结果截断约束
 * 
 * @author CaiPeng
 *
 */
public class ModelResultWyConstraintTruncation {

	private ModelResultWyEnumerationItem minItem;
	private ModelResultWyEnumerationItem maxItem;

	public ModelResultWyEnumerationItem getMinItem() {
		return minItem;
	}

	public void setMinItem(ModelResultWyEnumerationItem minItem) {
		// 延长下限到0
		minItem.setMinScore(0);
		this.minItem = minItem;
	}

	public ModelResultWyEnumerationItem getMaxItem() {
		return maxItem;
	}

	public void setMaxItem(ModelResultWyEnumerationItem maxItem) {
		// 延长上限到9999999
		maxItem.setMaxScore(9999999);
		this.maxItem = maxItem;
	}

}