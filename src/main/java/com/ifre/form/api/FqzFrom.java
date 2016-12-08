package com.ifre.form.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class FqzFrom {

	private String name;
	private String idCard;
	private String mobile;
	private String companyTel;
	private String linkmanMobile;

	private String[] linkmanNames;
	private String[] linkmanMobiles;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCompanyTel() {
		return companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	public String getLinkmanMobile() {
		if(getLinkmanMobiles() != null){
			List<Map<String, Object>> linkmanList = new ArrayList<Map<String, Object>>();
			Map<String, Object> linkmanMap;
			int linkmanNum = getLinkmanMobiles().length;
			for (int i = 0; i < linkmanNum; i++) {
				linkmanMap = new HashMap<String, Object>();
				linkmanMap.put("mobile", getLinkmanMobiles()[i]);
				linkmanMap.put("name", getLinkmanNames()[i]);
				linkmanList.add(linkmanMap);
			}
			linkmanMobile = JSON.toJSONString(linkmanList);
		}
		return linkmanMobile;
	}

	public void setLinkmanMobile(String linkmanMobile) {
		this.linkmanMobile = linkmanMobile;
	}

	public String[] getLinkmanNames() {
		return linkmanNames;
	}

	public void setLinkmanNames(String[] linkmanNames) {
		this.linkmanNames = linkmanNames;
	}

	public String[] getLinkmanMobiles() {
		return linkmanMobiles;
	}

	public void setLinkmanMobiles(String[] linkmanMobiles) {
		this.linkmanMobiles = linkmanMobiles;
	}

	@Override
	public String toString() {
		return "FqzFrom [name=" + name + ", idCard=" + idCard + ", mobile=" + mobile + ", companyTel=" + companyTel
				+ ", linkmanMobile=" + linkmanMobile + ", linkmanNames=" + Arrays.toString(linkmanNames)
				+ ", linkmanMobiles=" + Arrays.toString(linkmanMobiles) + "]";
	}

}