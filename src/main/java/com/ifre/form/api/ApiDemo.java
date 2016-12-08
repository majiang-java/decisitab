package com.ifre.form.api;

import java.util.Arrays;

public class ApiDemo {

	private String url;
	private String sysSource;
	private String encryptKey;
	private String signKey;
	private String prodId;
	private String prodIdShow;
	private String businessNum;
	private String modelType;
	private String[] key;
	private String[] value;
	// 舍弃？
	private String model;

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSysSource() {
		return sysSource;
	}

	public void setSysSource(String sysSource) {
		this.sysSource = sysSource;
	}

	public String getEncryptKey() {
		return encryptKey;
	}

	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}

	public String getSignKey() {
		return signKey;
	}

	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdIdShow() {
		return prodIdShow;
	}

	public void setProdIdShow(String prodIdShow) {
		this.prodIdShow = prodIdShow;
	}

	public String getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(String businessNum) {
		this.businessNum = businessNum;
	}

	public String[] getKey() {
		return key;
	}

	public void setKey(String[] key) {
		this.key = key;
	}

	public String[] getValue() {
		return value;
	}

	public void setValue(String[] value) {
		this.value = value;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return "ApiDemo [url=" + url + ", sysSource=" + sysSource + ", encryptKey=" + encryptKey + ", signKey="
				+ signKey + ", prodId=" + prodId + ", prodIdShow=" + prodIdShow + ", businessNum=" + businessNum
				+ ", modelType=" + modelType + ", key=" + Arrays.toString(key) + ", value=" + Arrays.toString(value)
				+ ", model=" + model + "]";
	}

}