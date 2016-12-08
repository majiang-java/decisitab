package com.ifre.form.api;

import java.util.ArrayList;
import java.util.List;

/**
 * 模型图表-对象集合理解为N条折线
 * 
 * @author CaiPeng
 *
 */
public class EChaetObjectList {

	private List<EChaetObject> objectList = new ArrayList<>();
	private List<String> keyList = new ArrayList<>();

	public List<EChaetObject> getObjectList() {
		return objectList;
	}

	public void setObjectList(List<EChaetObject> objectList) {
		this.objectList = objectList;
	}

	public List<String> getKeyList() {
		return keyList;
	}

	public void setKeyList(List<String> keyList) {
		this.keyList = keyList;
	}

	@Override
	public String toString() {
		return "EChaetObjectList [objectList=" + objectList + ", keyList=" + keyList + "]";
	}

}