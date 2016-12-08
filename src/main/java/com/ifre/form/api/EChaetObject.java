package com.ifre.form.api;

import java.util.Map;

/**
 * 模型图表-一个对象理解为一条折线
 * 
 * @author CaiPeng
 *
 */
public class EChaetObject {

	/** 对象名称 **/
	private String name;
	/** 对象元素集合-折线点集合 **/
	private Map<String, Integer> elementMap;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Integer> getElementMap() {
		return elementMap;
	}

	public void setElementMap(Map<String, Integer> elementMap) {
		this.elementMap = elementMap;
	}

	@Override
	public String toString() {
		return "EChaetObject [name=" + name + ", elementMap=" + elementMap + "]";
	}

}