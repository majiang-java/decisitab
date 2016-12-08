package com.ifre.form.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 模型图表-近三月等级分布
 * 
 * @author CaiPeng
 *
 */
public class EChaetObject3Month {

	/** 对象名称 **/
	private String name;
	private String prodId;
	/** 对象元素集合-折线点集合 **/
	private Map<String, Integer> elementMap1;
	private Map<String, Integer> elementMap2;
	private Map<String, Integer> elementMap3;
	private List<String> keyList = new ArrayList<>();
	
	public EChaetObject3Month(){
		
	}
	
	public EChaetObject3Month(String name,String prodId){
		this.name = name;
		this.prodId = prodId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public Map<String, Integer> getElementMap1() {
		return elementMap1;
	}

	public void setElementMap1(Map<String, Integer> elementMap1) {
		this.elementMap1 = elementMap1;
	}

	public Map<String, Integer> getElementMap2() {
		return elementMap2;
	}

	public void setElementMap2(Map<String, Integer> elementMap2) {
		this.elementMap2 = elementMap2;
	}

	public Map<String, Integer> getElementMap3() {
		return elementMap3;
	}

	public void setElementMap3(Map<String, Integer> elementMap3) {
		this.elementMap3 = elementMap3;
	}

	public List<String> getKeyList() {
		return keyList;
	}

	public void setKeyList(List<String> keyList) {
		this.keyList = keyList;
	}

	@Override
	public String toString() {
		return "EChaetObject3Month [name=" + name + ", prodId=" + prodId + ", elementMap1=" + elementMap1
				+ ", elementMap2=" + elementMap2 + ", elementMap3=" + elementMap3 + ", keyList=" + keyList + "]";
	}

}